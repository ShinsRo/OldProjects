<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>로그인</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/login.css" />
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
<link
	href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
	<script type="text/javascript">
	$(document).ready(function(){
		var checkResultId="";		
		$("#regForm").submit(function(){			
			if($("#ids").val()==""){
				alert("아이디를 입력하세요");				
				return false;
			}
			if($("#passwords").val().trim()==""){
				alert("패스워드를 입력하세요");				
				return false;
			}
			if($("#names").val().trim()==""){
				alert("이름을 입력하세요");				
				return false;
			}
			if($("#addresss").val().trim()==""){
				alert("주소를 입력하세요");				
				return false;
			}
			($("#tels").val().trim()==""){
				alert("전화번호를 입력하세요");				
				return false;
		}
			($("#jobs").val().trim()==""){
				alert("직업을 입력하세요");				
				return false;
		}
			if(checkResultId==""){
				alert("아이디 중복확인을 하세요");
				return false;
			}		
		});
		$("#id").keyup(function(){
			var id=$(this).val().trim();
			if(id.length<4 || id.length>10){
				$("#idCheckView").html("아이디는 4자이상 10자 이하여야 함!").css(
						"background","pink");
				checkResultId="";
				return;
			}
			
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/idcheckAjax.do",				
				data:"id="+id,	
				success:function(data){						
					if(data=="fail"){
					$("#idCheckView").html(id+" 사용불가!").css("background","red");
						checkResultId="";
					}else{						
						$("#idCheckView").html(id+" 사용가능!").css(
								"background","yellow");		
						checkResultId=id;
					}					
				}//callback			
			});//ajax
		});//keyup
	});//ready
</script>
</head>
<body>
<a href="${pageContext.request.contextPath }/home.do">홈으로</a>
<hr>
	<div class="container">
		<section>
			<div id="container_demo">
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
					<h1>로그인</h1>
						<%-- <form action="${pageContext.request.contextPath }/login.do"> --%>
						<!-- 	autocomplete="on" method="post">
							아이디 <input id="id"
									name="id" required="required" type="text" placeholder="아이디" /><br>
							비밀번호<input type="text" 
							name="password" id="password" required="required" placeholder="비밀번호"><br>
							<input type="submit" value="로그인">		 -->
							<p>
								<label for="id" class="id"> 아이디 </label> <input id="id"
									name="id" required="required" type="text" placeholder="아이디" />
							</p>
							<p>
								<label for="password" class="password"> 비밀번호 </label> <input
									id="password" name="password"  required="required"
									type="password" placeholder="비밀번호" />
							</p>
							<p class="login button">
								<a href="http://cookingfoodsworld.blogspot.in/" target="_blank"></a>
							</p>
							<p>
								<input type="submit" value="로그인">
							</p>
							<p class="change_link">
								회원이 아닌 상태입니다 <a href="#toregister" class="to_register">회원가입</a>
							</p>
						<!-- </form> -->
					</div>
		<div id="register" class="animate form">
						<%-- <form action="${pageContext.request.contextPath }/register.do"
							autocomplete="on" method="post" id="regForm"> --%>
							<h1>회원가입</h1>
							<p>
								<label for="id" class="id"> 아이디</label> <input id="ids"
								required="required"	 name="id"  type="text" placeholder="아이디" />
									<span id="idCheckView"></span><br>
							</p>
							<p>
								<label for="password" class="password">비밀번호 </label> <input
									id="passwords" name="password"  required="required"
									type="password" placeholder="비밀번호" />
							</p>
							<p>
								<label for="name" class="name">이름</label> <input id="names"
								required="required"	name="name"  type="text" placeholder="이름" />
							</p>
						
							<label for="tel" class="tel">우편번호</label><input type="text" name="" class="postcodify_postcode5" value="" />
							<button id="postcodify_search_button">검색</button><br />
						<label for="tel" class="tel">주소 </label><input type="text" name="" class="postcodify_address" value="" readonly="readonly"/><br />
						<label for="tel" class="tel">상세주소  </label><input type="text" name="" class="postcodify_details" value="" />
							<p>
								<label for="tel" class="tel">전화번호 </label> <input id="tels"
								required="required"	name="tel"  type="tel" placeholder="전화번호" />
							</p>
							<p>
								<label for="job" class="job">직업</label> <input id="jobs"
								required="required"	name="job"  type="text" placeholder="직업" />
							</p>
							<p class="signin button">
								<input type="submit" value="회원가입" />
							</p>
							<p class="change_link">
								이미 회원 입니까? <a href="#tologin" class="to_register"> 로그인 </a>
							</p>
					<!-- 	</form> -->
					</div>
				</div>
			</div>
		</section>
	</div> 
</body>
