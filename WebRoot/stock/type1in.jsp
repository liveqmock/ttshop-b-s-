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
    <title>--产品入库--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<link rel="stylesheet" type="text/css"href="<%=request.getContextPath()%>/css/default.css">
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
  	</style>
  </head>

  <body>
  <div class="container-fluid">
  <div class="panel panel-default">
  	<div class="panel-heading">
  		<h3 class="panel-title">入货开单
  			<button class="btn btn-primary clear-padding-height pull-right" onclick="window.location.href='${returnurl}'">返回</button>
  		</h3>
  	</div>
  	<div class="panel-body">
	<s:form action="type1in" namespace="/inbound"  method="post" id="f1" theme="simple" cssClass="form" autocomplete="off">
		<div class="col-xs-12 col-sm-12 col-md-3 pull-left">
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">供货商</div>
					<SELECT class="form-control" name="supplierid">
					<s:iterator value="suppliers" id="s" >
						<option value="${s.supplierId}">${s.supplierName }</option>
					</s:iterator>
					</SELECT>
				</div>	
			</div>
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">仓库</div>
					<SELECT class="form-control" id="warehouse" name="warehouseid">
					<s:iterator value="warehouses" id="w" >
						<option value="${w.id}">${w.wnickname }</option>
					</s:iterator>
					</SELECT>
				</div>	
			</div>
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">产品名称</div>
					<input type="text" class="form-control" name="desc" id="desc" readonly="readonly"/>
					<!--  <div class="input-group-addon"><a id="stock" style="color:green;"></a></div> -->
				</div>	
			</div>
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">产品编码</div>
					<input type="text" class="form-control" name="barcode" id="barcode" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">数量</div>
					<input type="number" class="form-control" id="quantity" name="quantity" value="1" onchange="checkQuan(this)"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
				    <div class="input-group-addon">入货价</div>
					<input type="number" class="form-control" id="price" name="price" value="0" onchange="check(this)"/>
				</div>
			</div>
			<div class="form-group">
				<button type="button" id="add" class="btn btn-sm btn-primary">加入列表</button>
				<button type="button" onclick="checkform('f1','barcodes')" class="btn btn-sm btn-success pull-right" style="margin-left: 5px">保存</button>
				<button type="button" id="res" onclick="window.location.reload(true)" class="btn btn-sm btn-warning pull-right">清空</button>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-9 pull-right">
		 <div class="row postable">
		 	<table class="table table-bordered">
					 <thead>
					 	<tr class="success">
					 		<td  width="12.5%">产品条码</td>
					 		<td  width="37.5%">产品描述</td>
					 		<td  width="12.5%">数量</td>
					 		<td  width="12.5%">价格</td>
					 		<td  width="12.5%">合计</td>
					 		<td  width="12.5%">#</td>
					 	</tr>
					 </thead>
					 <tbody id="con">
					 	<s:if test="#session.tempinbound!=null">
					 		<s:iterator value="#session.tempinbound" id="t">
								<tr>
									 <td class="lh">
									 <input type='text' class=' tdinput' readonly name='barcodes' value='${t.barcode }'/>
									 </td>
									 <td><input type='text' id="pdescs" class=' tdinput' readonly name='descs' value='${t.imei }'/></td>
          							 <td><input type='text' id="quantitys" class=' tdinput' readonly name='quantitys' value='${t.quantity }'/></td>
          							 <td><input type='text' id="prices" class=' tdinput' readonly name='prices' value='${t.price }'/></td>
          							 <td><input type='text' id="amounts" class=' tdinput' readonly name='amounts' value='${t.price*t.quantity }'/></td>
          							 <td><button type='button' class='btn btn-xs btn-default' id='del'>删除</button></td>
          						</tr>
					 		</s:iterator>
					 		<s:iterator begin="#session.tempinbound.size()" var="1" end="15">
					 			<tr>
									 <td class="lh"></td>
									 <td></td>
									 <td></td>
									 <td></td>
									 <td></td>
									 <td></td>
          						</tr>
					 		</s:iterator>
					 	</s:if>
						<s:else>
							<s:iterator begin="1" var="1" end="15" id="s">
					 			<tr>
									 <td class="lh"></td>
									 <td></td>
									 <td></td>
									 <td></td>
									 <td></td>
									 <td></td>
          						</tr>
					 		</s:iterator>
						</s:else>
					 </tbody>
				</table>
				</div>
				<div class="row well">
				<p>操作提示：</p>
				<p>选择“供货商”及入货“仓库”，然后产品编码栏录入编码，数量，价格后点击“添加产品”至右边列表，待全部录入完毕后点击“保存”</p>
				</div>
			</div>
		<div>
		<s:token></s:token>
		</div>
	</s:form>
	</div> <!-- panelbody  -->
	</div> <!-- panel  -->
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
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
  <SCRIPT type="text/javascript">
	$(function(){
	
		var itemlist = new Array();
		inniItemlist();
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
						$.post('<%=request.getContextPath()%>/inbound/tempInboundaddquanity.action',{barcode:bar,quantity:qua,price:pri,desc:desc});
						return;
					};
				}
				if (times<15) {
					var item = newItem(bar,desc,pri,qua);
					itemlist.push(item);
					listAll(itemlist);
					$.post('<%=request.getContextPath()%>/inbound/tempInbound.action',{barcode:bar,quantity:qua,price:pri,desc:desc});
					reset();
				}else{
					alert("此单已满，不能超过15行!");
				};
  			});
  		
  		$("#barcode").on("keypress",function(e){
			if(event.keyCode==13){
				$("#quantity").focus();
			}
		});
		
		$("#barcode").on("change",function(){
			var bar = $(this).val();
			var warehouse = $("#warehouse").val();
			$.post('<%=request.getContextPath()%>/productinfo/findProductInfo.action',{barcode:bar,warehouse:warehouse},function(json){
				 	 if(json.indexOf("error")!=-1){
				 	 	if(json.indexOf("error02")!=-1){
				 	 		var r = window.confirm("没有找到此产品资料,是否现在添加？");
					 	 	if(r){
					 	 		window.location.href="<%=request.getContextPath()%>/productinfo/listproductinfo.action";
					 	 	}else{
					 	 		$("#barcode").val("");
					 	 		return;
					 	 	};
				 	 	}else if(json.indexOf("error01")!=-1){
				 	 		alert(json);
				 	 		return;
				 	 	};
				 	 }
				 	 var obj = eval("("+json+")");
				 	// $("#stock").html("剩余库存:"+obj.stock);
				 	 $("#desc").val(obj.desc);
				 	 return;
				});
		});
		
		$("#quantity").on("keypress",function(e){
			if(event.keyCode==13){
				$("#price").focus();
			}
		});
		$("#price").on("keypress",function(e){
			if(event.keyCode==13){
				$("#add").click();
			}
		});
		$("table").delegate("button","click",function(){
				var datarole = $(this).attr("data-role");
				var tds = $(this).parent().parent().children();
				var bar = tds.eq(0).children().val();
	  		  	var desc = tds.eq(1).children().val();
				var qua = tds.eq(2).children().val();
				var pri = tds.eq(3).children().val();
				$.post('<%=request.getContextPath()%>/inbound/removetempInbound.action',{barcode:bar,quantity:qua,price:pri,desc:desc});
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
			 tds.eq(0).html("<input type='text' class=' tdinput' readonly name='barcodes' value='"+barcode+"'/>");
			 tds.eq(1).html("<input type='text' class=' tdinput' readonly name='descs' value='"+desc+"'/>");
			 tds.eq(2).html("<input type='text' class=' tdinput' readonly value='"+quantity+"' name='quantitys'>");
			 tds.eq(3).html("<input type='text' class=' tdinput' readonly value='"+parseFloat(price).toFixed(1)+"' name='prices'>");
			 tds.eq(4).html("<input type='text' class=' tdinput' readonly value='"+parseFloat(price*quantity).toFixed(1)+"' name='amounts'>");
			 tds.eq(5).html("<button type='button' class='btn btn-xs btn-default' data-role='"+i+"' id='del'>删除</button>");
		 };
	 };
  	   function reset(){
	    	var bar = $("input[name='barcode']").val("");
        	var des = $("input[name='desc']").val("");
			var qua = $("input[name='quantity']").val(1);
			var pri = $("input[name='price']").val(0);
			$("input[name='barcode']").focus();
  	   };
  	   
  	   	function inniItemlist(){
			var trs = $("#con").find("tr");
			for ( var i = 0; i < trs.length; i++) {
				 var tds = trs.eq(i).find("td");
				 var b=tds.eq(0).html();
				 var de=tds.eq(1).html();
				 var qu=tds.eq(2).html();
				 var pr=tds.eq(3).html();
				 var am=tds.eq(4).html();
				 if(b.trim()!="" && qu.trim()!="" && pr.trim()!=""){
					 var barcode=tds.eq(0).find("input").val();
					 var desc=tds.eq(1).find("input").val();
					 var quantity=tds.eq(2).find("input").val();
					 var price=tds.eq(3).find("input").val();
					 var temp = newItem(barcode,desc,price,quantity);
					 itemlist.push(temp);
				};
			};
			times = itemlist.length;
	 };
  	   
  	 });
  </SCRIPT>
</html>
