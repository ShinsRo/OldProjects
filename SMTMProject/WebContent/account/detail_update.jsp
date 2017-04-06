<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>update title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
   <script src="//code.jquery.com/jquery.min.js"></script>
   <script type="text/javascript">
   $(document).ready(function(){
	   $("#updateBtn").click(function(){
		   if($("input[name=inAndOut]:checked").val()==null){
				alert("수입,지출을 선택하세요!");
		   }else if($("#detail").val()==""){
				alert("상세내용을 입력하세요!");
			}else if($("#money").val()==""){
				alert("금액을 입력하세요!");
			}else if($("#morningAfternoon").val()==""){
				alert("시간대를 선택하세요!");
			}else{
				
		 		  $.ajax({
			  		  type:"get",
			 		  url:"${pageContext.request.contextPath}/DispatcherServlet?command=update",
			 		  data:"no=${param.no}&today=${param.today}&"+$("#updateForm").serialize()
			 		
			 		 
		 		 });//ajax
			}
	   });//click
  	 });//ready();
   
</script>
</head>
<body>
<center><h3>수정</h3></center>
<form id = "updateForm">

<table>
	<tr>
		<td>
		<input type="radio" name="inAndOut" value="income">수입		
		<input type="radio" name="inAndOut" value="spend">지출
		
		</td>   	
	</tr>	
	
	
	
	<tr>
		<!-- 사용자가 상세내용과 금액을 확인할 수 있도록 상세내용과 금액은 기존에 있는 정보를 가져와서 출력함 -->
		<td>상세내용<input type = "text" name = "detail"id = "detail" value = "222"></td>
		
		<td>금액<input type = "text" name = "money" id = "money" value = "4000"></td>
		
	
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

<input type = "button" class="btn btn-sm btn-primary btn-block" value = "수정" id = "updateBtn">
</body>
</html>