<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 및 탈퇴</title>
<link href="${pageContext.request.contextPath}/webapp/resources/board/css/bootstrap.css" rel="stylesheet"> 
</head>
<body>
<h1>수정할 내용을 입력하세요</h1>
<form method="post" action="${pageContext.request.contextPath}/update.do">
아이디<input type="text" name="id" required="required" placeholder ="아이디" style="text-align: center" ><br>
이름<input type="text" name="name" required="required" placeholder="이름" style="text-align: center" ><br>
비밀번호<input type="password" name="name" required="required" placeholder="비밀번호" style="text-align: center" ><br>
비밀번호 확인<input type="password" name="password" required="required" placeholder="비밀번호 확인" style="text-align: center" ><br>
전화번호<input type="text" name="tel1" required="required" maxlength=3 size="1" placeholder="000" style="text-align: center" >-<input type="text" name="tel2" required="required" maxlength=4 size="1" placeholder="0000" style="text-align: center" >-<input type="text" name="tel3" required="required"maxlength=4 size="1" placeholder="0000" style="text-align: center" ><br>
주소<input type="text" name="address" required="required" placeholder="주소" style="text-align: center" ><br>
직업<input type="text" name="occupation" required="required" placeholder="직업" style="text-align: center" ><br style="line-height:30px;">
<input type="submit" value="회원 정보 수정">
<input type="submit" value="회원 탈퇴">
</form>
</body>
</html>

























