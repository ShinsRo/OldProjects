<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<header id="header_admin">      
        <div class="container">
            <div class="row">
                <div class="col-sm-12 overflow">
                   <div class="social-icons pull-right">
                        <ul class="nav nav-pills">
                            <li><a href=""><i class="fa fa-facebook"></i></a></li>
                            <li><a href=""><i class="fa fa-twitter"></i></a></li>
                            <li><a href=""><i class="fa fa-google-plus"></i></a></li>
                            <li><a href=""><i class="fa fa-dribbble"></i></a></li>
                            <li><a href=""><i class="fa fa-linkedin"></i></a></li>
                        </ul>
                    </div> 
                </div>
             </div>
        </div>
        <div class="navbar navbar-inverseadmin" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/home.do">
                    	<h1><img class="logo_img" src="${pageContext.request.contextPath}/resources/img/logo_tw.png" alt="logo" height="100" width="100"></h1>
                    </a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                    <c:choose>
							<c:when test="${sessionScope.mvo.id=='admin'}">
								<li class="active"><a href="${pageContext.request.contextPath}/admin.do">관리자홈으로</a></li>
                        <li class="active"><a href="${pageContext.request.contextPath}/home.do">일반홈으로</a></li>
                        <li class="active"><a href="${pageContext.request.contextPath}/getBoardList_admin.do">상품관리</a></li>
                        <li class="active"><a href="${pageContext.request.contextPath}/getCommentList_admin.do">후기관리</a></li>
                        <li class="active"><a href="${pageContext.request.contextPath}/getMemberList_admin.do">회원관리</a></li>
                       	<li class="active"><a href="${pageContext.request.contextPath}/admin.do">용달관리</a></li>
                       	<li class="active"><a href="${pageContext.request.contextPath}/logout.do">로그아웃</a></li>
							</c:when>
							<c:otherwise>
					<li class="active"><a href="${pageContext.request.contextPath}/home.do">관리자 로그인 요망 일반홈으로</a></li>
					</c:otherwise>
						</c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <!--/#header-->
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    