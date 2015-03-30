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
	<title>--盘货单--</title>
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
	<style type="text/css">
		td{
			vertical-align: middle!important;
		}
	 </style>
  </head>

  <body>
  <div class="panel panel-default">
   <div class="panel-heading">
    <form action="<%=request.getContextPath()%>/checking/listCheckingList.action" id="f1" class="form-inline">
    <h5 class="panel-title">
			<select name="warehouse.id" class="form-control">
	  	 		<option value="0"  <s:if test="#w.wname=='all'">selected</s:if>>所有仓库</option>
	  	 		<s:iterator value="warehouses" id="w">
	  	 		<option value="${w.id}"<s:if test="#w.wname==warehouse.wname">selected</s:if>>${w.wnickname }</option>
	  	 		</s:iterator>
	  	 	</select>
			<input class="form-control" type="date" name="date" value="${date }" id="date" placeholder="日期">
			<button type="submit" class="btn btn-primary">搜索</button>
			<button type="button" id="excel" class="btn btn-success">导出表格</button>
			<button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	  </h5>
	  </form>
   	 </div>
   	</div>
   	<div class="table-responsive">
	<table class="table" id="datatable">
   		<thead>
   			<tr class="warning">
   				<td width="10%">盘点人</td>
   				<td width="10%">仓库</td>
   				<td width="25%">产品</td>
   				<td width="10%">盘点前</td>
   				<td width="10%">盘点后 </td>
   				<td width="10%">差额</td>
   				<td width="15%">盘点时间</td>
   				<td width="10%">操作</td>
   			</tr>
   		</thead>
   		<tbody>
   		<s:iterator value="stockCheckingList" id="s">
			<tr>
				<td>${s.operator}</td>
				<td>${s.warehouse.wnickname}</td>
				<td>${s.productInfo.pdesc}</td>
				<td>${s.quantity_before}</td>
				<td>${s.quantity_after}</td>
				<td>
					<s:if test="#s.quantity_after-#s.quantity_before<0">
   	  						少 ${(s.quantity_after-s.quantity_before)*(-1) }
   	  				</s:if>
  					<s:elseif test="#s.quantity_after-#s.quantity_before==0">
  						0
  					</s:elseif>
  					<s:else>
  						多 ${s.quantity_after-s.quantity_before }
  					</s:else>
				</td>
				<td>
					${s.updatetime }
				</td>
				<td><a class="btn btn-sm btn-default" href="<%=request.getContextPath()%>/checking/cancelChecking.action?scid=${s.id}">作废</a></td>
			</tr>		
   		</s:iterator>
   		</tbody>
	</table>
   	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  <script>
  	$(function(){
  		$("#bdate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#edate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#datatable").dataTable({ "lengthMenu": [[-1], ["All"]]});
  		$("#excel").on("click",function(e){
  			var action1 = $("#f1").attr("action");
  			var action2 = "<%=request.getContextPath()%>/checking/createCheckingExcel.action";
  			$("#f1").attr("action",action2);
  			$("#f1").submit();
  			$("#f1").attr("action",action1);
  		});
  	});
  </script>
</html>
