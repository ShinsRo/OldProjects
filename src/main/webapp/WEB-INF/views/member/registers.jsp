<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- <script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script> -->
	<script type="text/javascript">
	$(document).ready(function(){
		var checkResultId="";		
		$(".regF").click(function(){		
			if($("#id").val()==""){
				alert("아이디를 입력하세요");				
				return false;
			}
			else if($("#pass1").val().trim()==""){
				alert("비밀번호를 입력하세요");				
				return false;
			}
			else if($("#pass2").val().trim()==""){
				alert("비밀번호확인를 입력하세요");				
				return false;
			}
			else if($("#name").val().trim()==""){
				alert("이름을 입력하세요");				
				return false;
			}
			else if($("#address").val().trim()==""){
				alert("주소를 입력하세요");				
				return false;
			}	
			else if($("#addr_detail").val().trim()==""){
				alert("상세주소를 입력하세요");				
				return false;
			}
			else if($("#tel1").val().trim()==""){
				var tel1=$(this).val().trim();
				if(tel1.length==1 || tel1.length==2){
				alert("3자리를 입력해주세요");
				return false;
				}
			}	
			else if($("#tel2").val().trim()==""){
				var tel2=$(this).val().trim();
				if(tel2.length==1 || tel2.length==2 || tel2.length==3 ){	
				alert("4자리를 입력하세요");				
				return false;
				}
			}	
			else if($("#tel3").val().trim()==""){
				var tel3=$(this).val().trim();
				if(tel3.length==1 || tel3.length==2 || tel3.length==3 ){	
				alert("4자리를 입력하세요");				
				return false;
				}
			}	
			else if(checkResultId==""){
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
		$("#pass2").keyup(function(){
			if($("#pass1").val()!=$("#pass2").val()){
				$("#passCheckView").html("비밀번호 틀렸습니다").css(
						"color","red");
				checkResultId="";
				return;
			}else{
				$("#passCheckView").html("비밀번호 OK ").css(
						"color","blue");
			}
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/passwordCheck.do",				
				data:"password="+password,	
				success:function(data){						
					if(data=="fail"){
					$("#passCheckView").html(password+" 사용불가!").css("color","red");
						checkResultId="";
					}else{						
						$("#passCheckView").html(password+" 사용가능!").css(
								"color","blue");		
						checkResultId=password;
					}					
				}//callback			
			});//ajax
	    	});//keyup  */ 
		$("#regfo").click(function(){
			var result=confirm("가입하시겠습니까?");
			if(result){
				return true;
			}else{
				return false;
				
			}
		});
	});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
	<div class="register-page">
	 	 <div class="form">
				<form action="${pageContext.request.contextPath }/register.do"
							autocomplete="on" method="post" id="regForm">
					<input id="id"  name="id"  type="text" required="required" placeholder="아이디" /><br>
					<span id="idCheckView"></span><br>
					<input id="pass1" name="password"  
							type="password" required="required" placeholder="비밀번호" /><br>
					<input id="pass2" type="password" required="required" name="pass2" placeholder="비밀번호확인"><br>
					<span id="passCheckView"></span><br>
					<input id="name" 	name="name" required="required"  type="text" placeholder="이름" /><br>
					<input type="text" name="addrcode" class="postcodify_postcode5"  placeholder="우편번호" /><br>
					<input type = "button" id="postcodify_search_button" class="button" value = "검색"><br />
					<input type="text" name="addr" class="postcodify_address" readonly="readonly" placeholder="주소" /><br />
					<input id="addr_detail" type="text" required="required" name="addr_detail" class="postcodify_details" placeholder="상세주소" /><br>
					<select id="tel1" name="tel1"  required="required" >
					<option value = "010" > 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					</select>
					<input id="tel2" name="tel2"  type="tel" required="required" placeholder="전화번호"  maxlength="4" />
					<input id="tel3" name="tel3"  type="tel" required="required" placeholder="전화번호" maxlength="4" /><br>
					
					<input id="job" 	name="job"  type="text" required="required" placeholder="직업" /><br>
					<button id="regfo" class="regF">회원가입</button><br>
						</form>
</div>
</div>

					