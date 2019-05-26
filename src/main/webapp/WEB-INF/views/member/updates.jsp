<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>   
<script type="text/javascript">
	$(document).ready(function() {
		$(".uplo").click(function() {
			var te = $("#tel2").val();
			var te1 = $("#tel3").val();
			var pass1 = $("#pass1").val();
			var pass2 = $("#pass2").val();
			if ($("#pass1").val().trim() == "") {
				alert("비밀번호를 입력하세요");
				return false;
			}
			if ($("#pass2").val().trim() == "") {
				alert("비밀번호확인을 입력하세요");
				return false;
			} else if (pass1.length < 4) {
				alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
				return false;
			} else if (pass2.length < 4) {
				alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
				return false;
			} else if ($("#pass1").val() != $("#pass2").val()) {
				alert("비밀번호 체크후 가입해주세요");
				return false;
			}

			if ($("#name").val().trim() == "") {
				alert("이름을 입력하세요");
				return false;
			}
			else if ($("#addr").val().trim() == "") {
				alert("주소를 입력하세요");
				return false;
			} else if ($("#addr_detail").val().trim() == "") {
				alert("상세주소를 입력하세요");
				return false;
			} else if ($("#tel1").val() == "") {
				alert("번호를 선택해주세요.");
				return false;
			}else if ($("#tel2").val().trim() == "") {
				alert("중간번호 4자리를 입력하세요");
				return false;
			} else if ($("#tel3").val().trim() == "") {
				alert("뒷번호 4자리를 입력하세요");
				return false;
			} else if ($("#job").val().trim() == "") {
				alert("직업을 입력해주세요");
				return false;
			}
			else {
				var result = confirm("수정하시겠습니까?");
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
		$(function() {
			$("#postcodify_search_button").postcodifyPopUp();
		});
		$("#pass2").keyup(function() {
			if ($("#pass1").val() != $("#pass2").val()) {
				$("#passCheckView").html("비밀번호 틀렸습니다").css("color", "red");
				checkResultId = "";
				return;
			} else {
				$("#passCheckView").html("비밀번호 동일합니다").css("color", "blue");
			}
		});//keyup  */ 
		$(".num").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
	});//ready
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/updates.css">
		<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원정보수정</h1>
						<p>
							<br>회원정보를변경해주세요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
 <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">
<section>
	<div class="container">
		<div class="row">
			<div class="update_page">
				<div class="form">
					<form id="updateM"
						action="${pageContext.request.contextPath }/updateMember.do"
						method="post">
						<input type="text" readonly="readonly" name="id" placeholder="아이디"
							id="id" value="<sec:authentication property="principal.id"/>"><br> <input
							id="pass1" name="password" type="password" placeholder="비밀번호"
							value="" maxlength="11" /><br>
						<input id="pass2" type="password" name="pass2"
							placeholder="비밀번호확인" value=""
							maxlength="11"><br> <span id="passCheckView"></span><br>
						<input type="text" name="name" readonly="readonly"
							placeholder="이름" id="name" value="<sec:authentication property="principal.name"/>"><br>
						<input id="addr_code" type="text" readonly="readonly"
							class="postcodify_postcode5" placeholder="우편번호" /><br> <input
							id="postcodify_search_button" type="button" value="검색" size="6">
						<br /> <input id="addr" type="text" name="addr"
							class="postcodify_address" value="<sec:authentication property="principal.addr"/>"
							readonly="readonly" placeholder="주소" /><br /> <input
							id="addr_detail" type="text" name="addr_detail"
							class="postcodify_details"
							value="<sec:authentication property="principal.addr_detail"/>" placeholder="상세주소" /><br>
						<select id="tel1" name="tel1">
							<option value="">번호</option>
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="070">070</option>
							<!-- <input id="tel1" name="tel1"  type="tel" placeholder="전화번호" maxlength="3"/> -->
						</select> <input class="num" id="tel2" name="tel2" type="tel"
							placeholder="전화번호" maxlength="4" size="4" /> <input class="num"
							id="tel3" name="tel3" type="tel" placeholder="전화번호" maxlength="4"
							size="4" /><br> <input id="job" name="job" type="text"
							value="<sec:authentication property="principal.job"/>" placeholder="직업" /><br>
						<button id="upd" class="uplo">회원수정</button>
						&nbsp;<a
							href="${pageContext.request.contextPath }/member/delete.do"><input
							type="button" value="회원탈퇴"></a>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
</sec:authorize>