package com.cjp.model.service;

import java.util.List;

import com.cjp.model.dto.Attendance;

public interface AttendanceService {
	 public void createAttendance(Attendance attendance);

	 public List<Attendance> getAllAttendances();

	 public List<Attendance> getAttendancesByUserId(String userId);

}
