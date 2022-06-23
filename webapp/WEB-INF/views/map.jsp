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
<!--카카오 맵 api  -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a526b4a8bd10e461cb093d3b9511aae3&libraries=services,clusterer,drawing"></script>
<meta charset="UTF-8">
<script>
    /* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
    function qnaList(page){
        location.href="${path}/team01/qna.do?curPage="+page;
    }
    
	var container = document.getElementById('map');
	var options = {
		center: new kakao.maps.LatLng(37.482808, 126.896106),
		level: 3
	};

	var map = new kakao.maps.Map(container, options);
	//마커 표시 위치
	var markerPosition  = new kakao.maps.LatLng(37.482808, 126.896106); 

	// 마커 생성
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커 지도 위에 표시
	marker.setMap(map);

</script>


<title>찾아오시는 길</title>
</head>
<body>
<%@ include file= "include/header.jspf" %>    

<div class="container QnAList">	
	<div class="QnA_title"><h3>찾아오시는 길</h3></div>
	<div id="map" class="map"></div>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(37.482808, 126.896106),
			level: 3
		};
	
		var map = new kakao.maps.Map(container, options);
		//마커 표시 위치
		var markerPosition  = new kakao.maps.LatLng(37.482808, 126.896106); 
	
		// 마커 생성
		var marker = new kakao.maps.Marker({
		    position: markerPosition
		});
	
		// 마커 지도 위에 표시
		marker.setMap(map);

	</script>
	<div>
		<table class="mapTable">
			<tbody>
				<tr>
					<th>주소</th>
					<td>서울특별시 구로구 디지털로 30길 31 코오롱디지털타워빌란트2차</td>
				</tr>
				<tr> 
					<th>전화번호</th>
					<td>TEL : 02-6925-4760</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	</div>

<%@ include file= "include/footer.jspf" %>  
</body>
</html>