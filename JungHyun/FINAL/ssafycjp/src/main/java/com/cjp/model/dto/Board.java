package com.cjp.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 DTO")
public class Board {
	private int id;
	// 게시판 타입
	private int postBoardId;
	private int category;
	private String categoryName;
	private String title;
	private String content;
	private String writer;
	private int like;
	private LocalDateTime regDate;
	private int viewCnt;

	
	public Board() {
		regDate = LocalDateTime.now();
	}
	
//	public Board(int postBoardId, int category, String title, String content, String writer, LocalDateTime regDate) {
//		this.postBoardId = postBoardId;
//		this.category = category;
//		this.title = title;
//		this.content = content;
//		this.writer = writer;
//		regDate = LocalDateTime.now();
//	}
	
	public Board(int postBoardId, int category, String title, String content,
			LocalDateTime regDate) {
		super();
		this.postBoardId = postBoardId;
		this.category = category;
		this.title = title;
		this.content = content;
		regDate = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostBoardId() {
		return postBoardId;
	}

	public void setPostBoardId(int postBoardId) {
		this.postBoardId = postBoardId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", postBoardId=" + postBoardId + ", category=" + category + ", categoryName="
				+ categoryName + ", title=" + title + ", content=" + content + ", writer=" + writer + ", like=" + like
				+ ", regDate=" + regDate + ", viewCnt=" + viewCnt + "]";
	}
}
