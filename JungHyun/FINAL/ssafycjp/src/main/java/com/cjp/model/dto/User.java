package com.cjp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 DTO")
public class User {
	private String name;
	private String id;
	private String email;
	private String password;
	private int age;
	private String img;
	private String sex;
	private double height;
	private double goalWeight;
	private double nowWeight;
	
	public User() {
	}

	public User(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
	}
	
	public User(String name, String id,String email, String password, int age, String img, String sex, double goalWeight,
			double nowWeight,double height) {
		this.name = name;
		this.id = id;
		this.email = email;
		this.password = password;
		this.age = age;
		this.img = img;
		this.sex = sex;
		this.goalWeight = goalWeight;
		this.nowWeight = nowWeight;
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getGoalWeight() {
		return goalWeight;
	}

	public void setGoalWeight(double goalWeight) {
		this.goalWeight = goalWeight;
	}

	public double getNowWeight() {
		return nowWeight;
	}

	public void setNowWeight(double nowWeight) {
		this.nowWeight = nowWeight;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", email=" + email + ", password=" + password + ", age=" + age
				+ ", img=" + img + ", sex=" + sex + ", height=" + height + ", goalWeight=" + goalWeight + ", nowWeight="
				+ nowWeight + "]";
	}

	
}
