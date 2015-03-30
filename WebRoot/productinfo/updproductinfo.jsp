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
    <title>--UPDATE PRODUCT-INFO--</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<style type="text/css">
		.modal-body row div{
			text-align:center
		}
		.panel-body img{
			width: 300px;
			height: 300px;
		}
		.modal-body img:hover{
			border: 2px solid #666;
		}
		.border{
			position: relative;
		}
		.edit{
	  		position:absolute;
	  		right:20px;
	  		bottom:10px;
	  		display: none;
	  	}
		@media screen and (max-width:768px){
			.panel-body img{
				width: 150px;
				height: 150px;
			}
		}
	</style>
  </head>

  <body>
  <div class="container-fluid">
  	<div class="row">
  		<div class="panel panel-info">
  			<div class="panel-heading">
  				<h3 class="panel-title">修改货品资料
  					<button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary clear-padding-height pull-right">返回</button>
  				</h3>
  			</div>
  			<div class="panel-body">
  			<form action="<%=request.getContextPath() %>/productinfo/updproductinfo.action" class="form-horizontal" method="post" autocomplete="off">
  				<input type="hidden" name="id"	value="${productinfo.id }">
  				<div class="col-xs-12 col-sm-12 col-md-8 pull-left">
  				<div class="form-group">
				   	<label class="col-xs-3 col-sm-2 col-md-2 control-label">产品编码*</label>
				   	<div class="col-xs-9 col-sm-10 col-md-10">
				   		<input class="form-control" id="barcode" name="barcode" type="text" value="${productinfo.barcode }"/>
					</div>
				</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品类型 * </label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<select class="form-control" name="ptype">
						<s:iterator id="pt" value="ptypes">
							<option <s:if test="#pt.typeName==productinfo.ptype">selected</s:if>  value="${pt.id}"> ${pt.typeName} </option>
						</s:iterator>
						</select>
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品名称 * </label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<input class="form-control" name="pdesc" type="text" value="${productinfo.pdesc }" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 牌子</label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<input class="form-control" name="pbrand" type="text" value="${productinfo.pbrand }" />  
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 颜色</label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<input class="form-control" name="pcolor" type="text" value="${productinfo.pcolor }" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 零售价</label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<input class="form-control" name="sprice" type="number" value="${productinfo.sprice }" id="sprice" onchange="checkQuan(this)" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 最低价</label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<input class="form-control" name="mprice" type="number" value="${productinfo.mprice }" id="mprice" onchange="checkQuan(this)" />
  		  			</div>
  		  		</div>
  		  		<div class="form-group">
  		  			<label class="col-xs-3 col-sm-2 col-md-2 control-label"> 产品介绍 </label>
  		  			<div class="col-xs-9 col-sm-10 col-md-10">
  		  				<textarea class="form-control" name="introduction" rows="3">${productinfo.introduction }</textarea>
  		  			</div>
  		  		</div>
				<div class="form-group">
					<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
						<div class="col-xs-9 col-sm-10 col-md-10">
						<button type="button" onclick="this.form.submit()" class="btn btn-success">确认</button>
						</div>
					</div>
				</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 pull-right text-center border">
					<s:if test="mainpictureurl!=null">
						<span style="display:block;margin-bottom: 5px;"><img class="img-thumbnail" style="width: 100%;height: auto;" src="${mainpictureurl}"></span>
					</s:if>
					<s:else>
						<span style="display:block;margin-bottom: 5px;"><img class="img-thumbnail" style="width: 100%;height: auto;" src="./images/im.png"></span>
					</s:else>
					<button type="button" class="btn btn-sm btn-success edit" id="opener">修改</button>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-8 pull-left">
					<div class="form-group">
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<p class="text-warning">${me}</p>
						</div>
					</div>
					<div class="form-group well">
						<label class="col-xs-3 col-sm-2 col-md-2 control-label">快捷操作提示</label>
						<div class="col-xs-offset-3 col-sm-offset-2 col-md-offset-2">
							<div class="col-xs-9 col-sm-10 col-md-10">
								<p class="text-info">快速复制此产品信息方法，若要录入的新产品设置与此产品一样或相差很少，只需修改有差异的项资料，然后（重要！）产品编码处（第一栏）录入新产品的编码，点击“确认”即可快速复制创建新产品信息</p>
							</div>
						</div>
					</div>
				</div>
   			</form>
   		  </div>
  		</div>
  	</div>
  </div>
  <div class="modal fade bs-example-modal-lg" role="dialog" id="modal">
	  <div class="modal-dialog modal-lg">
	  	<div class="modal-content">
	  		<div class="modal-header">
	  			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<h4 class="modal-title">选择主图片</h4>
	  		</div>
	  		<div class="modal-body">
	  		   <div class="row">
	  			<div class="col-xs-12 col-sm-4 col-md-4">
	  				<a data-index="" style="cursor: pointer;">
	  					<img class="mg-responsive img-thumbnail" src="./images/loading.gif">
	  				</a>
	  			</div>
	  			<div class="col-xs-12 col-sm-4 col-md-4">
	  				<a>
	  					<img class="mg-responsive img-thumbnail" src="./images/loading.gif">
	  				</a>
	  			</div>
	  			<div class="col-xs-12 col-sm-4 col-md-4">
	  				<a>
	  					<img class="mg-responsive img-thumbnail" src="./images/loading.gif">
	  				</a>
	  			</div>
	  			</div>
	  		</div>
	    </div><!-- conten -->
	  </div>  <!-- dialog -->
  </div> <!-- modal -->
  </body>
  <script type="text/javascript">
  $(function(){
  	$("#opener").on("click",function(){
  		var bar = $("#barcode").val();
  		window.location.href="<%=request.getContextPath()%>/upload/toChangeMainPicture.action?barcode="+bar;
  	});
  	$(".border").hover(function(e){
	  		e.preventDefault();
	  		$(this).find(".edit").css("display","block");
	  	},function(e){
	  		e.preventDefault();
	  		$(this).find(".edit").css("display","none");
	  });
  });
  </script>
</html>
