<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>title: </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board.css" type="text/css">
</head>
<body>
<jsp:include page="/layout/header.jsp" />
<form method=post action="${pageContext.request.contextPath}/DispatcherServlet">
	<input type=hidden name=command value=boardUpdate></input>	
	<table class="content">
	<tr>
		<td>
			<table>
				<tr>
					<td>
					글번호: <input type=text name="boardNO" value=${bvo.boardNO } readonly></input>
					| 타이틀:<input type=text name="title"></input>					
					</td></tr><tr>	<td>
						<font size="2">
						작성자: <input type=text name=writer value=${bvo.id } readonly></input>| 
						작성일시:${bvo.timePosted } 
					</font>
					</td>
				</tr>
				<tr>
					<td>						
	<textarea rows="15" cols="75" name="content"></textarea>
					</td>
				</tr>
				<tr>
				<td valign="middle">						
					<input type="submit" value="수정하기" class="action"></input>			
					</td>				
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>	
</body>
</html>














