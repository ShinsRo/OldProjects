<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>   
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    
  	<!-- jquery 로딩 -->
  	<script type="text/javascript" src="//code.jquery.com/jquery.min.js"></script>
    
  <title><tiles:insertAttribute name="title" ignore="true" /></title>
  
	
	<!-- js 로딩 -->	
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/lightbox.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/wow.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/main.js"></script>   
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-ui.js"></script>
	<!-- css 링크 -->
    <link href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/resources/css/animate.min.css" rel="stylesheet"> 
    <link href="${pageContext.request.contextPath }/resources/css/lightbox.css" rel="stylesheet"> 
	<link href="${pageContext.request.contextPath }/resources/css/main.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath }/resources/css/responsive.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath }/resources/css/jquery-ui.css" rel="stylesheet">
</head>

<body>
	<tiles:insertAttribute name = "header"/>
	<tiles:insertAttribute name = "main"/>
	<tiles:insertAttribute name = "footer"/>
</body>
</html>










