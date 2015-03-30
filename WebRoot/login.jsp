<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">
  	<meta name="apple-mobile-web-app-capable" content="yes">
  	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
  	<meta name="apple-touch-fullscreen" content="yes">
  	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <base href="<%=basePath%>">
    <title>--系统登陆--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/login_part.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
  </head>
  <body>
  	<div class="container main">
		<div class="row">  
			<div class="col-xs-12 col-sm-8 col-md-8 flat-span">
				<div class="span color1">
				  <div class="inner-left">
				  <h3><span class="glyphicon glyphicon-thumbs-up"></span>
				  简单易用紧跟潮流的 O2O 销售管理软件</h3>
  				  <h5><span class="glyphicon glyphicon-check"></span>
  				  1.简单易用,无需任何专业知识,4步让你轻松实现店铺日常销售管理</h5>
                  <h5><span class="glyphicon glyphicon-cloud"></span>
                  2.随时随地操作,无论你身在何处,一开手机即可查看店铺实时销售情况</h5>
                  <h5><span class="glyphicon glyphicon-stats"></span>
                  3.清晰明确的销售报表,利润报表,助你通往成功</h5>
                  </div>
                  <div class="inner-right">
                  <h3>立即使用<span class="glyphicon glyphicon-road"></span>通往成功</h3>
  				  <p><a class="btn btn-warning btn-lg" href="<%=request.getContextPath()%>/user/register.jsp" data-role="button">点击注册</a>
  				  </p>
  				  </div>				
                </div>
			</div>
			<div class="col-xs-12 col-sm-4 col-md-4 flat-span">
				<div class="span color2">
				<h4 class="font-color-wihte pull-left"><span class="glyphicon glyphicon-user"></span>登录</h4>
				<form action="<%=request.getContextPath()%>/user/login.action" method="post" class="form" role="form" target="_top">
					<div class="form-group">
						 <input type="text" name="userid" value="admin" placeholder="User ID" class="form-control" >
					</div>
					<div class="form-group">
						 <input type="password" name="password" value="1987September" placeholder="Password" class="form-control">
					</div>
					<button class="btn btn-success" id="login">登录</button>
				</form>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 flat-span">
				<div class="span color3">
				<div class="inner-left">
				<form action="" id="f1" class="form">
					<h3><span class="glyphicon glyphicon-home"></span>&nbsp;TT美妆小铺-购物频道</h3>
	  				<h5><span class="glyphicon glyphicon-hand-right"></span>&nbsp;TT美妆小铺,设在广州市南沙自贸区内,经营各种化妆品牌,实体店经营年多时间,信誉良好</h5>
	  				<h5><span class="glyphicon glyphicon-hand-right"></span>&nbsp;各种日韩化妆品和护符品应有尽有 </h5>
	  				<h5><span class="glyphicon glyphicon-hand-right"></span>&nbsp;美丽大学生创业,诚信为先,开张以来得到广大学生,新入职场的毕业生,年轻辣妈的支持 </h5>
	  				<h5><span class="glyphicon glyphicon-hand-right"></span>&nbsp;立即点击<span class="glyphicon glyphicon-hand-down"></span>献给想要靓靓的你
	  				<span class="glyphicon glyphicon-heart"></span></h5>
	   				<p><a class="btn btn-warning btn-lg" href="<%=request.getContextPath()%>/webshop/towebshop.action" data-role="button">购物频道</a></p>
				</form>
				</div>
				<div class="inner-right" style="vertical-align: middle;text-align: center;">
				    <div class="barcode-box">
				    	<div id="barcode">
							<img alt="he84976468" src="./images/ttshop_barcode.jpg">
						</div>
				    	<h5>店主<span class="glyphicon glyphicon-qrcode"></span></h5>
					</div>
					<div class="barcode-box">
				    	<div id="barcode2">
							<img alt="he84976468" src="./images/barcode_gz.jpg">
						</div>
				    	<h5>公众号<span class="glyphicon glyphicon-qrcode"></span></h5>
					</div>
				</div>
				</div>
			</div>
		</div>
		<footer class="footer">
			<div class="container-fluid">
	   		 	<div class="text-center">
	    				<span class="text-info" style="border-radius:0 ">Copyright &copy; 2015 david_du1987@126.com </span>
	    			</div>
  			</div>
   		</footer>
	</div><!-- container -->
  </body>
  <script type="text/javascript" src="./js/myhtml5fileupload.js"></script>
  <script type="text/javascript">
  	$(function(){
  		var w= $(".inner-right").width()/2+30;
  		$("#barcode").responsiveimage(w);
  		$("#barcode2").responsiveimage(w);
  	});
  </script>
</html>
