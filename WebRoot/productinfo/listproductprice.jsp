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
    <title>--LIST PRODUCT-PRICE--</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  <SCRIPT type="text/javascript">
	function createexcel(){
		var form = document.getElementById("f1");
		var a1 = form.action;
		form.action = "<%=request.getContextPath() %>/productinfo/createpriceexcel.action";
		form.submit();
		form.action = a1;
	}
	
	
	 $(function() {
	 
   		$("#dialog").dialog({
			autoOpen:false,
			height:200,
			width:500,
			modal:true,
			position:[80,50],
			buttons:{
				"确   认":function(){
					$("form[name='f2']").submit();
				}
			}
  		}); 
  		
   		$("#msg").dialog({
			autoOpen:false,
			height:200,
			width:500,
			modal:true,
			position:[80,50],
  		}); 
  			
       	$("#opener").click(function(){
       		$("#dialog").dialog("open");
       	});
   		
   		$("#datatable").dataTable({ "lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]});
  	 });
  </SCRIPT>
  
  <body>
  	<div id="msg">
		<a style="color: red;">${msg}</a>
	</div>
 	<form id="f1" action="<%=request.getContextPath() %>/productinfo/listproductprice.action" class="pure-form" method="post">
  		 <fieldset>
  		  <label> 关键字: </label><input name="pdesc" type="text" value="${pdesc}" /> 
  		 <button type="button" onclick="this.form.submit()" class="pure-button pure-button-primary"> 搜   索  </button>
  		 <button type="button" onclick="createexcel();" class="pure-button pure-button-primary"> 生成EXCEL </button>
  		 <button type="button" id="opener" class="pure-button pure-button-primary"> 批量修改   </button>
  		</fieldset>
   	</form>
   	<table class="pure-table display" id="datatable" cellspacing="0" width="100%">
   	 <thead>
   	 <tr>
		<Td> 产品编码 </Td> 
		<Td> 产品名称 </Td>
		<Td> 零售价 </Td>
		<Td> 最低价 </Td>
		<td> 操 作  </td>
	</tr>
   	 </thead>
   	<tbody>
   		<s:iterator value="productinfos" id="p" >
   			<tr>  
   				<Td > ${p.barcode } </Td> 
   				<Td > ${p.pdesc } </Td>
   				<Td > ${p.sprice } </Td>
   				<Td > ${p.mprice } </Td>
   				<td > <a href="<%=request.getContextPath() %>/productinfo/toupdproductinfo.action?barcode=${p.barcode }"> 更    新 </a> </td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	<div id="dialog" title="批量修改">
   		<form action="<%=request.getContextPath() %>/productinfo/updatepricefromexcel.action" name="f2" method="post" class="pure-form pure-form-stacked" enctype="multipart/form-data">
   		<fieldset>
   		<input type="file" class="pure-u-1-2" name="file">
   		</fieldset>
   		</form>
	</div>
  </body>
</html>
