<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/goods/registerUpdateForm.css"/>
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
<!-- js -->
<script type="text/javascript" src="resources/js/goods/registerUpdateForm.js"></script>

<title>관리자 | 상품등록</title>
</head>
<body onload="initDate()">
<%@ include file= "../include/header.jspf" %>
	
	<div class="container">
		<div class="regit_title">
			<h3>상품 등록</h3>
		</div>
		<div class="col-md-4 regit_group p-0">
			<form:form method="post" action="./insert.do" commandName="odate" enctype="multipart/form-data">		
				<div class="form-group">
					<label for="gCode">상품 코드</label>
					<div class="horizontal">
						<input type="text" class="form-control" id="gCode" name="gCode" onkeypress="resetgCode()">
						<button type="button" class="button" id="gCodeExist" onclick='gCodecheck()'>중복확인</button>
					</div>
				</div>
				<div class="check_font" id="gCode_check"></div>

				<div class="form-group">
					 <label for="gName">상품 명</label>
					 <input type="text" class="form-control" id="gName" name="gName" />
				</div>
				
				<div class="form-group">
					 <label for="gPrice">상품 가격</label>
					 <input type="text" class="form-control" id="gPrice" name="gPrice" />
				</div>
				
				<div class="form-group">
					<label>상품 카테고리</label>
					<select class="form-control gCateCode" id="gCateCode" name="gCateCode">
						 <option value="">전체</option>
						 <option value="001">브레드</option>
						 <option value="002">케이크</option>
						 <option value="003">샌드위치</option>
						 <option value="004">샐러드</option>
						 <option value="005">음료</option>
					</select>
				</div>
					
				<div class="form-group">
					<label for="gDate">상품 등록일</label>
					<input 	type="Date" class="form-control" name="gDate" id="gDate"><br /> 
				</div>
						
				<div class="form-group">
					<label for="gImg">상품이미지</label>
					<input type="file" id="gImg" name="gFile"/>
					<div class="select_img"><img src="" /></div>			 	 		
					<%=request.getRealPath("/") %>
				</div>
				
				<div class="form-group">
					 <label for="gDes">상품소개</label>
					 <textarea rows="5" cols="50" class="form-control" id="gDes" name="gDes"></textarea>
				</div>
						
				<div class="form-group text-center">
					<input type="submit" id="regit_btn" value="등록"/>
					<button type="button" id="regit_btn" onclick="history.back(-1);">취소</button>
				</div>				
			</form:form>
		</div>
	</div>	
<%@ include file= "../include/footer.jspf" %>	
</body>

</html>