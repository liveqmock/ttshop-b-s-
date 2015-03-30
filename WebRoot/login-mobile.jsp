<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
  	<meta name="apple-mobile-web-app-capable" content="yes">
  	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
  	<meta name="apple-touch-fullscreen" content="yes">
  	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <base href="<%=basePath%>">
    <title>--TTSHOP系统登陆--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  <body>
  	<div class="container-fluid">
 	<div class="jumbotron">
  		<div class="row-fluid">
  			<h3>欢迎使用TTSHOP销售管理系统</h3>
  			<form action="<%=request.getContextPath()%>/user/login.action" method="post" class="form" role="form" target="_top">
			<div class="form-group">
				 <input type="text" name="userid" value="" placeholder="User ID" class="form-control" >
			</div>
			<div class="form-group">
				 <input type="password" name="password" placeholder="Password" class="form-control">
			</div>
			<button class="btn btn-success" id="login">登录</button>
  			</form>
  			<p></p>
  			<p><span>还不是我们的用户?</span>
  			<span><a class="btn btn-primary btn-lg" href="<%=request.getContextPath()%>/user/register.jsp" data-role="button">立即注册</a></span></p>
  		</div>
  	</div>
    </div>
    <div class="container">
		<div class="footer">
			<div class="row-fluid">
		    	<div class="text-center">
		    		<span class="text-info" style="border-radius:0 ">Copyright &copy; 2015 david_du1987@126.com </span>
		    	</div>
	  		</div>
   		</div>
   	</div>
  </body>
</html>
