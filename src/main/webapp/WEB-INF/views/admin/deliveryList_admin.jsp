<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	$(document).ready(function(){
		$("#confirm-btn").click(function(){
			
			alert("hi");
		});
	});
</script>
<div class="container">
	<table class="table table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>전화번호</th>
				<th>승인여부</th>
				<th>승인</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.NotSelectedlist}" var="delivery">
				<tr>
					<td>${delivery.id}</td>
					<td>${delivery.name}</td>
					<td>${delivery.tel}</td>
					<td>${delivery.is_confirmed}</td>
					<td><input type="button" id="confirm-btn" class="btn btn-info" value="승인"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
