<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>--ADD CUSTOMER--</title>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  
  <body>
  <div class="container-fluid">
  	<div class="row">
  		<div class="panel panel-info">
  			<div class="panel-heading">
  				<h3 class="panel-title">增加客户
  					<button type="button" onclick="window.history.back()" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
			  <s:form action="addcustomer" namespace="/customer" theme="simple" cssClass="form-horizontal" method="post">
				<fieldset>
			  		<div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 客户编号*(不能有中文) </label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input name="customerid" type="text" class="form-control" placeholder="客户编号" onchange="isChinese(this)"/>
				  		</div>
				  	</div>
					<div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 客户名称*(可以有中文) </label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input name="customername" type="text" class="form-control" placeholder="客户名称"/>
				  		</div>
				  	</div>
			   		<div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 电话 </label>
				  		<div class="col-xs-9 col-sm-8 col-md-6"> 
				  			<input name="customertel" type="tel" class="form-control" placeholder="电话" onchange="istelephone(this)"/>
				  		</div>
				  	</div>
			   		<div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 地址 </label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input name="customeraddress" type="text" class="form-control" placeholder="地址"/>
				  		</div>
				  	</div>
				  	<div class="form-group">
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<div class="col-xs-9 col-sm-8 col-md-6">
								<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
								<button type="button" onclick="location.href='<%=request.getContextPath() %>/customer/toimportpage.action'" class="btn btn-success"> 批量导入 </button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-9 col-sm-8 col-md-6">
							<p class="text-warning">${me}</p>
						</div>
					</div>
				</fieldset>
				<s:token></s:token>
			   	</s:form>
  			</div>
  		</div>
  	</div>
  </div> 	
  </body>
</html>
