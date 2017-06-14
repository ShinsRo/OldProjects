<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/_css/dropdown.css">
<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">Q&amp;A</h1>
						<p>
							<br>Q&amp;A
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
<div class="container">
	<table class="table table-hover" id="commentList">
		<thead>
			<tr>
				<th class="no">NO</th>
				<th class="name">제목</th>
				<th class="date">아이디</th>
				<th class="hit">작성일</th>
				<th class="addr">조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.lvo.list}" var="qvo">
				<tr>
					<td>${qvo.qno}</td>
					<!-- 비밀글 -->
					<c:choose>
						<c:when test="${qvo.is_secret == '1' && qvo.id==mvo.id}">
							<td>
							<c:if test="${qvo.re_lev > 1}">
								<c:forEach begin="1" end="${qvo.re_lev}">
								&nbsp;&nbsp; <!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
								</c:forEach>
								RE : 
							</c:if>
							<img src="${pageContext.request.contextPath}/resources/img/secret.png" style="width:20px">&nbsp;&nbsp;<a
							href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a></td>
						</c:when>
						<c:when test="${qvo.is_secret == '1' && mvo.id == 'admin'}">
							<td>
							<c:if test="${qvo.re_lev > 1}">
								<c:forEach begin="1" end="${qvo.re_lev}">
								&nbsp;&nbsp; <!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
								</c:forEach>
								RE : 
							</c:if>
							<img src="${pageContext.request.contextPath}/resources/img/secret.png" style="width:20px">&nbsp;&nbsp;<a
							href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a></td>
						</c:when>
						<c:when test="${qvo.is_secret == '1' && qvo.id!=mvo.id}">
							<td>
							<c:if test="${qvo.re_lev > 1}">
								<c:forEach begin="1" end="${qvo.re_lev}">
								&nbsp;&nbsp; <!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
								</c:forEach>
								RE : 
							</c:if>
							<img src="${pageContext.request.contextPath}/resources/img/secret.png" style="width:20px">&nbsp;&nbsp;${qvo.title}</td>
						</c:when>
						<c:otherwise>
							<td><c:if test="${qvo.re_lev > 1}">
								<c:forEach begin="1" end="${qvo.re_lev}">
								&nbsp;&nbsp; <!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
								</c:forEach>
								RE : 
							</c:if>
							<a
							href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a></td>
						</c:otherwise>
					</c:choose>
					<td>${qvo.id}</td>
					<td>${qvo.time_posted }</td>
					<td>${qvo.hit}</td>
				</tr>
			</c:forEach>

		</tbody> 
	</table>
	<br></br>

	<div class="portfolio-pagination">
		<ul class="pagination">
			<c:set var="pb" value="${requestScope.lvo.pagingBean}"></c:set>

			<c:choose>
				<c:when test="${pb.previousPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
							href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="active"><a href="#">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${pb.nextPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<br> <br>
	<span style="float:right">
		<c:if test="${sessionScope.mvo.id != null }">
			<input  class="btn btn-info" type="button" value="글쓰기"
				onclick="javascript:location.href='${pageContext.request.contextPath}/registerQuestionView.do'">
		</c:if>
	</span>
</div>
