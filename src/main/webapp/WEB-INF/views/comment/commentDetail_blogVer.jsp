<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
   function sendList(){
      location.href= "getCommentList.do";
   }
   function updateComment(){
      if(confirm("수정하시겠습니까?")){
         location.href="commentUpdateView.do?cno="+${requestScope.cvo.cno};
      }else{
         return false;
      }
   }
   function deleteComment(){
      if(confirm("삭제하시겠습니까?")){
         location.href="deleteComment.do?cno="+${requestScope.cvo.cno};
      }else{
         return false;
      }
   }
    function reportComment(){
       if(confirm("신고하시겠습니까?")){
          $("#reportComment").modal();
      }else{
         return;
      }
    }
   function fn_formSubmit() {
      var f = document.replyWriteForm;
      if($("#content").val()==""){
         alert("댓글을 입력해주세요!");
         return;
      }else{
         f.submit();
      }
   }
   function fn_replyDelete(rno,cno){
      if(confirm("삭제하시겠습니까?")){
         location.href ="deleteCommentReply.do?rno="+rno+"&cno="+cno;
      }else{
         return;
      }
   }
    function fn_replyReport(){
       if(confirm("신고하시겠습니까?")){
          $("#replyReport").modal();
      }else{
         return;
      }
    }
   //댓글 수정버튼 클릭시 수정창 나타나게 하기
   var updaterno = updatecontent = null;
   function fn_replyUpdate(rno){
       var form = document.replyUpdateForm;
       var reply = document.getElementById("reply"+rno);
       var replyDiv = document.getElementById("replyDiv");
       replyDiv.style.display = "";
       if (updaterno) {
           document.body.appendChild(replyDiv);
           var oldrno = document.getElementById("reply"+updaterno);
           oldrno.innerText = updatecontent;
       } 
       
       form.rno.value=rno;
       form.content.value = reply.innerText;
       reply.innerText ="";
       reply.appendChild(replyDiv);
       updaterno   = rno;
       updatecontent = form.content.value;
       form.content.focus(); 
   } 

   // 댓글 수정버튼->저장 누를때 
   function fn_replyUpdateSave(){
       var form = document.replyUpdateForm;
       var rno = document.replyUpdateForm.rno;
       var cno = document.replyUpdateForm.cno;
       if (form.content.value=="") {
           alert("글 내용을 입력해주세요.");
           form.content.focus();
           return;
       }
      if(confirm("댓글을 수정하시겠습니까?")){
         form.action="updateCommentReply.do";
         form.submit();
      }
       //form.action="updateCommentReply.do?rno="+rno+"&cno="+cno+"&content="+form.content.value;
       //form.submit();     
   } 
   // 댓글 수정하고 취소 누르기
   function fn_replyUpdateCancel(){
       var form = document.replyUpdateForm;
       var replyDiv = document.getElementById("replyDiv");
       document.body.appendChild(replyDiv);
       replyDiv.style.display = "none";
       
       var oldrno = document.getElementById("reply"+updaterno);
       oldrno.innerText = updatecontent;
       updaterno = updatecontent = null; 
   } 
   
   
   
   // 대댓글
   function hideDiv(id){
       var div = document.getElementById(id);
       div.style.display = "none";
       document.body.appendChild(div);
   }

   function fn_replyReply(rno){
       var form = document.form3;
       var reply = document.getElementById("reply"+rno);
       var replyDia = document.getElementById("replyDialog");
       replyDia.style.display = "";
       
       if (updaterno) {
           fn_replyUpdateCancel();
       } 
       
       form.content.value = "";
       form.parent.value=rno;
       reply.appendChild(replyDia);

   } 
   
   
   function fn_replyReplyCancel(){
       hideDiv("replyDialog");
   } 
   function fn_replyReplySave(){
       var form = document.form3;
       var param = form.parent.value
       if (form.content.value=="") {
           alert("글 내용을 입력해주세요.");
           form.content.focus();
           return;
       }
       form.action="writeCommentReply2.do";
       form.submit();    
   }
   // 좋아요 기능
   function like_btn(){
      var id = $("#userid").val();
      var cno = $("#cno").val();
       $.ajax({
         type : "POST",
         url : "${pageContext.request.contextPath}/clickLikeBtn.do",
         data : "id=" + id+"&cno="+cno,
         success : function(data) {
            if(data.status == "ok"){
               $("#like-heart").css("color","red");
               $("#likeCount").text(data.count);
            }else{
               $("#like-heart").css("color","#0099AE");
               $("#likeCount").text(data.count);
            }
            
         }//callback         
      });//ajax 
   }
   $(document).ready(function(){
      var id = $("#userid").val();
      var cno = $("#cno").val();
      $.ajax({
         type : "POST",
         url : "${pageContext.request.contextPath}/checkClickLike.do",
         data : "id=" + id+"&cno="+cno,
         success : function(data) {
            if(data == "ok"){
               $("#like-heart").css("color","#0099AE");
            }else{
               $("#like-heart").css("color","red");
            }
            
         }//callback         
      });//ajax 
   });
</script>
<link rel="shortcut icon" href="images/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/resources/images/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/resources/images/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/resources/images/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/resources/images/ico/apple-touch-icon-57-precomposed.png">
<script type="text/javascript">var switchTo5x=true;</script>
<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
<script type="text/javascript">stLight.options({publisher: "7e8eb33b-fbe0-4915-9b93-09490e3d10df", doNotHash: false, doNotCopy: false, hashAddressBar: false});</script>

    <section id="page-breadcrumb">
        <div class="vertical-center sun">
             <div class="container">
                <div class="row">
                    <div class="action">
                        <div class="col-sm-12">
                     <h1 class="title">지역후기</h1>
                     <p>
                        <br>지역에 거주한 회원이 작성한 후기입니다.
                     </p>
                        </div>                                                                                
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--/#page-breadcrumb-->

    <section id="blog-details" class="padding-top">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <div class="row">
                      <div class="col-md-1 col-sm-1"></div>
                         <div class="col-md-10 col-sm-10">
                            <div class="single-blog blog-details">
                                <div class="post-content overflow">
                                <input type="hidden" id="cno" value="${requestScope.cvo.cno}">
                                    <h2 class="post-title bold"><a href="#">${requestScope.cvo.title}</a></h2>
                                    <h3 class="post-author"><a href="#">Posted by ${requestScope.cvo.id}</a></h3>
                        <!-- 구글지도 검색 api-->

                        <script type="text/javascript">
                           $(document).ready(
                                 function() {
                                    $("#map").hide();
                                    $("#findBtn").hide();
                                    $("#mapContent").click(
                                          function() {
                                             $("#findBtn")
                                                   .toggle();
                                          });
                                    $("#findRouteBtn").on("click",
                                          function() {
                                             $("#map").show();
                                             initMap();
                                          });
                                 });
                        </script>

                        <!-- Context -->
                        <div id="Context">
                                 <table class="col-md-12 padding-0">
                                    <tr>
                                       <td><a href="#"> <span
                                             class="fa fa-map-marker fa-3x" id="mapContent"></span></a></td>
                                    </tr>
                                    <tr id="findBtn">
                                       <td colspan="3"><input type="text" id="dest"
                                          name="dest"> <input class="btn btn-info"
                                          type="button" value="길찾기" id="findRouteBtn"></td>
                                    </tr>
                                 </table>
                              </div>
                           <div style="float: left; width: 100%; height: 300px;" id="map"></div>
                           <!-- 지오코드로 한글 검색을 통한 좌표찾기 -->
                           <script type="text/javascript">
                              function initMap() {
                                 var map;
                                 var geocoder = new google.maps.Geocoder();
                                 var marker;
                                 var infoWindow;
                                 map = new google.maps.Map(document
                                       .getElementById('map'), {
                                    zoom : 17,
                                    center : {
                                       lat : 37.384974,
                                       lng : 127.123432
                                    }
                                 });
                                 var dest = $("#dest").val();
                                 geocoder
                                       .geocode(
                                             {
                                                'address' : dest
                                             },
                                             function(results,
                                                   status) {
                                                if (status === 'OK') {
                                                   map
                                                         .setCenter(results[0].geometry.location);
                                                   marker = new google.maps.Marker(
                                                         {
                                                            map : map,
                                                            position : results[0].geometry.location
                                                         });

                                                   infoWindow = new google.maps.InfoWindow(
                                                         {
                                                            content : dest
                                                         });

                                                   infoWindow
                                                         .open(
                                                               map,
                                                               marker);
                                                }
                                             });
                              }
                           </script>
                           <!-- 구글api를 가져오기 위한 script -->
                           <script async defer
                              src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCvP2byenxiGWdtrFrb9Bk5Bf5J8kiCqA&callback=initMap">
    </script>
                           <p>${requestScope.cvo.content}</p>
                           <br> <br>
                           <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
                              <sec:authentication property="principal.id" var="mvoId" />
                              <div align="center">
                                 <input class="btn btn-info" type="button" value="목록"
                                    onclick="sendList()">
                                 <c:if test="${requestScope.cvo.id==mvoId}">
                                    <input class="btn btn-info" type="button" value="수정"
                                       onclick="updateComment()">
                                    <input class="btn btn-danger" type="button" value="삭제"
                                       onclick="deleteComment()">
                                 </c:if>
                                 <c:if test= "${requestScope.cvo.id != mvoId }">
                                 	<input class="btn btn-danger" type = "button" value ="신고"
                                 		onclick="reportComment()">
                                 </c:if>
                                 </div>
                                 </sec:authorize>
                                    <div class="post-bottom overflow">
                                        <ul class="nav navbar-nav post-nav">
                                            <li><a href="#"><i class="fa fa-tag"></i>Creative</a></li>
                                            <li><a onclick="like_btn()"><i id="like-heart" class="fa fa-heart"></i><span id="likeCount" >${likeCount}</span> Love</a></li>
                                            <li><span id="ifLike"></span></li>
                                            <li><a href="#"><i class="fa fa-comments"></i>${commentCount} Comments</a></li>
                                        </ul>
                                    </div>
                                    <!-- 댓글 입력구간 -->
                                    <div class="author-profile">
                                        <div class="row">
                                            <div class="col-sm-10">
                                    <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
                                       <div class="replyList" >
                                          <div class="form-group" align="center">
                                             <form name="replyWriteForm"
                                                action="writeCommentReply1.do" method="post">
                                                <ul class="nav navbar-nav navbar-default" id="reply_ul">
                                                   <li><input type="hidden" name="parent" value="0">
                                                   <input type="hidden" name="cno" value="${requestScope.cvo.cno}">
                                                   <input type="hidden" id="userid" name="id" value="<sec:authentication property="principal.id"/>">
                                                   <input type="hidden" name="name" value="<sec:authentication property="principal.name"/>">
                                                   <input type="hidden" name="gno" value="1">
                                                   <input type="hidden" name="depth" value="0">
                                                   <input type="hidden" name="order_no" value="1">
                                                   <br>
                                                   <br>
                                                   <textarea class="reply_field" id="content" name="content" rows="3" cols="130"
                                                   placeholder="댓글을 달아주세요."></textarea>
                                                   <input type="button" id="writeReplyBtn" class="btn btn-sm btn-info" value="등록" onclick="fn_formSubmit()"></li>
                                                   <li></li>
                                                </ul>
                                             </form>
                                          </div>
                                       </div>
                                    </sec:authorize>
                                 </div>
                              </div>
                           </div>
                                    <!-- 댓글 입력구간 끝 -->
                                    <!--  -->
                                    <sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">

                                    	<div id="replyDialog" style="width: 99%; display: none">
											<form name="form3" action="writeCommentReply2.do" method="post">
												<input type="hidden" name="cno" value="<c:out value="${requestScope.cvo.cno}"/>"> 
												<input type="hidden" name="parent">
												<input type="hidden" name="id" value="<sec:authentication property="principal.id"/>">
												<input type="hidden" name="name" value="<sec:authentication property="principal.name"/>">
												<textarea class="reply_field" name="content" rows="3" cols="60" maxlength="500" style="border:solid 1px #D8D8D8;
												margin-left:10px;"></textarea>
												<a onclick="fn_replyReplySave()">저장</a>
												<a onclick="fn_replyReplyCancel()">취소</a>
											</form>
										</div>
									</sec:authorize>
                                   	<!-- 댓글 구간 -->
                                    <div class="response-area">
                                    <h2 class="bold">Comments</h2>
                                    <ul class="media-list">
                                    <c:forEach items="${requestScope.CommentReplyList}" var="reply">
                                    <c:if test = "${reply.depth <1}">                                    
                                    
                                    	<li class="media">
                                            <div class="post-comment">
                                            	 <div class="media-body">
                                                    <span><i class="fa fa-user">	</i>Posted by <a href="#">${reply.id}</a></span>
                                					<div align="left" id="reply<c:out value="${reply.rno}"/>">
														<c:out value="${reply.content }" />
													</div>
                                            		<ul class="nav navbar-nav post-nav">
                                                        <li><a href="#"><i class="fa fa-clock-o"></i>${reply.time_posted}</a></li>
                                                        <li><a onclick="fn_replyReply(${reply.rno})"><i class="fa fa-reply"></i>Reply</a></li>
	                                                    <li><a><i class="fa fa-pencil">수정</i></a></li>
	                                                    <li><a><i class="fa fa-times">삭제</i></a></li>
                                                    </ul>
                                            	</div>
                                            </div>
                                         </li>
                                    </c:if>
                                      <c:if test="${reply.depth >=1}">                                    
	                                      	<div class="parrent">
	                                                <ul class="media-list">
	                                                    <li class="post-comment reply">
	                                                        <div class="media-body">
	                                                            <span><i class="fa fa-user"></i>Posted by <a href="#">${reply.id}</a></span>
	                                                            
	                                                            <p><c:out value="${reply.content }" /></p>
	                                                            <ul class="nav navbar-nav post-nav">
	                                                                <li><a href="#"><i class="fa fa-clock-o"></i>${reply.time_posted}</a></li>
	                                                            </ul>
	                                                        </div>
	                                                    </li>
	                                                </ul>
	                                          </div>
                                    </c:if>
                                            
                                    </c:forEach>
                                   		<!-- 댓글 구간 끝-->
                                   	</ul>                   
                                </div><!--/Response-area-->
                                </div>
                            </div>
                        </div>
                    </div>
                 </div>
            </div>
        </div>
    </section>
<sec:authorize ifAnyGranted="ROLE_MEMBER,ROLE_ADMIN,ROLE_DELIBERY">
<sec:authentication property="principal.id" var="mvoId" />
<!-- comment 신고modal -->
<!-- start modal -->
<div class="modal fade" id="reportComment" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
			<div class="modal-content" id="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">신고하기</h4>
							</div>
							<div class="contact-form bottom">                                    	
							<form id="app-form" name="app-form" method="post" action="${pageContext.request.contextPath}/reportComment_admin.do"></form>
								<form id="app-form" name="app-form" method="post" action="${pageContext.request.contextPath}/reportComment_admin.do">
									<div><input type="hidden" name="reno" value="${requestScope.cvo.cno}">
										<input type="hidden" name="category" value="comment">
										<input type="hidden" name="reporter" value="${mvoId}">
										작성자:${requestScope.cvo.id}<br><br>후기 내용: ${requestScope.cvo.content}
									</div>
									<div class="form-group">
										<input type="hidden" name="id" value="${requestScope.cvo.id}">
										<p>신고 사유를 구체적으로 적어주세요</p>
										<textarea name="why" id="why" required="required" class="form-control" rows="8" 
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
</sec:authorize>