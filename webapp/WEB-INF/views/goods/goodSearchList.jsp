<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<!-- js -->
<script type="text/javascript" src="resources/js/index.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/goods/goodSearchList.css"/>
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 화살표아이콘 -->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->   
<meta charset="UTF-8">
<script>
/* 페이징함수 */
function searchList(page){
	location.href="${path}/team01/search.do?curPage="+page+"&keyword=${map.keyword}";
}
</script>
</head>


<title>검색결과</title>
<body>
<%@ include file= "../include/header.jspf" %>
<div class="container search_list">
	<!-- ################# 검색된 상품목록  ################## -->
	<div class="search_title">
		<h2>검색 결과</h2><br>
		<p>총 ${map.count}개의 상품이 검색되었습니다.</p>	
	</div>		
	<div class="search_note">
		<H4>" ${map.keyword} " 에 대한 검색 결과 </H4>
	</div>
	<!-- 검색결과  -->	
	<div class="search_result">						
		<c:forEach items="${map.searchList}" var="GoodsVO">														
			<div class="search_item">
			    <a class="search_dtl" href="goodDetail?gCode=${GoodsVO.gCode}"><img src="${GoodsVO.gImg}"/></a>
				<div class="search_dtl">
					<span class="product_name"><c:out value="${GoodsVO.gName}"/></span>
					<br>
					<span class="product_price"><fmt:formatNumber pattern="###,###,###" value="${GoodsVO.gPrice}"/>원</span>
				</div>	
			</div>										
		</c:forEach>
	</div>		
		
	<!-- ################# 상품 페이징  ################## -->	
	<div class="container pagenum">	
		<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
		<c:if test="${map.paging.curBlock > 0}">
			<a id="paging_btn" href="javascript:searchList('1')">[처음]</a>
		</c:if>
		
		<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
		<c:if test="${map.paging.curBlock > 1}">
			<a id="paging_btn" href="javascript:searchList('${map.paging.prevPage}')">[이전]</a>
		</c:if>			
		
		<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
		<c:forEach var="num" begin="${map.paging.blockBegin}" end="${map.paging.blockEnd}">
			<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
			<c:choose>
				<c:when test="${num == map.paging.curPage}">
					<span style="color : #FE9A2E">${num}</span>&nbsp;
				</c:when>
				<c:otherwise>
					<a id="paging_btn" href="javascript:searchList('${num}')">${num}</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
					
		<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
		<c:if test="${map.paging.curBlock <= map.paging.totBlock}">
			<a id="paging_btn" href="javascript:searchList('${map.paging.nextPage}')">[다음]</a>
		</c:if>
		
		<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
		<c:if test="${map.paging.curPage <= map.paging.totPage}">
			<a id="paging_btn" href="javascript:searchList('${map.paging.totPage}')">[끝]</a>
		</c:if>
		<br><br>
		<a id="paging_btn" href="main.do" >전체 상품 보기  <i class='fas fa-angle-right'></i></a>	
	</div>
</div>	

<%@ include file= "../include/footer.jspf" %>
              
</body>
</html>