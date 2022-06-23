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
<link rel="stylesheet" type="text/css" href="resources/css/orders/orderView.css"/>
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<meta charset="UTF-8">


<title>마이페이지 | 주문현황상세</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>

<!-- ################### 주문 정보 #################### -->	
<div class="container orderinfo">
	<div class="orderUserinfo_title"><h4>주문정보 상세</h4></div>
	<c:choose>
		<c:when test="${list == null}">
			<h5>주문내역이 없습니다.</h5>
		</c:when>					
		<c:otherwise>
			<c:forEach items="${list}" var="list" varStatus="status">
				<c:if test="${status.first}">
					<table class="orderinfo_table">
						<tr>
							<th scope="row">주문 번호</th>
							<td>${list.oId}</td>
						</tr>
						<tr>
							<th scope="row">주문자</th>
							<td>${list.ouId}</td>
						</tr>
						<tr>
							<th scope="row">수령인</th>
							<td>${list.oreceiveName}</td>
						</tr>
						<tr>
							<th scope="row">주소</th>
							<td>(${list.oAddr1}) ${list.oAddr2} ${list.oAddr3}</td>
						</tr>
						<tr>
							<th scope="row">결제 방식</th>
							<td>${list.oPay}</td>
						</tr>
						<tr>
							<th scope="row">결제 가격</th>
							<td>
								<fmt:formatNumber pattern="###,###,###" value="${list.oTotal}" /> 
								원
							</td>
						</tr>
						<tr>
							<th scope="row">배송 상태</th>
							<td>${list.sState}&nbsp;&nbsp;	
								<c:if test="${list.sState=='배송전'}">
									<button type="button" class="button" onclick="location.href='deliver_cancelUser.do?oId=${list.oId}'">
										구매취소
									</button>
								</c:if>
								<c:if test="${list.sState=='배송중'}">
									<button type="button" class="button" onclick="location.href='deliver_finiUser.do?oId=${list.oId}'">
										구매확정
									</button>
								</c:if>								
							</td>						
						</tr>					
					</table>	
				</c:if>					
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>

<!-- ################### 주문상품 정보 #################### -->
<div class="container">
	<div class="orderUserinfo_title"><h4>주문상품 상세</h4></div>				
	<c:forEach items="${list}" var="list" varStatus="status">
		<table class="orderinfo_table">
			<tr>
				<th scope="row">상품명</th>
				<td>${list.dgName}</td>
			</tr>
			<tr>
				<th scope="row">상품 번호</th>
				<td>${list.dgCode}</td>
			</tr>
			<tr>
				<th scope="row">상품 가격</th>
				<td>
					<fmt:formatNumber pattern="###,###,###" value="${list.dgPrice}" /> 
					원
				</td>
			</tr>
			<tr>
				<th scope="row">구입 수량</th>
				<td>${list.dAmount} 개</td>
			</tr>
			<tr>
				<th scope="row">최종 가격</th>
				<td>
					<fmt:formatNumber pattern="###,###,###" value="${list.dgPrice * list.dAmount}" /> 
					원
				</td>
			</tr>					
		</table>										
	</c:forEach>
</div>
	
<%@ include file= "../include/footer.jspf" %>
</body>
</html>