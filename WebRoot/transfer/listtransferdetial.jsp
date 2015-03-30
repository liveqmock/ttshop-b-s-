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
	<title>--调货单明细--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<style type="text/css">
  		ul{
  			margin-left:0; 
  		}
  		ul li{
  			list-style: none;
  			margin-left:0; 
  		}
  	</style>
  </head>
  <script>
  	$(function(){
  		$(document).ajaxStart(function(e){
	        	var h = window.outerHeight;
	        	$("#zhezhao").css("height",h);
	        	$("#zhezhao").css("display","block");
	         }).ajaxStop(function(e){
	         	$("#zhezhao").css("display","none");
	    });
  		$("#print").click(function(){
				window.print();
		});
  		$("#cancel").click(function(){
				window.location.href = "<%=request.getContextPath()%>/transfer/cancelTransfer.action?id=${transfer.id}";
		});
  	});
  </script>
  <body>
  <div class="panel panel-default">
  <div class="panel-heading hidden-print">
  		<s:if test="%{transfer.status==1}">
  		<button class="btn btn-primary noprint" id="cancel">作废</button>
  		</s:if>
  		<button class="btn btn-primary noprint" id="print">打印</button>
  		<button type="button" onclick="window.history.back()" class="btn btn-primary noprint" style="float:right;"> 返回  </button>
  	</div>
   	<div class="panel-body print-border">
   		<div style="width: 50%;float: left;">
   			<p>单编号:${transfer.transferno }</p><s:if test="%{transfer.status==0}"><p class="return-msg">作废</p></s:if>
   			<p>发货仓:${transfer.warehouse }</p>
   			<p>原因:${transfer.reason}</p>
   			<p>时间:${transfer.updatetime }</p>
   		</div>
		<div style="width: 50%;float: left;">
			<p>&nbsp;</p>
			<p>收货仓:${transfer.receiverid }</p>
			<p>总数量:${transfer.totalquantity }</p>
		</div>
	   	<table class="table" width="100%">
	   		<thead>
	   			<tr class="warning">
					<td width="30%">产品编码</td>
	   				<td width="50%">描述</td>
	   				<td width="20%">数量</td>
	   			</tr>
	   		</thead>
	   		<tbody>
	   		<s:iterator value="transferLists" id="tl">
				<tr>
					<td>${tl.barcode}</td>
					<td>${tl.description}</td>
					<td>${tl.quantity}</td>
				</tr>
	   		</s:iterator>
	   		</tbody>
	   	</table>
   	</div> <!-- row -->
  </div><!-- container  -->
  <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div> 	
  </body>
</html>
