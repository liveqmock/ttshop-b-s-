<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-UPDATE PRODUCT-TYPE--</title>
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
  <form action="<%=request.getContextPath() %>/ptype/updptype.action" method="post">
  <table>
  <thead>
  	<tr><td><h3> 更新类型 </h3></td></tr>
  </thead>
  <tbody>
  		<tr> <td> 产品类型： </td> <td> <input name="typename" type="text" readonly="readonly" value="${ptype.typeName }"/> * </td></tr>
		<tr> <td><input type="submit" value=" 更  新  "></td></tr>
	</tbody>
	</table>
   	</form>
   	<p> ${me } </p>
   	
  </body>
</html>
