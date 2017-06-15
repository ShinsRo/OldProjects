<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#pasteImg").hide();
    	$("#updateBtn").click(function(){ 
    		if($("#title").val()==""){
    			alert("제목을 입력하세요!");
    			return false;
    		}
    		if($("#content").val()==""){
    			alert("본문을 입력하세요!");
    			return false;
    		}
    		$("#update_form").submit();
    	});
    	$("#resetBtn").click(function(){    		
    		$("#write_form")[0].reset();
    	});
    });
    function loadPrev() {
    	pasteHTML("${cvo.content }");
	}
</script>

<!-- 배너 타이틀 -->
<section id="page-breadcrumb">
	<div class="vertical-center sun">
		<div class="container">
			<div class="row">
				<div class="action">
					<div class="col-sm-12">
						<h1 class="title">지역후기 수정</h1>
						<p>
							<br>지역에 거주하며 경험한 후기를 적어주세요
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--배너 타이틀-->
<form id="update_form"
      name="commentRegForm" method="post" action="${pageContext.request.contextPath}/commentUpdate.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               <input type="text" name="title" class="form-control"
                  required="required" placeholder="제목"  value="${cvo.title }">
            </div>
            <div class="form-group" align="center">
               <input type="hidden" name="cno" value="${cvo.cno}">
               <input type="text" name="writer"
                  class="postcodify_address form-control" value="작성자&nbsp;&nbsp;${sessionScope.mvo.name}"
                  readonly="readonly"/>
                  <input type="text" name="addr"
                  class="postcodify_address form-control" value="${cvo.addr}"
                  />
            </div>
            <div class="form-group" align="center">
			<textarea name="content" id="content" rows="10" cols="100" style="width:100%; height:412px; min-width:610px; display:none;"></textarea>
			
			<p>
			<br>
			<button class = "btn btn-default" onclick = "openImgSelector();">사진 찾기</button>
			<button class = "btn btn-default" id="pasteImg" onclick = "insertImg();" >사진 붙이기</button>
						<button class = "btn btn-default"onclick = "loadPrev();">이전 글 불러오기</button>
			<input type="button" onclick = "loadPrev();" value="이전 글 불러오기"/>
			<input type="hidden" id="picno" name = "picno" value = "${cvo.picno }">
			<input type="hidden" id="pic_cursor" name = "currentPicId" value =1000>
				<!-- 
				<input type="button" onclick="pasteHTML('');" value="본문에 내용 넣기" />
				<input type="button" onclick="showHTML();" value="본문 내용 가져오기" /> -->
			</p>
			</div>
			<div class="form-group" align="center">
               <input class="btn btn-submit" type="button" id="updateBtn" onclick="submitContents(this);" value="업데이트" >
               <input class="btn btn-submit" type="button" id="resetBtn" value="리셋" >    
            </div>
      	 </div>
       </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
	var oEditors = [];
	
	var sLang = "ko_KR";	// 언어 (ko_KR/ en_US/ ja_JP/ zh_CN/ zh_TW), default = ko_KR
	
	// 추가 글꼴 목록
	//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
	
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "${pageContext.request.contextPath}/resources/se2/SmartEditor2Skin.html",	
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			},
			I18N_LOCALE : sLang
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["content"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});
	
	function pasteHTML(imgHTML) {
		//sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
		sHTML = imgHTML;
		oEditors.getById["content"].exec("PASTE_HTML", [sHTML]);
	}
	
 	function insertImg() {
 		var imgURL = "";
 		var imgHTML = "";
 		$("#pasteImg").show();
 		$.ajax({
 			type : "POST",
 			url : "${pageContext.request.contextPath}/getImgPath.do",
 			data : "picno="+$("#picno").val() +"&pic_cursor="+$("#pic_cursor").val(),
 			success :  function(data) {
 					alert("data: "+ data);
 					imgURL += "${pageContext.request.contextPath}/"
 					imgURL += data;
			 		imgHTML = "<img src='" + imgURL + "'>";
					pasteHTML(imgHTML);
 			}
 		});
		
		$("#pic_cursor").val(parseInt($("#pic_cursor").val())+1);
	} 
 	
	function openImgSelector(){
 		if($("#picno").val() == "0"){
			$.getJSON("${pageContext.request.contextPath}/getPicNo.do", function(data) {
				$("#picno").val(data);
			});
		}
		var appPopURL = "${pageContext.request.contextPath}/showImgSelector.do";
		var PopOption = "width=470, height=160, resizable=no, scrollbars=no, status=no";
		window.open(appPopURL,"",PopOption);
	}
	function showHTML() {
		var sHTML = oEditors.getById["content"].getIR();
		alert(sHTML);
	}
		
	function submitContents(elClickedObj) {
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
		// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("content").value를 이용해서 처리하면 됩니다.
		// alert(document.getElementById("content").value);
		try {
		//	elClickedObj.form.submit();
		false
		} catch(e) {}
	}
	
	function setDefaultFont() {
		var sDefaultFont = '궁서';
		var nFontSize = 24;
		oEditors.getById["content"].setDefaultFont(sDefaultFont, nFontSize);
	}
</script>

<!-- 여기는요 수정폼 -->
<%-- <section id="">
   <form enctype="multipart/form-data" id="update_form"
      name="boardRegForm" method="post" action="${pageContext.request.contextPath}/commentUpdate.do">
      <div class="col-md-1 col-sm-1" align="center">
      </div>
      <div class="col-md-10 col-sm-10" align="center">
         <div class="contact-form bottom">
         <br>
            <div class="form-group" align="center">
               <input type="text" name="title" class="form-control"
                  required="required" value="${cvo.title }">
            </div>
            <div class="form-group" align="center">
            <input type="hidden" name="cno" value="${cvo.cno}">
               <input type="text" name="writer"
                  class="postcodify_address form-control" value="작성자&nbsp;&nbsp;${sessionScope.mvo.name}"
                  readonly="readonly"/>
                  <input type="text" name="addr"
                  class="postcodify_address form-control" value="${cvo.addr}"
                  />
            </div>
            <div class="form-group" align="center">
               <textarea name="content" id="content" required="required"
                  class="form-control" rows="15">${cvo.content }</textarea>
            </div>
            <div class="form-group" align="center">
				<input class="btn btn-submit" type="button" id="updateBtn" value="수정하기" > 
            </div>
         </div>
      </div>
   </form>
</section> --%>
<!-- 여기는요 수정폼 -->


