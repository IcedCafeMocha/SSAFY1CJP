package com.cjp.model.dao;

import java.util.List;

import com.cjp.model.dto.Attendance;

public interface AttendanceDao {
	
	public void insert(Attendance attendance);
	
	public List<Attendance> selectAll();
	
	public List<Attendance> getAttendancesByUserId(String userId);

}
