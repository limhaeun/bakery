package com.seven.team01.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seven.team01.service.EmailSenderService;
import com.seven.team01.service.OrdersService;
import com.seven.team01.vo.DetailOrderVO;
import com.seven.team01.vo.EmailVO;
import com.seven.team01.vo.OrderDetailStateVO;
import com.seven.team01.vo.OrdersVO;
import com.seven.team01.vo.Pagination;
import com.seven.team01.vo.StateOrderVO;
import com.seven.team01.vo.UserVO;

@Controller
public class OrderActionController {

	@Autowired
	OrdersService ordersService;
	
	@Autowired
	EmailSenderService emailSender;

	private int pAGE_SCALE = 5;
	private int bLOCK_SCALE = 5;
	// 주문진행
	@RequestMapping(value = "/orderPro.do", method = RequestMethod.POST)
	public String cartorder(HttpSession session, OrdersVO ordersVO, DetailOrderVO detailOrderVO) {
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getuId();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String tm = ymd + new DecimalFormat("00").format(cal.get(Calendar.HOUR_OF_DAY));
		String tmm = tm + new DecimalFormat("00").format(cal.get(Calendar.MINUTE));
		String subNum = "";

		for (int i = 1; i <= 6; i++) {
			subNum += (int) (Math.random() * 10);
		}

		String orderId = tmm + "_" + subNum;

		ordersVO.setoId(orderId);
		ordersVO.setOuId(userId);

		ordersService.insertOrders(ordersVO);
		List<DetailOrderVO> detailList = (List<DetailOrderVO>) session.getAttribute("detailList");
		Object cId = session.getAttribute("cId");
		if (cId == null) {
			detailOrderVO = detailList.get(0);
			detailOrderVO.setoId(orderId);
			System.out.println(detailOrderVO);
			ordersService.insertDetailOrders(detailOrderVO);
		} else {
			for (int i = 0; i < detailList.size(); i++) {
				detailOrderVO = detailList.get(i);
				detailOrderVO.setoId(orderId);
				ordersService.insertOrdersFromCart(detailOrderVO);
			}
		}
		return "redirect:orderUser.do";
	}

	
	// 관리자 주문현황 상세 페이지 가기
	@RequestMapping(value = "orderStatusDetail.do", method = RequestMethod.GET)
	public String orderStatusDetailForm(Model model, @RequestParam("oId") String oId) {
		System.out.println("oId : " + oId);
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		System.out.println("list : " + list);
		return "orders/orderView";
	}


	// 마이페이지 주문 목록 - 상태 변경 <배송완료>
	@RequestMapping(value = "deliver_finiUser.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String deliveryFiniUser(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId)
			throws Exception {
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		ordersService.deliveryFini(stateorderVO);
		return "redirect:orderUser.do";
	}

	// 관리자주문 상세 목록 - 상태 변경 <구매취소>
	@RequestMapping(value = "deliver_cancelAdmin.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderCancel(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId)
			throws Exception {
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		ordersService.orderCancel(stateorderVO);
		return "redirect:orderStatus.do";
	}

	// 관리자 주문 상세 목록 - 상태 변경 <배송중>
	@RequestMapping(value = "deliver_ing.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String delivery(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId) throws Exception {
		System.out.println("oId : " + oId);
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		System.out.println("list : " + list);
		ordersService.delivery(stateorderVO);
//			##################이메일 가져오기#####################
		String listEmail = ordersService.listEmail(oId);
		System.out.println("listEmail : " + listEmail);
//			##################################################
//			################이메일##################
		System.out.println("Email Controller");
		EmailVO emailVO = new EmailVO();
		emailVO.setReceiver(listEmail);
		emailVO.setContent("주문하신 상품의 배송이 출발합니다.");
		emailVO.setSubject("배송이 출발합니다.");
		emailSender.SendEmail(emailVO);
		// ######################################
		return "redirect:orderStatus.do";
	}

	// 관리자 주문 상세 목록 - 상태 변경 <배송완료>
	@RequestMapping(value = "deliver_fini.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String deliveryFini(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId)
			throws Exception {
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		ordersService.deliveryFini(stateorderVO);
//			##################이메일 가져오기#####################
		String listEmail = ordersService.listEmail(oId);
		System.out.println("listEmail : " + listEmail);
//			##################################################
//			################이메일##################
		System.out.println("Email Controller");
		EmailVO emailVO = new EmailVO();
		emailVO.setReceiver(listEmail);
		emailVO.setContent("주문하신 상품의 배송이 완료됐습니다.");
		emailVO.setSubject("배송이 완료됐습니다.");
		emailSender.SendEmail(emailVO);
		// ######################################
		return "redirect:orderStatus.do";
	}
	
	//관리자 주문현황 페이지 가기 + 11/05 17:37에 시작 //완성!!!!
		@RequestMapping(value = "orderStatus.do", method = RequestMethod.GET)
		public String orderStatusForm(Model model,HttpSession session, @RequestParam(defaultValue="1") int curPage) throws Exception {
			 int count = ordersService.countForPagingAdmin();
			 Pagination.setBLOCK_SCALE(bLOCK_SCALE);
			 Pagination.setPAGE_SCALE(pAGE_SCALE);
			 Pagination paging = new Pagination(count, curPage);
			 /////////////페이징 값 view마다 다르게 주기 도전 /////////////////
			
			 ///////////////////////////////////////////////////////
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			List<OrdersVO> list = ordersService.getOrderList(start, end);
			model.addAttribute("count",count);
			model.addAttribute("list", list);
			model.addAttribute("paging",paging);
			return "orders/orderStatus";		
		}	
		
		

}
