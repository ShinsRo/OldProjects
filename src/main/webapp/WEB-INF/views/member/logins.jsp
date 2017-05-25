<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/member/logins.css" > 
    <a href="${pageContext.request.contextPath }/home.do">홈으로</a><hr>
<form action="${pageContext.request.contextPath }/login.do" method="post">
	 <input id="id" name="id" required="required" type="text" placeholder="아이디" /><br>
	 <input type="text" name="password" id="password" required="required" placeholder="비밀번호"><br>
			<input type="submit" value="로그인">			
</form>	
	
	