package com.cjp.model.dao;

import java.util.List;

import com.cjp.model.dto.Rereply;

public interface RereplyDao {
	// 댓글에 대한 전체 대댓글 불러오기
	public List<Rereply> selectAllRereply(int replyId);
	// 대댓글 작성
	public void insertRereply(Rereply rereply);
	// 대댓글 수정
	public void updateRereply(Rereply rereply);
	// 대댓글 삭제
	public void deleteRereply(int rereplyId);
	// 끝
}
