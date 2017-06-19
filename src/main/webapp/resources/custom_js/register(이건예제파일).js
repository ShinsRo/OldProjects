	$(document)
			.ready(
					function() {
						var checkResultId = "";
						var checkResultPass = "";

						$(".regF")
								.click(
										function() {
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
											}

											else if ($("#pass2").val().trim() == "") {
												alert("비밀번호확인를 입력하세요");
												return false;
											} else if (pass2.length < 4) {
												alert("비밀번호 4자리이상 10자리 이하로 입력해주세요");
												return false;
											} else if ($("#pass1").val() != $(
													"#pass2").val()) {
												alert("비밀번호 체크후 가입해주세요");
												return false;
											} else if ($("#name").val().trim() == "") {
												alert("이름을 입력하세요");
												return false;
											} else if ($("#addr_code").val()
													.trim() == "") {
												alert("우편번호를 검색해주세요");
												return false;
											} else if ($("#addr").val().trim() == "") {
												alert("주소를 입력하세요");
												return false;
											} else if ($("#addr_detail").val()
													.trim() == "") {
												alert("상세주소를 입력하세요");
												return false;
											} else if ($("#tel1").val() == "") {
												alert("번호를 선택해주세요.");
												return false;
											}

											else if ($("#tel2").val().trim() == "") {
												alert("중간번호 4자리를 입력하세요");
												return false;

											} else if (te.length < 4) {
												alert("중간번호 4자리를 입력하세요");
												return false;
											}

											else if ($("#tel3").val().trim() == "") {
												alert("뒷번호 4자리를 입력하세요");
												return false;
											} else if (te1.length < 4) {
												alert("뒷자리 4자리를 입력해주세요");
												return false;
											}

											else if ($("#job").val().trim() == "") {
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
												} else if (te.length == 4
														&& te1.length == 4) {
													return true;
												} else if (pass1.length > 4
														&& pass2.length > 4) {
													return true;
												}

												else if ($("#pass1").val() == $(
														"#pass2").val()) {
													return true;
												}
											}
										});

						$("#id")
								.keyup(
										function() {
											var id = $(this).val().trim();
											if (id.length<4 || id.length>10) {
												$("#idCheckView")
														.html(
																"아이디는 4자이상 10자 이하여야 함!")
														.css("color", "green");
												checkResultId = "";
												return;
											}

											$
													.ajax({
														type : "POST",
														url : "${pageContext.request.contextPath}/idcheckAjax.do",
														data : "id=" + id,
														success : function(data) {
															if (data == "fail") {
																$(
																		"#idCheckView")
																		.html(
																				id
																						+ " 사용불가!")
																		.css(
																				"color",
																				"red");
																checkResultId = "";
															} else {
																$(
																		"#idCheckView")
																		.html(
																				id
																						+ " 사용가능!")
																		.css(
																				"color",
																				"blue");
																checkResultId = id;
															}
														}//callback         
													});//ajax
										});//keyup
						$(function() {
							$("#postcodify_search_button").postcodifyPopUp();
						});
						$("#pass2")
								.keyup(
										function() {
											if ($("#pass1").val() != $("#pass2")
													.val()) {
												$("#passCheckView").html(
														"비밀번호 틀렸습니다").css(
														"color", "red");
												checkResultPass = "";
												return false;
											} else {
												$("#passCheckView").html(
														"비밀번호 동일합니다 ").css(
														"color", "blue");
												return true;
											}
										});//keyup  
						$(".num").keyup(function() {
							$(this).val($(this).val().replace(/[^0-9]/g, ""));
						});
						$(".ids").keyup(function() {
		                     $(this).val($(this).val().replace(/[^a-z0-9]/g, ""));
		                  });   
						$(".pass").keyup(function() {
		                     $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ""));
		                  });  
					});//ready