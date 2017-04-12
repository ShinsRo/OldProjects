<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글쓰기</title>
<link rel="stylesheet" type="text/css" 
href="${pageContext.request.contextPath}/css/board.css">
<script type="text/javascript">
function content_submit(){
   var f=document.write_form;
   if(f.title.value==""){
      alert("제목을 입력하세요!");
      f.title.focus();
      return; 
   }
   if(f.writer.value==""){
      alert("이름을 입력하세요!");
      f.writer.focus();
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
<%-- <jsp:include page="/board/board.jsp" /> --%>
 <br>
  <form action="${pageContext.request.contextPath}/DispatcherServlet" method="post" name="write_form">  
   <input type="hidden" name="command" value="boardWrite"> 
   <table class="inputForm" >
    <caption>글쓰기</caption>
    <tbody>
    <tr>
     <td>제목</td>
     <td colspan="3">
     <input type="text" name="title" size="48" >
     </td>
    </tr>
    <tr>
     <td>이름</td>
     <td><input type="text" name="id" size="20" ></td>
    </tr>
    <tr>
     <td colspan="4" align="left">
     &nbsp;&nbsp;
     <textarea cols="53" rows="15" name="content"></textarea>
     </td>
    </tr> 
    <tr>
     <td colspan="4" align="center" >
      <img class="action" src="${pageContext.request.contextPath}/img/write_btn.jpg" alt="글입력"  onclick="content_submit()">
      <img class="action" src="${pageContext.request.contextPath}/img/cancel.gif" onclick="cancel()">      
     </td>  
    </tr>
    </tbody>
   </table>
  </form>
</html>