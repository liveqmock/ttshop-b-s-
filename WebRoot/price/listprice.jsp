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
    
    <title>--LIST PRODUCT-TYPES--</title>
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
 	<form action="<%=request.getContextPath() %>/ptype/listptype.action" method="post">
  	<table >
	<thead>
  		<tr class="td"><td> TYPE-NAME : </td> <td> <input name="keyword" type="text" value="${keyword }" /> </td>
  		<td colspan="5"> <input type="submit" value="SEARCH "> </td></tr>
   	</thead>
   	</table>
   	<table style="margin-top: 10px;">
   	<tbody>
   		<s:iterator value="ptypes" id="ptype" >
   			<tr>  
   				<Td class="td"> ${ptype.typeName } </Td> 
   				<Td class="td"> ${ptype.updateTime } </Td>
   				<Td class="td"> ${ptype.status } </Td>
   				<td class="td"> <a href="<%=request.getContextPath() %>/ptype/toupdptype.action?typename=${ptype.typeName }"> 修 改 </a> </td>
   			</tr>
   		</s:iterator>
   	
   	</tbody>
   	</table>
   	</form>
  </body>
</html>
