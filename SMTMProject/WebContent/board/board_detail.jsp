<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="robots" content="noindex">
<title>Insert title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
   href="https://fonts.googleapis.com/css?family=Open+Sans">
<style type="text/css">

</style>
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
            <table class="table table-striped table-hover">
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



               <tr>
                  <td valign="middle" align="center" colspan="3">
                  <button type="button" class="btn btn-primary" onclick="sendList()">목록</button>
                  <c:choose>
                        <c:when
                           test="${sessionScope.mvo!=null&&sessionScope.mvo.id == requestScope.bvo.id }">
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
</body>
</html>