<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<!-- js -->
<script type="text/javascript" src="resources/js/user/insertUpdateUser.js"></script>
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
<!-- 도로명주소 찾기 api --> 
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<meta charset="UTF-8">


<title>회원정보 수정</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>

<div class="container">
	<div class="signup_title"><h3>회원정보 수정</h3></div>			
	<div class="col-md-4 signup_group">
		<form:form action="updateUser.do" method="post" id="modifyform" commandName="user" >
			<!-- 아이디 -->
			<div class="form-group">
				<label for="uId">아이디</label> 
				<div class="input-group">
					<input type="text" class="form-control" id="uId" name="uId" value="${user.uId}" readonly="readonly"> 
				</div>
			</div>
			<!-- 현재 비밀번호 -->
			<div class="form-group">
				<label for="uNPwd">현재 비밀번호</label> 
				<input type="password" 	class="form-control" id="uNPwd" name="uNPwd" onblur="checkUserPwd()">
				 <div class="check_font" id="pwd_check"></div>			
			</div>
			
			<!-- 변경할 비밀번호 -->
			<div class="form-group">
				<label for="uPwd">새 비밀번호</label> 
				<input type="password" 	class="form-control" id="uPwd" name="uPwd">
			</div>
						
			<!-- 이름  -->	
			<div class="form-group">
				<label for="uName">이름</label>
				 <input type="text" class="form-control" id="uName" name="uName" value="${user.uName}">
			</div>
			
			<!-- 이메일 -->
			<div class="form-group">
				<label for="uEmail">이메일</label>
				<input type="email" class="form-control" id="uEmail" name="uEmail" value="${user.uEmail}">
			</div>
			
			<!-- 연락처 -->
			<div class="form-group">
				<label for="uPhone">휴대폰 번호('-'없이 10-11자리 숫자만 입력해주세요)</label> 
				<input type="tel" class="form-control" id="uPhone" name="uPhone" value="${user.uPhone}">
			</div>
			
			<!-- 주소 -->
			<div class="form-group">
				<label for="uAddr1">주소</label><br>
				<div class="horizontal">
					<input type="text" class="form-control" style="width: 40%; display: inline;"
						   value="${user.uAddr1}" name="uAddr1" id="uAddr1" readonly="readonly">
					<button type="button" class="button button_post" onclick="execPostCode();">
						 <i class="fa fa-search"></i> 우편번호 찾기
					</button>
				</div>	
			</div>			
			<div class="form-group">
				<input  type="text" class="form-control" style="top: 5px;" value="${user.uAddr2}"
					name="uAddr2" id="uAddr2" readonly="readonly" />
			</div>
			<div class="form-group">
				<input type="text" class="form-control" value="${user.uAddr3}" name="uAddr3" id="uAddr3"  />
			</div>				
			<br>
			<div class="form-group text-center">
				<button type="button" class="button button" onclick="modifyCheck();" id="signup_btn">수정</button>
				<button type="button" class="button button" onclick="history.back(-1);" id="signup_btn">취소</button>
			</div>
		</form:form>
	</div>
</div>	
<br><br>

<%@ include file= "../include/footer.jspf" %>
</body>

</html>