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
<div style="padding:150px; text-align:center;">
<h1>
 ${param.id}님<br> 
 회원정보가 수정되었습니다.
</h1>
<input type="button" value="확인" onclick="pageOut()">
</div>
</body>
</html>