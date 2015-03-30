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
    <title>-UPDATE PRODUCT-TYPE--</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
  		<div class="panel panel-info">
  			<div class="panel-heading">
  				<h3 class="panel-title">更新客户资料
  					<button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
  			  <form action="<%=request.getContextPath() %>/ptype/updptype.action" class="form-horizontal" method="post">
   				<input type="hidden" name="typeid" value="${ptype.id}">
   					<div class="form-group">
   						<label class="col-xs-3 col-sm-2 col-md-2 control-label">产品类型</label>
   						<div class="col-xs-9 col-sm-8 col-md-6">
   							<input name="typename" type="text" readonly="readonly" value="${ptype.typeName }" class="form-control" /> 
   						</div>
   					</div>
   					<div class="form-group">
   						<label class="col-xs-3 col-sm-2 col-md-2 control-label">库存类型*</label> 
   						<div class="col-xs-9 col-sm-8 col-md-6">
   							<select name="itype" class="form-control">
								<option value="0" <s:if test="ptype.itype==0">  selected</s:if>>-不记库存产品-</option> 
				  				<option value="1" <s:if test="ptype.itype==1">  selected</s:if>>-记库存产品不记IMEI-</option>
				  				<option value="2" <s:if test="ptype.itype==2">  selected</s:if>>-记库存产品记IMEI-</option>
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
   				</form>
   			</div>
  		</div>
  	</div>
  </div>			
  </body>
</html>
