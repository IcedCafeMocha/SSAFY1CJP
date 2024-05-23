package com.cjp.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 DTO")
public class Reply {
	private int id;
	private int boardId;
	private String writer;
	private String content;
	private int like;
	private LocalDateTime regDate;
	
	public Reply() {
		
	}
	
	public Reply(int boardId,String writer, String content, LocalDateTime regDate) {
		this.boardId = boardId;
		this.writer = writer;
		this.content = content;
		this.regDate = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	public String toString() {
		return "Reply [id=" + id + ", boardId=" + boardId + ", writer=" + writer + ", content=" + content + ", like="
				+ like + ", regDate=" + regDate + "]";
	}
	
	
	
}
