package com.cjp.model.service;

import java.util.List;

import com.cjp.model.dto.Reply;
import com.cjp.model.dto.Rereply;

public interface RereplyService {
	//대댓글 전체 조회
	public List<Rereply> getRereplyList(int replyId);
	
	//대댓글 작성
	public void writeRereply(Rereply rereply);
	
	//대댓글 삭제
	public void removeRereply(int rereplyId);
	
	//대댓글 수정
	public void modifyRereply(Rereply rereply);
	
}
