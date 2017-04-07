<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>CodePen - Login </title>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	/* function loginFunc() {
		document.loginForm.submit();
	} */
	function registerFunc() {
		location.href = "${pageContext.request.contextPath}/member/register.jsp";
	}
</script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" media="screen" type="text/css" />
</head>
<body>
<div class="wrap">
	<form action = "${pageContext.request.contextPath}/DispatcherServlet?command=login" name = "loginForm" method = "post">
		<div class="avatar">
      <img src="${pageContext.request.contextPath}/img/logo.png">
		</div>
		<input type="text" placeholder = "아이디" name = "id" required>
		<div class="bar">
			<i></i>
		</div>
		<input type="password" placeholder="비밀번호" name = "password" required>
		<a href="" class="forgot_link">forgot ?</a>
		<button style="margin-bottom:15px;" onclick="registerFunc()">Sign up</button>
		<!-- <button onclick="loginFunc()">Login</button> -->
		 <input id = "loginBtn" type="submit" value="Login">
	</form>
	</div>
</body>
</html>