<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$(".push_menu").click(function() {
			$(".wrapper").toggleClass("active");
		});
	});
</script>
       <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
       <div><img class="logo_img" alt="logo" src="${pageContext.request.contextPath}/resources/img/logo_tw.png"></div>
            <ul class="nav sidebar-nav">
                <li class="sidebar-brand">
                    <a href="${pageContext.request.contextPath}/home.do"></a>
                </li>
		<li><a href="${pageContext.request.contextPath}/home.do"><i class="fa fa-fw fa-home"></i> Home</a></li>
		<c:choose>
			<c:when test="${sessionScope.mvo == null}">
				<li><a href="${pageContext.request.contextPath}/member/logins.do">Login</a></li>
				<li><a href="${pageContext.request.contextPath}/member/registers.do">회원가입</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/logout.do">로그아웃</a></li>
			</c:otherwise>
		</c:choose>
		<li><a href="${pageContext.request.contextPath}/getCommentList.do">모든 후기 보기</a></li>
		<li><a href="${pageContext.request.contextPath}/board/boardList.do">모든 게시물 보기</a></li>
		<li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown"><i class="fa fa-fw fa-cog"></i>마이페이지<span
				class="caret"></span></a>
			<ul class="dropdown-menu" role="menu">
				<!-- <li class="dropdown-header">Dropdown heading</li> -->
				<li><a href="#">내가 등록한 상품</a></li>
				<li><a href="#">내가 쓴 후기</a></li>
				<li><a href="#">내 신청현황</a></li>
				<li><a href="#">받은 신청현황</a></li>
			</ul></li>
		<li>
                    <a href="#"><i class="fa fa-fw fa-bank"></i> Page four</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-dropbox"></i> Page 5</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-twitter"></i> Last page</a>
                </li>
            </ul>
        </nav>
        <!-- /#sidebar-wrapper -->