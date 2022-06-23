package com.seven.team01.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.seven.team01.service.GoodsService;
import com.seven.team01.service.ReplyService;
import com.seven.team01.vo.CartVO;
import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.Pagination;
import com.seven.team01.vo.ReplyVO;
import com.seven.team01.vo.UserVO;

@Controller
public class GoodsController {
	@Resource(name="uploadPath")
	private String uploadPath;
	private int pAGE_SCALE = 10;
	private int bLOCK_SCALE = 5;
	@Autowired
	GoodsService goodService;
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/main.do")
	public String main() {
		return "redirect:/";
	}

	@RequestMapping("/goodRegister.do")
	public String regGoods() {
		return "goods/registerForm";
	}

	@RequestMapping("/goodList.do")
	public String goodList(Model model,  @RequestParam(defaultValue="1") int curPage) throws Exception {
		System.out.println("GoodController");
		 int count = goodService.countForPaging();
		 Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		 Pagination.setPAGE_SCALE(pAGE_SCALE);
		 Pagination paging = new Pagination(count, curPage);
		 /////////////페이징 값 view마다 다르게 주기 도전 /////////////////
		 
		 ///////////////////////////////////////////////////////
		 int start = paging.getPageBegin();
		 int end = paging.getPageEnd();
		//goodList.jsp에서 보이는 상품 목록 페이징 !
		List<GoodsVO> list = goodService.pagingGoods(start, end);
		model.addAttribute("count",count);
		model.addAttribute("list", list);
		model.addAttribute("paging",paging);
		return "goods/goodList";
	}
	
	//index.jsp에 페이징 기능 추가함 !!
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String lookList(Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
		System.out.println("GoodController");
		
		 int count = goodService.countForPaging();
		 Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		 Pagination.setPAGE_SCALE(pAGE_SCALE);
		 Pagination paging = new Pagination(count, curPage);
		
		
		 
		 ///////////////////////////////////////////////////////
		 int start = paging.getPageBegin();
		 int end = paging.getPageEnd();
		 
		//index.jsp에서 보이는 상품 목록 페이징 !
		 List<GoodsVO> list = goodService.pagingGoods(start, end);
		 model.addAttribute("count",count);
		
		model.addAttribute("list", list);
		model.addAttribute("paging",paging);
		return "index";
	}
	/////////상품등록유효성검사1104 13:53///////////
	@ResponseBody
	@RequestMapping(value="/checkgCode.do", method =RequestMethod.POST)
	public int checkgCode(HttpServletRequest request, Model model) {
		String gCode = request.getParameter("gCode");
		int result = goodService.checkgCode(gCode); 
		return result;  
	}
	////////////////////////////////////////
   
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insert(@Valid @ModelAttribute("odate") GoodsVO goodsVO, MultipartFile gFile, Errors errors,
			Model model, HttpServletResponse response) throws IOException {

		try {
			gFile = goodsVO.getgFile();
			System.out.println("gFile=" + gFile); // 파일미선택도 객체 생성

			if (gFile != null && gFile.getSize() != 0) {
				String fileName = gFile.getOriginalFilename();// 업된 파일명
				// 파일명을 VO UploadPath에 설정
				System.out.println(">>>>> 파일명을 VO에  설정");
				// upload폴더의 물리적인 폴더 절대경로
				String upath = uploadPath + "/" + "Uploadfile";
				File file = new File(upath + "/" + fileName); // upload폴더 File 객체 생성
				gFile.transferTo(file); // 파일을 upload폴더로 복사
				System.out.println(file + " upath" + "에 저장");
				String showimgpath = "resources/Uploadfile/";
				goodsVO.setgImg(showimgpath + gFile.getOriginalFilename());
				System.out.println("파일크기=" + gFile.getSize() + "바이트");
			} else {
				///////////////////////////////////////////////
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('이미지 업로드는 필수입니다.');</script>");

				out.flush();
				//////////////////////////////////////////////
			}

			System.out.println(goodsVO.getgImg());
			goodService.insertService(goodsVO);
			return "redirect:/goodList.do";
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage().toString());
			///////////////////////////////////////////////
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('상품 등록에 실패했습니다. 다시 시도해주십시오.');</script>");

			out.flush();
			//////////////////////////////////////////////
			return "goods/registerForm";
		}

	}
	
	
	
	@RequestMapping(value = "/delete.do")
	public String deleteGood(@Valid @ModelAttribute("del") GoodsVO goodsVO) {
		goodService.deleteService(goodsVO);
		return "redirect:/goodList.do";
	}
	

	@RequestMapping(value = "/goodDetail", method = RequestMethod.GET)
	public String goodDetail( @RequestParam("gCode")int gCode,HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		
		   
			GoodsVO good = goodService.goodDetail(gCode);
			model.addAttribute("good", good);
			List<ReplyVO> reply = goodService.replyList(gCode);//댓글 list 보기
			model.addAttribute("reply",reply);

			Cookie[] cookies = request.getCookies();
			Cookie viewcookies = null;
			System.out.println("여기부터 쿠키");
			
			
			//조회수 중복방지 
			if(cookies!=null) {
				
				for(Cookie c:cookies) {
					if(c.getName().equals("cookie"+gCode)) {
						System.out.println("쿠키랑 이름이 같을 때");
						viewcookies = c;
						
					}
				
				}
			}
			
			if(good!=null){
					if(viewcookies ==null) {
						System.out.println("쿠키 없다");
						Cookie newCookie = new Cookie("cookie"+gCode,String.valueOf(gCode));
						newCookie.setMaxAge(60);
						//newCookie.setMaxAge(60*60*24); //하루동안보관
						response.addCookie(newCookie);
						goodService.UpGcount(gCode);
					}
					else {
						System.out.println("쿠키 있다");
						String value = viewcookies.getValue();
						System.out.println(value);
					}
		
					
			}		
						
			return "goods/goodDetail";
		
	}

	  //검색기능 추가한 것!!
	  @RequestMapping(value ="/search.do") 
	  public ModelAndView goodSearchList(@RequestParam(value="keyword") String keyword
			  ,@RequestParam(defaultValue="1") int curPage 
			  ) throws Exception { 
		  System.out.println("GoodController _ goodsSearchList");
		  int count = goodService.countSearchItem(keyword);
		  
		  
		  Pagination paging = new Pagination(count, curPage);
		  int start = paging.getPageBegin();
		  int end = paging.getPageEnd();
		  
		  List<GoodsVO> searchList = goodService.searchGoods(start, end, keyword);
		  
		  ModelAndView mav = new ModelAndView();	
		  Map<String, Object> map = new HashMap<String, Object>();
			
		  map.put("count", count);
		  map.put("keyword", keyword);
		  map.put("searchList", searchList);
		  map.put("paging", paging);

		  mav.addObject("map", map);
		  mav.setViewName("goods/goodSearchList"); 
			
		  return mav;
	  }
	  
	  
	  
		
	 //#############댓글################################################

	
	  @RequestMapping(value="/replyAction.do",method=RequestMethod.POST) 
		public String insertReply(ReplyVO reply, HttpSession session) {
			UserVO user = (UserVO) session.getAttribute("user");// user값session에 저장
			reply.setrUId(user.getuId());// reply에 userID값 저장 System.out.println(reply);
			//줄바꿈 처리
			String content = reply.getrCont().replace("\r\n", "<br>");
			reply.setrCont(content);
			replyService.insertReply(reply);
			return "redirect:/goodDetail?gCode=" + reply.getrGCode();
	  
	  }
	 
	  

		@RequestMapping(value = "/deleteUserReply.do", method = RequestMethod.GET) 
		public String deleteUserReply(@RequestParam int rNum, @RequestParam int gCode, Model model) {
			goodService.deleteUserReply(rNum); 
			return "redirect:/goodDetail?gCode=" + gCode; 
		}
		
	  
	  @RequestMapping(value = "/deleteAdminReply.do", method = RequestMethod.GET) 
		public String deleteAdminReply(@RequestParam int rNum, @RequestParam int gCode, Model model) {
			goodService.deleteAdminReply(rNum); 
			return "redirect:/goodDetail?gCode=" + gCode; 
		}
		
		
		//####################카테고리#########################################
		//카테고리 필터 <신상품순>
		@RequestMapping(value="/new.do")
		public ModelAndView cateListNew(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp에서 보이는 상품 목록 페이징 !
			List<GoodsVO> list = goodService.cateNew(start, end,gCateCode);
	

			ModelAndView mav = new ModelAndView();	
			Map<String, Object> map = new HashMap<String, Object>();
				
			map.put("count", count);
			map.put("list", list);
			map.put("gCateCode", gCateCode);
			map.put("gCate", gCate);
			map.put("paging", paging);

			mav.addObject("map", map);
			mav.setViewName("goods/goodCateList"); 
				
			
			return mav;
		}
		
		//카테고리 필터 <인기상품순>
		@RequestMapping(value="/hot.do")
		public ModelAndView cateHot(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp에서 보이는 상품 목록 페이징 !
			List<GoodsVO> list = goodService.cateHot(start, end,gCateCode);

			ModelAndView mav = new ModelAndView();	
			Map<String, Object> map = new HashMap<String, Object>();
				
			map.put("count", count);
			map.put("list", list);
			map.put("gCateCode", gCateCode);
			map.put("gCate", gCate);
			map.put("paging", paging);

			mav.addObject("map", map);
			mav.setViewName("goods/goodCateList"); 
			System.out.println(list);
			
			return mav;
		}
		
		//카테고리 필터 <낮은가격순>
		@RequestMapping(value="/low.do")
		public ModelAndView cateLow(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp에서 보이는 상품 목록 페이징 !
			List<GoodsVO> list = goodService.cateLow(start, end,gCateCode);

			ModelAndView mav = new ModelAndView();	
			Map<String, Object> map = new HashMap<String, Object>();
				
			map.put("count", count);
			map.put("list", list);
			map.put("gCateCode", gCateCode);
			map.put("gCate", gCate);
			map.put("paging", paging);

			mav.addObject("map", map);
			mav.setViewName("goods/goodCateList"); 
				
			
			return mav;
		}
		
		//카테고리 필터 <높은가격순>
		@RequestMapping(value="/high.do")
		public ModelAndView cateHigh(@RequestParam String gCateCode,@RequestParam(required = false) String gCate, Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp에서 보이는 상품 목록 페이징 !
			List<GoodsVO> list = goodService.cateHigh(start, end,gCateCode);

			ModelAndView mav = new ModelAndView();	
			Map<String, Object> map = new HashMap<String, Object>();
				
			map.put("count", count);
			map.put("list", list);
			map.put("gCateCode", gCateCode);
			map.put("gCate", gCate);
			map.put("paging", paging);

			mav.addObject("map", map);
			mav.setViewName("goods/goodCateList"); 
				
			return mav;
		}
		
}
