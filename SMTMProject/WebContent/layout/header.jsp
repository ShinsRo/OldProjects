<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
   function logout() {
      var logout = confirm("로그아웃 하시겠습니까?");
      if (logout)
         location.href = "${pageContext.request.contextPath}/DispatcherServlet?command=logout";
   }
   function modify(){
	      var modify=confirm("정보수정을 하시겠습니까?");
	      if(modify)
	         location.href="${pageContext.request.contextPath}/member/modify.jsp";
	   }

</script>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Light, Basic Header</title>

<link rel="stylesheet"
   href="${pageContext.request.contextPath}/css/header-basic-light.css">
<link href='http://fonts.googleapis.com/css?family=Cookie'
   rel='stylesheet' type='text/css'>

</head>

<header class="header-basic-light">
   <div class="header-limiter">
      <h1>
         <a href="#">ShowMeThe<span>Money!</span></a>
      </h1>
      <c:if test="${!(empty mvo)}">
         <nav>

            <a href="">${sessionScope.mvo.name}님 가계부</a> <a
               href="${pageContext.request.contextPath}/account/list.jsp"><img
               src="${pageContext.request.contextPath }/img/list_icon.png"></a>
            <a href="../DispatcherServlet?command=getCurrent&go=calendar"> <img
               src="${pageContext.request.contextPath }/img/calendar_icon.png"></a>
          <c:if test="${param.nowPage == 'calendar'}">
               <a id="toGraphBtn" onclick="toGraph()"> <img
                  src="${pageContext.request.contextPath }/img/graph_icon.png"></a>
            </c:if>
            <a href="javascript:logout()" class="selected">Logout</a>
            <a href="javascript:modify()">modify</a>
         </nav>
      </c:if>
   </div>
</header>

