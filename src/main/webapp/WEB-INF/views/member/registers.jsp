<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/member/login_register.css" > 
    	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- <script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script> -->
	<script type="text/javascript">
	$(document).ready(function(){
		var checkResultId="";		
		$("#regForm").submit(function(){			
			if($("#id").val()==""){
				alert("아이디를 입력하세요");				
				return false;
			}
			if($("#password").val().trim()==""){
				alert("패스워드를 입력하세요");				
				return false;
			}
			if($("#name").val().trim()==""){
				alert("이름을 입력하세요");				
				return false;
			}
			if($("#address").val().trim()==""){
				alert("주소를 입력하세요");				
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
						"background","pink");
				checkResultId="";
				return;
			}
			
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/idcheckAjax.do",				
				data:"id="+id,	
				success:function(data){						
					if(data=="fail"){
					$("#idCheckView").html(id+" 사용불가!").css("background","red");
						checkResultId="";
					}else{						
						$("#idCheckView").html(id+" 사용가능!").css(
								"background","yellow");		
						checkResultId=id;
					}					
				}//callback			
			});//ajax
		});//keyup
		$(function() { $("#postcodify_search_button").postcodifyPopUp(); }); 
	});//ready
</script>
				<div class="login-page">
	 	 <div class="form">
				<form action="${pageContext.request.contextPath }/register.do"
							autocomplete="on" method="post" id="regForm">
					<input id="id" required="required" name="id"  type="text" placeholder="아이디" />
					<span id="idCheckView"></span><br>
					<input id="password" name="password"  required="required"
							type="password" placeholder="비밀번호" /><br>
					<input id="name" 	required="required"	name="name"  type="text" placeholder="이름" /><br>
					<input type="text" name="" class="postcodify_postcode5" value=""  placeholder="우편번호" /><br>
					<button id="postcodify_search_button">우편번호 검색</button><br>
					<input type="text" name="addr" class="postcodify_address" value="" readonly="readonly" placeholder="주소" /><br />
					<input type="text" name="addr_detail" class="postcodify_details" value="" placeholder="상세주소" /><br>
					<input id="tel" required="required" name="tel"  type="tel" placeholder="전화번호" /><br>
					<input id="job" required="required"	name="job"  type="text" placeholder="직업" /><br>
					<button>회원가입</button><br>
						</form>
</div>
</div>

					