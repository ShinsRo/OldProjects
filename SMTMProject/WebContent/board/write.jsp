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
   var f=document.write_form;
   f.reset();
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
<!--     <tr>
     <td>TITLE</td>
     <td colspan="3">
     <input type="text" name="title" size="48" >
     </td>
    </tr> -->
   <div class="container">
   <div class="form-group">
      <label for="inputdefault">TITLE</label>
      <input class="form-control" id="inputdefault" type="text" name="title">
    </div>
    </div>
    
<!--     <tr>
     <td colspan="4" align="left">
     &nbsp;&nbsp;
     <textarea cols="53" rows="15" name="content"></textarea>
     </td>
    </tr>  -->
    <div class="container">
    <div class="form-group">
      <label for="comment">COMMENT</label>
      <textarea class="form-control" rows="5" id="comment" name="content"></textarea>
    </div> 
    </div>
    
    <tr>
     <td colspan="4" align="center" >
        <button type="button" class="btn btn-primary" onclick="content_submit()">글쓰기</button>
         <button type="button" class="btn btn-primary" onclick="cancel()">취소</button>
     </td>  
    </tr>
    
    </tbody>
   </table>
  </form>
</html>