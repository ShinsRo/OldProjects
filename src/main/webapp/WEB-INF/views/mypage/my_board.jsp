<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<a href="${pageContext.request.contextPath}/boardDetail.do?bno=${bvo.bno }">${bvo.title }</a>
					</c:when>
					<c:otherwise>
						${bvo.title }
					</c:otherwise>
					</c:choose>
					</td>
					<td>${bvo.time_posted }</td>
					<td>${bvo.hit }</td>
					<td>신청현황</td>
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
<c:if test="${sessionScope.mvo.id != null }">
	<input class="write_btn" type="button" value="글쓰기"
		onclick="javascript:location.href='${pageContext.request.contextPath}/boardRegisterView.do'">
</c:if>