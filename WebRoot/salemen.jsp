<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
    <base href="<%=basePath%>">
    <title> 欢迎使用TTPOS系统 </title>
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <style>
		body {
			padding-top:50px;
		}
		.main{
			width: 100%;
			margin-top: 2px;
		}
  	</style>
  </head>
  <body> 
   		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
   		<div class="container">
   			<div class="navbar-header">
   				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		            <span class="sr-only">Toggle navigation</span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
	          	</button>
   				<a class="navbar-brand" href="<%=request.getContextPath()%>/option.jsp" target="main"> TTSHOP </a>
   			</div>
   			<div class="navbar-collapse collapse">
		    <ul class="nav navbar-nav">
		   	<li class="nav navbar-nav">
		   		<a class="dropdown-toggle" data-toggle="dropdown">零售<b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/retial/topos.action" target="main"> 销售开单 </a> </li>
		   			<li> <a href="<%=request.getContextPath()%>/sale/findRecordByInvoice.action?keyword=s-" target="main"> 销售记录  </a> </li>	
		   		</ul>
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">库存管理 <b class="caret"></b></a>
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/stock/toinboundpage.action?itype=1" target="main">入货开单</a> </li>
		   			<li> <a href="<%=request.getContextPath()%>/stock/innoteRecords.action" target="main">入货记录 </a> </li>
		   			<%-- <li> <a href="<%=request.getContextPath()%>/stock/listinnotelist.action" target="main">入货明细</a> </li> --%>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/transfer/toTransfer.action" target="main">调货开单</a></li>
		   			<li><a href="<%=request.getContextPath()%>/transfer/listTransfer.action" target="main">调货记录</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/stock/listStock.action" target="main">查看库存</a></li>
		   		</ul>
		   	</li>
		   </ul>
		   <ul class="nav navbar-nav navbar-right">
			   <li class="dropdown">
			   		<a class="dropdown-toggle" data-toggle="dropdown">  欢迎  ${user.username }  <b class="caret"></b> </a> 
		   			<ul class="dropdown-menu">
		   			<li><a href="<%=request.getContextPath()%>/user/toupdateuser.action?userid=${user.userid }" target="main">修改用户资料</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/user/toupdatepassword.action?userid=${user.userid }" target="main">修改密码</a></li>
		   		</ul>
			   	</li>
			   	<li>
			   		<a href="<%=request.getContextPath()%>/user/logout.action">退出</a>
			   	</li>
		   </ul>
		   </div>
  		</div>
  		</div>
	  <iframe name="main" seamless width="100%" height="100%" scrolling="auto" frameborder="0" src="<%=request.getContextPath()%>/homepage.jsp"></iframe>
	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".dropdown").on("click",function(){
			});
		});
	</script>
  </body>
</html>
