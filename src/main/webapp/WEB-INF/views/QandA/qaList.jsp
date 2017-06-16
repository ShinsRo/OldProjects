<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css"
   href="${pageContext.request.contextPath }/resources/_css/dropdown.css">
<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
   <div class="vertical-center sun">
      <div class="container">
         <div class="row">
            <div class="action">
               <div class="col-sm-12">
                  <h1 class="title">Q&amp;A</h1>
                  <p>
                     <br> 질문 사항이 있으면 남겨주세요! 관리자가 확인 후 답변 드리겠습니다.(≖ᴗ≖✿)
                           <span style="float:right">
      <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
         <input  class="btn btn-info" type="button" value="글쓰기"
            onclick="javascript:location.href='${pageContext.request.contextPath}/registerQuestionView.do'">
      </sec:authorize>
   </span>
                  </p>
               </div>
            </div>
         </div>
      </div>
   </div>
</section>
<!--배너 타이틀-->
<div class="container">
   <table class="table table-hover" id="commentList">
      <thead>
         <tr>
            <th class="no">NO</th>
            <th class="name">제목</th>
            <th class="date">아이디</th>
            <th class="hit">작성일</th>
            <th class="addr">조회수</th>
         </tr>
      </thead>
      <tbody>
         <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
         <sec:authentication property="principal.id" var="mvoId"/>
         <c:forEach items="${requestScope.lvo.list}" var="qvo">
            <tr>
               <td>${qvo.qno}</td>
               <!-- 비밀글 -->
                <td>
                        <c:forEach begin="2" end="${qvo.re_lev}">
                        &nbsp;&nbsp;<!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
                        </c:forEach>
                        <c:if test="${qvo.is_secret == '1'}">
                        	<img src="${pageContext.request.contextPath}/resources/img/secret.png" style="width:20px">&nbsp;&nbsp;
                        </c:if>
                	<c:if test="${qvo.re_lev == 1}">
                		<c:set var="prevId" value="${qvo.id}"></c:set>
                	</c:if>
                	<c:if test="${qvo.re_lev > 1}">
                        <img class="reply_icon" src="${pageContext.request.contextPath}/resources/img/reply_icon.png" width="20">
                    </c:if>
	               <c:choose>
                  <c:when test="${mvoId=='admin'}">
                     <a href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a>
                  </c:when>
                  <c:when test="${qvo.is_secret == '0'}">
                     <a href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a>
                  </c:when>
                  <c:when test="${qvo.id=='admin' && mvoId==prevId}">
                     <a href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a>
                  </c:when>
                  <c:when test="${qvo.is_secret == '1' && qvo.id == mvoId}">
                     <a href="${pageContext.request.contextPath}/showQuestionHit.do?qno=${qvo.qno}">${qvo.title}</a>
                  </c:when>
                   <c:otherwise>
                        ${qvo.title}
                  </c:otherwise>
               </c:choose>
                </td>
               <td>${qvo.id}</td>
               <td>${qvo.time_posted }</td>
               <td>${qvo.hit}</td>
            </tr>
         </c:forEach>
         </sec:authorize>
      </tbody> 
   </table>
   <br></br>
   <div class="portfolio-pagination">
      <ul class="pagination">
         <c:set var="pb" value="${requestScope.lvo.pagingBean}"></c:set>

         <c:choose>
            <c:when test="${pb.previousPageGroup}">
               <li><a
                  href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
            </c:when>
            <c:otherwise>
               <li></li>
            </c:otherwise>
         </c:choose>

         <c:forEach var="i" begin="${pb.startPageOfPageGroup}"
            end="${pb.endPageOfPageGroup}">
            <c:choose>
               <c:when test="${pb.nowPage!=i}">
                  <li><a
                     href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${i}">${i}</a></li>
               </c:when>
               <c:otherwise>
                  <li class="active"><a href="#">${i}</a></li>
               </c:otherwise>
            </c:choose>
         </c:forEach>

         <c:choose>
            <c:when test="${pb.nextPageGroup}">
               <li><a
                  href="${pageContext.request.contextPath}/getQuestionList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
            </c:when>
            <c:otherwise>
               <li></li>
            </c:otherwise>
         </c:choose>
      </ul>
   </div>

   <br> <br>

</div>