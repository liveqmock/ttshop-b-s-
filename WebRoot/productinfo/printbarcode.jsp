<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>--打印条码--</title>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-barcode.js"></script>
  </head>
  	<script type="text/javascript">
  		$(function(){
  			$("#barcodeshow").find("div").each(function(i){
  				var b = $("#barcode").val();
  				$(this).barcode(b,"code128",{barWidth:3,barHeight:50}); 
  			});
  			
  			$("#check").on("click",function(){
  				var b = $("#barcode").val();
	  			var d = $("#pdesc").val();
  				if($(this).attr("checked")==undefined){
	  				$("#barcodeshow").find("div").each(function(i){
		  				var b = $("#barcode").val();
		  				$(this).barcode(b,"code128",{barWidth:3,barHeight:50});
		  				$(this).append("<div class='text-center'>"+d+"</div>"); 
		  				$("#check").attr("checked","checked");
	  				});
  				}else{
  						$("#barcodeshow").find("div").each(function(i){
		  				var b = $("#barcode").val();
		  				$(this).barcode(b,"code128",{barWidth:3,barHeight:50}); 
		  				$("#check").removeAttr("checked");
	  				});
  				}
  			});
  			
  			$("#quantity").on("change",function(){
  				
  			});
  			
  			$("#print").on("click",function(){
  				window.print();
  			});
  		});
  	</script>
  <body>
    <div class="panel panel-default">
    	<div class="panel-heading hidden-print">
   			<input type="checkbox" id="check" name="check">打印条码及名称
   			<a class="text-danger">|</a>
   			<label class="control-label">行数</label>
   			<input type="text" id="quantity" name="quantity" value="1">
   			<a class="text-danger">|</a>
   			<button type="button" id="print" class="btn btn-sm btn-primary">打印</button>
   			<input type="hidden" name="barcode" id="barcode" value="${productinfo.barcode }">
   			<input type="hidden" name="pdesc" id="pdesc" value="${productinfo.pdesc}">
   			<button type="button" onclick="location.href='${returnurl}'" class="btn btn-sm btn-primary pull-right">返回</button>
    	</div>
    	<div class="panel-body print-border">
	   		<div id="barcodeshow" class="col-xs-12 col-sm-12 col-md-12">
	   			<div class="col-xs-12 col-sm-6 col-md-6"></div>
	   			<div class="col-xs-12 col-sm-6 col-md-6"></div>
	   		</div>
   		</div>
    </div>
  </body>
</html>
