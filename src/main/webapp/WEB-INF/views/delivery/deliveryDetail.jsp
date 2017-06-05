<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">신청 상세 정보</h1>
						<p>용달을 신청한 고객들의 상세 정보입니다.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<hr>
<div>
<c:set var="reciever" value="${requestScope.map.reciever}"/>
<h3>신청자</h3>
아이디: ${reciever.id}<br>
이름: ${reciever.name}<br>
연락처: ${reciever.tel}<br>
주소: ${reciever.addr} ${reciever.addr_detail}<br>
</div>
<div>
<h3>기부자</h3>
<c:set var="giver" value="${requestScope.map.giver}"/>
아이디: ${giver.id}<br>
이름: ${giver.name}<br>
연락처: ${giver.tel}<br>
주소: ${giver.addr} ${reciever.addr_detail}<br>
</div>