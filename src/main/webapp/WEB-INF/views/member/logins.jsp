<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- spring security custom tag를 사용하기 위한 선언 --%>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	$('.message a').click(function() {
		$('form').animate({
			height : "toggle",
			opacity : "toggle"
		}, "slow");
	});
	$(document).ready(function() {
		 var userInputId = getCookie("userInputId");
		    $("input[name='id']").val(userInputId); 
		     
		    if($("input[name='id']").val() != ""){ 
		        $("#idSaveCheck").attr("checked", true); 
		    }
		     
		    $("#idSaveCheck").change(function(){ 
		        if($("#idSaveCheck").is(":checked")){ 
		            var userInputId = $("input[name='id']").val();
		            setCookie("userInputId", userInputId, 7);
		        }else{
		            deleteCookie("userInputId");
		        }
		    });
		     
		    $("input[name='id']").keyup(function(){ 
		        if($("#idSaveCheck").is(":checked")){ 
		            var userInputId = $("input[name='id']").val();
		            setCookie("userInputId", userInputId, 7);
		        }
		    });
		    
		$(".logc").click(function() {
			if ($("#id").val() == "") {
				alert("아이디를 입력하세요");
				return false;

			}
			else if ($("#password").val().trim() == "") {
				alert("비밀번호를 입력하세요");
				return false;
			}else{
				var result = confirm("로그인하시겠습니까?");
				if (result) {
					return true;
				} else {
					return false;

				}
			}
		});
	});
	
	function setCookie(cookieName, value, exdays){
	    var exdate = new Date();
	    exdate.setDate(exdate.getDate() + exdays);
	    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	    document.cookie = cookieName + "=" + cookieValue;
	}
	 
	function deleteCookie(cookieName){
	    var expireDate = new Date();
	    expireDate.setDate(expireDate.getDate() - 1);
	    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	}
	 
	function getCookie(cookieName) {
	    cookieName = cookieName + '=';
	    var cookieData = document.cookie;
	    var start = cookieData.indexOf(cookieName);
	    var cookieValue = '';
	    if(start != -1){
	        start += cookieName.length;
	        var end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cookieValue = cookieData.substring(start, end);
	    }
	    return unescape(cookieValue);
	}
</script>
	<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원 로그인</h1>
						<p>
							<br>회원 로그인 페이지입니다
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
<sec:authorize ifNotGranted="ROLE_MEMBER">
<section id="login">
	<div class="container">
		<div class="row">
			<br><br>
			<div class="login-page">
				<div class="form">
					<form action="${pageContext.request.contextPath }/login.do"
						method="post">
						<input id="id" name="id" required="required" type="text"
							placeholder="아이디" /><br> <input type="password"
							name="password" id="password" required="required"
							placeholder="비밀번호"><br> <span id="idCheckView"></span>
						<a href="${pageContext.request.contextPath }/member/forgotId.do">아이디찾기</a> | 
						<a href="${pageContext.request.contextPath }/member/forgotPass.do">비밀번호찾기</a><br>
						<input type="checkbox" id="idSaveCheck">&nbsp;&nbsp;Rememeber me!<br>
						 <button id="log" class="logc">로그인</button>
						<p class="message">
							Not registered? <a
								href="${pageContext.request.contextPath}/member/registers.do">Create
								an account</a>
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
</sec:authorize>

