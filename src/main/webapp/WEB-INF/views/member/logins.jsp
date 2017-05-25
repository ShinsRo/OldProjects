<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript">
	$('.message a').click(function(){
		   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
		});
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
<div class="login-page">
  <div class="form">
	<form action="${pageContext.request.contextPath }/login.do" method="post">
	<input id="id" name="id" required="required" type="text" placeholder="아이디" /><br> 
	<input type="text" name="password" id="password" required="required" placeholder="비밀번호"><br>
      <button>login</button>
      <p class="message">Not registered? <a href="${pageContext.request.contextPath}/member/registers.do">Create an account</a></p>
    </form>
  </div>
</div>

