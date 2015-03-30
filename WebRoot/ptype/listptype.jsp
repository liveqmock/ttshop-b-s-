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
    
    <title>--LIST PRODUCT-TYPES--</title>
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
  </head>
  <body>
  <div class="container-fluid notprint">
   <div class="row">
 	<form action="<%=request.getContextPath() %>/ptype/listptype.action" id="f1" class="form-inline" method="post">
 		<div class="form-group">
	 		<div class="input-group">
	  			<div class="input-group-addon">关键字:</div>
	  			<input name="keyword" class="form-control" type="text" value="${keyword}"/> 
	  		</div>
  		</div>
		<button type="submit" class="btn btn-primary">搜索</button>
		<button type="button" onclick="createexcel()" class="btn btn-info">导出</button>
		<button type="button" id="opener" class="btn btn-success">添加类型</button>
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
   		<td> 产品类型  </td>
   		<td> 库存类型  </td>
   		<td> 操作 </td>
   		</tr>
   	</thead>
   	<tbody>
   		<s:iterator value="ptypes" id="ptype" >
   			<tr> 
   				<Td> <input type="checkbox" id="ids" name="ids" value="${ptype.id }" onclick="checkone(this);"> </Td> 
   				<Td class="td"> ${ptype.typeName } </Td> 
   				<Td class="td"> 
   					<s:if test="#ptype.itype==1"> 记库存 </s:if>
	   				<s:if test="#ptype.itype==0"> 不记库存 </s:if>
	   				<s:if test="#ptype.itype==2"> 库存有IMEI </s:if> 
	   			</Td> 
   				<td class="td"> 
   				<a class="btn btn-default btn-sm clear-padding-height" href="<%=request.getContextPath() %>/ptype/toupdptype.action?typeid=${ptype.id }"> 修 改 </a> 
   				</td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   	</form>
   	</div>
   	<DIV ID="dialog" title="添加产品类型">
   	<s:form action="addptype" id="f3" namespace="/ptype" theme="simple" cssClass="form" method="post">
	<fieldset>
		<div class="form-group">
  		  <label>产品类型*</label> 
  		  <input class="form-control" name="typename" type="text" placeholder="类型名称" /> 
  		</div>
  		<div class="form-group">  
  		  <label>库存类型*</label>
  		  	<select name="itype" class="form-control">
	  			<option value="0">-不记库存产品-</option>
	  			<option value="1">-记库存产品不记IMEI-</option>
	  			<option value="2">-记库存产品记IMEI-</option>
  			</select>
  		</div>
  		<div class="form-group"> 
  		<div class="well">
  			<p>我该如何选择库存类型?</p>
  			<p>该类型设置为<span style="color:red;">"不记库存产品"</span>,该产品在"入库"及"销售"系统操作时将不计算库存增减,实物产品不选这项,例如"某种形式的服务",服务我们无法计算库存,所以应选择这个</p>
  			<p>该类型设置为<span style="color:red;">"记库存产品不记IMEI"</span>,该产品在"入库"及"销售"系统操作时会连同计算库存增减,适用极大部分情况</p>
  			<p>该类型设置为<span style="color:red;">"记库存产品记IMEI"</span>,该产品在"入库"及"销售"系统操作时会连同计算库存增减,并且该产品一定要求有 imei, 例如电子产品都由 sn/ 或者 imei, 系统会额外记录该 imei 的操作记录</p>
  		</div>
  		</div>
	</fieldset>
   	</s:form>
   	</DIV>
   	<div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
  </body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>  
  <script type="text/javascript">
        $(function(){  
        	$("#dialog").dialog({
  				autoOpen:false,
  				height:500,
  				width:500,
  				modal:true,
  				position:[50,20],
  				buttons:{
  					"保存":function(){
  						$.post("<%=request.getContextPath()%>/ptype/addptypeAjax.action",
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
  					},
  					"EXCEL导入":function(){
  						window.location.href="<%=request.getContextPath()%>/ptype/toimportpage.action";
  					}
  				},
  				
  			}); 
  			
        	$("#opener").click(function(e){
        		e.preventDefault();
        		if(isMobile()){
        			window.location.href = "<%=request.getContextPath() %>/ptype/addptype.jsp?returnurl=<%=request.getContextPath() %>/ptype/listptype.action";
        		}else{
        			$("#dialog").dialog("open");
        		}
        	});
        	
        	$("button[name='del']").each(function(i){
        		$(this).click(function(){
        			var r = confirm("是否确认删除？");
        			if(r==true){
        		 	var tid = $(this).val();
        			$.post("<%=request.getContextPath()%>/ptype/deleteptypeAjax.action",
  						{typeid:tid},
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
        		$.post("<%=request.getContextPath()%>/ptype/deleteptypeAjax.action",
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
			form.action = "<%=request.getContextPath() %>/ptype/createExcel.action";
			form.submit();
			form.action = a1;
		};    
    </script>
</html>
