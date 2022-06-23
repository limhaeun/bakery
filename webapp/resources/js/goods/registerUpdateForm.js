/* 상품이미지 읽어오기 */
$("#gImg").change(function(){
	if(this.files && this.files[0]) {
		
		var reader = new FileReader;
		reader.onload = function(data) {
			$(".select_img").attr("src", data.target.result).width(500);        
		}
		reader.readAsDataURL(this.files[0]);		
	}
});


/* 상품가격 숫자유효성검사 */	
var regExp = /[^0-9]/gi;

$("#gPrice").keyup(function(){ numCheck($(this)); });

function numCheck(selector) {
	var tempVal = selector.val();
	selector.val(tempVal.replace(regExp, ""));
}


/*상품코드 중복체크*/
function gCodecheck(){
	var gCode = $("#gCode").val();
	if(gCode.length == 0){
		alert("상품코드를 입력해주세요");
		return;
	}
	$.ajax({
		url : 'checkgCode.do',
		type : 'post',
		data : {
			"gCode" : $("#gCode").val()
		},
		dataType : 'json',
		success : function(data) {
			if (data == 1) {
				$("#gCode_check").text("사용중인 상품번호입니다.");
				$("#gCode_check").css("color", "red");
			} else if (data == 0) {
				$("#gCode_check").text("사용가능한 상품번호입니다.");
				$("#gCode_check").css("color", "blue");
			}
		},
		error : function(data) {
			alert("에러가 발생했습니다.");
		},
		complete : function(data) {
		}
	}); // end ajax
}


/*키보드 입력시 중복버튼 초기화  */
function resetgCode(){
	 $('#gCodeExist').prop("disabled", false);
}

/*가입직전 최종 유효성검사 및 가입성공 메시지 팝업창 */
$("#signup_btn").on("click",function(){
	
	// 입력정보를  취합	
	var gCode = $("#gCode").val();
	var gName = $("#gName").val();
	var gPrice = $("#gPrice").val();
	
	// 유효성검사를 위한 정규식표현
	var gCodeRegex = /^[0-9]{2,10}$/; // 한글,영어(대소문자),숫자만 사용가능, 2-10자 이내
	var gNameRegex =/^[가-힣a-zA-Z0-9]{1,50}$/;
	var gPriceRegex = /^[0-9]{2,10}$/;
	/*var uAddr2Regex = /^[가-힣a-zA-Z0-9]{2,10}$/; 
	var uAddr3Regex = /^[가-힣a-zA-Z0-9]{2,10}$/;*/
	 
	var gCodeRegex = gCodeRegex.exec(gCode);
	if(gCodeRegex == null){
		   alert("상품코드를  다시 입력해주세요");
	
	   }
	
	var gNameRegex = gNameRegex.exec(gName);
	if(gNameRegex == null){
	   alert("상품명를 다시 입력해주세요");
	 }
	
	var gPriceRegex = gPriceRegex.exec(gPrice);
	if(gPriceRegex == null){
	 alert("가격을 다시 입력해주세요");
	 
	}
	if(gCodeRegex != null&&gNameRegex != null&&gPriceRegex != null)
	{
		$("#signform").submit();
		alert("상품이 등록되었습니다");		
	}	
})	
	