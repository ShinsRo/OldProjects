<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>     
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>  
<title>주간 입/출 내역 현황</title> 

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
#title, #graphInfo, #chart_div{
font-size: 13px;
font-weight: bold;
color: #5c616a;
}
</style> 

<script type="text/javascript" src="https://www.google.com/jsapi"></script>     
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">       
 google.load('visualization', '1', {packages: ['corechart']}); 

</script> 
<script type="text/javascript">      

//chartdata : 그래프 초기 설정 정보
var chartdata =  new google.visualization.DataTable();
chartdata.addColumn('string', 'Day'); 
chartdata.addColumn('number', '수입');
chartdata.addColumn('number', '지출');
chartdata.addRows(7);

// week : 시작 요일 인덱스
var week = 0;
var day=${param.day};
var data_length;
var week_arr = ['일','월','화','수','목','금','토'];
var month_arr = ['','1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'];
var wi = 5;
// 차트 데이터 초기 설정 일,월,화,수,목,금,토
for(var i = 0;i<7;i++){
   chartdata.setCell(i,0,week_arr[i]);
}

// m : 그래프 월 이름 정보
var m = month_arr[${param.month}];

  $(document).ready(function(){
     $("#MonthGraph").click(function(){
          location.href = "monthGraph.jsp?day=${param.day}&month=${param.month}&wi="+wi; 
        });

   $.ajax({
      type:"get",
      url:"${pageContext.request.contextPath}/DispatcherServlet",
      data:"command=graph&week=1",
      dataType:"json",
      success:function(data){
            data_length=data.length;
            if(day==30 && data_length==1 || day==31 && data_length==2){
            $("#graphInfo").append("<option value="+6+">6주차</option>");
               wi = 6;
            }
            // alert(data_length);
            // week : data.length 길이로 시작 요일 찾기
            // data.length가 1일 경우 1-1 = 0
            // 0일 경우 시작 요일 일(1주차 일요일 하나뿐)
             if(data.length == 1){
                week = 0;
             }else if(data.length == 0){
               week = 0;  
          }else{
                week = 8- data.length;
             }
             //week = data.length-1;
         for(var i=0;i<data.length;i++){
            /*      
                  data[i].today.substring(data[i].today.length-2,data[i].today.length);
                  이 코드는 DayVO 데이터가 없는 날짜를 GetGraphController에서 생성할 때
                  CalendarBean month를 사용해서 생성합니다.
                  CalendarBean month는 1,2,3,4,5....으로 시작하고
                  data.today의 경우 01,02,03으로 시작해서 서로 today길이값이 달라서
                  위 코드가 필요합니다.
            */
            
            // 요일 이름 설정
            chartdata.setCell(week, 0, week_arr[week]+"("+data[i].today.substring(data[i].today.length-2,data[i].today.length)+")"); 
            // 수입
            chartdata.setCell(week,1,data[i].totalIncome);
            // 지출
            chartdata.setCell(week,2,data[i].totalSpend);
            // 다음 요일을 설정하기 위해 증감연산자사용 0일경우 1(일요일 -> 월요일시작)
            week++;
            if(week == 7){
               week = 0;
            }
         }
         // drawVisualization : 그래프 그리기 함수
         drawVisualization(chartdata,m);
      }
      // $("#graphInfo").hide();
   });
   /* if(last)
       $("#graphInfo").append("<option value="+6+">6주차</option>"); */
   
   $("#graphInfo").change(function(){
      
      // 1주차일 경우 chartdata 재설정 이유는 재설정하지 않을 경우 요일(week)가 겹쳐서 
      // 필요한 데이터(inAndOut)가 겹침 그러므로 select하면 다시 차트 데이터 초기 설정
      for(var i = 0;i<7;i++){
         chartdata.setCell(i,0,week_arr[i]);
         chartdata.setCell(i,1,0);
         chartdata.setCell(i,2,0);
      }
      // 1주차의 경우 처음에 week를 설정 해야 하므로 val값이 1이면 다시 초기 설정
      if($(this).val() == 1){
         $.ajax({
            type:"get",
            url:"${pageContext.request.contextPath}/DispatcherServlet",
            data:"command=graph&week=1",
            dataType:"json",
            success:function(data){
               data_length=data.length;
               
               if(data.length == 1){
                         week = 0;
                }else if(data.length == 0){
                        week = 0;  
               }else{
                         week = 8- data.length;
                }
                  // week = data.length-1;
               for(var i=0;i<data.length;i++){
                  chartdata.setCell(week, 0, week_arr[week]+"("+data[i].today.substring(data[i].today.length-2,data[i].today.length)+")"); 
                  chartdata.setCell(week,1,data[i].totalIncome);
                  chartdata.setCell(week,2,data[i].totalSpend);
                  week++;
                  if(week == 7){
                     week = 0;
                  }
               }
               drawVisualization(chartdata,m);
            }
         });
      }else{
          $.ajax({
               type:"get",
               url:"${pageContext.request.contextPath}/DispatcherServlet",
               data:"command=graph&week="+$("#graphInfo").val(),
               dataType:"json",
               success:function(data){
                  for(var i=0;i<data.length;i++){
                     // data가 null일경우 정보 0으로 설정(설정하지 않을 경우 이전값이랑 겹침)
                     if(data[i]==null){
                        chartdata.setCell(week,1,0);
                        chartdata.setCell(week,2,0);
                        week++;
                        
                     }else{
                        chartdata.setCell(week, 0, week_arr[week]+"("+data[i].today.substring(data[i].today.length-2,data[i].today.length)+")"); 
                        chartdata.setCell(week,1,data[i].totalIncome);
                        chartdata.setCell(week,2,data[i].totalSpend);
                        week++;
                     }
                     // 6이 토요일 마지막이므로 week(요일카운트)가 7일 경우 0으로 초기화
                     if(week == 7){
                        week = 0;
                     }
                  }
                  drawVisualization(chartdata,m);
               }
            }); //ajax
      }
   })
}) 

function drawVisualization(chartdata,m) {         
   var options = { //차트 옵션
   title : m,
   hAxis: {title: "요일"},// x축 옵션
   seriesType: "bars",
   series: {7: {type: "line"}}
   };
   var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
   chart.draw(chartdata, options);
}

google.setOnLoadCallback(drawVisualization);

</script>  
</head>  
<body>  
<jsp:include page="layout/header.jsp"/>
<br>
<center>
<span id="title">
주간 입/출 내역 현황</span>
<select id = "graphInfo" style="font-size: 12px">
   <option value = "1">1주차</option>
   <option value = "2">2주차</option>
   <option value = "3">3주차</option>
   <option value = "4">4주차</option>
   <option value = "5">5주차</option>

</select>
<input type = "button" class="btn btn-default" id = "MonthGraph" value = "월간그래프"></input>
<div id="chart_div" style="width: 900px; height: 500px;"></div> 
</center>  
</body> 
</html>