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
    
    <title>-- 销售产品明细 --</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
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
    <form action="<%=request.getContextPath()%>/report/saleImeiReport.action" method="post"  class="pure-form">
    <fieldset>
     <input class="pure-u-1-8" id="keyword" name="keyword" value="${keyword }" placeholder="单号/单抬头" type="text" />
     <input class="pure-u-1-8" id="bdate" name="begindate" value="${begindate }" placeholder="开始时间" type="date" />
     <input class="pure-u-1-8" id="edate" name="enddate" value="${enddate }" placeholder="结束时间" type="date" />
     <button type="submit" class="pure-button pure-button-primary">COMFIRM</button>
     </fieldset>
    </form>
	<br>
	<div class="table-responsive">
    <table class="pure-table pure-table-bordered" width="100%">
    <thead>
    <tr>
	<td class="td"> 单号 </td>
	<td class="td"> 仓库 </td>
	<td class="td"> 客户</td>
	<td class="td"> 编号 </td>
	<td class="td"> 名称 </td>
	<td class="td"> IMEIS </td>
	<td class="td"> 时间 </td>
	<td class="td"> 状态 </td>
	</tr>
	</thead>
	<tbody>
    <s:iterator value="list" id="l">
    <tr>
	<td class="td">
	<a href="<%=request.getContextPath()%>/sale/findlistByInvoice.action?keyword=${l[0]}" target="main" >${l[0] }</a>
	</td>
	<td class="td">${l[6] }</td>    
	<td class="td">${l[7] }</td>    
	<td class="td">${l[1] }</td>    
	<td class="td">${l[2] }</td>    
	<td class="td">${l[3] }</td>    
	<td class="td">${l[4] }</td>  
	<td class="td">
	<s:if test="#l[5]==0"> 已作废  </s:if>
	<s:if test="#l[5]==1"> 生 效    </s:if>
	</td>
    </tr>
    </s:iterator>
    </tbody>
    </table>
    </div>
  </body>
</html>
