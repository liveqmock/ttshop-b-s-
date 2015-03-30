<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-- 退货单列表 --</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
  	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  	<script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  <script type="text/javascript">
  	$(function(){
  		$("#bdate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#edate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#t1").dataTable({ "lengthMenu": [[15,50, 100,-1], [15, 50, 100,"All"]]});
  		
  	});
  </script>
  
  
  <body> 
  	<form action="<%=request.getContextPath()%>/tuihuo/listReturnSale.action" class="form-inline" method="post">
  	<fieldset>
  	<div class="panel panel-default">
  	 <div class="panel-heading">
  	 	<h5 class="panel-title">
	  	 <select name="warehouse" class="form-control">
	  	 		<option value="all"  <s:if test="#w.wname=='all'">selected</s:if>>所有仓库</option>
	  	 		<s:iterator value="warehouses" id="w">
	  	 		<option value="${w.wname }"<s:if test="#w.wname==warehouse">selected</s:if>>${w.wnickname }</option>
	  	 		</s:iterator>
	  	  </select>
	  	 <input type="date" class="form-control" name="begindate" id="bdate" value="${begindate}" placeholder="开始日期">
	  	 <input type="date" class="form-control" name="enddate" id="edate" value="${enddate}" placeholder="结束日期">
	     <button type="submit" class="btn btn-primary">搜索</button>
	     <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
     	</h5>
     </div>
    </div> 
    </fieldset>
    </form>
    <div class="table-responsive">
    <table id="t1" style="margin-top: 10px;width: 100%" class="table">
   		<thead>
    	<tr class="warning">
    		<td width="12%"> 退货单号 </td>
    		<td width="12%"> 销售单号 </td>
    		<td width="10%"> 操作员</td>
    		<td width="16%"> 客户 </td>
    		<td width="10%"> 仓库 </td>
    		<td width="8%"> 数量 </td>
    		<td width="7%%"> 总额 </td>
    		<td width="17%"> 退货日期 </td>
    		<td width="8%"> 状态 </td>
    	</tr>
    	</thead>
    	 <tbody>
    	<s:iterator value="returnSales" var="re">
    		<tr>
				<td> 
					<a href="<%=request.getContextPath()%>/tuihuo/findByReturnNo.action?returnsaleno=${re.returnsaleno}">${re.returnsaleno}</a> 
				</td>
				<td>
					<a href="<%=request.getContextPath()%>/sale/findlistByInvoice.action?keyword=${re.invoiceno}">${re.invoiceno}</a> 
				</td>
				<td>${re.operatorid} </td>		
				<td>${re.customerid} </td>
				<td>${re.warehouse} </td>
				<td>${re.totalQuantity} </td>		
				<td><fmt:formatNumber value="${re.totalAmount}" type="currency" pattern="＄.0#"/> </td>	
				<td>${re.updatetime} </td>
				<td> <s:if test="#re.status==0">作废</s:if>
					<s:else>生效</s:else>
				</td>
    		</tr>
    	</s:iterator>
    </tbody>
    </table>
    </div>
  </body>
</html>
