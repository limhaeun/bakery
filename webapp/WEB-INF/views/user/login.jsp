<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/user/login.css"/>
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 기본폰트 영어-->
<link href="https://fonts.googleapis.com/css2?family=Libre+Baskerville&family=Noto+Sans+JP:wght@300&display=swap" rel="stylesheet">
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
<style>	
	
</style>
<title>로그인</title>
<script type="text/javascript" >
	window.onload = function(){
		var result = '${msg}';
		if(result=='false'){
		alert("아이디, 비밀번호 확인해주세요");
	}}
	
</script>

</head>
<body>
<%@ include file= "../include/header.jspf" %>

	<!-- ################ 로그인 ###############-->
	<div class="container login_group">	
		<div class="login_form">					
			<form action="loginPro.do?returnURL=${empty returnURL ? '/' : returnURL }" method="post">																																					
				<div class="login_formgroup">
					<!-- 타이틀 -->
					<h6>저희 홈페이지에 오신 것을 환영합니다.</h6>
					<h6>회원으로 더많은 혜택을 누려보세요!</h6>	
					<!-- 로그인 입력창 -->
					<input id="login_input" type="text" id="uId" name="uId" required="required" placeholder="  아이디"/>
					<br>
					<input id="login_input" type="password" id="uPwd" name="uPwd" required="required" placeholder="  비밀번호"/>																							
					<!-- 회원가입링크 -->					
					<div class="signup_div">
						<a class="signup_btn" href="#" onClick="location.href='insertUser.do'">
							회원가입  <i class='fas fa-angle-right'></i>
						</a>
					</div>
				</div>										
				<div class="login_btngroup" > 						
					<!-- 로그인 버튼  -->					
					<input id="login_btn" type="submit" value="로그인" />														
					<!-- 구글 로그인 버튼: 화면으로 이동시키는 URL(구글 로그인 화면에서 ID, PW를 올바르게 입력하면 oauth2callback 메소드 실행 요청) -->					
					<div>
						<a id="glogin_btn" href="${google_url}">
							<div id="glogin_div">
								<img src="${pageContext.request.contextPath}/resources/img/googleLogo.png" style="width:25px; height:25px;" />
								로그인
							</div>
						</a>
					</div>
				</div>							
			</form>	
		</div>
	</div>	
	
	<!-- ################ 회원혜택 ###############-->
	<div class="container membership">
		<h5>세븐베이커리 온라인회원 혜택!</h5>
		<div class="row benefits">
			<div >
				<i id="member_icon" class="fas fa-desktop"></i>
				<br><br>
				<p>다양한 정보와 <br>온라인 이벤트 참여</p>
			</div>
			<div>
				<i id="member_icon" class="far fa-envelope"></i>
				<br><br>
				<p>월별 신제품,<br>이벤트 정보 메일링</p>
			</div>
			<div>
				<i id="member_icon" class="fas fa-birthday-cake"></i>
				<br><br>
				<p>생일회원 케이크 <br>할인 혜택</p>
			</div>
		</div>	
	</div>		
	
<%@ include file= "../include/footer.jspf" %>
</body>
</html>