package com.seven.team01.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seven.team01.service.OrdersService;
import com.seven.team01.service.UserService;
import com.seven.team01.vo.OrderDetailStateVO;
import com.seven.team01.vo.OrdersVO;
import com.seven.team01.vo.Pagination;
import com.seven.team01.vo.StateOrderVO;
import com.seven.team01.vo.UserVO;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrdersService ordersService;
	
	/* GoogleLogin */
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	//????????? ????????? ????????? ?????????
	private int pAGE_SCALE = 5;
	private int bLOCK_SCALE = 5;
	//?????? ????????? ??????
	@RequestMapping(value="/map.do", method = RequestMethod.GET)
	public String Map() {
		return "map";
	}
	//???????????? ????????? ??????
	@RequestMapping(value="/insertUser.do", method = RequestMethod.GET)
	public String insertUser() {
		return "user/insertUser";
	}
	
	//????????????
	@RequestMapping(value="/insertUser.do", method = RequestMethod.POST)
	public String insertUser(HttpServletRequest request, UserVO user) {
		System.out.println(" UserController??? insertUser");	
		userService.createUser(user);
		System.out.println(" sevice??? createUser????????? ??????");
		return "redirect:/"; 
	}  
	
	//????????? ????????????
	@ResponseBody
	@RequestMapping(value="/checkUserId.do", method =RequestMethod.POST)
	public int checkUserId(HttpServletRequest request, Model model) {
		String uId = request.getParameter("uId");
		int result = userService.checkUserId(uId); 
		return result;  
	}
	
	// ?????????????????? ??????
	@RequestMapping(value = "/login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest req, Model model) {
		model.addAttribute("returnURL", req.getParameter("returnURL"));

		/* ??????code ?????? */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

		System.out.println("??????:" + url);
		model.addAttribute("google_url", url);

		/* ????????? ?????? URL??? View??? ?????? */
		return "user/login";
	}	
	
	// ????????? ??????
	@RequestMapping(value = "/loginPro.do", method = RequestMethod.POST)
	public String login(HttpServletRequest req, @ModelAttribute("user") UserVO user, RedirectAttributes rttr) {
		HttpSession session = req.getSession();
		UserVO login = userService.login(user);
		session.setAttribute("user", login); // ????????? ?????? ??????
		if (login == null) {
			System.out.println("????????? ??????");	
			rttr.addFlashAttribute("msg", false);
			return "redirect:/login.do";
		} else {
			System.out.println("????????? ??????");	
			String returnURL = req.getParameter("returnURL");
			return "redirect:"+returnURL;
		}
	}

	// ?????? ????????? Callback?????? ?????????
	@RequestMapping(value = "/oauth2callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String googleCallback(@RequestParam String code, HttpSession session, UserVO user) {
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(),
				null);
		String accessToken = accessGrant.getAccessToken();
		Long expireTime = accessGrant.getExpireTime();
		if (expireTime != null && expireTime < System.currentTimeMillis()) {
			accessToken = accessGrant.getRefreshToken();
		}
		BufferedReader in = null;
		InputStream is = null;
		InputStreamReader isr = null;
		JSONParser jsonParser = new JSONParser();

		try {
			String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;

			URL gUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) gUrl.openConnection();

			is = conn.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			in = new BufferedReader(isr);

			JSONObject jsonObj = (JSONObject) jsonParser.parse(in);
			System.out.println(jsonObj);

			String uId = "_" + jsonObj.get("sub").toString();
			String name = jsonObj.get("name").toString();
			String uEmail = jsonObj.get("email").toString();

			user.setuId(uId);
			user.setuName(name.replace(" ", ""));  
			user.setuEmail(uEmail);
			user.setAdminYN(3); // ????????????
						
			//????????? ????????? ?????? ??????
			if(userService.checkUserId(uId) == 0) {
				user.setuPwd(pwdEncoder.encode(uId));
				userService.createUser(user);
				System.out.println("????????????");
			} //????????? ????????? ??????????????? ?????????
			UserVO login = userService.selectlogin(user);
			session.setAttribute("user", login);
			} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/"; 
	}
	
	//????????????
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	////////////////////////??????????????? ?????? ??????///////////////////////////////////////////////	
	//??????????????? -> ?????? ?????? ???????????? ????????? ??????
	@RequestMapping(value = "/orderUser.do", method = RequestMethod.GET)
	public String orderUser(HttpSession session, Model model, @RequestParam(defaultValue = "1") int curPage) {
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		// ??????????????? ?????????
		String uId = sessionUser.getuId();
		int count = userService.countForPagingUser(uId);
		Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		Pagination.setPAGE_SCALE(pAGE_SCALE);
		Pagination paging = new Pagination(count, curPage);
		///////////// ????????? ??? view?????? ????????? ?????? ?????? /////////////////

		///////////////////////////////////////////////////////
		System.out.println("count???" + count);
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();
		// ????????? ?????? user??? ????????? sessionUser????????? ?????????

		System.out.println(uId);
		List<OrdersVO> list = ordersService.getOrderListUser(uId, start, end);
		model.addAttribute("count", count);
		model.addAttribute("uId", uId);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		System.out.println("???????????? ????????????");
		return "user/orderStatus";
	}

	//??????????????????????????? ??????
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.GET)
	public String updateUser() {
		return "user/updateUser";
	}

	//???????????? ????????? ???????????? ??????
	@ResponseBody
	@RequestMapping(value = "/checkUserPwd.do", method = RequestMethod.POST)
	public int checkUserPwd(HttpServletRequest req, HttpSession session) {
		int result = 1;
		String uNPwd = req.getParameter("uNPwd");
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		String sessionPwd = sessionUser.getuPwd();
		if (!(sessionPwd.equals(uNPwd))) {
			result = 2;
		}
		return result;
	}
	
	// ??????????????????
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest req, @Valid @ModelAttribute("user") UserVO user) {
		userService.updateUser(user);
		// update??? login?????? ?????? ??????
		UserVO login = userService.login(user);
		req.getSession().setAttribute("user", login);
		return "redirect:/"; // ???????????????
	}

	//????????????????????? ??????
	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.GET)
	public String deleteUser() {
		return "user/deleteUser";
	}

	// ????????????
	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user") UserVO user, HttpSession session, RedirectAttributes rttr) {
		System.out.println(" UserController??? deleteUser");
		// ????????? ?????? user??? ????????? sessionUser????????? ?????????
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		if (sessionUser.getAdminYN() != 3) { // ????????????,???????????????
			// ??????????????? ????????????
			String sessionPwd = sessionUser.getuPwd();
			// userVO??? ???????????? ????????????
			String userPwd = user.getuPwd();
			if (!(sessionPwd.equals(userPwd))) {
				rttr.addFlashAttribute("msg", false); // ????????? ????????? ??????
				System.out.println("???????????? ??????");
				return "redirect:/deleteUser.do";
			}
		}
		userService.deleteUser(user); // ?????? ??????
		session.invalidate();

		return "redirect:/"; // ???????????????
	}

//??????????????? -> ???????????? ?????? ????????? ??????
	@RequestMapping(value = "orderUserStatusDetail.do", method = RequestMethod.GET)
	public String orderStatusDetailForm(Model model, @RequestParam("oId") String oId) {
		System.out.println("oId : " + oId);
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		System.out.println("list : " + list);
		return "orders/orderUserView";
	}
	
	// ??????????????? ?????? ?????? ?????? - ?????? ?????? <????????????>
	@RequestMapping(value = "deliver_cancelUser.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderCancel(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId)
			throws Exception {
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		ordersService.orderCancel(stateorderVO);
		return "redirect:orderUser.do";
	}
}