<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
//총 게시글,멤버수,물품수	
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/CountReport_admin.do",
		dataType:"json",
		data : "category=board",
		success:function(data){
			$("#CountReportBoardView").html(data);
		}
	});
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/CountReport_admin.do",
		dataType:"json",
		data : "category=comment",
		success:function(data){
			$("#CountReportCommentView").html(data);
		}
	});
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/CountReport_admin.do",
		dataType:"json",
		data : "category=reply",
		success:function(data){
			$("#CountReportReplyView").html(data);
		}
	});
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/CountDelivery_admin.do",
		dataType:"json",
		success:function(data){
			$("#CountDeliveryView").html(data);
		}
	});
});//ready
</script>

<div class="col-sm-12">
                    <div class="time-count">
                        <ul id="countdown">
                            <li class="angle-one">
                                <span id="CountReportBoardView" class="days time-font"></span>
                                <p>신고된 기부글</p>
                            </li>
                            <li class="angle-two">
                                <span id="CountReportCommentView" class="hours time-font"></span>
                                <p>신고된 지역후기</p>
                            </li>
                            <li class="angle-three">
                                <span id="CountReportReplyView" class="minutes time-font"></span>
                                <p class="minute">신고된 댓글</p>
                            </li>                            
                            <li class="angle-four">
                                <span id="CountDeliveryView" class="seconds time-font"></span>
                                <p>제휴 승인 대기인원</p>
                            </li>               
                        </ul>   
                    </div>
                </div>
