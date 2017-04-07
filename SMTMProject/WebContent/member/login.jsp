<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>CodePen - Login </title>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	function loginFunc() {
		/* var df = document.loginForm
		if(df.id.val == ""){
			alert("아이디를 입력해주세요");
			return;
		} */
		document.loginForm.submit();
	}
	function registerFunc() {
		location.href = "register.jsp";
	}
</script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" media="screen" type="text/css" />
</head>
<body>
<!-- 
	로그인 View
	login.jsp -> LoginController
			id,password
-->
<jsp:include page="../layout/header.jsp"/>
<div class="wrap">
	<form action = "${pageContext.request.contextPath}/DispatcherServlet" name = "loginForm" method = "post">
	<input type = "hidden" value = "login" name = "command">
		<div class="avatar">
      <img src="${pageContext.request.contextPath}/img/logo.png">
		</div>
		<input type="text" placeholder = "아이디" name = "id" required="required">
		<div class="bar">
			<i></i>
		</div>
		<input type="password" placeholder="비밀번호" name = "password" required="required">
		<a href="" class="forgot_link">forgot ?</a>
		
		<button onclick="loginFunc()" style="margin-bottom:15px;">Login</button>
	</form>
	<button onclick="registerFunc()">Sign up</button>
	<!-- form 밖으로 signup뺌 -->
	</div>
</body>
</html>