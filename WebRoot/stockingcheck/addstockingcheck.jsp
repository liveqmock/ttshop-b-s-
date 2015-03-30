<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<title>--库存盘点--</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css"href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<style type="text/css">
  	</style>
  </head>
  <body>
  <div class="container-fluid">
	 <div class="panel panel-default">
	  <div class="panel-heading">
	   <h3 class="panel-title">盘点数量修正单
	   		<button class="btn btn-primary clear-padding-height pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	   </h3>
	  </div>
	  <div class="panel-body">
   	  <s:form name="" namespace="" action="/checking/addChecking.action" id="f1" cssClass="form-horozontal" autocomplete="off">
   		<div class="col-xs-12 pull-left">
  				<div class="form-group">
   				<div class="input-group">
			    <div class="input-group-addon">盘点仓库</div>
   				<select name="warehouse.id" id="warehouse" class="form-control">
   				<s:iterator value="warehouses" id="w">
   					<option value="${w.id}">${w.wnickname}</option>
   				</s:iterator>
   				</select>
   				<div class="input-group-addon">锁定<input type="checkbox" id="isdefaultwarehouse" name="isdefaultwarehouse"></div>
   				</div>
  				</div>
  			<div class="form-group">
			<div class="input-group">
			    <div class="input-group-addon">产品名称*</div>
				<input type="text" class="form-control" name="desc" id="desc" value="" readonly="readonly"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
			    <div class="input-group-addon">产品编码*</div>
				<input type="text" class="form-control" name="barcode" id="barcode" value="" autofocus="autofocus" required="required"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
			    <div class="input-group-addon">盘点数量*</div>
				<input type="number" class="form-control" id="quantity" name="quantity" value="0" onchange="isNumber(this)" required="required"/>
				</div>
			</div>
   		</div>
   		<div class="col-xs-12 pull-left">
			<div class="form-group">
				<button type="button" id="sub" class="btn btn-sm btn-success">下一步</button>
				<button type="button" id="res" class="btn btn-sm btn-warning">清空</button>
			</div>
		</div>
		<s:token></s:token>
   	</s:form>
   	</div><!-- panelbody -->
   	</div><!--panel  -->
   </div><!-- container  -->
   <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   </div>	
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <SCRIPT type="text/javascript">
	$(function(){
		${resJson}
		$("#barcode").autocomplete({
  			minLength:1,
			source:infos,
			change:function(event,ui){
				$("#barcode").val(ui.item.value);
				$("#desc").val(ui.item.desc);
				getBarcodeInfo();
				return;
			}
	  	});
/* 		$("#barcode").on("change",function(){
			getBarcodeInfo();
		}); */
		
		function getBarcodeInfo(){
			var bar = $("#barcode").val();
			var warehouse = $("#warehouse").val();
			$.post('<%=request.getContextPath()%>/checking/findProductInfo.action',{barcode:bar,wid:warehouse},function(json){
			 	 if(json.indexOf("error")!=-1){
			 	 	if(json.indexOf("error02")!=-1){
				 	 	alert("没有找到此产品资料");
				 	 	return;
			 	 	}else if(json.indexOf("error01")!=-1){
			 	 		alert(json);
			 	 		return;
			 	 	};
			 	 }
			 	 var obj = eval("("+json+")");
			 	 $("#quantity").val(parseInt(obj.stock));
			 	 $("#desc").val(obj.desc);
			 	 return;
			});
		};
		
  		$("#barcode").on("keypress",function(e){
			if(event.keyCode==13){
				$("#quantity").focus();
			}
		});
		$("#quantity").on("keypress",function(e){
			if(event.keyCode==13){
				$("#add").click();
			}
		});
  	   $("#res").on("click",function(e){
  	   		e.preventDefault();
  	   		reset();
  	   });
  	   $("#sub").on("click",function(){
  	    	var f= $("#f1");
  	    	f.submit();
  	   });
  	   $("#isdefaultwarehouse").on("click",function(){
  			if($(this).prop("checked")){
  				var dw = $("#warehouse").val();
  				localStorage.defaultcheckingwarehouse = dw;
  			}else{
  				localStorage.defaultcheckingwarehouse = "";
  			};
  	   });
  	   function init(){
  			var dw = localStorage.defaultcheckingwarehouse;
  			$("option").each(function(i){
  				if($(this).val()==dw){
  					$(this).prop("selected",true);
  					$("#warwhouse").prop("readonly",true);
  					$("#isdefaultwarehouse").prop("checked",true);
  				}
  			});
  		};
  	   function reset(){
	    	$("input[name='barcode']").val("");
        	$("input[name='desc']").val("");
			$("input[name='quantity']").val(0);
			$("input[name='barcode']").focus();
  	   };
  	   init();
  	 });
  </SCRIPT>
</html>
