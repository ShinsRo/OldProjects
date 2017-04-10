<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보수정</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" media="screen" type="text/css" />
<script type="text/javascript">

function openPopup(){
   var id = document.testForm.id.value;
   
      if (id == "") {
         alert("아이디를 입력하세요.");
      } else
         open("${pageContext.request.contextPath}/DispatcherServlet?command=idcheck"+"&id="+id, "mypopup",
               "width=200, height=200, top=150, left=200");
      }
function checkId() {
      if(document.testForm.data.value!=document.testForm.id.value){
      alert("인증 되지 않은 아이디입니다. 다시 중복확인 해주세요");
      return false;
      }
   }
   
</script>
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<form name="testForm" method="post" action="${pageContext.request.contextPath}/DispatcherServlet?command=modify">
 <div class="wrap">
      <div class="avatar">
      <img src="${pageContext.request.contextPath}/img/logo.png">
      </div>
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
      <input type="password" name="password" placeholder="패스워드를 확인해주세요." required>
      <div class="bar">
          <i></i>
      </div>
      <input type="text" name="limit" placeholder="기준금액: ${sessionScope.mvo.limit}" id="lowerRound" required>
      <br>
     <input type="submit" id = "modifyBtn" style="margin-bottom:15px; HEIGHT: 35pt" value="회원 정보 수정">  
   </div>

  <script src=""></script>
  </form>
</body>
</html>