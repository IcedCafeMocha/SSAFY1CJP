package com.cjp.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjp.model.dao.ReplyDao;
import com.cjp.model.dto.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private final ReplyDao replyDao;

	@Autowired
	public ReplyServiceImpl(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	@Override
	public List<Reply> getReplyList(int boardNo) {
		return replyDao.selectAll(boardNo);
	}

	@Transactional
	@Override
	public void writeReply(Reply reply) {
		replyDao.insertReply(reply);
	}

	@Transactional
	@Override
	public void removeReply(int id) {
		replyDao.deleteReply(id);
	}

	@Transactional
	@Override
	public void modifyReply(Reply reply) {
		replyDao.updateReply(reply);
	}

	@Override
	public void updateLikeUp(String userId,int replyId) {
		// DB에서 라이크 1 증가
		// 좋아요는 userid랑 게시글 id랑 엮여서 unique값이므로 1계당 1좋아요만 가능
		replyDao.likeUp(userId,replyId);
		replyDao.updateReplylike(replyId);
	}

	@Override
	public void updateLikeDown(String userId,int replyId) {
		// DB에서 라이크 1 감소
		replyDao.LikeDown(userId,replyId);
		replyDao.updateReplylike(replyId);
	}
	
	@Override
	public boolean checkReplyLiked(String userId, int replyId) {
		return replyDao.hasUserLikedReply(userId, replyId);
	}


}
