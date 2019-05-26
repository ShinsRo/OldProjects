<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Result</title>
</head>
<body>
<c:choose>
   <c:when test="${empty sessionScope.mvo}">
      <script type="text/javascript">
         alert("세션이 만료되었습니다.");
         location.href="${pageContext.request.contextPath}/member/login.jsp";
      </script>
   </c:when>
   <c:otherwise>
      <script type="text/javascript">
         alert("${sessionScope.mvo.name}님 반갑습니다.");
         location.href="${pageContext.request.contextPath}/account/list.jsp";
      </script>
   </c:otherwise>
</c:choose>
</body>
</html>