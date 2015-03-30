<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title> 欢迎使用 TTPOS系统  </title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">
  	<meta name="apple-mobile-web-app-capable" content="yes">
  	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
  	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <style>
		body{
			height:100%;
			min-height: 100%;
		}
		.navbar{
			border-radius:0!important;
			margin-bottom:0!important;
		}
  	</style>
  </head>
  <body>
   		<nav id="navigation" class="navbar navbar-inverse" role="navigation">
   		<div class="container-fluid">
   			<div class="navbar-header">
   				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse" aria-expanded="false" aria-controls="navbar">
		            <span class="sr-only">Toggle navigation</span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
	          	</button>
   				<a class="navbar-brand" href="<%=request.getContextPath()%>/homepage.jsp" target="main"> 
   				TTSHOP</a>
   			</div>
   			<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
			   	<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown">基础资料<b class="caret"></b></a>
			   	<ul class="dropdown-menu" role="menu">
				   	<li> <a href="<%=request.getContextPath()%>/supplier/listsupplier.action" target="main">供应商 </a> </li>
				   	<li class="divider"></li>
				   	<li> <a href="<%=request.getContextPath()%>/customer/listcustomer.action" target="main">客户</a></li>
				   	<li class="divider"></li>
				   	<li> <a href="<%=request.getContextPath()%>/warehouse/listwarehouse.action" target="main">仓库</a></li>
				   	<li class="divider"></li>
				   	<li> <a href="<%=request.getContextPath()%>/ptype/listptype.action" target="main">产品类型</a></li>
				   	<li class="divider"></li>
				   	<li><a href="<%=request.getContextPath()%>/productinfo/listproductinfo.action" target="main">产品列表</a></li>
				   	<%-- <!--<li><a href="<%=request.getContextPath()%>/productinfo/listproductprice.action" target="main">报价列表</a></li>--> --%>
				   	<li><a href="<%=request.getContextPath()%>/productinfo/toaddproductinfo.action" target="main">添加产品</a></li>
			   	</ul>	
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">销售<b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/retial/topos.action" target="main">销售开单</a></li>
		   			<li> <a href="<%=request.getContextPath()%>/sale/findRecordByDate.action?keyword=s-" target="main">销售记录</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/tuihuo/toaddReturnSale.action" target="main">退货开单</a></li>
		   			<li><a href="<%=request.getContextPath()%>/tuihuo/listReturnSale.action" target="main">退货记录</a></li>
		   		</ul>
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">库存管理 <b class="caret"></b></a>
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/inbound/toinboundpage.action?itype=1" target="main">入货开单</a> </li>
		   			<li> <a href="<%=request.getContextPath()%>/inbound/innoteRecords.action" target="main">入货记录 </a> </li>
		   			<%-- <li> <a href="<%=request.getContextPath()%>/inbound/listinnotelist.action" target="main">入货明细</a> </li> --%>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/transfer/toTransfer.action" target="main">调货开单</a></li>
		   			<li><a href="<%=request.getContextPath()%>/transfer/listTransfer.action" target="main">调货记录</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/checking/toCheckingPage.action" target="main">库存盘点</a></li>
		   			<li><a href="<%=request.getContextPath()%>/checking/listCheckingList.action" target="main">盘点记录</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/stock/listStock.action" target="main">查看库存</a></li>
		   		</ul>
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">报表 <b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/reportfunction.jsp" target="main">销售统计</a></li>
		   			<li> <a href="<%=request.getContextPath()%>/report/profitReport.action?keyword=s-" target="main">利润报表</a></li>
		   			<%-- <!-- <li> <a href="<%=request.getContextPath()%>/sale/listCount.action" target="main"> 销售统计  </a> </li> --> --%>
		   			<%-- <!-- <li><a href="<%=request.getContextPath()%>/report/saleImeiReport.action" target="main">销售明细</a></li>--> --%>
		   			<%-- <!-- <li><a href="<%=request.getContextPath()%>/stock/toinitStock.action" target="main">初始化库存</a></li>  --> --%>
		   		</ul>
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">用户管理 <b class="caret"></b> </a> 
		   		<ul class="dropdown-menu">
		   			<li> <a href="<%=request.getContextPath()%>/user/listusers.action" target="main">用户列表</a></li>
		   		</ul>
		   	</li>
		   	<li class="dropdown">
		   		<a class="dropdown-toggle" data-toggle="dropdown">系统管理<b class="caret"></b></a> 
		   		<ul class="dropdown-menu">
		   			<%-- <!--  <li><a href="<%=request.getContextPath()%>/stock/revocerStock.action" target="main"> 库存恢复 </a></li> --> --%>
		   			<%-- <li><a href="<%=request.getContextPath()%>/backup.jsp" target="main">数据备份</a></li>
		   			<li class="divider"></li> --%>
		   			<li><a href="<%=request.getContextPath()%>/printoption/toupdate.action" target="main">发票格式设置</a></li>
		   		</ul>
		   	</li>
		  <li>
		   		<a href="<%=request.getContextPath()%>/upload/listPicture.action" target="main">图片管理</a>
		   		<%-- <a href="<%=request.getContextPath()%>/upload/upload.jsp" target="_blank">图片管理</a> --%>
		   	</li>
		   </ul>
		   <ul class="nav navbar-nav navbar-right">
			   	<li class="dropdown">
			   		<a class="dropdown-toggle" data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>欢迎 ${user.username }<b class="caret"></b></a> 
		   			<ul class="dropdown-menu">
		   			<li><a href="<%=request.getContextPath()%>/user/toupdateuser.action?optype=1&userid=${user.userid }" target="main">修改用户资料</a></li>
		   			<li class="divider"></li>
		   			<li><a href="<%=request.getContextPath()%>/user/toupdatepassword.action?optype=1&userid=${user.userid }" target="main">修改密码</a></li>
		   		</ul>
			   	</li>
			   	<li>
			   		<a href="<%=request.getContextPath()%>/user/logout.action"><span class="glyphicon glyphicon-off"></span>退出</a>
			   	</li>
		   </ul>
		   </div>
  		</div>
  		</nav>
  		<div class="container-fluid" style="height:100%;" id="content">
	  		<div class="row-fluid" style="height:100%;">
			   	<iframe name="main" width="100%" height="100%" scrolling="auto" frameborder="0" src="<%=request.getContextPath()%>/homepage.jsp"></iframe>
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
   		<!-- dashboard功能menu -->
   		<div id="dashzz"></div>
		<div class="dashboard" id="dashboard">		
			<ul>
				<li><a href="javascript:" id="openmenu"><span class="glyphicon glyphicon-th-large"></span></a></li>
			</ul>
			<div class="menu" id="menu">
				<div style="display: inline-block;float: left;"></div>
				<ul>
					<!-- <li><a href="javascript:" id="close">
						<span class="glyphicon glyphicon-th-list"></span>
					</a></li> -->
					<li><a href="<%=request.getContextPath()%>/homepage.jsp" target="main">
						<span class="glyphicon glyphicon-home"></span>
						<span class="glyphicon-class">主页</span>
					</a></li>
					<li><a href="<%=request.getContextPath()%>/infofunction.jsp" target="main">
						<span class="glyphicon glyphicon-info-sign"></span>
						<span class="glyphicon-class">资料</span>
						</a></li>
					<li><a href="<%=request.getContextPath()%>/salefunction.jsp" target="main">
						<span class="glyphicon glyphicon-shopping-cart"></span>
						<span class="glyphicon-class">销售</span>
					</a></li>
					<li><a href="<%=request.getContextPath()%>/warehousefunction.jsp" target="main">
						<span class="glyphicon glyphicon-tower"></span>
						<span class="glyphicon-class">仓库</span>
						</a></li>
					<li><a href="<%=request.getContextPath()%>/reportfunction.jsp" target="main">
						<span class="glyphicon glyphicon-list-alt"></span>
						<span class="glyphicon-class">报表</span>
						</a></li>
					<li><a href="<%=request.getContextPath()%>/upload/listPicture.action" target="main">
						<span class="glyphicon glyphicon-picture"></span>
						<span class="glyphicon-class">图片</span>
						</a></li>
					<li><a href="<%=request.getContextPath()%>/userfunction.jsp" target="main">
						<span class="glyphicon glyphicon-user"></span>
						<span class="glyphicon-class">${user.username}</span>
					</a></li>
					<li><a href="<%=request.getContextPath()%>/printoption/toupdate.action" target="main">
						<span class="glyphicon glyphicon-cog"></span>
						<span class="glyphicon-class">设置</span>
						</a></li>
					<li><a href="javascript:" id="totop">
						<span class="glyphicon glyphicon-upload"></span>
						</a></li>
				</ul>
			</div>
		</div>
  </body>
  <script src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript">
	$(function(){
		function initiframe(){
			var wh = $(window).height();
			$("iframe").height(wh);
		};
		initiframe();
		$(".dropdown").on("click",function(){
		});
		$(".dropdown-menu").on("click",function(){
			$(".dropdown").click();
		});
		$("#dashzz").on("click",function(){
			$("#menu").css("display","none");
			$("#dashzz").css("display","none");
			if($(window).width()>768){
				$("#navigation").css("display","block");
			}else{
				$("#navigation").css("display","none");
			}
		});
		$("#openmenu").on("click",function(){
			$("#navigation").css("display","none");
			$("#menu").css("display","block");
			$("#dashzz").css("display","block");
		});
		$("#totop").on("click",function(){
			scrollTopest();
		});
		function scrollTopest(){
			$("body").scrollTop(0);
		};
	});
  </script>
</html>
