package com.seven.team01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seven.team01.dao.GoodsDAO;
import com.seven.team01.dao.ReplyDAO;
import com.seven.team01.vo.ReplyVO;
@Service
public class ReplyService {
	@Autowired
	private ReplyDAO replyDAO;
	@Autowired
	private GoodsDAO goodsDAO;

	public void insertReReply(ReplyVO reply) {
		 replyDAO.insertReReply(reply);
	}

	public List<ReplyVO> replyList(int gCode) {
		List<ReplyVO> reply = replyDAO.replyList(gCode);
		for(ReplyVO r: reply) {
			System.out.println(r);
		}
		return reply;
	}
	
	@Transactional
	public void insertReply(ReplyVO reply) {
		replyDAO.insertReply(reply);
		goodsDAO.updateReplyCnt(reply.getrGCode());
	}
}
