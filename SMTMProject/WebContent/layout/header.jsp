<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function logout(){
		var logout=confirm("로그아웃 하시겠습니까?");
		if(logout)
			location.href="${pageContext.request.contextPath}/DispatcherServlet?command=logout";
	}
</script>
${sessionScope.mvo.name}님 가계부 | <a href="javascript:logout()">로그아웃</a>