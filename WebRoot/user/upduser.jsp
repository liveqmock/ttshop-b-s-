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
    
    <title>--UPDATE USER--</title>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
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
  <div class="container-fluid">
  	<div class="row">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				<h3 class="panel-title">修改用户信息 
  					<button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
			  <form action="<%=request.getContextPath() %>/user/updateuser.action" method="post" class="form-horizontal">
			 	 <fieldset>
			 	 	<s:if test="optype!=null && optype==1">
			  			<input type="hidden" name="optype" value="${optype}"/>
			  		</s:if>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">用户账号*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="userid" type="text" readonly="readonly" value="${user.userid}" required/>
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">用户名*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="username" type="text" value="${user.username }" required/>
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">Email</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="mail" type="email" value="${user.mail }" />
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">电话</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="telephone" type="tel" value="${user.telephone }" />
				  		</div>
					</div>
			 	 	<div class="form-group">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">输入密码*</label>
				  		<div class="col-xs-9 col-sm-8 col-md-6">
				  			<input class="form-control" name="password" type="password" required/>
				  		</div>
					</div>
					 <div class="form-group">
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<div class="col-xs-9 col-sm-8 col-md-6">
							<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-9 col-sm-8 col-md-6">
							<p class="text-warning">${me}</p>
						</div>
					</div>
				</fieldset>
			   	</form>
   			</div>
   		</div>
   	  </div>
   	</div>
  </body>
</html>
