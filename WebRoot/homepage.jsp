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
    <title>--首页--</title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main_page.css">
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
		a{
			text-decoration: none;
		}
		a:hover{
			text-decoration: none;
		}
		.glyphicon{
			margin-top: 5px;
			margin-bottom: 10px;
			display:block;
			text-align:center;
			font-size: 55px !important;
		}
		.glyphicon-class{
			font-size:16px !important;
			display: block;
			text-align:center;
			word-wrap:break-word;
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
		<div class="col-md-4 col-sm-4 well">
			<h5 class="text-center">今日收入</h5>
			<h3 id="tsq" class="text-center"></h3>
			<h3 id="tsa" class="text-center"></h3>
		</div>
		<div class="col-md-4 col-sm-4 well">
			<h5 class="text-center">本月收入</h5>
			<h3 id="msq" class="text-center"></h3>
			<h3 id="msa" class="text-center"></h3>
		</div>
		<div class="col-md-4 col-sm-4 well">
			<h5 class="text-center">本年收入</h5>
			<h3 id="ysq" class="text-center"></h3>
			<h3 id="ysa" class="text-center"></h3>
		</div>
	 </div>
 	</div>
 	<div class="container-fiuld">
 	<div class="row">
  		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/userfunction.jsp" target="main">
				<span class="glyphicon glyphicon-user"></span>
				<span class="glyphicon-class">${user.username}</span>
			</a>
		</div>
		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/upload/listPicture.action" target="main">
				<span class="glyphicon glyphicon-picture"></span>
				<span class="glyphicon-class">图片上传</span>
			</a>
		</div>
		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/printoption/toupdate.action" target="main">
				<span class="glyphicon glyphicon-cog"></span>
				<span class="glyphicon-class">设置</span>
			</a>
		</div>
  	</div>
 	<div class="row">
  		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/retial/topos.action" target="main">
				<span class="glyphicon glyphicon-shopping-cart"></span>
				<span class="glyphicon-class">销售开单</span>
			</a>
		</div>
		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/inbound/toinboundpage.action?itype=1" target="main">
				<span class="glyphicon glyphicon-download-alt"></span>
				<span class="glyphicon-class">入货开单</span>
			</a>
		</div>
		<div class="col-xs-12 col-md-4 col-sm-4 flat-span well text-center">
			<a href="<%=request.getContextPath()%>/report/profitReport.action?keyword=s-" target="main">
				<span class="glyphicon glyphicon-stats"></span>
				<span class="glyphicon-class">利润报表</span>
			</a>
		</div>
  	</div>
  </div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$.ajax({
	  		url:"<%=request.getContextPath()%>/report/daytodaySaleReportAjax.action",
	  		type:"POST",
	  		contentType:"application/x-www-form-urlencoded;charset=utf-8",
	  		success:function(data){
	  			var obj = eval('('+data+')');
	  			$("#tsq").html("售货"+obj.todaysalequantity+"件");
	  			$("#msq").html("售货"+obj.monthsalequantity+"件");
	  			$("#ysq").html("售货"+obj.yearsalequantity+"件");
	  			$("#tsa").html(obj.todaysaleamount+"元");
	  			$("#msa").html(obj.monthsaleamount+"元");
	  			$("#ysa").html(obj.yearsaleamount+"元");
	  		}
  		});
  	
  	});
  </script>
</html>
