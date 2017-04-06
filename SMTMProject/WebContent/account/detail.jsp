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
<!-- <script type="text/javascript">
   $(document).ready(function() {
      $("#listTable tr").click(function() {
         //alert($(this).text());
         $(".form-signin").submit();
      });//click
   });
   var defaultCSS = document.getElementById('bootstrap-css');
   function changeCSS(css) {
      if (css)
         $('head > link')
               .filter(':first')
               .replaceWith(
                     '<link rel="stylesheet" href="'+ css +'" type="text/css" />');
      else
         $('head > link').filter(':first').replaceWith(defaultCSS);
   }
</script> -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
      $("input[name=update]").click(function(){
    	  open("detail_update.jsp","update","width=410,height=185,top=150,left=200");
      });// updateBtn.click
      
      $("input[name=delete]").click(function(){
         if(confirm("정말로 삭제하시겠습니까?")){
            $.ajax({
               type:"get",
               url:"DispatcherServlet",
               data:"command=delete&no=1"
            });//ajax
            alert("삭제되었습니다.");
         }else{
            alert("삭제가 취소되었습니다.");
         }
      });// deleteBtn.click
      $("#insertBtn").click(function(){
    	  open("detail_insert.jsp","update","width=410,height=185,top=150,left=200");
      })
   })
</script>
</head>
<body>
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
                                 <th>금액</th>
                                 <th>수정</th>
                                 <th>삭제</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>

                                 <td>3/14</td>
                                 <td>07:00</td>
                                 <td>커피</td>
                                 <td>-3,000</td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "수정" name = "update" id = "updateBtn"></td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "삭제" name = "delete" id = "deleteBtn"></td>
                              </tr>
                              <tr>

                                 <td>3/14</td>
                                 <td>12:00</td>
                                 <td>점심</td>
                                 <td>-8,000</td>
                                  <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "수정" name = "update" id = "updateBtn"></td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "삭제" name = "delete" id = "deleteBtn"></td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>14:00</td>
                                 <td>빌린돈 받음</td>
                                 <td>+10,000</td>
                                  <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "수정" name = "update" id = "updateBtn"></td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "삭제" name = "delete" id = "deleteBtn"></td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>19:00</td>
                                 <td>2월 교통비</td>
                                 <td>-50,000</td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "수정" name = "update" id = "updateBtn"></td>
                                 <td><input type = "button" class="btn btn-sm btn-primary btn-block" value = "삭제" name = "delete" id = "deleteBtn"></td>
                              </tr>

                           </tbody>
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