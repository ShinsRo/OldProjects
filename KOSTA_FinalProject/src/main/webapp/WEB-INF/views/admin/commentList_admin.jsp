<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
    	$('#checkAll').click(function(){
    		if($("#checkAll").is(":checked")){
    			$('input:checkbox[name="check"]').each(function() {
    			      this.checked = true; //checked 처리
    			 });
        	}else{
        		$('input:checkbox[name="check"]').each(function() {
  			      this.checked = false; //checked 처리
  			 });
        	}
    	})
    	
    	
       $("#deleteCheck").click(function(){ 
    	   $('#check:checked').each(function() { 
    		   $.ajax({
    				type:"post",
    				url:"deleteCheck_admin.do",
    				dataType:"json",
    				data:"cno="+$(this).val(),
    				success:function(data){	
    				}
    			})
    	   });
    	   location.href = "${pageContext.request.contextPath}/getCommentList_admin.do";
       });
    });   
</script>
<div class="container">
	<table class="table table-hover" id="commentList">
		<thead>
			<tr>
				<th class="check"><input type="checkbox" id="checkAll">전체선택/해제</th>
				<th class="no">NO</th>
				<th class="name">제목</th>
				<th class="date">아이디</th>
				<th class="hit">작성일</th>
				<th class="addr">조회수</th>
				<th class="title">지역</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.lvo.list}" var="cvo">
				<tr>
				<td><input type="checkbox" name="check" id="check" value="${cvo.cno}"></td>
					<td>${cvo.cno }</td>
					<td>
						<a href="${pageContext.request.contextPath}/showComment_admin.do?cno=${cvo.cno }">${cvo.title }</a>
					</td>
					<td>${cvo.id }</td>
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
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
			<input  class="btn btn-info" type="button" id="deleteCheck" value="선택삭제">
		</sec:authorize>
	</span>
</div>
