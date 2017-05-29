<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#updateForm").submit(function(){ 
    		if($("#title").val()==""){
    			alert("제목을 입력하세요!");
    			return false;
    		}
    		if($("#content").val()==""){
    			alert("본문을 입력하세요!");
    			return false;
    		}
    	});
    	$("#resetBtn").click(function(){    		
    		$("#write_form")[0].reset();
    	});
    });	
</script>

<form method="post" id="commentUpdateForm" action="${pageContext.request.contextPath}/commentUpdate.do">

		<table id="inputForm" class="table table-hover">
    <tbody align="center">
    <tr>
     <td colspan="3">
     글번호: ${cvo.cno }&nbsp;&nbsp;&nbsp;&nbsp;작성자&nbsp;&nbsp;${sessionScope.mvo.name}
     
     </td>
    </tr>
    <tr>
     <td colspan="3"> 제목 &nbsp;&nbsp;<input type=text id=title name=title value=${cvo.title } size="48" ></td>     
    </tr>
    <tr>
     <td colspan="4" align="center">
     &nbsp;&nbsp;
     <textarea rows="15" cols="75" id="content" name="content">${cvo.content }</textarea>
     </td>
    </tr> 
    <tr>
     <td colspan="4" align="center" >
      <input type="submit" value="수정하기" class="update_btn"></input>   
     </td>  
    </tr>
    </tbody>
   </table>

</form>	
