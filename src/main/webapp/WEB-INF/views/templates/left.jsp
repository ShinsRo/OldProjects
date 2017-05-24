<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="//code.jquery.com/jquery.min.js"></script>
<script >
$(document).ready(function(){
    $(".push_menu").click(function(){
         $(".wrapper").toggleClass("active");
    });
});
</script>
<div class="container">
	<div class="row">
		<div class="wrapper">
    	    <div class="side-bar">
                <ul>
                    <li class="menu-head">
                        CLASH OF CLANS <a href="#" class="push_menu"><span class="glyphicon glyphicon-align-justify pull-right"></span></a>
                    </li>
                    <div class="menu">
                        <li>
                            <a href="#">Dashboard <span class="glyphicon glyphicon-dashboard pull-right"></span></a>
                        </li>
                        <li>
                            <a href="#" class="active">Love snippet<span class="glyphicon glyphicon-heart pull-right"></span></a>
                        </li>
                        <li>
                            <a href="#">Like it? <span class="glyphicon glyphicon-star pull-right"></span></a>
                        </li>
                        <li>
                            <a href="#">Settings <span class="glyphicon glyphicon-cog pull-right"></span></a>
                        </li>
                    </div>
                </ul>
    	    </div>   
    	    </div>
	</div>
</div>
