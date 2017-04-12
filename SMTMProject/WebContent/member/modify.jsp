<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보수정</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" media="screen" type="text/css" />
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
   $("#testForm").submit(function(){
      if(document.testForm.password.value != document.testForm.checkPassword.value){
         alert("비밀번호가 틀립니다.");
         return false;
      }
   })
})

/*          if(document.testForm.password.value!=document.testForm.checkPassword.value){
            alert("비밀번호가 틀립니다.");
            return false;
         } */
</script>
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
<form name="testForm" id = "testForm" method="post" action="${pageContext.request.contextPath}/DispatcherServlet">
 <div class="wrap">
      <div class="avatar">
      <img src="${pageContext.request.contextPath}/img/logo.png">
      </div>
      <input type = "hidden" name = "command" value = "modify">
      <input type="text" name="name" value="${sessionScope.mvo.name}" required>
      <div class="bar">
         <i></i>
      </div>
      <input type="text" name="id" value="${sessionScope.mvo.id}" id="middleField" readonly="readonly">
      <!-- <input type="hidden" name="data" value="">
      <a class="forgot_link" value="중복확인" onclick="openPopup()">중복확인</a> -->
      <div class="bar">
          <i></i>
      </div>
      <input type="password" name="password" value="${sessionScope.mvo.password}" required>
      <div class="bar">
         <i></i>
      </div>
      <input type="password" name="checkPassword" placeholder="패스워드를 확인해주세요." required>
      <div class="bar">
          <i></i>
      </div>
      <input type="text" name="limit" placeholder="기준금액: ${sessionScope.mvo.limit}" id="lowerRound" required>
      <br>
     <input type="submit" id = "modifyBtn" style="margin-bottom:15px; HEIGHT: 35pt" value="회원 정보 수정">  
   </div>
  </form>
</body>
</html>