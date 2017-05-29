<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover" id="commentList">
	<thead>
		<tr>
			<th class="no">NO</th>
			<th class="title">지역</th>
			<th class="name">제목</th>
			<th class="date">아이디</th>
			<th class="hit">작성일</th>
			<th class="addr">조회수</th>
		</tr>
	</thead>
	<tbody>			
<c:forEach items="${requestScope.lvo.list}" var="cvo" >				
			<tr>
			    <td>${cvo.cno }</td>		
			    <td>${cvo.addr }</td>	
				<td>
			
					<c:choose>
					<c:when test="${sessionScope.mvo==null}">
					<a href="${pageContext.request.contextPath}/showComment.do?cno=${cvo.cno }">${cvo.title }</a>
					</c:when>
					<c:otherwise>
					${cvo.title }
					</c:otherwise>
					</c:choose>
					</td>
					<td>${cvo.id }</td>
					<td>${cvo.time_posted }</td>
					<td>${cvo.hit }</td>
			</tr>	
		</c:forEach>
			
		</tbody>
</table>
<br></br>

<div class="portfolio-pagination">
	<ul class="pagination">
		<c:set var="pb" value="${requestScope.lvo.pagingBean}"></c:set>

		<c:if test="${pb.previousPageGroup}">
			<li><a
				href="${pageContext.request.contextPath}/getCommentList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
		</c:if>
		<li><a href="#">left</a></li>
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
		<c:if test="${pb.nextPageGroup}">
			<li><a
				href="${pageContext.request.contextPath}/getCommentList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
		</c:if>
		<li><a href="#">right</a></li>
	</ul>
</div>

<br>
<br>
<c:if test="${sessionScope.mvo.id != null }">
	<input class="write_btn" type="button" value="글쓰기"
		onclick="javascript:location.href='${pageContext.request.contextPath}/commentRegisterView.do'">
</c:if>
