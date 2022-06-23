<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<!-- css -->
<link rel="stylesheet" type="text/css" href="resources/css/qna/qnaList.css"/>
<!-- 기본폰트 한글-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300;400&display=swap" rel="stylesheet">
<!-- CDN(jquery용) -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- CDNJS CDN --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <!-- 구글CDN -->
<!-- 부트스트랩4  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<script>
    /* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
    function qnaList(page){
        location.href="${path}/team01/qna.do?curPage="+page;
    }
</script>


<title>문의게시판</title>
</head>
<body>
<%@ include file= "../include/header.jspf" %>    

<div class="container QnAList">	
	<div class="QnA_title"><h3>고객 문의게시판</h3></div>
	<div class="QnA_content">
		<!-- 글 등록버튼 -->		
		<c:if test="${user.adminYN=='1' || user.adminYN=='3'}">
			<button class="button btn_qna" onclick="location.href='insertQna.do'">
				문의하기
			</button>
		</c:if>
		<!-- 문의게시판 -->
		<table class="table QnATable">
			<thead>
				<tr>
					<th>번호</th>					
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>답변상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="QnaVO">
					<tr>
						<td><c:out value="${QnaVO.qNum}"/></td>						
						<td>
							<c:if test ="${QnaVO.quId != user.uId && user.adminYN != '2'}" >
								<c:out value="비밀글입니다."/>
							</c:if>
							<c:if test ="${QnaVO.quId == user.uId || user.adminYN == '2'}" >
								<a id="post_title" href = "qnaDetail.do?qNum=${QnaVO.qNum}"/>
								<c:out value="${QnaVO.qTitle}"/>
							</c:if>
						</td>
						<td><c:out value="${QnaVO.quId}"/></td>
						<td>
							<fmt:formatDate pattern="yyyy.MM.dd" value="${QnaVO.qDate}"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${QnaVO.qState == 1}">미답변</c:when>
								<c:otherwise>답변 완료</c:otherwise>
							</c:choose>
						</td>			
					</tr>						
				</c:forEach>				
			</tbody>		
		</table>
		 <!--######### 페이징 화면 ###########  -->
        <div class="container pagenum">
            <!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 [처음] -->  
            <c:if test="${paging.curBlock > 0}">
                <a id="paging_btn" href="javascript:qnaList('1')">[처음]</a>
            </c:if>
            
            <!-- 이전페이지 블록으로 이동 : 현재 페이지 블록이 1보다 크면 [이전] -->   
            <c:if test="${paging.curBlock > 1}">
                <a id="paging_btn" href="javascript:qnaList('${paging.prevPage}')">[이전]</a>
            </c:if>
            
            <!-- 하나의 블럭에서 반복문 수행 시작 페이지부터 끝페이지 까지 -->
            <c:forEach var="num" begin="${paging.blockBegin}" end="${paging.blockEnd}">         
                <!-- 현재 페이지이면 하이퍼 링크 제거!!! -->
                <c:choose>
                    <c:when test="${num == paging.curPage}">
                        <span style="color : #FE9A2E">${num}</span>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a id="paging_btn" href="javascript:qnaList('${num}')">${num}</a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
            <!-- 다음 페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음] 하이퍼링크를 화면에 출력-->
            <c:if test="${paging.curBlock <= paging.totBlock}">
                <a id="paging_btn" href="javascript:qnaList('${paging.nextPage}')">[다음]</a>
            </c:if>
            
            <!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝] 하이퍼링크 -->
            <c:if test="${paging.curPage <= paging.totPage}">
                <a id="paging_btn" href="javascript:qnaList('${paging.totPage}')">[끝]</a>
            </c:if>       
        </div>
	</div>	
</div>

<%@ include file= "../include/footer.jspf" %>
</body>
</html>