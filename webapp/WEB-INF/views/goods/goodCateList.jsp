<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<meta charset="UTF-8">
<!-- css -->
<link href="resources/css/goods/goodCateList.css" rel="stylesheet" type="text/css"/>
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 화살표아이콘 -->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script> 

<script>
	var g_code = '${map.gCateCode}';
	/* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
	function cateFilter(page1, cate, code){		
		location.href="${path}/team01/" + cate + ".do?curPage="+page1+"&gCateCode=" + code + "&gCate=" + cate;
	}		
</script>
<title>상품 | 브레드</title>
</head>
<style>
	
</style>
<body>
<%@ include file= "../include/header.jspf" %>
															
<div class="container cate_list">

	<!-- ############## 제품목록 타이틀  ############## -->		
	<div class="cate_title">
		<c:if test="${map.gCateCode eq '1'}">
			<H4 class="mx-3">BREAD</H4>
		</c:if>
		<c:if test="${map.gCateCode eq '2'}">
			<H4 class="mx-3">CAKE</H4>
		</c:if>
		<c:if test="${map.gCateCode eq '3'}">
			<H4 class="mx-3">SANDWICH</H4>
		</c:if>
		<c:if test="${map.gCateCode eq '4'}">
			<H4 class="mx-3">SALAD</H4>
		</c:if>
		<c:if test="${map.gCateCode eq '5'}">
			<H4 class="mx-3">BEVERAGE</H4>
		</c:if>
	</div>

	<!-- ############## 카테고리 필터 ############## -->	
	<div class="cate_filter">			   		  
		<a id="filter_item" href="javascript:cateFilter(1,'new', '${map.gCateCode}')">신상품순</a><span>&nbsp; | &nbsp;</span>
		<a id="filter_item" href="javascript:cateFilter(1,'hot', '${map.gCateCode}')">인기상품순</a><span>&nbsp; | &nbsp;</span>
		<a id="filter_item" href="javascript:cateFilter(1,'low', '${map.gCateCode}')">낮은 가격순</a><span>&nbsp; | &nbsp;</span>	    
		<a id="filter_item" href="javascript:cateFilter(1,'high', '${map.gCateCode}')">높은 가격순</a>		  
	</div>	
					
	<!-- ############### 상품목록 ################ -->							
	<div class="container"> 
		<c:forEach items="${map.list}" var="GoodsVO">
			<div class ="cate_item">
			    <a class="cate_dtl" href="goodDetail?gCode=${GoodsVO.gCode}"><img src="${GoodsVO.gImg}"/></a>
				<div class="cate_dtl">
					<span class="cate_name"><c:out value="${GoodsVO.gName}"/></span>
					<br>
					<span class="cate_price"><fmt:formatNumber pattern="###,###,###" value="${GoodsVO.gPrice}"/>원</span>
					<br>				
				</div>
			</div>	
		</c:forEach>
	</div>	
		
	<!--#################### 페이징 화면용 #####################  -->			
	<div class="container pagenum">			
		<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
		<c:if test="${map.paging.curBlock > 0}">
			<a id="paging_btn" href="javascript:cateFilter('1', '${map.gCate}', g_code, g_cateCode)">[처음]</a>
		</c:if>
		
		<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
		<c:if test="${map.paging.curBlock > 1}">
			<a id="paging_btn" href="javascript:cateFilter('${map.paging.prevPage}', '${map.gCate}', g_code, g_cateCode)">[이전]</a>
		</c:if>	
		
		<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
		<c:forEach var="num" begin="${map.paging.blockBegin}" end="${map.paging.blockEnd}">
			<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
			<c:choose>
				<c:when test="${num == map.paging.curPage}">
					<span style="color : #FE9A2E">${num}</span>&nbsp;
				</c:when>
				<c:otherwise>
					<a id="paging_btn" href="javascript:cateFilter('${num}', '${map.gCate}', g_code, g_cateCode)">${num}</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
			
		<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
		<c:if test="${map.paging.curBlock <= map.paging.totBlock}">
			<a id="paging_btn" href="javascript:cateFilter('${map.paging.nextPage}', '${map.gCate}', g_code, g_cateCode)">[다음]</a>
		</c:if>
		
		<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
		<c:if test="${map.paging.curPage <= map.paging.totPage}">
			<a id="paging_btn" href="javascript:cateFiltert('${map.paging.totPage}', '${map.gCate}', g_code, g_cateCode)">[끝]</a>
		</c:if>			 
	</div>		
</div>

	
<%@ include file= "../include/footer.jspf" %>
</body>
</html>