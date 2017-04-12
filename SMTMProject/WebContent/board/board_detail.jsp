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
                  <td valign="middle" align="center" colspan="3"><img
                     class="action"
                     src="${pageContext.request.contextPath}/img/list_btn.jpg"
                     onclick="sendList()"> <c:choose>
                        <c:when
                           test="${sessionScope.mvo!=null&&sessionScope.mvo.id == requestScope.bvo.id}">
                           <img class="action" onclick="winOpen('delete')"
                              src="${pageContext.request.contextPath}/img/delete_btn.jpg">
                           <img class="action" onclick="winOpen('update')"
                              src="${pageContext.request.contextPath}/img/modify_btn.jpg"></td>
                  </c:when>
                  </c:choose>
               </tr>


            </table>
         </div>
      </div>
   </div>
</body>
</html>