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
	
	//페이징 블록과 페이지 설정값
	private int pAGE_SCALE = 5;
	private int bLOCK_SCALE = 5;
	//지도 페이지 이동
	@RequestMapping(value="/map.do", method = RequestMethod.GET)
	public String Map() {
		return "map";
	}
	//회원가입 페이지 이동
	@RequestMapping(value="/insertUser.do", method = RequestMethod.GET)
	public String insertUser() {
		return "user/insertUser";
	}
	
	//회원가입
	@RequestMapping(value="/insertUser.do", method = RequestMethod.POST)
	public String insertUser(HttpServletRequest request, UserVO user) {
		System.out.println(" UserController의 insertUser");	
		userService.createUser(user);
		System.out.println(" sevice의 createUser메소드 호출");
		return "redirect:/"; 
	}  
	
	//아이디 중복확인
	@ResponseBody
	@RequestMapping(value="/checkUserId.do", method =RequestMethod.POST)
	public int checkUserId(HttpServletRequest request, Model model) {
		String uId = request.getParameter("uId");
		int result = userService.checkUserId(uId); 
		return result;  
	}
	
	// 로그인페이지 이동
	@RequestMapping(value = "/login.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest req, Model model) {
		model.addAttribute("returnURL", req.getParameter("returnURL"));

		/* 구글code 발행 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

		System.out.println("구글:" + url);
		model.addAttribute("google_url", url);

		/* 생성한 인증 URL을 View로 전달 */
		return "user/login";
	}	
	
	// 로그인 동작
	@RequestMapping(value = "/loginPro.do", method = RequestMethod.POST)
	public String login(HttpServletRequest req, @ModelAttribute("user") UserVO user, RedirectAttributes rttr) {
		HttpSession session = req.getSession();
		UserVO login = userService.login(user);
		session.setAttribute("user", login); // 뷰에서 사용 예정
		if (login == null) {
			System.out.println("로그인 실패");	
			rttr.addFlashAttribute("msg", false);
			return "redirect:/login.do";
		} else {
			System.out.println("로그인 성공");	
			String returnURL = req.getParameter("returnURL");
			return "redirect:"+returnURL;
		}
	}

	// 구글 로그인 Callback호출 메소드
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
			user.setAdminYN(3); // 구글회원
						
			//유저에 없을시 자동 가입
			if(userService.checkUserId(uId) == 0) {
				user.setuPwd(pwdEncoder.encode(uId));
				userService.createUser(user);
				System.out.println("구글가입");
			} //유저에 있다면 로그인정보 받아옴
			UserVO login = userService.selectlogin(user);
			session.setAttribute("user", login);
			} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/"; 
	}
	
	//로그아웃
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	////////////////////////마이페이지 주문 현황///////////////////////////////////////////////	
	//마이페이지 -> 해당 회원 주문현황 페이지 가기
	@RequestMapping(value = "/orderUser.do", method = RequestMethod.GET)
	public String orderUser(HttpSession session, Model model, @RequestParam(defaultValue = "1") int curPage) {
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		// 세션에있는 아이디
		String uId = sessionUser.getuId();
		int count = userService.countForPagingUser(uId);
		Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		Pagination.setPAGE_SCALE(pAGE_SCALE);
		Pagination paging = new Pagination(count, curPage);
		///////////// 페이징 값 view마다 다르게 주기 도전 /////////////////

		///////////////////////////////////////////////////////
		System.out.println("count는" + count);
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();
		// 세션에 있는 user를 가져와 sessionUser변수에 넣어줌

		System.out.println(uId);
		List<OrdersVO> list = ordersService.getOrderListUser(uId, start, end);
		model.addAttribute("count", count);
		model.addAttribute("uId", uId);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		System.out.println("리스트다 가져왔당");
		return "user/orderStatus";
	}

	//회원정보수정페이지 이동
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.GET)
	public String updateUser() {
		return "user/updateUser";
	}

	//회원정보 수정시 비밀번호 확인
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
	
	// 회원정보수정
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest req, @Valid @ModelAttribute("user") UserVO user) {
		userService.updateUser(user);
		// update후 login해서 바뀐 정보
		UserVO login = userService.login(user);
		req.getSession().setAttribute("user", login);
		return "redirect:/"; // 메인페이지
	}

	//회원탈퇴페이지 이동
	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.GET)
	public String deleteUser() {
		return "user/deleteUser";
	}

	// 회원탈퇴
	@RequestMapping(value = "/deleteUser.do", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user") UserVO user, HttpSession session, RedirectAttributes rttr) {
		System.out.println(" UserController의 deleteUser");
		// 세션에 있는 user를 가져와 sessionUser변수에 넣어줌
		UserVO sessionUser = (UserVO) session.getAttribute("user");
		if (sessionUser.getAdminYN() != 3) { // 일반회원,관리자일때
			// 세션에있는 비밀번호
			String sessionPwd = sessionUser.getuPwd();
			// userVO로 들어오는 비밀번호
			String userPwd = user.getuPwd();
			if (!(sessionPwd.equals(userPwd))) {
				rttr.addFlashAttribute("msg", false); // 화면에 메세지 보냄
				System.out.println("비밀번호 다름");
				return "redirect:/deleteUser.do";
			}
		}
		userService.deleteUser(user); // 공통 탈퇴
		session.invalidate();

		return "redirect:/"; // 메인페이지
	}

//마이페이지 -> 주문현황 상세 페이지 가기
	@RequestMapping(value = "orderUserStatusDetail.do", method = RequestMethod.GET)
	public String orderStatusDetailForm(Model model, @RequestParam("oId") String oId) {
		System.out.println("oId : " + oId);
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		System.out.println("list : " + list);
		return "orders/orderUserView";
	}
	
	// 마이페이지 주문 상세 목록 - 상태 변경 <구매취소>
	@RequestMapping(value = "deliver_cancelUser.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderCancel(Model model, StateOrderVO stateorderVO, @RequestParam("oId") String oId)
			throws Exception {
		List<OrderDetailStateVO> list = ordersService.getOrderDetailList(oId);
		model.addAttribute("list", list);
		ordersService.orderCancel(stateorderVO);
		return "redirect:orderUser.do";
	}
}