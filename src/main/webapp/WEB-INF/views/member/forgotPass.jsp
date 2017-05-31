<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/search.css">
<script type="text/javascript">

	$('.message a').click(function(){
		   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
		});
	$(document).ready(function(){
  		$(".logc").click(function(){
  			if($("#id").val()==""){
				alert("아이디를 입력하세요");				
				return false; 
				
			 }
			if($("#password").val().trim()==""){
				alert("비밀번호를 입력하세요");				
				return false;
			}
	
  		});
  		
			 $("#log").click(function(){
				var result=confirm("로그인하시겠습니까?");
				if(result){
					return true;
				}else{
					return false;
					
				} 
			});
			 $(".num").keyup(function(){$(this).val( $(this).val().replace(/[^0-9]/g,"") );} );
  	});
</script>
<section>
<div class="login-page">
  <div class="form">
	<form action="${pageContext.request.contextPath }/login.do" method="post">
	<input id="id" name="id" required="required" type="text" placeholder="아이디" /><br> 
	<input type="password" name="password" id="password" required="required" placeholder="비밀번호"><br>
     <span id="idCheckView"></span>
      <button id="log" class="logc">login</button>
      <p class="message">Not registered? <a href="${pageContext.request.contextPath}/member/registers.do">Create an account</a></p>
    </form>
  </div>
</div>
</section>

