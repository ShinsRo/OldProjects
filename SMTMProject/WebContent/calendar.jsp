<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

   <meta charset="UTF-8">
   <title>Calendar</title>
   <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700">
   <link rel= "stylesheet" href="css/calendar_template.css">
   
   <script src="//code.jquery.com/jquery.min.js"></script>
   <script type="text/javascript">
   var monthArr = [0,"January","February","March", "April", "May", "June", "July", "August", "September", "October","November","December"];
   var monthPos = 4;
   var yearPos = 2017;
   
   $(document).ready(function(){
      $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
         //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
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
         $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
            //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
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
         $.getJSON("DispatcherServlet","command=getCalendarList&year="+yearPos+"&month="+monthPos, function(data) {
            //alert("월 : " + data.month + " 마지막 날짜 : " + data.lastDayOfMonth + " 시작 요일 : " + data.firstDayOfMonth);
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
      });
      $("#calendar-body").on("click", "td", function(){
            //alert($(this).children("#dayPos").text());
            location.href = "${pageContext.request.contextPath}/account/detail.jsp?&today="+
                  yearPos+"/0"+
                  monthPos+"/0"+                  
                  $(this).children("#dayPos").text();
      });
   });//ready
   </script>
</head>
<body>

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