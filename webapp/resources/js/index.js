/* 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해  */
function list(page){
	location.href="${path}/team01/?curPage="+page;
}
