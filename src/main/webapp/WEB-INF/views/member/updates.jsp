<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/member/updates.css" > 
<h3>마이페이지</h3>
<form action="${pageContext.request.contextPath }/register.do">
	 <input type="text" required="required" name="id" placeholder="아이디" id="id"><br> 
		<input type="password" name="password" required="required" placeholder="비밀번호" id="password"><br>
	<input type="text" name="name" required="required" placeholder="이름" id="name"><br> 
		<input type="text" name="" 	class="postcodify_postcode5" value="" placeholder="우편번호" /><br>
	<button id="postcodify_search_button">검색</button>
	<br />  <input type="text" name="" class="postcodify_address" 	value="" readonly="readonly" placeholder="주소"  /><br /> 
	<input type="text" name="" class="postcodify_details" value="" placeholder="상세주소" /><br> 
		 <input id="tels" required="required" name="tel" type="tel" placeholder="전화번호" /><br>
	<input id="jobs" required="required" name="job" type="text" placeholder="직업" /><br> 
	<input type="submit" value="회원수정" />&nbsp<input type="button" value="회원탈퇴">
</form>
