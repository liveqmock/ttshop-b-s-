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
    
    <title>--ADD PRODUCT-PRICE--</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  </head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	
  
  
  <body>
  <form action="<%=request.getContextPath() %>/ptype/addptype.action" method="post">
  <table>
  <thead>
  	<tr><td><h3> 产品定价 </h3></td></tr>
  </thead>
  <tbody>
  		<tr> <td> 产品编码： </td> <td> <input name="barcode" type="text" /> * </td></tr>
  		<tr> <td> 单价： </td> <td> <input name="price" type="text" /> * </td></tr>
  		<tr> <td> 最低价： </td> <td> <input name="lprice" type="text" /> * </td></tr>
		<tr> <td><input type="submit" value=" 创 建  "></td></tr>
	</tbody>
	</table>
	<s:token></s:token>
   	</form>
   	<p> ${me } </p>
   	
  </body>
</html>
