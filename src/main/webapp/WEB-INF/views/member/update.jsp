<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container" style="padding-top: 60px;">
      <h3>회원수정</h3>
      <br>
      <form class="form-horizontal" role="form" 
      action="${pageContext.request.contextPath }/update.do">
        <div class="form-group">
          <label class="col-lg-3 control-label">아이디:</label>
          <div class="col-lg-4">
            <input class="form-control" type="text" placeholder="아이디">
          </div>
        </div>
        <div class="form-group">
          <label class="col-lg-3 control-label">비밀번호:</label>
          <div class="col-lg-4">
            <input class="form-control"  type="password" placeholder="비밀번호">
          </div>
        </div>
        <div class="form-group">
          <label class="col-lg-3 control-label">이름:</label>
          <div class="col-lg-4">
            <input class="form-control"  type="text" placeholder="이름">
          </div>
        </div>
        <div class="form-group">
          <label class="col-lg-3 control-label">주소:</label>
          <div class="col-lg-4">
            <input class="form-control"  type="text" placeholder="주소">
          </div>
        </div>
        <div class="form-group">
          <label class="col-lg-3 control-label">전화번호:</label>
          <div class="col-lg-4">
            <input class="form-control" type="tel" placeholder="전화번호">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3 control-label"></label>
          <div class="col-md-4">
            <input class="btn btn-primary" value="수정" type="button">
            <span></span>
            <input class="btn btn-default" value="취소" type="reset">
          </div>
        </div>
      </form>
    </div>
