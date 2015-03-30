<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <title>--图片上传--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
	<style type="text/css">
		
	
	</style>
  </head>
  <script type="text/javascript">
  $(function(){
  	function showstatus(){
  		$.post("<%=request.getContextPath()%>/upload/progress.action",
		function(data,status){
			var obj = eval("("+data+")");
			$("#pb").css("width",obj.precent+"%");
			$("#pb").html(obj.precent+"%");
			$("#speed").html(obj.speed+"kb/s");
			if(obj.precent>=100){
				$("#msg").append("<p>图片"+obj.item+"，"+obj.filename+"上传成功</p>"+"</br>");
			}
		});	
  	}
	$("#f1").submit(function(event){
	  	$("#comfirm").attr("display","none");
		window.setInterval(showstatus, 100);
	});
  	});
  </script>
  <body>
  	<div class="container">
  		<div class="row">
			<form action="<%=request.getContextPath()%>/upload/uploadFile.action" id="f1" enctype="multipart/form-data" class="form-horizontal" method="post">
				<input type="hidden" id="catalogid" name="catalogid" value="${pictureCatalog.id}">
				<div id="con" class="col-xs-12 col-sm-6 col-md-6">
				<div class="form-group">
           		 	<label class="control-label">文件:</label>
           		 	<input type="file" class="form-control" name="images" multiple="multiple">
				 </div>
                 <input type="submit" id="comfirm" class="btn btn-primary" value="上传" />
                </div>
       		</form>
  		</div>
  		<br>
  		<div class="row">
	  	    <div class="progress">
			  <div class="progress-bar" id="pb" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
			    0%
			  </div>
			</div>
			<div id="speed"></div>
		</div>
		<div class="row" id="msg">
		</div>
  	</div>
  	 	
  </body>
</html>
