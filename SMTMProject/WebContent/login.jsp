<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>CodePen - Login </title>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
      $("#login").click(function(){
         $.ajax({
            type:"post",
            url:"DispatcherServlet",
            data:"command=login&id="+$("#id").val()+"&password="+$("#password").val(),
         });// login ajax
      });// login click
      $("#registration").click(function(){
         $.ajax({
            type:"post",
            url:"DispatcherServlet",
            data:"command=register",
            success:function(data){
            }
         });// registr ajax
      });// registr click
   });//ready  
</script>
  <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />
</head>
<body>
<div class="wrap">
		<div class="avatar">
      <img src="img/logo.png">
		</div>
		<input type="text" placeholder="아이디" required>
		<div class="bar">
			<i></i>
		</div>
		<input type="password" placeholder="비밀번호" required>
		<a href="" class="forgot_link">forgot ?</a>
		<button style="margin-bottom:15px;">Sign up</button>
		<button>Login</button>
	</div>
</body>
</html>