<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/goods/goodAdminSearchList.css"/>
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
	/* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
	var g_code = '${map.gCateCode}';
	function searchListAdmin(page){
		location.href="${path}/team01/searchAdmin.do?curPage="+page+"&keyword=${map.keyword}"+"&gCateCode=${map.gCateCode}"; 
	}
</script>
</head>


<title>관리자 | 검색결과</title>
<body>
<%@ include file= "../include/header.jspf" %>
<div class="container search_list">	
	<!-- 검색결과  -->		
	<div class="search_result">		
		<!-- ################# 검색된 상품목록  ################## -->
		<div class="search_title">
			<H4 id="search_text">" ${map.keyword} " 에 대한 검색 결과 </H4>&nbsp;&nbsp;&nbsp;
			<p id="search_text"><i class='fas fa-angle-right'></i>  총 ${map.count}개의 상품이 검색되었습니다.</p>				
		</div>																
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
					<th>상품설명</th>
					<th>수정 / 삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${map.searchList}" var="GoodsVO">
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
	</div>		
		
	<!--######### 페이징 화면 ###########  -->
	<div class="container pagenum">
		<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
		<c:if test="${map.paging.curBlock > 0}">
			<a href="javascript:searchListAdmin('1',g_code)">[처음]</a>
		</c:if>
		
		<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
		<c:if test="${map.paging.curBlock > 1}">
			<a href="javascript:searchListAdmin('${map.paging.prevPage}',g_code)">[이전]</a>
		</c:if>
		
		<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
		<c:forEach var="num" begin="${map.paging.blockBegin}" end="${map.paging.blockEnd}">			
			<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
			<c:choose>
				<c:when test="${num == map.paging.curPage}">
					<span style="color : red">${num}</span>&nbsp;
				</c:when>
				<c:otherwise>
					<a href="javascript:searchListAdmin('${num}',g_code)">${num}</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
		<c:if test="${map.paging.curBlock <= map.paging.totBlock}">
			<a href="javascript:searchListAdmin('${map.paging.nextPage}',g_code)">[다음]</a>
		</c:if>
		
		<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
		<c:if test="${map.paging.curPage <= map.paging.totPage}">
			<a href="javascript:searchListAdmin('${map.paging.totPage}',g_code)">[끝]</a>
		</c:if>		
		<br><br>
		<a href="#" onclick="location.href='goodList.do'"><i class='fas fa-angle-left'></i>  상품목록 돌아가기</a>
	</div>
</div>	
<br><br><br> 	
<%@ include file= "../include/footer.jspf" %>
              
</body>
</html>