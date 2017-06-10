<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

function getApp(bno) {
	var appPopURL = "${pageContext.request.contextPath}/getApplications.do?bno="+bno;
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
					<c:if test="${!empty requestScope.targetId}">
						<h1 class="title">${requestScope.targetId}이 올린 드려요</h1>						
					</c:if>
					<c:if test="${empty requestScope.targetId}">
						<h1 class="title">내가 올린 드려요</h1>
					</c:if>
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
<div class="container">
<table class="table table-hover" id="my_board">
	<thead>
		<tr>
			<th class="no">NO</th>
			<th class="title">제목</th>
			<th class="date">올린일</th>
			<th class="hit">조회수</th>
			<th class="addr">신청현황</th>
		</tr>
	</thead>
	<tbody>
			<c:forEach items="${requestScope.blvo.list}" var="bvo" >				
			<tr>
			    <td>${bvo.bno }</td>
				<td>
					<c:choose>
					<c:when test="${sessionScope.mvo!=null}">
						<a href="${pageContext.request.contextPath}/boardDetail.do?bno=${bvo.bno}">${bvo.title }</a>
					</c:when>
					<c:otherwise>
						${bvo.title }
					</c:otherwise>
					</c:choose>
					</td>
					<td>${bvo.time_posted }</td>
					<td>${bvo.hit }</td>
					<td>${bvo.is_traded }&nbsp;&nbsp;&nbsp;
					<c:if test="${bvo.is_traded == 'WAITING' }">
					<button class = "btn btn-sm btn-info" value = "${bvo.bno}" id = "showApps" onclick="getApp(${bvo.bno})">
					신청현황보기
					</button>
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
					<li><a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
	</ul>
</div>

<br>
<br>
<span style="float:right">
<c:if test="${sessionScope.mvo.id != null }">
	<input type="button" value="글쓰기"  class="btn btn-info"
		onclick="javascript:location.href='${pageContext.request.contextPath}/boardRegisterView.do'">
</c:if>
</span>
</div>