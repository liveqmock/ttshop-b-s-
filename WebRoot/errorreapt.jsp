<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
  </head>
  
  
  <body>
  	<div class="container-fluid">
   		<div class="row well text-center">
  	 		<h2 class="text-danger"> 系统错误！！</h2>
  	 		<h3 class="text-danger"> 原因：不能重复提交资料！提醒:网速慢时不要刷新网页!添加,修改操作时刷新网页会造成重复提交错误!</h3>
  	 		<h3 class="text-danger"><a class="btn btn-default" href="${returnurl}" target="main">返回</a></h3>
  		</div>
    </div>
  </body>
</html>
