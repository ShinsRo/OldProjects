<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="robots" content="noindex">
<title>Insert title here</title>
<link rel="stylesheet"
   href="${pageContext.request.contextPath}/css/comment.css">
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
   href="https://fonts.googleapis.com/css?family=Open+Sans">
   <script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
function sendList(){
   location.href="${pageContext.request.contextPath}/DispatcherServlet?command=board";
}
function winOpen(kind){   
   
   if(kind=="delete"){
      if(confirm("삭제하시겠습니까?")){
         location.href = "${pageContext.request.contextPath}/DispatcherServlet?command=boardDelete&boardNO=${requestScope.bvo.boardNO }";                  
      }
      
   }else if(kind=="update"){
      if(confirm("수정하시겠습니까?")){
         location.href = "${pageContext.request.contextPath}/DispatcherServlet?command=boardUpdateView&boardNO=${requestScope.bvo.boardNO }";                  
      }
   }
}
</script>
</head>
<body>
   <jsp:include page="/layout/header.jsp" />
   <div class="container">
  
   <!-- row : 1 -->
      <div class="row">
      <div class="col-sm-2"></div>
         <div class="table-responsive col-md-8">
            <table class="table table-striped table-hover" id = "detail-table">
               <tr>
                  <td>NO : ${requestScope.bvo.boardNO }</td>
                  <td colspan="2">${requestScope.bvo.title}</td>
               </tr>
               <tr>
                  <td>작성자 : ${requestScope.bvo.id }</td>
                  <td>${requestScope.bvo.timePosted }</td>

               </tr>
               <tr>
                  <td colspan="3"><textarea cols = "100" rows ="8" readonly="readonly">${requestScope.bvo.content}</textarea></td>
               </tr>
               <c:forEach items ="${requestScope.bvo.commentList }" var = "com" >
                     <tr>

                     <td colspan = "2">
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      ${com.id }&nbsp;&nbsp;|&nbsp;&nbsp;${com.content }
                     </td>
                     <td>
                     <!-- DeleteCommentForm -->
                     <form method = "post" action="${pageContext.request.contextPath}/DispatcherServlet?command=deleteComment">
                        <c:if test="${sessionScope.mvo.id  == com.id }">   
                           <input type = "hidden" name = "comNO" value = "${com.comNO }">
                           <input type = "hidden" name = "boardNO" value = "${requestScope.bvo.boardNO }">
                           <button type = "submit" class= "btn btn-success"><font size = "1">삭제</font></button>
                        </c:if>
                     </form>
                     <!-- DeleteCommentForm -->
                     </td>
                     </tr>
               </c:forEach>
               <tr>
               <td valign="middle" align="center" colspan="3">
               <form method= "post" action ="${pageContext.request.contextPath}/DispatcherServlet?command=insertComment&board_no=${requestScope.bvo.boardNO }" >
                  
                     <textarea cols = "75" placeholder="댓글을 작성해주세요" name = "content" required="required"></textarea>
               
               <input type = "hidden" name = "parrentComNO" value = "0">
               <input type = "hidden" name = "depth" value = "0">
                     <button type = "submit" class="btn btn-success green" id = "commentBtn">
                        <i class="fa fa-share"></i> 댓글달기
                     </button>
                  </form>
               </td>
               </tr>
               <tr>
                  <td valign="middle" align="center" colspan="3">
                  <button type="button" class="btn btn-primary" onclick="sendList()">목록</button>
                  <c:choose>
                        <c:when
                           test="${sessionScope.mvo!=null&&sessionScope.mvo.id == requestScope.bvo.id}">
                           <button type="button" class="btn btn-primary" onclick="winOpen('delete')">삭제</button>
                           <button type="button" class="btn btn-primary" onclick="winOpen('update')">수정</button>
                           
                  </c:when>
              <c:when
                           test="${sessionScope.mvo.authority==1 }">
                           <button type="button" class="btn btn-primary" onclick="winOpen('delete')">삭제</button>

                        </c:when>
                  </c:choose>
               </tr>
            </table> 
         </div>
         <div class="col-sm-2"></div>
      </div> 
      
  <!-- row : 2 -->
     <div class="row">
     <div class="col-sm-2"></div>
         <div class="col-md-8">
            <div class="widget-area no-padding blank">
               <div class="status-upload">
                  
               </div>
               <!-- Status Upload  -->
            </div>
            <!-- Widget Area -->
         </div>
         <div class="col-sm-2"></div>
      </div> <!-- row 2 닫아줌 -->
      
      <!-- row : 3 -->
      <div class = "row">
      <div class="col-sm-2"></div>
      <div class="col-sm-8">
      <table class="table table-striped table-hover" >
      
       </table>
       </div>
       <div class="col-sm-2"></div>
      </div> <!-- row : 3 닫아줌 -->
      
   </div>
</body>
</html>