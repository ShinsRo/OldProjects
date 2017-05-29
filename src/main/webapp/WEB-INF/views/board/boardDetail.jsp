<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="bvo" value="${requestScope.bvo}"></c:set>
<script>
	$(document).ready(function(){
		$("#give-me").click(function(){
			$("#myModal").modal();
		});
	});
</script>
<section id="portfolio-information" class="padding-top">
       <div class="container">
           <div class="row">
               <div class="col-sm-6">
                   <img src="${pageContext.request.contextPath}/resources/images/portfolio-details/1.jpg" class="img-responsive" alt="">
               </div>
               <div class="col-sm-6">
                    <div class="project-name overflow">
                        <h2 class="bold">${bvo.title}</h2>
                        <ul class="nav navbar-nav navbar-default">
                            <li><i class="fa fa-clock-o"></i>${bvo.time_posted}</li>
                            <li><i class="fa fa-tag"></i>${bvo.addr}</li>
                        </ul>
                    </div>
                    <div class="project-info overflow">
                        <h3>상품 설명</h3>
                        <p>${bvo.bcontent}</p>
                    </div>
                    <div class="give-me-btn" id="give-me">
                        <a href="#" class="btn btn-common uppercase">주세요 신청</a>
                    </div>
                    <!-- 주세요 신청 modal -->
                    <!-- start modal -->
					<div class="modal fade" id="myModal" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">주세요 신청하기</h4>
								</div>
								<div class="modal-body">
									<div>
										신청 글: ${bvo.title}
									</div>
									신청 사유를 적어주세요.<br>
									<input type="text" name="reason">
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-sm btn-info">신청하기</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>
							</div>
	
						</div>
					</div>
				<!-- end of modal -->
			</div>
            </div>
        </div>
    </section>
     <!--/#portfolio-information-->

    <section id="related-work" class="padding-top padding-bottom">
        <div class="container">
            <div class="row">
                <h1 class="title text-center">Related Image</h1>
                <div class="col-sm-3">
                    <div class="portfolio-wrapper">
                        <div class="portfolio-single">
                            <div class="portfolio-thumb">
                                <img src="${pageContext.request.contextPath}/resources/images/portfolio/1.jpg" class="img-responsive" alt="">
                            </div>
                            <div class="portfolio-view">
                                <ul class="nav nav-pills">
                                    <li><a href="${pageContext.request.contextPath}/resources/images/portfolio/1.jpg" data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="portfolio-info ">
                            <h2>Sailing Vivamus</h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="portfolio-wrapper">
                        <div class="portfolio-single">
                            <div class="portfolio-thumb">
                                <img src="${pageContext.request.contextPath}/resources/images/portfolio/2.jpg" class="img-responsive" alt="">
                            </div>
                            <div class="portfolio-view">
                                <ul class="nav nav-pills">
                                    <li><a href="${pageContext.request.contextPath}/resources/images/portfolio/2.jpg" data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="portfolio-info ">
                            <h2>Sailing Vivamus</h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="portfolio-wrapper">
                        <div class="portfolio-single">
                            <div class="portfolio-thumb">
                                <img src="${pageContext.request.contextPath}/resources/images/portfolio/3.jpg" class="img-responsive" alt="">
                            </div>
                            <div class="portfolio-view">
                                <ul class="nav nav-pills">
                                    <li><a href="${pageContext.request.contextPath}/resources/images/portfolio/3.jpg" data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="portfolio-info ">
                            <h2>Sailing Vivamus</h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="portfolio-wrapper">
                        <div class="portfolio-single">
                            <div class="portfolio-thumb">
                                <img src="${pageContext.request.contextPath}/resources/images/portfolio/4.jpg" class="img-responsive" alt="">
                            </div>
                            <div class="portfolio-view">
                                <ul class="nav nav-pills">
                                    <li><a href="${pageContext.request.contextPath}/resources/images/portfolio/4.jpg" data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="portfolio-info ">
                            <h2>Sailing Vivamus</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--/#related-work-->