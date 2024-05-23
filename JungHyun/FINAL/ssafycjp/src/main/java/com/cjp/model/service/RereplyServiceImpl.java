package com.cjp.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjp.model.dao.RereplyDao;
import com.cjp.model.dto.Rereply;

@Service
public class RereplyServiceImpl implements RereplyService {
	
	@Autowired
	private final RereplyDao rereplyDao;

	@Autowired
	public RereplyServiceImpl(RereplyDao rereplyDao) {
		this.rereplyDao = rereplyDao;
	}

	@Override
	public List<Rereply> getRereplyList(int replyId) {
		return rereplyDao.selectAllRereply(replyId);
	}

	@Transactional
	@Override
	public void writeRereply(Rereply rereply) {
		rereplyDao.insertRereply(rereply);
		
	}

	@Transactional
	@Override
	public void removeRereply(int rereplyId) {
		rereplyDao.deleteRereply(rereplyId);
		
	}

	@Transactional
	@Override
	public void modifyRereply(Rereply rereply) {
		rereplyDao.updateRereply(rereply);
	}


}
