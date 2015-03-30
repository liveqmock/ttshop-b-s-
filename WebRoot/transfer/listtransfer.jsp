<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
	<title>--调货单--</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css"href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  <script>
  	$(function(){
  		$("#bdate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#edate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#datatable").dataTable({ "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]]});
  	});
  </script>
  <body>
  <div class="panel panel-default">
   <div class="panel-heading">
    <h5 class="panel-title">
	   	<s:form action="listTransfer" namespace="/transfer" theme="simple" cssClass="form-inline">
			<select name="warehouse" class="form-control">
	  	 		<option value="all"  <s:if test="#w.wname=='all'">selected</s:if>>所有仓库</option>
	  	 		<s:iterator value="warehouses" id="w">
	  	 		<option value="${w.wname }"<s:if test="#w.wname==warehouse">selected</s:if>>${w.wnickname }</option>
	  	 		</s:iterator>
	  	 	</select>
			<input class="form-control" type="date" name="bdate" value="${bdate }" id="bdate" placeholder="开始时间">
			<input class="form-control" type="date" name="edate" value="${edate}" id="edate" placeholder="结尾时间">
			<button type="submit" class="btn btn-primary">搜索</button>
			<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	   	</s:form>
	   	</h5>
   	 </div>
   	</div>
   	<div class="table-responsive">
	<table class="table" id="datatable">
   		<thead>
   			<tr class="warning">
   				<td width="15%">调货单号</td>
   				<td width="20%">发货仓库</td>
   				<td width="20%">收货仓库</td>
   				<td width="15%">总数量</td>
   				<td width="20%">时间</td>
   				<td width="10%">状态</td>
   			</tr>
   		</thead>
   		<tbody>
   		<s:iterator value="transfers" id="transfer">
			<tr>
				<td>
					<a href="<%=request.getContextPath()%>/transfer/transferDetial.action?id=${transfer.id}">${transfer.transferno}</a>
				</td>
				<td>${transfer.warehouse}</td>
				<td>${transfer.receiverid}</td>
				<td>${transfer.totalquantity}</td>
				<td>${transfer.updatetime}</td>
				<td>
					<s:if test="#transfer.status==0"><a class="return-msg">作废</a></s:if>
					<s:else>生效</s:else>
				</td>
			</tr>	   		
   		</s:iterator>
   		</tbody>
	</table>
   	</div>
  </body>
</html>
