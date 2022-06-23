package com.seven.team01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seven.team01.dao.GoodsDAO;
import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.ReplyVO;
@Service
public class GoodsService {
	
	@Autowired
	private GoodsDAO goodDao;

	public List<GoodsVO> getList() {
		List<GoodsVO> list = goodDao.getList();
		for(GoodsVO good : list){
			System.out.println(good);
		}
		return list;
	}
	
	public void insertService(GoodsVO goodsVO) {
		// TODO Auto-generated method stub
		goodDao.insertGood(goodsVO);	
	}

	
	public void deleteService(GoodsVO goodsVO) {
		// TODO Auto-generated method stub
		goodDao.deleteGood(goodsVO);	
	}

	@Transactional
	public void updateService(GoodsVO goodsVO) {
		// TODO Auto-generated method stub
		goodDao.updateGood(goodsVO);
	}
	
	//관리자모드 수정시 불러오기
	@Transactional 
	public GoodsVO selectAfterUpdate(int gCode) {
	
		return goodDao.selectBygCode(gCode);
	}
	
	//상품클릭시 상세보기 + 조회수증가
	public GoodsVO goodDetail(int gCode) {
		
		//goodDao.upGcount(gCode);
		return goodDao.selectBygCode(gCode);
	}
	
	//조회수 증가
	public void UpGcount(int gCode) {
		
		goodDao.upGcount(gCode);
		
	}
	
	//##########index.jsp에서 보이는 상품 목록 페이징 service !
	public List<GoodsVO> pagingGoods(int start, int end) {
		return goodDao.pagingGoods(start, end);
	}
	
	public int countForPaging() throws Exception {
			return goodDao.countPaging();
	}
	//###############################################
	
	//######################################검색 service
	public List<GoodsVO> searchGoods(int start, int end, String keyword) {
		return goodDao.searchGoods2(start, end, keyword);
	}
			
	public int countSearchItem(String keyword) throws Exception {
		return goodDao.countSearch(keyword);
	}
	
	// 관리자가 카테고리 별로 검색할 수 있게 하기 위해 새로 추가한
	public List<GoodsVO> searchGoodsAdmin(int start, int end, String keyword, String gCateCode) {
		return goodDao.searchGoodsAdmin(start, end, keyword, gCateCode);
	}

	// 관리자가 카테고리 별로 검색할 수 있게 하기 위해 새로 추가한
	public int countSearchItemAdmin(String keyword, String gCateCode) throws Exception {
		return goodDao.countSearchAdmin(keyword, gCateCode);
	}
	//###############################################	
	
	//###################댓글#############################

	public List<ReplyVO> replyList(int gCode) {
		List<ReplyVO> reply = goodDao.replyList(gCode);
		for(ReplyVO r: reply) {
			System.out.println(r);
		}
		return reply;
	}
	

	@Transactional
	public void deleteUserReply(int rNum) {
		int gCode = goodDao.getGCode(rNum);
		goodDao.deleteUserReply(rNum);
		goodDao.updateReplyCnt2(gCode);
	}
	
	public void deleteAdminReply(int rNum) {
		goodDao.deleteAdminReply(rNum);
		
	}
	//#######################################################
	
	//#############카테고리###########################################
	

		public int countForPagingCate(String gCateCode) throws Exception {
			return goodDao.countPagingCate(gCateCode);
		}

		//카테고리 필터
		public List<GoodsVO> cateNew(int start, int end, String gCateCode) {
			List<GoodsVO> list = goodDao.cateNew(start, end, gCateCode);
			return list;
		}

		public List<GoodsVO> cateHot(int start, int end, String gCateCode) {
			List<GoodsVO> list = goodDao.cateHot(start, end, gCateCode);		
			return list;
		}

		public List<GoodsVO> cateLow(int start, int end, String gCateCode) {
			List<GoodsVO> list = goodDao.cateLow(start, end, gCateCode);
			return list;
		}

		public List<GoodsVO> cateHigh(int start, int end, String gCateCode) {
			List<GoodsVO> list = goodDao.cateHigh(start, end, gCateCode);		
			return list;
		}

		/////////상품등록유효성검사1104 13:53///////////
		public int checkgCode(String gCode) {
			System.out.println("UserServiceImpl의 gCode 체크");
			return goodDao.checkgCode(gCode);
		}
		
		public List<GoodsVO> adminFilter(int start, int end, String gCateCode) {
			List<GoodsVO> list = goodDao.adminFilter(start, end, gCateCode);
			System.out.println("adminfilter 서비스!!!");
			return list;
		}
}
