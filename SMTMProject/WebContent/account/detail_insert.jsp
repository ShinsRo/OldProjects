<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<script src="//code.jquery.com/jquery.min.js"></script>
  <script type="text/javascript">
   function insertFunc() {
	document.insertForm.submit();
	}

   
</script>

</head>
<body>
<center><h3>추가</h3></center>
<form id = "insertForm" name = "insertForm" method = "post" action = "${pageContext.request.contextPath}/DispatcherServlet"> 
<input type = "hidden" name = "command" value = "add">

<input type = "hidden" name = "today" value = "${param.today}">
<table>

   <tr>
      <td><input type="radio" name="inAndOut" value="income">수입 
      <input type="radio" name="inAndOut" value="spend">지출</td>         
     </tr>
     
     <tr>
      <td>상세내용<input type = "text" name = "detail" id = "detail"></td>
      <td>금액<input type = "text" name = "money" id = "money"></td>
   </tr>
   
   <tr>
		<select id="morningAfternoon" name = "morningAfternoon">
			<option value="">-시간대선택-</option>
			<option value = "am">am</option>
			<option value = "pm">pm</option>
		</select>
		<select name = "hh" id="hh">
			<option value="">-시간-</option>
			<c:forEach begin="1" end="12" var = "hh">
				<option value="${hh}">${hh}</option>
			</c:forEach> 
		</select>
		<select name = "mm" id="mm">
			<option value="">-분-</option>
			<c:forEach begin="00" end="59" var = "mm">
				<option value="${mm}">${mm}</option>
			</c:forEach> 
		</select>
		</tr>
		
</table>
</form>
<br>
<input type = "button" class="btn btn-sm btn-primary btn-block" value = "추가" id = "insertBtn" onclick="insertFunc()">
</body>
</html>