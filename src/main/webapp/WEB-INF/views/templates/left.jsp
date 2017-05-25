<%@ page language="java" contentType="text/html; charset=UTF-8"
/*    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:if test="${sessionScope.mvo==null }">
<a href="${pageContext.request.contextPath }/member/logins.do">로그인</a><br>
<a href="${pageContext.request.contextPath }/member/registers.do">회원가입</a><br>
</c:if>
<p><c:if test="${sessionScope.mvo!=null}">
<a href="${pageContext.request.contextPath }/member/updates.do">${sessionScope.mvo.name}</a>로그인<br>
<a href="${pageContext.request.contextPath }/logout.do">로그아웃</a>
</c:if>
<p>
*/
	pageEncoding="UTF-8"%>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$(".push_menu").click(function() {
			$(".wrapper").toggleClass("active");
		});
	});
</script>
<!-- <div class="container"> -->
<!-- 	<div class="row">
		<div class="wrapper"> -->
<!--     	    <div class="side-bar"> -->
<ul>
	<li class="menu-head">메뉴 <a href="#" class="push_menu"><span class="glyphicon glyphicon-align-justify pull-right"></span></a>
	</li>
	<!--                     <div class="menu"> -->
	<li><a href="${pageContext.request.contextPath}/home.do">HOME<span class="glyphicon glyphicon-dashboard pull-right"></span></a></li>
	<li><a href="${pageContext.request.contextPath}/member/login.do">로그인<span class="glyphicon glyphicon-dashboard pull-right"></span></a></li>
	<li><a href="${pageContext.request.contextPath}/comment/commentList.do" class="active">모든 후기 보기<span class="glyphicon glyphicon-heart pull-right"></span></a></li>
	<li><a href="${pageContext.request.contextPath}/board/boardList.do">모든 게시물 보기<span class="glyphicon glyphicon-star pull-right"></span></a></li>
	<li><a href="${pageContext.request.contextPath}/member/myPage.do">마이 페이지<span class="glyphicon glyphicon-cog pull-right"></span></a></li>
</ul>
	<!--                     </div> -->
<!--     	    </div>    -->
<!--     	 </div>
	</div> -->
<!-- </div> -->
