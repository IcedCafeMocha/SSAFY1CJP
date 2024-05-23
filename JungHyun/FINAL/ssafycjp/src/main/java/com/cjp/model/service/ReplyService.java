package com.cjp.model.service;

import java.util.List;

import com.cjp.model.dto.Board;
import com.cjp.model.dto.Reply;
import com.cjp.model.dto.SearchCondition;

public interface ReplyService {
	//댓글 전체 조회
	public List<Reply> getReplyList(int boardNo);
	
	//댓글 작성
	public void writeReply(Reply reply);
	
	///댓글 삭제
	public void removeReply(int id);
	
	//댓글 수정
	public void modifyReply(Reply reply);
	
	// 추천수 증가
	public void updateLikeUp(String userId,int replyId);

	// 추천수 감소
	public void updateLikeDown(String userId,int replyId);	
	
	// 해당 아이디 좋아요 여부 확인
	public boolean checkReplyLiked(String userId, int replyId);

}
