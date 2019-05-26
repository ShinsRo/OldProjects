<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<meta charset="UTF-8">
<title>아이디 중복확인 팝업창</title>

<script type="text/javascript">
function closeWindow(result){
	var of=window.opener.document.testForm;
	
	if(result==""){//사용불가일 경우 본 창의 아이디를 지운 후 포커스 준다
		of.id.value="";
		of.data.value="";
		of.id.focus();			
		self.close();
	}else{// 사용가능할 경우 본 창의 패스워드에 포커스를 준다.
		of.data.value = of.id.value;
		//of.password.focus();
		self.close();
	}		
}
</script>
</head>
<body style="text-align: center;"><!-- onunload="closeWindow()" -->
<br>
<span style=" font-size: 17px; color: #5c616a;
   font: Arial, Helvetica, sans-serif"><%=request.getAttribute("message")%> </span>
<br><br>
<button type="button" class="btn btn-primary" onclick="closeWindow('<%=request.getAttribute("flag")%>')">확인</button>
<%-- <input type="button" value="확인" onclick="closeWindow('<%=request.getAttribute("flag")%>')"> --%>
</body>
</html>