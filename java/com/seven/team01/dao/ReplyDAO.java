package com.seven.team01.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.ReplyVO;
@Repository
public class ReplyDAO {
	@Autowired
	SqlSessionTemplate sqlSession;

	public void insertReReply(ReplyVO reply) {
		 sqlSession.insert("goodMapper.insertReReply", reply);
	}
	
	public List<ReplyVO> replyList(int gCode){
		return sqlSession.selectList("goodMapper.selectReply",gCode);
	}
	
	public void insertReply(ReplyVO reply) {
		int cnt = sqlSession.insert("goodMapper.insertReply",reply);
	}
}
