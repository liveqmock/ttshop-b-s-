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
    <title>--LIST COMPANIES--</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  </head>
  <body>
 	<form action="<%=request.getContextPath() %>/company/listcompany.action" method="post">
  	<div class="pure-u-1">
  		<span> 
  		  <label> COMPANYID / 名称  / 电话  / 地址： </label>
  		  <input class="pure-input-1-2" name="keyword" type="text" value="${keyword }" />
  		  <input class="pure-button pure-button-primary" type="submit" value="SEARCH">
  		</span>
	</div>
   	<table>
   	<tbody>
   		<s:iterator value="companyInfos" id="companyInfo" >
   			<tr>  
   				<Td class="td"> ${companyInfo.companyId } </Td> 
   				<Td class="td"> ${companyInfo.companyName } </Td>
   				<Td class="td"> ${companyInfo.companyTel } </Td>
   				<Td class="td"> ${companyInfo.companyAddress } </Td>
   				<Td class="td"> ${companyInfo.companyManager} </Td>
   				<Td class="td"> ${companyInfo.updateTime } </Td>
   				<Td class="td"> ${companyInfo.status } </Td>
   				<td class="td"> <a href="<%=request.getContextPath() %>/company/toupdcompany.action?companyid=${companyInfo.companyId }">修 改 </a> </td>
   			</tr>
   		</s:iterator>
   	
   	</tbody>
   	</table>
   	</form>
  </body>
</html>
