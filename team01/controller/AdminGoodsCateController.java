package com.seven.team01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.seven.team01.service.GoodsService;
import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.Pagination;

@Controller
public class AdminGoodsCateController {

	@Autowired
	GoodsService goodService;

	private int pAGE_SCALE = 10;
	private int bLOCK_SCALE = 5;

	// ####################카테고리#########################################
	@RequestMapping(value = "/adminFilter.do")
	public ModelAndView AdminFilter(@RequestParam String gCateCode, Model model,
			@RequestParam(defaultValue = "1") int curPage) throws Exception {

		int count = goodService.countForPagingCate(gCateCode);
		Pagination paging = new Pagination(count, curPage);
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();
		// goodCateList.jsp에서 보이는 상품 목록 페이징 !
		List<GoodsVO> list = goodService.adminFilter(start, end, gCateCode);
		ModelAndView mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);
		map.put("list", list);
		map.put("gCateCode", gCateCode);
		map.put("paging", paging);
		mav.addObject("map", map);
		mav.setViewName("goods/goodListAdmin");
		return mav;
	}

	// ################### 관리자용 검색기능 ###################
	@RequestMapping(value = "/searchAdmin.do")
	public ModelAndView goodAdminSearchList(@RequestParam(required = false) String gCateCode,
			@RequestParam(value = "keyword") String keyword, @RequestParam(defaultValue = "1") int curPage)
			throws Exception {

		System.out.println("관리자_전체_검색");
		System.out.println(keyword);
		int count = goodService.countSearchItemAdmin(keyword, gCateCode);
		Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		Pagination.setPAGE_SCALE(pAGE_SCALE);
		Pagination paging = new Pagination(count, curPage);
		///////////// 페이징 값 view마다 다르게 주기 /////////////////

		///////////////////////////////////////////////////////
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();

		List<GoodsVO> searchList = goodService.searchGoodsAdmin(start, end, keyword, gCateCode);

		ModelAndView mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);
		map.put("keyword", keyword);
		map.put("searchList", searchList);
		map.put("gCateCode", gCateCode);
		map.put("paging", paging);
		mav.addObject("map", map);
		mav.setViewName("goods/goodAdminSearchList");

		return mav;
	}

	// ################### 관리자용 검색기능 ###################
	@RequestMapping(value = "/searchAdminCate.do")
	public ModelAndView goodAdminSearchListCate(@RequestParam String gCateCode,
			@RequestParam(value = "keyword") String keyword, @RequestParam(defaultValue = "1") int curPage)
			throws Exception {

		System.out.println("관리자_카테고리_검색 ");

		int count = goodService.countSearchItemAdmin(keyword, gCateCode);
		Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		Pagination.setPAGE_SCALE(pAGE_SCALE);
		Pagination paging = new Pagination(count, curPage);
		///////////// 페이징 값 view마다 다르게 주기 /////////////////

		///////////////////////////////////////////////////////
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();

		List<GoodsVO> searchList = goodService.searchGoodsAdmin(start, end, keyword, gCateCode);

		ModelAndView mav = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);
		map.put("keyword", keyword);
		map.put("gCateCode", gCateCode);
		map.put("searchList", searchList);
		map.put("paging", paging);
		mav.addObject("map", map);
		mav.setViewName("goods/goodAdminSearchList");

		return mav;
	}
}
