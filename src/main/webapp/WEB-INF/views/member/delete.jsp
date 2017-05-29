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
<script type="text/javascript">
function passwordChecking(){
	if("${sessionScope.mvo.password}"==$("#pass1").val()){
		return true;
	}else{
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
}

	$(document).ready(function(){
		$("#DeleteMember").click(function(){
			var result=confirm("탈퇴하시겠습니까?");
			if(result){
				return true;
			}else{
				return false;
			}
		});//click
		$("#DeleteMember").click(function(){
			if("${sessionScope.mvo.password}"==$("#pass1").val()){
				return true;
			}else{
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
		}
		});
	});//ready
</script>
</head>
<body>

<div class="delete_page">
<div class="form">
<a href="${pageContext.request.contextPath}/home.do">
</a>

<form action="${pageContext.request.contextPath}/deleteMember.do" onsubmit="return passwordChecking()">
<input id="pass1" type="password" name="password" placeholder="비밀번호" required="required" ><br>
<button id="DeleteMember" >회원탈퇴</button>
</form>
</div>
</div>
</body>
</html>