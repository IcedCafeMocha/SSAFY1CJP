package com.cjp.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjp.model.dao.BoardDao;
import com.cjp.model.dto.Board;
import com.cjp.model.dto.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private final BoardDao boardDao;

	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	// 전체 게시글 조회
	@Override
	public List<Board> getBoardList() {
		return boardDao.selectAll();
	}
	
	// 특정 게시판 게시글 조회
	@Override
	public List<Board> getBoardListByPostBoardId(int postBoardId){
		return boardDao.selectByPostBoardId(postBoardId);
	}

	@Override
	public Board readBoard(int id) {
		System.out.println(id + "번 게시글을 읽어옵니다.");
		boardDao.updateViewCnt(id); // 조회수 증가
		return boardDao.selectOne(id);
	}

	@Transactional
	@Override
	public void writeBoard(Board board) {
		System.out.println("게시글 작성합니다.");
		boardDao.insertBoard(board);
	}

	@Transactional
	@Override
	public void removeBoard(int id) {
		System.out.println(id+"번 게시글을 삭제하겠습니다.");
		boardDao.deleteBoard(id);
	}

	@Transactional
	@Override
	public void modifyBoard(Board board) {
		System.out.println("게시글 수정");
		boardDao.updateBoard(board);
	}

	@Override
	public List<Board> search(SearchCondition searchCondition) {
		return boardDao.search(searchCondition);
	}
	
	// 좋아요 증가
	@Transactional
	@Override
	public void updateLikeUp(String userId,int boardId) {
		boardDao.insertBoardLike(userId, boardId);
		boardDao.updateBoardLike(boardId);
	}
	
	// 좋아요 감소
	@Transactional
	@Override
	public void updateLikeDown(String userId,int boardId) {
		boardDao.deleteBoardLike(userId, boardId);
		boardDao.updateBoardDislike(boardId);
	}
	
	// 전체 게시판 이름 조회
	@Override
	public List<String> getNames() {
        return boardDao.getPostboardNames();
    }
	
	// 해당 아이디 좋아요 여부 확인
	@Override
	public boolean checkBoardLiked(String userId, int boardId) {
		return boardDao.hasUserLikedBoard(userId, boardId);	
	}
	
	// 해당 게시판의 게시글 말머리 목록 조회
	@Override
    public List<Map<String, Object>> getCategoriesByPostBoardId(int postBoardId) {
        return boardDao.selectCategoriesByPostBoardId(postBoardId);
    }
}
