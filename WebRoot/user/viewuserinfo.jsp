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
    <title>用户信息概览</title>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <div class="panel panel-default">
		<div class="panel-heading">
			<h5 class="panel-title">用户信息概览
				<button onclick="window.location.href='${returnurl}'" class="btn btn-primary pull-right clear-padding-height">返回</button>
			</h5>
		</div>    
    	<div class="panel-body">
    		<div class="table-responsive">
    		<table class="table" width="100%">
    			<tr>
    				<td class="warning" width="15%">用户 ID </td>
    				<td width="15%">${request.user.userid }</td>
    				<td class="warning" width="15%"> 用户名 </td>
    				<td width="15%">${request.user.username } </td>
    				<td rowspan="3" class="active" width="40%"> 
    					<img src="" width="90px" height="90px">
    				</td>
    			</tr>
    			<tr>
    				<td class="warning" width="15%"> EMAIL </td>
    				<td width="15%">${request.user.mail }</td>
    				<td class="warning" width="15%"> 电话号码 </td>
    				<td width="15%">${request.user.telephone } </td>
    			</tr>
    			<tr>
    				<td class="warning" width="15%"> 职位 </td>
    				<td width="15%">
    					<s:if test="#request.user.role==0"> 系统管理员 </s:if>
    					<s:elseif test="#request.user.role==1">销售员</s:elseif>
    					<s:elseif test="#request.user.role==2">仓管员</s:elseif>
    					<s:elseif test="#request.user.role==3">经理</s:elseif>
    				</td>
    				<td class="warning" width="15%"> &nbsp; </td>
    				<td width="15%">&nbsp;</td>
    			</tr>
    		</table>
    		</div>
    		<s:if test="saleRecords!=null && saleRecords.size>0">
    			<div class="table-responsive">
    			<table class="table">
    			   <thead>
    				<tr>
    					<td>单号</td>
    					<td>数量</td>
    					<td>总额</td>
    					<td>出货仓库</td>
    					<td>时间</td>
    				</tr>
    				</thead>
    				<tbody id="state">
    				<s:iterator value="saleRecords" id="s">
    					<tr>
    						<td>${s.invoiceno }</td>
    						<td>${s.totalquantity }</td>
    						<td>${s.totalamount }</td>
    						<td>${s.warehouse }</td>
    						<td>${s.updatetime } </td>
    					</tr>
    				</s:iterator>
    				</tbody>
    				<tfoot>
    					<tr>
    						<td><span class="text-danger">Total</span></td>
    						<td id="tq" class="text-success"></td>
    						<td id="ta" class="text-success"></td>
    						<td></td>
    						<td></td>
    					</tr>
    				</tfoot>
    			</table>
    			</div>
    		</s:if>
    		<s:else>
    			<p class="text-danger"> 此用户没有任何销售记录 </p>
    		</s:else>
    	</div>
    </div>
  </body>
  <script type="text/javascript">
  	$(function(){
  		var totalquantity = 0;
  		var totalamount = 0;
  		$("#state").find("tr").each(function(i){
  			var oneq = parseInt($(this).find("td").eq(1).text());
  			var onea = parseFloat($(this).find("td").eq(2).text());
  			totalquantity+=oneq;
  			totalamount+=onea;
  		});
  		$("#tq").text(totalquantity);
  		$("#ta").text(totalamount);
  		
  		$("#datatable").dataTable({ "lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]});
  	});
  </script>
</html>
