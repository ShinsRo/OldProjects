<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
/* 	function updateMember(){
		if(confirm("수정하시겠습니까?")){
			location.href="updateMember_admin.do";
		}else{
			return false;
		}
	}
	function deleteMember(id){
		if(confirm("삭제하시겠습니까?")){
			alert($("#mem_id").val());
			location.href="deleteMember_admin.do?id="+$("#mem_id").val();
		}else{
			return false;
		}
	} */
</script>

<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원관리</h1>
						<p>
							<br>착한이사 회원들의 정보관리
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
				<th class="id">아이디</th>
				<th class="name">이름</th>
				<th class="password">비밀번호</th>
				<th class="addr">주소</th>
				<th class="addr_detail">상세주소</th>
				<th class="tel">전화번호</th>
				<th class="job">직업</th>
				<th class="deletemember">활동회원여부</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.lvo.list}" var="cvo">
				<tr>
					<td><input type="hidden" id="mem_id" value="${cvo.id}">${cvo.id}</td>
					<td>${cvo.name}</td>
					<td>${cvo.password}</td>
					<td>${cvo.addr }</td>
					<td>${cvo.addr_detail}</td>
					<td>${cvo.tel}</td>
					<td>${cvo.job}</td>
					<td>${cvo.deletemember}</td>
					<td><a href="updateMember_admin.do?id=${cvo.id}">수정</a>
					<td><a href="deleteMember_admin.do?id=${cvo.id}">삭제</a>
					<c:if test="${cvo.deletemember ==false}">
					<td><a href="restoreMember_admin.do?id=${cvo.id}">복구</a>
					</c:if>
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
						href="${pageContext.request.contextPath}/getMemberList_admin.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
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
							href="${pageContext.request.contextPath}/getMemberList_admin.do?pageNo=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="active"><a href="#">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:choose>
				<c:when test="${pb.nextPageGroup}">
					<li><a
						href="${pageContext.request.contextPath}/getMemberList_admin.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>
