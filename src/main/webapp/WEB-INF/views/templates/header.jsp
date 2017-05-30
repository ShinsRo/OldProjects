<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<header id="header">      
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
        <div class="navbar navbar-inverse" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                    <a class="navbar-brand" href="${pageContext.request.contextPath}/home.do">
                    	<h1><img class="logo_img" src="${pageContext.request.contextPath}/resources/img/logo_w.png" alt="logo" height="100" width="100"></h1>
                    </a>
                    
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="${pageContext.request.contextPath}/home.do">Home</a></li>
                         <li class="dropdown"><a href="portfolio.html">드려요<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <li><a href="${pageContext.request.contextPath}/getBoardList.do">드려요 모아보기</a></li>
                                <li><a href="${pageContext.request.contextPath}/boardRegisterView.do">드려요 작성하기</a></li>
                            </ul>
                        </li>
                        <li class="dropdown"><a href="portfolio.html">여기는요<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <li><a href="${pageContext.request.contextPath}/getCommentList.do">여기는요 모아보기</a></li>
                                <li><a href="${pageContext.request.contextPath}/commentRegisterView.do">여기는요 작성하기</a></li>
                            </ul>
                        </li>
                        <li class="dropdown"><a href="portfolio.html">내 정보<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <li><a href="portfolio.html">내가 올린 드려요</a></li>
                                <li><a href="portfoliofour.html">내가 쓴 여기는요 </a></li>
                                <li><a href="portfoliotwo.html">드려요 현황</a></li>
                                <li><a href="portfolioone.html">주세요 현황</a></li>
                            </ul>
                        </li>                         
                        <li><a href="${pageContext.request.contextPath }/member/logins.do ">로그인</a></li>
                        <li><a href="${pageContext.request.contextPath }/member/registers.do">회원가입</a></li>                               
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <!--/#header-->
    