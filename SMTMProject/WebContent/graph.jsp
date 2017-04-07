<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>     
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>     
<title>주간 입/출 내역 현황</title>    
<!-- 구글차트를 불러오기위한 코드 -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>     
<!-- $jquery를 위한 한줄 추가! -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">       
 google.load('visualization', '1', {packages: ['corechart']});     
//차트 로드 
</script> 
<script type="text/javascript">      
//data : 그래프 정보
	
  $(document).ready(function(){
	$.ajax({
		type:"get",
		url:"${pageContext.request.contextPath}/DispatcherServlet",
		data:"command=graph&week=1",
		dataType:"json",
		success:function(data){		
			var chartdata =  new google.visualization.DataTable();
			chartdata.addColumn('string', 'Day'); 
			chartdata.addColumn('number', '수입');
			chartdata.addColumn('number', '지출');
			chartdata.addRows(7);
			chartdata.setCell(0, 0, '일'); 
			chartdata.setCell(1, 0, '월'); 
			chartdata.setCell(2, 0, '화'); 
			chartdata.setCell(3, 0, '수');
			chartdata.setCell(4, 0, '목'); 
			chartdata.setCell(5, 0, '금'); 
			chartdata.setCell(6, 0, '토');
			
			for(var i = 0;i<data.length;i++){
				chartdata.setCell(i,1,data[i].totalIncome);
				chartdata.setCell(i,2,data[i].totalSpend);
			}
			
			drawVisualization(chartdata);
		}
	});
	
	$("#graphInfo").change(function(){
		 $.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/DispatcherServlet",
			data:"command=graph&week="+$("#graphInfo").val(),
			dataType:"json",
			success:function(data){		
				var chartdata =  new google.visualization.DataTable();
				chartdata.addColumn('string', 'Day'); 
				chartdata.addColumn('number', '수입');
				chartdata.addColumn('number', '지출');
				chartdata.addRows(7);
				chartdata.setCell(0, 0, '일'); 
				chartdata.setCell(1, 0, '월'); 
				chartdata.setCell(2, 0, '화'); 
				chartdata.setCell(3, 0, '수');
				chartdata.setCell(4, 0, '목'); 
				chartdata.setCell(5, 0, '금'); 
				chartdata.setCell(6, 0, '토');
				
				for(var i = 0;i<data.length;i++){
					chartdata.setCell(i,1,data[i].totalIncome);
					chartdata.setCell(i,2,data[i].totalSpend);
				}
				
				drawVisualization(chartdata);
			}
		}); //ajax
	})//change
}) 


function drawVisualization(chartdata) {         
	var options = { //차트 옵션 (더 많은 옵션은 구글 차트 api 참고)
	hAxis: {title: "요일"},// x축 옵션
	seriesType: "bars",
	series: {7: {type: "line"}}
	};
	
	var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	chart.draw(chartdata, options); //LineChart Column이건 원하는 모양의 차트를 넣으면 모양이 바뀐다!

}

google.setOnLoadCallback(drawVisualization);
</script>  
</head>  
<body>     
주간 입/출 내역 현황
<select id = "graphInfo">
	<option value = "1">1주차</option>
	<option value = "2">2주차</option>
	<option value = "3">3주차</option>
	<option value = "4">4주차</option>
</select>
<%-- <jsp:include page="/layout/chartHeader.jsp" /> --%>
<div id="chart_div" style="width: 900px; height: 500px;"></div>   
</body> 
</html>