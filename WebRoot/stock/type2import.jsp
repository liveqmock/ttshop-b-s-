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
    <title>--设备入货--</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  </head>
  <SCRIPT type="text/javascript">
  
	$(function(){
		
		${resJson}
		
	  	$("#barcode").autocomplete({
  			minLength:2,
			source:infos,
			focus:function(event,ui){
				$("#barcode").val(ui.item.value);
				return false;
			}
  		});

  	 });
  	 
 	function check(id){
  		var obj = document.getElementById(id);
  		var sp = obj.value;
		if(!isFinite(sp)){
			alert("PLEASE INSERT THE RIGHT QUANTITY!");
			obj.value = 0;
		}
  	};
  </SCRIPT>
  
  <body> 
  	<s:form  action="type2import" namespace="/inbound" method="post" enctype="multipart/form-data"  theme="simple" cssClass="pure-form pure-form-stacked">
		<fieldset>	
		<legend> 货物入库  </legend>
		</fieldset>
		<fieldset>
		<label>SUPPLIER*</label>
			<SELECT class="pure-u-1-4" name="supplierid" id="supplierid">
			<s:iterator value="suppliers" id="s" >
				<option value="${s.supplierId}">${s.supplierName }</option>		
			</s:iterator>
			</SELECT>  
		</fieldset>
		<fieldset>
		<label>WAREHOUSE*</label>
			<SELECT class="pure-u-1-4" name="warehouse" id="warehouse">
			<s:iterator value="warehouses" id="w" >
				<option value="${w.wname}">${w.wname }</option>
			</s:iterator>
			</SELECT> 
		</fieldset>
		<fieldset>
		<label>BARCODE*</label>
		<input type="text" class="pure-u-1-4" name="barcode" id="barcode" /> 
		<div id="show"></div>
		</fieldset>
		<fieldset>
		<label>PRICE</label>
		<input type="text" class="pure-u-1-4" id="price" name="price" value="0" onblur="check('price');"/>
		</fieldset>
		<fieldset>
		<label>IMEIS</label>
		<input class="pure-u-1-4" name="file" type="file" /> 
		</fieldset>
		<fieldset>
		<button type="button" onclick="this.form.submit()" class="pure-button pure-button-primary"> COMFIRM </button>
		</fieldset>
  	<s:token></s:token>
  	</s:form>
  	<a style="color: red;"> ${me} </a>
  </body>
</html>
