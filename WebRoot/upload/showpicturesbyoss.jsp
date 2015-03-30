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
    <title>图片浏览</title>
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
    <script type="text/javascript" src="./js/basefn.js"></script>
    <script type="text/javascript" src="./js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="./js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="./css/default.css">
	<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
	<style>
	  	.modal-dialog{
	  		background-color:white;
	  		z-index: 9999!important;
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
	  </style>
   
  </head>
  
  <body>
    <div class="panel panel-default">
  		<div class="panel-heading">
  			<h3 class="panel-title">图片预览 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  			<span>/ <a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action"> ROOT </a> /</span>
	  			<s:if test="pictureCatalog!=null">
	  			    <span><a HREF="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${pictureCatalog.id}">${pictureCatalog.catalog}</a> /</span>
	  			</s:if>
  				<button type="button" id="opener" data-toggel="modal" data-target="#mymodal" class="btn btn-primary clear-padding-height pull-right">上传图片</button>
	  			<button type="button" id="delete" class="btn btn-primary clear-padding-height pull-right" style="visibility: hidden;margin-left: 5px;margin-right: 5px;">删除选中</button>
	  			<button type="button" id="change" class="btn btn-primary clear-padding-height pull-right" style="visibility: hidden;margin-left: 5px;margin-right: 5px;">移动</button>
	  			<button type="button" id="changebarcode" class="btn btn-primary clear-padding-height pull-right" style="visibility: hidden;margin-left: 5px;margin-right: 5px;">设置图片条码</button>
  			</h3>
  		</div>
		<div class="panel-body">
			<div class="col-xs-12 col-sm-4 col-md-3">
				<p class="bg-info" style="margin-top: -3px;">目录 </p>
				<ul class="list-unstyled">
					<li>
						<span>
						<span class="glyphicon glyphicon-folder-open mfs" aria-hidden="true"></span>
							<a class="text-info" style="cursor:pointer;" href="<%=request.getContextPath()%>/upload/listPictureByOSS.action" data-index="0">ROOT</a>
				  		</span>
				  		<span class="glyphicon glyphicon-plus-sign mfs pull-right" style="cursor: pointer;" id="opener1"></span>
				  <s:if test="catalogs.size()>0">
				  	<ul>
					<s:iterator value="catalogs" id="c">
						<li>
							<span>
							<span class="glyphicon glyphicon-folder-open mfs" aria-hidden="true"></span>
							<a class="text-info" style="cursor:pointer;" href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${c.id}" data-index="${c.id }">${c.catalog}</a> 
							</span>
						</li>
					</s:iterator>
					</ul>
				   </s:if>
				   </li>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-8 col-md-9">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<form action="#" id="pics">
				<s:iterator value="pictures" id="p">
					<div class="col-xs-12 col-sm-6 col-md-4">
					    <div class="border">
							<a data-url="${p.url}" style="cursor:pointer;">
								<img src="${p.small}" class="img-responsive img-thumbnail">
							</a>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 text-center" style="margin-top:5px; margin-bottom:5px;">
							<input type="checkbox" name="ids" style="width: 20px;" value="${p.id}" onclick="checkone(this);">
						</div>
					</div>
				</s:iterator>
				</form>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
				<s:if test="pictures.size()>0">
					<nav>
					<ul class="pagination">
						<s:if test="page.totalPage>6">
						<s:if test="page.currentPage==1">
							
						</s:if>
						<s:else>
						<li><a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${page.currentPage-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a></li>
						</s:else>
							<s:if test="page.totalPage-page.currentPage>5">
								<s:iterator begin="page.currentPage" end="page.currentPage+5" var="p">
									<li <s:if test="#p==page.currentPage">class="active"</s:if>>
									<a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
									</li>
								</s:iterator>
									<li class="disabled"><a href="#">...</a></li>
							</s:if>
							<s:else>
								<s:iterator begin="page.totalPage-5" end="page.totalPage" var="p">
									<li <s:if test="#p==page.currentPage">class="active"</s:if>>
									<a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
									</li>
								</s:iterator>
							</s:else>
							<s:if test="page.currentPage>=page.totalPage">
							
							</s:if>
							<s:else>
								<li><a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${page.currentPage+1}" aria-label="Next">
							        <span aria-hidden="true">&raquo;</span>
							      </a>
								</li>
							</s:else>
							</s:if>
					<!-- totalpage<6  -->
					<s:else>
						<s:if test="page.currentPage==1">
						</s:if>
						<s:else>
						<li><a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${page.currentPage-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a></li>
						</s:else>
								<s:iterator begin="1" end="page.totalPage" var="p">
									<li <s:if test="#p==page.currentPage">class="active"</s:if>>
									<a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
									</li>
								</s:iterator>
							<s:if test="page.currentPage>=page.totalPage">
							</s:if>
							<s:else>
								<li><a href="<%=request.getContextPath()%>/upload/listPictureByOSS.action?catalogid=${catalogid}&page.currentPage=${page.currentPage+1}" aria-label="Next">
							        <span aria-hidden="true">&raquo;</span>
							      </a>
								</li>
							</s:else>
						</s:else>
					</ul>
					</nav>
				</s:if>
				</div>
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
					<form action="<%=request.getContextPath()%>/upload/uploadFileByOSS.action" id="f1" enctype="multipart/form-data" class="form-horizontal" method="post">
					   <input type="hidden" id="catalogid" name="catalogid" value="${pictureCatalog.id}">
						   <div class="form-group">
			           		 	<input type="file" class="form-control" name="images" multiple="multiple">
						   </div>
					  </form>
					  <div id="message">
				           		<div class="row">
						  	    <div class="progress">
								  <!-- <div class="progress-bar" id="pb" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
								    0% 
								  </div> -->
								</div>
									<!-- <div id="speed" style="padding-left: 5px;"></div> -->
									<div id="msg" style="padding-left: 5px;"></div>
								</div>
				           </div>
				</div>
				<div class="modal-footer">
		           <button type="submit" id="comfirm" class="btn btn-primary">上传</button>
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
					<form class="form" id="f2">
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
  	 
  	 <div class="modal fade" id="mymodal2" tabindex="-1" role="dialog" aria-labelledby="mymodal2" aria-hidden="true">
  		<div class="modal-dialog">
  			<div class="madal-content bgcolor">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title">移动到目录</h5>				
				</div>
				<div class="modal-body">
					<form class="form" id="f2">
						<div class="form-group">
						    <label class="control-label">目录名称</label>
						    <select class="form-control" id="cid" name="catalogid">
						    	<option value="0">ROOT</option>
						    	<s:iterator value="catalogs" id="c">
						    		<option value="${c.id }"> ${c.catalog} </option>
						    	</s:iterator>
						    </select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="yidong">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	  </div>
	  
	  <div class="modal fade" id="mymodal3" tabindex="-1" role="dialog" aria-labelledby="mymodal3" aria-hidden="true">
  		<div class="modal-dialog">
  			<div class="madal-content bgcolor">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title">设置图片的产品条码</h5>				
				</div>
				<div class="modal-body">
				<p class="text-warning">注意:每个产品不要选择超过20张图片,多出的则不能显示</p>
					<form class="form" id="f2">
						<div class="form-group">
						    <label class="control-label">选择产品</label>
						    <select class="form-control" id="barcode" name="barcode">
						    	<s:iterator value="infos" id="i">
						    		<option value="${i.barcode }"> ${i.pdesc} </option>
						    	</s:iterator>
						    </select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="setbarcode">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	  </div>
	  
	  <div class="modal fade" id="mymodal4" tabindex="-1" role="dialog" aria-labelledby="mymodal4" aria-hidden="true">
  		<div class="modal-dialog">
  			<div class="madal-content bgcolor">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title">图片预览</h5>			
				</div>
				<div id="imagepreview" class="modal-body">
					
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
			$(".border img").each(function(i){
				$(this).css("width",width);
				$(this).css("height",height);
				});
	  		};
	  		setimgsize();
				  		
            $("#mymodal").on("hidden.bs.modal", function (e) {
			  	$("#msg").html("");
			});
            $("#opener").on("click",function(e){
            	$("#mymodal").modal("show");
            });
            $("#opener1").on("click",function(e){
            	$("#mymodal1").modal("show");
            });
            $("#change").on("click",function(e){
            	$("#mymodal2").modal("show");
            });
            $("#changebarcode").on("click",function(e){
            	$("#mymodal3").modal("show");
            });
			$("#mymodal").on("hidden.bs.modal", function (e) {
			  	$("#msg").html("");
			});
        $("#tianjia").on("click",function(e){
            	e.preventDefault();
            	$.post("./upload/addCatalog.action",$("#f2").serialize(),function(data){
            		alert(data);
            		$("#mymodal1").modal("hide");
            		window.location.reload(true);
            	});
        });
            
        $("#yidong").on("click",function(e){
           	e.preventDefault();
           	var cid = $("#cid").val();
           	$.post("./upload/changeCatalog.action",$("#pics").serialize()+"&catalogid="+cid,function(data){
           		$("#mymodal2").modal("hide");
           		window.location.reload(true);
           	});
        });
        
        $("#delete").on("click",function(e){
           	e.preventDefault();
           	$.post("./upload/deleteImageByOSS.action",$("#pics").serialize(),function(data){
           		alert(data);
           		window.location.reload(true);
           	});
        });
        
        $("#setbarcode").on("click",function(e){
           	e.preventDefault();
           	var barcode = $("#barcode").val();
           	$.post("./upload/changeBarcode.action",$("#pics").serialize()+"&barcode="+barcode,function(data){
           		alert(data);
           		window.location.reload(true);
           	});
        });
        
        $("#comfirm").on("click",function(){
        	$("#f1").submit();
        });
        
        function showstatus(){
  		$.post("<%=request.getContextPath()%>/upload/progress.action",
			function(data,status){
				var obj = eval("("+data+")");
				//$("#pb").css("width",obj.precent+"%");
				//$("#pb").html(obj.precent+"%");
				//$("#speed").html(obj.speed+"kb/s");
				if(obj.message!="null"&&obj.message!=""){
					$("#msg").html(obj.message);
				}
			});	
	  	}
	  	
		$("#f1").submit(function(event){
		  	$("#comfirm").attr("display","none");
		  	$("input[type='file']").prop("readonly",true);
			window.setInterval(showstatus, 100);
		});
		
		$(".border a").on("click",function(e){
			e.preventDefault();
			var url = $(this).attr("data-url");
			$("#imagepreview").html("<img style='width:100%' src='"+url+"'>");
			$("#mymodal4").modal("show");
		});
		
        });
	</script>
</html>
