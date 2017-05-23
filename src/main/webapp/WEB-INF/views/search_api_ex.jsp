<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">

</script>
<!-- jQuery와 Postcodify를 로딩한다 -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<!-- 검색 기능을 표시할 <div>를 생성한다 -->
<!-- <div id="postcodify"></div>

주소와 우편번호를 입력할 <input>들을 생성하고 적당한 name과 id를 부여한다
<input type="text" name="" id="postcode" value="" /><br />
<input type="text" name="" id="address" value="" /><br />
<input type="text" name="" id="details" value="" /><br />
<input type="text" name="" id="extra_info" value="" /><br />

위에서 생성한 <div>에 검색 기능을 표시하고, 결과를 입력할 <input>들과 연동한다
<script>
    $(function() { $("#postcodify").postcodify({
        insertPostcode5 : "#postcode",
        insertAddress : "#address",
        insertDetails : "#details",
        insertExtraInfo : "#extra_info",
        hideOldAddresses : false
    }); });
</script> -->
home
<!-- 주소와 우편번호를 입력할 <input>들을 생성하고 적당한 name과 class를 부여한다 -->
<input type="text" name="" class="postcodify_postcode5" value="" />
<button id="postcodify_search_button">검색</button><br />
주소 <input type="text" name="" class="postcodify_address" value="" readonly="readonly"/><br />
상세주소 <input type="text" name="" class="postcodify_details" value="" /><br />

<!-- "검색" 단추를 누르면 팝업 레이어가 열리도록 설정한다 -->
<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
</body>
</html>