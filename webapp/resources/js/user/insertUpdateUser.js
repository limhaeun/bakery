/* 회원가입 */

//우편번호 찾기 버튼 클릭시 발생 이벤트
function execPostCode() { 
	new daum.Postcode({ 
		oncomplete: function(data) { 
		// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분. 
		/* console.log('첫로그' +data.roadAddress + '/'+data.bname+'/'+data.buildingName);  */
		// 도로명 주소의 노출 규칙에 따라 주소를 조합한다. 
		// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다. 
		var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
		var extraRoadAddr = ''; // 도로명 조합형 주소 변수 
		
		// 법정동명이 있을 경우 추가한다. (법정리는 제외) 
		// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다. 
		if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){ 
			extraRoadAddr += data.bname; 
			} 
		// 건물명이 있고, 공동주택일 경우 추가한다.
		if(data.buildingName !== '' && data.apartment === 'Y'){ 
			extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName); 
			} 
		// 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다. 
		if(extraRoadAddr !== ''){ 
			extraRoadAddr = ' (' + extraRoadAddr + ')'; 
			} 
		// 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다. 
		if(fullRoadAddr !== ''){
			fullRoadAddr += extraRoadAddr; 
		} 
		console.log(extraRoadAddr+'/'+fullRoadAddr)
		// 우편번호와 주소 정보를 해당 필드에 넣는다. 
	    console.log(data.zonecode); 
		console.log(fullRoadAddr);   
	
		document.getElementById('uAddr1').value = data.zonecode; //5자리 새우편번호 사용 
		document.getElementById('uAddr2').value = fullRoadAddr;   
		} 
	}).open(); 
	}
/*아이디 중복체크  */
function checkUserId(){
	var uId = $("#uId").val();
	if(uId.length == 0){
		alert("아이디를 입력해주세요");
		return;
	}
	$.ajax({
		url : 'checkUserId.do',
		type : 'post',
		data : {"uId" : uId},
		dataType : 'json',
		success : function(data){
			 if(data == 1){
					$("#id_check").text("사용중인 아이디입니다.");
					$("#id_check").css("color","red");
				}else if(data == 0){
					$("#id_check").text("사용가능한 아이디입니다.");
					$("#id_check").css("color","blue");
					$("#idChkDup").val('true');
					$('#userIdExist').prop("disabled", true); 
					$('#userIdExist').css("background-color","#F5D0A9");
				}
		       },
		       error:function(data){
		          alert("에러가 발생했습니다.");
		       },
		       complete:function(data){
		          //alert("작업을완료 했습니다");
		       } 
		    });  //end ajax	 
		 	
}
/*키보드 입력시 중복버튼 초기화  */
function resetUserId(){
	 $('#userIdExist').prop("disabled", false);
	 $('#userIdExist').css("background-color","#ff9d4a");
}
/*가입직전 최종 유효성검사 및 가입성공 메시지 팝업창 */
  $("#signup_btn").on("click",function(){
	
	// 입력정보를  취합	
	var idChkDup = $("#idChkDup").val();
	var uId = $("#uId").val();
	var uPwd = $("#uPwd").val();
	var uName = $("#uName").val();
	var uEmail = $("#uEmail").val();
	var uPhone = $("#uPhone").val();
	var uAddr3 = $("#uAddr3").val();
	/*var uAddr2 = $("#uAddr2").val(); 
	var uAddr3 = $("#uAddr3").val();*/
		
	
	// 유효성검사를 위한 정규식표현
	var uIdRegex = /^[가-힣a-zA-Z0-9]{2,10}$/; // 한글,영어(대소문자),숫자만 사용가능, 2-10자 이내
    var uPwdRegex =/^[가-힣a-zA-Z0-9]{2,10}$/;
    var uNameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;
    var uEmailRegex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
    var uPhoneRegex = /^01\d\d{3,4}\d{4}$/;
   	var uAddr3Regex = /^[가-힣a-zA-Z0-9]{2,10}$/;
    /*var uAddr2Regex = /^[가-힣a-zA-Z0-9]{2,10}$/; 
   	var uAddr3Regex = /^[가-힣a-zA-Z0-9]{2,10}$/;*/
   	 
   	if(idChkDup == 'false'){
      	 alert("아이디 중복체크를 해주세요");
      	}
   	var uIdRegex = uIdRegex.exec(uId);
   	if(uIdRegex == null){
   		   alert("아이디를  다시 입력해주세요");
   	   }
   	var uPwdRegex = uPwdRegex.exec(uPwd);
   	if(uPwdRegex == null){
   	   alert("비밀번호를 다시 입력해주세요");
   	 }
   	var uNameRegex = uNameRegex.exec(uName);
   	if(uNameRegex == null){
   	 alert("이름을 다시 입력해주세요");
   	 
   	}
   	var uEmailRegex = uEmailRegex.exec(uEmail);
   	if(uEmailRegex == null){
   	 alert("이메일을 다시 입력해주세요");
   	}
   	var uPhoneRegex = uPhoneRegex.exec(uPhone);
   	if(uPhoneRegex == null){
   	 alert("휴대폰번호를 다시 입력해주세요");
   	}
   	var uAddr3Regex = uAddr3Regex.exec(uAddr3);
   	if(uAddr3Regex == null){
   	 alert("주소를 다시 입력해주세요");
   	}
   	if(idChkDup!='false'&&uIdRegex != null&&uPwdRegex != null&&uNameRegex != null&&uEmailRegex != null&&uPhoneRegex != null&&uAddr3Regex != null)
   	{ 
   		$("#signform").submit();
   		alert('회원가입을 축하합니다');		 		
   	}
	
})	

/*회원정보 수정시 기존 비밀번호 확인  */
function checkUserPwd(){
	var uNPwd = $("#uNPwd").val();
	if(uNPwd.length == 0){
		alert("기존 비밀번호를 입력하세요");
		return;
	}
	$.ajax({
		url : 'checkUserPwd.do',
		type : 'post',
		data : {"uNPwd" :$("#uNPwd").val()},
		dataType : 'json',
		success : function(data){
			 if(data == 2){
					$("#pwd_check").text("비밀번호가 다릅니다.");
					$("#pwd_check").css("color","red");
				}else if(data == 1){
					$("#pwd_check").text("비밀번호 확인");
					$("#pwd_check").css("color","blue");
					$('#uNPwd').prop("disabled", true);
				}
		       },
		       error:function(data){
		          alert("에러가 발생했습니다.");
		       },
		       complete:function(data){
		       } 
		    });  	 
		 	
}
// 회원정보 수정 유효성 검사
function modifyCheck(){
	
	// 입력정보를  취합	
	var PwdCheck = $('#uNPwd').prop("disabled");
	var uNPwd = $("#uNPwd").val();
	var uPwd = $("#uPwd").val();
	var uName = $("#uName").val();
	var uEmail = $("#uEmail").val();
	var uPhone = $("#uPhone").val();
	var uAddr3 = $("#uAddr3").val();
	/*var uAddr2 = $("#uAddr2").val(); 
	var uAddr3 = $("#uAddr3").val();*/
		
	
	// 유효성검사를 위한 정규식표현
    var uPwdRegex =/^[가-힣a-zA-Z0-9]{2,10}$/;
    var uNameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;
    var uEmailRegex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
    var uPhoneRegex = /^01\d\d{3,4}\d{4}$/;
   	var uAddr3Regex = /^[가-힣a-zA-Z0-9]{2,10}$/;
    /*var uAddr2Regex = /^[가-힣a-zA-Z0-9]{2,10}$/; 
   	var uAddr3Regex = /^[가-힣a-zA-Z0-9]{2,10}$/;*/
   	 
   	if(PwdCheck == false){
   		alert("기존 비밀번호를 입력해주세요");
   	}
   	var uPwdRegex = uPwdRegex.exec(uPwd);
   	if(uPwdRegex == null){
   	   alert("새 비밀번호를 다시 입력해주세요");
   	 }
   	var uNameRegex = uNameRegex.exec(uName);
   	if(uNameRegex == null){
   	 alert("이름을 다시 입력해주세요");
   	 
   	}
   	var uEmailRegex = uEmailRegex.exec(uEmail);
   	if(uEmailRegex == null){
   	 alert("이메일을 다시 입력해주세요");
   	}
   	var uPhoneRegex = uPhoneRegex.exec(uPhone);
   	if(uPhoneRegex == null){
   	 alert("휴대폰번호를 다시 입력해주세요");
   	}
   	var uAddr3Regex = uAddr3Regex.exec(uAddr3);
   	if(uAddr3Regex == null){
   	 alert("주소를 다시 입력해주세요");
   	}
   	if(PwdCheck != false&&uNPwd != null &&uPwdRegex != null&&uNameRegex != null&&uEmailRegex != null&&uPhoneRegex != null&&uAddr3Regex != null)
   	{ 
   		$("#modifyform").submit();
   		alert('회원정보가 수정되었습니다');		 		
   	}
	
}