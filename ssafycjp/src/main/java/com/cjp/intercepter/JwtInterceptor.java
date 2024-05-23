package com.cjp.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cjp.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private final String HEADER_AUTH ="Authorization";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 클라이언트는 서버에게 실제 요청을 보내려고 했을 때 사전 요청을 보내서 서버가 현재 요청을 수락할 수 있는지 확인
		// 그게 OPTIONS 요청
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}
		
		String token = null;
		if(request.getHeader(HEADER_AUTH) != null) {
			String[] input = request.getHeader(HEADER_AUTH).split(" ");			
			token = input[1];
		}
		if (token != null) {
            try {
                jwtUtil.validate(token);
                System.out.println("토큰 검증 결과: "+jwtUtil.validate(token));
                return true;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"유효하지 않은 토큰입니다.\"}");
                return false;
            }
        }

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("{\"message\":\"토큰이 없습니다.\"}");
        return false;
	}
	
}
