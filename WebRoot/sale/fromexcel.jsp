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
    
    <title>-- SALE INVOICE --</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<style type="text/css"></style>
  	<script type="text/javascript">
  	$(function(){
  		
  		${outstr}
  		
  		$("#customer").autocomplete({
  			minLength:2,
			source:customers,
			focus:function(event,ui){
				$("#customer").val(ui.item.value);
				return false;
			}
  		});
  		
  	});
  	
  	function formreset(){
  		var f = document.getElementById("f1");
  		f.submit();
  		f.reset();
  	}
  	
   </script>
  </head>
  
  <body> 
    <s:form action="createOutDetailfromExcel" namespace="/sale" id="f1" method="post" theme="simple" enctype="multipart/form-data" cssClass="pure-form pure-form-stacked">
    <fieldset>
    	<legend> Excel出货  <a style="color: red;">${me }</a> </legend>
	    <div class="pure-u-1">
			<select class="pure-input-1-4" name="paidtype">
				<option value="0" selected="selected">现金</option>
				<option value="1">刷卡</option>
				<option value="2">签单</option>
				<option value="3">支票</option>
				<option value="4">其它</option>
			</select>
			<a style="color: green;display:block;vertical-align: middle;padding-left: 2em;" class="pure-u-1-4">&nbsp;</a>
		</div>
		<div class="pure-u-1">
			<select class="pure-input-1-4" name="currency">
				<option value="HKD" selected="selected">HKD</option>
				<option value="MOP">MOP</option>
				<option value="RMB">RMB</option>
			</select>
			<a style="color: green;display:block;vertical-align: middle;padding-left: 2em;" class="pure-u-1-4">&nbsp;</a>
		</div>
    	 <div class="pure-u-1">
	     	<input class="pure-input-1-4" placeholder="Customer" id="customer" name="customer" type="text" />
	     	<a style="color: green;display:block;vertical-align: middle;padding-left: 2em;" class="pure-u-1-4">&nbsp;</a>
	     </div>
	     <div class="pure-u-1">
	     	<input class="pure-input-1-4"  name="file" type="file"/>
	     	<a style="color: green;display:block;vertical-align: middle;padding-left: 2em;" class="pure-u-1-4">&nbsp;</a>
	     </div>
	     <div class="pure-u-1">
		 	<button type="button" onclick="formreset()" class="pure-button pure-button-primary"> COMFIRM </button>
		 </div>
	 </fieldset>
    </s:form>
    
  </body>
</html>
