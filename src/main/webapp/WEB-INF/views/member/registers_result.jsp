<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/result.css">
	<script type="text/javascript">
	function pageOut() {
		location.href = "${pageContext.request.contextPath}/home.do";
	}
	function keypress(){
		if(event.keyCode==13){
			location.href = "${pageContext.request.contextPath}/home.do";
		}
	}
</script>
<body onkeypress="keypress()" class="result" >
	<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원가입 결과</h1>
						<p>
							<br>회원가입이 완료되었어요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
  <h1>${requestScope.memberVO.id }님 회원가입 ok!</h1> <br>
	<a href="${pageContext.request.contextPath}/home.do">
	<input type="button" value="확인" onclick="pageOut()"></a>
</body>