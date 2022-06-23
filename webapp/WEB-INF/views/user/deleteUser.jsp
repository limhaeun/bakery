<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/user/insertUpdateDeleteUser.css"/>
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

<title>회원 탈퇴</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>
<article class="container">
	<div class="signup_title"><h3>회원탈퇴</h3></div>		
	<div class="col-md-4 signup_group">
		<form:form action="deleteUser.do" method="post" commandName="user">
			<!-- 아이디 -->
			<div class="form-group">
				<label for="uId">아이디</label> 
				<div class="input-group">
				<input type="text" class="form-control" id="uId" name="uId" value="${user.uId}" readonly="readonly"> 
			</div>
			<br>
			
			<c:if test="${user.adminYN eq '1'}">
			<!-- 비밀번호 -->
			<div class="form-group">
				<label for="uPwd">비밀번호</label> 
				<input type="password" 	class="form-control" id="uPwd" name="uPwd">
				<c:if test="${msg == false}">
					<p id="uPwd_alert">비밀번호를 확인해주세요</p>
				</c:if>
			</div>
			</c:if>
			
			<!-- 이름 -->
			<div class="form-group">
				<label for="uName">이름</label>
				 <input type="text" class="form-control" id="uName" name="uName" value="${user.uName}" readonly="readonly">
			</div>
			<br><br>
			<!-- 탈퇴/취소 버튼 -->
			<div class="form-group text-center">
				<button type="submit" class="button button" id="signup_btn">탈퇴</button>
				<button type="button" class="button button" id="signup_btn" onclick="history.back(-1);">취소</button>
			</div>
		</form:form>
	</div>
</article>
<%@ include file= "../include/footer.jspf" %>
</body>
</html>