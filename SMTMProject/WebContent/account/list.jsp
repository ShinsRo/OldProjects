<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 경로변경 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<style type="text/css">
body {
   background-color: white;
   padding-top: 40px;
}

.form-signin {
   max-width: 280px;
   padding: 15px;
   margin: 100px;
   margin-top: 70px;
}

.input-group-addon {
   background-color: rgb(50, 118, 177);
   border-color: rgb(40, 94, 142);
   color: rgb(255, 255, 255);
}

.form-signup input[type="text"], .form-signup input[type="password"] {
   border: 1px solid rgb(50, 118, 177);
}

.fullscreen_bg {
   position: fixed;
   top: 0;
   right: 0;
   bottom: 0;
   left: 0;
   background-size: cover;
   background-position: 50% 50%;
   background-image:
      url('http://www.banisite.com/sample-sirenadentist/theme/images/slider/slider-8.jpg');
   background-repeat: repeat;
}
</style>
<script src="//code.jquery.com/jquery.min.js"></script>
<script
   src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!-- 
	listView 
	-jquery를 이용해 페이지를 띄우는 순간 -> GetAllListController -> AccountDAO -> list.jsp
								command = getAllList			DAO에서 사용자의 일별 총지출/총수입 JSON으로 받아옴
-->

<script type="text/javascript">

   $(document).ready(function() {
	  if(${empty sessionScope.mvo}){
		  location.href = "${pageContext.request.contextPath}/member/login.jsp";
	  }
      $.ajax({
         type:"get",
         url:"${pageContext.request.contextPath}/DispatcherServlet",
         data:"command=getAllList",
         dataType:"json",
         success:function(data){
            for(var i = 0; i < data.length; i ++){
            
               $("#info").html("<tr><td class = 'today'>"
                     +data[i].today+"</td><td>"
                     +data[i].totalSpend+"</td><td>"
                     +data[i].totalIncome+"</td></tr>"
                     +$("#info").html());
            }
         }
      });//ajax
      
       /*
       		List에서 td 클릭시, account/detail.jsp 로 이동(today값 넘김)
       */
      $("#info").on("click","td", function(){
        //alert($(this).parent().children(".today").css("color", "blue"));
        if(confirm("상세보기로 이동하시겠습니까?")){
           alert("ㅇㅋ 기달");
           var lf = document.listForm;
           location.href = "${pageContext.request.contextPath}/account/detail.jsp?&today="+$(this).parent().children(".today").text();
        }else{
           return;
        }//if
        
        
     });//td click
   });// ready
   
</script>
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
   <!--    <div id="fullscreen_bg" class="fullscreen_bg"/> -->
   <form class="form-signin" id="listForm" name = "listForm" action="${pageContext.request.contextPath}/DispatcherServlet">
      <div class="container">
         <div class="row">
            <div class="col-md-6 col-md-offset-3">
               <div class="panel panel-default">
                  <div class="panel panel-primary">

                     <h3 class="text-center">Show Me The Money</h3>

                     <div class="panel-body">

                  <input type = "hidden" name = "command" value = "detail">
                        <table class="table table-striped table-condensed" id="listTable">
                  
                           <thead>
                              <tr>
                                 <th>Date</th>
                                 <th>지출금액</th>
                                 <th>수입금액</th>
                              </tr>
                           </thead>
                           <tbody id="info">
                           
                           </tbody>
                       <!--     <tbody>
                              <tr>

                                 <td>3/14
                                 </td>
                                 <td>300,000</td>
                                 <td>0</td>
                              </tr>
                              <tr>

                                 <td>3/14</td>
                                 <td>100,000</td>
                                 <td>0</td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>0</td>
                                 <td>300,000</td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>50,000</td>
                                 <td>0</td>
                              </tr>

                           </tbody> -->
                        </table>

                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </form>


</body>
</html>