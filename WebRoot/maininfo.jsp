<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> 欢迎使用 TTSHOP POS 系统  </title>
	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
    <!--[if lte IE 8]>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/side-menu.css">
    <!--<![endif]-->
    <style type="text/css" media="print">
		.noprint{display: none;}
  	</style>
    <style type="text/css">
		ul li a{font-size: 9px;}
  	</style>
  </head>
  <script src="<%=request.getContextPath()%>/js/ui.js"></script>
  <body> 
   <div id="layout">
   		<div id="menu"  class="noprint">
   		<div class="pure-menu pure-menu-open">
   			<a class="pure-menu-heading" style="font-size: 10px;"> 欢迎  ${user.username }  </a>
		    <ul>
		   	<li> <a href="<%=request.getContextPath()%>/supplier/addsupplier.jsp" target="main"> 添加供应商</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/supplier/listsupplier.action" target="main"> 供应商列表</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/customer/addcustomer.jsp" target="main"> 添加客户</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/customer/listcustomer.action" target="main"> 客户列表</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/warehouse/addwarehouse.jsp" target="main"> 添加仓库</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/warehouse/listwarehouse.action" target="main"> 仓库列表</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/ptype/addptype.jsp" target="main"> 添加类型</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/ptype/listptype.action" target="main"> 类型列表</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/productinfo/toaddproductinfo.action" target="main"> 添加产品</a> </li>
		   	<li> <a href="<%=request.getContextPath()%>/productinfo/listproductinfo.action" target="main"> 产品列表</a> </li>   	
		   	<li> <a href="<%=request.getContextPath()%>/productinfo/listproductprice.action" target="main"> 报价列表</a> </li>   	
		   </ul>
  		</div>
  		</div>
	   <div id="main">
	   	  <iframe class="main" name="main" scrolling="auto" frameborder="0" width="100%" height="100%"></iframe>
	   </div>
    </div>
  </body>
</html>
