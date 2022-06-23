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
<link rel="stylesheet" type="text/css" href="resources/css/cartList.css" />
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<meta charset="UTF-8">

<title>주문 | 장바구니</title>
</head>
<body onload=allcheck()>
<%@ include file= "include/header.jspf" %>

<div class="container bag_list">
	<div class="bag_title"><h2>장바구니</h2><br></div>	
	<section class="bag_result">		
		<c:choose>						
			<c:when test="${empty list}">
				<h5 class="text-center">장바구니가 비어있습니다.</h5>
			</c:when>
			
			<c:otherwise>
				<table class="table">
					<!-- ############# 테이블 상품속성 ############# -->
					<thead>
						<tr>
							<th>
					   			<input type="checkbox" name="allCheck" id="allCheck" />	   					
							</th>						
							<th>상품명</th>
							<th>상품 가격</th>
							<th>수량</th>
							<th>합계</th>
							<th>수량수정</th>
							<th>삭제</th>
						</tr>
					</thead>
					
					<!-- ############# 테이블 상품목록 ############# -->
					<c:forEach items="${list}" var="list">
						<tbody>						
							<td>
	 							<input type="checkbox" name="chBox" class="chBox" 
	 								   data-cId="${list.cId}" onClick="itemSum()" value="${list.gPrice * list.cAmount}"/>  							
							</td>																									
							<td><c:out value="${list.gName}"/></td>							
							<td><fmt:formatNumber pattern="###,###,###" value="${list.gPrice}"/>원</td> 						
							<td><c:out value="${list.cAmount}"/></td> 					
							<td><fmt:formatNumber pattern="###,###,###" value="${list.gPrice * list.cAmount}"/>원</td>							
							<td>
								<form:form method="post" action="./cartUpdate.do?cId=${list.cId}">								
									<input type="number" name="cAmount" id="cAmount" class="numBox" 
										   min="1" max="1000"value="${list.cAmount}"/>개									
									<button type="submit" class="bag_btn" id="update_Btn">수정</button>
								</form:form>
							</td>
							<td>								
								<button type="submit" class="bag_btn" id="delete_Btn" 
										onclick="location.href='cartDelete.do?cId=${list.cId}'">삭제</button>
							</td>
						</tbody>								   										
					</c:forEach>					
 				</table> 
 				
 				<!-- 총결제금액 -->		
				<div class="sum">
					<span>총 결제금액 &nbsp;&nbsp;&nbsp;</span>   
					<input type="text" name="sum" id="sum" value="0" size="3"/> 
					<span>원</span>
				</div>
				
				<!-- 결제버튼  -->							 				
  				<div class="selectOrder">
					<button type="button" class="selectOrder_btn">결제하기</button>	 					
  				</div>	
  										
			</c:otherwise>
		</c:choose>
	</section>
</div>	
	<!-- <button type="button" class="cartorder_btn">구매하기</button>
	<script>
		$(".cartorder_btn").click(
				
				function() {
					var sum = $("#sum").val();
					var data = {
							sum : sum
						};
					location.href = "CartgoOrderForm.do?sum="+sum;

				});
	</script> -->
	
<%@ include file= "include/footer.jspf" %>
</body>
<!-- js -->
<script type="text/javascript" src="resources/js/cartList.js"></script>
</html>

