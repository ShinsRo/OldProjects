<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<!-- <script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script> -->
<script type="text/javascript">
$(document).ready(function(){
	var checkResultId = "";
	var checkResultPass = "";
	
	$(".regF").click(function() {
		var te = $("#tel2").val();
		var te1 = $("#tel3").val();
		var pass1 = $("#pass1").val();
		var pass2 = $("#pass2").val();
		if ($("#id").val() == "") {
			alert("아이디를 입력하세요");
			return false;
		} else if ($("#pass1").val().trim() == "") {
			alert("비밀번호를 입력하세요");
			return false;
		} else if (pass1.length < 4) {
			alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
			return false;
		} else if ($("#pass2").val().trim() == "") {
			alert("비밀번호확인를 입력하세요");
			return false;
		} else if (pass2.length < 4) {
			alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
			return false;
		} else if ($("#pass1").val() != $("#pass2").val()) {
			alert("비밀번호 체크후 가입해주세요");
			return false;
		} else if ($("#name").val().trim() == "") {
			alert("이름을 입력하세요");
			return false;
		} else if ($("#tel1").val() == "") {
			alert("번호를 선택해주세요.");
			return false;
		} else if ($("#tel2").val().trim() == "") {
			alert("중간번호 4자리를 입력하세요");
			return false;
	
		} else if (te.length < 4) {
			alert("중간번호 4자리를 입력하세요");
			return false;
		} else if ($("#tel3").val().trim() == "") {
			alert("뒷번호 4자리를 입력하세요");
			return false;
		} else if (te1.length < 4) {
			alert("뒷자리 4자리를 입력해주세요");
			return false;
		} else if ($("#job").val().trim() == "") {
			alert("직업을 입력해주세요");
			return false;
		} else if (checkResultId == "") {
			alert("아이디 중복확인을 하세요");
			return false;
		} else {
			var result = confirm("가입하시겠습니까?");
			if (result) {
				return true;
			} else if (result == false) {
				return false;
			} else if (te.length == 4 && te1.length == 4) {
				return true;
			} else if (pass1.length > 4 && pass2.length > 4) {
				return true;
			} else if ($("#pass1").val() == $("#pass2").val()) {
				return true;
			}
		}
	});
	$("#id").keyup(function() {
		var id = $(this).val().trim();
		if (id.length<4 || id.length>10) {
			$("#idCheckView").html("아이디는 4자이상 10자 이하여야 합니다.").css("color", "green");
			checkResultId = "";
			return;
		}
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/idcheckAjax_delivery.do",
			data : "id=" + id,
			success : function(data) {
				if (data == "fail") {
					$("#idCheckView").html(id+ " 사용불가!").css("color","red");
					checkResultId = "";
				} else {
					$("#idCheckView").html(id+ " 사용가능!").css("color","blue");
					checkResultId = id;
				}
			}//callback         
		});//ajax
	});//keyup
	$(function() {
		$("#postcodify_search_button").postcodifyPopUp();
	});
	$("#pass2").keyup(function() {
		if ($("#pass1").val() != $("#pass2").val()) {
			$("#passCheckView").html("비밀번호 틀렸습니다").css("color", "red");
			checkResultPass = "";
			return false;
		} else {
			$("#passCheckView").html("비밀번호 동일합니다 ").css("color", "blue");
			return true;
		}
	});//keyup  
	$(".num").keyup(function() {
		$(this).val($(this).val().replace(/[^0-9]/g, ""));
	});
});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login_register.css">
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">사업자 회원가입</h1>
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
			<div class="register-page">
				<div class="form">
					<form action="${pageContext.request.contextPath }/register_delivery.do"
						autocomplete="on" method="post" id="regForm">
						<input id="id" name="id" type="text" placeholder="아이디" /><br>
						<span id="idCheckView"></span><br> 
						<input id="pass1" name="password" type="password" placeholder="비밀번호" maxlength="10" /><br>
						<input id="pass2" type="password" name="pass2" placeholder="비밀번호확인" maxlength="10"><br> 
						<span id="passCheckView"></span><br> 
						<input id="name" name="name" type="text" placeholder="사업자 이름" /><br> 
						<select id="tel1" name="tel1">
							<option value="">번호</option>
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="070">070</option>
						</select> 
						<input class="num" id="tel2" name="tel2" type="tel" placeholder="1234" maxlength="4" /> 
						<input class="num" id="tel3" name="tel3" type="tel" placeholder="5678" maxlength="4" /><br>
						<button id="regfo" class="regF">회원가입</button>
						<br>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
