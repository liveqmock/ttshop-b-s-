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
    
    <title>--增加公司信息--</title>
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
  <s:form action="addcompany" namespace="/company" theme="simple" method="post" cssClass="pure-form pure-form-stacked">
	<fieldset>
		<legend> 添加客户 </legend>
  		 <label>公司编号 ＊</label>   <input class="pure-input-1-4" name="companyid" type="text" />
		 <label>公司名称＊</label>   <input class="pure-input-1-4"  name="companyname" type="text" />
   		 <label>电 话</label>    <input class="pure-input-1-4" name="companytel" type="text" />
   		 <label>地 址</label>   <input class="pure-input-1-4" name="companyaddress" type="text" />  
   		 <label>公司LOGO</label>   <input class="pure-input-1-4" name="companylogo" type="file" /> 
   		 <label>负责人</label>   <input class="pure-input-1-4" name="companymanager" type="text" /> 
		 <button type="button" onclick="this.form.submit()" class="pure-button pure-button-primary"> 确   认  </button>
		</fieldset>
	<s:token></s:token>
   	</s:form>
   	
  </body>
</html>
