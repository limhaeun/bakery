<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<meta charset="UTF-8">
<script>
	/* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
	function goodList(page){
		location.href="${path}/team01/goodList.do?curPage="+page;
	}
</script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/goods/goodList.css"/>
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 검색아이콘 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>


<title>관리자 | 상품리스트</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>
<div class="container productList">	
	<h3>상품 관리</h3>
	<span><button type ="submit" class="button regit_btn" id="cart_set" onclick="location.href='goodRegister.do'">상품등록</button></span>
	<div class="searchForm">
		<!-- ############## 검색기능 ############## -->			
		<form name="searchform" method="post" action="searchAdmin.do">				
			<input id="search_input" type="search" name="keyword" value="${keyword}" placeholder="상품명을 입력하세요">
			<button id="search_btn" class="search_btn" type="submit"><i class="fas fa-search search_icon"></i></button>
		</form>
	</div>
	<!-- ############## 전체 상품목록 ############## -->	
	<table id="productTable">
		<thead>
			<tr>
				<th>상품 코드</th>
				<th>상품 이름</th>
				<th>상품 가격</th>
				<th>상품 카테고리</th>
				<th>등록 날짜</th>
				<th>조회수</th>
				<th>이미지</th>
				<th>상품 설명</th>
				<th>수정 / 삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="GoodsVO">
				<tr>
					<td><a href = "goodDetail?gCode=${GoodsVO.gCode}"/><c:out value="${GoodsVO.gCode}"/></td>
					<td><c:out value="${GoodsVO.gName}"/></td>
					<td><c:out value="${GoodsVO.gPrice}"/></td>
					<td><c:out value="${GoodsVO.gCateCode}"/></td>
					<td><c:out value="${GoodsVO.gDate}"/></td>
					<td><c:out value="${GoodsVO.gCount}"/></td>
					<td><img src="${GoodsVO.gImg}"/></td>
					<td><c:out value="${GoodsVO.gDes}"/></td>
					<td>
						<button class="btn_admin" type ="submit" id="update_Btn" onclick="location.href='goodUpdateView.do?gCode=${GoodsVO.gCode}'">수정</button>			
						<button class="btn_admin" type ="submit" id="delete_Btn" onclick="location.href='delete.do?gCode=${GoodsVO.gCode}'">삭제</button>
					</td>
				</tr>				
			</c:forEach>								
		</tbody>
	</table>
	<!--######### 페이징 화면 ###########  -->
	<div class="container pagenum">
		<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
		<c:if test="${paging.curBlock > 0}">
			<a id="paging_btn" href="javascript:goodList('1')">[처음]</a>
		</c:if>
		
		<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
		<c:if test="${paging.curBlock > 1}">
			<a id="paging_btn" href="javascript:goodList('${paging.prevPage}')">[이전]</a>
		</c:if>
		
		<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
		<c:forEach var="num" begin="${paging.blockBegin}" end="${paging.blockEnd}">			
			<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
			<c:choose>
				<c:when test="${num == paging.curPage}">
					<span style="color : #FE9A2E">${num}</span>&nbsp;
				</c:when>
				<c:otherwise>
					<a id="paging_btn" href="javascript:goodList('${num}')">${num}</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
		<c:if test="${paging.curBlock <= paging.totBlock}">
			<a id="paging_btn" href="javascript:goodList('${paging.nextPage}')">[다음]</a>
		</c:if>
		
		<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
		<c:if test="${paging.curPage <= paging.totPage}">
			<a id="paging_btn" href="javascript:goodList('${paging.totPage}')">[끝]</a>
		</c:if>		
	</div>
</div>

<%@ include file= "../include/footer.jspf" %>
</body>
</html>