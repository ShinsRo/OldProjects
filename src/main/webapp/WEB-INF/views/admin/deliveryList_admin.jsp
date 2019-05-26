<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function confirm_fn(id) {
		if(confirm(id+" 회원을 제휴 승인하시겠습니까?")){
			location.href="confirmDelivery_admin.do?id="+id;
		}else{
			return false;
		};
	}
	function revocation_fn(id){
		if(confirm(id+" 용달회원을 계약 해지하시겠습니까?")){
			location.href="revocationContract_admin.do?id="+id;
		}else{
			return false;
		};
	}
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
				<th>용달해지</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.NotSelectedlist}" var="delivery">
				<tr>
					<td id="delivery-id">${delivery.id}</td>
					<td>${delivery.name}</td>
					<td>${delivery.tel}</td>
					<td>${delivery.is_confirmed}</td>
					<td>
					<c:if test="${delivery.is_confirmed=='READY'}">
					<input type="button" id="confirm-btn" class="btn btn-info" value="승인" onclick="confirm_fn('${delivery.id}')">
					</c:if>
					</td>
					<td>
					<c:if test="${delivery.is_confirmed=='YES'}">
					<input type="button" id="confirm-btn" class="btn btn-info" value="사업자해지" onclick="revocation_fn('${delivery.id}')">
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
