<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./js/editor.js"></script>
  </head>
  
  <body>
  	<div class="container">
  	<form action="" class="form">
  		<div id="con">
    		<textarea rows="10" class="form-control">aaa</textarea>
    	</div>
    </form>
    </div>
  </body>
  <script type="text/javascript">
  $(function(){
  	$("#con").editor();
  });
  </script>
</html>
