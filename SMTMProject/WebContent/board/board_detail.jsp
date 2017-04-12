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
      <div class="row">
         <div class="table-responsive col-md-12">
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
                  <td colspan="3"><pre>${requestScope.bvo.content}</pre></td>
               </tr>
               <c:forEach items ="${requestScope.bvo.commentList }" var = "com" >
               		<tr>
               		<td colspan = "2">comment : ${com.content }</td><td>${com.id }</td>
               		</tr>
               </c:forEach>
            </table>
            <div class="row">

         <div class="col-md-12">
            <div class="widget-area no-padding blank">
               <div class="status-upload">
                  <form method= "post" action ="${pageContext.request.contextPath}/DispatcherServlet?command=insertComment&board_no=${requestScope.bvo.boardNO }" >
                  
                     <textarea placeholder="댓글을 작성해주세요" name = "content" ></textarea>
					
					<input type = "hidden" name = "parrentComNO" value = "0">
					<input type = "hidden" name = "depth" value = "0">
                     <button type = "submit" class="btn btn-success green" id = "commentBtn">
                        <i class="fa fa-share"></i> 댓글달기
                     </button>
                  </form>
               </div>
               <!-- Status Upload  -->
            </div>
            <!-- Widget Area -->
         </div>

      </div>
      <div class = "row">
      <table  class="table table-striped table-hover" >
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
         </div>
      </div>
   </div>
</body>
</html>