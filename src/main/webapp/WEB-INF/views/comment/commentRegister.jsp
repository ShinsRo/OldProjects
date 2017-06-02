<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
       $("#writeBtn").click(function(){ 
          if($("#title").val()==""){
             alert("제목을 입력하세요!");
          }else if($("#content").val()==""){
             alert("본문을 입력하세요!");
          }else{
             $("#write_form").submit();
          }
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
                  <h1 class="title">여기는요 작성하기</h1>
                  <p>
                     <br>지역에 거주하며 경험한 후기를 적어주세요
                  </p>
               </div>
            </div>
         </div>
      </div>
   </div>
</section>
<!--배너 타이틀-->

<!-- 여기는요 작성폼 -->
<section id="">
   <form enctype="multipart/form-data" id="write_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentRegister.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               <input type="text" name="title" class="form-control"
                  required="required" placeholder="제목">
            </div>
            <div class="form-group" align="center">
               <input type="text" name="writer"
                  class="postcodify_address form-control" value="작성자&nbsp;&nbsp;${sessionScope.mvo.name}"
                  readonly="readonly"/>
                  <input type="text" name="addr"
                  class="postcodify_address form-control" value="${requestScope.add}"
                  />
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15" placeholder="글을 작성해주세요."></textarea>
            </div>
            <div class="form-group" align="center">

                     <input class="btn btn-submit" type="button" id="writeBtn" value="등록" >
                     <input class="btn btn-submit" type="button" id="resetBtn"  value="리셋" >    
            </div>
         </div>
      </div>
   </form>
</section>
<!-- 여기는요 작성폼 -->