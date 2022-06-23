<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- js -->
<script type="text/javascript" src="resources/js/orders/orderForm.js"></script>
<!-- css -->
<link href="resources/css/orders/orderForm.css" rel="stylesheet" type="text/css" />
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
<!-- 도로명주소 찾기 api -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<meta charset="UTF-8">


<title>주문 | 결제하기</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>

<div class="container pay_list">	
	<div class="pay_title"><h2>주문서 작성</h2><br></div>		
	<section class="pay_result">
		<!-- ################ 주문목록 테이블 ############## -->
		<table class="table" id="pay_table">
			<thead>
				<tr>
					<th>상품 이미지</th>
					<th>상품 명</th>
					<th>상품 가격</th>
					<th>합계</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${detailList}" var="detailList">
					<tr> 
					    <td><img src="${detailList.dgImg}"/></td>
						<td><c:out value="${detailList.dgName}" /></td>
						<td>
							<c:out value="${detailList.dgPrice}" />원 X 
							<c:out value="${detailList.dAmount}" />개 =  
						</td>
						<td>
							<fmt:formatNumber pattern="###,###,###" value="${detailList.dAmount*detailList.dgPrice}" />원
						</td>
						<c:set var="sum" value="${sum + (detailList.dAmount*detailList.dgPrice)}" />
					</tr>
				</c:forEach>			
			</tbody>
		</table>
		
		<!-- 총결제금액 -->		
		<div class="sum">
			<span>총 결제금액 &nbsp;&nbsp;&nbsp;</span>  
			<input type="hidden" class="total" id="total" value="${sum}" >
			<fmt:formatNumber pattern="###,###,###" value="${sum}" />
			<span>원</span>
		</div>		
	</section>
	
	<div class="container">
		<div class="pay_title"><h2>배송 정보</h2><br></div>			
		<div class="col-md-4 del_group">
			<!-- ################ 배송정보 입력폼 ############## -->
			<form:form action="orderPro.do" method="post">			
				<!-- 주문자  -->
				<div class="form-group">
					<label for="uName">보내시는 분</label>
					<div class="input-group">
						<input type="text" class="form-control" value="${user.uName}"
							   name="oName" id="oName" readonly="readonly">
					</div>
				</div>
				<!-- 수신자  -->
				<div class="form-group">
					<label for="receiveName">받으시는 분</label> 
					<input type="text" class="form-control" value="${user.uName}" 
						   name="oreceiveName" id="oreceiveName">
				</div>
				
				<!-- 휴대폰 번호  -->
				<div class="form-group">
					<label for="uPhone">휴대폰 번호</label> 
					<input type="text" class="form-control" value="${user.uPhone}" 
						   name="oPhone" id="oPhone">
				</div>
					
				<!-- 주소 -->
				<div class="form-group">
					<label for="uAddr1">주소</label><br> 
					<div class="horizontal">
						<input type="text" class="form-control" value="${user.uAddr1}" 
							   name="oAddr1" id="oAddr1" >
						<button type="button" class="button button_post" onclick="execPostCode();">
							<i class="fa fa-search"></i> 우편번호 찾기
						</button>
					</div>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" value="${user.uAddr2}" 
						   name="oAddr2" id="oAddr2" />
				</div>
				<div class="form-group">
					<input type="text" class="form-control" value="${user.uAddr3}" 
						   name="oAddr3" id="oAddr3" />
				</div>
				
				<!-- 결제방식 -->				
         		<label for="uName">결제 방식</label>
				<div class="input-group">
					<input type="text" class="form-control" id="oPay" name="oPay" value="신용카드" readonly="readonly">
				</div>
				
				<!-- 최종 결제금액 -->			
				<p class="TotalPrice">
					<!-- <span>최종 결제 금액 : </span> -->
					<input type="hidden" class="form-control" value="${sum}" 
						   name="oTotal" id="oTotal" readonly="readonly">
					<%-- <fmt:formatNumber pattern="###,###,###" value="${sum}" />원 --%>
				</p>			

				<!-- 결제버튼  -->
				<div class="form-group text-center">
					<button type="submit" class="button button">결제</button>
				</div>				
			</form:form>
		</div>
	</div>
</div>	

<%@ include file= "../include/footer.jspf" %>
</body>
</html>