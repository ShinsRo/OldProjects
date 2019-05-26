<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section id="projects" class="padding-top">
	<div class="container">
		<div class="row">
			<div class="col-md-3 col-sm-4">
				<div class="sidebar portfolio-sidebar">
					<div class="sidebar-item categories">
						<h3>Project Categories</h3>
						<ul class="nav navbar-stacked">
							<!-- 검색 조건 -->
							<c:choose>
								<c:when test="${type!='board'}">
									<li><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=board">기부목록
											신고내역<span class="pull-right">(${requestScope.count.reportBoardCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=board">기부목록
											신고내역<span class="pull-right">(${requestScope.count.reportBoardCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${type!='comment'}">
									<li><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=comment">지역후기
											신고내역<span class="pull-right">(${requestScope.count.reportCommentCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=comment">지역후기
											신고내역<span class="pull-right">(${requestScope.count.reportCommentCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${type!='reply'}">
									<li><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=reply">지역후기 댓글
											신고내역<span class="pull-right">(${requestScope.count.reportReplyCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=reply">지역후기 댓글
											신고내역<span class="pull-right">(${requestScope.count.reportReplyCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${type!='boardAll'}">
									<li><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=board">기부목록
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllBoardCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=board">기부목록
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllBoardCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${type!='commentAll'}">
									<li><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=comment">지역후기
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllCommentCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=comment">지역후기
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllCommentCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${type!='replyAll'}">
									<li><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=reply">지역후기 댓글
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllReplyCount})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/getAllReportList_admin.do?category=reply">지역후기 댓글
											전체 신고내역<span class="pull-right">(${requestScope.count.reportAllReplyCount})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<!-- 검색 조건 -->
						</ul>
					</div>
				</div>
			</div>


			<!-- comment -->
			<div class="col-md-9 col-sm-8">
				<div class="row">
					<table class="table table-hover" id="commentList">
						<thead>
							<tr>
								<th class="report_no">신고번호</th>
								<th class="category">분류</th>
								<th class="reno">글번호</th>
								<th class="id">신고된 아이디</th>
								<th class="reporter">신고자</th>
								<th class="why">신고사유</th>
								<th class="time_posted">신고일시</th>
								<th class="process">처리상태</th>
								<th class="process">처리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.lvo.list}" var="rvo">
								<tr>
									<td>${rvo.report_no }</td>
									<td>${rvo.category }</td>
									<td><a href="${pageContext.request.contextPath}/showReport_admin.do?category=${rvo.category }&reno=${rvo.reno }&report_no=${rvo.report_no}&type=${type}&pageNo=${requestScope.lvo.pagingBean.nowPage}">${rvo.reno }</a></td>
									<td>${rvo.id }</td>
									<td>${rvo.reporter }</td>
									<td>${rvo.why }</td>
									<td>${rvo.time_posted }</td>
									<td>${rvo.process }</td>
									<td><a href="${pageContext.request.contextPath}/deleteReport_admin.do?category=${rvo.category }&reno=${rvo.reno }&report_no=${rvo.report_no}&type=${type}&pageNo=${requestScope.lvo.pagingBean.nowPage}">삭제</a>
									<a href="${pageContext.request.contextPath}/rejectReport_admin.do?category=${rvo.category }&reno=${rvo.reno }&report_no=${rvo.report_no}&type=${type}&pageNo=${requestScope.lvo.pagingBean.nowPage}">거부</a>
									</td>
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
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=${requestScope.lvo.category}&pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
											href="${pageContext.request.contextPath}/getReportList_admin.do?category=${requestScope.lvo.category}&pageNo=${i}">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li class="active"><a href="#">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:choose>
								<c:when test="${pb.nextPageGroup}">
									<li><a
										href="${pageContext.request.contextPath}/getReportList_admin.do?category=${requestScope.lvo.category}&pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
								</c:when>
								<c:otherwise>
									<li></li>
								</c:otherwise>
							</c:choose>
						</ul>
						<br>

					</div>
				</div>
			</div>
			<!-- comment -->

		</div>
	</div>
</section>
