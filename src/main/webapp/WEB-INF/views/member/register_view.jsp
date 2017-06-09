<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">회원가입</h1>
						<p>일반회원은 개인 회원 가입을, 용달 사업자는 사업자 가입을 해주세요.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="container" >
		<table style="width:60%; height:100%; margin:auto; text-align:center;">
			<tr>
				<td><img src="${pageContext.request.contextPath}/resources/img/customer.png" style="width: 300px;"></td>
				<td><img src="${pageContext.request.contextPath}/resources/img/delivery_truck.png" style="width: 300px;"></td>
			</tr>
			<tr>
				<td><h2><a href="${pageContext.request.contextPath}/member/registers_terms.do">개인</a></h2></td>
				<td><h2><a href="${pageContext.request.contextPath}/delivery/register.do">사업자</a></h2></td>
			</tr>
		</table>
</div>