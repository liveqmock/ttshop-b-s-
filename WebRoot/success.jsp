<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-- SUCCESS PAGE--</title>
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript">
		var time = 4;
		function jump(){
			document.getElementById("time").innerHTML=time;
			time--;
			if(time<0){
				window.location.href="${returnurl}";
			}
		}
		window.setInterval(jump,1000);
	</script>
  </head>
  
  <body>
  	<div class="container-fluid">
   		<div class="row well text-center">
   			<h3 class="text-success">${me }</h3>
   			<span class="text-success"> <a class="text-danger" id="time">5</a> <a href="${returnurl}"> 自动跳转 </a> </span>
   		</div>
   	</div>
  </body>
</html>
