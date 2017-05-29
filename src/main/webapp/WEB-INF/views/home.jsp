<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
//총 게시글,멤버수,물품수	
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/getCountBoardMain.do",
		dataType:"json",
		success:function(data){
			$("#CountBoardView").html(data);
		}
	});
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/getCountCommentMain.do",
		dataType:"json",
		success:function(data){
			$("#CountCommentView").html(data);
		}
	});
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/getCountMemberMain.do",
		dataType:"json",
		success:function(data){
			$("#CountMemberView").html(data);
		}
	});
});//ready
</script>



<section id="action" class="responsive">
	<div class="vertical-center">
		<div class="container">
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8 text-center padding wow fadeIn"
					data-wow-duration="1000ms" data-wow-delay="600ms">
					<form action="${pageContext.request.contextPath}/search.do" method="post" role="form">
						<input type="text" class="search-form" autocomplete="off"
							placeholder="검색하기" size="80" name="word"> <i class=""></i>
							<button type="submit" class="btn btn-info">검색</button>
					</form>
				</div>
				<div class="col-sm-2"></div>
			</div>
		</div>
	</div>
</section>
<!--/#action-->
    <section id="action">
        <div class="vertical-center">
             <div class="container">
                <div class="row">
                    <div class="action count">
                        <div class="col-sm-3 text-center wow bounceIn" data-wow-duration="1000ms" data-wow-delay="300ms">
                            <h1 id="CountMemberView" class="timer bold" data-to="7000" data-speed="3000" data-from="0"></h1>   
                            <h3>전체회원 수</h3>
                        </div>
                        <div class="col-sm-3 text-center wow bounceIn" data-wow-duration="1000ms" data-wow-delay="300ms">
                            <h1 id="CountBoardView" class="timer bold" data-to="12" data-speed="3000" data-from="0"></h1>   
                            <h3>전체물품 수</h3> 
                        </div>
                        <div class="col-sm-3 text-center wow bounceIn" data-wow-duration="1000ms" data-wow-delay="300ms">
                            <h1 id="CountCommentView" class="timer bold" data-to="432" data-speed="3000" data-from="0"></h1> 
                            <h3>전체후기 수</h3>
                        </div>
                        <div class="col-sm-3 text-center wow bounceIn" data-wow-duration="1000ms" data-wow-delay="300ms">
                            <h1 class="timer bold" data-to="145" data-speed="3000" data-from="0"></h1> 
                            <h3>아직 안정했음!</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   </section>