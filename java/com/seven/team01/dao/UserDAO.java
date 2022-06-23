package com.seven.team01.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.UserVO;
@Repository
public class UserDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	//회원가입
	public int insertUser(UserVO userVO) {
		int rowcnt = sqlSessionTemplate.insert("user_ns.insertUser", userVO);
		System.out.println(rowcnt + "행 삽입");
		return rowcnt;
		}
	//아이디체크
	public int checkUserId(String uId) {
		int result =sqlSessionTemplate.selectOne("user_ns.checkuID", uId);
		System.out.println(result +"아이디 유무 체크");
		return result;
	}
	// 로그인
	public UserVO login(UserVO userVO) {
		return sqlSessionTemplate.selectOne("user_ns.login", userVO);
	}
	
	//회원정보수정
	public void updateUser(UserVO userVO) {
		sqlSessionTemplate.update("user_ns.updateUser", userVO);
	}
	//회원탈퇴
	public void deleteUser(UserVO userVO) {
	   sqlSessionTemplate.delete("user_ns.deleteUser", userVO);
	}
	public int countPagingUser(String uId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uId", uId);
		return sqlSessionTemplate.selectOne("user_ns.countPagingUser", map);
	}
	//로그인정보
	public UserVO selectlogin(UserVO userVO) {
		return sqlSessionTemplate.selectOne("user_ns.selectUser", userVO);
	}
	
}
