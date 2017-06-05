<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
		function convertIt(id, bno, currState) {
			location.href="${pageContext.request.contextPath}/convertDoneState.do?bno="+bno+"&id="+id+"&currState="+currState;
		}
</script>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">'주세요' 신청 현황</h1>
						<p>나의 주세요 신청 현황을 볼 수 있습니다.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="container">
	<table class="table table-hover" id="my_board">
		<thead>
			<tr>
				<th>NO</th>
				<th>상품번호</th>
				<th>신청사유</th>
				<th>신청현황</th>
				<th>용달 서비스 신청</th>
				<th>수령 여부</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.appList}" var="apl">
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/boardDetail.do?bno=${apl.bno}">${apl.bno}</a></td>
					<td>${apl.pnos}</td>
					<td>${apl.reason}</td>
					<td>${apl.is_selected}</td>
					<td>${apl.is_delivery}</td>
					<td>
					<c:if test = "${apl.is_selected == 'SELECTED' }">
						<c:if test="${apl.is_done == 'NO' }">
						미수령 &nbsp; 
						<button class = "btn btn-sm btn-success" onclick="convertIt('${apl.id}', '${apl.bno}', '${apl.is_done}')">
						수령 완료</button>
						</c:if>
						<c:if test = "${apl.is_done == 'YES' }">
						수령 완료 &nbsp; 
						<button class ="btn btn-sm btn-warning" onclick="convertIt('${apl.id}', '${apl.bno}', '${apl.is_done}')">
						수령 취소</button>
						</c:if>
					</c:if>
					<c:if test="${apl.is_selected == 'WATING' }">
						승인 대기중
					</c:if>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<br></br>

	<div class="portfolio-pagination">
		<ul class="pagination">
			<c:set var="pb" value="${requestScope.blvo.pagingBean}"></c:set>
			<c:choose>
				<c:when test="${pb.previousPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>

			<c:forEach var="i" begin="${pb.startPageOfPageGroup}"
				end="${pb.endPageOfPageGroup}">
				<c:choose>
					<c:when test="${pb.nowPage!=i}">
						<li><a
							href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="active"><a href="#">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${pb.nextPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<br> <br>
</div>