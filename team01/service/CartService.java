package com.seven.team01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.team01.dao.CartDAO;
import com.seven.team01.vo.CartVO;
import com.seven.team01.vo.UserVO;

@Service
public class CartService {
	@Autowired
	CartDAO cartDAO;
	
	//카트 추가(H 수정)
	public void addCart(CartVO cartVO) {
		cartDAO.addCart(cartVO);
	}

	public List<CartVO> getList(UserVO userVO) {
		List<CartVO> list = cartDAO.getList(userVO);
		System.out.println(list);
		for(CartVO cartVO : list){
			System.out.println(cartVO);
		}
		return list;
	}
	
	public void deleteService(CartVO cartVO) {
		cartDAO.deleteCart(cartVO);	
	}
	
	public void updateService(CartVO cartVO) {
		cartDAO.updateCart(cartVO);
	}
	//장바구니 테스트중
	public List<CartVO> getSelectList(CartVO cartVO) {
		List<CartVO> list = cartDAO.getSelectList(cartVO);
		return list;
	}
	
	//cartMap수정용 추가
	public CartVO selectCartOne(int cId) {
		return cartDAO.selectCartOne(cId);		
	}
	

	public int checkCart(CartVO cartVO) {
		int result = cartDAO.checkCart(cartVO);
		return result;
	}
	
}
