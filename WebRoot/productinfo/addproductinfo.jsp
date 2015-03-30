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
    
    <title>--ADD PRODUCT-INFO--</title>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  
  <body>
  <div class="container-fluid">
  	<div class="row">
  		<div class="panel panel-info">
  			<div class="panel-heading">
  				<h3 class="panel-title">添加产品资料
  					<button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
  			<s:form action="addproductinfo" namespace="/productinfo" theme="simple" cssClass="form-horizontal"  method="post">
  				<div class="form-group">
				   	<label class="col-xs-3 col-sm-2 col-md-2 control-label">产品编码*</label>
				   	<div class="col-xs-9 col-sm-8 col-md-6">
				   		<input class="form-control" name="barcode" type="text" onchange="isChinese(this)"/>
					</div>
				</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品类型 * </label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<select class="form-control" name="ptype">
						<s:iterator id="ptype" value="ptypes">
							<option value="${ptype.id}"> ${ptype.typeName} </option>
						</s:iterator>
						</select>
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品名称 * </label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<input class="form-control" name="pdesc" type="text" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 牌子</label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<input class="form-control" name="pbrand" type="text" />  
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 颜色</label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<input class="form-control" name="pcolor" type="text" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 零售价</label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<input class="form-control" name="sprice" type="number" value="1"  id="sprice" onchange="isNumber(this)" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 最低价</label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<input class="form-control" name="mprice" type="number" value="1" id="mprice" onchange="isNumber(this)" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品介绍(限200字) </label>
  		  			<div class="col-xs-9 col-sm-8 col-md-6">
  		  				<textarea class="form-control" name="introduction" rows="3" maxlength="200"></textarea>
  		  			</div>
  		  		</div>
   				<div class="form-group">
					<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
						<div class="col-xs-9 col-sm-8 col-md-6">
							<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
		 					<button type="button" onclick="location.href='<%=request.getContextPath() %>/productinfo/toimportpage.action'" class="btn btn-success">批量添加</button>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 col-sm-2 col-md-2 control-label">快捷操作提示</label>
					<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
						<div class="col-xs-9 col-sm-8 col-md-6">
							<p class="text-info">快速复制此产品信息方法，若要录入的新产品设置与此产品一样或相差很少，只需修改有差异的项资料，然后（重要！）产品编码处（第一栏）录入新产品的编码，点击“确认”即可快速复制创建新产品信息</p>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-9 col-sm-8 col-md-6">
						<p class="text-warning">${me}</p>
					</div>
				</div>
			<s:token></s:token>
   			</s:form>
   		  </div>
  		</div>
  	</div>
  </div>
   	
  </body>
</html>
