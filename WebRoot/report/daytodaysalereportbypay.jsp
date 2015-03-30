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
    <title> 销售统计报表 - 按收银统计  </title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/data.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/Chart.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  	<style type="text/css">
  	*{
  	  font-size: 12px;
  	}
  	</style>
  	<script type="text/javascript">
	  	$(function(){
			  $("#bdate").datepicker({dateFormat:"yy-mm-dd"});		
			  $("#edate").datepicker({dateFormat:"yy-mm-dd"});
			  $("#t1").dataTable({ "lengthMenu": [[15,30,100,-1], [15, 30, 100, "All"]]});
			  $("#dialog").dialog({
  				autoOpen:false,
  				height:600,
  				width:980,
  				modal:true,
  				position:[0,0]});
  			  $("#opener").click(function(e){
  			  	e.preventDefault();
        		$("#dialog").dialog("open");
        	  });
	  	});
	   </script>
  </head>
  
  <body>
  <div class="panel panel-default">
  	<div class="panel-heading noprint">
	  	<form action="<%=request.getContextPath()%>/report/daytodaySaleReportBypay.action" method="post" class="form-inline">
		    <h5 class="panel-title">销售统计
			     <input class="form-control" id="keyword" name="keyword" value="${keyword}" type="hidden" readonly="readonly"/>
			     <input class="form-control" id="bdate" name="begindate" value="${begindate }" placeholder="开始时间" type="date" />
			     <input class="form-control" id="edate" name="enddate" value="${enddate }" placeholder="结束时间" type="date" />
			     <button type="submit" class="btn  btn-primary">确认</button>
			     <button class="btn  btn-primary" id="opener">图表显示</button>
			     <button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary pull-right">返回</button>
		     </h5>
	    </form>
	 </div>
    <div class="panel-body">
    <div class="table-responsive">
    <table id="t1" class="table" width="100%">
        <thead>
		    <tr class="warning">
			<td width="25%"> 货币  </td>
			<td width="25%"> 支付方式 </td>
			<td width="25%"> 总数 </td>
			<td width="25%"> 总额 </td>
			</tr>
		</thead>
	    <tbody>
	    <s:iterator value="objects" id="sta">
		    <tr>
				<td width="25%"> ${sta[2]} </td>
				<td width="25%">
					<s:if test="#sta[3]==0">现金</s:if>
					<s:if test="#sta[3]==1">刷卡</s:if>
					<s:if test="#sta[3]==2">签单</s:if>
					<s:if test="#sta[3]==3">支票</s:if>
					<s:if test="#sta[3]==4">其它</s:if>
				</td>
				<td width="25%"> ${sta[0]}</td>
				<td width="25%"> <fmt:formatNumber value="${sta[1]}" type="currency" pattern="＄.0#"/> </td>
			</tr>
			</s:iterator>
			<tfoot style="border-top: 1px solid silver">
			<tr>
				<td width="50%" colspan="2"> 合 计  </td>
				<td width="25%"> ${totalquantitty}</td>
				<td width="25%"> <fmt:formatNumber value="${totalamount}" type="currency" pattern="＄.0#"/> </td>
			</tr>
			</tfoot>
		</tbody>
    </table>
    </div>
    </div>
    <div id="dialog" title="统计表">
    	<p style="font-weight: bolder;color: blue;">销售统计</p>
    	<div class="row" style="display:block; ">
    		<canvas id="canvas" height="300" width="800"></canvas>
   	 	</div>
    </div>
  </div>  
  </body>
  <script>
	$(function(){
		function sortNumber(a,b){return b - a;};
		var ls = getLable1("t1");
		var vs = gevalue1("t1");
		var vs2 = gevalue2("t1");
		var barData ={
			labels :ls,
			datasets : [
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,0.8)",
				highlightFill: "rgba(220,220,220,0.75)",
				highlightStroke: "rgba(220,220,220,1)",
				data : vs
			},
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,0.8)",
				highlightFill : "rgba(151,187,205,0.75)",
				highlightStroke : "rgba(151,187,205,1)",
				data : vs2
			}
		]
		};
		
		var ctx = $("#canvas").get(0).getContext("2d");
		var mychart = new Chart(ctx).Bar(barData);
		
		function getLable1(id){
			var trs = $("#"+id).find("tbody").find("tr");
			var s = trs.size();
			var labels=new Array();
			for ( var i = 0; i < s; i++) {
				labels.push(trs.eq(i).find("td:eq(1)").text().trim()+trs.eq(i).find("td:eq(0)").text().trim());
			}
			return labels;
		};
		function getLable0(id){
			var trs = $("#"+id).find("tbody").find("tr");
			var s = trs.size();
			var labels=new Array();
			for ( var i = 0; i < s; i++) {
				labels.push(trs.eq(i).find("td:eq(0)").text().trim());
			}
			return labels;
		};
		function gevalue0(id){
			var trs = $("#"+id).find("tbody").find("tr");
			var s = trs.size();
			var vals=new Array();
			for ( var i = 0; i < s; i++) {
				vals.push(trs.eq(i).find("td:eq(1)").text().trim().replace("＄",""));
			}
			return vals;
		};
		function gevalue1(id){
			var trs = $("#"+id).find("tbody").find("tr");
			var s = trs.size();
			var vals=new Array();
			for ( var i = 0; i < s; i++) {
				vals.push(trs.eq(i).find("td:eq(2)").text().trim().replace("＄",""));
			}
			return vals;
		};
		function gevalue2(id){
			var trs = $("#"+id).find("tbody").find("tr");
			var s = trs.size();
			var vals=new Array();
			for ( var i = 0; i < s; i++) {
				vals.push(trs.eq(i).find("td:eq(3)").text().trim().replace("＄",""));
			}
			return vals;
		};
		function createPiedata(ls,vs){
			var cs = new Array();
			var hcs = new Array();
			var data = new Array();
			for ( var i = 0; i < ls.length; i++) {
				var co = Math.round(Math.random()*1000000);
				var color = "#"+co;
				var color1 = "#"+(co+20);
				cs.push(color);
				hcs.push(color1);
				var one = {
					value:vs[i],
					color:color,
					label:ls[i],
				};
				data.push(one);
			}
			return data;
		};
	});
	</script>
</html>
