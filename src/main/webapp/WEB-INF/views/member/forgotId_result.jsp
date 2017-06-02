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
  <h1>고객님의 아이디는 ${requestScope.id} 입니다</h1> <br>
	<a href="${pageContext.request.contextPath}/home.do">
	<input type="button" value="확인" onclick="pageOut()"></a>
</body>