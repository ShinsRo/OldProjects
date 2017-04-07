<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>register</title>
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
  /*  function submit() {
      if(document.testForm.data.value!=document.testForm.id.value){
         alert("인증 되지 않은 아이디입니다. 다시 중복확인 해주세요");
         return;
      }
         document.testForm.submit();
   } */
</script>
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<form name="testForm" method="post" action="${pageContext.request.contextPath}/DispatcherServlet?command=register" onsubmit="return checkId()">
 <div class="wrap">
      <div class="avatar">
      <img src="${pageContext.request.contextPath}/img/logo.png">
      </div>
      <input type="text" name="name" placeholder="이름을 입력하세요." required>
      <div class="bar">
         <i></i>
      </div>
      <input type="text" name="id" placeholder="아이디를 입력하세요." id = "lowerRound" required>
      <input type="hidden" name="data" value="">
      <a class="forgot_link" value="중복확인" onclick="openPopup()">중복확인</a>
      
      <input type="password" name="password" placeholder="비밀번호를 입력하세요." id = "upperRound" required>
      <div class="bar">
         <i></i>
      </div>
      <input type="password" name="password" placeholder="다시 한번 입력하세요."required>
      <br>
      <input type="submit" id = "registerBtn" value="회원가입">
<!--       <button onclick="submit()">Sign up</button> -->
      
   </div>

  <script src=""></script>
  </form>
</body>
</html>