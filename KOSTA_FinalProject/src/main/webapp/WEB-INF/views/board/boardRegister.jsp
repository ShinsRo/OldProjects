<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 서울시 주소 API 로딩 -->
<script type="text/javascript"
	src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<script type="text/javascript">
	var ProductRegFormHtml="";
	var productCnt = 1;
	
	$(function() {
		//서울시 주소 검색 팝업 제이쿼리
		$("#postcodify_search_button").postcodifyPopUp();
		
		//상품 추가 폼 html 초기화
		productRegFormHtml = $("#productFormView").html();
	
		//상품 추가 폼 html div영역 추가
		$("#productFormView").prepend("<div id = 'product'"+ productCnt + ">");
		$("#productFormView").append("</div>");
		
		//상품 추가 버튼 클릭 시 추가 폼 생성
		$("#addProduct").click(function() {
			if(productCnt >= 3){
				alert("더 이상 물건을 올릴 수 없습니다.");
				return;
			}
		//상품 추가 시
			productCnt++;
			var productRegFormHtmlTemp =
				"<div id = 'product'>" + productRegFormHtml + "</div>";
			$("#productFormView").prepend(productRegFormHtmlTemp);
		});
		$(document).on("click","#deleteProduct", function() {
			if(productCnt <= 1){
				alert("물품은 하나 이상 등록 되어야 합니다.");
				return;
			}
			productCnt--;
			$(this).parent().parent().remove();
		})
	});
</script>

<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">드려요 작성하기</h1>
						<p>
							<br>버리기 아까운 물건들에게 새 주인을 찾아주세요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->

<!-- 드려요 작성폼 -->
<section id="">
	<form enctype="multipart/form-data" id="main-contact-form" name = "fakeForm"method="post" action=""></form>					
	<form enctype="multipart/form-data" id="main-contact-form"
		name="boardRegForm" method="post" action="${pageContext.request.contextPath }/boardRegister.do">
		<div class="col-md-8 col-sm-8">
			<div class="contact-form bottom">
			<br>
				<div class="form-group">
					<input type="text" name="title" class="form-control"
						required="required" placeholder="제목" maxlength="20">
				</div>
				<div class="form-group" align="right">
					<button type="button" id="postcodify_search_button"
						class="btn btn-sm btn-info">검색</button>
					<input type="text" name="addr"
						class="postcodify_address form-control" value=""
						readonly="readonly" required="required" placeholder="주소" />
				</div>
				<div class="form-group">
					<textarea name="bcontent" id="content" required="required"
						class="form-control" rows="15" placeholder="글을 작성해주세요."></textarea>
				</div>
				<div class="form-group">
					<input type="submit" name="submit" class="btn btn-submit"
						value="게시하기">
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-4">
			<div id = "productFormView" class="contact-form bottom">
				<!-- 상품 번호 -->
				<div class="form-group">
					<input type="text" name="ptitle" class="form-control"
						required="required" placeholder="물건명">
				</div>
				<div class="form-group">
					<input type="text" name="kind" class="form-control"
						required="required" placeholder="종류">
				</div>
				<div class="form-group">
					<textarea name="pcontent" id="message" required="required"
						class="form-control" rows="8" placeholder="물건 설명"></textarea>
				</div>
				<div align = "right" class="">
				<input type ='button' class = "btn btn-sm btn-danger" id ='deleteProduct' value = "물건 삭제">
				</div>
				<input type = "file" name="file" required="required"> 
				<hr>
			</div>
				<div align="right">
					<button type = "button" class="btn btn-sm btn-success" id="addProduct">상품추가</button>
				</div>
		</div>
	</form>
</section>
<!-- 드려요 작성폼 -->