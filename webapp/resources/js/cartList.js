/* 모두선택 기능  */
function allcheck(){
	$("#allCheck").prop("checked",true);
	$(".chBox").prop("checked", true);
	itemSum();
}

/* 선택주문 기능 */	
$(".selectOrder_btn").click(function(){							
	
	var checkArr = new Array();							
	
	$("input[class='chBox']:checked").each(
		function(){
			checkArr.push($(this).attr("data-cId"));
		}
	);
	location.href = "CartgoOrderForm.do?checkArr="+checkArr;								
 });
 
/* 모두선택 기능 */	
$("#allCheck").click(function(){
	var chk = $("#allCheck").prop("checked");
	if(chk) {
		$(".chBox").prop("checked", true); 
	} 
	else {
		$(".chBox").prop("checked", false);
	}
	itemSum();
});

/* 선택주문 가격 합산기능 */	

//선택주문 시 전체선택 해제
$(".chBox").click(function(){	
	$("#allCheck").prop("checked", false);
});

//선택주문 상품가격 합산기능 
function itemSum() {
	var sum = 0;
	var count = $(".chBox").length;
	
	for (var i = 0; i < count; i++) {								
		if ($(".chBox")[i].checked == true) {
			sum += parseInt($(".chBox")[i].value);
		}
	}
	$("#sum").val(sum);
}