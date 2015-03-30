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
    
    <title>--初始化库存--</title>
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
    <s:form action="initStock" namespace="/stock" id="f1" method="post" theme="simple" enctype="multipart/form-data" cssClass="pure-form pure-form-stacked">
    
    	<legend>初始化库存</legend>
    	   <fieldset>
			<select class="pure-input-1-4"  name="warehouse">
			 	<s:iterator value="warehouses" var="w">
					<option value="${w.wname }" selected="selected">${w.wname }</option>
				</s:iterator>
			</select>
			</fieldset>
			<fieldset>
	     		<input class="pure-input-1-4"  name="file" type="file"/>
		   </fieldset>
		   <fieldset>
		 		<button type="button" onclick="formreset()" class="pure-button pure-button-primary"> 确认</button>
		 	</fieldset>
	 <a style="color: red;">${me }</a>
    </s:form>
    
  </body>
</html>
