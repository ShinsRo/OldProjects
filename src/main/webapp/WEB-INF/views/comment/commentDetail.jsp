<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function sendList(){
		location.href="getCommentList.do";
	}
	function updateComment(){
		if(confirm("수정하시겠습니까?")){
			location.href="commentUpdateView.do?cno="+${requestScope.cvo.cno};
		}else{
			return false;
		}
	}
	function deleteComment(){
		if(confirm("삭제하시겠습니까?")){
			location.href="";
		}else{
			return false;
		}
	}
	function fn_formSubmit() {
		var f = document.replyWriteForm;
		if($("#rememo").val()==""){
			alert("댓글을 입력해주세요!");
			return;
		}else{
			f.submit();
		}
	}
</script>
<table id="inputForm" class="table table-hover">
	<tbody>
		<tr>
			<td>NO : ${requestScope.cvo.cno}</td>
			<td colspan="2">${requestScope.cvo.title}</td>
		</tr>
		<tr>
			<td>작성자 : ${requestScope.cvo.id}</td>
			<td>${requestScope.cvo.time_posted}</td>
			<td>조회수 : ${requestScope.cvo.hit}</td>
		</tr>
		<tr>
			<td colspan="3"><pre>${requestScope.cvo.content}</pre></td>
		</tr>
		<tr>
			<td valign="middle" align="center" colspan="3">
			 <input class="update_btn" type="button" value="목록" onclick="sendList()" >
			 <input class="update_btn" type="button" value="수정" onclick="updateComment()">
			 <input class="update_btn" type="button" value="삭제" onclick="deleteComment()">
			 <br><br>			
			 </td>
		</tr>
	</tbody>
</table>
<!-- 댓글구간 -->
<div class="reply_container">
	<%-- <c:if test="${sessionScope.mvo != null}"> --%>
		<div class="replyList">
			<form name="replyWriteForm" action="writeCommentReply.do" method="post">
				<input type="hidden" name="parent" value="0">
				<input type="hidden" name="reFlag" value="false">
				<input type="hidden" name="cno" value="${requestScope.cvo.cno}">
				<textarea class="reply_field" id="rememo" name="rememo"
				placeholder="댓글을 달아주세요." style="border:solid 3px #ffd100;"></textarea>
				<input type="button" id="writeReplyBtn" value="등록" onclick="fn_formSubmit()">
			</form>
		</div>
	<%-- </c:if> --%>

	<!-- 댓글 리스트 -->
	<div class="box_reply">
		<ul class="cmlist">
			<c:forEach items="${requestScope.CommentReplyList}" var="reply">
				<li>
				<div class="h">
					<span class="nickspan"> <c:if test="${reply.depth >=1 }">&nbsp;&nbsp;&nbsp;&nbsp;
						<img class="reply_icon" src="${pageContext.request.contextPath}/img/reply_icon.png" width="20">
						</c:if>${reply.name}</span> <span class="cmdate">${reply.time_posted }</span>
						<span class="recmbtn">
						<a href="#"	onclick="fn_replyReply(${reply.id })">
				 		<img class="reply_icon" src="${pageContext.request.contextPath}/img/bu_arr.png">답글 </a></span> 
						<span class="cmbtn">
						<c:if test="${sessionScope.mvo.id==reply.id}">
						<a href="#"
					    onclick="fn_replyDelete(${reply.id },${requestScope.cvo.cno})">삭제</a>
						<a href="#" onclick="fn_replyUpdate(${reply.reply_id })">수정</a>
						</c:if></span>
				</div>
				<div class="cm_list_box" id="reply<c:out value="${reply.id}"/>">
					<c:out value="${reply.content }" />
				</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>