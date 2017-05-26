<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<a href="commentUpdateView.do?cno=${cvo.cno }">수정하기</a>
