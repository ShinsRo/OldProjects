<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글쓰기</title>

<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<script type="text/javascript">
function content_submit(){
   var f=document.write_form;
   if(f.title.value==""){
      alert("제목을 입력하세요!");
      f.title.focus();
      return; 
   }
  
   if(f.content.value==""){
      alert("내용을 입력하세요!");
      f.content.focus();
      return;
   }
   f.submit();
}
function cancel(){
   location.href="${pageContext.request.contextPath}/DispatcherServlet?command=board";
}
</script>
</head>
<body>
<jsp:include page="/layout/header.jsp" />
 <br>
  <form action="${pageContext.request.contextPath}/DispatcherServlet" method="post" name="write_form">  
   <input type="hidden" name="command" value="boardWrite"> 
   <table class="inputForm" align="center">
    <tbody>

   <div class="container">
   <div class="col-sm-3"></div>
   <div class="form-group">
   	  <div class="col-sm-6">
      <label for="inputdefault">TITLE</label>
      <input class="form-control" id="inputdefault" type="text" name="title" td colspan="3">
    </div>
    </div>
    <div class="col-sm-3"></div>
    </div>

    <br>
    
    <div class="container">
   <div class="col-sm-3"></div>
   <div class="form-group">
   	  <div class="col-sm-6">
      <label for="comment">CONTENT</label>
      <textarea class="form-control" rows="10" id="comment" name="content"></textarea>
    </div>
    </div>
    <div class="col-sm-3"></div>
    </div>
    
    <tr>
     <td colspan="4" align="center" >
     <br>
        <button type="button" class="btn btn-primary" onclick="content_submit()">글쓰기</button>
         <button type="button" class="btn btn-primary" onclick="cancel()">취소</button>
     </td>  
    </tr>
    
    </tbody>
   </table>
  </form>
</html>