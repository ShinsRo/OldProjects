<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="bvo" value="${requestScope.bvo}"></c:set>
<script>
	$(document).ready(function(){
		
		$("#give-me").click(function(){
			$("#myModal").modal();
		});
		$("form[name=app-form]").submit(function() {
			if($("input[name=pnos]:checkbox:checked").length<1){
				alert("물품은 하나 이상 신청되어야 합니다.");
				return false;
			}
			return true;
		});
	});
/* 	
	// 쿠키 체크 함수
	function checkCookie() {
		var itemID = getCookie("itemID");
		var thisItem = '<?=$bookID?>:<?=$itemImage ?>'; // 제품 아이디와 이미지 이름을 저장
		if (thisItem) {
			if (itemID != "" && itemID != null) {
				if (itemID.indexOf(thisItem) == -1) { //값이 없으면 
					setCookie("itemID", thisItem + "&" + itemID, 1);
				}
			} else {
				if (itemID == "" || itemID == null) {
					setCookie("itemID", thisItem + "&", 1);
				}
			}
		}
	}
	checkCookie(); */
	  function setCookie(cName, cValue, cDay){
        var expire = new Date();
        expire.setDate(expire.getDate() + cDay);
        cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
        if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
        document.cookie = cookies;
    }
	
	function getCookie(cName) {
	    cName = cName + '=';
	    var cookieData = document.cookie;
	    var start = cookieData.indexOf(cName);
	    var cValue = '';
	    if(start != -1){
	        start += cName.length;
	        var end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cValue = cookieData.substring(start, end);
	    }
	    return unescape(cValue);
	}
	
	function checkCookie(){
		<c:set value="${bvo.thumbPath}" var="img"/>
		var itemID = getCookie("itemID");
		var thisItem = $("#bno").val()+":"+$("#thumbPath").val();
		if(thisItem){
			if(itemID != "" && itemID != null){
				if (itemID.indexOf(thisItem) == -1) { //값이 없으면 
					setCookie("itemID", thisItem + "&" + itemID, 1);
				}
			} else {
				if (itemID == "" || itemID == null) {
					setCookie("itemID", thisItem + "&", 1);
				}
			}
		}
	}

</script>
<body onload="checkCookie()">
<section id="portfolio-information" class="padding-top">
       <div class="container">
           <div class="row">
               <div class="col-sm-6">
                   <img src="${pageContext.request.contextPath }/${bvo.thumbPath}" class="img-responsive" alt="썸네일">
                   <input type="hidden" id="thumbPath" value="${bvo.thumbPath}">
               </div>
               <div class="col-sm-6">
                    <div class="project-name overflow">
                        <h2 class="bold">${bvo.title}</h2>
                        <ul class="nav navbar-nav navbar-default">
                            <li><i class="fa fa-clock-o"></i>${bvo.time_posted}</li>
                            <li><img width="12" height="14" src="${pageContext.request.contextPath}/resources/images/portfolio-details/addr-icon.png" class="addr-icon">${bvo.addr}</li>
                        </ul><br>
                        <h3>작성자: ${bvo.id}</h3>
                    </div>
                    <div class="project-info overflow">
                        <h3>상품 설명</h3>
                        <p>${bvo.bcontent}</p>
                    </div>
                    <c:if test="${sessionScope.mvo != null && sessionScope.mvo.id !=bvo.id}">  
	                    <div class="give-me-btn" id="give-me">
	                        <a href="#" class="btn btn-common uppercase">주세요 신청</a>
	                    </div>
                    </c:if>
                    <!-- 주세요 신청 modal -->
                <!-- start modal -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content" id="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">주세요 신청하기</h4>
							</div>
							<div class="contact-form bottom">
							<form id="app-form" name="app-form" method="post" action="${pageContext.request.contextPath}/registerGiveMe.do"></form>
								<form id="app-form" name="app-form" method="post" action="${pageContext.request.contextPath}/registerGiveMe.do">
									<div><input type="hidden" id="bno" name="bno" value="${bvo.bno}">
										글번호:${bvo.bno}&nbsp;&nbsp;글 제목: ${bvo.title}
									</div>
									<div class="kind-of-product">
										<p>주세요 신청 할 상품을 선택해주세요! ლ(╹◡╹ლ)๑</p>
										<ul>
											<c:forEach items="${requestScope.plist}" var="product">
												<li> <label><input type="checkbox" name="pnos" 
												value="${product.pno}">${product.ptitle}</label></li>
											</c:forEach>
										</ul>
									</div>
									<div class="delivery-select">
										<label><input type="radio" name="is_delivery" value="YES">용달 서비스</label><br>
										<label><input type="radio" name="is_delivery" value="NO">직거래</label>	
									</div> 
									<div class="form-group">
										<input type="hidden" name="writer" value="${bvo.id}">
										<p>상품 등록자가 신청사유 검토 후 물건을 드릴게요! (◕‿◕✿)</p>
										<textarea name="reason" id="reason" required="required" class="form-control" rows="8" 
										placeholder="신청 사유를 적어주세요."></textarea>
									</div>
									<div class="form-group">
										<input type="submit" id="submit-btn" name="submit" class="btn btn-submit" value="Submit">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- end of modal -->
			</div>
            </div>
        </div>
    </section>
     <!--/#portfolio-information-->

<section id="related-work" class="padding-top padding-bottom">
	<div class="container">
		<div class="row">
			<h1 class="title text-center">등록한 상품 사진</h1>
			<c:forEach items="${requestScope.plist}" var="imgList">
				<div class="col-sm-4">
					<div class="portfolio-wrapper">
						<div class="portfolio-single">
							<div class="portfolio-thumb">
								<img
									src="${pageContext.request.contextPath}/${imgList.img_path}"
									class="img-responsive" alt="">
							</div>
							<div class="portfolio-view">
								<ul class="nav nav-pills">
									<li><a
										href="${pageContext.request.contextPath}/${imgList.img_path}"
										data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
								</ul>
							</div>
						</div>
						<div class="portfolio-info ">
							<h2>${imgList.ptitle}</h2>
							<p>${imgList.pcontent}</p>
						</div>
					</div>
					<!-- wrapper -->
				</div>
			</c:forEach>
		</div>
		<div class = "row">
			<div align = "center">
				<c:if test="${mvo.id == bvo.id }">
					<button class = "btn btn-info"
					onclick = "javascript:location.href='${pageContext.request.contextPath}/boardUpdateView.do?bno=${bvo.bno}'">수정</button> 
					<button class = "btn btn-danger"
					onclick = "javascript:location.href='${pageContext.request.contextPath}/boardDelete.do?bno=${ bvo.bno}'">삭제</button>
				</c:if>
				<button class = "btn btn-info"
				onclick = "javascript:location.href='${pageContext.request.contextPath}/getBoardList.do'">목록</button> 
			</div>
		</div>
	</div>
</section>
</body>
<!--/#related-work-->