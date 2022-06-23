package com.seven.team01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.team01.dao.UserDAO;
import com.seven.team01.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserDAO udao; // 회원 가입

	public int createUser(UserVO userVO) {
		return udao.insertUser(userVO);
	}

	// 아이디 체크
	public int checkUserId(String uId) {

		System.out.println("UserServiceImpl의 아이디 체크");
		return udao.checkUserId(uId);
	}

	// 로그인
	public UserVO login(UserVO userVO) {
		System.out.println("UserServiceImpl의 login");
		return udao.login(userVO);
	}

	// 회원정보 수정
	public void updateUser(UserVO userVO) {
		System.out.println("UserServiceImpl의 updateUser");
		udao.updateUser(userVO);
	}

	// 회원 탈퇴
	public void deleteUser(UserVO userVO) {
		udao.deleteUser(userVO);
	}
	//유저의 주문 목록 갯수 카운터
	public int countForPagingUser(String uId) {
		return udao.countPagingUser(uId);
	}
	
	//로그인정보
	public UserVO selectlogin(UserVO userVO) {
		return udao.selectlogin(userVO);
	}

}
