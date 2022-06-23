package com.seven.team01.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.QnaReplyVO;
import com.seven.team01.vo.QnaVO;

@Repository
public class QnaDAO {
	@Autowired
	SqlSessionTemplate sqlSession;
 
	public void insertQna(QnaVO qnaVO) {
		sqlSession.insert("qnaMapper.insertQna", qnaVO);		
	}

	public QnaVO detailQna(int qNum) {
		QnaVO qnaVO = sqlSession.selectOne("qnaMapper.detailQna", qNum);		
		return qnaVO;
	}

	public void qnaDelete(int qNum) {
		sqlSession.delete("qnaMapper.deleteQna", qNum);		
		
	}

	public void qnaReplyInsert(QnaReplyVO qnaReplyVO) {
		System.out.println("qna댓글 dao");
		sqlSession.insert("qnaMapper.ReplyInsert", qnaReplyVO);
	}
	
	
	public List<QnaReplyVO> selectQnaReply(int qNum) {
		List<QnaReplyVO> list = sqlSession.selectList("qnaMapper.selectQnaReply",qNum);	
		return list;
	}

	public void qnaReplyDelete(int qRNum) {
		sqlSession.delete("qnaMapper.qnaReplyDelete", qRNum);			
	}

	public void updateState(int qNum) {
		sqlSession.update("qnaMapper.updateState", qNum);
	}
	
	//##페이징 추가함
	public List<QnaVO> selectQna(int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("qnaMapper.selectQna",map);
	}

	public int countForQna() {
		Map<String, String> map = new HashMap<String, String>();
		return sqlSession.selectOne("qnaMapper.countForQna", map);
	}
	
		public void updateQna(QnaVO qnaVO) {
		sqlSession.update("qnaMapper.updateQna", qnaVO);
	}

	public void qnaReplyUpdateDelete(int qNum) {
		System.out.println(qNum);
		sqlSession.update("qnaMapper.DeleteUpdateState",qNum);

	}
	
}
