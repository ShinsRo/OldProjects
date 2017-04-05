<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 경로변경 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
      $("#updateBtn").click(function(){
         $.ajax({
            type:"get",
            url:"DispatcherServlet",
            // no을 어디서 받아와야 하나
            data:"command=update&no="
         });//ajax
      });// updateBtn.click
      
      $("#deleteBtn").click(function(){
         if(confirm("정말로 삭제하시겠습니까?")){
            $.ajax({
               type:"get",
               url:"DispatcherServlet",
               data:"command=delete&no="
            });//ajax
            alert("삭제되었습니다.");
         }else{
            alert("삭제가 취소되었습니다.");
         }
      });// deleteBtn.click
      $("#insertBtn").click(function(){
         $.ajax({
            type:"get",
            url:"${pageContext.request.contextPath}/DispatcherServlet",
            data:"command=insert"
         });//insertBtn.click
      })
   })
</script>
</head>
<body>
<center><h1>Show Me The Money</h1></center>
<table>
<tr>
   <th>Data</th><th>ID</th><th>이름</th><th>지출금액</th><th>수입금액</th><th>수정</th><th>삭제</th>
   </tr>
   <tr>
   <td>3/14</td><td>JAVA</td><td>김승신</td><td>300,000</td><td>0</td><td><input type = "button" value = "수정" id = "updateBtn"></td><td><input type = "button" value = "삭제" id = "deleteBtn"></td>
   </tr>
   <tr>
   <td>3/14</td><td>KING</td><td>오남준</td><td>100,000</td><td>0</td><td><input type = "button" value = "수정" id = "updateBtn"></td><td><input type = "button" value = "삭제" id = "deleteBtn"></td>
   </tr>
   <tr>
   <td>3/14</td><td>JAVA</td><td>김승신</td><td></td><td>300,000</td><td><input type = "button" value = "수정" id = "updateBtn"></td><td><input type = "button" value = "삭제" id = "deleteBtn"></td>
   </tr>
   <tr>
   <td>3/14</td><td>KING</td><td>오남준</td><td>50,000</td><td>0</td><td><input type = "button" value = "수정" id = "updateBtn"></td><td><input type = "button" value = "삭제" id = "deleteBtn"></td>
   </tr>
   <tr>
      <td><input type = "button" value = "추가" id = "insertBtn"></td>
      <!-- "추가"버튼 수정-->
   </tr>
</table>
</body>
</html>