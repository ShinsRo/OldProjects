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
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Light, Basic Header</title>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header-basic-light.css">
	<link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>

</head>

<header class="header-basic-light">
	<div class="header-limiter">
		<h1><a href="#">ShowMeThe<span>Money!</span></a></h1>
		<c:if test="${!(empty mvo)}">
		<nav>
		
			<a href="">${sessionScope.mvo.name}님 가계부</a>
		
			<a href="../account/list.jsp">List</a>
			<a href="${pageContext.request.contextPath}/graph.jsp">Graph</a>
			<a href="../calendar.jsp">Calendar</a>
			<a href="javascript:logout()" class="selected">Logout</a>
		

		</nav>
		</c:if>
	</div>
</header>


