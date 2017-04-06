<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
   $(document).ready(function(){
	   $("#addBtn").click(function(){
			if($("input[name=inAndOut]:checked").val()==null){
				alert("수입,지출을 선택하세요!");
			}else if($("#detail").val()==""){
				alert("상세내용을 입력하세요!");
			}else if($("#money").val()==""){
				alert("금액을 입력하세요!");
			}else{
		  		 $.ajax({
					   type:"get",
			  		   url:"DispatcherServlet",
			   		  data:"command=add&detail="+$("#detail").val()+"&money="+$("#money").val()+"&inAndOut="+$("input[name=inAndOut]:checked").val()
			  });//ajax
			}
	   });//click
   });//ready
</script>
</head>
<body>
<center><h3>추가</h3></center>
<table>
	<tr>
		<th></th>
		<th><input type="radio" name="inAndOut" value="income">수입 
		<input type="radio" name="inAndOut" value="spend">지출</th>   		
  	</tr>
  	<tr>
		<th>상세내용<input type = "text" id = "detail"></th><th>금액<input type = "text" id = "money"></th>
	</tr>
</table>
<br>
<!-- butten 크기(아직 미정)-->
<input type = "button" class="btn btn-sm btn-primary btn-block" value = "추가" id = "addBtn">
</body>
</html>