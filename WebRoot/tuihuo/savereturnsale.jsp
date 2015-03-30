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
    <title>-退货单开单-</title>
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
	.lh {
		height: 26px;
	}
	.pure-table td {
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
   <h3 class="panel-title">退货开单
   		<button class="btn btn-primary pull-right clear-padding-height" onclick="window.location.href='${returnurl}'">返回</button>
   </h3> 
  </div>
  <div class="panel-body">
	<form action="<%=request.getContextPath()%>/tuihuo/createreturnsale.action" id="f1" class="form" autocomplete="off">
	<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3 pull-left">
				<div class="form-group">
				  <div class="input-group">
				    <div class="input-group-addon">单号</div>
				    <input type="text" class="form-control" name="invoiceno" id="invoiceno" required>
				    <div class="input-group-addon">
						<button type="button" id="choose">选择退货项</button>
					</div>
				  </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">客户</div> 
				    <input type="text" class="form-control" name="customerid" value="" id="customer" readonly>
				    </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">仓库</div> 
				    <input type="text" class="form-control" name="warehousename" value="" id="warehouse" readonly>
				    </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">产品名称*</div> 
				    <input type="text" class="form-control" name="desc" id="desc" value="" readonly />
				    </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">产品编码*</div> 
				    <input type="text" class="form-control" name="barcode" id="barcode" value="" readonly />
				    </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">退货价*</div> 
				    <input type="number" class="form-control" id="price" name="price" value="0.0" onchange="checkQuan(this)" />
				    </div>
				</div>
				<div class="form-group">
					<div class="input-group">
				    <div class="input-group-addon">数量*</div> 
				    <input type="number" class="form-control" id="quantity" name="quantity" value="1" onchange="checkQuan(this)" />
					<input type="hidden" name="maxquantity" id="maxquantity">
					</div>
				</div>
				<div class="form-group">
					<button type="button" id="add" class="btn btn-sm btn-primary">加入列表</button>
					<button type="button" id="sub" class="btn btn-sm btn-success pull-right" style="margin-left: 5px;">保存</button>
					<button type="button" id="res" onclick="window.location.reload(true)" class="btn btn-sm btn-warning pull-right">清空</button>
			</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 pull-right">
				<div class="row postable">
				<table class="table table-bordered">
					<thead>
						<tr class="success">
							<td width="15%">产品条码</td>
							<td width="35%">产品描述</td>
							<td width="12.5%">数量</td>
							<td width="12.5%">退货价</td>
							<td width="12.5%">总额</td>
							<td width="12.5%">#</td>
						</tr>
					</thead>
					<tbody id="con">
						<s:iterator begin="1" var="1" end="20">
							<tr>
								<td class="lh" width="12.5%"></td>
								<td width="37.5%"></td>
								<td width="12.5%"></td>
								<td width="12.5%"></td>
								<td width="12.5%"></td>
								<td width="12.5%"></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				</div>
				<div class="row">
					<div class="input-group">
				    <div class="input-group-addon">原因</div>
						<textarea class="form-control" name="remarks" rows="2"></textarea>
					</div>
				</div>
				<div class="row well">
					<p>操作提示 :</p>
					<p>1. 退货单号及退货客户必须填写</p>
					<p>2. 退货原因限制100字内</p>
				</div>
			</div>
		<div id="dialog" title="选择要退货的产品">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="chooseitem"></div>
		</div>
	  </form>
	 </div><!-- panel-body  -->
	</div><!-- panel -->
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
  		${jsondata}
  		
		  	$("#barcode").autocomplete({
	  			minLength:1,
				source:infos,
				focus:function(event,ui){
					$("#barcode").val(ui.item.value);
					$("#desc").val(ui.item.desc);
					return false;
				}
	  		});
  			
  			$("#customer").autocomplete({
	  			minLength:2,
				source:customers,
				change:function(event,ui){
					$("#customer").val(ui.item.value);
					return false;
				}
  			 });
  			
  			$("#invoiceno").autocomplete({
	  			minLength:2,
				source:invoicenos,
				change:function(event,ui){
					$("#invoiceno").val(ui.item.value);
					$("#customer").val(ui.item.customerid);
					$("#warehouse").val(ui.item.warehouse);
					return false;
				}
  			 });
  			
  			$("#dialog").dialog({
  				autoOpen:false,
  				height:500,
  				width:700,
  				postion:[10,10],
  				modal:true,
  			});
  			
  			$("#choose").on("click",function(){
  				var no = $("#invoiceno").val();
  				if(no.trim()==""){
  					alert("请输入单号!");
  					return false;
  				}else{
  				$.post("<%=request.getContextPath()%>/tuihuo/getSaleLists.action",
  				{invoiceno:no},
  				function(data,status){
  					if(data.indexOf("error")!=-1){
  						alert("找不到该销售单数据!");
  					}else{
  						var content = "<table width=\"100%\">";
  						content+="<thead>";
  						content+="<tr>";
						content+="<td width=\"25%\">"+"产品条码"+"</td>";
						content+="<td width=\"25%\">"+"产品名称"+"</td>";
						content+="<td width=\"15%\">"+"该单数量"+"</td>";
						content+="<td width=\"15%\">"+"当时售价"+"</td>";
						content+="</tr>";
						content+="</thead>";
						var obj = eval("("+data+")");
						content+="<tbody>";
  						for ( var i = 0; i < obj.length; i++) {
							var sl = obj[i];
							content+="<tr>";
							content+="<td width=\"25%\">"+sl.barcode+"</td>";
							content+="<td width=\"25%\">"+sl.pdesc+"</td>";
							content+="<td width=\"15%\">"+sl.quantity+"</td>";
							content+="<td width=\"15%\">"+sl.price+"</td>";
							content+="<td width=\"15%\">"+"<button id=\"chooseone\">选中</button>"+"</td>";
							content+="</tr>";
						}
						content+="</tbody>";
						content+="</table>";
  						$("#chooseitem").html(content);
  						$("#invoiceno").prop("readonly",true);
  						$("#dialog").dialog("open");
  						}
  				});
  				};
  			});
  			
  			$("#chooseitem").on("click","button",function(){
  				var tds = $(this).parent().siblings();
  				var bar = tds.eq(0).html();
  				var desc = tds.eq(1).html();
  				var maxquantity = tds.eq(2).html();
  				var price = tds.eq(3).html();
  				$("#barcode").val(bar);
  				$("#price").val(price);
  				$("#desc").val(desc);
  				$("#maxquantity").val(maxquantity);
  				$("#dialog").dialog("close");
  			});
  			
  			$("#add").on("click",function(){
  				var bar = $("input[name='barcode']").val();
  				var desc =$("input[name='desc']").val();
				var qua = $("input[name='quantity']").val();
				var pri = $("input[name='price']").val();
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
						itemlist[i].price = parseFloat(pri);
						listAll(itemlist);
						reset();
						return;
					};
				}
				if (times<10) {
					var item = newItem(bar,desc,pri,qua);
					itemlist.push(item);
					listAll(itemlist);
					reset();
				}else{
					alert("此单已满，不能超过10行!");
				};
  			});
  		
  		$("#barcode").on("keypress",function(e){
  			var ba = $(this).val();
  			if(ba.trim()!=""){
				if(event.keyCode==13){
					$("#price").focus();
				};
			};
		});
		
  		$("#price").on("keypress",function(e){
			if(event.keyCode==13){
				$("#quantity").focus();
			}
		});
		
		$("#quantity").on("change",function(){
			var max = parseInt($("#maxquantity").val());
			var qua = parseInt($(this).val());
			if(qua>max){
				alert("退货的数量不能大于销售发票的数量！");
				$(this).val(1);
			};
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
  		
  		function newItem(barcode,desc,price,quantity){
			return eval('('+"{"+
						"\"barcode\":\""+barcode+"\","+
						"\"desc\":\""+desc+"\","+
						"\"price\":"+price+","+
						"\"quantity\":"+quantity+"}"+')');
	 	};
  		
  		function listAll(itemlist){
		 var trs = $("#con").find("tr");
		 for(var i=0;i<itemlist.length;i++){
			 var tds = trs.eq(i).find("td");
			 var barcode = itemlist[i].barcode;
			 var desc = itemlist[i].desc;
			 var quantity=itemlist[i].quantity;
			 var price=itemlist[i].price;
			 quantity = parseInt(quantity);
			 tds.eq(0).html("<input type='text' class='tdinput' readonly name='barcodes' value='"+barcode+"'/>");
			 tds.eq(1).html("<input type='text' class='tdinput' readonly name='descs' value='"+desc+"'/>");
			 tds.eq(2).html("<input type='text' class='tdinput' readonly value='"+quantity+"' name='quantitys'>");
			 tds.eq(3).html("<input type='text' class='tdinput' readonly value='"+parseFloat(price).toFixed(1)+"' name='prices'>");
			 tds.eq(4).html("<input type='text' class='tdinput' readonly value='"+quantity*parseFloat(price).toFixed(1)+"' name='amounts'>");
			 tds.eq(5).html("<button type='button' class='btn btn-xs btn-default' data-role='"+i+"' id='del'>删除</button>");
		 };
	   };
	 
  	   function reset(){
	    	$("input[name='barcode']").val("");
        	$("input[name='desc']").val("");
			$("input[name='quantity']").val(1);
			$("input[name='price']").val(0);
			$("input[name='barcode']").focus();
  	   };
  	   
  	   $("#sub").on("click",function(){
  	    	var f= $("#f1");
  	    	if(itemlist.length>0){
  	    		f.submit();
  	    	}else{
  	    		alert("资料有误，阻止提交！");
  	    	};
  	    });
  	 });
  </SCRIPT>
</html>
