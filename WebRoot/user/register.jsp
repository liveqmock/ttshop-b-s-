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
    <title>--用户注册--</title>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=0">
  	<meta name="apple-mobile-web-app-capable" content="yes">
  	<meta name="apple-touch-fullscreen" content="yes">
  	<link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  <script type="text/javascript">
  jQuery(function($){
  		$("#userid").change(function(){
  			checkuserid();
  		});
  		
  		$("#email").on("change",function(e){
  			checkemail();
  		});
  		
  		$("#telephone").on("change",function(){
  			checktelephone();
  		});
  		
  		$("#comfirmpass").on("change",function(){
  			checkpassword();
  		});
  		
  		$("#comfirm").on("click",function(e){
  			e.preventDefault();
  			if(checkform()){
  				$("#f1").submit();
  			}
  		});
  		
  		function checkform(){
  			var flag  = false;
  			if(checkemail()&&checkpassword()&&checktelephone()&&checkuserid()){
  				flag = true;
  			}
  			return flag;
  		};
  		
  		function checktelephone(){
  			var thisobj = $("#telephone");
  			var istelenumber = /^\d{11}|\d{3}\-\d{8}$/;
  			var v = thisobj.val();
  			if(!istelenumber.test(v)){
  				thisobj.siblings("div").html("<span style='color:red'>手机号码格式不正确！大陆地区为11为数字,港澳为xxx-xxxxxxxx</span>");
  				return false;
  			}else{
  				thisobj.siblings("div").html("");
  				return true;
  			}
  		};
  		
  		function checkemail(){
  			var thisobj = $("#email");
  			var v = thisobj.val();
  			var isemail = /^\w+@\w+\.\w+$/;
  			if(!isemail.test(v)){
  				thisobj.siblings("div").html("<span style='color:red'>email格式不正确!</span>");
  				return false;
  			}else{
  				thisobj.siblings("div").html("");
  				return true;
  			}
  		};
  		
  		function checkpassword(){
  			var thisobj = $("#comfirmpass");
  			var pass = $("#pass").val();
  			var comfirmpass = thisobj.val();
  			if(pass!=comfirmpass){
  				thisobj.siblings("div").html("<span style='color:red'>两次输入的密码不符!</span>");
  				return false;
  			}else{
  				thisobj.siblings("div").html("");
  				return true;
  			}
  		};
  		
  		function checkuserid(){
  			var thisobj = $("#userid");
  			var value = thisobj.val();
  			var action = "<%=request.getContextPath()%>/user/checkuserid.action?userid="+value;
  			var reg = /^\w{5,20}$/;
  			thisobj.siblings("div").html("");
  			if(value!=""){
  			  if(reg.test(value)){
	  		   return $.post(action,function(data){
	  				if(data.indexOf("error01")!=-1){
	  					thisobj.siblings("div").html("<span style='color:red'>此用户Id已被使用</span>");
	  					return false;
	  				}else{
	  					thisobj.siblings("div").html("<span style='color:green'>此用户Id可以使用</span>");
	  					return true;
	  				}
	  			});
  			  }else{
  			  	 thisobj.siblings("div").html("<span style='color:red'>用户名为5~20个字符或数字组成</span>");
  			  	 return false;
  			  }
  			}else{
  				thisobj.siblings("div").html("<span style='color:red'>用户名为5~20个字符或数字组成</span>");
  				return false;
  			}
  		};
  });
  </script>
  
  
  <body>
  <div class="container">
 	<div class="row">
 		<div class="panel panel-info">
 			<div class="panel-heading">
 				<h3 class="panel-title">新用户注册
  					<button type="button" onclick="window.history.back()" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
 			</div>
 			<div class="panel-body">
 				<s:form id="f1" action="register" namespace="/user" theme="simple" method="post" role="form" cssClass="form-horizontal" autoComplete="false">
			  		<fieldset>
			  		 <div class="form-group">
				  		 <label class="col-xs-3 col-sm-2 col-md-2 control-label" class="col-sm-2 col-md-2 control-label" for="userid">用户ID *</label>
				  		 <div class="col-xs-9 col-sm-10 col-md-10">
					  		 <input name="userid" type="text" id="userid" class="form-control" autocomplete="off" required/>
				  		 <div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
				  		 </div>
			  		 </div>
			  		 <div class="form-group">
				  		 <label class="col-xs-3 col-sm-2 col-md-2 control-label" for="email">用户名*</label>
				  		 <div class="col-xs-9 col-sm-10 col-md-10">
						 	<input name="username" type="text" class="form-control" autocomplete="off" required/>
						 <div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
						 </div>
					 </div>
					 <div class="form-group">
						 <label class="col-xs-3 col-sm-2 col-md-2 control-label" for="email">密码 *</label>
						 <div class="col-xs-9 col-sm-10 col-md-10">
				   		 	<input name="password" type="password" id="pass" class="form-control" autocomplete="off" required/>
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
				   		 </div>
			   		 </div>
			   		 <div class="form-group">
				   		 <label class="col-xs-3 col-sm-2 col-md-2 control-label"  for="cmfpassword">重复密码*</label>
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				   		 	<input name="cmfpassword" type="password"  id="comfirmpass" class="form-control" autocomplete="off" required/>
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
				   		 </div>
			   		 </div>
			   		 <div class="form-group">
				   		 <label class="col-xs-3 col-sm-2 col-md-2 control-label" for="mail">Email</label>
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				   		 	<input name="mail" type="email" id="email" class="form-control" autocomplete="off" /> 
			   		 	<div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
			   		 	</div>
			   		 </div>
			   		 <div class="form-group">
				   		 <label class="col-xs-3 col-sm-2 col-md-2 control-label" for="email">手机号码</label>
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				   		 	<input name="telephone" type="tel" id="telephone" class="form-control" autocomplete="off"/> 
				   		 <div class="col-xs-9 col-sm-10 col-md-10">
				  		 	
				  		 </div>
				   		 </div>
			   		 </div>
			   		 <div class="form-group">
				   		 <div class="col-xs-9 col-sm-10 col-md-10 col-sm-offset-2 col-md-offset-2">
						 	<button type="button" id="comfirm" class="btn btn-success">确认</button>
						 </div>
					 </div>
					 </fieldset>
					 <s:token></s:token>
					 <div class="form-group">
					 	 <div class="col-xs-9 col-sm-10 col-md-10 col-sm-offset-2 col-md-offset-2">
	 						<p id="me" class="text-warning">${me}</p> 
	 					 </div>
	 				 </div>
			   	</s:form>
 			</div>
 		</div>
   	</div>
   </div>
  </body>
</html>
