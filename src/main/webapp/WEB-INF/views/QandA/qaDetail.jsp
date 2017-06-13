<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function QuestionList(){
		location.href= "getQuestionList.do";
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
			location.href="deleteComment.do?cno="+${requestScope.cvo.cno};
		}else{
			return false;
		}
	}
 	function reportComment(){
 		if(confirm("신고하시겠습니까?")){
 			$("#reportComment").modal();
		}else{
			return;
		}
 	}
	function fn_formSubmit() {
		var f = document.replyWriteForm;
		if($("#content").val()==""){
			alert("댓글을 입력해주세요!");
			return;
		}else{
			f.submit();
		}
	}
	function fn_replyDelete(rno,cno){
		if(confirm("삭제하시겠습니까?")){
			location.href ="deleteCommentReply.do?rno="+rno+"&cno="+cno;
		}else{
			return;
		}
	}
 	function fn_replyReport(){
 		if(confirm("신고하시겠습니까?")){
 			$("#replyReport").modal();
		}else{
			return;
		}
 	}

</script>

<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">Q&amp;A</h1>
						<p>
							<br>Q&amp;A detail
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
<section id="">
   <form enctype="multipart/form-data" id="write_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentRegister.do">
      <div class="col-md-1 col-sm-1" align="center"></div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               NO : ${requestScope.qvo.qno}&nbsp;&nbsp;&nbsp;&nbsp;제목 : ${requestScope.qvo.title}
            </div>
            <div class="form-group" align="center">
            작성자 : ${requestScope.qvo.id}&nbsp;&nbsp;&nbsp;&nbsp;
			작성일 : ${requestScope.qvo.time_posted}&nbsp;&nbsp;&nbsp;&nbsp;
			조회수 : ${requestScope.qvo.hit}&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15" readonly="readonly">${requestScope.qvo.content}</textarea>
            </div>
            <div class="form-group" align="center">
			<input class="btn btn-info" type="button" value="목록" onclick="QuestionList()">
			<c:if test="${requestScope.qvo.id==sessionScope.mvo.id}">
			 <input class="btn btn-info" type="button" value="수정" onclick="updateComment()">
			 <input class="btn btn-danger" type="button" value="삭제" onclick="deleteComment()">  
			 </c:if>
			 <c:if test="${sessionScope.mvo.id == 'admin'}">
			 	 <input class="btn btn-danger" type="button" value="답글" onclick=""> 
			 </c:if>
            </div>
         </div>
      </div>
   </form>
</section>
