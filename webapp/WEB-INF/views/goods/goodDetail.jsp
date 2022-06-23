<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- css -->
<link href="resources/css/goods/goodDetail.css" rel="stylesheet" type="text/css" />
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

<title>제품 상세보기</title>
</head>
<body>

<%@ include file= "../include/header.jspf" %>

<div class="content">
	<div class="content_main">
		<form:form action="addCart.do" method="post" commandName="cart">
			<input type="hidden" id="gCode" name="gCode" value="${good.gCode}" />		
			<input type="hidden" id="gCateCode" name="gCateCode" value="${good.gCateCode}" />
			
			<div class="row">			
				<!-- ############## 제품이미지 섹션 (1/2화면) ############## -->
				<div class="goods_img">		
					<!-- 카테고리 링크 -->
					<div class="mb-2 ">											
						<!-- ############## 제품목록 타이틀  ############## -->		
						<i class='fas fa-angle-right'></i> 
						<span class="cate_title">						  
							<c:if test="${good.gCateCode eq '1'}">
								<a id="cate_item" href="new.do?gCateCode=1">브레드</a>
							</c:if>
							<c:if test="${good.gCateCode eq '2'}">
								<a id="cate_item" href="new.do?gCateCode=2">케이크</a>
							</c:if>
							<c:if test="${good.gCateCode eq '3'}">
								<a id="cate_item" href="new.do?gCateCode=3">샌드위치</a>
							</c:if>
							<c:if test="${good.gCateCode eq '4'}">
								<a id="cate_item" href="new.do?gCateCode=4">샐러드</a>
							</c:if>
							<c:if test="${good.gCateCode eq '5'}">
								<a id="cate_item" href="new.do?gCateCode=5">음료</a>
							</c:if>
						</span>
					
						<span>
							&nbsp;<i class='fas fa-angle-right'></i>&nbsp;${good.gName}
						</span>
					</div>
					<!-- 이미지 -->		
					<img src="${good.gImg}" id="img">					
				</div>
			
				<!-- ############## 제품설명 섹션 (1/2화면) ############## -->	
				<div class="goods_detail">	
					<div class="gName">
						<h3>${good.gName}</h3>
						<div class="gDes">${good.gDes}</div>
					</div>
					
					<p class="gPrice">
						<span>가격:&nbsp;&nbsp;</span>
						<fmt:formatNumber pattern="###,###,###" value="${good.gPrice}" />원
					</p>
					
					<p class="cAmount">
						<span>구입 수량:&nbsp;&nbsp;</span>					
						<input type="number" class="numBox" id="numBox" min="1" max=999 value="1" />
					</p>
									
					<br>				
					<p class="addToCart">					
						<button type="submit" class="button goOrderForm_btn" form="goOrderForm">구매하기</button>
						<button type="button" class="button addCart_btn">장바구니</button>				  									 
					</p>													
				</div>			
			</div>
		</form:form>
		
		<span> 
			<form:form action="goOrderForm.do" method="POST" id = "goOrderForm"
					commandName="detailVO">
					<input type="hidden" name="dgCode" value="${good.gCode}" />
					<input type="hidden" name="dgImg" value="${good.gImg}" />
					<input type="hidden" name="dgName" value="${good.gName}" />
					<input type="hidden" name="dgPrice" value="${good.gPrice}" />
					<input type="hidden" id="dAmount" name="dAmount" value="1" />
					<!-- amount위에 바뀌면 바뀌게 value = "1"-->
			</form:form>
		</span>
		
		<div class="reply_content">
			<h3>REVIEW</h3>
			<p>${good.gName}에대한 리뷰을 남겨주세요.</p>
			<br><br>
			
			<!-- ################## 댓글입력  ################## -->
			<section class="reply_Form">
				<form role="form" action="replyAction.do" method="post"
					autocomplete="off" id="reply_form" name="reply_form">
					<div class="reply_input mb-3">
						<input type="hidden" name="rUId" id="rUId" value="${user.uId}">
						<input type="hidden" name="rGCode" id="rGCode"
							value="${good.gCode}">
						<!-- 댓글 썸네일  -->
						<img src="${good.gImg}" id="img_thumbnail">
						<!-- 댓글 제품명  -->
						<div class="reply_name">
							<span id="r_name" class="input-group-text">${good.gName}</span>
						</div>
						<!-- 댓글 입력창  -->
						<div class="input_area">
							<textarea name="rCont" class="reply_cont" id="rCont"
									  placeholder="여기에 리뷰를 남겨주세요."></textarea>
						</div>
						<!-- 댓글 등록버튼  -->
						<div class="input_area">
							<button type="button" id="rBtn">등록</button>
						</div>
					</div>
				</form>
			</section>

			<!-- ################## 댓글목록  ################## -->
			<section class="replyList">				
				<c:forEach items="${reply}" var="replyVO">
					<div class="replyEach">							
						<c:choose>		
							<c:when test="${replyVO.rDepth >0}">
								<div class="nonAdminReply">																		
									<!-- 댓글 등록자 -->
									<span class="userID"> 
										<i class="fas fa-arrow-right"></i>&nbsp;&nbsp;${replyVO.rUId}
									</span>
									<br>
									<!-- 등록일시 -->
									<div class="dateTime">
										<span class="date"><fmt:formatDate value="${replyVO.rDate}" pattern="yyyy-MM-dd" /></span> 
										<span class="time"><fmt:formatDate value="${replyVO.rDate}" type="time" /></span>
									</div>																			
									<!-- 댓글 내용 -->
									<div class="replyContent">${replyVO.rCont}</div>
								</div>
							</c:when>
							
							<c:otherwise>
								<span class="userID">${replyVO.rUId}</span><br>
								<div class="dateTime">
									<span class="date"><fmt:formatDate value="${replyVO.rDate}" pattern="yyyy-MM-dd" /></span> 
									<span class="time"><fmt:formatDate value="${replyVO.rDate}" type="time" /></span>
								</div>									
								<!-- 댓글 내용 -->
								<div class="replyContent">${replyVO.rCont}</div>							
								<!-- 대댓글입력 토글  -->
								<c:if test="${user.adminYN == '2' && replyVO.rDepth == 0 }">
									<a class="replyOpen" href="#a">답글달기<i class='fas fa-angle-down'></i></a>&nbsp;&nbsp;
								</c:if>						
							</c:otherwise>
						</c:choose>
						
						<!-- 댓글삭제 -->
						<span class="replyDelete">
							<c:if test="${user.uId == replyVO.rUId && user.adminYN eq '1'}">
								<a id="reply_btn" href="#" onClick="location.href='deleteUserReply.do?gCode=${good.gCode}&rNum=${replyVO.rNum}'">리뷰삭제
									<i class='fas fa-angle-right'></i>
								</a>
							</c:if>
							
							<c:if test="${user.uId == replyVO.rUId && user.adminYN eq '2'}">
								<a id="reply_btn" href="#a"
									onClick="location.href='deleteAdminReply.do?gCode=${good.gCode}&rNum=${replyVO.rNum}'">답글삭제
									<i class='fas fa-angle-right'></i>
								</a>
							</c:if>
						</span>
						
						<!-- ################## 대댓글 입력(re_reply.do) ################## -->						
						<div class="reReplyForm">
							<form role="form" method="post" action="re_reply.do">
								<div class="reReply_input"  data-repnum="${replyVO.rNum}">
									<input type="hidden" name="rGCode" id="rGCode" value="${good.gCode}"> 
									<input type="hidden" name="rPnum" id="rPnum" value="${replyVO.rNum}">
									<!-- 대댓글 창 -->						
									<div class="form-group">
										<textarea name="rCont" class="form-control rr_cont" id="rCont"></textarea>
									</div>
									<!-- 대댓글 버튼 -->								
									<div class="input_area">
										<button id="re_reply_btn">답글 등록</button>
									</div>
								</div>
							</form>							
						</div>						
				   </div>
				</c:forEach>					
			</section>			
		</div>			
	</div>
</div>

<%@ include file="../include/footer.jspf"%>
</body>
<!-- js -->
<script type="text/javascript" src="resources/js/goods/goodDetail.js"></script>

</html>