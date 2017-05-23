<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>   
<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <!-- title이 null인 경우는 무시된다 -->
  <title><tiles:insertAttribute name="title" ignore="true" /></title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
  <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/css/home.css" >
  <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/css/left.css" >
</head>
<body>
	<div id="header"><tiles:insertAttribute name="header" /></div>
	<div class="container-fluid text-center">    
     <div class="row content">
    <div id="left" class="col-sm-3 sidenav" ><tiles:insertAttribute name="left" /></div>
	<div id="main" class="col-sm-7 text-left"><tiles:insertAttribute name="main" /></div>
	</div>
	</div>
</body>
</html>










