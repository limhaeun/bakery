<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/qna/insertQna.css"/>
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- js -->
<script type="text/javascript" src="resources/js/qna/insertQna.js"></script>
<meta charset="UTF-8">


<title>문의게시판 | 글작성</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>

<div class="container QnAwrite">	
	<div class="QnAwrite_title"><h3>고객 문의게시판</h3></div>	
	
	<!-- 최초 화면 출력시 모델(요청영역에 저장)에서  board 이름의 커맨드 객체 검색 @ModelAttribute("board")-->
	<form:form method="post" action="QnaWrite.do" id="QnAform" enctype="multipart/form-data" commandName="qna">		
		<input type="hidden" name="quId" id="quId"  readonly="readonly" value="${user.uId}"/>		
		<div class="table_area">
			<table class="QnAwrite_table">
				<!-- value="${board.title}"의 역할은 error message가 출력될 때 값을 유지하는 것 -->
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" name="qTitle" id="qTitle" class ="rows"/></td>				
				</tr>
				<tr>
					<th scope="row">내용입력</th>
					<td><textarea rows="5" cols="60" name="qContent" id="qContent" class ="rows"></textarea></td>
				</tr>
				<tr>
					<th scope="row">첨부파일</th>
					<td><input type="file" name="qFile" id="qFile" class ="rows"></td>
				</tr>						
			</table>	
			<span id="buttons">
				<button type=button id="btn_write" onclick='checkQnA()'>등록</button>
				<button type="button" id="btn_write" onclick="history.back(-1);">취소</button>
			</span>	
		</div> 	
	</form:form>	
</div>

<%@ include file= "../include/footer.jspf" %>
</body>

</html>