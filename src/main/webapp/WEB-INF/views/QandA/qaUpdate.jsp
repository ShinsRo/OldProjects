<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#updateBtn").click(function(){ 
    		if($("#title").val()==""){
    			alert("제목을 입력하세요!");
    			return false;
    		}
    		if($("#content").val()==""){
    			alert("본문을 입력하세요!");
    			return false;
    		}
    		$("#update_form").submit();
    	});
    	$("#resetBtn").click(function(){    		
    		$("#write_form")[0].reset();
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
						<h1 class="title">Q&amp;A수정</h1>
						<p>
							<br>Q&amp;A 수정
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->

<!-- 여기는요 수정폼 -->
<sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
<section id="">
   <form enctype="multipart/form-data" id="update_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/updateQuestion.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               <input type="text" name="title" class="form-control"
                  required="required" value="${qvo.title }">
            </div>
            <c:choose>
            	<c:when test="${qvo.is_secret == '1'}">
            		<div><input type="checkbox" name="is_secret" value="1" checked="checked">비밀글</div>
            	</c:when>
            	<c:otherwise>
            		<div><input type="checkbox" name="is_secret" value="0">비밀글</div>
            	</c:otherwise>
            </c:choose>
            
            <div class="form-group" align="center">
            <input type="hidden" name="qno" value="${qvo.qno}">
               <input type="text" name="writer"
                  class="postcodify_address form-control" value="작성자&nbsp;&nbsp;<sec:authentication property="principal.id"/>"
                  readonly="readonly"/>
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15">${qvo.content}</textarea>
            </div>
            <div class="form-group" align="center">
				<input class="btn btn-submit" type="button" id="updateBtn" value="수정하기" > 
            </div>
         </div>
      </div>
   </form>
</section>
</sec:authorize>
<!-- 여기는요 수정폼 -->


