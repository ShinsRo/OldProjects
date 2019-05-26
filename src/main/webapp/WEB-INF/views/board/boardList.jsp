<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath }/resources/css/todayShow.css" rel="stylesheet">
<script>

var Cpage;   // 현재 페이지 
var pagingSize = 4;
$(document).ready(function(){
	 chkRecent(1);
	 
	// 오늘 본 상품 페이징 버튼 함수
	 $(".btn_next").on('click', function() {
	 	chkRecent(Cpage + 1);
	 });

	 $(".btn_prev").on('click', function() {
	 	chkRecent(Cpage - 1);
	 });
});


function chkRecent(a){
	var itemID = getCookie("itemID");
	$("#right_zzim ul").html(''); //ul 지우기
	if(itemID){
		var totcount = itemID.split('&').length-1;
		var totpage = Math.ceil(totcount/pagingSize)*1;
		Cpage = (totpage >= a )? a:1;
		Cpage = (Cpage <1)? totpage:Cpage;

		var start = (Cpage-1) * pagingSize;    
 		for (i = start ; i <= start+(pagingSize-1) ;i++){
			var thisItem = itemID.split('&')[i];
			if(thisItem){
				var itemId = thisItem.split(':')[0];
				var itemImg = thisItem.split(':')[1];
				$("#right_zzim ul").append('<li><a href="boardDetail.do?bno='+itemId+'">'+itemId+'번 게시물</a><img src="${pageContext.request.contextPath}/'+itemImg+'"></li>'); 
			}//if(thisItem)
		}//for
		$("#paging").show();
	}else{
		$("#right_zzim ul").append('<p class="noData">최근 본 상품이<br>없습니다.</p>');
		$("#paging").hide();$("#recentCnt").text('');
	}
	updateRecentPage(totcount, Cpage);
}
function removeRecentItem(itemname) {
	var itemID = getCookie("itemID");
	itemID = itemID.replace(itemname + "&", "");
	setCookie("itemID", itemID, 1);
	chkRecent(Cpage);
}

function updateRecentPage(totcount, Cpage) {
	$("#recentCnt").text(totcount);
	$("#totalPageCount").text("/" + Math.ceil((totcount / pagingSize) * 1));
	if (Math.ceil((totcount / pagingSize) * 1) < Cpage) {
		$("#currentPage").text(Math.ceil((totcount / pagingSize) * 1));
	} else {
		$("#currentPage").text(Cpage);
	}
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
</script>
<section id="page-breadcrumb">
        <div class="vertical-center sun">
             <div class="container">
                <div class="row">
                    <div class="action">
                        <div class="col-sm-12">
                            <h1 class="title">드려요</h1>
                            <p>안쓰는 물건을 필요한 사람들에게 나누어 주세요!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </section>
    <!--/#action-->
<section id="portfolio">
	<div class="container">
		<div class="row">
			<!--/#portfolio-filter-->
			<div class="portfolio-items">
				<c:forEach items="${requestScope.blvo.list}" var="list">
					<div class="col-xs-6 col-sm-4 col-md-3 portfolio-item branded logos">
						<div class="portfolio-wrapper">
							<div class="portfolio-single">
								<div class="portfolio-thumb">
									<img src="${pageContext.request.contextPath}/${list.thumbPath}"
										class="img-responsive" alt="">
								</div>
								<div class="portfolio-view">
									<ul class="nav nav-pills">
										<li><a
											href="${pageContext.request.contextPath}/boardDetail.do?bno=${list.bno}"><i
												class="fa fa-link"></i></a></li>
										<li><a
											href="${pageContext.request.contextPath}/${list.thumbPath}"
											data-lightbox="example-set"><i class="fa fa-eye"></i></a></li>
									</ul>
								</div>
							</div>
							<div class="portfolio-info">
								<h2 style="text-overflow: ellipsis; overflow: hidden;">${list.title}</h2>
								<h3>${list.addr}</h3>
							</div>
						</div>
					</div>
				</c:forEach>
			</div><!-- portfolio -->
		</div><!-- row -->
		
	</div><!-- container -->
</section>
<!-- pg -->
<div class="portfolio-pagination">
	<ul class="pagination">
		<c:set var="pb" value="${requestScope.blvo.pagingBean}"></c:set>
		<c:choose>
			<c:when test="${pb.previousPageGroup}">
				<li><a
					href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.startPageOfPageGroup-1}">left</a></li>
			</c:when>
			<c:otherwise>
				<li></li>
			</c:otherwise>
		</c:choose>

		<c:forEach var="i" begin="${pb.startPageOfPageGroup}"
			end="${pb.endPageOfPageGroup}">
			<c:choose>
				<c:when test="${pb.nowPage!=i}">
					<li><a
						href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${i}">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li class="active"><a href="#">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:choose>
			<c:when test="${pb.nextPageGroup}">
				<li><a
					href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
			</c:when>
			<c:otherwise>
				<li></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
<div id="right">
</div>
<!-- 오늘 본 드려요 게시물 -->

<div id="rightSide">
	<div id="right_zzim">
		<div class="recTit">
			최근 본 드려요 <span id=recentCnt></span>
		</div>
		<ul></ul>
		<!-- 본 상품이 뿌려질 부분  -->
		<div id="paging">
			<a class="btn_prev">◀</a>
			<span id="currentPage"></span><span id="totalPageCount"></span>
			<a class="btn_next" style="cursor: pointer">▶</a>
		</div>
	</div>
</div> 
