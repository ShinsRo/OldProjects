<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정하기</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/board.css" type="text/css">
</head>
<body>
	<jsp:include page="/layout/header.jsp" />
	<br>
	<div class="container">
	<div class = "row">
	 <div class="col-md-2">
	 </div>
	 	 <div class="col-md-8">
	<form method=post
			action="${pageContext.request.contextPath}/DispatcherServlet">
			<input type=hidden name=command value=boardUpdate></input>
	<table class="table table-striped table-hover" id = "detail-table">
               <tr>
                  <td><input class="form-control" id="inputdefault" type="text"
								name="boardNO" value = "${bvo.boardNO }" readonly></td>
                  <td colspan="2"><input class="form-control" id="inputdefault" type="text"
								name="title" value = "${bvo.title }"></td>
               </tr>	
               <tr>
                  <td><input class="form-control" id="inputdefault" type=text 
                  				name="writer" value="작성자 : ${requestScope.bvo.id }" readonly> </td>
                  <td colspan="2"><input class="form-control" id="inputdefault" type="text"
								name="timePosted" value = "${requestScope.bvo.timePosted }" readonly>
                  </td>

               </tr>
               <tr>
                  <td colspan="4"><textarea rows="15" cols="135" name="content">${requestScope.bvo.content}</textarea></td>
               </tr>
               <tr>
								<td colspan = "12" align="center"><!-- <input type="submit" value="수정하기"
									class="action"></input> -->
									 <button type="submit" class="btn btn-primary">수정하기</button>
									</td>
							</tr>
							
    </table>
    </form>
    </div>
	</div>
	</div>
</body>
</html>














