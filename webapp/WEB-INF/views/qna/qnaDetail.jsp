<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/qna/qnaDetail.css"/>
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
<meta charset="UTF-8">


<title>문의게시판 | 글내용</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>
<div class="container">
	<div class="QnApost_title"><h4>고객 문의글</h4></div>	
	<div class="QnApost_content"> 
		<table class="table QnApost_table" id="post_table">
			<thead>	
				<tr>
					<th scope="row">번호</th>
					<td>${qnaVO.qNum}</td>
												
					<th scope="row">작성자</th>
					<td><c:out value="${qnaVO.quId}"/></td>
					
					<th scope="row">작성일</th>
					<td><fmt:formatDate pattern="yyyy.MM.dd" value="${qnaVO.qDate}"/></td>																			
				</tr>
				<tr>
					<th scope="row" >제목</th>
					<td colspan="5">${qnaVO.qTitle}</td>
				</tr>				
				<tr>
					<c:if test="${qnaVO.qImg != null}">	
						<th class="align-middle" scope="row" >첨부파일</th>
						<td colspan="5">
							<img src="<spring:url value='/resources/qnafile/${qnaVO.qImg}'/>" id="img">
						</td>								
					</c:if>
				</tr>				
			</thead>
			<tbody>								
				<td  colspan="6">${qnaVO.qContent}</td>				
			</tbody>
		</table>
				
		
		
		<!-- ################## 댓글입력  ################## -->
		<c:if test="${ user.adminYN == '2'}">	
			<a class="replyOpen" href="#a">관리자 답글달기<i class='fas fa-angle-down'></i></a>&nbsp;&nbsp;															
			<!-- 댓글입력 폼 -->
			<form role="form" action="replyQna.do?qNum=${qnaVO.qNum}" method="post"
			  class="replyform" name="reply_form" autocomplete="off" >
				<div class="replyinput">
					<input type="hidden" name="rUId" id="rUId" value="${user.uId}"></i>
					<input type="hidden" name="rGCode" id="rGCode" value="${qnaVO.qNum}">								
					<!-- 댓글 입력창  -->
					<div class="input_area">
						<textarea name="rCont" class="reply_cont" id="rCont" placeholder="답변을 남겨주세요."></textarea>
						<div class="rBtn_area">
							<button type="submit" id="rBtn">댓글등록</button>
							<button type="button" id="rBtn" onclick="history.back(-1);">취소</button>
						</div>						
					</div>					
				</div>
			</form>	
		</c:if>			
	</div>
				
	<!-- ################## 댓글목록  ################## -->
	<div class="replyList">
		<c:forEach items="${QnaReplyVO}" var="QnaReplyVO">			
			<div class="adminReply">					
				<div class="dateTime">
					<i class="fas fa-arrow-right"></i>&nbsp; 관리자&nbsp;&nbsp;&nbsp;
					<span class="date"><fmt:formatDate value="${QnaReplyVO.qrRegdate}" pattern="yyyy-MM-dd" /></span> 
					<span class="time"><fmt:formatDate value="${QnaReplyVO.qrRegdate}" type="time" /></span>
				</div>	
												
				<div class="replyContent">${QnaReplyVO.qrContent}</div>
				<c:if test="${user.adminYN == '2'}">
					<input type="button" value="답글삭제" onclick="location.href='qnaReplyDelete.do?qrNum=${QnaReplyVO.qrNum}&qNum=${QnaReplyVO.qNum}'" />
				</c:if>								
			</div>		
		</c:forEach>
		
		
		<!-- 수정, 삭제버튼 -->             
        <p class="buttons">                     
            <c:if test="${ qnaVO.quId == user.uId }">
                <button id="postList_btn" onclick="location.href='qna.do'">목록</button>
                <button id="post_btn" onclick="location.href='qnaUpdate.do?qNum=${qnaVO.qNum}'">수정</button> 
                <button id="post_btn" class="post_delete" onclick="location.href='qnaDelete.do?qNum=${qnaVO.qNum}'">삭제 </button>                    
            </c:if>           
        </p>              		
	</div>																	
</div>

<%@ include file= "../include/footer.jspf" %>
</body>
<!-- js -->
<script type="text/javascript" src="resources/js/qna/qnaDetail.js"></script>	
</html>