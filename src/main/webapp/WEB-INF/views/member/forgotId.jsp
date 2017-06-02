<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- <script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script> -->
	<script type="text/javascript">
	$(document).ready(function(){
		$(".forgotId").click(function(){
			var te=$("#tel2").val();
			var te1=$("#tel3").val();
			if($("#name").val().trim()==""){
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
			else{
				var result=confirm("아이디를 찾으시겠습니까?");
				if(result){
					return true;	
				}else if(result==false){
					return false;
				}
				 else if(te.length==4 && te1.length==4){
					return true;
				} 
			}
		});
	    	$(".num").keyup(function(){$(this).val( $(this).val().replace(/[^0-9]/g,"") );} );
	});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
<section id="forgotIds">
	<div class="container">
		<div class="row">
			<div class="forgotId-page">
				<div class="form">
				<form action="${pageContext.request.contextPath }/forgotId.do"
							 method="post" id="findId">
					<input id="name" 	name="name"   type="text" placeholder="이름" /><br>
					<select id="tel1" name="tel1"  >
					<option value="">번호</option>
					<option value = "010" > 010 </option>
                    <option value = "011"> 011 </option>
                    <option value = "070"> 070 </option>
					</select>
					<input class="num" id="tel2" name="tel2"  type="tel"  placeholder="전화번호"  maxlength="4" />
					<input class="num" id="tel3" name="tel3"  type="tel" placeholder="전화번호" maxlength="4" /><br>
					<button id="forgotId" class="forgotId">아이디찾기</button><br>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>

