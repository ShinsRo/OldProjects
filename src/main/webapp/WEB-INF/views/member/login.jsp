<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3>Login</h3>
<form id="loginForm" action="" method="post">
	<input type="text" name="id" placeholder="ID"><br>
	<input type="text" name="password" placeholder="Password"><br>
	<input type="submit" value="로그인">
</form>
<a href="${pageContext.request.contextPath}/member/register.do">register</a>