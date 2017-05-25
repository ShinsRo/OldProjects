<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3>Register</h3>
<form id="registerForm" action="" method="post">
	<input type="text" name="id" placeholder="ID"><br>
	<input type="password" name="password" placeholder="Password"><br>
	<input type="password" name="password_confirm" placeholder="PasswordConfirm"><br>
	<input type="text" name="name" placeholder="Name"><br>
	<input type="text" name="address" placeholder="Address"><br>
	<input type="tel" name="tel" placeholder="Tel" pattern="[0-9]{10}[0-9]?" 
	title="'-'를 뺀 휴대전화 번호 10~11자리를 입력해주세요."><br>
	<select name="job">
	  <option value="학생">학생</option>
	  <option value="직장인">직장인</option>
	</select>
	<br>
	<input type="submit" value="회원가입">
</form>
