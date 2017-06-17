<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert("사진이 선택되었습니다. 게시하기 위해 사진 붙이기를 눌러주세요.");
		this.close();
		window.opener.showPasteBtn();
		window.opener.focus();
	</script>
</body>
</html>