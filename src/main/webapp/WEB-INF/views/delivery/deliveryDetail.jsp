<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<h2>신청 상세 정보</h2>
<div class="panel panel-default">
	<div class="panel-heading">신청자</div>
	<div class="panel-body">
		<c:set var="reciever" value="${requestScope.map.reciever}" />
		아이디: ${reciever.id}<br> 이름: ${reciever.name}<br> 연락처:
		${reciever.tel}<br> 주소: ${reciever.addr} ${reciever.addr_detail}<br>
	</div>
	<div class="panel-heading">기부자</div>
	<div class="panel-body">
		<c:set var="giver" value="${requestScope.map.giver}" />
		아이디: ${giver.id}<br> 이름: ${giver.name}<br> 연락처: ${giver.tel}<br>
		주소: ${giver.addr} ${reciever.addr_detail}<br>
	</div>
</div>