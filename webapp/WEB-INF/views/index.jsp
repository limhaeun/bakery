<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<!-- js -->
<script type="text/javascript" src="resources/js/index.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/index.css"/>
<!-- 로고폰트-->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@700&display=swap" rel="stylesheet">
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- 검색아이콘 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<script type="text/javascript">
function list(page){
	location.href="${path}/team01/?curPage="+page;
}
</script>
<meta charset="UTF-8">
<title>SEVEN BAKERY</title>
</head>
<body>       
<%@ include file= "include/header.jspf" %>      
		
	<!-- ################# 메인 상품목록  ################## -->
	<div class="container product_list">
		<div class="product_title">
			<H4>OUR FRESH PRODUCTS</H4>
			<p>당신의 아침과 함께할 매일 아침 굽는 신선한 빵을 만나보세요.</p>
		</div>
		
		<div class="product_content">
			<c:forEach items="${list}" var="GoodsVO">			
				<div class="product_item">
				    <a class="product_dtl" href="goodDetail?gCode=${GoodsVO.gCode}"><img src="${GoodsVO.gImg}"/></a>
					<div class="product_dtl">
						<span class="product_name"><c:out value="${GoodsVO.gName}"/></span>
						<br>
						<span class="product_price"><fmt:formatNumber pattern="###,###,###" value="${GoodsVO.gPrice}"/>원</span>
					</div>	
				</div>		
			</c:forEach>				
		</div>			
		<br>	
		<!-- ################# 상품 페이징  ################## -->
		<div class="container pagenum">
			<tr>
				<!-- 페이징 -->
				<td colspan="5"> 
					<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->	
					<c:if test="${paging.curBlock > 0}">
						<a id="paging_btn" href="javascript:list('1')">[처음]</a>
					</c:if>
					<!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->	
					<c:if test="${paging.curBlock > 1}">
						<a id="paging_btn" href="javascript:list('${paging.prevPage}')">[이전]</a>
					</c:if>
					
					<!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
					<c:forEach var="num" begin="${paging.blockBegin}" end="${paging.blockEnd}">
						<!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
						<c:choose>
							<c:when test="${num == paging.curPage}">
								<span style="color : #FE9A2E">${num}</span>&nbsp;
							</c:when>
							<c:otherwise>
								<a id="paging_btn" href="javascript:list('${num}')">${num}</a>&nbsp;
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
					<c:if test="${paging.curBlock <= paging.totBlock}">
						<a id="paging_btn" href="javascript:list('${paging.nextPage}')">[다음]</a>
					</c:if>
					
					<!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
					<c:if test="${paging.curPage <= paging.totPage}">
						<a id="paging_btn" href="javascript:list('${paging.totPage}')">[끝]</a>
					</c:if>
				</td>
		    </tr>  
		</div>
	</div>
<%@ include file= "include/footer.jspf" %>  
</body>
   			
</html>