package com.cjp.model.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjp.model.dao.AttendanceDao;
import com.cjp.model.dto.Attendance;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	
	@Autowired
	private final AttendanceDao attendanceDao;
	
	public AttendanceServiceImpl(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}
	
	@Override
	public void createAttendance(Attendance attendance) {
		attendanceDao.insert(attendance);
		
	}

	@Override
	public List<Attendance> getAllAttendances() {
		return attendanceDao.selectAll();
	}

	@Override
    public List<Attendance> getAttendancesByUserId(String userId) {
        List<Attendance> attendanceList = attendanceDao.getAttendancesByUserId(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return attendanceList.stream()
            .peek(att -> att.setDateString(att.getDate().format(formatter)))
            .collect(Collectors.toList());
    }


}
