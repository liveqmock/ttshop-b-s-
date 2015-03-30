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
    <title>图片上传</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="favicon.ico">
    <script type="text/javascript" src="./js/jquery.js"></script>
    <script type="text/javascript" src="./js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="./js/uploadify/jquery.uploadify.v2.1.0.min.js"></script>
    <script type="text/javascript" src="./js/uploadify/swfobject.js" ></script>
    <link rel="stylesheet" type="text/css" href="./js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<style>
	  	.bgcolor{
	  		background-color: white;
	  	}
	  </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <div class="panel panel-default">
  		<div class="panel-heading navbar-fixed-top">
  			<h3 class="panel-title">图片预览 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  			<span>/ <a href="<%=request.getContextPath()%>/upload/listPicture.action"> ROOT </a> /</span>
	  			<s:if test="pictureCatalog!=null">
	  				<input type="hidden" id="catalogid" name="catalogid" value="${pictureCatalog.id}">
	  			    <span> <a HREF="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${pictureCatalog.id}">${pictureCatalog.catalog}</a> /</span>
	  			</s:if>
	  			<s:else>
	  				<input type="hidden" id="catalogid" name="catalogid" value="-1">
	  			</s:else>
  				<button type="button" id="opener" data-toggel="modal" data-target="#mymodal" class="btn btn-primary clear-padding-height pull-right">上传图片</button>
  			</h3>
  		</div>
		<div class="panel-body" style="margin-top: 30px;">
			<div class="col-xs-12 col-sm-6 col-md-2 well">
				<ul class="col-xs-offset-2 col-sm-offset-2 col-md-offset-2 list-unstyled">
				  <s:if test="catalogs.size()>0">
					<s:iterator value="catalogs" id="c">
						<li>
							<span class="glyphicon glyphicon-plus-sign" style="cursor: pointer;" id="opener1"></span>
							<span class="glyphicon glyphicon-option-vertical" aria-hidden="true"></span>
							<span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
							<a class="text-info" style="cursor:pointer;" href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${c.id}" data-index="${c.id }">${c.catalog}</a> 
						</li>
					</s:iterator>
				   </s:if>
				   <s:else>
				   	  <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"><a id="opener1">添加目录</a></span>
				   </s:else>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-10">
				<s:iterator value="pictures" id="p">
					<div class="col-xs-12 col-sm-6 col-md-4">
					    <div class="border">
							<a href="${p.url}">
								<img src="${p.url}" class="img-responsive img-thumbnail">
							</a>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 text-center">
							<input type="checkbox" style="width:20px;height:20px;" name="check" value="${p.id}">
						</div>
					</div>
				</s:iterator>
			</div>
		</div>
  	</div>  
  	<div class="modal fade" id="mymodal" tabindex="-1" role="dialog" aria-labelledby="mymodal" aria-hidden="true">
  		<div class="modal-dialog">
  			<div class="madal-content bgcolor">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title">图片上传</h5>				
				</div>
				<div class="modal-body">
					<form>
					   <div id="fileborder">
							<input type="file" name="images" id="uploadify" />
							<div id="fileQueue"></div>
							<p>&nbsp;</p>
			           </div>
			           <div id="message">
			           		
			           </div>					
					</form>
				</div>
				<div class="modal-footer">
		            <a class="btn btn-success" href="javascript:$('#uploadify').uploadifyUpload()">上传</a> 
		            <a class="btn btn-danger" href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:$('#uploadify').uploadifyClearQueue()">关闭</button>
				</div>
  			</div>
  		</div>
  	</div> 
  	
  	<div class="modal fade" id="mymodal1" tabindex="-1" role="dialog" aria-labelledby="mymodal1" aria-hidden="true">
  		<div class="modal-dialog">
  			<div class="madal-content bgcolor">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title">添加目录</h5>				
				</div>
				<div class="modal-body">
					<form class="form" id="f1">
						<div class="form-group">
						    <label class="control-label">目录名称</label>
							<input type="text" name="catalog" class="form-control"/>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="tianjia">添加</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	  </div>
  	 
  </body>
  <script type="text/javascript">
		 $(function () {
		 	function setimgsize(){
  			var width = $(".border").width();
  			var height = width*3/4;
  			//var barheight = $(".navbar-fixed-top").height();
			$("img").each(function(i){
				$(this).css("width",width);
				$(this).css("height",height);
				});
	  		};
	  		setimgsize();
			
			$("img").lazyload({effect: "fadeIn"});
				  		
	  		var cid = $("#catalogid").val();
            $("#uploadify").uploadify({
                'uploader':'js/uploadify/uploadify.swf',
                'script': 'http://localhost:8080/ttshop/upload/uploadimage.action',
                'buttonText': 'SELECT IAMGES',
                'cancelImg':'js/uploadify/cancel.png',
                 //一次性最多允许上传多少个,不设置的话默认为999个
                'queueSizeLimit' : 5,
                //在浏览窗口底部的文件类型下拉菜单中显示的文本
                'fileTypeDesc': 'Image Files',
                'fileDataName': 'images',
                'sizeLimit' : 1*1024*1024,
                //允许上传的文件后缀
                'fileTypeExts': '*.gif; *.jpg; *.png',
                //发送给后台的其他参数通过formData指定
                'scriptData': {'catalogid': cid},
                //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
                'queueID': 'fileQueue',
                //选择文件后自动上传
                'auto': false,
                //设置为true将允许多文件上传
                'multi': true,
                'onComplete' : function(event, ID, fileObj, response, data) {  
                        //当每个文件上传完成后的操作  
                        $("#cancel").hide();  
                        $("#full").hide();
                        $("#message").append("<p>成功上传:"+fileObj.name+"</p>");
                 },
                 'onAllComplete' : function(event,data) {  
                        //当所有文件上传完成后的操作  
                        $("#cancelBtn").hide();  
                        if(data.errors==0){  
                            $("#message").append("<p>所有文件已上传成功(本次共上传"+data.filesUploaded+"个),上传总大小:"+(data.allBytesLoaded/1024).toFixed(2)+"KB,平均传输速度："+data.speed.toFixed(2)+"KB/s"+"</p>");  
                            alert("所有文件已上传成功(本次共上传"+data.filesUploaded+"个),上传总大小:"+(data.allBytesLoaded/1024).toFixed(2)+"KB,平均传输速度："+data.speed.toFixed(2)+"KB/s");
                            window.location.reload();
                        }else{  
                            $("#message").append("<p>成功上传"+data.filesUploaded+"个文件，失败"+data.errors+"个,上传总大小:"+(data.allBytesLoaded/1024).toFixed(2)+"kB,平均传输速度："+data.speed.toFixed(2)+"KB/s"+"</p>");  
                            alert("成功上传"+data.filesUploaded+"个文件，失败"+data.errors+"个,上传总大小:"+(data.allBytesLoaded/1024).toFixed(2)+"kB,平均传输速度："+data.speed.toFixed(2)+"KB/s");
                        	window.location.reload();
                        } 
                  },
                  'onError':function(event,queueID,file,errorObj){
                  	alert("文件过大"+errorObj.type+","+errorObj.info+","+queueID+","+file.name+","+file.size);
                  }
            });
            
            $("#mymodal").on("hidden.bs.modal", function (e) {
			  	$("#message").html("");
			});
            $("#opener").on("click",function(e){
            	$("#mymodal").modal("show");
            });
            $("#opener1").on("click",function(e){
            	$("#mymodal1").modal("show");
            });
			$("#mymodal").on("hidden.bs.modal", function (e) {
			  	$("#fileQueue").html("");
			});
            $("#tianjia").on("click",function(e){
            	e.preventDefault();
            	$.post("./upload/addCatalog.action",$("#f1").serialize(),function(data){
            		alert(data);
            		$("#mymodal1").modal("hide");
            		window.location.reload(true);
            	});
            });
        });
	</script>
</html>
