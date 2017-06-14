<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	/* $(document).ready(function(){
		$("#select_delivery_btn").click(function(){
			alert("접수버튼 클릭");
			alert($(this).parent().siblings().text());
		});
	}); */
	function getDetail(id,bno) {
		var appPopURL = "${pageContext.request.contextPath}/getDeliveryDetail.do?id="+id+"&bno="+bno;
		var PopOption = "width=670, height=360, resizable=no, scrollbars=no, status=no";
		window.open(appPopURL,"",PopOption);
	}
	
	function match_Delivery(bno,aid,did) {
		if(confirm(bno+"번 용달 신청을 접수하시겠습니까?")){
			location.href="registerDeliveryMatch.do?bno="+bno+"&aid="+aid+"&did="+did;
		}else{
			return false;
		}
	}
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
						<th>게시물 번호</th>
						<th>물건 번호</th>
						<th>신청자 ID</th>
						<th>용달 완료 여부</th>
						<th>접수하기</th>
						<th>상세정보</th>
					</tr>
				</thead>
				<tbody>
				<sec:authentication property="principal" var="mvo"/>
					<c:forEach items="${requestScope.delivery_list}" var="list">
						<tr>
							<td>${list.bno}</td><td>${list.pnos}</td>
							<td id = "id">${list.id}</td><td>${list.is_done}</td>
							<td><input type="button" id="select_delivery_btn" class="btn btn-sm btn-info" 
							value="접수" onclick="match_Delivery(${list.bno},'${list.id}', '${mvo.id})"></td>
							<td><input type="button" id="select_delivery_btn" class="btn btn-sm btn-info" 
							value="상세 정보" onclick="getDetail('${list.id}',${list.bno})"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</div>