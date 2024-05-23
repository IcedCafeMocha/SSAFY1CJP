package com.cjp.model.service;

import java.util.List;

import com.cjp.model.dto.User;

public interface UserService {
	// 전체 사용자 목록 불러오기
	public List<User> getUserList();
	
	// 사용자 등록하기
	public void signup(User user);
	
	// 로그인 하기
	public User login(String id, String password);
	
	// 회원탈퇴 -> 불가능
//	public void signout(String id);
	
	// 회원 정보 조회
	public User search(String id);
	
	// 회원 정보 수정
//	public void modifyUserInfo(User user);

	// 이게 진짜 회원 정보 수정
	public void changeUserAge(String id, String field, int changedAge);

	public void changeUserWeight(String id, String field, double changedWeight);

	public void changeUserInfo(String id, String field, String changeValue);
}
