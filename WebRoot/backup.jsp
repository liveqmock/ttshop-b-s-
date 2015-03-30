<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  </head>
  
  <body>
  	<div>
  		<ul>
  			<li><a href="<%=request.getContextPath()%>/sale/backupSalerecord.action">销售数据备份</a></li>
  			<li><a href="<%=request.getContextPath()%>/sale/recover.action">销售数据恢复</a></li>
  		</ul>
  	</div>
  </body>
</html>
