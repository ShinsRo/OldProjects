<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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













