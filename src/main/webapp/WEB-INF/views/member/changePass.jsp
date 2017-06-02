<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
 <script type="text/javascript">
$(document).ready(function(){
	$(".changePass").click(function(){
		if($("#pass1").val().trim()==""){
			alert("비밀번호를 입력하세요");				
			return false;
		}
		if($("#pass2").val().trim()==""){
			alert("비밀번호확인을 입력하세요");				
			return false;
		}
		else if($("#pass1").val()!=$("#pass2").val()){
			alert("비밀번호 체크후 가입해주세요");
			return false;
		}

		else{
			var result=confirm("비밀번호를 변경하시겠습니까?");
			if(result){
				return true;	
		 	}
		else if(result==false){
			return false;
		}
		 else if($("#pass1").val()==$("#pass2").val()){
				return true;		
	}
	}
});
	$(function(){ $("#postcodify_search_button").postcodifyPopUp(); });
	$("#pass2").keyup(function(){
		if($("#pass1").val()!=$("#pass2").val()){
			$("#passCheckView").html("비밀번호 틀렸습니다").css(
					"color","red");
			checkResultId="";
			return;
		}else{
			$("#passCheckView").html("비밀번호 동일합니다").css(
					"color","blue");
		}
		
    	});//keyup  */ 
});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/search.css">
<section>
<div class = "container">
<div class = "row">
	<div class="forgotPass_page">
	<div class="form">
<form id="updateM" action="${pageContext.request.contextPath }/changePass.do" method="post">
		<input type="hidden" name="id" value="${mvo.id }">
		<input id="pass1" name="password"  type="password" placeholder="비밀번호" maxlength="11"/><br>
		<input id="pass2" type="password" name="pass2" placeholder="비밀번호확인" maxlength="11"><br>
		<span id="passCheckView"></span><br>
	<button id="changePass" class="changePass" > 비밀번호변경</button>
</form>
</div>
</div>
</div>
</div>
</section>