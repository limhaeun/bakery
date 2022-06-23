/* 대댓글달기 텍스트 입력창 펼쳐짐 기능 */
$(".reReply_input").hide();
$(document).ready(function() {
	$(".replyOpen").click(function() {//replyOpen을 id에서 클래스로 바꿔줌
		$(this).parent().find(".reReplyForm").find(".reReply_input").toggle();
		//바로 못찾으면 부모로갔다가 찾으면 됨
	});

});

/* 로그인후 댓글달기 기능 */
$("#rBtn").click(function() {

	if ($('#rUId').val()=="") {
		var confirm_user = confirm("리뷰를 남기시려면 로그인을 해주세요.");
		if (confirm_user == true) {
			location.href = "login.do";
		}
	}
	else{
		reply_form.submit();
	}
	
});

/* 로그인 후 장바구니 넣기 가능 */
$(".addCart_btn").click(function() {
	var returnURL = window.location.href;
	var gCode = $("#gCode").val();
	var cAmount = $(".numBox").val();	

	var data = {
		gCode : gCode,
		cAmount : cAmount
	};

	$.ajax({
		url : "addCart.do",
		type : "post",
		data : data,
		success : function(result) {
			if (result == 1) {
				$(".numBox").val("1");
				var confirm_cart=confirm("장바구니에 담겼습니다. 장바구니로 가시겠습니까?");
				if(confirm_cart==true){
					location.href="cart.do"
				}
			}else if(result ==2){
				$(".numBox").val("1");
				var confirm_cart = confirm("장바구니에 이미 있는 상품입니다. \n장바구니로 이동하시겠습니까?");
				if (confirm_cart == true) {
					location.href = "cart.do"
				}
			}else {
				alert("회원만 사용할 수 있습니다.")
				$(".numBox").val("1");
				location.href="login.do?returnURL="+encodeURIComponent(returnURL);
			}
		},
		error : function() {
			alert("장바구니 담기 실패");
		}
	});
});
/* 구매하기기능 */
/*
 * $(".order_btn").click(function() {
 * 
 * var gCode = $("#gCode").val(); var cAmount = $(".numBox").val();
 * 
 * var data = { gCode : gCode, cAmount : cAmount };
 * location.href="goOrderForm.do?gCode="+gCode+"&cAmount="+cAmount; });
 */

/* 로그인 후 구매하기(폼에 수량 담기) 기능  */
$(".goOrderForm_btn").click(function() {
	var returnURL = window.location.href;
	if ($('#rUId').val()=="") {
		var confirm_user = confirm("구매하시려면 로그인을 해주세요.");
		if (confirm_user == true) {
			location.href = "login.do?returnURL="+encodeURIComponent(returnURL);
		}
		return false;
	}
	else{	
		var cAmount = $(".numBox").val();
		$("#dAmount").val(cAmount);
}
});


