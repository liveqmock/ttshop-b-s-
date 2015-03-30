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
    
    <title>-- FIND BY IMEI --</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/data.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  </head>
  
  <body> 
    <form action="<%=request.getContextPath()%>/sale/findByImei.action" method="post" class="pure-form">
    <fieldset>
     <input class="pure-u-1-4" id="imei" name="imei" placeholder="IMEI/SERIALNUMBER" type="text" />
     <button type="submit" class="pure-button pure-button-primary">COMFIRM</button>
     </fieldset>
    </form>
	<br>
	<div class="table-responsive">
    <table class="pure-u-1 pure-table pure-table-bordered">
    <thead>
    <tr>
	<td class="td"> 单号  </td>
	<td class="td"> BARCODE </td>
	<td class="td"> DESCRIPTION </td>
	<td class="td"> IMEI </td>
	<td class="td"> 时间  </td>
	<td class="td"> 状态  </td>
	</tr>
	</thead>
	<tbody>
    <s:iterator value="recordLists" id="sl">
    <tr>
	<td class="td">
	<a href="<%=request.getContextPath()%>/sale/findlistByInvoice.action?keyword=${sl.invoiceno}" target="main" >${sl.invoiceno }</a>
	</td>
	<td class="td">${sl.barcode }</td>    
	<td class="td">${sl.pdesc }</td>    
	<td class="td">${sl.remark }</td>    
	<td class="td">${sl.updatetime }</td>    
	<td class="td">
	<s:if test="#sl.status==0"> 已作废  </s:if>
	<s:if test="#sl.status==1"> 有效</s:if>
	</td>
    </tr>
    </s:iterator>
    </tbody>
    </table>
    </div>
  </body>
</html>
