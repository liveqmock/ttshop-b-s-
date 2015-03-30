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
    
    <title>--LIST SUPPLIERS--</title>
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
	<script type="text/javascript">
        $(function(){  
        	
        	$(document).ajaxStart(function(e){
	        	var h = window.outerHeight;
	        	$("#zhezhao").css("height",h);
	        	$("#zhezhao").css("display","block");
	         }).ajaxStop(function(e){
	         	$("#zhezhao").css("display","none");
	         });
        
        	$("#dialog").dialog({
  				autoOpen:false,
  				height:400,
  				width:500,
  				modal:true,
  				position:[50,50],
  				buttons:{
  					"保存":function(){
  						$.post("<%=request.getContextPath()%>/supplier/addsupplierAjax.action",
  						$("#f3").serialize(),
					   		function(data){
					   			if(data.indexOf("error")!=-1){
					   				alert(data);
					   				return;
					   			}else{
					   				alert("操作成功");
					   				window.location.reload();
					   				return;
					   			}
					  	 });
  						$(this).dialog("close");
  					},
  					"EXCEL导入":function(){
  						window.location.href = "<%=request.getContextPath() %>/supplier/toimportpage.action";
  					}
  				},
  				
  			}); 
  			
        	$("#opener").click(function(e){
        		e.preventDefault();
        		if(isMobile()){
        			window.location.href = "<%=request.getContextPath() %>/supplier/addsupplier.jsp?returnurl=<%=request.getContextPath() %>/supplier/listsupplier.action";
        		}else{
        			$("#dialog").dialog("open");
        		}
        	});
        	
        	$("button[name='del']").each(function(i){
        		$(this).click(function(){
        			var r = confirm("是否确认删除？");
        			if(r==true){
        		 	var sid = $(this).val();
        			$.post("<%=request.getContextPath()%>/supplier/deleteSupplier.action",
  						{supplierid:sid},
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
					   				alert(data);
					   				window.location.reload();
					   				return;
					   			}
					  	 });
					}
        		});
        	
        	});
        	
        	$("#datatable").dataTable({ "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]]});
        	
        	$("#delete").click(function(){
        		var r = confirm("是否确认删除所选中的？");
        		if(r==true){
        		$.post("<%=request.getContextPath()%>/supplier/deleteSupplierAjax.action",
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
        });
        
        function createexcel(){
			var form = document.getElementById("f1");
			var a1 = form.action;
			form.action = "<%=request.getContextPath() %>/supplier/createExcel.action";
			form.submit();
			form.action = a1;
		};    
    </script>  
  
  <body>
  <div class="container-fluid notprint">
   <div class="row">
 	<form action="<%=request.getContextPath() %>/supplier/listsupplier.action" class="form-inline" id="f1" method="post">
 		<div class="form-group">
	 		<div class="input-group">
	  			<div class="input-group-addon">关键字:</div>
	  			<input name="keyword" class="form-control" type="text" value="${keyword}"/> 
	  		</div>
  		</div>
		<button type="submit" class="btn btn-primary">搜索</button>
		<button type="button" onclick="createexcel()" class="btn btn-info">导出</button>
		<button type="button" id="opener" class="btn btn-success">添加供应商</button>
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
   				<Td> 账 号 </Td> 
   				<Td> 名 称 </Td>
   				<Td> 电 话 </Td>
   				<Td> 地 址 </Td>
   				<td> 操 作 </td>
   			</tr>
   	</thead>
   	<tbody>
   		<s:iterator value="suppliers" id="supplier" >
   			<tr>  
   				<Td> <input type="checkbox" id="ids" name="ids" value="${supplier.id }" onclick="checkone(this);"> </Td> 
   				<Td > ${supplier.supplierId } </Td> 
   				<Td > ${supplier.supplierName } </Td>
   				<Td > ${supplier.supplierTel } </Td>
   				<Td > ${supplier.supplierAddress } </Td>
   				<td > <a class="btn btn-default btn-sm clear-padding-height" href="<%=request.getContextPath() %>/supplier/toupdsupplier.action?supplierid=${supplier.supplierId }"> 修 改 </a> 
   				</td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   	</form>
   	</div>
   	<div id="dialog" title="增加供应商  ">
   		<form action="/" id="f3" class="form" method="post">
  		<fieldset>
  		 <div class="form-group">
  		 <label> 供应商编号*(不能有中文)  </label>
	  	 <input class="form-control" name="supplierid" type="text" onchange="isChinese(this)" placeholder="编 号" />
	  	 </div>
	  	 <div class="form-group">
		 <label> 供应商名称*(可以有中文)</label> 
		 <input class="form-control" name="suppliername" type="text" placeholder="名 称" />
		 </div>
		 <div class="form-group">
   		 <label> 电话 </label>  
   		 <input class="form-control" name="suppliertel" type="tel" onchange="istelephone(this)" placeholder="电 话" />
   		 </div>
   		 <div class="form-group">
   		 <label> 地址 </label> 
   		 <input class="form-control" name="supplieraddress" type="text" placeholder="地  址" />
   		 </div>
		</fieldset>
	   	</form>
   	</div>
   	<div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
  </body>
</html>
