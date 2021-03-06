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
		 /////////////????????? ??? view?????? ????????? ?????? ?????? /////////////////
		 
		 ///////////////////////////////////////////////////////
		 int start = paging.getPageBegin();
		 int end = paging.getPageEnd();
		//goodList.jsp?????? ????????? ?????? ?????? ????????? !
		List<GoodsVO> list = goodService.pagingGoods(start, end);
		model.addAttribute("count",count);
		model.addAttribute("list", list);
		model.addAttribute("paging",paging);
		return "goods/goodList";
	}
	
	//index.jsp??? ????????? ?????? ????????? !!
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
		 
		//index.jsp?????? ????????? ?????? ?????? ????????? !
		 List<GoodsVO> list = goodService.pagingGoods(start, end);
		 model.addAttribute("count",count);
		
		model.addAttribute("list", list);
		model.addAttribute("paging",paging);
		return "index";
	}
	/////////???????????????????????????1104 13:53///////////
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
			System.out.println("gFile=" + gFile); // ?????????????????? ?????? ??????

			if (gFile != null && gFile.getSize() != 0) {
				String fileName = gFile.getOriginalFilename();// ?????? ?????????
				// ???????????? VO UploadPath??? ??????
				System.out.println(">>>>> ???????????? VO???  ??????");
				// upload????????? ???????????? ?????? ????????????
				String upath = uploadPath + "/" + "Uploadfile";
				File file = new File(upath + "/" + fileName); // upload?????? File ?????? ??????
				gFile.transferTo(file); // ????????? upload????????? ??????
				System.out.println(file + " upath" + "??? ??????");
				String showimgpath = "resources/Uploadfile/";
				goodsVO.setgImg(showimgpath + gFile.getOriginalFilename());
				System.out.println("????????????=" + gFile.getSize() + "?????????");
			} else {
				///////////////////////////////////////////////
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('????????? ???????????? ???????????????.');</script>");

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
			out.println("<script>alert('?????? ????????? ??????????????????. ?????? ?????????????????????.');</script>");

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
			List<ReplyVO> reply = goodService.replyList(gCode);//?????? list ??????
			model.addAttribute("reply",reply);

			Cookie[] cookies = request.getCookies();
			Cookie viewcookies = null;
			System.out.println("???????????? ??????");
			
			
			//????????? ???????????? 
			if(cookies!=null) {
				
				for(Cookie c:cookies) {
					if(c.getName().equals("cookie"+gCode)) {
						System.out.println("????????? ????????? ?????? ???");
						viewcookies = c;
						
					}
				
				}
			}
			
			if(good!=null){
					if(viewcookies ==null) {
						System.out.println("?????? ??????");
						Cookie newCookie = new Cookie("cookie"+gCode,String.valueOf(gCode));
						newCookie.setMaxAge(60);
						//newCookie.setMaxAge(60*60*24); //??????????????????
						response.addCookie(newCookie);
						goodService.UpGcount(gCode);
					}
					else {
						System.out.println("?????? ??????");
						String value = viewcookies.getValue();
						System.out.println(value);
					}
		
					
			}		
						
			return "goods/goodDetail";
		
	}

	  //???????????? ????????? ???!!
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
	  
	  
	  
		
	 //#############??????################################################

	
	  @RequestMapping(value="/replyAction.do",method=RequestMethod.POST) 
		public String insertReply(ReplyVO reply, HttpSession session) {
			UserVO user = (UserVO) session.getAttribute("user");// user???session??? ??????
			reply.setrUId(user.getuId());// reply??? userID??? ?????? System.out.println(reply);
			//????????? ??????
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
		
		
		//####################????????????#########################################
		//???????????? ?????? <????????????>
		@RequestMapping(value="/new.do")
		public ModelAndView cateListNew(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp?????? ????????? ?????? ?????? ????????? !
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
		
		//???????????? ?????? <???????????????>
		@RequestMapping(value="/hot.do")
		public ModelAndView cateHot(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp?????? ????????? ?????? ?????? ????????? !
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
		
		//???????????? ?????? <???????????????>
		@RequestMapping(value="/low.do")
		public ModelAndView cateLow(@RequestParam String gCateCode,@RequestParam(required = false) String gCate,Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp?????? ????????? ?????? ?????? ????????? !
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
		
		//???????????? ?????? <???????????????>
		@RequestMapping(value="/high.do")
		public ModelAndView cateHigh(@RequestParam String gCateCode,@RequestParam(required = false) String gCate, Model model, @RequestParam(defaultValue="1") int curPage) throws Exception {
			
			int count = goodService.countForPagingCate(gCateCode);
			 
			Pagination paging = new Pagination(count, curPage);
			 int start = paging.getPageBegin();
			 int end = paging.getPageEnd();
			 
			//goodCateList.jsp?????? ????????? ?????? ?????? ????????? !
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
