<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<style type="text/css">

.rgb{
margin: 30px 10px 0px 0px;
max-width: 25px; /* 넓이를 지정 */
height: 25px;
}
.font{
margin: 30px 10px 0px 0px;
}
.imgColor{
   margin: 50px 0px 0px 100px; /* 여백을 적용 4가지 조건이 가능 위쪽, 오른쪽, 아래쪽, 왼쪽 순서 */
   max-width: 25px; /* 넓이를 지정 */
   height: 25px;
  /*  font-size: 15px; 
   font: Arial, Helvetica, sans-serif; */
   }
#textView,#balanceView{
margin: 0px 0px 0px 10px;
font-size: 13px; 
font: Arial, Helvetica, sans-serif;
font-weight: bold;
color: #5c616a;
/* color: #5c616a; */
}

</style>
   <meta charset="UTF-8">
   <title>Calendar</title>
   <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700">
   <link rel= "stylesheet" href="css/calendar_template.css">
   
   <script src="//code.jquery.com/jquery.min.js"></script>
   <script type="text/javascript">
   /*
   2017.04.11 마지막 수정
   Calendar.jsp
   - 사용자에게 해당 월의 일별 지출/수입 내역을 출력하여 제공
   - 달력 일을 클릭하면 일별 상세보기 창으로 이동
   - 최초 진입(getCurrentController로부터 진입) 시 현재 일을 기준으로 달력을 보여줌
   */
   // monthPos : 현재 보여지는 월
   var monthArr = [0,"January","February","March", "April", "May", "June", "July", "August", "September", "October","November","December"];
   var monthPos = ${requestScope.month};
   var yearPos = ${requestScope.year};
  
   var day;
   function toGraph() {
	      location.href = "${pageContext.request.contextPath}/graph.jsp?month="+monthPos
	            +"&day="+day;
	   }
   
   //페이지 초기화
   $(document).ready(function(){
      $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
         //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
         day = data.lastDayOfMonth;
         $("#balanceView").text("${sessionScope.mvo.total}");
         var html = "<img class='imgColor' src='${pageContext.request.contextPath}/img/";
          var span = "<span id = 'textView'>이번달 당신의 지출현황</span>";
	        if(data.ryb =="red"){
	           $("#imgView").html(html+"red.png'>"+span);
	        }else if(data.ryb == "yellow"){
	           $("#imgView").html(html+"yellow.png'>"+span);
	        }else if(data.ryb = "green"){
	           $("#imgView").html(html+"green.png'>"+span);
	       
	        }
         $(".year").text(yearPos);
         $("#month").html(monthArr[data.month]);
         for(var j = data.firstDayOfMonth-1; j >= 0 ; j--){
            $("#calendar-body td:eq("+j+")").html("");
         }
         
        
         for(var i = 1 ; i <= data.lastDayOfMonth; i ++){
            var income = 0;
            var spend = 0;
            if(data.listOnMonth[i] != null){
               income += data.listOnMonth[i].totalIncome;
               spend += data.listOnMonth[i].totalSpend;
            }
            
            $("#calendar-body td:eq("+(i+data.firstDayOfMonth)+")").html(
                  "<br><span id = 'dayPos'>"+i+"</span>"+
                  "<br><br><br>"+
                  "<span class = 'income'>"+income+"</sapn><br><br>"+
                  "<span class = 'spend'>"+spend+"</span>");
         }
         
      });//getJSON
      $(".btn-prev").click(function() {
         //alert("left");
         if(1<monthPos){
            monthPos -= 1;
            //alert(monthPos);
         }
         else{
            monthPos = 12;
            yearPos -= 1;
         }
         if((${requestScope.month}-monthPos) == 0){
        	 $("#conditionView").show();
         }else{
        	 $("#conditionView").hide();
         }
         $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
            //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
            $(".year").text(yearPos);
            $("#month").html(monthArr[data.month]);
            $("#calendar-body td").text("");
            for(var j = data.firstDayOfMonth-1; j >= 0 ; j--){
               $("#calendar-body td:eq("+j+")").html("");
            }
            for(var i = 1 ; i <= data.lastDayOfMonth; i ++){
               var income = 0;
               var spend = 0;
               if(data.listOnMonth[i] != null){
                  income += data.listOnMonth[i].totalIncome;
                  spend += data.listOnMonth[i].totalSpend;
               }
               
               $("#calendar-body td:eq("+(i+data.firstDayOfMonth)+")").html(
                     "<br><span id = 'dayPos'>"+i+"</span>"+
                     "<br><br><br>"+
                     "<span class = 'income'>"+income+"</sapn><br><br>"+
                     "<span class = 'spend'>"+spend+"</span>");
            }
           
         });//getJSON
      })//move to prev
      $(".btn-next").click(function() {
         //alert("right");
         if(12>monthPos){
            monthPos += 1;
            //alert(monthPos);
         }
         else{
            monthPos = 1;
            yearPos += 1;
         }
         
         if((${requestScope.month}-monthPos) == 0){
        	 $("#conditionView").show();
         }else{
        	 $("#conditionView").hide();
         }
         $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
            //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
            $(".year").text(yearPos);
            $("#month").html(monthArr[data.month]);
            $("#calendar-body td").text("");
            for(var j = data.firstDayOfMonth-1; j >= 0 ; j--){
               $("#calendar-body td:eq("+j+")").html("");
            }
            for(var i = 1 ; i <= data.lastDayOfMonth; i ++){
               var income = 0;
               var spend = 0;
               if(data.listOnMonth[i] != null){
                  income += data.listOnMonth[i].totalIncome;
                  spend += data.listOnMonth[i].totalSpend;
               }
               
               $("#calendar-body td:eq("+(i+data.firstDayOfMonth)+")").html(
                     "<br><span id = 'dayPos'>"+i+"</span>"+
                     "<br><br><br>"+
                     "<span class = 'income'>"+income+"</sapn><br><br>"+
                     "<span class = 'spend'>"+spend+"</span>");
            }
         });//getJSON
      });
      $("#calendar-body").on("click", "td", function(){
            //alert($(this).children("#dayPos").text());
            var dayPos = $(this).children("#dayPos").text();
            if(monthPos < 10){
               monthPos = "0"+monthPos;
            }
            if(dayPos < 10){
               dayPos = "0" + dayPos;
            }
               location.href = "${pageContext.request.contextPath}/account/detail.jsp?&today="+
                yearPos+"/"+
                monthPos+"/"+                  
                dayPos;
      });
   });//ready
   </script>
</head>
<body>
<jsp:include page="layout/header.jsp">
   <jsp:param value="calendar" name="nowPage"/>
</jsp:include>
<div id="front-padding">
</div>
<div id = "conditionView">
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img class='rgb' src='${pageContext.request.contextPath}/img/red.png'>
<span style="font-weight:bold; font-size: 13px; color: #5c616a;
   font: Arial, Helvetica, sans-serif">Danger</span><br>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img class='rgb' src='${pageContext.request.contextPath}/img/yellow.png'>
<span style="font-weight:bold; font-size: 13px; color: #5c616a;
   font: Arial, Helvetica, sans-serif">Warning</span><br>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img class='rgb' src='${pageContext.request.contextPath}/img/green.png'>
<span style="font-weight:bold; font-size: 13px; color: #5c616a;
   font: Arial, Helvetica, sans-serif">Stable</span>
<br>
	<div id ="imgView"></div>

<br><br>

</div>
<div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<font style="font-size: 13px; font: Arial, Helvetica, sans-serif;
font-weight: bold; color: #5c616a;">${sessionScope.mvo.name}님의 잔액</font>
<span  id = "balanceView"></span>
</div>
   <div class="container">
         <div class = "year"></div>
      <div class="calendar">
         <header>            

            <h2 id = "month"></h2>

            <a class="btn-prev fontawesome-angle-left"></a>
            <a class="btn-next fontawesome-angle-right"></a>
         
         </header>
         <table>
         
            <thead>
               
               <tr>
                  
                  <td>Su</td>
                  <td>Mo</td>
                  <td>Tu</td>
                  <td>We</td>
                  <td>Th</td>
                  <td>Fr</td>
                  <td>Sa</td>

               </tr>

            </thead>

            <tbody id = "calendar-body">
               <!-- class="prev-month" -->
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>
               <tr>
<!--                   <td class="event">10</td> -->
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>
               <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>

               <tr>
                  <!-- <td class="current-day event">23</td> -->
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>
               <tr>
                  <!-- <td class="next-month">1</td> -->
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td ></td>
                  <td></td>
               </tr>

            </tbody>

         </table>

      </div> <!-- end calendar -->

   </div> <!-- end container -->
   
</body>
</html>