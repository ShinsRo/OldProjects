<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
   href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
   rel="stylesheet" id="bootstrap-css">
<style type="text/css">
body {
   background-color: #white;
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

<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
/* 
	list에서 detail로 이동시, 페이지가 로드되는 순간
	Ajax	->	DetailController	-> AccountDAO-GetDatilList	-> detail.jsp
		command = detail										JSON으로 getDatailList에서 받은 상세내역 JSON으로 받아옴
		today = ${param.today}
*/
   $(document).ready(function(){   
	   if(${empty sessionScope.mvo}){
			  location.href = "${pageContext.request.contextPath}/member/login.jsp";
		  }
     var m;
      $.ajax({
        type:"get",
        url:"${pageContext.request.contextPath}/DispatcherServlet",
        dataType:"json",
        data:"command=detail&today=${param.today}",
        success:function(data){
         for(var i = 0;i<data.length;i++){
        	  m += 
	              "<tr>"+
	              "<input type = 'hidden' name='no' id = 'no' value="+data[i].no+">"+
	              "<td>"+data[i].today+"</td><td>"+data[i].time+"</td><td>"+data[i].detail+"</td><td>"+data[i].income+"</td><td>"+data[i].spend+"</td>"+
	              "<td><input type = 'button' class='btn btn-sm btn-primary btn-block' value = '수정' name = 'update' id = 'updateBtn'></td>"+
	              "<td><input type = 'button' class='btn btn-sm btn-primary btn-block' value = '삭제' name = 'delete' id = 'deleteBtn'></td>"+            
	              "</tr>";
           }//for
           $("#info").html(m);
        }//success
   	});//ajax
   	
   	
   	/* 
   		삭제버튼 클릭시 -> DeleteController
   					command = delete
   					no : 클릭한 td의 sibling.eq(0)
   					today
   	*/
      $("#info").on("click","#deleteBtn", function(){
    	  if(confirm("정말로 삭제하시겠습니까?")){
    		  $.ajax({
                 type:"get",
                 url:"${pageContext.request.contextPath}/DispatcherServlet",
                 data:"command=delete&no="+$(this).parent().siblings().eq(0).val()+"&today=${param.today}"
              });//ajax
              alert("삭제되었습니다.");
              location.href="${pageContext.request.contextPath}/account/detail.jsp?today=${param.today}";
           }else{
              alert("삭제가 취소되었습니다.");
           }
 	    });//delete click 
   	
   	/*
   		추가 버튼 클릭시 -> AddController
   					command = add
   					today
   	*/
      $("#insertBtn").click(function(){
          open("detail_insert.jsp?today=${param.today}","insert","width=410,height=185,top=150,left=200");
       });//insert click
      
      /*
      	수정 버튼 클릭시	-> UpdateController
      		command = update
      		no
      		today
      */
      $("#info").on("click","#updateBtn", function(){
			//alert($(this).parent().siblings().eq(1).text());
		
			open("detail_update.jsp?no="+$(this).parent().siblings().eq(0).val()+"&today="+$(this).parent().siblings().eq(1).text(),"update","width=500,height=185,top=150,left=200");				
		});//update click  
      
      
   });//ready
      

    
 
</script>
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
   <!--    <div id="fullscreen_bg" class="fullscreen_bg"/> -->
   <form class="form-signin" id="listForm" action="DispatcherServlet">
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
                                 <th>날짜</th>
                                 <th>시간</th>
                                 <th>상세정보</th>
                                 <th>수입</th>
                                 <th>지출</th>
                                 <th>수정</th>
                                 <th>삭제</th>
                              </tr>
                           </thead>
                           <tbody id = "info"></tbody>
                
                        </table>
                           <input type = "button" class="btn btn-sm btn-primary btn-block" value = "추가" id = "insertBtn">
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </form>
</body>
</html>