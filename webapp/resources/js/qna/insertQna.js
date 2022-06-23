/*QnA글쓰기 null값 조회*/
function checkQnA(){
 
	var qTitle = $("#qTitle").val();
	var qContent = $("#qContent").val();

	if(qTitle.length == 0){
		alert("제목을 입력해주세요");
	   }	
	if(qContent.length == 0){
	   alert("내용을 입력해주세요");
	 }
	
	if(qTitle.length != 0 &&qContent.length != 0)
	{
		$("#QnAform").submit();
		alert('글이 등록되었습니다');		
	}
	
}
	
function checkupdateQnA(){
	 
	var qTitle = $("#qTitle").val();
	var qContent = $("#qContent").val();

	if(qTitle.length == 0){
		alert("제목을 입력해주세요");
	   }	
	if(qContent.length == 0){
	   alert("내용을 입력해주세요");
	 }
	
	if(qTitle.length != 0 &&qContent.length != 0)
	{
		$("#QnAUpdateForm").submit();
		alert('글이 수정되었습니다');		
	}
	
}
											
