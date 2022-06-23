package com.seven.team01.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.DetailOrderVO;
import com.seven.team01.vo.OrderDetailStateVO;
import com.seven.team01.vo.OrdersVO;
import com.seven.team01.vo.StateOrderVO;

@Repository
public class OrdersDAO {

	@Autowired
	SqlSessionTemplate sqlSession;

	// 개별주문 및 카트주문 insert
	public void insertOrders(OrdersVO ordersVO) {
		sqlSession.insert("orderMapper.insertOrders", ordersVO);
	}

	// 개별주문 디테일 insert
	public void insertDetailOrders(DetailOrderVO detailOrderVO) {
		sqlSession.insert("orderMapper.insertDetailOrders", detailOrderVO);
	}

	// 카트주문 디테일 insert
	public void insertDetailFromCart(DetailOrderVO detailOrderVO) {
		sqlSession.insert("orderMapper.insertDetailFromCart", detailOrderVO);

	}

	// 개별주문 및 카트주문 배송상태 insert
	public void insertOrdersState(DetailOrderVO detailOrderVO) {
		int cnt = sqlSession.insert("orderMapper.insertOrdersState", detailOrderVO);
		System.out.println(cnt + "행 state 테이블에 추가");
	}

	// 주문 성공한 카트 삭제
	public void deleteOrderedCart(DetailOrderVO detailOrderVO) {
		sqlSession.delete("orderMapper.deleteOrderedCart", detailOrderVO);
	}

	public List<OrderDetailStateVO> selectOrderDetailList(String oId) {
		return sqlSession.selectList("orderMapper.orderDetailList", oId);
	}

	// 주문 상세 목록 - 상태 변경 <배송중>
	public void delivery(StateOrderVO stateorderVO) {
		sqlSession.update("orderMapper.delivery", stateorderVO);
	}

	// 주문 상세 목록 - 상태 변경 <배송완료>
	public void deliveryFini(StateOrderVO stateorderVO) {
		sqlSession.update("orderMapper.deliveryFini", stateorderVO);
	}

	// 마이페이지_개인 주문 현황
	public List<OrdersVO> selectOrderUserList(String uId, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uId", uId);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("orderMapper.orderListUser", map);
	}

	// ##주문 상세 목록 - 상태 변경 <주문취소>
	public void orderCancel(StateOrderVO stateorderVO) {
		sqlSession.update("orderMapper.orderCancel", stateorderVO);
	}

	// ######이메일보내기용 <유저 이메일 주소>
	public String listEmail(String oId) {
		String email = sqlSession.selectOne("orderMapper.listEmail", oId);
		return email;
	}
	//관리자가 보는 유저들의 주문 현황
	 //페이징용 짝꿍들###################################################
		public List<OrdersVO> selectOrderList(int start, int end) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			return sqlSession.selectList("orderMapper.orderList",map);
		}
		
		public int countPagingAdmin() {
			Map<String, String> map = new HashMap<String, String>();
			return sqlSession.selectOne("orderMapper.countPagingAdmin", map);
		}
		//페이징용################################################################

}
