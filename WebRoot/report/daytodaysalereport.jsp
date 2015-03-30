<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title> 销售统计 </title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  </head>
  
  <body>
  	<div class="container-fliud">
  		<div class="row-fliud">
			<div class="well text-center col-xs-12 col-sm-6 col-md-3 col-lg-3">
				<h3><span><a href="<%=request.getContextPath()%>/report/daytodaySaleReportBypay.action">按收银统计</a></span></h3>
				<h3><img src="./images/combo_chart.png" style="width:50%;"></h3>
			</div>
			<div class="well text-center col-xs-12 col-sm-6 col-md-3 col-lg-3">
				<h3><span><a href="<%=request.getContextPath()%>/report/daytodaySaleReportBytype.action">按类型统计</a></span></h3>
				<h3><img src="./images/combo_chart.png" style="width:50%;"></h3>
			</div>
			<div class="well text-center col-xs-12 col-sm-6 col-md-3 col-lg-3">
				<h3><span><a href="<%=request.getContextPath()%>/report/daytodaySaleReportByproduct.action">按产品统计</a></span></h3>
				<h3><img src="./images/combo_chart.png" style="width:50%;"></h3>
			</div>
			<div class="well text-center col-xs-12 col-sm-6 col-md-3 col-lg-3">
				<h3><span><a href="<%=request.getContextPath()%>/report/daytodaySaleReportBycustomer.action">按客户统计</a></span></h3>
				<h3><img src="./images/combo_chart.png" style="width:50%;"></h3>
			</div>
  		</div>
  	</div>
  </body>
</html>
