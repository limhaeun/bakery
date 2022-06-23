package com.seven.team01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seven.team01.service.CartService;
import com.seven.team01.vo.CartVO;
import com.seven.team01.vo.UserVO;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	
	@ResponseBody
	@RequestMapping(value = "addCart.do", method = RequestMethod.POST)
	public int addCart(@ModelAttribute("cart") CartVO cartVO, HttpSession session,HttpServletRequest request) throws Exception {
		int result = 0; 
		UserVO user = (UserVO)session.getAttribute("user");
		/* 카트 중복체크, 로그인체크 */
		if(user != null) {
			cartVO.setuId(user.getuId());
			String gCode = request.getParameter("gCode");
			int gCode1 = Integer.parseInt(gCode); 
			cartVO.setgCode(gCode1);
			int checkResult = cartService.checkCart(cartVO); //카트에 상품 있는지 확인
			if(checkResult == 0) { //로그인후, 카트에 중복없을시
				cartService.addCart(cartVO);
				result = 1;
			}else if(checkResult >0) { //로그인후, 카트에 중복있을시
				result = 2;
			}
		}		
		return result; // 로그인X		
	}
	
	@RequestMapping("/cart.do")
	public String cartList(Model model ,CartVO cartVO, HttpSession session) {
		UserVO user = (UserVO)session.getAttribute("user");
		System.out.println(user);
		
		System.out.println("CartController");
		List<CartVO> list = cartService.getList(user);
		model.addAttribute("list", list);
		
		if(list.isEmpty()) {
			System.out.println("리스트에 아무것도 없어!!!");
		}
		return "cartList";
	}
	
	@RequestMapping(value = "/cartDelete.do")
	public String deleteCart(@Valid @ModelAttribute("del") CartVO cartVO) {
		cartService.deleteService(cartVO);
		return "redirect:/cart.do"; 
	}
	
	@RequestMapping(value = "/cartUpdate.do")
	public String updateCart(CartVO cartVO) {
		cartService.updateService(cartVO);
		return "redirect:/cart.do"; 
	}
	//장바구니테스트중
	@ResponseBody
	@RequestMapping(value="/cartSelectList.do",method=RequestMethod.POST)
	public void cartSelectList(@RequestParam(value="chbox[]") List<String> chArr,Model model ,CartVO cartVO, HttpSession session) {
	
		int cId=0;
		Map<Integer, List<CartVO>> cartMap = new HashMap<Integer, List<CartVO>>();
		for(String i:chArr) {
			cId = Integer.parseInt(i);
			cartVO.setcId(cId);
			List<CartVO> list3 = cartService.getSelectList(cartVO);
			
			cartMap.put(cId, list3);
			
			//model.addAttribute("list3", list3);
			System.out.println(list3);
		}
		model.addAttribute("cartMap", cartMap);

	}
	

}
