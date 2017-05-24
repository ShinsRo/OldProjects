<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <link >
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>로그인</title>
        <style>
        


        </style>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/member/login.css"/>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
		<link href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
</head>
<body>
   <div class="container">
           
            <header>
            
				
            </header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  action="${pageContext.request.contextPath }/login.do" autocomplete="on" metho> 
                                <h1>로그인</h1> 
                                <p> 
                                    <label for="id" class="id" > 아이디 </label>
                                    <input id="id" name="id" required="required" type="text" placeholder="아이디"/>
                                </p>
                                <p> 
                                    <label for="password" class="password"> 비밀번호 </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="비밀번호" /> 
                                </p>
                                <p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
									<label for="loginkeeping">로그인상태 유지</label>
								</p>
                                <p class="login button"> 
                                   <a href="http://cookingfoodsworld.blogspot.in/" target="_blank" ></a>
								</p>
								   <p>
                                <input type="submit" value="로그인">
                                </p>
                                <p class="change_link">
									회원이 아닌 상태입니다
									<a href="#toregister" class="to_register">회원가입</a>
								</p>
                            </form>
                        </div>

                        <div id="register" class="animate form">
                            <form  action="mysuperscript.php" autocomplete="on" method="post"> 
                                <h1> 회원가입</h1> 
                                  <p> 
                                    <label for="id" class="id"  > 아이디</label>
                                    <input id="id" name="id" required="required" type="text" placeholder="아이디"/> 
                                </p>
                                <p> 
                                    <label for="password" class="password" >비밀번호 </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="비밀번호"/>
                                </p>
                                <p> 
                                    <label for="name" class="name" >이름</label>
                                    <input id="name" name="name" required="required" type="text" placeholder="이름" />
                                </p>
                                <p> 
                                    <label for="address" class="address" >주소 </label>
                                    <input id="address" name="address" required="required" type="text" placeholder="주소"/>
                                </p>
                                <p>
                                    <label for="tel" class="tel" >전화번호 </label>
                                    <input id="tel" name="tel" required="required" type="tel" placeholder="전화번호"/>
                                </p>
                             
                                <p class="signin button"> 
									<input type="submit" value="회원가입"/> 
								</p>
                                <p class="change_link">  
									이미 회원 입니까?
									<a href="#tologin" class="to_register"> 로그인 </a>
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
</body>
</html>