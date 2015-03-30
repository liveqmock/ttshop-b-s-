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
    <title> 营业报表  </title>
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
  	<style type="text/css">
  	*{
  	  font-size: 12px;
  	}
  	</style>
  	<script type="text/javascript">
	  	$(function(){
			  $("#bdate").datepicker({dateFormat:"yy-mm-dd"});		
			  $("#edate").datepicker({dateFormat:"yy-mm-dd"});		
	  	});
	   </script>
  </head>
  
  <body>
	    <div class="panel panel-default">
	    	<div class="panel-heading">
	    		<form action="<%=request.getContextPath()%>/report/profitReport.action" method="post"  class="form-inline">
				     <input class="form-control" id="keyword" name="keyword" value="${keyword }" type="hidden" readonly="readonly"/>
				     <input class="form-control" id="bdate" name="begindate" value="${begindate }" placeholder="开始时间" type="date" />
				     <input class="form-control" id="edate" name="enddate" value="${enddate }" placeholder="结束时间" type="date" />
				     <button type="submit" class="btn btn-primary">确认</button>
				     <button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary pull-right">返回</button>
				</form>
	    	</div>
	    	<div class="panel-body">
	    		<div class="panel panel-default">
	    		<div class="panel-heading">
	    			<h3 class="panel-title">利润报表</h3>
	    		</div>
	    		<div class="table-responsive">
		        <table class="table table-bordered">
		        <thead>
				    <tr class="warning">
					<td > 货币 </td>
					<td > 支付方式  </td>
					<td > 总数 </td>
					<td > 总额 </td>
					</tr>
				</thead>
			    <tbody>
			    <s:iterator value="objects" id="obj">
				    <tr>
						<td > ${obj[2]} </td>
						<td >
						<s:if test="#obj[3]==0">现金</s:if>
						<s:if test="#obj[3]==1">刷卡</s:if>
						<s:if test="#obj[3]==2">签单</s:if>
						<s:if test="#obj[3]==3">支票</s:if>
						<s:if test="#obj[3]==4">其它</s:if>
						</td>
						<td > ${obj[0]} </td>
						<td > <fmt:formatNumber value="${obj[1]}" type="currency" pattern="＄.0#"/></td>
					</tr>
					</s:iterator>
					<tr>
						<td  colspan="2"> 收入合计  </td>
						<td> ${totalquantitty } </td>
						<td> <fmt:formatNumber value="${totalamount }" type="currency" pattern="＄.0#"/> </td>
					</tr>
					<tr>
						<td  colspan="2"> 退货合计  </td>
						<td> ${totalreturnquantitty } </td>
						<td> <fmt:formatNumber value="${totalreturnamount }" type="currency" pattern="＄.0#"/> </td>
					</tr>
					<tr>
						<td  colspan="2"> 销售成本  </td>
						<td>  </td>
						<td> <fmt:formatNumber value="${totalcost }" type="currency" pattern="＄.0#"/> </td>
					</tr>
					<tr>
						<td  colspan="2"> 销售利润 </td>
						<td>  </td>
						<td> <fmt:formatNumber value="${totalamount-totalreturnamount-totalcost }" type="currency" pattern="＄.0#"/> </td>
					</tr>
				</tbody>
		      </table>
		      </div>
		   </div>
  		</div>
   </div>
  </body>
</html>
