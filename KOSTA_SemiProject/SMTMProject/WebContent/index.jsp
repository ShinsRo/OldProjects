<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <c:if test="${!(empty mvo)}">
		<jsp:forward page="account/list.jsp"></jsp:forward>
    </c:if>
      
<jsp:forward page="member/login.jsp"></jsp:forward>