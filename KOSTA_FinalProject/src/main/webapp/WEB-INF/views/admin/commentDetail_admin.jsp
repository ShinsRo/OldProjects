<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	function sendList(){
		location.href="getCommentList_admin.do";
	}
	function updateComment(){
		if(confirm("수정하시겠습니까?")){
			location.href="commentUpdateView_admin.do?cno="+${requestScope.cvo.cno};
		}else{
			return false;
		}
	}
	function deleteComment(){
		if(confirm("삭제하시겠습니까?")){
			location.href="deleteComment_admin.do?cno="+${requestScope.cvo.cno};
		}else{
			return false;
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
			location.href ="deleteCommentReply_admin.do?rno="+rno+"&cno="+cno;
		}else{
			return;
		}
	}
	//댓글 수정버튼 클릭시 수정창 나타나게 하기
	var updaterno = updatecontent = null;
	function fn_replyUpdate(rno){
	    var form = document.replyUpdateForm;
	    var reply = document.getElementById("reply"+rno);
	    var replyDiv = document.getElementById("replyDiv");
	    replyDiv.style.display = "";
	    if (updaterno) {
	        document.body.appendChild(replyDiv);
	        var oldrno = document.getElementById("reply"+updaterno);
	        oldrno.innerText = updatecontent;
	    } 
	    
	    form.rno.value=rno;
	    form.content.value = reply.innerText;
	    reply.innerText ="";
	    reply.appendChild(replyDiv);
	    updaterno   = rno;
	    updatecontent = form.content.value;
	    form.content.focus(); 
	} 

	// 댓글 수정버튼->저장 누를때 
	function fn_replyUpdateSave(){
	    var form = document.replyUpdateForm;
	    var rno = document.replyUpdateForm.rno;
	    var cno = document.replyUpdateForm.cno;
	    if (form.content.value=="") {
	        alert("글 내용을 입력해주세요.");
	        form.content.focus();
	        return;
	    }
		if(confirm("댓글을 수정하시겠습니까?")){
			form.action="updateCommentReply_admin.do";
			form.submit();
		}
	    //form.action="updateCommentReply.do?rno="+rno+"&cno="+cno+"&content="+form.content.value;
	    //form.submit();     
	} 
	// 댓글 수정하고 취소 누르기
	function fn_replyUpdateCancel(){
	    var form = document.replyUpdateForm;
	    var replyDiv = document.getElementById("replyDiv");
	    document.body.appendChild(replyDiv);
	    replyDiv.style.display = "none";
	    
	    var oldrno = document.getElementById("reply"+updaterno);
	    oldrno.innerText = updatecontent;
	    updaterno = updatecontent = null; 
	} 
	
	
	
	// 대댓글
	function hideDiv(id){
	    var div = document.getElementById(id);
	    div.style.display = "none";
	    document.body.appendChild(div);
	}

	function fn_replyReply(rno){
	    var form = document.form3;
	    var reply = document.getElementById("reply"+rno);
	    var replyDia = document.getElementById("replyDialog");
	    replyDia.style.display = "";
	    
	    if (updaterno) {
	        fn_replyUpdateCancel();
	    } 
	    
	    form.content.value = "";
	    form.parent.value=rno;
	    reply.appendChild(replyDia);

	} 
	
	
	function fn_replyReplyCancel(){
	    hideDiv("replyDialog");
	} 
	function fn_replyReplySave(){
	    var form = document.form3;
	    var param = form.parent.value
	    if (form.content.value=="") {
	        alert("글 내용을 입력해주세요.");
	        form.content.focus();
	        return;
	    }
	    form.action="writeCommentReply2_admin.do";
	    form.submit();    
	}
</script>

<section id="">
   <form enctype="multipart/form-data" id="write_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentRegister.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               NO : ${requestScope.cvo.cno}&nbsp;&nbsp;&nbsp;&nbsp;제목 : ${requestScope.cvo.title}
            </div>
            <div class="form-group" align="center">
            작성자 : ${requestScope.cvo.id}&nbsp;&nbsp;&nbsp;&nbsp;
			작성일 : ${requestScope.cvo.time_posted}&nbsp;&nbsp;&nbsp;&nbsp;
			조회수 : ${requestScope.cvo.hit}&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15" readonly="readonly">${requestScope.cvo.content}</textarea>
            </div>
            <div class="form-group" align="center">
			<input class="btn btn-info" type="button" value="목록" onclick="sendList()" >
			<sec:authorize ifAnyGranted="ROLE_ADMIN">
			 <input class="btn btn-info" type="button" value="수정" onclick="updateComment()">
			 <input class="btn btn-danger" type="button" value="삭제" onclick="deleteComment()">  
			</sec:authorize>
            </div>
         </div>
      </div>
   </form>
</section>

<!-- 댓글구간 -->
<div class="container">
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
		<div class="replyList" >
			<div class="form-group" align="center">
				<form name="replyWriteForm" action="writeCommentReply1_admin.do" method="post">
					<ul class="nav navbar-nav navbar-default" id="reply_ul">
						<li><input type="hidden" name="parent" value="0">
						<input type="hidden" name="cno" value="${requestScope.cvo.cno}">
						<input type="hidden" name="id" value="<sec:authentication property="principal.id"/>">
						<input type="hidden" name="name" value="<sec:authentication property="principal.name"/>">
						<input type="hidden" name="gno" value="1">
						<input type="hidden" name="depth" value="0">
						<input type="hidden" name="order_no" value="1">
						<textarea class="reply_field" id="content" name="content" rows="3" cols="130"
						placeholder="댓글을 달아주세요."></textarea></li>
						<li><input type="button" id="writeReplyBtn" class="btn btn-lg btn-info" value="등록" onclick="fn_formSubmit()"></li>
					</ul>
				</form>
			</div>
		</div>
	</sec:authorize>

	<!-- 댓글 리스트 -->
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
	<div class="box_reply">
	<div class="form-group" align="center">
		<ul class="cmlist">
			<c:forEach items="${requestScope.CommentReplyList}" var="reply">
				<li>
				<div class="col-md-10 col-sm-10" align="left">
					<span class="nickspan">
					<c:if test="${reply.depth >=1}">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<img class="reply_icon" src="${pageContext.request.contextPath}/resources/img/reply_icon.png" width="20">
						</c:if>${reply.id}</span>
						<span class="cmdate">${reply.time_posted}</span>
						<span class="recmbtn">
						<a onclick="fn_replyReply(${reply.rno})">
				 		답글</a></span> 
						<span class="cmbtn">
						<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<a onclick="fn_replyDelete(${reply.rno },${requestScope.cvo.cno})">삭제</a>
						<a onclick="fn_replyUpdate(${reply.rno })">수정</a>
						</sec:authorize></span>
				</div>
				<!-- class="cm_list_box" -->
				<div class="col-md-10 col-sm-10" align="left" id="reply<c:out value="${reply.rno}"/>">
					<c:out value="${reply.content }" />
				</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	</div>
	</sec:authorize>
	<!--  댓글 수정시 나오는 부분 -->
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
	<div id="replyDiv" style="width: 99%; display: none">
		<form name="replyUpdateForm" action="updateCommentReply.do" method="post">
			<input type="hidden" name="cno" value="${requestScope.cvo.cno}">
			 <input type="hidden" name="rno">
			<textarea class="reply_field" name="content" rows="3" cols="60" style="border:solid 1px #D8D8D8;
			maxlength="500"></textarea>
			<a onclick="fn_replyUpdateSave()">저장</a>
			<a onclick="fn_replyUpdateCancel()">취소</a>
		</form>
	</div>
	<!--  대댓글 창 -->
	<div id="replyDialog" style="width: 99%; display: none">
		<form name="form3" action="writeCommentReply2_admin.do" method="post">
			<input type="hidden" name="cno" value="<c:out value="${requestScope.cvo.cno}"/>"> 
			<input type="hidden" name="parent">
			<input type="hidden" name="id" value="<sec:authentication property="principal.id"/>">
			<input type="hidden" name="name" value="<sec:authentication property="principal.name"/>">
			<textarea class="reply_field" name="content" rows="3" cols="60" maxlength="500" style="border:solid 1px #D8D8D8;
			margin-left:10px;"></textarea>
			<a onclick="fn_replyReplySave()">저장</a>
			<a onclick="fn_replyReplyCancel()">취소</a>
			</form>
	</div>
	</sec:authorize>
</div>
