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
    
    <title>--LIST WAREHOUSES--</title>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  <body>
  <div class="container-fluid notprint">
   <div class="row">
 	<form action="<%=request.getContextPath() %>/warehouse/listwarehouse.action" id="f1" class="form-inline" method="post">
 		<div class="input-group">
  			<div class="input-group-addon">关键字:</div>
  			<input name="keyword" class="form-control" type="text" value="${keyword}"/> 
  		</div>
		<button type="submit" class="btn btn-primary">搜索</button>
		<button type="button" onclick="createexcel()" class="btn btn-info">导出</button>
		<button type="button" id="opener" class="btn btn-success">添加仓库</button>
		<button type="button" id="delete" class="btn btn-danger" style="visibility: hidden;">删除选中</button>
		<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
  	</form>
  	</div>
  	<form action="#" id="f2">
  	<div class="row table-responsive">
   	<table class="table display" id="datatable" cellspacing="0" width="100%">
   	<thead>
			<tr class="warning">  
				<td> <input type="checkbox" id="all" onclick="checkall(this)"> </td>
				<Td> 编号 </Td> 
				<Td> 名称 </Td> 
				<Td> 管理员 </Td>
				<td > # </td>
			</tr>
   	</thead>
   	<tbody>
   		<s:iterator value="warehouses" id="warehouse" >
   			<tr>  
   			    <Td> <input type="checkbox" id="ids" name="ids" value="${warehouse.id }" onclick="checkone(this);"> </Td> 
   				<Td> ${warehouse.wname } </Td> 
   				<Td> ${warehouse.wnickname } </Td> 
   				<Td> ${warehouse.wadmin } </Td>
   				<td> 
   				<a class="btn btn-default btn-sm clear-padding-height" href="<%=request.getContextPath() %>/warehouse/toupdwarehouse.action?id=${warehouse.id }" > 修改 </a> 
   				</td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   	</form>
   	</div>
   <div id="dialog" title="添加仓库">
   	<s:form action="addwarehouse" id="f3" namespace="/warehouse" theme="simple" cssClass="form" method="post">
	 <fieldset>
	 	<div class="form-group">
  		<label>仓库编号*(不能有中文)</label> 
  		<input class="form-control" type="text" name="wname"  placeholder="编号" onchange="isChinese(this)" /> 
  		</div>
  		<div class="form-group">
		<label>名称(可以有中文)</label> 
		  	<input type="text" name="wnickname" class="form-control" placeholder="名称"/>
		</div>
  		<div class="form-group">
		<label> 管理人 </label>  
		<input class="form-control" type="text" name="wadmin" placeholder="管理人"/>
		</div>
		<div class="form-group">
		    <p>注意：</p>
			<p class="text-info">仓库名称不能用中文</p>
		</div>
	</fieldset>
   	</s:form>
   	</div>
   	<div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
  </body>
  <script type="text/javascript">
       $(function(){
       	$("#dialog").dialog({
 				autoOpen:false,
 				height:400,
 				width:500,
 				modal:true,
 				position:[50,20],
 				buttons:{
 					"保存":function(){
 						$.post("<%=request.getContextPath()%>/warehouse/addwarehouseAjax.action",
 						$("#f3").serialize(),
				   		function(data){
				   			if(data.indexOf("error")!=-1){
				   				if(data.indexOf("error01")!=-1){
				   					alert("此ID已被使用！");
				   					return;
				   				}else if(data.indexOf("error02")!=-1){
				   					alert("*栏为必要填写资料，请正确填写！");
				   					return;
				   				}
				   			}else{
				   				alert("操作成功");
				   				window.location.reload();
				   				return;
				   			}
				  	 });
 						$(this).dialog("close");
 					}
 				},
 				
 			}); 
       	$("#opener").click(function(e){
       		e.preventDefault();
       		if(isMobile()){
       			window.location.href = "<%=request.getContextPath() %>/warehouse/addwarehouse.jsp?returnurl=<%=request.getContextPath() %>/warehouse/listwarehouse.action";
       		}else{
       			$("#dialog").dialog("open");
       		}
       	});
       	$("#delete").click(function(){
       		var r = confirm("是否确认删除所选中的？");
       		if(r==true){
       		$.post("<%=request.getContextPath()%>/warehouse/deletewarehouseAjax.action",
				$("#f2").serialize(),
		   		function(data){
		   			if(data.indexOf("error")!=-1){
		   				alert(data);
		   				return;
		   			}else{
		   				alert(data);
		   				window.location.reload();
		   				return;
		   			}
		  	 });
		  	}
       	});
       	$("#datatable").dataTable({ "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]]});
       });  
       function createexcel(){
		var form = document.getElementById("f1");
		var a1 = form.action;
		form.action = "<%=request.getContextPath() %>/warehouse/createExcel.action";
		form.submit();
		form.action = a1;
	};     
   </script>
  <script type="text/javascript">
  	function isChinese(obj){
	  var str = obj.value;
	  var reg1 = /.*[\u4e00-\u9fa5]+.*$/;
	  if(!reg1.test(str)){ 
		  return false; 
	  } 
	  alert("仓库名称不能有中文");
	  obj.value="";
	  return true; 
	};
  </script>
</html>
