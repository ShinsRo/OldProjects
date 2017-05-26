<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css">
<script>
	function img_click(){
		alert("click~");
	}
</script>
<!DOCTYPE html>
        <!-- Projects Row -->
        <div class="product_container">
        <div class="row">
        <c:forEach items="${requestScope.blvo.list}" var="list">
        	 <div class="col-md-4 portfolio-item">
	                <a href="${pageContext.request.contextPath}/board/boardDetail.do?bno=${list.bno}">
	                    <img class="img-responsive" src="${pageContext.request.contextPath}/resources/img/siba.jpg" alt="item_img">
	                </a>
	                <span class="product_name">${list.title}</span><br><span>[${list.addr}]</span>
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
