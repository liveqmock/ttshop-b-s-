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
    
    <title>--LIST CUSTOMERS--</title>
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
  <div class="container-fluid">
   <div class="row hidden-print">
 	<form action="<%=request.getContextPath() %>/customer/listcustomer.action" class="form-inline" id="f1" method="post">
 		<div class="input-group">
  			<div class="input-group-addon">关键字:</div>
  			<input name="keyword" class="form-control" type="text" value="${keyword }"/> 
  		</div>
		<button type="submit" class="btn btn-primary">搜索</button>
		<button type="button" onclick="createexcel()" class="btn btn-info">导出</button>
		<button type="button" id="opener" class="btn btn-success">添加客户</button>
		<button type="button" id="delete" class="btn btn-danger" style="visibility: hidden;">删除选中</button>
		<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
  	</form>
  	</div>
  	<form action="#" id="f2">
  	<div class="row table-responsive">
   	<table class="table display" id="datatable" width="100%">
   	<thead>
	<tr class="warning">
		<td> <input type="checkbox" id="all" onclick="checkall(this)"> </td>
		<Td> 客户编号  </Td> 
		<Td> 客户名称 </Td>
		<Td> 积分</Td>
		<Td> 折扣率 </Td>
		<Td> 电话号码 </Td>
		<Td> 地 址  </Td>
		<Td> 是否有效  </Td>
		<td> 操作  </td>
	</tr>	
   	</thead>
   	<tbody>
   		<s:iterator value="customers" id="customer" >
   			<tr>  
   				<Td> <input type="checkbox" id="ids" name="ids" value="${customer.id }" onclick="checkone(this);"> </Td> 
   				<Td> ${customer.customerId } </Td>
   				<Td> ${customer.customerName } </Td>
   				<Td> ${customer.credits } </Td>
   				<Td> ${customer.discountrate } </Td>
   				<Td> ${customer.customerTel } </Td>
   				<Td> ${customer.customerAddress } </Td>
   				<Td>
   				<s:if test="#customer.status==1">有效</s:if>
   				<s:if test="#customer.status==0">注销</s:if>
   				</Td>
   				<td> 
   					<a class="btn btn-default btn-sm clear-padding-height" href="<%=request.getContextPath() %>/customer/toupdcustomer.action?customerid=${customer.customerId }">修改</a> 
   				</td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   	</form>
   	</div>
   	<div id="dialog" title="增加客户">
	<s:form action="addcustomer" namespace="/customer" theme="simple" id="f3" cssClass="form" method="post">
	<fieldset>
		<div class="from-group">
  			<label>客户编号*(不能有中文)</label>
  			<input class="form-control" name="customerid" type="text" placeholder="客户编号" onchange="isChinese(this)"/>
  		</div>
		<div class="from-group">
  			<label>客户名称*(可以有中文)</label>
  			<input class="form-control" name="customername" type="text" placeholder="客户名称"/>
  		</div>
   		<div class="from-group">
  			<label>电话</label>
  			<input class="form-control" name="customertel" type="tel" placeholder="电 话" onchange="istelephone(this)"/>
  		</div>	
   		<div class="from-group">
  			<label>地址</label>
  			<input class="form-control" name="customeraddress" type="text" placeholder="地 址"/>
  		</div>
  		<div class="from-group">
  			<p class="text-warning">${me }</p>
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
				position:[50,50],
				buttons:{
					"保存":function(){
						$.post("<%=request.getContextPath()%>/customer/addCustomerAjax.action",
						$("#f3").serialize(),
			   		function(data){
			   			if(data.indexOf("error")!=-1){
			   				if(data.indexOf("error01")!=-1){
			   					alert("此客户ID已被用!");
			   					return;
			   				}else if(data.indexOf("error02")!=-1){
			   					alert("*栏为必要资料，请正确填写!");
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
						window.location.href = "<%=request.getContextPath() %>/customer/toimportpage.action";
					}
				},
				
			}); 
			
      	$("#opener").click(function(e){
      		e.preventDefault();
      		if(isMobile()){
      			window.location.href = "<%=request.getContextPath() %>/customer/addcustomer.jsp?returnurl=<%=request.getContextPath() %>/customer/listcustomer.action";
      		}else{
      			$("#dialog").dialog("open");
      		}
      	});
      	
      	
      	$("button[name='del']").each(function(i){
      		$(this).click(function(){
      			var r = confirm("是否确认删除此客户？");
      			if(r==true){
      		 	var cid = $(this).val();
      			$.post("<%=request.getContextPath()%>/customer/deleteCustomer.action",
						{customerid:cid},
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
      	
   	$("#delete").click(function(){
   		var r = confirm("是否确认删除所选中的？");
   		if(r==true){
   		$.post("<%=request.getContextPath()%>/customer/deleteCustomerAjax.action",
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
		form.action = "<%=request.getContextPath() %>/customer/createExcel.action";
		form.submit();
		form.action = a1;
	};
  </script>
</html>
