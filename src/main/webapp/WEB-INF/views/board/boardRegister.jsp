<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
	$("#add_picture_btn").click(function() {
		alert("test");
	});//add_picture_btn
</script>
<div align="left">
	<form enctype="multipart/form-data"
	action = "${pageContext.request.contextPath }/boardRegister.do" method="post">
	작성자 : <input type = text name = id value = "${sessionScope.mvo.id}"><br>
	게시글 제목 :  <input type = text name = title><br>
	<br><br>
	물려주기할 물건의 정보를 입력해주세요.
	<hr>
	<span id = "add_picture">
	<input type = file name = picture><br>
	물건 제목	: <input type = text name = ptitle><br>
	물건 종류	: <input type = text name = kind><br>
	</span>
	<input type = button id = "add_picture_btn" value = "상품 추가"><br>
	게시글 내용 <br> <textarea cols="53" rows="15" id="content" name="bcontent"></textarea><br>
</form>
</div>