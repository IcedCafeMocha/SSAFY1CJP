package com.cjp.model.dao;

import java.util.List;
import java.util.Map;

import com.cjp.model.dto.Board;
import com.cjp.model.dto.SearchCondition;

public interface BoardDao {
	// 전체 게시글을 조회
	public List<Board> selectAll();
	
	// 특정 게시판 게시글 조회
	public List<Board> selectByPostBoardId(int postBoardId);

	// ID에 해당하는 게시글 하나 가져오기
	public Board selectOne(int id);

	// 게시글 등록
	public void insertBoard(Board board);

	// 게시글 삭제
	public void deleteBoard(int id);

	// 게시글 수정
	public void updateBoard(Board board);

	// 조회수 증가
	public void updateViewCnt(int id);

	// 추천수 증가
	public void upLike(String userId,int boardId);

	// 추천수 감소
	public void downLike(String userId,int boardId);

	// 검색 기능
	public List<Board> search(SearchCondition searchCondition);
	
	// 모든 게시판의 이름 목록 가져오기
	public List<String> getPostboardNames();
	
	// 게시판-말머리 리스트 조회
	List<Map<String, Object>> getCategoriesByPostBoardId(int postBoardId);
	
	// 업데이트 후 갈아 끼우기
	public void insertBoardLike(String userId, int boardId);
	
	// 좋아요 수 업데이트 (증가)
	public void updateBoardLike(int boardId);
	
	// 업데이트 후 갈아 끼우기
	public void deleteBoardLike(String userId, int boardId);
	
	// 좋아요 수 업데이트 (감소)
	public void updateBoardDislike(int boardId);
	
	// 해당 아이디 좋아요 여부 확인
	public boolean hasUserLikedBoard(String userId, int boardId);

	public List<Map<String, Object>> selectCategoriesByPostBoardId(int postBoardId);

}
