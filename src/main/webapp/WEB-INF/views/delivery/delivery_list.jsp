<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(document).ready(function(){
		$("#select_delivery_btn").click(function(){
			alert("접수버튼 클릭");
			alert($(this).parent().siblings().text());
		});
	});
</script>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">용달 대기 신청</h1>
						<p>아직 용달 신청 후 대기중인 신청서들입니다.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="container">
	<c:choose>
		<c:when test="${requestScope.delivery_list==''}">
			<h3>아직 등록된 신청이 없습니다.</h3>
		</c:when>
		<c:otherwise>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>BNO</th>
						<th>물건 번호</th>
						<th>신청자 ID</th>
						<th>용달 완료 여부</th>
						<th>접수하기</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${requestScope.delivery_list}" var="list">
						<tr>
							<td>${list.bno}</td><td>${list.pnos}</td>
							<td id = "id">${list.id}</td><td>${list.is_done}</td>
							<td><input type="button" id="select_delivery_btn" class="btn btn-sm btn-info" value="접수"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</div>