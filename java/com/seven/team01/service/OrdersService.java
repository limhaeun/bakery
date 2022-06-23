package com.seven.team01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seven.team01.dao.OrdersDAO;
import com.seven.team01.vo.DetailOrderVO;
import com.seven.team01.vo.OrderDetailStateVO;
import com.seven.team01.vo.OrdersVO;
import com.seven.team01.vo.StateOrderVO;

@Service
public class OrdersService {

	@Autowired
	OrdersDAO ordersDAO;

	public void insertOrders(OrdersVO ordersVO) {
		ordersDAO.insertOrders(ordersVO);
	}

	// 카트주문
	@Transactional 
	public void insertOrdersFromCart(DetailOrderVO detailOrderVO) {
		ordersDAO.insertDetailOrders(detailOrderVO);
		ordersDAO.insertOrdersState(detailOrderVO);
		ordersDAO.deleteOrderedCart(detailOrderVO); // 카트 삭제
	}

	// 개별주문
	@Transactional 
	public void insertDetailOrders(DetailOrderVO detailOrderVO) {
		ordersDAO.insertDetailOrders(detailOrderVO);
		ordersDAO.insertOrdersState(detailOrderVO);
	}

	public List<OrderDetailStateVO> getOrderDetailList(String oId) {
		List<OrderDetailStateVO> orderlist = ordersDAO.selectOrderDetailList(oId);
		return orderlist;
	}

	// 주문 상세 목록 - 상태 변경 <배송중>
	public void delivery(StateOrderVO stateorderVO) {
		ordersDAO.delivery(stateorderVO);
	}

	// 주문 상세 목록 - 상태 변경 <배송완료>
	public void deliveryFini(StateOrderVO stateorderVO) {
		ordersDAO.deliveryFini(stateorderVO);
	}

	// 마이페이지_주문 내역 확인
	public List<OrdersVO> getOrderListUser(String uId, int start, int end) {
		List<OrdersVO> list = ordersDAO.selectOrderUserList(uId, start, end);
		return list;
	}

	// ##주문 상세 목록 - 상태 변경 <주문취소>
	public void orderCancel(StateOrderVO stateorderVO) {
		ordersDAO.orderCancel(stateorderVO);
	}

	// ######이메일보내기용 <유저 이메일 주소>
	public String listEmail(String oId) {
		// TODO Auto-generated method stub
		String email = ordersDAO.listEmail(oId);
		return email;
	}
	
	// 관리자용 주문현황 페이징 짝꿍들##########################################
	public List<OrdersVO> getOrderList(int start, int end) {
		List<OrdersVO> list = ordersDAO.selectOrderList(start, end);
		return list;
	}

	public int countForPagingAdmin() {
		return ordersDAO.countPagingAdmin();
	}
	// ###############################################################
}
