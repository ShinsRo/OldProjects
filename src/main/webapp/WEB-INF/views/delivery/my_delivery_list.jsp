<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
	function getDetail(id,bno) {
		var appPopURL = "${pageContext.request.contextPath}/getDeliveryDetail.do?id="+id+"&bno="+bno;
		var PopOption = "width=670, height=360, resizable=no, scrollbars=no, status=no";
		window.open(appPopURL,"",PopOption);
	}
</script>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">나의 배송 관리</h1>
						<p>내가 채택한 배송 페이지</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="container">
	<c:choose>
		<c:when test="${fn:length(my_list) == 0}">
			<h3>아직 등록된 신청이 없습니다.</h3>
		</c:when>
		<c:otherwise>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>게시물 번호</th>
						<th>신청자 ID</th>
						<th>용달 완료 여부</th>
						<th>상세정보</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestScope.my_list}" var="list">
						<tr>
							<td>${list.bno}</td>
							<td id = "id">${list.aid}</td>
							<td>${list.state}</td>
							<td><input type="button" id="select_delivery_btn" class="btn btn-sm btn-info" 
							value="상세 정보" onclick="getDetail('${list.aid}',${list.bno})"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose> 
</div>