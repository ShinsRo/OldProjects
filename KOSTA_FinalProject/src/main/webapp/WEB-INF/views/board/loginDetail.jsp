<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link href="${pageContext.request.contextPath}/webapp/resources/board/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<h1 align="center">아이디와 비밀번호를 입력하세요</h1>
아이디<input type="text" name="id" required="required" placeholder="아이디"><br>
비밀번호<input type="password" name="password" required="required"placeholder="비밀번호"><br style="line-height:30px;">
<input type="submit" value="로그인">
</body>
</html>