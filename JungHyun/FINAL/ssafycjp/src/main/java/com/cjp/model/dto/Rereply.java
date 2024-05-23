package com.cjp.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대댓글 DTO")
public class Rereply {
	private int id;
	private int reply_id;
	private int boardNo;
	private String writer;
	private String content;
	private LocalDateTime regDate;
	
	public Rereply() {
		
	}
	
	public Rereply(int boardNo,String writer, String content, LocalDateTime regDate) {
		this.boardNo = boardNo;
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

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Rereply [id=" + id + ", reply_id=" + reply_id + ", boardNo=" + boardNo + ", writer=" + writer
				+ ", content=" + content + ", regDate=" + regDate + "]";
	}
}
