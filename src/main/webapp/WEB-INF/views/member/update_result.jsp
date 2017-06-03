<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/member/result.css">
</head>
<body>

<script type="text/javascript">
function pageOut(){
	location.href="${pageContext.request.contextPath}/home.do";
}
function keypress(){
	if(event.keyCode==13){
		location.href = "${pageContext.request.contextPath}/home.do";
	}
}
</script>
</head>
<body onkeypress="keypress()">
	<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원정보수정 결과</h1>
						<p>
							<br>회원 정보가 수정되었어요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
<div style="padding:150px; text-align:center;">
<h1>
 ${param.id}님<br> 
 회원정보가 수정되었습니다.
</h1>
<input type="button" value="확인" onclick="pageOut()">
</div>
</body>
</html>