<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-- 选择入货类别 --</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  </head>
  
  <body> 
  	<form action="<%=request.getContextPath()%>/inbound/toinboundpage.action" class="form" method="post">
 	<fieldset>
 	<legend> 产品入货   <a style="color: red;"> ${me }</a></legend>
 	<label>选择入货类别 </label>
    <select name="itype" class="col-xs-12">
    	<option value="1">- 配件入货 -</option>
    	<option value="2">- 批发入货 -</option>
    	<option value="3">- 门市入货  -</option>
    </select>
    <button type="submit" class="btn btn-primary">下一步</button>
	</fieldset>
    </form>
  
  </body>
</html>
