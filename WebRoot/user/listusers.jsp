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
   
  <title>--用户管理--</title>
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
  </head>
  <script type="text/javascript">
	  $(function($){
			$("#t1").dataTable({ "lengthMenu": [[15,50, 100,-1], [15, 50, 100,"All"]]});
	  });
  </script>
  
  <body>
  	<div class="container-fluid">
  	<div class="row hidden-print">
 	<form action="<%=request.getContextPath() %>/user/listusers.action" method="post" class="form-inline">
 		<div class="form-group">
 		   <div class="input-group">
 		 	  <div class="input-group-addon">关键字:</div>
	  		  <input name="keyword" class="form-control" type="text" value="" placeholder="关键字" />
 		   </div>
 		</div>
  			<button type="submit" class="btn btn-primary">搜索</button>
 		 <button type="button" class="btn btn-primary pull-right" onclick="window.location.href='${returnurl}'">返回</button>
   	</form>
   	</div>
   	<div class="row table-responsive">
   	<table id="t1" class="table" width="100%">
   	<thead>
   		<tr class="warning">
   		   	<Td> 用户Id </Td>
   			<Td> 用户名称 </Td>
   			<Td> 用户权限 </Td>
   			<Td> Email</Td>
   			<Td> 电话号码</Td>
   			<Td> 更新时间 </Td>
   			<Td> 状 态 </Td>
   			<td> 修  改  </td>
   			<td> 权限修改  </td>
   		</tr>	
   </thead>
   	<tbody>
   		<s:iterator value="users" id="user" >
   			<tr>  
   				<Td> <a href="<%=request.getContextPath() %>/user/viewuserinfo.action?user.id=${user.id}">${user.userid }</a> </Td> 
   				<Td> ${user.username } </Td>
   				<Td>
   					<s:if test="#user.role==0">系统管理员</s:if>
   					<s:if test="#user.role==1">销售员</s:if>
   					<s:if test="#user.role==2">仓库管理员</s:if>
   					<s:if test="#user.role==3">经理/Boss</s:if>
   				 </Td>
   				<Td> ${user.mail } </Td>
   				<Td> ${user.telephone } </Td>
   				<Td> ${user.updatetime} </Td>
   				<Td>
   					<s:if test="#user.status==1">
   						有效
   					</s:if>
   					<s:if test="#user.status==0">
   						作废
   					</s:if>
   				</Td>
   				<td> <a href="<%=request.getContextPath() %>/user/toupdateuser.action?userid=${user.userid }">修改资料 </a> </td>
   				<td> <a href="<%=request.getContextPath() %>/user/tosetrole.action?userid=${user.userid }">修改权限 </a> </td>
   			</tr>
   		</s:iterator>
   	</tbody>
   	</table>
   	</div>
   </div>	
  </body>
</html>
