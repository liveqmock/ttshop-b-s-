<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <title>--TTBeautyShop--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webshop/css/webshop.css">
	<style type="text/css">
		.modal-backdrop.in{
			opacity:0!important;
			-webkit-opacity:0!important;
			-moz-opacity:0!important;
		}
		#carlist li a{
			text-align: left;
		}
		#carlist li div{
			float: right;
		}
		#carlist li input{
			border: none;
			box-shadow:0px;
			width: 3em;
			height: 2em;
			max-height: 2em;
			padding: 0;
			margin:0;
			color: #000;
			text-align: center;
		}
	</style>
  </head>
  <body>
	<div class="container box">
		<nav class="navbar">
		 	<div class="container">
		 		<div class="navbar-header">
		 		  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>
			      <a class="navbar-brand"><span>TTBeautyShop</span></a>
			    </div>
			    <div class="collapse navbar-collapse">
			    	<form action="<%=request.getContextPath()%>/webshop/towebshop.action" class="navbar-form pull-right" role="search">
		    			<div class="input-group input-group-sm">
		    				<input type="text" class="form-control" name="keyword" value="${keyword}">
		    			</div>
		    				<button class="btn btn-sm btn-default navbar-btn">搜索</button>
			    	</form>
			    	<ul class="nav navbar-nav pull-right">
			    		<li><a href="#">美妆教程</a></li>
			    		<li><a href="#">护肤课堂</a></li>
			    		<li><a href="#">联系我们</a></li>
			    	</ul>
			    </div>
		 	</div>
		 </nav>
		 <div class="row" id="shopping">
		 		<div id="productbox" class="col-xs-12 col-sm-12 col-md-12 pull-left">
		 			<s:if test="productInfos.size()>0">
		 			<s:iterator value="productInfos" id="p">
		 			<div class="col-xs-12 col-sm-6 col-md-3">
		 				<div class="thumbnail box">
		 					<s:if test="#p.image.small!=null">
		 						<img class="responsive" src="${p.image.small}" alt="${p.pdesc }">
		 					</s:if>
		 					<s:else>
		 						<img class="responsive" src="./images/im.png" alt="${p.pdesc }">
		 					</s:else>
		 					<div class="caption">
			 					<p class="text-info title"><a data-role="desc" data-value="${p.id}" href="javascript:">${p.pdesc }</a></p>
			 					<p class="text-danger title">售价: ${p.sprice } 元</p>
			 					<div class="desc title">
	       					   		<a href="javascript:" class="text-success"><nobr>
	       					   		<s:if test="#p.introduction!=null||p.introduction!=''">
	       					   			${p.introduction}
	       					   		</s:if>
	       					   		<s:else>
	       					   			该产品未设置任何描述
	       					   		</s:else>
	       					   		</nobr></a>
	       					    </div>
	       						<div class="button">
		       						<a class="btn btn-warning addtocar" role="button" data-value="${p.id}">加入购物车</a>
		       						<a id="like" class="btn" role="button" data-value="${p.id}">
		       							<span class="glyphicon glyphicon-heart-empty">999</span>
		       						</a>
	       						</div>
		 					</div>
		 				</div>
		 			</div>
		 			</s:iterator>
		 			</s:if>
		 			<s:else>
		 				<p class="text-danger text-center">💔还没有上架的商品💔</p>
		 			</s:else>
		 		</div>
		 		<div class="col-xs-12 col-sm-12 col-md-12 pull-left text-center" id="loading">
		 			<img style="width: 30px;height: 30px;" src="./images/loading.gif">
		 		</div>
		 </div>
		 <div class="shoppingcar" id="shoppingcar">
			<ul>
 				<li><a href="javascript:" id="userbox" title="用户"><span class="glyphicon glyphicon-user"></span></a></li>
 				<li style="position: relative;">
 					<a href="javascript:" id="carbox" title="购物车">
 					<span class="glyphicon glyphicon-shopping-cart"></span>
 					<span class="badge badge-warning" style="font-size: 8px!important;background-color:orange;position: absolute;right: 0;top: 0;">
 					${car.lists.size()}</span></a>
 				</li>
 				<li><a href="javascript:" id="scrolltop" title="顶端"><span class="glyphicon glyphicon-upload"></span></a></li>
 			</ul>
 			<div class="userbox">
 				<s:if test="1==1">
 					您还未登录
 				</s:if>
 				<s:else>
 					<ul>
 						<li>客户名</li>
 						<li>客户名</li>
 					</ul>
 				</s:else>
 			</div>
 			<div class="carbox">
 				<s:if test="1==1">
 					<p>购物清单</p>
 					<ul id="carlist">
 				    </ul>
 				    <ul>
 				    	<li><p id="total" style="float: right;"></p></li>
 				    </ul>
 				    
 				</s:if>
 				<s:else>
 					您还没有选购任何商品
 				</s:else>
 			</div>
		</div>
		<div class="modal fade" id="desc" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header"> 
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">商品详细</h4>
					</div>
					<div class="modal-body"> 
						
					</div>
					<div class="modal-footer">
						<a class="btn btn-warning">加入购物车</a>
					</div>
				</div>
			</div>
		</div>
		<footer class="footer">
			<div class="container-fluid clearfix">
		    	<div class="text-center">
		    		<span class="text-info" style="border-radius:0 ">Copyright &copy; 2015 david_du1987@126.com</span>
		    	</div>
	  		</div>
	   	</footer>
	   	
    </div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/myhtml5fileupload.js"></script>
  <script type="text/javascript">
  $(function(){
  	$(document).ajaxStart(function(e){
    	$("#loading").css("display","block");
     }).ajaxStop(function(e){
     	$("#loading").css("display","none");
     });
  	var p = 2; //初始化已经加载了第一页，由2开始
  	$(window).scroll(function(){
  		var bodyheight = $("body").outerHeight();
  		var windowsheight = $(window).height();
  		var scrolltop = $("body").scrollTop();
  		if(scrolltop >= bodyheight-windowsheight){
  			var key = $("input[name='keyword']").val();
  			var url = '<%=request.getContextPath()%>/webshop/moreproduct.action?currentPage='+p+'&keyword='+key;
  			$.post(url,function(data){
  				var obj = eval('('+data+')');
  				addcontent(obj);
  				p++;
  			});
  		}
  	});
  	
  	$("#scrolltop").on("click",function(e){
  		e.preventDefault();
		scrollTopest();
	});
  	
  	$("#userbox").on("click",function(e){
  		e.preventDefault();
  		var flag = $(".userbox").css("display");
  		initbox();
  		if(flag=="none"){
  			$(".userbox").css("display","block");
  		}else{
  			$(".userbox").css("display","none");
  		}
  	});
  	
  	$("#carbox").on("click",function(e){
  		e.preventDefault();
  		var flag = $(".carbox").css("display");
  		initbox();
  		if(flag=="none"){
  			var url = "<%=request.getContextPath()%>/webshop/getCarInfo.action"; 
  			$.post(url,function(data){
  				if(data.indexOf("no data")!=-1){
  					
  				}else{
	  				var html = "";
	  				var infos = eval("("+data+")");
	  				var total = 0.0;
	  				for(var i= 0;i<infos.length;i++){
	  					var info = infos[i];
	  					html+="<li><a>"+(i+1)+"."+info.desc+"</a>";
	  					html+="<div><input type='number' name='quantity' value='"+info.quantity+"'>";
	  					html+="*"+info.sprice+"="+info.amount+"</div>";
	  					html+="</li>";
	  					total=total+info.amount;
	  				}
	  				$("#total").html("合计:"+total+"元");
	  				$("#carlist").html(html);
  				}
  			});
  			$(".carbox").css("display","block");
  		}else{
  			$(".carbox").css("display","none");
  		}
  	});
  	
  	function addcontent(json){
  		var html = "";
  		if(json.length==0){
  			//alert("💔没有更多了💔");
  		}
  		for (var i = 0 ;i<json.length;i++) {
			object = json[i];
			html+="<div class=\"col-xs-12 col-sm-6 col-md-3\">";
			html+="<div class=\"thumbnail box\">";
			if(object.small!="null"){
				html+="<img class=\"responsive\" src=\""+object.small+"\" alt=\""+object.pdesc+"\">";
			}else{
				html+="<img class=\"responsive\" src=\"./images/im.png\" alt=\""+object.pdesc+"\">";
			};
			html+="<div class=\"caption\">";
			html+="<p class=\"text-info title\"><a data-role=\"desc\" data-value=\""+object.id+"\" href=\"javascript:\"><nobr>"+object.pdesc+"</nobr></a></p>";
			html+="<p class=\"text-danger title\"> 售价: "+object.sprice+" 元</p>";
			html+="<div class=\"desc title\">";
			if(object.introduction!='null'){
	       		html+="<a href=\"javascript:\" class=\"text-success\"><nobr>"+object.introduction+"</nobr></a></div>";
			}else{
				html+="<a href=\"javascript:\" class=\"text-success\"><nobr>"+"该产品未设置任何描述"+"</nobr></a></div>";
			}
	       	html+="<div class=\"button\"><a href=\"javascript:\" class=\"btn btn-warning addtocar\" role=\"button\" data-value=\""+object.id+"\">加入购物车</a>";
		    html+="<a href=\"javascript:\" class=\"btn\" role=\"button\" data-value=\""+object.id+"\">";
		   	html+="<span class=\"glyphicon glyphicon-heart-empty\">999</span></a></div>";
		   	html+="</div></div></div>";
		}
		$("#productbox").append(html);
  	};
  	
    $("#productbox").delegate("a[data-role='desc']","click",function(e){
    	e.stopPropagation();
  		var id = $(this).attr("data-value");
  		var url = "<%=request.getContextPath()%>/webshop/getonedetail.action?id="+id;
  		$.post(url,function(data){
  			var p = eval('('+data+')');
  			var html = "";
  			html+='<div class="name">';
			html+='<h5 class="text-info">'+p.pdesc+'</h5>';
			html+='</div>';			
			html+='<div class="introduction" style="border-bottom:1px solid #eee">';
			if(p.introduction!="null"){
				html+='<p>'+p.introduction+'</p>';
			}else{
				html+='<p>该商品未设置任何描述</p>';
			}
			html+='</div>';
			html+='<div class="images">';
			html+='<div style="width: 100%;">';
			var pics = p.images;
			if(pics.length>0){
				for(var i=0;i<pics.length;i++){
					html+='<a target="viewer" href="./webshop/viewer.action?url=';
					html+=pics[i];
					html+='" title="';
					html+=p.pdesc;
					html+='"><img style="border:1px solid #eee;margin:5px;" src="';
					html+=pics[i];
					html+='" alt="';
					html+=p.pdesc;
					html+='"></a>';
				}
			}else{
				html+='<p>该商品未上传图片</p>';
			}
			html+='</div>';
			html+='<div class="price" style="border-top:1px solid #eee">';
			html+='<h5 class="text-danger">优惠价:¥ <span>'+p.sprice+'</span> </h5>';
			html+='</div>';
  			$(".modal-body").html(html);
			$(".modal-body").responsiveimage(300);
  			$("#desc").modal("show");
  		});
  	});	
  	
  	function addtocar(data){
  		if(data.isFinite()){
  			$(".badge").html(data);
	  		return;
	  	}else{
	  		$(".badge").html("");
	  		return;
	  	}
  	};
  	
  	$("#shopping").delegate("a[class*='addtocar']","click",function(e){
  		e.preventDefault();
  		var v = $(this).attr("data-value");
  		$.post("<%=request.getContextPath()%>/webshop/addToCar.action",
  			{addp_id:v},function(data){
  				addtocar();
  		});
  		
  	});
  	
	function initbox(){
		$(".carbox").css("display","none");
		$(".userbox").css("display","none");
	}
	
	function scrollTopest(){
		$("body").scrollTop(0);
	};
	
	
	
	
  });  
  </script>
</html>
