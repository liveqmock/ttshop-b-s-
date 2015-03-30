<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>图片上传-美图秀秀</title>
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
    <style type="text/css">
		html, body { height:100%; overflow:hidden; }
		body { margin:0; }
	</style>
	<script type="text/javascript">
	 var action = "http://localhost:8080/ttshop/upload/meituEditor.action";
	 var url = "${picture.url}";
	 var address = "<%=basePath%>";
	 var end = address.indexOf("/ttshop");
	 address =  address.substring(0, end);
	 var aburl = address+url;
	 window.onload = function(){
	 	xiuxiu.setLaunchVars("maxFinalWidth", 700);
	    /*第1个参数是加载编辑器div容器，第2个参数是编辑器类型，第3个参数是div容器宽，第4个参数是div容器高*/
  		xiuxiu.embedSWF("imageContent", 1, "100%","90%", "lite");
	 	//修改为您自己的图片上传接口
	 	
        xiuxiu.setUploadURL(action);
        //type = 2 为标准表单上传,相当于 multi/formdata
	 	xiuxiu.setUploadType(2);
	 	//相当于input[file] 的 name 属性 
	 	xiuxiu.setUploadDataFieldName("image");
	 	xiuxiu.onInit = function () {
		    xiuxiu.loadPhoto(aburl);//修改为要处理的图片url,完整路径
		};
		xiuxiu.onBeforeUpload  = function(data,id) {
			var size = data.size;
			if(size > 2*1024*1024){
				alert("图片不能超过2M");
				return false;
			}
			return true;
		};
		xiuxiu.onUploadResponse = function (data) {
		    alert(data); //可以开启调试
		}; 
		xiuxiu.onDebug= function(data,id) {
			alert( data);
		};
	 };
	</script>
  </head>
  
  <body>
    <div id="imageContent">
    	<h1>美图秀秀</h1>
    </div>
  </body>
</html>
