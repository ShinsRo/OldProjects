<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
#rightSide {
	position: absolute;
	top: 547px;
	left: 50%;
	margin: 0 0 0 510px;
}

#rightSide #right_zzim {
	position: fixed;
	top: 126px;
	left: 50%;
	margin-left: 600px;
	border: 1px solid #B0B5BD;
	width: 114px;
	height: 543px;
}

#rightSide #right_zzim  div {
	text-align: center;
}

#rightSide #right_zzim  div.recTit {
	line-height: 1.5em;
	padding: 5px;
	color: white;
	background-color: #505A69;
}

#right_zzim #recentCnt {
	color: yellow;
}

#rightSide #right_zzim ul {
	min-height: 495px;
	padding:0px;
}

#rightSide #right_zzim  li {
	text-align: center;
	padding: 5px;
	position: relative;
}

#rightSide #right_zzim ul li img {
	border: 1px solid #ccc;
	width : 100%;
	height: 23%;
}

#right_zzim .detail {
	display: none;
	position: absolute;
	top: 3px;
	right: 20px;
	xheight: 40px;
	xpadding: 15px 11px 0;
	xbackground: #404a59;
	color: #fff;
	xtext-align: left;
	white-space: nowrap;
}

#right_zzim li:hover .detail {
	display: block
}

#right_zzim li .btn_delete {
	position: absolute;
	top: 3px;
	right: -1px;
	width: 11px;
	height: 11px;
	background: url(/img/sp.png) no-repeat -193px -111px;
	text-indent: -9000px;
}

#right_zzim  #currentPage {
	position: absolute;
	top: 510px;
	left:45px;
	color: #505A69;
	font-weight: bold
}

#right_zzim  #totalPageCount {
	position: absolute;
	top: 510px;
	color: #CBC8D2;
	font-weight: bold
}

.noData {
	color: #ccc;
	text-align: center;
	margin-top: 223px;
}

}
#paging {
	display:inline;
	position: relative;
	line-height: 1em;
}

#paging .btn_prev {
	position: absolute;
	top: 510px;
	left: 4px;
	width: 13px;
	height: 11px;
	display: inline-block;
}

#paging .btn_next {
	position: absolute;
	top: 510px;
	right: 4px;
	width: 13px;
	height: 11px;
	display: inline-block;
}
</style>
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
			<ul class="portfolio-filter text-center">
				<li><a class="btn btn-default active" href="#" data-filter="*">All</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".branded">Branded</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".design">Design</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".folio">Folio</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".logos">Logos</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".mobile">Mobile</a></li>
				<li><a class="btn btn-default" href="#" data-filter=".mockup">Mockup</a></li>
			</ul>
			<!--/#portfolio-filter-->
			<%--${pageContext.request.contextPath }/uploadedFiles/JAVA/board${list.bno}/1.jpg --%>
			<div class="portfolio-items">
				<c:forEach items="${requestScope.blvo.list}" var="list">
					<%-- <c:if test = "${list.is_deleted != 'YES'}"> --%>
					<div class="col-xs-6 col-sm-4 col-md-3 portfolio-item branded logos">
						<div class="portfolio-wrapper">
							<div class="portfolio-single">
								<div class="portfolio-thumb">
									<%-- "${pageContext.request.contextPath }/resources/images/portfolio/1.jpg" --%>
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
					<%-- </c:if> --%>
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
