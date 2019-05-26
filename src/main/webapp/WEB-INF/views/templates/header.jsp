<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
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
                    <li class="dropdown"><a href="">에러 테스트 메뉴<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
		                    	<li><a href = "_없는페이지">404Error Test</a></li>
                            	<li><a href = "${pageContext.request.contextPath }/makeError.do">500Error Test</a></li>
                            </ul>
                            </li>
                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
	                        <li><a href="${pageContext.request.contextPath }/admin.do">관리자페이지</a></li>
                    </sec:authorize>
                        <li class="active"><a href="${pageContext.request.contextPath}/home.do">Home</a></li>
                         <li class="dropdown"><a href="">드려요<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <li><a href="${pageContext.request.contextPath}/getBoardList.do">드려요 모아보기</a></li>
                                <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">       
                                	<li><a href="${pageContext.request.contextPath}/boardRegisterView.do">드려요 작성하기</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        <li class="dropdown"><a href="">여기는요<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <li><a href="${pageContext.request.contextPath}/getCommentList.do">여기는요 모아보기</a></li>
                                <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">       
                                	<li><a href="${pageContext.request.contextPath}/commentRegisterView.do">여기는요 작성하기</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                         <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">      
                        <li class="dropdown"><a href="">내 정보<i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                            	<li><a href="${pageContext.request.contextPath}/member/updates.do">회원수정</a></li>
                                <li><a href="${pageContext.request.contextPath}/myBoardList.do">내가 올린 드려요</a></li>
                                <li><a href="${pageContext.request.contextPath}/getCommentList.do?id=<sec:authentication property="principal.id"/>">내가 쓴 여기는요 </a></li>
                                <li><a href="${pageContext.request.contextPath}/getApplicationsById.do">주세요 현황</a></li>
                                <sec:authorize ifNotGranted="ROLE_ADMIN,ROLE_DEL"> 
                                <li><a href="${pageContext.request.contextPath}/coalition.do">운송제휴 신청</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        </sec:authorize>  
                         <sec:authorize ifNotGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DEL">                     
	                        <li><a href="${pageContext.request.contextPath}/member/logins.do">로그인</a></li>
	                        <li><a href="${pageContext.request.contextPath }/member/register_view.do">회원가입</a></li> 
                       	</sec:authorize>
                       <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN">
                        <sec:authorize ifNotGranted="ROLE_DEL"> 
	                        <li><a href="${pageContext.request.contextPath }/member/updates.do"><sec:authentication property="principal.name"/>님 로그인</a></li>
	                        <li><a href="${pageContext.request.contextPath }/logout.do">로그아웃</a></li>
                        </sec:authorize>
                        </sec:authorize>
						<sec:authorize ifAllGranted="ROLE_DEL">
							<li class="dropdown"><a href=""><sec:authentication property="principal.name"/>님 로그인<i class="fa fa-angle-down"></i></a>
								<ul role="menu" class="sub-menu">
									
									<sec:authentication property="principal.is_confirmed" var="mvo"/>
									<c:choose>
										<c:when test="${mvo == 'YES' }">
											<li><a href="${pageContext.request.contextPath}/getAllDeliveryList.do">용달 대기 신청</a></li>
											<li><a href="${pageContext.request.contextPath}/getDeliveryListByDid.do">나의 배송 관리</a></li>
										</c:when>
										<c:when test="${mvo == 'READY' }">
											<li><a>제휴 승인 대기중입니다.</a></li>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>

								</ul>
							</li>
							<li><a href="${pageContext.request.contextPath}/logout.do">로그아웃</a></li>
						</sec:authorize>
					<li><a href="${pageContext.request.contextPath}/getQuestionList.do">Q&amp;A</a></li>
					<li><a href="${pageContext.request.contextPath}/member/contact.do">Contact</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <!--/#header-->
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    