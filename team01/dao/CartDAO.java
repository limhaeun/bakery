package com.seven.team01.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.CartVO;
import com.seven.team01.vo.UserVO;

@Repository
public class CartDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertCart(CartVO cartVO) {
		int count = sqlSessionTemplate.insert("cartMapper.insertCart", cartVO);
		return count;
	}
	
	//카트 추가(H 수정)
	public void addCart(CartVO cartVO) {
		sqlSessionTemplate.insert("cartMapper.insertCart", cartVO);
	}
	
	//카트 조회
	public List<CartVO> getList(UserVO userVO){
		return sqlSessionTemplate.selectList("cartMapper.selectAll",userVO);	
	}
	
	public void deleteCart(CartVO cartVO) { 
		int cnt =sqlSessionTemplate.delete("cartMapper.deleteCart",cartVO);
		System.out.println(cnt + "행이 삭제됐습니다."); 
	 }

	public void updateCart(CartVO cartVO) {
		int cnt =sqlSessionTemplate.update("cartMapper.updateCart",cartVO);
		System.out.println(cnt + "행이 수정됐습니다.");
	}
	
	//장바구니 테스트중
	public List<CartVO> getSelectList(CartVO cartVO){
		return sqlSessionTemplate.selectList("cartMapper.selectCartOrder",cartVO);	
	}
	
	//cartMap수정용 추가
	public CartVO selectCartOne(int cId){
		return sqlSessionTemplate.selectOne("cartMapper.selectCartOne",cId);	
	}

	public int checkCart(CartVO cartVO) {
		int result = sqlSessionTemplate.selectOne("cartMapper.checkCart",cartVO);
		return result;
	}
}
