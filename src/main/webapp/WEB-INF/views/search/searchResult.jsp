<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<script src="//code.jquery.com/jquery.min.js"></script>
<link href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" rel="Stylesheet"></link>
<!-- <script src="YourJquery source path"></script> -->
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	//검색 자동완성
	$("#keyword").autocomplete({
			source : function( request, response ){		
				$.ajax({
				url:"${pageContext.request.contextPath}/autoSearch.do",
				type: "post",
				dataType : "json",
				data : "keyword=" + $("#keyword").val(),
				success : function(data){
					alert(date.length);
				},
				error : function(data){
					alert("ajax 에러 발생");
				}
			});
				$.getJSON("${pageContext.request.contextPath}/autoSearch.do?keyword="
					+ $("#keyword").val(),function(data){
					response(data);
				});	
		}		
	});
});
</script>
<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<h1 class="title">검색</h1>
							<p>
								<br>다양한 검색어로 찾으세요
							</p>
						</div>
						<div class="col-sm-8 text-center padding wow fadeIn"
						data-wow-duration="1000ms" data-wow-delay="600ms">
					<form action="${pageContext.request.contextPath}/search.do" method="post" role="form">
						<input type="text" class="search-form" id="keyword"  
							placeholder="검색하기" size="80" name="keyword"> <i class=""></i>
							<button type="submit" class="btn btn-info">검색</button>
						<div id="suggest"></div>
					</form>
				</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
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
								<c:when test="${search.type!='btitle'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=title&keyword=${requestScope.search.svo.keyword}">기부목록
											제목으로 검색<span class="pull-right">(${requestScope.search.btitle})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=title&keyword=${requestScope.search.svo.keyword}">기부목록
											제목으로 검색<span class="pull-right">(${requestScope.search.btitle})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${search.type!='baddr'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=addr&keyword=${requestScope.search.svo.keyword}">기부목록
											주소로 검색<span class="pull-right">(${requestScope.search.baddr})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=addr&keyword=${requestScope.search.svo.keyword}">기부목록
											주소로 검색<span class="pull-right">(${requestScope.search.baddr})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${search.type!='bid'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=id&keyword=${requestScope.search.svo.keyword}">기부목록
											아이디로 검색<span class="pull-right">(${requestScope.search.bid})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=board&scategory=id&keyword=${requestScope.search.svo.keyword}">기부목록
											아이디로 검색<span class="pull-right">(${requestScope.search.bid})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${search.type!='ctitle'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=title&keyword=${requestScope.search.svo.keyword}">지역후기
											제목으로 검색<span class="pull-right">(${requestScope.search.ctitle})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=title&keyword=${requestScope.search.svo.keyword}">지역후기
											제목으로 검색<span class="pull-right">(${requestScope.search.ctitle})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${search.type!='caddr'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=addr&keyword=${requestScope.search.svo.keyword}">지역후기
											주소로 검색<span class="pull-right">(${requestScope.search.caddr})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=addr&keyword=${requestScope.search.svo.keyword}">지역후기
											주소로 검색<span class="pull-right">(${requestScope.search.caddr})</span>
									</a></li>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${search.type!='cid'}">
									<li><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=id&keyword=${requestScope.search.svo.keyword}">지역후기
											아이디로 검색<span class="pull-right">(${requestScope.search.cid})</span>
									</a></li>
								</c:when>
								<c:otherwise>
									<li class="active"><a
										href="${pageContext.request.contextPath}/search.do?mcategory=comment&scategory=id&keyword=${requestScope.search.svo.keyword}">지역후기
											아이디로 검색<span class="pull-right">(${requestScope.search.cid})</span>
									</a></li>
								</c:otherwise>
							</c:choose>
							<!-- 검색 조건 -->
						</ul>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${requestScope.search.mcategory=='board'}">
					<div class="col-md-9 col-sm-9">
					<!-- board -->
					<c:forEach items="${requestScope.search.search.list}" var="list">
						<div
							class="col-xs-6 col-sm-4 col-md-4 portfolio-item branded logos">
							<div class="portfolio-wrapper">
								<div class="portfolio-single">
									<div class="portfolio-thumb">
										<%-- "${pageContext.request.contextPath }/resources/images/portfolio/1.jpg" --%>
										<img
											src="${pageContext.request.contextPath}/${list.thumbPath}"
											class="img-responsive" alt="">
									</div>
									<div class="portfolio-view">
										<ul class="nav nav-pills">
											<li><a
												href="${pageContext.request.contextPath}/boardDetailHit.do?bno=${list.bno}"><i
													class="fa fa-link"></i></a></li>
											<li><a
												href="${pageContext.request.contextPath}/${list.thumbPath}"
												data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
										</ul>
									</div>
								</div>
								<div class="portfolio-info">
									<h2 style="text-overflow: ellipsis; overflow: hidden;">${list.title}</h2>
									<h3>${list.addr}</h3>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- board -->
					</div>
				</c:when>
				<c:otherwise>
					<!-- comment -->
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
									<c:forEach items="${requestScope.search.search.list}" var="cvo">
										<tr>
											<td>${cvo.cno }</td>
											<td>${cvo.addr }</td>
											<td>
						                         <sec:authorize ifNotGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">            
						                         	${cvo.title }         
						                       	</sec:authorize>
						                       	<sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">            
													<a href="${pageContext.request.contextPath}/showComment.do?cno=${cvo.cno }">${cvo.title }</a>
						                       	</sec:authorize>
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
									<c:set var="pb" value="${requestScope.search.search.pagingBean}"></c:set>

									<c:choose>
										<c:when test="${pb.previousPageGroup}">
											<li><a
												href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.search.svo.mcategory}&scategory=${requestScope.search.svo.scategory}&word=${requestScope.search.svo.keyword}&pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
													href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.search.svo.mcategory}&scategory=${requestScope.search.svo.scategory}&word=${requestScope.search.svo.keyword}&pageNo=${i}">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li class="active"><a href="#">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>

									<c:choose>
										<c:when test="${pb.nextPageGroup}">
											<li><a
												href="${pageContext.request.contextPath}/search.do?mcategory=${requestScope.search.svo.mcategory}&scategory=${requestScope.search.svo.scategory}&word=${requestScope.search.svo.keyword}&pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
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
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</section>
