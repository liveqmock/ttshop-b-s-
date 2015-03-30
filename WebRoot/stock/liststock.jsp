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
    
    <title>-- 产看库存 --</title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
	<style type="text/css">
		table{
			table-layout: fixed;
		}
		table td{
			word-break: break-all; 
			word-wrap: break-word;
		}
	</style>
  </head>
  <script>
  $(function(){
  	
  	$("#datatable").dataTable({ "lengthMenu": [[15,50, 100,-1], [15, 50, 100,"All"]]});
  	
  });
  function createexcel(){
	var form = document.getElementById("f1");
	var a1 = form.action;
	form.action = "<%=request.getContextPath() %>/stock/listStockExcel.action";
	form.submit();
	form.action = a1;
	};  
  </script>
  
  <body>
  	<div class="panel panel-default">
  	 <div class="panel-heading">
  	  <form action="<%=request.getContextPath()%>/stock/listStock.action" id="f1" class="form-inline" method="post">
  	  <h3 class="panel-title">
	  	 <select name="warehouse" id="warehouse" class="form-control">
	  	 		<option value="all" >所有仓库</option>
	  	 	<s:iterator value="warehouses" id="w">
	  	 		<option value="${w.wname}" <s:if test="#w.wname==warehouse">selected</s:if>>${w.wnickname}</option>
	  	 	</s:iterator>
	  	 </select>
	     <button type="submit" class="btn btn-primary">搜索</button>
	     <button type="button" class="btn btn-success" onclick="createexcel();">excel表格</button>
	     <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
  	  </h3>
      </form>
  	 </div>
  	</div> 
  	<div class="table-responsive">
    <table id="datatable" class="table" style="margin-top: 10px" width="100%">
   		<thead>
    	<tr class="warning">
    		<td width="10%"> 条码 </td>
    		<td width="20%"> 产品名称 </td>
    		<td width="10%"> 存仓位置 </td>
    		<td width="10%"> 数量 </td>
    		<td width="30%" style="display: none;"> IMEI </td>
    		<td width="20%"> 更新日期 </td>
    	</tr>
    	</thead>
    	 <tbody>
    	<s:iterator value="objects" id="obj">
    		<tr>
				<td >${obj[1]} </td>
				<td >${obj[2]} </td>  	
				<td >
				<s:if test="warehouse=='all'">全部合计</s:if>
				<s:else>${obj[3]} </s:else>
				</td>    		
				<td >${obj[4]} </td>    		
				<td align="left" style="display: none;">${obj[7]} </td>    		
				<td >${obj[5]} </td>    		
    		</tr>
    	</s:iterator>
    </tbody>
    </table>
    </div>
  </body>
</html>
