<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- css -->
<link href="resources/css/orders/orderStatusAdmin.css" rel="stylesheet" type="text/css" />
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 기본폰트 영어-->
<link href="https://fonts.googleapis.com/css2?family=Libre+Baskerville&family=Noto+Sans+JP:wght@300&display=swap" rel="stylesheet">
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<script>
	/* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
	function orderList(page){
		location.href="${path}/team01/orderStatus.do?curPage="+page;
	}
</script>
<title>관리자 | 주문현황</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>

<div class="container order_list">	
	<div class="order_title"><h3>주문현황 관리</h3></div>			
	<!-- ############## 주문목록 ############## -->	
	<table id="order_table">
		<thead>
			<tr>
				<th>주문 번호</th>
				<th>주문자</th>
				<th>수령인</th>
				<th>주소</th>
				<th>결제수단</th>
				<th>가격</th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list">
				<c:if test="${list == null}">
					<h2>주문없음</h2>
				</c:if>				
				<tr>
					<td><a href ="orderStatusDetail.do?oId=${list.oId}">${list.oId}</a></td>
					<td><c:out value="${list.ouId}"/></td>
					<td><c:out value="${list.oreceiveName}"/></td>
					<td><c:out value="(${list.oAddr1}) ${list.oAddr2} ${list.oAddr3}"/></td>
					<td><c:out value="${list.oPay}"/></td>
					<td><fmt:formatNumber pattern="###,###,###" value="${list.oTotal}" />원</td>									
				</tr>				
			</c:forEach>								
		</tbody>
	</table>
	<!--######### 페이징 화면 ###########  -->
	<div class="container pagenum">
		<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
		<c:if test="${paging.curBlock > 0}">
			<a id="paging_btn" href="javascript:orderList('1')">[처음]</a>
		</c:if>
		
		<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
		<c:if test="${paging.curBlock > 1}">
			<a id="paging_btn" href="javascript:orderList('${paging.prevPage}')">[이전]</a>
		</c:if>
		
		<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
		<c:forEach var="num" begin="${paging.blockBegin}" end="${paging.blockEnd}">			
			<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
			<c:choose>
				<c:when test="${num == paging.curPage}">
					<span style="color : #FE9A2E">${num}</span>&nbsp;
				</c:when>
				<c:otherwise>
					<a id="paging_btn" href="javascript:orderList('${num}')">${num}</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
		<c:if test="${paging.curBlock <= paging.totBlock}">
			<a id="paging_btn" href="javascript:orderList('${paging.nextPage}')">[다음]</a>
		</c:if>
		
		<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
		<c:if test="${paging.curPage <= paging.totPage}">
			<a id="paging_btn" href="javascript:orderList('${paging.totPage}')">[끝]</a>
		</c:if>		
	</div>
</div>	
<%@ include file= "../include/footer.jspf" %>
</body>
</html>