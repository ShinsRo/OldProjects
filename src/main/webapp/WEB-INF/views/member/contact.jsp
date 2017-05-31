<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function initialize() {
		var Y_point = 37.402108; // Y 좌표
		var X_point = 127.106700; // X 좌표

		var zoomLevel = 16; // 지도의 확대 레벨 : 숫자가 클수록 확대정도가 큼

		var markerTitle = "위즈소프트"; // 현재 위치 마커에 마우스를 오버을때 나타나는 정보
		var markerMaxWidth = 300; // 마커를 클릭했을때 나타나는 말풍선의 최대 크기
		// 말풍선 내용
		var contentString = '<div>' + '<h2>한국소프트웨어기술진흥협회</h2>'
				+ '<p>kosta 145기 벌써 다 했조</p>' + '</div>';

		var myLatlng = new google.maps.LatLng(Y_point, X_point);
		var mapOptions = {
			zoom : zoomLevel,
			center : myLatlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(document.getElementById('map_view'),
				mapOptions);

		var marker = new google.maps.Marker({
			position : myLatlng,
			map : map,
			title : markerTitle
		});

		var infowindow = new google.maps.InfoWindow({
			content : contentString,
			maxWidth : markerMaxWidth
		});

		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open(map, marker);
		});
	}
</script>

<body onload="initialize()">
	<section id="page-breadcrumb">
		<div class="vertical-center sun">
			<div class="container">
				<div class="row">
					<div class="action">
						<div class="col-sm-12">
							<h1 class="title">Contact US</h1>
							<p>Stay close</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--/#action-->
	<section id="map-section">
        <div class="container">
           <div id="map_view" style="width: 940px; height: 350px;"></div>
            <div class="contact-info">
                <h2>Contacts</h2>
                <address>
                E-mail: <a href="mailto:goodmove@kosta145.com">goodmove@kosta145.com</a> <br> 
                Phone: 070-5039-5810<br> 
                </address>

                <h2>Address</h2>
                <address>
                경기도 성남시 분당구<br> 
                대왕판교로 670길 <br> 
               	유스페이스2 B동 8층, 9층<br> 
                </address>
            </div>
        </div>
    </section> <!--/#map-section-->     
  
</body>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB1SyVOKczvIO35hWM0-IiLGeEna0uqAvQ&callback=initMap"
	async defer></script>
