package com.seven.team01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.team01.dao.QnaDAO;
import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.QnaReplyVO;
import com.seven.team01.vo.QnaVO;

@Service
public class QnaService {
	@Autowired
	QnaDAO qnaDAO;
	
	public void insertQna(QnaVO qnaVO) {
		System.out.println(qnaVO);
		qnaDAO.insertQna(qnaVO);
		
	}

	public QnaVO qnaDetail(int qNum) {
		QnaVO qnaVO = qnaDAO.detailQna(qNum);
		return qnaVO;
	}

	public void qnaDelete(int qNum) {
		qnaDAO.qnaDelete(qNum);
	}

	public void ReplyInsert(QnaReplyVO qnaReplyVO) {
		System.out.println("qna 댓글 service");
		qnaDAO.qnaReplyInsert(qnaReplyVO);
	}

	public List<QnaReplyVO> selectQnaReply(int qNum) {
		 List<QnaReplyVO>  list = qnaDAO.selectQnaReply(qNum);
		return list;
	}

	public void qnaReplyDelete(int qRNum) {
		qnaDAO.qnaReplyDelete(qRNum);
		
	}

	public void updateState(int qNum) {
		qnaDAO.updateState(qNum);
	}
	
	//페이징 추가함
	public List<QnaVO> selectQna(int start, int end) {
		 List<QnaVO>  list = qnaDAO.selectQna(start, end);
		return list;
	}

	public int countForQna() {
		return qnaDAO.countForQna();
	}
	
	public void updateQna(QnaVO qnaVO) {
		qnaDAO.updateQna(qnaVO);
	}

	//qna댓글 상태 변경
	public void qnaReplyUpdateDelete(int qNum) {
		System.out.println(qNum);
		qnaDAO.qnaReplyUpdateDelete(qNum);//업데이트해주세요
	}
}
