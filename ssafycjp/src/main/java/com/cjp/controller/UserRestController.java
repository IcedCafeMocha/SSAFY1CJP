package com.cjp.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cjp.model.dto.Attendance;
import com.cjp.model.dto.User;
import com.cjp.model.service.AttendanceService;
import com.cjp.model.service.UserService;
import com.cjp.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final ResourceLoader resourceLoader;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceService attendanceService;
	
	public UserRestController(UserService userService, ResourceLoader resourceLoader,AttendanceService attendanceService) {
		this.userService = userService;
		this.resourceLoader = resourceLoader;
		this.attendanceService = attendanceService;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		System.out.println("로그인 시도: " + user.getId());
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		
		// 사용자 인증
		User checkUser = userService.login(user.getId(), user.getPassword());

		// login은 id, pw 일치 시 해당 유저 정보 반환, 일치하지 않을 시 null 반환
		// 사용자 인증에 성공했을 경우
		if (checkUser != null) {
			// 토큰 생성
			String token = jwtUtil.createToken(checkUser.getId());
			result.put("success", SUCCESS);
			result.put("access-token", token);
			status = HttpStatus.OK;
			return new ResponseEntity<>(result, status);
		} else {
			result.put("fail", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(result, status);
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		User tmp = userService.search(user.getId());
		if (tmp == null) {
			userService.signup(user);
			return new ResponseEntity<>("Signup successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("이미 있는 아이디입니다", HttpStatus.CONFLICT);
		}
	}
	// 회원 가입할 때 이미지 들고오기
	@PostMapping("/userImg")
	public ResponseEntity<?> userImgUpload(@RequestParam("userId") String id,@RequestParam("file") MultipartFile file, Model model) throws IllegalStateException, IOException {
		if (file != null && file.getSize() > 0) {
//			String fileName = file.getOriginalFilename();
			String fileName = id + "_profile.jpg";
			 String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/upload";  // 애플리케이션 루트 디렉토리를 기준으로 설정
	        File uploadPath = new File(uploadDir);
	
			 if (!uploadPath.exists()) {
		            uploadPath.mkdirs();  // 디렉토리가 존재하지 않으면 생성
		        }
			File destinationFile = new File(uploadPath, fileName);
			file.transferTo(destinationFile);
		}
		return new ResponseEntity<>("Signup successful", HttpStatus.OK);
	}

	// 유저 정보 조회
	@GetMapping("/info")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) {
        String id = jwtUtil.getIdFromToken(token);
        User user = userService.search(id);
        // 감춰야 할 정보는 여기서 제거 가능! (ex. 비밀번호 부분을 null 값으로 변경)
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
	
//	@GetMapping("/{id}")
//	public ResponseEntity<User> userInfo(@PathVariable("id") String id) {
//		System.out.println("들어왔어!");
//		User user = userService.search(id);
//		System.out.println(user);
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//
//	}
	
	// 테스트용
	@GetMapping("/getList")
	public ResponseEntity<?> getList() {
		List<User> list = userService.getUserList();
		System.out.println(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 회원 정보 수정
	@PutMapping("/changeInfo")
	public ResponseEntity<?> changeInfo(@RequestBody Map<String, String> params) {
	    String id = params.get("userId");
	    String field = params.get("field");
	    String changeValue = params.get("changeValue");
		

		if(field.equals("age")) {
			int changedAge = Integer.parseInt(changeValue);
			userService.changeUserAge(id,field,changedAge);
		} else if(field.equals("goalWeight")||field.equals("nowWeight")) {
			double changedWeight = Double.parseDouble(changeValue);
			userService.changeUserWeight(id,field,changedWeight);
		} else {
			userService.changeUserInfo(id,field,changeValue);
		}
		
		 return new ResponseEntity<>(HttpStatus.OK);

	}
	// 프로필 사진 가져오기
		@GetMapping("/profileImage/{userId}")
		public ResponseEntity<String> getProfileImageUrl(@PathVariable String userId) {
		    String fileName = userId + "_profile.jpg";
		    String filePath = "/upload/" + fileName;
		    File imgFile = new File(System.getProperty("user.dir") + "/src/main/resources/static" + filePath);
		    if (imgFile.exists()) {
		    	String fullUrl = "http://localhost:8080" + filePath; // 서버 주소와 파일 경로를 결합
		        return new ResponseEntity<>(fullUrl, HttpStatus.OK);
		    }
		    return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
		}

		// 회원 프로필 사진 수정
		@PutMapping("/updateProfileImage")
		public ResponseEntity<?> updateProfileImage(@RequestParam("userId") String id, @RequestParam("file") MultipartFile file) {
		    try {
		        if (file.isEmpty()) {
		            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
		        }

		        String fileName = id + "_profile.jpg";  // 이 규칙을 통일
		        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/upload";
		        File uploadPath = new File(uploadDir);

		        if (!uploadPath.exists()) {
		            uploadPath.mkdirs();
		        }

		        File destinationFile = new File(uploadPath, fileName);
		        file.transferTo(destinationFile);

		        return new ResponseEntity<>("Profile image updated successfully", HttpStatus.OK);
		    } catch (Exception e) {
		        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}

	// 출석
	@PostMapping("/attendance")
	public ResponseEntity<?> createAttendance(@RequestBody Attendance attendance) {
		attendanceService.createAttendance(attendance);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 츌석 가져오기
	@GetMapping("/attendance")
	public ResponseEntity<?> getAllAttendances() {
		List<Attendance> list = attendanceService.getAllAttendances();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// 유저 출석 가져오기
	@GetMapping("/attendance/{userId}")
	public ResponseEntity<?> getAttendancesByUserId(@PathVariable("userId") String userId) {
		System.out.println("??");
		List<Attendance> list = attendanceService.getAttendancesByUserId(userId);
		System.out.println(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// 성공적으로 회원가입이 되었어!
	// 1. 회원가입 축하합니다. 페이지로 이동
	// 2. 로그인 페이지로 이동(v)
	// 3. 게시글 목록 화면으로 이동
	// 3-1. 지금 User 객체를 그대로 실어서 로그인 요청을 보내기
	// 3-2. 세션불러다가 직접 등록해버리고 넘어가

	/*
	 * 이전 꺼!!!!! private final UserService userService;
	 * 
	 * @Autowired public UserController(UserService userService) { this.userService
	 * = userService; }
	 * 
	 * //로그인 페이지 주세요
	 * 
	 * @GetMapping("/login") public String loginForm() { return "/user/loginform"; }
	 * 
	 * //로그인 해주세요
	 * 
	 * @PostMapping("/login") // public String login(@RequestParam("id") String
	 * id, @RequestParam("password") String password) { // // } public String
	 * login(@ModelAttribute User user, HttpSession session) { User tmp =
	 * userService.login(user.getId(), user.getPassword()); //tmp에 들어갈 수 있는 값은? //1.
	 * 실제로 로그인 잘 되었다면 유저객체가 반환이 되어 사용할 수 있음 //2. 뭔가 아이디나 비밀번호가 틀렸어! null if(tmp ==
	 * null) return "redirect:login";
	 * 
	 * //로그인 성공 (세션 영역에 정보를 저장했다) session.setAttribute("loginUser", tmp.getName());
	 * return "redirect:list"; }
	 * 
	 * //로그아웃
	 * 
	 * @GetMapping("/logout") public String logout(HttpSession session) { //
	 * session.removeAttribute("loginUser"); session.invalidate(); //리스트 화면으로 갔는데,
	 * 로그인페이지로 보낼 수도 있다. return "redirect:list"; }
	 * 
	 * 
	 * 
	 * @GetMapping("/users") public String userList(Model model) {
	 * model.addAttribute("userList", userService.getUserList()); return
	 * "/user/adminPage"; }
	 */

}
