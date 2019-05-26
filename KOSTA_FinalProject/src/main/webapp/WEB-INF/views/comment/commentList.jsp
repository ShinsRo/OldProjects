<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/_css/dropdown.css">
<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">지역후기</h1>
						<p>
							<br>지역에 거주하며 경험한 후기를 적어주세요
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
				<th class="no" width="5%">NO</th>
				<th class="name" width="30%">제목</th>
				<th class="date" width="10%">아이디</th>
				<th class="hit" width="20%">작성일</th>
				<th class="addr" width="10%">조회수</th>
				<th class="title" width="25%">지역</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.lvo.list}" var="cvo">
				<tr>
					<td>${cvo.cno }</td>
					<td>
							<sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
								<a href="${pageContext.request.contextPath}/showComment.do?cno=${cvo.cno }">${cvo.title }&nbsp;&nbsp;<i class="fa fa-comments"></i>
								&nbsp;${cvo.reply_cnt}</a>
							</sec:authorize> 
							<sec:authorize ifNotGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
								${cvo.title }&nbsp;&nbsp;<i class="fa fa-comments"></i>&nbsp;${cvo.reply_cnt}
						</sec:authorize></td>
					<td>
				<ul class = "nav navbar-nav navbar-left">
					<li class="dropdown">${cvo.id }<i class="fa fa-angle-down"></i>
                            <ul role="menu" class="sub-menu" id="down">
                                <li class="color1"><a href="${pageContext.request.contextPath }/findCommentListById.do?id=${cvo.id }&pageNo=1">이 사람의 후기</a></li>
                                	<li class="color2"><a href="${pageContext.request.contextPath}/BoardListById.do?id=${cvo.id }&pageNo=1">이 사람의 드려요</a></li>
                            </ul>
                        </li>
                     </ul>
                     </td>
					<td>${cvo.time_posted }</td>
					<td>${cvo.hit }</td>
					<td>${cvo.addr }</td>
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
						href="${pageContext.request.contextPath}/getCommentList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
							href="${pageContext.request.contextPath}/getCommentList.do?pageNo=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="active"><a href="#">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${pb.nextPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getCommentList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<br> <br>
	<span style="float:right">
		<sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
			<input  class="btn btn-info" type="button" value="글쓰기"
				onclick="javascript:location.href='${pageContext.request.contextPath}/commentRegisterView.do'">
		</sec:authorize>
	</span>
</div>