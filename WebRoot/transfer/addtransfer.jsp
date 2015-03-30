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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>--调货开单--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css"href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
  	<style type="text/css">
  	.tdinput {
		text-align: center;
		border: none !important;
		padding: 0;
		margin:0;
		background-color: white !important;
		width: 98%;
		height: 100%;
	}
  	.lh{
  		height: 26px;
  		
  	}
  	.pure-table td{
  		padding: 0 !important;
  	}
	.input-group-addon{
  		padding: 0px 12px!important;
  	}
  	</style>
  </head>
  <body>
  <div class="container-fluid">
	 <div class="panel panel-default">
	  <div class="panel-heading">
	   <h3 class="panel-title">调货开单
	   		<button class="btn btn-primary clear-padding-height pull-right" onclick="window.location.href='${returnurl}'">返回</button>
	   </h3>
	  </div>
	  <div class="panel-body">
   	  <form action="<%=request.getContextPath()%>/transfer/addTransfer.action" id="f1" class="form" autocomplete="off">
   		<div class="col-xs-12 col-sm-12 col-md-3 pull-left">
  				<div class="form-group">
   				<div class="input-group">
			    <div class="input-group-addon">出货仓库</div>
   				<select name="warehouse2.id" id="warehouse" class="form-control">
   				<s:iterator value="warehouses" id="w">
   					<option value="${w.id}">${w.wnickname}</option>
   				</s:iterator>
   				</select>
   				</div>
  				</div>
  				<div class="form-group">
   				<div class="input-group">
			    <div class="input-group-addon">接收仓库</div>
   				<!--  <input class="pure-input-1" type="text" name="receiverid" required> -->
   				<select name="receiverid" id="receiverid" class="form-control">
   				<s:iterator value="warehouses" id="w">
   					<option value="${w.id}">${w.wnickname}</option>
   				</s:iterator>
   				</select>
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
				<input type="text" class="form-control" name="barcode" id="barcode" value="" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
			    <div class="input-group-addon">数量*</div>
				<input type="number" class="form-control" id="quantity" name="quantity" value="1" onchange="checkQuan(this)"/>
				</div>
			</div>
			<div class="form-group">
				<button type="button" id="add" class="btn btn-sm btn-primary">加入列表</button>
				<button type="button" id="sub" class="btn btn-sm btn-success pull-right" style="margin-left: 5px">保存</button>
				<button type="button" id="res" onclick="window.location.reload(true)" class="btn btn-sm btn-warning pull-right">清空</button>
			</div>
   		</div>
   		<div class="col-xs-12 col-sm-12 col-md-9 pull-right">
  				<div class="table-responsive postable">
  				<table class="table table-bordered">
				 <thead>
				 	<tr class="success">
				 		<td width="12.5%">产品条码</td>
				 		<td width="62.5%">产品描述</td>
				 		<td width="12.5%">数量</td>
				 		<td width="12.5%">#</td>
				 	</tr>
				 </thead>
				 <tbody id="con">
						<s:iterator begin="1" var="1" end="10">
				 			<tr>
								 <td class="lh"></td>
								 <td></td>
								 <td></td>
								 <td></td>
         						</tr>
				 		</s:iterator>
				 </tbody>
			</table>
			</div>
			<div class="form-group">
   				<div class="input-group">
			    <div class="input-group-addon">调货原因</div>
   				<textarea class="form-control" name="reason" rows="2"></textarea>
   				</div>
  				</div>
  				<div class="form-group well">
				<p>操作提示 :</p>
				<p>1."发货者"和"接收者"必须不相同，否则无法提交</p>
			</div>
   		</div>
   	</form>
   	</div><!-- panelbody -->
   	</div><!--panel  -->
   </div><!-- container  -->
   <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
    <div class="salelist">
   		<ul>
 		  <li><a href="javascript:" id="jumpup"><span class="glyphicon glyphicon-circle-arrow-up"></span></a></li>
 		  <li><a href="javascript:" id="jumpdown"><span class="glyphicon glyphicon-circle-arrow-down"></span></a></li>
 		</ul>
   	</div>		
  </body>
  <SCRIPT type="text/javascript">
	$(function(){
		var itemlist = new Array();
		var times = 0;
  		${resJson}
		  	$("#barcode").autocomplete({
	  			minLength:1,
				source:infos,
				focus:function(event,ui){
					$("#barcode").val(ui.item.value);
					$("#desc").val(ui.item.desc);
					return false;
				}
	  		});
  			
  			$("#add").on("click",function(){
  				var bar = $("input[name='barcode']").val();
  				var desc =$("input[name='desc']").val();
				var qua = $("input[name='quantity']").val();
				//var pri = $("input[name='price']").val();
				desc = desc.replace("'","-");
				if(bar==""){
					alert("请输入产品条码!");
					$("input[name='barcode']").focus();
					return;
				}
				for ( var i = 0; i < itemlist.length; i++) {
					var tempbarcode = itemlist[i].barcode;
					if(tempbarcode.indexOf(bar)!=-1){
						itemlist[i].quantity = parseInt(qua)+parseInt(itemlist[i].quantity);
						//itemlist[i].price = parseFloat(pri);
						listAll(itemlist);
						reset();
						return;
					};
				}
				if (times<10) {
					var item = newItem(bar,desc,qua);
					itemlist.push(item);
					listAll(itemlist);
					reset();
				}else{
					alert("此单已满，不能超过10行!");
				};
  			});
  		
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
		$("table").delegate("button","click",function(e){
				var datarole = $(this).attr("data-role");
				$("#con").find("td").html(""); //point!
  	   			itemlist.splice(datarole,1); // point!
				listAll(itemlist);
		});
  		
  		function newItem(barcode,desc,quantity){
			return eval('('+"{"+
						"\"barcode\":\""+barcode+"\","+
						"\"desc\":\""+desc+"\","+
						//"\"price\":"+price+","+
						"\"quantity\":"+quantity+"}"+')');
	 	};
  		
  		
  		function listAll(itemlist){
		 var trs = $("#con").find("tr");
		 for(var i=0;i<itemlist.length;i++){
			 var tds = trs.eq(i).find("td");
			 var barcode=itemlist[i].barcode;
			 var desc=itemlist[i].desc;
			 var quantity=itemlist[i].quantity;
			// var price=itemlist[i].price;
			 quantity = parseInt(quantity);
			 tds.eq(0).html("<input type='text' class='tdinput' readonly name='barcodes' value='"+barcode+"'/>");
			 tds.eq(1).html("<input type='text' class='tdinput' readonly name='descs' value='"+desc+"'/>");
			 tds.eq(2).html("<input type='text' class='tdinput' readonly value='"+quantity+"' name='quantitys'>");
			// tds.eq(3).html("<input type='text' class='tdinput' readonly value='"+parseFloat(price).toFixed(1)+"' name='prices'>");
			 tds.eq(3).html("<button type='button' class='btn btn-xs btn-default ' data-role='"+i+"' id='del'>删除</button>");
		 };
	   };
	 
  	   function reset(){
	    	var bar = $("input[name='barcode']").val("");
        	var des = $("input[name='desc']").val("");
			var qua = $("input[name='quantity']").val(1);
			//var pri = $("input[name='price']").val(0);
			$("input[name='barcode']").focus();
  	   };
  	   
  	   
   		$("#warehouse").on("change",function(){
   			var w = $(this).val();
   			var r = $("#receiverid").val();
   			if(w==r){
   				alert("请选择其它的接收仓库！");
   			};
   		});
  	   
  	    function checkWarehouse(){
  	    	var r = $("#receiverid").val();
  	    	var w = $("#warehouse").val();
   			if(w==r){
   				return false;
   			};
   			return true;
  	    };
  	    
  	    $("#sub").on("click",function(){
  	    	var f= $("#f1");
  	    	if(checkWarehouse() && itemlist.length>0){
  	    		f.submit();
  	    	}else{
  	    		alert("资料有误，阻止提交！");
  	    	};
  	    });
  	 });
  </SCRIPT>
</html>
