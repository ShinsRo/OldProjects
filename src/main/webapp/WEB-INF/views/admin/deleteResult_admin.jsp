<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	alert("게시물이 정상적으로 삭제되었습니다.");
	location.href = "${pageContext.request.contextPath}/getBoardList_admin.do";
</script>
