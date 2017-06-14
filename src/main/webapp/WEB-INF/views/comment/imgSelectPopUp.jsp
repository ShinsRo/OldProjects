<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function pictureFormFnc() {
		var picNo = opener.commentRegForm.picno.value;
		var currentPictureId = opener.commentRegForm.currentPicId.value;
		document.fileForm.action="${pageContext.request.contextPath}/stackImg.do?picno="+picNo+"&currPicId="+currentPictureId;
		document.fileForm.submit();
	}
</script>
<body>
<form enctype="multipart/form-data" action="" name = "fileForm" method = "post">
	<input type=file name=file>
	<input type=button value='완료' onclick="pictureFormFnc()">
</form>
</body>
</html>