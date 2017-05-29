<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
	
<script src="//code.jquery.com/jquery.min.js"></script>
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
			if(checkResultId==""){
				alert("아이디 중복확인을 하세요");
				return false;
			}
  		});
  		
			$("#id").keyup(function(){
				var id=$(this).val().trim();
				if(id.length<4 || id.length>10){
					$("#idCheckView").html("아이디는 4자이상 10자 이하여야 함!").css(
							"color","green");
					checkResultId="";
					return;
				}
				
				$.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/idcheckAjax.do",				
					data:"id="+id,	
					success:function(data){						
						if(data=="fail"){
						$("#idCheckView").html(id+" 사용가능!").css("color","blue");
						checkResultId=id;	
						}else{						
							$("#idCheckView").html(id+" 사용불가!").css(
									"color","red");		
							checkResultId="";
						}					
					}//callback			
				});//ajax
			});//keyup
			$("#log").click(function(){
				var result=confirm("로그인하시겠습니까?");
				if(result){
					return true;
				}else{
					return false;
					
				}
			});
  	});
</script>
<div class="login-page">
  <div class="form">
	<form action="${pageContext.request.contextPath }/login.do" method="post">
	<input id="id" name="id" required="required" type="text" placeholder="아이디" /><br> 
	<input type="password" name="password" id="password" required="required" placeholder="비밀번호"><br>
     <span id="idCheckView"></span><br>
      <button id="log" class="logc">login</button>
      <p class="message">Not registered? <a href="${pageContext.request.contextPath}/member/registers.do">Create an account</a></p>
    </form>
  </div>
</div>

