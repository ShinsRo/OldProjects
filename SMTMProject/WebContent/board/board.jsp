<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<meta name="viewport" content="width=device-width, initial-scale=1">



<head>

<title>Insert title here</title>
<script type="text/javascript">
   $(document).ready(function(){
      
   });
   function toWrite(){
      location.href = "${pageContext.request.contextPath}/board/write.jsp";
   }
</script>
<style type="text/css">
   p.paging{
      width:50%;
      text-align: center;
   }
</style>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
   href="https://fonts.googleapis.com/css?family=Open+Sans">
</head>
<body>
   <jsp:include page="/layout/header.jsp" />
   <div class="container" align="center">
      <div class="row" >
      <div class="table-responsive col-md-12" >
            <table class="table table-striped table-hover" >
               <thead>
                  <tr>
                     <th class="no">NO</th>
                     <th class="title">제목</th>
                     <th class="name">아이디</th>
                     <th class="date">작성일</th>

                  </tr>
               </thead>
               <tbody>
                  <c:forEach var="bvo" items="${requestScope.listVO.list}">
                     <tr>
                        <td>${bvo.boardNO }</td>
                        <td><a href = "${pageContext.request.contextPath}/DispatcherServlet?command=boardDetail&boardNO=${bvo.boardNO }">${bvo.title }</a></td>
                        <td>${bvo.id}</td>
                        <td>${bvo.timePosted }</td>
                     </tr>
                  </c:forEach>
               </tbody>
            </table>
         </div>
         </div>
         
      <p class = "paging">
            <c:if
               test="${requestScope.listVO.pagingBean.previousPageGroup ==true}">
               <li><a
                  href="${pageContext.request.contextPath}/DispatcherServlet?command=board&nowPage=${requestScope.listVO.pagingBean.startPageOfPageGroup-1}">◀&nbsp; </a></li>
            </c:if>
            <c:forEach
               begin="${requestScope.listVO.pagingBean.startPageOfPageGroup }"
               end="${requestScope.listVO.pagingBean.endPageOfPageGroup }"
               var="num">
               <c:choose>
                  <c:when test="${num!= requestScope.listVO.pagingBean.nowPage}">
                     <a
                        href="${pageContext.request.contextPath}/DispatcherServlet?command=board&nowPage=${num}">
                        ${num}</a>
                  </c:when>
                  <c:otherwise>
               ${num}
            </c:otherwise>
               </c:choose>
              
            </c:forEach>
            <c:if test="${requestScope.listVO.pagingBean.nextPageGroup ==true}">
               <a
                  href="${pageContext.request.contextPath}/DispatcherServlet?command=board&nowPage=${requestScope.listVO.pagingBean.endPageOfPageGroup+1}">▶</a>
            </c:if>
            </p>
            <button type="button" class="btn btn-primary" onclick = "toWrite()">글쓰기</button>
         </div>

</body>
</html>