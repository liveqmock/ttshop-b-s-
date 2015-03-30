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
    
    <title>--LIST PRODUCT-INFOS--</title>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-barcode.js"></script>
	<style type="text/css" media="print">
		.notprint {
			display: none;
		}
		.print {
			display: block;
			width:200mm;
			height:145mm;
		}
	</style>
  </head>
  <body>
   <div class="container-fluid notprint">
    <div class="row">
    <form action="<%=request.getContextPath() %>/productinfo/listproductinfo.action" class="form-inline" id="f1" method="post">
 		<div class="form-group">
	 		<div class="input-group">
	  			<div class="input-group-addon">关键字:</div>
	  			<input name="keyword" class="form-control" type="text" value="${keyword}"/> 
	  		</div>
  		</div>
		<button type="submit" class="btn btn-primary">搜索</button>
		<button type="button" onclick="createexcel()" class="btn btn-info">导出</button>
		<button type="button" id="opener" class="btn btn-success">添加产品</button>
		<button type="button" id="delete" class="btn btn-danger" style="visibility: hidden;">删除选中</button>
		<button type="button" id="setallup" class="btn btn-info" style="visibility: hidden;">上架</button>
		<button type="button" id="setalldown" class="btn btn-warning" style="visibility: hidden;">下架</button>
		<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	</form>
	</div>
	<form action="#" id="f2">
	<div class="row table-responsive">
   	<table class="table display" id="datatable" cellspacing="0" width="100%">
   	<thead>
   	<tr class="warning">
	<Td> <input type="checkbox" id="all" onclick="checkall(this)"> </Td> 
	<Td> 产品编码 </Td> 
	<Td> 类 型 </Td>
	<Td> 名 称 </Td>
	<Td> 品 牌 </Td>
	<Td> 颜 色 </Td>
	<Td> 价 格  </Td>
	<Td> 最低价 </Td>
	<Td> 销售状态 </Td>
	<td> 条码 </td>
	</tr>
   	</thead>
   	<tbody>
   		<s:iterator value="productinfos" id="p" >
   			<tr>  
   				<Td> <input type="checkbox" id="ids" name="ids" value="${p.id }" onclick="checkone(this);"> </Td> 
   				<Td> <a href="<%=request.getContextPath() %>/productinfo/toupdproductinfo.action?barcode=${p.barcode }"> ${p.barcode } </a> </Td>
   				<Td> ${p.ptype } </Td>
   				<Td> ${p.pdesc } </Td>
   				<Td> ${p.pbrand } </Td>
   				<Td> ${p.pcolor } </Td>
   				<Td> ${p.sprice } </Td>
   				<Td> ${p.mprice } </Td>
   				<td> 
   				<s:if test="#p.saleornot==0">
   					<span class="text-danger">下架</span> 
   				</s:if>
   				<s:elseif test="#p.saleornot==1">
   					<span class="text-success">上架</span>  
   				</s:elseif>
   				</td>
   				<td>
   					<a class="btn btn-default btn-sm clear-padding-height" id="baropener" data-name="baropener" data-value="${p.barcode }">条码</a>
   				</td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   	</form>
   	</div>
   	<div class="notprint" id="dialog" title="增加产品">
   		<form action="" id="f3" class="form">
   		<div class="row">
	  		<div class="col-xs-6 col-sm-6 col-md-6">
			<label>产品类型*</label>   
			<select name="ptype" class="form-control">
			<s:iterator id="ptype" value="ptypes">
				<option value="${ptype.id}"> ${ptype.typeName} </option>
			</s:iterator>
			</select>
			</div>
		    <div class="col-xs-6 col-sm-6 col-md-6">
		    <label>产品编码*</label> <input name="barcode" type="text" class="form-control" placeholder="BARCODE" onkeyup="isChinese(this)" onchange="isChinese(this)"/>
		    </div>
		    <div class="col-xs-6 col-sm-6 col-md-6">
	  		 <label>产品名称*</label> <input name="pdesc" type="text" class="form-control" placeholder="DESCRATION" />
	  		 </div>
	  		 <div class="col-xs-6 col-sm-6 col-md-6">
	  		 <label>牌子</label> <input name="pbrand" type="text" class="form-control" placeholder="BRAND"/>  
	  		 </div>
	  		 <div class="col-xs-6 col-sm-6 col-md-6">
	  		 <label>颜色</label> <input  name="pcolor" type="text" class="form-control" placeholder="COLOR" />  
	  		 </div>
	  		 <div class="col-xs-6 col-sm-6 col-md-6">
	  		 <label>零售价</label> <input name="sprice" type="number" class="form-control" value="0.0" id="sprice" onchange="check(this)" /> 
	  		 </div>
	  		 <div class="col-xs-6 col-sm-6 col-md-6">
	  		 <label>最低价</label> <input name="mprice" type="number" class="form-control" value="0.0" id="mprice" onchange="check(this)"  />  
	  		 </div>
	  		 <div class="col-xs-6 col-sm-6 col-md-6">
	  		 </div>
	  		 <div class="col-xs-12 col-sm-12 col-md-12">
	  		 <label>产品介绍</label> 
	  		 	<textarea class="form-control" name="introduction" rows="3"></textarea>
	  		 </div>
	 	 </div>
   		</form>
   	</div>
   	<!--  
   	<div id="barcode" title="条码打印">
   		<div class="col-xs-12 col-sm-12 col-md-12">
	   		<div class="col-xs-6 col-sm-6 col-md-6" id="barcodeshow"></div>
   		</div>
   	</div>
   	-->
   	<div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
  </body>
  <script type="text/javascript">
        $(function(){ 
        	$("#dialog").dialog({
  				autoOpen:false,
  				height:500,
  				width:700,
  				modal:true,
  				position:[80,50],
  				buttons:{
  					"保存":function(){
  						$.post("<%=request.getContextPath()%>/productinfo/addproductinfoAjax.action",
  						$("#f3").serialize(),
					   		function(data){
					   			if(data.indexOf("error")!=-1){
					   				if(data.indexOf("error01")!=-1){
					   					alert("此Barcode已被使用！");
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
  						$(this).dialog("close");
  					},
  					"EXCEL导入":function(){
  						window.location.href = "<%=request.getContextPath() %>/productinfo/toimportpage.action";
  					}
  				},
  				
  			}); 
  			
        	$("#opener").click(function(e){
        		e.preventDefault();
        		if(isMobile()){
        			window.location.href = "<%=request.getContextPath() %>/productinfo/toaddproductinfo.action";
        		}else{
        			$("#dialog").dialog("open");
        			$("input[name='barcode']").focus();
        		}
        	});
        	
			$("button[data-name='baropener']").each(function(){
        		$(this).on("click",function(){
	        		var b = $(this).attr("data-value");
	        		$("#barcodeshow").barcode(b,"code128",{barWidth:2,barHeight:30}); 
	        		window.location.href = "<%=request.getContextPath() %>/productinfo/printbarcode.action?barcode="+b;    
        		});
        	});
        	
        	
        	$("#setallup").on("click",function(){
        		$.post("<%=request.getContextPath()%>/productinfo/setallup.action",
        			$("#f2").serialize(),
        			function(data){
        				alert(data);
        				window.location.reload(true);
        			});
        	});
        	$("#setalldown").on("click",function(){
        		$.post("<%=request.getContextPath()%>/productinfo/setalldown.action",
        			$("#f2").serialize(),
        			function(data){
        				alert(data);
        				window.location.reload(true);
        			});
        	});
        	$("#delete").click(function(){
        		var r = confirm("是否确认删除所选中的？");
        		if(r==true){
        		$.post("<%=request.getContextPath()%>/productinfo/delproductinfoAjax.action",
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
			  	};
        	});
        	$("#datatable").dataTable({ "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]]});
        });
        function createexcel(){
			var form = document.getElementById("f1");
			var a1 = form.action;
			form.action = "<%=request.getContextPath() %>/productinfo/createExcel.action";
			form.submit();
			form.action = a1;
		};    
    </script>
</html>
