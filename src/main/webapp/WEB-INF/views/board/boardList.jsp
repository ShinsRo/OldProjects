<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/3-col-portfolio.css">
<!DOCTYPE html>
        <!-- Projects Row -->
        <div class="product_container">
        <div class="row">
        <c:forEach items="${requestScope.blvo.list}" var="list">
        	 <div class="col-md-4 portfolio-item">
                <a href="#">
                    <img class="img-responsive" src="http://placehold.it/700x400" alt="">
                </a>
                <span class="product_name">${list.title }</span>
            </div>
        </c:forEach>
        </div>
        <!-- /.row -->

        <hr>
	<c:set var="pb" value="${requestScope.blvo.pagingBean}"></c:set>
        <!-- Pagination -->
        <div class="row text-center">
            <div class="col-lg-12">
                <ul class="pagination">
                	<c:if test="${pb.previousPageGroup}">
                    <li>
                        <a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.startPageOfPageGroup-1}">&laquo;</a>
                    </li>
                    </c:if>
				    <c:forEach var="i" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
						<c:choose>
							<c:when test="${pb.nowPage!=i}">
								<li><a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${i}">${i}</a></li>
							</c:when>
							<c:otherwise><li class="active"><a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${i}">${i}</a></li></c:otherwise>
						</c:choose>
					</c:forEach>	
					<c:if test="${pb.nextPageGroup}">
                    <li>
                        <a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.endPageOfPageGroup+1}">&raquo;</a>
                    </li>
                    </c:if>
                </ul>
            </div>
        </div>
        
        </div>
        <!-- /.row -->
