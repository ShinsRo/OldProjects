<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>     
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>     
<title>월간 입/출 내역 현황</title> 
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
chartdata.addColumn('string', 'week'); 
chartdata.addColumn('number', '수입');
chartdata.addColumn('number', '지출');
chartdata.addRows(${param.wi});
var week_name = ['1주차','2주차','3주차','4주차','5주차','6주차'];
var month_arr = ['','1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'];
var m = month_arr[${param.month}];
// 차트 데이터 초기 설정 일,월,화,수,목,금,토
for(var i = 0;i<${param.wi};i++){
   chartdata.setCell(i,0,week_name[i]);
}

  $(document).ready(function(){
     $("#Graph").click(function(){
        location.href = "${pageContext.request.contextPath}/graph.jsp?month=${param.month}&day=${param.day}"
     })
      $.ajax({
         type:"get",
         url:"${pageContext.request.contextPath}/DispatcherServlet",
         data:"command=monthGraph&wi="+${param.wi},
         dataType:"json",
         success:function(data){
             for(var i=0; i<data.length; i++){
                    if(data[i].today == i+1+"주차"){
                       chartdata.setCell(i,1,data[i].totalIncome);
                       chartdata.setCell(i,2,data[i].totalSpend);
                    }
                 }
            drawVisualization(chartdata,m);
         }
      })//ajax;
  })
     
function drawVisualization(chartdata,m) {         
   var options = {
   title : m,
   hAxis: {title: "주차"},
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
월간 입/출 내역 현황</span>
<input type = "button" class="btn btn-default" id = "Graph" value = "주간그래프"></input>
<div id="chart_div" style="width: 900px; height: 500px;"></div> 
</center>  
</body> 
</html>