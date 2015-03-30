<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
  	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <title>报表</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<style type="text/css">
		h3{
			color: green;
			font-weight: bold;
		}
		h5{
			font-weight: bolder;
		}
		img{
			width: 160px;
			height: 160px;
		}
		.glyphicon{
			margin-top: 5px;
			margin-bottom: 10px;
			display:block;
			text-align:center;
			font-size: 45px !important;
		}
		.glyphicon-class{
			font-size:16px !important;
			display: block;
			text-align:center;
			word-wrap:break-word;
		}
		.row div{
			padding-top: 20px;
			padding-bottom: 20px;
			background-color: #eee;
			border: 1px solid #fff;
		}
		@MEDIA screen and (max-width: 720px) {
			img{
				width: 80px;
				height: 80px;
			}
		}
	</style>
  </head>
  <body>
 	<div class="container-fiuld">
 		<div class="row">
			<div class="col-xs-12 col-md-4 col-sm-4 text-center">
				<a href="<%=request.getContextPath()%>/report/daytodaySaleReportBypay.action" target="main">
					<span class="glyphicon glyphicon-stats"></span>
					<span class="glyphicon-class">收银统计</span>
				</a>
			</div>
			<div class="col-xs-12 col-md-4 col-sm-4 text-center">
				<a href="<%=request.getContextPath()%>/report/daytodaySaleReportBytype.action" target="main">
					<span class="glyphicon glyphicon-stats"></span>
					<span class="glyphicon-class">类型统计</span>
				</a>
			</div>
			<div class="col-xs-12 col-md-4 col-sm-4 text-center">
				<a href="<%=request.getContextPath()%>/report/daytodaySaleReportByproduct.action" target="main">
					<span class="glyphicon glyphicon-stats"></span>
					<span class="glyphicon-class">产品统计</span>
				</a>
			</div>
			<div class="col-xs-12 col-md-4 col-sm-4 text-center">
				<a href="<%=request.getContextPath()%>/report/daytodaySaleReportBycustomer.action" target="main">
					<span class="glyphicon glyphicon-stats"></span>
					<span class="glyphicon-class">客户统计</span>
				</a>
			</div>
			<div class="col-xs-12 col-md-4 col-sm-4 text-center">
				<a href="<%=request.getContextPath()%>/report/profitReport.action?keyword=s-" target="main">
					<span class="glyphicon glyphicon-usd"></span>
					<span class="glyphicon-class">利润报表</span>
				</a>
			</div>
		</div>
	 </div>
  </body>
</html>
