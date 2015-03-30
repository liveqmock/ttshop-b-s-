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
    
    <title>-- 销售报表 --</title>
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
  	  	<form action="<%=request.getContextPath()%>/sale/findRecordByDate.action" method="post" id="f1" class="form-inline noprint">
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
     	 <button type="button" id="mxb" class="btn btn-success">明细表</button>
     	 <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
    	</form>
  	  </div>
  	 </div>
  	</div>
  	<div class="table-responsive">
    <table class="table noprint" id="t1" width="100%">
    <thead>
    <tr class="warning">
	<td> 销售单号 </td>
	<td> 操作者 </td>
	<td> 仓库 </td>
	<td> 客户 </td>
	<td> 数量 </td>
	<td> 货币 </td>
	<td> 支付方式</td>
	<td> 总额 </td>
	<td> 时间 </td>
	<td> 状态 </td>
	</tr>
	</thead>
	<tbody>
    <s:iterator value="saleRecords" id="sr">
    <tr>
	<td >
	<a href="<%=request.getContextPath()%>/sale/findlistByInvoice.action?keyword=${sr.invoiceno}" target="main" >${sr.invoiceno }</a>
	</td>
	<td >${sr.operatorid }</td>    
	<td >${sr.warehouse }</td>    
	<td >  <%-- <a name="customer" href="<%=request.getContextPath()%>/sale/findRecordByInvoice.action?"> ${sr.customerid } </a> --%>
		${sr.customerid }
	</td>    
	<td >${sr.totalquantity }</td>    
	<td > ${sr.currency }</td>    
	<td >
	<s:if test="#sr.paidtype==0">现金</s:if>
	<s:if test="#sr.paidtype==1">刷卡</s:if>
	<s:if test="#sr.paidtype==2">签单</s:if>
	<s:if test="#sr.paidtype==3">支票</s:if>
	<s:if test="#sr.paidtype==4">其它</s:if>
	</td>    
	<td > <fmt:formatNumber value="${sr.totalamount }" type="currency" pattern="＄.0#"/></td>    
	<td >${sr.createtime } </td>
	<td >
	<s:if test="#sr.status==0"> 作废
	 </s:if>
	<s:if test="#sr.status==1"> 有效   
	</s:if>
	<s:if test="#sr.status==2">退货</s:if>
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
    <div id="dialog" title="销售明细">
    	
    </div>
  </body>
  <script type="text/javascript">
  	$(function(){
		  $("#bdate").datepicker({dateFormat:"yy-mm-dd"});		
		  $("#edate").datepicker({dateFormat:"yy-mm-dd"});	
		  $("#t1").dataTable({ "lengthMenu": [[20,50,-1], ["20","50","All"]]});
		  $("#dialog").dialog({
  				autoOpen:false,
  				height:600,
  				width:900,
  				modal:true,
  				position:[0,0],
  		  }); 
		  
		  $("a[name='more']").on("click",function(e){
		  	e.preventDefault();
		  	var v = $(this).attr("data-index");
		  	$("#currentPage").val(v);
			$("#f1").submit();		  	
		  });
		  
		  $("a[name='customer']").on("click",function(e){
		  	e.preventDefault();
		  	var val = $(this).html();
		  	$.ajax({
		  		type:"POST",
		  		url:"<%=request.getContextPath()%>/sale/findCustomerSaleRecords.action",
		  		data:"customer="+val,
		  		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		  		success:function(json){
		  			var ja = eval('('+json+')');
		  			$("#dialog").dialog("open");
		  			var table="";
		  			table+="<table style='width:100%'>";
		  			for(var a = 0; a < ja.length; a++){
		  				var index = a;
		  				table+="<tr>";
			  				table+="<td style='width:25%'>";
			  				table+= (index+1) ;
			  				table+="</td>";
			  				table+="<td style='width:25%'>";
			  				table+=ja[index].invoiceno;
			  				table+="</td>";
			  				table+="<td style='width:25%'>";
			  				table+=ja[index].totalquantity;
			  				table+="</td>";
			  				table+="<td style='width:25%'>";
			  				table+=ja[index].totalamount;
			  				table+="</td>";
		  				table+="</tr>";
		  			}
		  			table+="</table>";
		  			$("#dialog").html(table);
		  		},
		 	 });
		  });
		$("#mxb").on("click",function(e){
			e.preventDefault();
			var a2 = "<%=request.getContextPath()%>/sale/findSaleRecordListByDate.action";
			$("#f1").attr("action",a2);
			$("#f1").submit();
		});
  	});
   </script>
</html>
