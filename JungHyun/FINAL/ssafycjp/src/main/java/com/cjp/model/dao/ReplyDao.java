package com.cjp.model.dao;

import java.util.List;

import com.cjp.model.dto.Reply;

public interface ReplyDao {
	// 게시글에 대한 전체 댓글 불러오기
	public List<Reply> selectAll(int boardNo);
	// 댓글 작성
	public void insertReply(Reply reply);
	// 댓글 수정
	public void updateReply(Reply reply);
	// 댓글 삭제
	public void deleteReply(int id);
	// 댓글 추천수 증가
	public void likeUp(String userId,int replyId);
	
	// 댓글 추천수 감소
	public void LikeDown(String userId,int replyId);
	
	// 해당 아이디 좋아요 여부 확인
	public boolean hasUserLikedReply(String userId, int replyId);
	
	// 좋아요 수 업데이트 (증가)
	public void updateReplylike(int boardId);
	// 끝
}
