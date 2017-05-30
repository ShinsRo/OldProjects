<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
 <script type="text/javascript">
$(document).ready(function(){
	$(".uplo").click(function(){
		if($("#pass1").val().trim()==""){
			alert("비밀번호를 입력하세요");				
			return false;
		}
		if($("#pass2").val().trim()==""){
			alert("비밀번호확인을 입력하세요");				
			return false;
		}
		if($("#name").val().trim()==""){
			alert("이름을 입력하세요");				
			return false;
		}
		if($("#postcodify_postcode5").val().trim()==""){
			alert("우편번호를 입력하세요");
			return false;
		}
		if($("#address").val().trim()==""){
			alert("주소를 입력하세요");				
			return false;
		}	
		if($("#addr_detail").val().trim()==""){
			alert("상세주소를 입력하세요");				
			return false;
		}
		if($("#tel1").val().trim()==""){
			var tel1=$(this).val().trim();
			if(tel1.length==1 || tel1.length==2){
			alert("3자리를 입력해주세요");
			return false;
			}
		}	
		if($("#tel2").val().trim()==""){
			var tel2=$(this).val().trim();
			if(tel2.length==1 || tel2.length==2 || tel2.length==3 ){	
			alert("4자리를 입력하세요");				
			return false;
			}
		}	
		if($("#tel3").val().trim()==""){
			var tel3=$(this).val().trim();
			if(tel3.length==1 || tel3.length==2 || tel3.length==3 ){	
			alert("4자리를 입력하세요");				
			return false;
			}
		}	
	
	});

	$(function(){ $("#postcodify_search_button").postcodifyPopUp(); });
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
	$("#upd").click(function(){
		var result=confirm("수정하시겠습니까?");
		if(result){
			return true;
		}else{
			return false;
			
		}
	});
});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/updates.css">
<div class="update_page">
<div class="form">
<form id="updateM" action="${pageContext.request.contextPath }/updateMember.do" method="post">
	 <input type="text" readonly="readonly" name="id" placeholder="아이디" id="id" value="${sessionScope.mvo.id }"><br> 
		<input id="pass1" name="password"  type="password" placeholder="비밀번호" /><br>
		<input id="pass2" type="password" name="pass2" placeholder="비밀번호확인"><br>
		<span id="passCheckView"></span><br>
	<input type="text" name="name" readonly="readonly" placeholder="이름" id="name" value="${sessionScope.mvo.name }"><br> 
		<input type="text" class="postcodify_postcode5" placeholder="우편번호" /><br>
		<input  id="postcodify_search_button" type="button" value="검색" size="6">
	<br />  <input type="text" name="addr" class="postcodify_address" 	value="${sessionScope.mvo.addr }" readonly="readonly" placeholder="주소" /><br /> 
	<input type="text" name="addr_detail" class="postcodify_details" value="${sessionScope.mvo.addr_detail }" placeholder="상세주소" /><br> 
					<select id="tel1" name="tel1" >
					<option value = "010"> 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					<!-- <input id="tel1" name="tel1"  type="tel" placeholder="전화번호" maxlength="3"/> -->
					</select>
					<input id="tel2" name="tel2"  type="tel" placeholder="전화번호"  maxlength="4" size="4"/>
					<input id="tel3" name="tel3"  type="tel" placeholder="전화번호" maxlength="4" size="4"/><br>
					<input id="job" 	name="job"  type="text" placeholder="직업" /><br>
	<button id="upd" class="uplo" > 회원수정</button>&nbsp;<a href="${pageContext.request.contextPath }/member/delete.do"><input type="button" value="회원탈퇴"></a>
</form>
</div>
</div>