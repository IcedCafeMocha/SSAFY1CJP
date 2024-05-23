package com.cjp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "운동 DTO")
public class Exercise {
	private int id;
	private String name;
	private int check;
	private int cal;
	private int time;
	

	public Exercise() {
		super();
	}

	public Exercise(String name, int check, int cal, int time) {
		super();
		this.name = name;
		this.check = check;
		this.cal = cal;
		this.time = time;
	}
	
	public Exercise(int id, String name, int check, int cal, int time) {
		super();
		this.id = id;
		this.name = name;
		this.check = check;
		this.cal = cal;
		this.time = time;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}
	
	public int getCal() {
		return cal;
	}
	
	public void setCal(int cal) {
		this.cal = cal;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	
	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", check=" + check + ", cal=" + cal + ", time=" + time + "]";
	}

}
