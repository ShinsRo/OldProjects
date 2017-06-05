<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- jquery 로딩 -->
<script type="text/javascript" src="//code.jquery.com/jquery.min.js"></script>

<title></title>
<!-- js 로딩 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/lightbox.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/wow.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/main.js"></script>

<!-- css 링크 -->
<link
	href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/resources/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/resources/css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/resources/css/lightbox.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/css/main.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/resources/css/responsive.css"
	rel="stylesheet">
</head>
<script type="text/javascript">
	$(document)
			.on(
					"click",
					"#selectApp",
					function() {
						location.href = "${pageContext.request.contextPath}/confirmApply.do?id="
								+ $(this).val()
								+ "&bno="
								+ $("#" + $(this).val() + "bno").val();
					});//on
</script>
<body>
	<!-- 배너 타이틀 -->
	<section id="page-breadcrumb">
		<div class="vertical-center sun">
			<div class="container">
				<div class="row">
					<div class="action">
						<div class="col-sm-12">
							<h2 class="title">신청 현황</h2>
							<p>
								<br>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--배너 타이틀-->
	<div id="accordion-container">
		<div class="panel-group" id="accordion">
			<c:forEach items="${aList }" var="avo">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div align="right">
							<c:if test="${avo.is_selected == 'SELECTED'}">
									선택됨
						</c:if>
						</div>
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapse${avo.id}"> ${avo.id}님의 신청<br>신청물품 : <c:forEach
										items="${avo.pList }" var="pvo">
                                 ${pvo.ptitle }
                                 </c:forEach>
								</a>
							</h4>
					</div>
					<div id="collapse${avo.id}" class="panel-collapse collapse in">
						<div class="panel-body">
							${avo.reason }
							<div align="right">
								<input type="hidden" id="${avo.id }bno" value="${avo.bno }">
								<c:if test="${avo.is_selected != 'SELECTED' & avo.is_done = 'disabled'}">
									<button class='btn btn-sm btn-info' id="selectApp"
										value="${avo.id}">채택</button>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<!-- /#accordion -->
	</div>
	<!-- /#accordion-container -->
</body>
</html>