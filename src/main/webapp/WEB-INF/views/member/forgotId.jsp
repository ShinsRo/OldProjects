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
  		$(".findid").click(function(){
  			if($("#name").val()==""){
				alert("이름를 입력하세요");				
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
  		});
			$("#findid").click(function(){
				var result=confirm("아이디를 찾으시겠습니까?");
				if(result){
					return true;
				}else{
					return false;
					
				}
			});
  	});
</script>
<div class="findMemberById-page">
  <div class="form">
	<form action="${pageContext.request.contextPath }/forgotId.do" >
	<input id="name" name="name" required="required" type="text" placeholder="이름" /><br> 
	<select id="tel1" name="tel1"  required="required" >
					<option value = "010" > 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					</select>
					<input id="tel2" name="tel2"  type="tel" required="required" placeholder="전화번호"  maxlength="4" />
					<input id="tel3" name="tel3"  type="tel" required="required" placeholder="전화번호" maxlength="4" /><br>
      <button id="findid" class="findid">아이디찾기</button>
    </form>
  </div>
</div>