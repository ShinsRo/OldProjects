<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/search.css">
	
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

	$('.message a').click(function(){
		   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
		});
	$(document).ready(function(){
  		$(".searchId").click(function(){
  			if($("#name").val()==""){
				alert("이름을 입력하세요");				
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
			else if($("#tel3").val().trim()==""){
				alert("뒷번호 4자리를 입력하세요");				
				return false;
			}else{
  			var result=confirm("아이디 찾으시겠습니까?");
			if(result){
				return true;
			}else{
				return false;
				
			} 
			}
  		});
  		$(".num").keyup(function(){$(this).val( $(this).val().replace(/[^0-9]/g,"") );} );
  	});
</script>
<div class="forgotId-page">
  <div class="form">
	<form action="${pageContext.request.contextPath }/forgotId.do" method="post">
	<input id="name" name="name"  type="text" placeholder="이름" /><br>
	 <select id="tel1" name="tel1"  >
					<option value="">번호</option>
					<option value = "010" > 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					</select>
					<input class="num" id="tel2" name="tel2"  type="tel"  placeholder="전화번호"  maxlength="4" />
					<input class="num" id="tel3" name="tel3"  type="tel" placeholder="전화번호" maxlength="4" /><br>
      <button id="searchId" class="searchId">아이디 찾기</button>
    </form>
  </div>
</div>

