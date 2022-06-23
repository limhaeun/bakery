package com.seven.team01.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seven.team01.service.CartService;
import com.seven.team01.service.GoodsService;
import com.seven.team01.vo.CartVO;
import com.seven.team01.vo.DetailOrderVO;
import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.UserVO;

@Controller
public class OrderController {

	@Autowired
	private GoodsService goodService;
	@Autowired
	private CartService cartService;
	
	//상품상세에서 일반구매화면 가기
	@RequestMapping(value = "goOrderForm.do", method = RequestMethod.POST)
	public String goOrderForm(@ModelAttribute("detailVO") DetailOrderVO detailVO, HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("user");
		List<DetailOrderVO> detailList = new ArrayList<DetailOrderVO>();
		detailList.add(detailVO);
		System.out.println(detailVO);
		session.setAttribute("detailList", detailList);
		return "orders/orderForm";
	}	
	
	//장바구니에서 구매화면가기
	@RequestMapping(value = "CartgoOrderForm.do", method = RequestMethod.GET)
	public String CartgoOrderForm2(@RequestParam(value = "checkArr") List<String> chArr, Model model, CartVO cartVO,
			HttpSession session) {
		int cId = 0;
		List<DetailOrderVO> detailList = new ArrayList<DetailOrderVO>();
		for (String i : chArr) {
			cId = Integer.parseInt(i);
			DetailOrderVO detailVO = new DetailOrderVO();
			cartVO = cartService.selectCartOne(cId); // 체크된 cID에 맞는 cartVO select
			GoodsVO goodVO = goodService.goodDetail(cartVO.getgCode()); // code로 구매할 상품 상세
			detailVO.setDgCode(cartVO.getgCode()); // cart에 있던 gCode를 detailVO에 담음
			detailVO.setdAmount(cartVO.getcAmount()); // cart에 있던 cAmount를 detailVO에 담음
			detailVO.setDgName(goodVO.getgName());
			detailVO.setDgPrice(goodVO.getgPrice());
			detailVO.setDgImg(goodVO.getgImg());
			detailList.add(detailVO);
		}
		session.setAttribute("cId", cId);
		session.setAttribute("detailList", detailList);
		return "orders/orderForm";
	}
}
