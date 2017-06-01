<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- <script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script> -->
	<script type="text/javascript">
	$(document).ready(function(){
		var checkResultId="";		
		var checkResultPass="";
		
		$(".regF").click(function(){
			var te=$("#tel2").val();
			var te1=$("#tel3").val();
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
			else if($("#pass1").val()!=$("#pass2").val()){
				alert("비밀번호 체크후 가입해주세요");
				return false;
			}
			else if($("#name").val().trim()==""){
				alert("이름을 입력하세요");				
				return false;
			}
			else if($("#addr_code").val().trim()==""){
				alert("우편번호를 검색해주세요");
				return false;
			}
			else if($("#addr").val().trim()==""){
				alert("주소를 입력하세요");				
				return false;
			}	
			else if($("#addr_detail").val().trim()==""){
				alert("상세주소를 입력하세요");				
				return false;
			}
			else if($("#tel1").val()=="") {
			    alert("번호를 선택해주세요.");
			    return false;
			}
		
			else if($("#tel2").val().trim()==""){
				alert("중간번호 4자리를 입력하세요");				
				return false;
			
			}
			else if(te.length<4){
				alert("중간번호 4자리를 입력하세요");
				return false;
			}
			
			else if($("#tel3").val().trim()==""){
				alert("뒷번호 4자리를 입력하세요");				
				return false;
			}	
			else if(te1.length<4){
				alert("뒷자리 4자리를 입력해주세요");
				return false;
			}
			
			else if($("#job").val().trim()==""){
				alert("직업을 입력해주세요");
				return false;
			}
			else if(checkResultId==""){
				alert("아이디 중복확인을 하세요");
				return false;
			}
			else{
				var result=confirm("가입하시겠습니까?");
				if(result){
					return true;	
				}
				else if($("#pass1").val()==$("#pass2").val()){
					return true;		
				}
				else if(te.length==4 && te1.length==4){
					return true;
				}
				else{
					return false;
			}
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
					$("#idCheckView").html(id+" 사용불가!").css("color","red");
						checkResultId="";
					}else{						
						$("#idCheckView").html(id+" 사용가능!").css(
								"color","blue");		
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
				checkResultPass="";
				return false;
			}else{
				$("#passCheckView").html("비밀번호 동일합니다 ").css(
						"color","blue");
				return true;
			}
	    	});//keyup  
    	$("#pass1").keyup(function(){
				var pass=$(this).val().trim();
				if(pass.length<4 || pass.length>10){
					$("#passCheckView").html("비밀번호는 4자이상 10자 이하여야 함!").css(
							"color","green");
					checkResultPass="";
					return false;
				}
	    	});
	    	$("#pass2").keyup(function(){
	    		var pass2=$(this).val().trim();
	    		if(pass2.length<4||pass2.length>10){
	    			$("#passCheckView").html("비밀번호는 4자이상 10자 이하여야 함!").css(
							"color","green");
					checkResultPass="";
					return false;
	    		}
	    	});
	    	$(".num").keyup(function(){$(this).val( $(this).val().replace(/[^0-9]/g,"") );} );
	});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
<section id="login">
	<div class="container">
		<div class="row">
			<div class="register-page">
				<div class="form">
				<form action="${pageContext.request.contextPath }/register.do"
							autocomplete="on" method="post" id="regForm">
					<input id="id"  name="id"  type="text"  placeholder="아이디" /><br>
					<span id="idCheckView"></span><br>
					<input id="pass1" name="password"  
							type="password"  placeholder="비밀번호" maxlength="11"/><br>
					<input id="pass2" type="password"  name="pass2" placeholder="비밀번호확인" maxlength="11"><br>
					<span id="passCheckView"></span><br>
					<input id="name" 	name="name"   type="text" placeholder="이름" /><br>
					<input id="addr_code" type="text" name="addrcode" class="postcodify_postcode5"  placeholder="우편번호" /><br>
					<input type = "button" id="postcodify_search_button" class="button" value = "검색"><br />
					<input id="addr" type="text" name="addr" class="postcodify_address" readonly="readonly" placeholder="주소" /><br />
					<input id="addr_detail" type="text"  name="addr_detail" class="postcodify_details" placeholder="상세주소" /><br>
					<select id="tel1" name="tel1"  >
					<option value="">번호</option>
					<option value = "010" > 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					</select>
					<input class="num" id="tel2" name="tel2"  type="tel"  placeholder="전화번호"  maxlength="4" />
					<input class="num" id="tel3" name="tel3"  type="tel" placeholder="전화번호" maxlength="4" /><br>
					<input id="job" 	name="job"  type="text"  placeholder="직업" /><br>
					<button id="regfo" class="regF">회원가입</button><br>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>

