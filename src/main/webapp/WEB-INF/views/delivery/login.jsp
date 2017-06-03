<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">

<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	$('.message a').click(function() {
		$('form').animate({
			height : "toggle",
			opacity : "toggle"
		}, "slow");
	});
	$(document).ready(function() {
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
</script>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">사업자 로그인</h1>
						<p>용달 사업자 회원가입 페이지 입니다.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section id="login">
	<div class="container">
		<div class="row">
			<div class="login-page">
				<div class="form">
					<form action="${pageContext.request.contextPath}/login_delivery.do"
						method="post">
						<input id="id" name="id" required="required" type="text" placeholder="아이디" /><br> 
						<input type="password" name="password" id="password" required="required" placeholder="비밀번호"><br>
						<button id="log" class="logc">로그인</button>
						<p class="message">
							Not registered? 
							<a href="${pageContext.request.contextPath}/delivery/register.do">Create an account</a>
						</p>
						<p class="message">
							일반회원이신가요? 
							<a href="${pageContext.request.contextPath}/member/logins.do">일반회원 로그인</a>
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>

