package com.cjp.model.dao;

import java.util.List;
import java.util.Map;

import com.cjp.model.dto.User;

public interface UserDao {
	// (admin) 유저 목록을 봐야 하나?
	public List<User> selectAll();
	// 회원 등록
	public void insertUser(User user);
	
	// (admin or 정보 확인) 유저 선택
	public User selectOne(String id);
	
	// 회원 탈퇴 -> 불가능
//	public void deleteUser(String id);
	
//	// 회원 정보 수정
//	public void updateUserInfo(User user);
	
	public void updateUserWeight(String id, String field, double changedWeight);
	public void updateUserInfo(String id, String field, String changeValue);
	public void updateUserAge(String id, String field, int changedAge);
}