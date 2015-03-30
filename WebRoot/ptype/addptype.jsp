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
    
    <title>--ADD PRODUCT-TYPE--</title>
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
  				<h3 class="panel-title">添加类型 
  					<button type="button" onclick="window.history.back()" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
			  <s:form action="addptype" namespace="/ptype" theme="simple" cssClass="form-horizontal" method="post">
				<fieldset>
			  		 <div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label">产品类型*</label> 
				  		<div class="col-xs-9 col-sm-8 col-md-6">
			  		 		<input name="typename" type="text" class="form-control" placeholder="产品类型"/>
				  		</div>
			  		 </div>
			  		 <div class="form-group">
				  		<label class="col-xs-3 col-sm-2 col-md-2 control-label">库存类型*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">   
				  		 	<select name="itype" class="form-control">
					  			<option value="0">-不记库存产品-</option>
					  			<option value="1">-记库存产品不记IMEI-</option>
					  			<option value="2">-记库存产品记IMEI-</option>
				  			</select>
			  			</div>
			  		</div>
					<div class="form-group">
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<div class="col-xs-9 col-sm-8 col-md-6">
							<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
							<button type="button" onclick="location.href='<%=request.getContextPath()%>/ptype/toimportpage.action'" class="btn btn-success">批量导入</button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="well">
				  			<p>我该如何选择库存类型?</p>
				  			<p>该类型设置为<span style="color:red;">"不记库存产品"</span>,该产品在"入库"及"销售"系统操作时将不计算库存增减,实物产品不选这项,例如"某种形式的服务",服务我们无法计算库存,所以应选择这个</p>
				  			<p>该类型设置为<span style="color:red;">"记库存产品不记IMEI"</span>,该产品在"入库"及"销售"系统操作时会连同计算库存增减,适用极大部分情况</p>
				  			<p>该类型设置为<span style="color:red;">"记库存产品记IMEI"</span>,该产品在"入库"及"销售"系统操作时会连同计算库存增减,并且该产品一定要求有 imei, 例如电子产品都由 sn/ 或者 imei, 系统会额外记录该 imei 的操作记录</p>
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
