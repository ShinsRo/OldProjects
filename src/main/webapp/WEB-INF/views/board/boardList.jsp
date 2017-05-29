<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="page-breadcrumb">
        <div class="vertical-center sun">
             <div class="container">
                <div class="row">
                    <div class="action">
                        <div class="col-sm-12">
                            <h1 class="title">드려요</h1>
                            <p>안쓰는 물건을 필요한 사람들에게 나누어 주세요!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </section>
    <!--/#action-->

    <section id="portfolio">
        <div class="container">
            <div class="row">
                <ul class="portfolio-filter text-center">
                    <li><a class="btn btn-default active" href="#" data-filter="*">All</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".branded">Branded</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".design">Design</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".folio">Folio</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".logos">Logos</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".mobile">Mobile</a></li>
                    <li><a class="btn btn-default" href="#" data-filter=".mockup">Mockup</a></li>
                </ul><!--/#portfolio-filter-->
              	
                <div class="portfolio-items">
                 <c:forEach items="${requestScope.blvo.list}" var="list">
                    <div class="col-xs-6 col-sm-4 col-md-3 portfolio-item branded logos">
                        <div class="portfolio-wrapper">
                            <div class="portfolio-single">
                                <div class="portfolio-thumb">
                                    <img src="${pageContext.request.contextPath }/resources/images/portfolio/1.jpg" class="img-responsive" alt="">
                                </div>
                                <div class="portfolio-view">
                                    <ul class="nav nav-pills">
                                        <li><a href="${pageContext.request.contextPath}/boardDetail.do?bno=${list.bno}"><i class="fa fa-link"></i></a></li>
                                        <li><a href="${pageContext.request.contextPath}/resources/images/portfolio/1.jpg" data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="portfolio-info ">
                                <h2>${list.title}</h2>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                 <!-- paging bean -->
                <c:set var="pb" value="${requestScope.blvo.pagingBean}"></c:set>
                <div class="portfolio-pagination">
                    <ul class="pagination">
                      <li><a href="#">left</a></li>
                      <li><a href="#">1</a></li>
                      <li><a href="#">2</a></li>
                      <li class="active"><a href="#">3</a></li>
                      <li><a href="#">4</a></li>
                      <li><a href="#">5</a></li>
                      <li><a href="#">6</a></li>
                      <li><a href="#">7</a></li>
                      <li><a href="#">8</a></li>
                      <li><a href="#">9</a></li>
                      <li><a href="#">right</a></li> 
<!--                       <li><a href="#">left</a></li>
                      <li><a href="#">1</a></li>
                      <li><a href="#">2</a></li>
                      <li class="active"><a href="#">3</a></li>
                      <li><a href="#">4</a></li>
                      <li><a href="#">5</a></li>
                      <li><a href="#">6</a></li>
                      <li><a href="#">7</a></li>
                      <li><a href="#">8</a></li>
                      <li><a href="#">9</a></li>
                      <li><a href="#">right</a></li> -->
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <!--/#portfolio-->

<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css"> --%>
<%-- <script>
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
	                <a href="${pageContext.request.contextPath}/boardDetail.do?bno=${list.bno}">
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
 --%>