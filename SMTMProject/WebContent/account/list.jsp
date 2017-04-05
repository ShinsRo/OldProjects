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
<script type="text/javascript">
   $(document).ready(function() {
      $("#listTable tr").click(function() {
         //alert($(this).text());
         $(".form-signin").submit();
      });//click
   });
 
</script>
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
   <!--    <div id="fullscreen_bg" class="fullscreen_bg"/> -->
   <form class="form-signin" id="listForm" action="${pageContext.request.contextPath}/DispatcherServlet">
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
                                 <th>ID</th>
                                 <th>이름</th>
                                 <th>지출금액</th>
                                 <th>수입금액</th>
                                 <th>수정</th>
                                 <th>삭제</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>

                                 <td>3/14</td>
                                 <td>JAVA</td>
                                 <td>김승신</td>
                                 <td>300,000</td>
                                 <td>0</td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">수정</a></td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">삭제</a></td>
                              </tr>
                              <tr>

                                 <td>3/14</td>
                                 <td>KING</td>
                                 <td>오남준</td>
                                 <td>100,000</td>
                                 <td>0</td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">수정</a></td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">삭제</a></td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>JAVA</td>
                                 <td>김승신</td>
                                 <td>0</td>
                                 <td>300,000</td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">수정</a></td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">삭제</a></td>
                              </tr>
                              <tr>

                                 <td>3/15</td>
                                 <td>KING</td>
                                 <td>오남준</td>
                                 <td>50,000</td>
                                 <td>0</td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">수정</a></td>
                                 <td><a href="http://www.jquery2dotnet.com"
                                    class="btn btn-sm btn-primary btn-block" role="button">삭제</a></td>
                              </tr>

                           </tbody>
                        </table>
                        <a href="http://www.jquery2dotnet.com"
                           class="btn btn-sm btn-primary btn-block" role="button">추가</a>

                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </form>


</body>
</html>