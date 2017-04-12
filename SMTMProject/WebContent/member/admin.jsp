<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자 page(login 후 authority check 후 이동)</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
table{
	border-collapse : collapse;
}
table, th, td{
	border:1px solid black;
}
th, td{
	padding : 15px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#boardBtn").click(function(){
		//게시판 관리 클릭시 게시판으로 이동,\
		location.href = "${pageContext.request.contextPath}/DispatcherServlet?command=board";
	});
	$("#authorityBtn").click(function(){
		$.ajax({
			 type:"get",
		      url:"${pageContext.request.contextPath}/DispatcherServlet",
		      data:"command=authority",
		      dataType:"json",
			success:function(data){
				var list=null;
				for(var i=0; i<data.length; i++){
					if(data[i].authority ==0){
						list +="<tr>"+
						"<td>"+data[i].id+"</td>"+
						"<td>"+data[i].name+"</td>"+
						"<td>일반회원</td>"
						+"</tr>";
					}else{
						list +="<tr>"+
						"<td>"+data[i].id+"</td>"+
						"<td>"+data[i].name+"</td>"+
						"<td>관리자회원</td>"
						+"</tr>";
					}
					
				}
						$("#memberList").html(list);
			}
			});	
		}); //authorityBtn
		
	$("#memberList").on("click","td",function(){
		var authoNum = $(this).parent().text(); // king정현지0 
		var id = $(this).parent().children().eq(0).text(); // king
		if(authoNum.endsWith("1")){ // 1일때 click -> 권한 해제 alert
			var authority = confirm("권한을 해제하시겠습니까?");
			if(authority){
			$.ajax({
				type:"get",
				url:"${pageContext.request.contextPath}/DispatcherServlet",
				data:"command=setAuthority&id="+id+"&authoNum=1",
				success:function(){
					alert("권한 해제 성공");
					location.href="${pageContext.request.contextPath}/member/admin.jsp";
					}
				})  
			}
		}
		
		else if(authoNum.endsWith("0")){
			var authority = confirm("권한을 부여하시겠습니까?");
			if(authority){
			$.ajax({
				type:"get",
				url:"${pageContext.request.contextPath}/DispatcherServlet",
				data:"command=setAuthority&id="+id+"&authoNum=0",
				success:function(){
					alert("권한 부여 성공");
					location.href="${pageContext.request.contextPath}/member/admin.jsp";
					}
				})  
			}
		}
	});// <td> click -> 권한 부여 및 해제
/* 		if(authoNum.endsWith("1")){
			alert(1);
		}
		else if(authoNum.endsWith("0")){
			alert(0);
		} */
		/* var id = $(this).siblings().eq(0).text();
		if
  		var authority = confirm("권한을 부여하시겠습니까?");
		if(authority){
			alert("asd");
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/DispatcherServlet",
			data:"command=setAuthority&id="+id+"&authoNum="+authoNum,
			success:function(){
				alert("권한 부여 성공");
				location.href="${pageContext.request.contextPath}/member/admin.jsp";
				}
			})  
		}  */
	
	});// ready

</script>
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<div class="container" align="center">
  <h2>Hello, administrator</h2>
  <p>authority 0: 사용자 / authority 1 : 관리자 </p>
  
  <div class="btn-group">
    <button type="button" id="authorityBtn" class="btn btn-primary">사용자 권한 부여</button>
     <button type="button" id="boardBtn" class="btn btn-primary">게시판관리</button>
    </div>
    <br><br>
    
    <table style="text-align: center;">
		<thead>
			<tr>
				<th style="text-align: center;">ID</th><th>NAME</th>
				<th>AUTHORITY</th>
			</tr>
		</thead>
		<tbody id="memberList"></tbody>
	</table>
    
    
    <!-- 
    	1.사용자 권한 부여 click -> ajax로 MemberDAO에서 getAllList 받아와서
    						 table로 회원 아이디, 이름, 권한(0 이면 회원/1이면 관리자) 뿌려주기 
    	2. 사용자의 id or name <td> click -> alert(confirm("권한을 부여하시겠습니까?")) 
    								    -> 확인하면 MemberDAO update로 authority 1로 set 해주기
    						 -->
<!--     <button type="button" class="btn btn-primary">Samsung</button>
 -->    
    <!--   <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
         게시글 <span class="caret"></span></button>
      <ul class="dropdown-menu" role="menu">
        <li><a href="#">삭제</a></li>
        <li><a href="#">수정</a></li> 
      </ul> -->
</div>
</body>
</html>