<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${requestScope.show.type == 'board' || requestScope.show.type == 'boardAll'}">
<c:set var="bvo" value="${requestScope.show.revo}"></c:set>

<section id="portfolio-information" class="padding-top">
       <div class="container">
           <div class="row">
               <div class="col-sm-6">
                   <img src="${pageContext.request.contextPath }/${bvo.thumbPath}" class="img-responsive" alt="">
               </div>
               <div class="col-sm-6">
                    <div class="project-name overflow">
                        <h2 class="bold">${bvo.title}</h2>
                        <ul class="nav navbar-nav navbar-default">
                            <li><i class="fa fa-clock-o"></i>${bvo.time_posted}</li>
                            <li><img width="12" height="14" src="${pageContext.request.contextPath}/resources/images/portfolio-details/addr-icon.png" class="addr-icon">${bvo.addr}</li>
                        </ul><br>
                        <h3>작성자: ${bvo.id}</h3>
                    </div>
                    <div class="project-info overflow">
                        <h3>상품 설명</h3>
                        <p>${bvo.bcontent}</p>
                    </div>
                    
			</div>
            </div>
        </div>
    </section>
     <!--/#portfolio-information-->

<section id="related-work" class="padding-top padding-bottom">
	<div class="container">
		<div class="row">
			<h1 class="title text-center">등록한 상품 사진</h1>
			<c:forEach items="${requestScope.show.plist}" var="imgList">
				<div class="col-sm-4">
					<div class="portfolio-wrapper">
						<div class="portfolio-single">
							<div class="portfolio-thumb">
								<img
									src="${pageContext.request.contextPath}/${imgList.img_path}"
									class="img-responsive" alt="">
							</div>
							<div class="portfolio-view">
								<ul class="nav nav-pills">
									<li><a
										href="${pageContext.request.contextPath}/${imgList.img_path}"
										data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
								</ul>
							</div>
						</div>
						<div class="portfolio-info ">
							<h2>${imgList.ptitle}</h2>
							<p>${imgList.pcontent}</p>
						</div>
					</div>
					<!-- wrapper -->
				</div>
			</c:forEach>
		</div>
		<div class = "row">
			<div align = "center">
<a href="${pageContext.request.contextPath}/deleteReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">삭제</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/rejectReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">거부</a>

			</div>
		</div>
	</div>
</section>
<!--/#related-work-->
       </c:when>
	<c:when test="${requestScope.show.type == 'comment' || requestScope.show.type == 'commentAll'}">
<section id="">
   <form enctype="multipart/form-data" id="write_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentRegister.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               NO : ${requestScope.show.revo.cno}&nbsp;&nbsp;&nbsp;&nbsp;제목 : ${requestScope.show.revo.title}
            </div>
            <div class="form-group" align="center">
            작성자 : ${requestScope.show.revo.id}&nbsp;&nbsp;&nbsp;&nbsp;
			작성일 : ${requestScope.show.revo.time_posted}&nbsp;&nbsp;&nbsp;&nbsp;
			조회수 : ${requestScope.show.revo.hit}&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15" readonly="readonly">${requestScope.show.revo.content}</textarea>
            </div>
            <div class="form-group" align="center">
			
			<c:if test="${sessionScope.mvo.id=='admin'}">
			 <a href="${pageContext.request.contextPath}/deleteReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">삭제</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/rejectReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">거부</a>
 
			 </c:if>

            </div>
         </div>
      </div>
   </form>
</section>
       </c:when>
	<c:when test="${requestScope.show.type == 'reply' || requestScope.show.type == 'replyAll'}">
<div class="box_reply">
	<div class="form-group" align="center">
		<ul class="cmlist">
			
				<li>
				<div class="col-md-10 col-sm-10" align="left">
					<span class="nickspan"> <c:if test="${show.revo.depth >=1}">&nbsp;&nbsp;&nbsp;&nbsp;
						<img class="reply_icon" src="${pageContext.request.contextPath}/img/reply_icon.png" width="20">
						</c:if>${show.revo.id}</span> <span class="cmdate">${show.revo.time_posted}</span>
						
						<span class="cmbtn">
						<c:if test="${sessionScope.mvo.id=='admin'}">
						<a href="${pageContext.request.contextPath}/deleteReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">삭제</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/rejectReport_admin.do?category=${show.category }&reno=${show.reno }&report_no=${show.report_no}&type=${show.type}&pageNo=${show.pageNo}">거부</a>

						</c:if></span>
				</div>
				<!-- class="cm_list_box" -->
				<div class="col-md-10 col-sm-10" align="left" id="reply<c:out value="${show.revo.rno}"/>">
					<c:out value="${show.revo.content }" />
				</div>
				</li>
			
		</ul>
	</div>
	</div>
       </c:when>
	<c:otherwise>
뭐죠??
      	</c:otherwise>
</c:choose>


