<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-- 销售明细表 --</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  
  <body>
  	<div class="panel panel-default">
  	 <div class="panel-heading">
  	  <div class="panel-title">
  	  	<form action="<%=request.getContextPath()%>/sale/findSaleRecordListByDate.action" method="post" id="f1" class="form-inline noprint">
     	 <input type="hidden" id="currentPage" name="page.currentPage" value="1">
     	 <select name="warehouse" class="form-control">
  	 		<option value="all"  <s:if test="#w.wname=='all'">selected</s:if>>所有仓库</option>
  	 	 <s:iterator value="warehouses" id="w">
  	 		<option value="${w.wname }"<s:if test="#w.wname==warehouse">selected</s:if>>${w.wnickname }</option>
  	 	 </s:iterator>
  	 	 </select>
	     <input id="keyword" name="keyword" value="${keyword }" placeholder="单号/客户" type="hidden" readonly="readonly"/>
	     <input class="form-control" id="bdate" name="begindate" value="${begindate }" placeholder="开始时间" type="date" />
	     <input class="form-control" id="edate" name="enddate" value="${enddate }" placeholder="结束时间" type="date" />
     	 <button type="submit" class="btn btn-primary">搜索</button>
     	 <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
    	</form>
  	  </div>
  	 </div>
  	</div>
    <div class="table-responsive">
    <table class="table noprint" id="t1" width="100%">
    <thead>
    <tr class="warning">
	<td> 单号 </td>
	<td> 行 </td>
	<td> 产品编码 </td>
	<td> 产品名称 </td>
	<td> 数量 </td>
	<td> 单价 </td>
	<td> 总额 </td>
	<td> 时间 </td>
	<td> 状态 </td>
	</tr>
	</thead>
	<tbody>
    <s:iterator value="recordLists" id="rl">
    <tr>
	<td >
	<a href="<%=request.getContextPath()%>/sale/findlistByInvoice.action?keyword=${rl.invoiceno}" target="main" >${rl.invoiceno }</a>
	</td>
	<td >${rl.invoiceline }</td>    
	<td >${rl.barcode }</td>    
	<td >  <%-- <a name="customer" href="<%=request.getContextPath()%>/sale/findRecordByInvoice.action?"> ${sr.customerid } </a> --%>
		${rl.pdesc }
	</td>    
	<td >${rl.quantity }</td>    
	<td > ${rl.price }</td>    
	<td > <fmt:formatNumber value="${rl.amount }" type="currency" pattern="＄.0#"/></td>    
	<td >${rl.updatetime } </td>
	<td >
	<s:if test="#rl.status==0"> 作废
	 </s:if>
	<s:if test="#rl.status==1"> 有效   
	</s:if>
	<s:if test="#rl.status==2">退货</s:if>
	</td>
    </tr>
    </s:iterator>
    </tbody>
    </table>
    </div>
    <!-- 分页,每次最多抓取200行记录,提高网页响应速度 -->
    <div class="row text-center">
    	<s:if test="page.totalPage>1&&page.currentPage<=page.totalPage">
    	<nav>
		  <ul class="pagination">
		  <s:iterator begin="1" end="page.totalPage" var="p">
		   	 <li <s:if test="#p==page.currentPage">class="active"</s:if>><a name="more" data-index="${p}">加载${(p-1)*200}~${p*200 }</a></li>
		  </s:iterator>
		  </ul>
		</nav>
		</s:if>
    </div>
    <br>
  </body>
  <script type="text/javascript">
  	$(function(){
		  $("#bdate").datepicker({dateFormat:"yy-mm-dd"});		
		  $("#edate").datepicker({dateFormat:"yy-mm-dd"});	
		  $("#t1").dataTable({ "lengthMenu": [[-1], ["All"]]});
		  
		  $("a[name='more']").on("click",function(e){
		  	e.preventDefault();
		  	var v = $(this).attr("data-index");
		  	$("#currentPage").val(v);
			$("#f1").submit();		  	
		  });
		  
  	});
   </script>
</html>
