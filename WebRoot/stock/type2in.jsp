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
    
    <title>--TYPE2 INBOUND--</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>  	
  </head>
  <SCRIPT type="text/javascript">
	$(function(){
	    ${resJson}
	    
	    /**
		$("input:text").keypress(function(event){  
            if(event.keyCode== 13){
               $(this).next().focus();
             }
	     });**/
	     
	  	$("#barcode").autocomplete({
  			minLength:2,
			source:infos,
			focus:function(event,ui){
				$("#barcode").val(ui.item.value);
				$("#description").val(ui.item.label);
				return false;
			}
  		});
	  	 
		 $("#quan").change(function(){
		  	 	var str = "";
		  	 	var q = document.getElementById("quan").value;
		  	 	for(var i=0; i<q; i++){
		  	 		str = str + "<input type=\"text\" name=\"imeis\" onkeypress='enterTotab(event,this);' class=\"pure-u-1-4\" />";
		  	 	};
		  	 	$("#imeis").html(str);
		  	 });
	  });
  	 
  </SCRIPT>
  
  <body> 
  	<s:form namespace="/inbound" action="type2in" theme="simple" method="post"  id="f1" cssClass="pure-form pure-form-stacked" onsubmit="check('price');">
		<fieldset>	
		<legend> 设备入库   <a style="color: red;"> ${me}</a></legend>
		</fieldset>
		<div class="pure-u-1-4 left">
		<fieldset>
		<label>SUPPLIER*</label>
			<SELECT class="pure-u-3-4" name="supplierid" id="supplierid">
			<s:iterator value="suppliers" id="s" >
				<option value="${s.supplierId}">${s.supplierName }</option>		
			</s:iterator>
			</SELECT>
		</fieldset>
		<fieldset>
		<label>WAREHOUSE*</label>
			<SELECT class="pure-u-3-4" name="warehouse" id="warehouse">
			<s:iterator value="warehouses" id="w" >
				<option value="${w.wname}">${w.wname }</option>
			</s:iterator>
			</SELECT>
		</fieldset>
		<fieldset>
		<label>DESCRIPTION</label>
		<input type="text" class="pure-u-3-4" name="description" id="description" readonly/> 
		</fieldset>
		<fieldset>
		<label>BARCODE*</label>
		<input type="text" class="pure-u-3-4" name="barcode" id="barcode" /> 
		</fieldset>
		<fieldset>
		<label>PRICE</label>
		<input type="text" class="pure-u-3-4" id="price" name="price" value="0" onblur="check(this);"/>
		</fieldset>
		<fieldset>
		<label>QUANTITY*</label>
		<input type="text" class="pure-u-3-4" id="quan" name="quantity" value="1" onblur="check(this);"/>
		</fieldset>
		<fieldset>
		<button type="button" onclick="checkform('f1','barcode')" class="pure-button pure-button-primary"> COMFIRM </button>
		<button type="button" onclick="location.href='<%=request.getContextPath() %>/inbound/totype2import.action'" class="pure-button pure-button-primary"> Excel导入  </button>
		</fieldset>
		</div>
		<div class="pure-u-3-4 left">
		<fieldset>
		<label>IMEIS</label>
		<div id="imeis">
		<s:iterator begin="1" end="1" var="1">
		<input type="text" name="imeis" onkeypress="enterTotab(event,this);" class="pure-u-1-4" />
		</s:iterator>
		</div>
		</fieldset>
		</div>
  	<s:token></s:token>
  	</s:form>
  </body>
</html>
