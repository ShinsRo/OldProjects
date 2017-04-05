<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
<body bgcolor="<%=request.getAttribute("color")%>"><!-- onunload="closeWindow()" -->
 <%=request.getAttribute("message")%> <br><br>
<input type="button" value="확인" onclick="closeWindow('<%=request.getAttribute("flag")%>')">
</body>
</html>