package com.seven.team01.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seven.team01.vo.GoodsVO;
import com.seven.team01.vo.ReplyVO;

@Repository
public class GoodsDAO {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<GoodsVO> getList(){
		return sqlSession.selectList("goodMapper.selectAllGoods");
	
	}
	

	public void insertGood(GoodsVO goodsVO) { 
		int cnt =sqlSession.insert("goodMapper.insertGood",goodsVO);
		System.out.println(cnt + "행이 삽입됐습니다."); 
	 }
	
	public void deleteGood(GoodsVO goodsVO) { 
		int cnt =sqlSession.delete("goodMapper.deleteGood",goodsVO);
		System.out.println(cnt + "행이 삭제됐습니다."); 
	 }


	public void updateGood(GoodsVO goodsVO) {
		// TODO Auto-generated method stub
		int cnt =sqlSession.update("goodMapper.updateGood",goodsVO);
		System.out.println(cnt + "행이 수정됐습니다."); 
	}


	public GoodsVO selectBygCode(int gCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gCode", gCode);// 상품코드 맵에 저장		

		return (GoodsVO) sqlSession.selectOne("goodMapper.selectBygCode", map); 
	}
	
	public void upGcount(int gCode) {
		sqlSession.update("goodMapper.upGcount",gCode);
	}
	
	//#######################index.jsp에서 보이는 상품 목록 페이징 dao!
		public List<GoodsVO> pagingGoods(int start, int end) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("end", end);
			return sqlSession.selectList("goodMapper.pagingGoods",map);
		}
	//##################################################
	
	//##########################################검색dao
		public List<GoodsVO> searchGoods2(
				int start, int end, String keyword){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("end", end);
			return sqlSession.selectList("goodMapper.searchGoods2",map);
		}
		
		public int countSearch(String keyword) throws Exception{		
			// Map()에 검색 옵션 & 키워드를 저장해서 SQL맵을 적용해 결과값 반환   
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", keyword);
			return sqlSession.selectOne("goodMapper.countSearch", map);
		}
		
		public List<GoodsVO> searchGoodsAdmin(int start, int end, String keyword, String gCateCode) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gCateCode", gCateCode);
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("end", end);
			return sqlSession.selectList("goodMapper.searchGoodsAdmin",map);
		}
		
		public int countSearchAdmin(String keyword, String gCateCode) throws Exception{		
			// Map()에 검색 옵션 & 키워드를 저장해서 SQL맵을 적용해 결과값 반환   
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", keyword);
			map.put("gCateCode", gCateCode);
			return sqlSession.selectOne("goodMapper.countSearchAdmin", map);
		}
		
		public int countPaging() throws Exception{		
			// Map()에 검색 옵션 & 키워드를 저장해서 SQL맵을 적용해 결과값 반환   
			Map<String, String> map = new HashMap<String, String>();
			return sqlSession.selectOne("goodMapper.countPaging", map);
		}
		//##################################################
		
		
		//##################댓글#################################
		public List<ReplyVO> replyList(int gCode){
			return sqlSession.selectList("goodMapper.selectReply",gCode);
		}
		
		public void deleteUserReply(int rNum) {
			int cnt =sqlSession.delete("goodMapper.deleteUserReply",rNum);
			System.out.println(cnt + "행이 삭제됐습니다."); 
		}
		
		public void deleteAdminReply(int rNum) {
			int cnt =sqlSession.delete("goodMapper.deleteAdminReply",rNum);
			System.out.println(cnt + "행이 삭제됐습니다."); 
		}
		
		public void updateReplyCnt(int gCode) {
			sqlSession.update("goodMapper.updateReplyCnt", gCode);
		}

		public void updateReplyCnt2(int gCode) {
			sqlSession.update("goodMapper.updateReplyCnt2", gCode);
		}
		
		public int getGCode(int rNum) {
			return sqlSession.selectOne("goodMapper.getGCode", rNum);
		}
		//########################################################
		
		//######################카테고리##################################

		
	public int countPagingCate(String gCateCode) throws Exception {
		// Map()에 검색 옵션 & 키워드를 저장해서 SQL맵을 적용해 결과값 반환
		Map<String, String> map = new HashMap<String, String>();
		map.put("gCateCode", gCateCode);
		return sqlSession.selectOne("goodMapper.countPagingCate", map);
	}

	// 카테고리 필터다
	public List<GoodsVO> cateNew(int start, int end, String gCateCode) {
		System.out.println("dao");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gCateCode", gCateCode);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("goodMapper.cateNew", map);
	}

	public List<GoodsVO> cateHot(int start, int end, String gCateCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gCateCode", gCateCode);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("goodMapper.cateHot", map);
	}

	public List<GoodsVO> cateLow(int start, int end, String gCateCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gCateCode", gCateCode);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("goodMapper.cateLow", map);
	}

	public List<GoodsVO> cateHigh(int start, int end, String gCateCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gCateCode", gCateCode);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("goodMapper.cateHigh", map);
	}

	/////////상품등록유효성검사1104 13:53///////////	
	public int checkgCode(String gCode) {
		int result =sqlSession.selectOne("goodMapper.checkgCode", gCode);
		System.out.println(result +"gCode 중복 체크");
		return result;
	}
	
	public List<GoodsVO> adminFilter(int start, int end, String gCateCode) {
		System.out.println("dao");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gCateCode", gCateCode);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("goodMapper.adminFilter", map);
	}
}
