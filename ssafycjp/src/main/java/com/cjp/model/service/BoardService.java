package com.cjp.model.service;

import java.util.List;
import java.util.Map;

import com.cjp.model.dto.Board;
import com.cjp.model.dto.SearchCondition;

public interface BoardService {
	//게시글 전체 조회
	public List<Board> getBoardList();
	
	// 특정 게시판 게시글 조회
	public List<Board> getBoardListByPostBoardId(int postBoardId);
	
	//게시글 상세조회
	public Board readBoard(int id); //Dao -> 뷰카운트 +1 / 게시글 조회 
	
	//게시글 작성
	public void writeBoard(Board board);
	
	///게시글 삭제
	public void removeBoard(int id);
	
	//게시글 수정
	public void modifyBoard(Board board);
	
	// 좋아요 수 증가
	public void updateLikeUp(String userId, int id);

	// 좋아요 수 감소
	public void updateLikeDown(String userId,int id);
	
	//게시글 검색
	public List<Board> search(SearchCondition searchCondition);
	
	// 전체 게시판 이름 조회
	public List<String> getNames();
	
	// 해당 아이디 좋아요 여부 확인
	public boolean checkBoardLiked(String userId, int boardId);

	List<Map<String, Object>> getCategoriesByPostBoardId(int postBoardId);
}
