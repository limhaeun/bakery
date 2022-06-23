/* 대댓글달기 텍스트 입력창 펼쳐짐 기능 */
$(".replyinput").hide();		
$(document).ready(function() {
	$(".replyOpen").click(function() {
		$(this).parent().find(".replyform").find(".replyinput").toggle();
		
		//바로 못찾으면 부모로갔다가 찾으면 됨
	});
});

/* 게시글 삭제시 알림 팝업창 */
$(".post_delete").click(function() {
	alert('글이 삭제되었습니다');				
});	






