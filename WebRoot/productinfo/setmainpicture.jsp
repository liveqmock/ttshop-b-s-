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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>图片浏览</title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
	<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="./css/default.css">
	<style>
	  	.modal-dialog{
	  		background-color:white;
	  		z-index: 9999!important;
	  	}
	  	.border{
	  		margin-top: 5px;
	  		position: relative;
	  	}
	  	.mfs{
	  		font-size: 18px;
	  		padding-left: 5px;
	  		padding-right: 5px;
	  	}
	  	ul li ul{
	  		margin-left: 10px;
	  	}
	  	.border img:hover{
	  		border: 2px solid #666;
	  	}
	  	.panel-title button{
	  		margin-left: 5px;
	  		margin-right: 5px;
	  	}
	  	.right{
	  		position:absolute;
	  		right:0;
	  		top:0;
	  		width:32px;
	  		height:32px;
	  		background:url(./images/white_check_mark.png);
	  		background-repeat: no-repeat;;
	  		background-size:cover; 
	  	}
	  	.edit{
	  		position:absolute;
	  		right:0;
	  		bottom:0;
	  		display: none;
	  	}
	  </style>
  </head>
  
  <body>
    <div class="panel panel-default">
    	<div class="panel-heading">
    		<h3 class="panel-title">设置主图片
    			<input type="checkbox" onclick="checkall(this)"><a>全选</a>
    			<button type="button" class="btn btn-primary clear-padding-height pull-right" onclick="window.location.href='${returnurl}'">返回</button>
    			<button type="button" class="btn btn-info clear-padding-height pull-right" onclick="window.location.reload(true)">刷新</button>
    			<button type="button" id="opener" class="btn btn-primary clear-padding-height pull-right">图片库</button>
    			<button type="button" id="opener1" class="btn btn-success clear-padding-height pull-right">现在上传</button>
    			<button type="button" id="delete" class="btn btn-danger clear-padding-height pull-right" style="visibility: hidden;margin-left: 5px;margin-right: 5px;">删除选中</button>
    		</h3>
    	</div>
    	<div class="row">
    		<s:if test="pictures.size()>0">
    		<form id="f1">
    		<input type="hidden" name="barcode" value="${barcode }">
    		<s:iterator value="pictures" id="p">
    			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
    				<div class="border">
    					<s:if test="info.picture==#p.id"><div class="right" ></div></s:if>
    					<button class="btn btn-sm btn-success edit" type="button" data-index="${p.id}" >编辑</button>
						<a data-index="${p.id}" style="cursor:pointer;">
							<img src="${p.small}" title="单击选择" class="img-responsive img-thumbnail">
						</a>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 text-center" style="margin-top:5px; margin-bottom:5px;">
						<input type="checkbox" name="ids" style="width: 20px;" value="${p.id}" onclick="checkone(this);">
					</div>
    			</div>
    		</s:iterator>
    		</form>
    		</s:if>
    		<s:else>
    			<p class="text-danger text-center">此产品没有上传图片</p>
    		</s:else>
    	</div>
    </div>
    <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
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
					<form action="javascript:" id="upload" enctype="multipart/form-data" class="form-horizontal" method="post">
					   <input type="hidden" name="barcode" value="${barcode }">
					   <input type="hidden" id="catalogid" name="catalogid" value="0">
						   <div class="form-group">
			           		 	<input type="file" class="form-control" id="images" name="images" multiple="multiple" accept="image/*">
			           		 	<div id="msg" class="text-waning" style="padding-left:5px;margin-top: 10px;"></div>
						   </div>
					  </form>
					  <div id="message">
				           	<div class="row">
								<progress style="width: 100%;" id="progressBar" value="0" max="100"></progress>
								<div id="percent" class="text-info text-center"></div>
				           </div>
					</div>
					<div class="modal-footer">
			           <button type="button" id="uploadfile" class="btn btn-primary">上传</button>
					</div>
			   </div>
  		   </div>
  		</div>
  	</div>
  </body>
  <script type="text/javascript" src="./js/jquery.js"></script>
  <script type="text/javascript" src="./js/basefn.js"></script>
  <script type="text/javascript" src="./js/bootstrap.min.js"></script>
  <script type="text/javascript" src="./js/myhtml5fileupload.js"></script>
  <script type="text/javascript" src="./js/uploadtool.js"></script>
  <script type="text/javascript">
  $(function(){
	  	$(".border").setimgsize();
	  	
	  	$(".border a").on("click",function(e){
	  		var id = $(this).attr("data-index");
	  		$.post("./upload/changeMainPicture.action",
	  			$("#f1").serialize()+"&picture.id="+id,function(data){
	  				window.location.reload(true);
	  		});
	  	});
	  	
	  	$(".border").hover(function(e){
	  			e.preventDefault();
	  			$(this).find(".edit").css("display","block");
	  		},function(e){
	  			e.preventDefault();
	  			$(this).find(".edit").css("display","none");
	  	});
	  	$(".edit").on("click",function(e){
	  		e.preventDefault();
	  		var id = $(this).attr("data-index");
	  		window.open("<%=request.getContextPath()%>/upload/toMeituEditor.action?picture.id="+id,"meitu_editor");
	  	});
	  	$("#opener").on("click",function(e){
	  		e.preventDefault();
            window.open("./upload/listPicture.action?catalogid=","picture");
        });
        
	  	$("#opener1").on("click",function(e){
	  		e.preventDefault();
            $(".modal").modal("show");
        });
        
        $("#images").on("change",function(){
			checkfile();
		});
		
		$("#uploadfile").on("click",function(){
			uploadfile("<%=request.getContextPath()%>/upload/uploadFileAjaxhaveBarcode.action");
		});
		
		$("#delete").on("click",function(e){
           	e.preventDefault();
           	$.post("./upload/deleteImage.action",$("#f1").serialize(),function(data){
           		alert(data);
           		window.location.reload(true);
           	});
        });
  });
  </script>
</html>
