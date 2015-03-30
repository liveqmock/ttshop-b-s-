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
    <title>-- 销售统计 --</title>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  	<script type="text/javascript">
  	$(function(){
  		$("#bdate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#edate").datepicker({dateFormat:"yy-mm-dd"});
  		$("#datatable").dataTable({ "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]]});
  	});
   </script>
  </head>
  
  <body> 
    <form action="<%=request.getContextPath()%>/sale/listCount.action" method="post"  class="form-inline">
    <fieldset>
       <div class="from-group">
       <input type="text" class="form-control" name="begindate" value="${begindate }" id="bdate" placeholder="开始日期">
  	   <input type="text" class="form-control" name="enddate" value="${enddate }" id="edate"placeholder="结束日期">
       <button type="submit" class="btn btn-primary">搜索</button>
       </div>
     </fieldset>
    </form>
	<br>
	<div class="table-responsive">
    <table class="table display" id="datatable" cellspacing="0" width="100%">
    <thead>
    <tr>
	<td> Barcode </td>
	<td> Description</td>
	<td> Quantity </td>
	<td> imeis </td>
	</tr>
	</thead>
	<tbody>
    <s:iterator value="objects" id="ob">
    <tr>
	<td>${ob[1]}</td>
	<td>${ob[2]}</td>    
	<td>${ob[3]}</td>
	<td>${ob[0]}</td>
    </tr>
    </s:iterator>
    </tbody>
    <tfoot>
    <tr>
    <td colspan="2"> 合 计 </td>
	<td>${totalCount} </td>    
	<td>&nbsp; </td>    
    </tr>
    </tfoot>
    </table>
	</div>
  </body>
</html>
