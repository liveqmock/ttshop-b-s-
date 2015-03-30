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
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <title> --欢迎使用TTPOS系统--  </title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css">
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
   				<a class="navbar-brand" href="#" target="main"> TTPOS </a>
   			</div>
   			<div class="navbar-collapse collapse">
		    <ul class="nav navbar-nav">
		   	<li> <a class="dropdown-toggle" data-toggle="dropdown">基础资料<b class="caret"></b> </a> 
		   	<ul class="dropdown-menu">
			   	<li> <a href="<%=request.getContextPath()%>/supplier/listsupplier.action" target="main"> 供应商 </a> </li>
			   	<li class="divider"></li>
			   	<li> <a href="<%=request.getContextPath()%>/customer/listcustomer.action" target="main"> 客户 </a> </li>
			   	<li class="divider"></li>
			   	<li> <a href="<%=request.getContextPath()%>/warehouse/listwarehouse.action" target="main"> 仓库  </a> </li>
			   	<li class="divider"></li>
			   	<li> <a href="<%=request.getContextPath()%>/ptype/listptype.action" target="main"> 类型 </a> </li>
			   	<li class="divider"></li>
			   	<li> <a href="<%=request.getContextPath()%>/productinfo/listproductinfo.action" target="main"> 产品列表</a> </li>
			   	<li> <a href="<%=request.getContextPath()%>/productinfo/listproductprice.action" target="main"> 报价列表</a> </li>
			   	<li> <a href="<%=request.getContextPath()%>/productinfo/toaddproductinfo.action" target="main"> 添加产品</a> </li>
		   	</ul>
		   	</li>
		   	<li class="nav navbar-nav">
		   		<a class="dropdown-toggle" data-toggle="dropdown">零售<b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/stock/toinboundpage.action?itype=1" target="main"> 入货 </a> </li>
		   			<li> <a href="<%=request.getContextPath()%>/stock/innotelist.action" target="main"> 入货记录 </a> </li>
		   			<li> <a href="<%=request.getContextPath()%>/stock/listinnotelist.action" target="main"> 入货明细 </a> </li>
		   		</ul>
		   	</li>
		   	<li class="nav navbar-nav">
		   		<a class="dropdown-toggle" data-toggle="dropdown"> 调货 <b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li><a href="<%=request.getContextPath()%>/transfer/toTransfer.action" target="main">调货</a></li>
		   			<li><a href="<%=request.getContextPath()%>/transfer/listTransfer.action" target="main">调货单</a></li>
		   		</ul>
		   	</li>
		   	<li class="nav navbar-nav">
		   		<a class="dropdown-toggle" data-toggle="dropdown"> 报表  <b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/report/daytodaySaleReport.action?keyword=s-" target="main"> 销售统计  </a> </li>
		   			<li><a href="<%=request.getContextPath()%>/stock/listStock.action" target="main"> 库存 </a></li>
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
		<div class="embed-responsive embed-responsive-16by9">
	   	  <iframe class="embed-responsive-item" name="main" scrolling="auto" frameborder="0" src="<%=request.getContextPath()%>/homepage.jsp" width="100%" height="100%"></iframe>
	   </div>
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
