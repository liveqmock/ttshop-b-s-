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
    <title>入货明细表</title>
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
  </head>
  
  <body>
    <div class="panel panel-default">
     <div class="panel-heading">
      <form action="<%=request.getContextPath()%>/inbound/createInnoteList.action" id="f1" class="form-inline" method="post">
      <input type="hidden" id="currentPage" name="page.currentPage" value="1">
      <h5 class="panel-title">明细表
      	 <select name="warehouse" class="form-control">
  	 		  <option value="all" <s:if test="#w.wname=='all'">selected</s:if>>所有仓库</option>
  	 	    <s:iterator value="warehouses" id="w">
  	 		  <option value="${w.wname }"<s:if test="#w.wname==warehouse">selected</s:if>>${w.wnickname }</option>
  	 	    </s:iterator>
  	     </select>
	  	 <input type="date" class="form-control" name="begindate" id="bdate" value="${begindate}" placeholder="开始日期">
	  	 <input type="date" class="form-control" name="enddate" id="edate" value="${enddate}" placeholder="结束日期">
	     <button type="submit" class="btn btn-primary">确认</button>
	     <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
      </h5>
      </form>
     </div>
     <div class="table-responsive">
      <table id="datatable" class="table">
        <thead>
		    <tr class="warning">
			<td width="10%"> 入货单 </td>
			<td width="20%"> 货品  </td>
			<td width="10%"> 总数 </td>
			<td width="10%"> 单价</td>
			<td width="10%"> 总额 </td>
			<td width="20%"> 日期 </td>
			<td width="10%"> 仓库 </td>
			<td width="10%"> 供应商 </td>
			</tr>
		</thead>
	    <tbody>
	    <s:iterator value="innotelistdetial" id="obj">
		    <tr>
				<td > <a href="<%=request.getContextPath()%>/inbound/innotelistdetail.action?notenumber=${obj[0]}"> ${obj[0]} </a>  </td>
				<td > ${obj[6]} </td>
				<td > ${obj[1]} </td>
				<td > <fmt:formatNumber value="${obj[2]}" type="currency" pattern="＄.0#"/> </td>
				<td > <fmt:formatNumber value="${obj[1]*obj[2]}" type="currency" pattern="＄.0#"/> </td>
				<td > ${obj[4]} </td>
				<td > ${obj[8]} </td>
				<td > ${obj[9]} </td>
			</tr>
			</s:iterator>
		</tbody>
    </table>
    </div>
    <!-- 分页,每次最多抓取200行记录,提高网页响应速度 -->
    <div class="container text-center">
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
   </div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$("#bdate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#edate").datepicker({dateFormat:"yy-mm-dd"});
  		$("a[name='more']").on("click",function(e){
		  	e.preventDefault();
		  	var v = $(this).attr("data-index");
		  	$("#currentPage").val(v);
			$("#f1").submit();		  	
		});
  		/* $("#datatable1").dataTable({ "lengthMenu": [[5,15,100,-1], [5, 15,100,"All"]]}); */
  		$("#datatable").dataTable({ "lengthMenu": [[-1], ["All"]]});
  	});
  </script>
</html>
