<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

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

<!-- 여기는요 수정폼 -->
<section id="">
   <form enctype="multipart/form-data" id="update_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentUpdate_admin.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               <input type="text" name="title" class="form-control"
                  required="required" value="${cvo.title }">
            </div>
            <div class="form-group" align="center">
            <input type="hidden" name="cno" value="${cvo.cno}">
               <input type="text" name="writer"
                  class="postcodify_address form-control" value="작성자&nbsp;&nbsp;${cvo.id}"
                  readonly="readonly"/>
                  <input type="text" name="addr"
                  class="postcodify_address form-control" value="${cvo.addr}"
                  />
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15">${cvo.content }</textarea>
            </div>
            <div class="form-group" align="center">
				<input class="btn btn-submit" type="button" id="updateBtn" value="수정하기" > 
            </div>
         </div>
      </div>
   </form>
</section>
<!-- 여기는요 수정폼 -->


