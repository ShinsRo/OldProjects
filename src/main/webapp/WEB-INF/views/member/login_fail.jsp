<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function pageOut() {
		location.href = "${pageContext.request.contextPath}/home.jsp";
	}
	function keypress(){
		if(event.keyCode==13){
			location.href = "${pageContext.request.contextPath}/home.jsp";
		}
	}
</script>
</head>
<body>
<body onkeypress="keypress()">
	<c:if test="${sessionScope.mvo==null}">
				<h1>등록된 회원이 없습니다.</h1>
				<a href="${pageContext.request.contextPath}/home.do"><input type="button" value="확인" onclick="pageOut()"></a>
			</c:if>
</body>
</html>