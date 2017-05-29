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
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=title&word=${requestScope.svo.word}">기부목록
									제목으로 검색<span class="pull-right">()</span>
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=addr&word=${requestScope.svo.word}">기부목록
									주소로 검색<span class="pull-right">()</span>
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=id&word=${requestScope.svo.word}">기부목록
									아이디로 검색<span class="pull-right">()</span>
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=title&word=${requestScope.svo.word}">지역후기
									제목으로 검색<span class="pull-right">()</span>
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=addr&word=${requestScope.svo.word}">지역후기
									주소로 검색<span class="pull-right">()</span>
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=id&word=${requestScope.svo.word}">지역후기
									아이디로 검색<span class="pull-right">()</span>
							</a></li>
						</ul>
					</div>


				</div>
			</div>
			<div class="col-md-9 col-sm-8">
				<div class="row">
					<table class="table table-hover" id="commentList">
						<thead>
							<tr>
								<th class="no">NO</th>
								<th class="addr">지역</th>
								<th class="title">제목</th>
								<th class="name">이름</th>
								<th class="date">작성일</th>
								<th class="hit">HIT</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.search.list}" var="cvo">
								<tr>
									<td>${cvo.cno }</td>
									<td>${cvo.addr }</td>
									<td><c:choose>
											<c:when test="${sessionScope.mvo!=null}">
												<a
													href="${pageContext.request.contextPath}/showComment.do?cno=${cvo.cno }">${cvo.title }</a>
											</c:when>
											<c:otherwise>
					${cvo.title }
					</c:otherwise>
										</c:choose></td>
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
							<c:set var="pb" value="${requestScope.search.pagingBean}"></c:set>

							<c:choose>
								<c:when test="${pb.previousPageGroup}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.svo.mcategory}&scategory=${requestScope.svo.scategory}&word=${requestScope.svo.word}&pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
											href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.svo.mcategory}&scategory=${requestScope.svo.scategory}&word=${requestScope.svo.word}&pageNo=${i}">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li class="active"><a href="#">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:choose>
								<c:when test="${pb.nextPageGroup}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.svo.mcategory}&scategory=${requestScope.svo.scategory}&word=${requestScope.svo.word}&pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
								</c:when>
								<c:otherwise>
									<li></li>
								</c:otherwise>
							</c:choose>
						</ul>
						<br> <input class="write_btn" type="button" value="글쓰기"
							onclick="javascript:location.href='${pageContext.request.contextPath}/commentRegisterView.do'">
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
