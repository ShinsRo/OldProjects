<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section id="action" class="responsive">
	<div class="vertical-center">
		<div class="container">
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8 text-center padding wow fadeIn"
					data-wow-duration="1000ms" data-wow-delay="600ms">
					<form action="${pageContext.request.contextPath}/search.do" method="post" role="form">
						<input type="text" class="search-form" autocomplete="off"
							placeholder="검색하기" size="80"> <i class=""></i>
							<button type="submit" class="btn btn-info">검색</button>
					</form>
				</div>
				<div class="col-sm-2"></div>
			</div>
		</div>
	</div>
</section>
<!--/#action-->