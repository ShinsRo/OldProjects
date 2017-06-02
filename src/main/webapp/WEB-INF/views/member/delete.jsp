<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
</head>
<body>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" >
$(document).ready(function(){
	$("#DeleteMember").click(function(){
		var result=confirm("탈퇴하시겠습니까?");
		if(result){
			return true;
		}else{
			return false;
		}
		 if($("#pass1").val().trim()==""){
			alert("비밀번호를 입력하세요");				
		return false;
		}
	});//click
});//ready
function passwordChecking(){
	if("${sessionScope.mvo.password}"==$("#pass1").val()){
		return true;
	}else{
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
}

	
</script>
</head>
<body>
<div class="delete_page">
<div class="form">
<form  method="post"
action="${pageContext.request.contextPath}/deleteMember.do" onsubmit="return passwordChecking()">
<input type = "hidden" name = "id" value = "${mvo.id }">
<input id="pass1" type="password" name="password" placeholder="비밀번호"  maxlength="11"><br>
<button id="DeleteMember" >회원탈퇴</button>
</form>
</div>
</div>
</body>
</html>