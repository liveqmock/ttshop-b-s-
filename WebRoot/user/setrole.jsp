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
    <title>--设置用户职位--</title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  <body>
  <div class="container-fluid">
  	<div class="row">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				<h3 class="panel-title">修改权限  
  					<button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
			  <form action="<%=request.getContextPath() %>/user/setrole.action" method="post" class="form-horizontal">
			 	 <fieldset>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">用户账号*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="userid" type="text" readonly="readonly" value="${user.userid}" required/>
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">用户名*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="username" type="text" readonly value="${user.username }" required/>
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">权限*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<select class="form-control" name="role">
					   		 	<option value="0" <s:if test="user.role==0">selected</s:if>>管理员</option>
					   		 	<option value="1" <s:if test="user.role==1">selected</s:if>>销售员</option>
					   		 	<option value="2" <s:if test="user.role==2">selected</s:if>>仓管员</option>
					   		 	<option value="3" <s:if test="user.role==3">selected</s:if>>经理</option>
					   		</select>
				  		</div>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<div class="col-xs-9 col-sm-8 col-md-6">
							<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-9 col-sm-8 col-md-6">
							<p class="text-warning">${me}</p>
						</div>
					</div>
				</fieldset>
			   	</form>
			</div>
   		</div>
   	</div>
   </div>
  </body>
</html>
