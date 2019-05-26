<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
</head>
<body>
	<script src="//code.jquery.com/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#DeleteMember").click(function() {
				var pass1 = $("#pass1").val();
				if ($("#pass1").val().trim() == "") {
					alert("비밀번호를 입력하세요");
					return false;
				} else if (pass1.length < 4) {
					alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
					return false;
				} else {
					var result = confirm("탈퇴하시겠습니까?");
					if (result) {
						alert("감사합니다. 탈퇴완료!");
						return true;

					} else if (result == false) {
						return false;
					} else if (pass1.length > 4) {
						return true;
					}
				}
			});//click
		});//ready
	</script>
</head>
<body>
	<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원탈퇴</h1>
						<p>
							<br>회원탈퇴 진행해주세요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
 <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">
	<div class="delete_page">
		<div class="form">
			<form method="post"
				action="${pageContext.request.contextPath}/deleteMember.do"
				onsubmit="return passwordChecking()">
				<input type="hidden" name="id" value="<sec:authentication property="principal.id"/>">
				<input id="pass1" type="password" name="password" placeholder="비밀번호"
					maxlength="11"><br>
				<input type="hidden" name="SecurityPassword" id="SecurityPassword"
				value="<sec:authentication property="principal.password"/>">
				<button id="DeleteMember">회원탈퇴</button>
			</form>
		</div>
	</div>
	</sec:authorize>
</body>
</html>