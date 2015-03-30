<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
	<title>--盘货单明细--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  </head>
  <script>
  	$(function(){
  		
  	});
  </script>
  <body>
  <div class="container-fluid">
	 <div class="panel panel-default">
	  <div class="panel-heading">
	   <h3 class="panel-title">盘点单
	   		<button class="btn btn-primary clear-padding-height pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	   </h3>
	  </div>
	  <div class="panel-body">
   	  <form action="<%=request.getContextPath()%>/checking/addchecking.action" id="f1" class="form-horozontal" autocomplete="off">
   	  	<div class="table-responsive">
   	  		<table class="table">
   	  			<tr>
   	  				<td class="warning" width="30%">盘点时间</td>
   	  				<td width="70%"> ${stockChecking.updatetime }</td>
   	  			</tr>
   	  			<tr>
   	  				<td class="warning">盘点人</td>
   	  				<td> ${stockChecking.operator }</td>
   	  			</tr>
   	  			<tr>
   	  				<td class="warning">产品信息</td>
   	  				<td>${productInfo.pdesc }</td>
   	  			</tr>
   	  			<tr>
   	  				<td class="warning"> 盘点前 </td>
   	  				<td>${stockChecking.quantity_before }</td>
   	  			</tr>
   	  			<tr>
   	  				<td class="warning"> 盘点后 </td>
   	  				<td>${stockChecking.quantity_after }</td>
   	  			</tr>
   	  			<tr>
   	  				<td class="warning"> 差额 </td>
   	  				<td>
   	  					<s:if test="stockChecking.quantity_after-stockChecking.quantity_before<0">
   	  						少 ${(stockChecking.quantity_after-stockChecking.quantity_before)*(-1) }
   	  					</s:if>
   	  					<s:elseif test="stockChecking.quantity_after-stockChecking.quantity_before==0">
   	  						0
   	  					</s:elseif>
   	  					<s:else>
   	  						多 ${stockChecking.quantity_after-stockChecking.quantity_before }
   	  					</s:else>
   	  				</td>
   	  			</tr>
   	  		</table>
   	  	</div>
   		<div class="col-xs-12 pull-left">
			<div class="form-group">
				<button type="button" id="sub" class="btn btn-sm btn-success" onclick="window.location.href='${returnurl}'">继续盘点</button>
				<a class="btn btn-danger" href="<%=request.getContextPath()%>/checking/cancelChecking.action?scid=${stockChecking.id}">作废</a>
			</div>
		</div>
   	</form>
   	</div><!-- panelbody -->
   	</div><!--panel  -->
   </div><!-- container  -->
   <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   </div>
  </body>
</html>
