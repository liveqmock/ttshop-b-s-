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
    
    <title>--用户注册 --</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  </head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript">
  jQuery(function($){
  		$("#username").change(function(){
  			var value = $(this).val();
  			var action = "<%=request.getContextPath()%>/user/checkuser.action?username="+value;
  			$.ajax({
  				url:action,
  				async:false,
  				success:function(data){
  					alert(data);
  					$("#check").html(data);
  				}
  			});
  		});
  });
  
  
  </script>
  
  
  <body>
  <s:form action="register" namespace="/user" theme="simple" method="post">
  <table>
  <thead>
  	<tr><td><h3>用户注册 </h3></td></tr>
  </thead>
  <tbody>
  		<tr> <td>账     号： </td> <td> <input name="userid" type="text" /> * </td></tr>
		<tr> <td>昵     称： </td> <td> <input name="username" type="text" /> * </td></tr>
   		<tr> <td>输入密码:  </td> <td> <input name="password" type="password" /> * </td></tr>
   		<tr> <td>确认密 码:  </td> <td> <input name="cmfpassword" type="password" /> * </td></tr>
   		<tr> <td>邮     箱:  </td> <td> <input name="mail" type="text" /> </td></tr>
   		<tr> <td>电     话:  </td> <td> <input name="telephone" type="text" /> </td></tr>
		<tr> <td><input type="submit" value="注 册"></td></tr>
	</tbody>
	</table>
	<s:token></s:token>
   	</s:form>
   	
  </body>
</html>
