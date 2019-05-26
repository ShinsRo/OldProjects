<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>error</title>
</head>
<body>
<script type="text/javascript">
	alert("오류가 발생하여 로그인 화면으로 돌아갑니다.");
	location.href = "${pageContext.request.contextPath}/member/login.jsp"
</script>
</body>
</html>