<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
$(function() {
	$("#showApps").click(function() {
		//alert($(this).val());
		$("#appViewModal").modal();
	});
});
</script>
            <!-- start modal -->
<div class="modal fade" id="appViewModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content" id="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">주세요 신청하기</h4>
			</div>
			<div class="contact-form bottom">
				            <div id="accordion-container">
				            
                <h2 class="page-header">Accordion</h2>
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    Collapsible Group Item #1
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                            <div class="panel-body">
                                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                    Collapsible Group Item #2
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                            <div class="panel-body">
                                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                    Collapsible Group Item #3
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse">
                            <div class="panel-body">
                                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                            </div>
                        </div>
                    </div>
                </div><!--/#accordion-->
            </div><!--/#accordion-container-->
				
			</div>
		</div>
	</div>
</div>
<!-- end of modal -->
<table class="table table-hover" id="my_board">
	<thead>
		<tr>
			<th class="no">NO</th>
			<th class="title">제목</th>
			<th class="date">올린일</th>
			<th class="hit">조회수</th>
			<th class="addr">신청현황</th>
		</tr>
	</thead>
	<tbody>			
			<c:forEach items="${requestScope.blvo.list}" var="bvo" >				
			<tr>
			    <td>${bvo.bno }</td>
				<td>
					<c:choose>
					<c:when test="${sessionScope.mvo!=null}">
						<a href="${pageContext.request.contextPath}/boardDetail.do?bno=${bvo.bno}">${bvo.title }</a>
					</c:when>
					<c:otherwise>
						${bvo.title }
					</c:otherwise>
					</c:choose>
					</td>
					<td>${bvo.time_posted }</td>
					<td>${bvo.hit }</td>
					<td>${bvo.is_traded }&nbsp;&nbsp;&nbsp;
					<button class = "btn btn-sm btn-info" value = "${bvo.bno}" id = "showApps">
					신청현황보기
					</button></td>
			</tr>
		</c:forEach>
			
		</tbody>
</table>
<br></br>

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
					<li><a href="${pageContext.request.contextPath}/getBoardList.do?pageNo=${pb.endPageOfPageGroup+1}">right</a></li>
				</c:when>
				<c:otherwise>
					<li></li>
				</c:otherwise>
			</c:choose>
	</ul>
</div>

<br>
<br>
<c:if test="${sessionScope.mvo.id != null }">
	<input class="write_btn" type="button" value="글쓰기"
		onclick="javascript:location.href='${pageContext.request.contextPath}/boardRegisterView.do'">
</c:if>