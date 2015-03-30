<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>--UPDATE COMPANYINFO--</title>
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
  <form action="<%=request.getContextPath() %>/company/updcompany.action" method="post">
  <table>
  <thead>
  	<tr><td><h3> 修改公司信息 </h3></td></tr>
  </thead>
  <tbody>
  		<tr> <td>公司编号： </td> <td> <input  class="pure-input-1-2" name="companyid" type="text" readonly="readonly" value="${companyInfo.companyId }"  /> * </td></tr>
		<tr> <td>公司名称： </td> <td> <input  class="pure-input-1-2" name="companyname" type="text" value="${companyInfo.companyName }"  /> * </td></tr>
   		<tr> <td>电 话:  </td> <td> <input  class="pure-input-1-2" name="companytel" type="text" value="${companyInfo.companyTel }"  />  </td></tr>
   		<tr> <td>地 址:</td> <td> <input  class="pure-input-1-2" name="companyaddress" type="text" value="${companyInfo.companyAddress }"  />  </td></tr>
   		<tr> <td>公司LOGO:</td> <td> <input  class="pure-input-1-2" name="companylogo" type="file" /> </td></tr>
   		<tr> <td>负责人:</td> <td> <input  class="pure-input-1-2" name="companymanager" type="text" value="${companyInfo.companyManager }"  /> </td></tr>
		<tr> <td><input type="submit" value="修 改"></td></tr>
	</tbody>
	</table>
   	</form>
   	
  </body>
</html>
