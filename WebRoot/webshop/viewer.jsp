<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>图片浏览器</title>
	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webshop/css/webshop.css">
  </head>
  
  <body>
    <div class="container">
    	<div class="row">
    		<img class="img-responsive img-thumbnail" src="${url}">
    	</div>
    </div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/myhtml5fileupload.js"></script>
  <script type="text/javascript">
	 $(function(){
	  	var h = $(window).height();
	  	$(".row").resizebyheight(h);
	 });
  </script>
</html>
