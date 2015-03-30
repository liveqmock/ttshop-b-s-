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
  	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">
    <title>--POS--</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pos.css">
  	<style type="text/css">
  	.lh{
  		height: 30px!important;
  		line-height: 30px!important;
  		vertical-align: middle;	
  	}
  	.table td{
  		padding: 0 !important;
  	}
  	.input-group-addon{
  		padding: 0px 12px!important;
  	}
  	.form-control{
  		padding:0 !important;
  		height: auto !important;
  	}
  	.hide{
  		display:none; 
  	}
	.show{
		display: inline-block;
	}
  </style>
  </head>
  <body>
  	<div class="container-fluid">
	<div class="row-fluid">
  	<form  action="./retial/createAdeal.action" id="f1" class="form-horizontal" method="post" target="printinvoice" autocomplete="off">
  		<div class="col-xs-12 col-sm-12 col-md-3 pull-left">
  			<fieldset>
    	  	<div class="form-group col-xs-12 col-sm-12 col-md-12">
    	  		<div class="input-group">
	    	  	<div class="input-group-addon">货币</div>
	    	  	<div>
		    	  	<select class="form-control" name="currency">
					<option value="RMB">RMB</option>
					</select>
				</div>
				</div>
			</div>
			<div class="form-group col-xs-12 col-sm-12 col-md-12">
    	  		<div class="input-group">
	    	  	<div class="input-group-addon">付款</div>
					<select class="form-control" name="paidtype">
					<option value="0" selected="selected">现金</option>
					<option value="1">刷卡</option>
					<option value="2">签单</option>
					<option value="3">支票</option>
					<option value="4">其它</option>
					</select>
				</div>
			</div>
			<div class="form-group col-xs-12 col-sm-12 col-md-12">
    	  		<div class="input-group">
		    	  	<div class="input-group-addon">仓库</div>
					<select class="form-control" id="warehouse" name="warehouse2.id">
					<s:iterator value="warehouses" id="w">
					<option value="${w.id}">${w.wnickname }</option>
					</s:iterator>
					</select>
					<div class="input-group-addon">锁定<input type="checkbox" id="isdefaultwarehouse" name="isdefaultwarehouse"></div>
				</div>	
			</div>
			<div class="form-group col-xs-12 col-sm-12 col-md-12">
    	  		<div class="input-group">
		    	  	<div class="input-group-addon">客户</div>
    	  			<input type="text" class="form-control" name="customer" value="Customer" id="customer" required>
    	  		</div>
    	  	</div>
			</fieldset>
			<fieldset>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    			<div class="input-group">
	    		   		<label>产品录入 
	    		   		<img id="img" class="hide" style="width: 15px;height: 15px" src="./images/loading.gif"> </label>
	    		   </div>
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">名称</div> 
			    	  	<input type="text" class="form-control" style="border: 1px silver solid;background-color: #eee;font-weight: bold;color: green;" id="pdesc" name="pdesc" readonly="readonly">
			    	 </div>
			    	 <div class="input-group">
			    	  	<a id="stock"></a>
			    	 </div>
    	  		</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">条码</div>
			    	  	<input type="text" class="form-control" id="barcode" name="barcode" autofocus>
			    	  	<input type="hidden" id="imei" name="imei">
		    	  	</div>
    	  		</div>
	    	  	<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">单价</div> 
			    	  	<input type="number" class="form-control" id="price" name="price" value="0.0" onchange="check(this)">
			    	  	<input type="hidden" id="mprice" name="mprice" value="0">
			    	 </div> 	
	    		</div>
	    	  	<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">数量</div> 
			    	  	<input type="number" class="form-control" id="quantity" name="quantity" value="1" onchange="checkQuan(this)">
	    				<input type="hidden" id="maxquantity" name="maxquantity">
	    			</div>
	    		</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
		    	  		<button type="button" id="add" class="btn btn-default btn-primary clear-padding-height">添加至列表</button>
		    	  	</div>
		    	</div>
		    </fieldset>
		    <fieldset>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    		   <div class="input-group">
	    		   		<label>收银</label>
	    		   </div>
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">折扣</div>
		    	  		<input type="number" min="0" max="100" class="form-control" style="font-weight: bold;color:blue;"  id="discount" name="discount" value="0" onchange="check(this)">
		    		</div>
		    	</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">折扣率</div>
		    	  		<input type="number" min="0" max="100" class="form-control" style="font-weight: bold;color:blue;"  id="discountrate" name="discountrate" value="100" onchange="check(this)">
		    			<div class="input-group-addon"><A style="color: red;">%</A></div>
		    		</div>
		    	</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">总数</div>
		    	  		<input type="number" class="form-control" style="font-weight: bold;color: green;" id="totalquantity" name="totalquantity" value="0" onchange="check(this)" readonly="readonly">
		    		</div>
		    	</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">总额</div>
		    	  		<input type="number" class="form-control" style="font-weight: bold;color: green;" id="totalamount" name="totalamount" value="0" onchange="check(this)" readonly="readonly">
		    		</div>
		    	</div>
		    	<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">收银</div>
		    	  		<input type="number" min="0" max="100" class="form-control" style="font-weight: bold;color:blue;" id="pay" name="pay" value="0" onchange="check(this)">
		    		</div>
		    	</div>
	    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
	    	  		<div class="input-group">
			    	  	<div class="input-group-addon">找零</div>
		    	  		<input type="number" class="form-control" style="font-weight: bold;color: green;" id="change" name="change" value="0" onchange="check(this)" readonly="readonly">
		    		</div>
		    	</div>
    		</fieldset>
	    </div>
    	<div class="col-xs-12 col-sm-12 col-md-9 mtop list pull-right">
    	  <div class="row postable">
			<table class="table table-bordered">
				<thead>
    			<tr class="success">
    				<td class="lh" width="18%">产品条码</td>
    				<td class="lh" width="40%">产品名称</td>
    				<td class="lh" width="6%">备注</td>
    				<td class="lh" width="9%">数量</td>
    				<td class="lh" width="9%">单价</td>
    				<td class="lh" width="9%">总数</td>
    				<td class="lh" width="9%">#</td>
    			</tr>
    			</thead>
    			<tbody id="content">
    				<s:if test="recordLists!=null">
    					<s:if test="saleRecord!=null">
    						<input type="hidden" id="hanpupid" name="hanpupid" value="${saleRecord.invoiceno}"/>
    					</s:if>
	    				<s:iterator value="recordLists" var="line">
		    				<tr>
		    				<td class="list-height lh" width="18%"><input type='text'  readonly value='${line.barcode}' name='barcodes'></td>
		    				<td  width="40%"><input type='text'  readonly value='${line.pdesc}' name='pdescs'></td>
		    				<td  width="6%"><input type='text'  readonly value='${line.remark}' name='imeis'></td>
		    				<td  width="9%"><input type='text'  readonly value='${line.quantity}' name='quantitys'></td>
		    				<td  width="9%"><input type='text'  readonly value='${line.price}' name='prices'></td>
		    				<td  width="9%"><input type='text'  readonly value='${line.amount}' name='amounts'></td>
		    				<td  width="9%"><button class='btn btn-default clear-padding-height clear-padding-width' type='button'>删除</button></td>
		    				</tr>
	    				</s:iterator>
	    				<s:iterator begin="recordLists.size()" end="11" step="1">
		    				<tr>
		    				<td class="list-height lh" width="18%"></td>
		    				<td  width="40%"></td>
		    				<td  width="6%"></td>
		    				<td  width="9%"></td>
		    				<td  width="9%"></td>
		    				<td  width="9%"></td>
		    				<td  width="9%"></td>
		    				</tr>
	    				</s:iterator>
    				</s:if>
    				<s:else>
    				<s:iterator begin="0" end="11" step="1">
	    				<tr>
	    				<td class="list-height lh" width="18%"></td>
	    				<td  width="40%"></td>
	    				<td  width="6%"></td>
	    				<td  width="9%"></td>
	    				<td  width="9%"></td>
	    				<td  width="9%"></td>
	    				<td  width="9%"></td>
	    				</tr>
    				</s:iterator>
    				</s:else>
    			</tbody>
    		</table>
    		</div>
    		<div class="row">
    		<div>
    			<label>备注:</label>
    			<textarea class="form-control" rows="3" name="remark"></textarea>
    		</div>
    		<div>
    			<div class="well">
    				<label>操作提示:</label>
    				<p>1、客户栏：输入客户（可在客户栏输入客户电话、名称搜索客户Id）</p>
    				<p>2、条码栏：输入产品编码（可输入名称搜索），若要修改价钱和数量，请修改后点击“添加”录入右边表格</p>
    				<p>3、折扣栏：输入优惠的实数，例如优惠了5元，打5即可；折扣率栏：输入相应的折扣优惠，若果无折扣，保持100即可，同理9折即90</p>
    				<p>4、提交前请输入收银数，核对找零数</p>
    			</div>
    		</div>
    	  </div>
    	</div>
    	<div class="col-xs-12 col-sm-12 col-md-3 pull-left">
    		<div class="form-group col-xs-12 col-sm-12 col-md-12">
 		    	<button type="button" class="btn btn-sm btn-success" id="comfirm">提交</button>
   				<button type="button" class="btn btn-sm btn-danger" id="cancel">清空</button>
				<button type="button" class="btn btn-sm btn-warning" id="handup">挂起</button>
				<button type="button" class="btn btn-sm btn-info" id="recover">恢复</button>
    		</div>
    	</div>
    	<input type="hidden" id="token" name="token">
    	</form>
  		</div>  <!-- panelbody -->
      </div>	<!-- panel  -->
    <div id="dialog" title="选择挂起的单">
    	<div id="dialogcontent" class="col-xs-12 col-sm-12 col-md-12">
    	
    	</div>
    </div>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/pos.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/basefn.js"></script>
<script>
  	$(function(){
  		${jsondata}
  		function init(){
  			var dw = localStorage.defaultwarehouse;
  			$("option").each(function(i){
  				if($(this).val()==dw){
  					$(this).prop("selected",true);
  					$("#warwhouse").prop("readonly",true);
  					$("#isdefaultwarehouse").prop("checked",true);
  				}
  			});
  		}
  		init();
  		
  		function inittoken(){
  			$("#token").val(Math.random()*100000000000000000);
  		}
  		inittoken();
  		$("#isdefaultwarehouse").on("click",function(){
  			if($(this).prop("checked")){
  				var dw = $("#warehouse").val();
  				localStorage.defaultwarehouse = dw;
  			}else{
  				localStorage.defaultwarehouse = "";
  			};
  		});
  		$("#warehouse").on("change",function(){
  			if($("#isdefaultwarehouse").prop("checked")){
			  	var dw = $("#warehouse").val();
			  	localStorage.defaultwarehouse = dw;
			}else{
				localStorage.defaultwarehouse = "";
			};
  		});
  		
  		$("#customer").autocomplete({
  			minLength:2,
			source:customers,
			change:function(event,ui){
				$("#customer").val(ui.item.value);
				$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/customer/findDiscount.action",
				data:"cid="+ui.item.value,
				contentType:"application/x-www-form-urlencoded;charset=utf-8",
				success:function(data){
					if(data.indexOf("error")==0){
						alert(data);
					}else{
						var obj = eval('('+data+')');
						$("#discountrate").val(obj.discountrate);
						$("#stock").html("");
					};
					return;
				}
			});
			return false;
			}
  		 });
  		 
  		 $("#customer").on("change",function(){
			var cid = $(this).val();
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/customer/findDiscount.action",
				data:"cid="+cid,
				contentType:"application/x-www-form-urlencoded;charset=utf-8",
				success:function(data){
					if(data.indexOf("error")==0){
						if(data.indexOf("error01")==0){
							alert("客户Id有误");
							$("#customer").val("Customer");
							$("#discountrate").val(100);
						}else if(data.indexOf("error02")==0){
							alert("找不到此客户");
							$("#customer").val("Customer");
							$("#discountrate").val(100);
						}
					}else{
						var obj = eval('('+data+')');
						$("#discountrate").val(obj.discountrate);
						$("#stock").html("");
					};
					return;
				}
			});
		 });
  			
  		$("#barcode").autocomplete({
  			minLength:1,
			source:infos,
			change:function(event,ui){
				$("#barcode").val(ui.item.value);
				$("#pdesc").val(ui.item.desc);
				$("#quantity").val(1);
				$("#price").val(ui.item.price);
				$("#mprice").val(ui.item.mprice);
				$("#imei").val("");
				var bar = $(this).val();
			 	var warehouse = $("#warehouse").val();
  				$.post('<%=request.getContextPath()%>/retial/findStock.action',{barcode:bar,warehouse:warehouse},function(json,status){
				 	 if(json.indexOf("error")!=-1){
				 	 	if(json.indexOf("error03")!=-1){
				 	 		alert("此仓库没有此商品库存信息,请先去入货!");
				 	 	}else if(json.indexOf("error02")!=-1){
				 	 		var r = window.confirm("没有此产品资料,是否现在添加？");
					 	 	if(r){
					 	 		window.location.href="<%=request.getContextPath()%>/productinfo/listproductinfo.action";
					 	 	};
				 	 	}else if(json.indexOf("error01")!=-1){
				 	 		alert("请选择仓库,若没有请先创建一个仓库!");
				 	 	};
				 	 	$("#stock").html("");
				 	 	return;
				 	 }
				 	 var obj = eval("("+json+")");
				 	 $("#stock").html("剩余库存:"+obj.stock);
				 	 $("#maxquantity").val(obj.stock);
				 	 return;
			 	});
			 }
  		});
  		
		 $("#barcode").on("change",function(){
			var bar = $(this).val();
			var warehouse = $("#warehouse").val();
			$.post('<%=request.getContextPath()%>/retial/findStock.action',{barcode:bar,warehouse:warehouse},function(json){
				 	if(json.indexOf("error")!=-1){
				 	 	if(json.indexOf("error03")!=-1){
				 	 		alert("此仓库没有此商品库存信息,请先去入货!");
				 	 	}else if(json.indexOf("error02")!=-1){
				 	 		var r = window.confirm("没有此产品资料,是否现在添加？");
					 	 	if(r){
					 	 		window.location.href="<%=request.getContextPath()%>/productinfo/listproductinfo.action";
					 	 	};
				 	 	}else if(json.indexOf("error01")!=-1){
				 	 		alert("请选择仓库,若没有请先创建一个仓库!");
				 	 	};
				 	 	$("#stock").html("");
				 	 	return;
				 	 }
				 	 var obj = eval("("+json+")");
				 	 $("#stock").html("剩余库存:"+obj.stock);
				 	 $("#pdesc").val(obj.desc);
					 $("#quantity").val(1);
					 $("#price").val(obj.price);
					 $("#mprice").val(obj.mprice);
					 $("#imei").val("");
					 $("#maxquantity").val(obj.stock);
					 return;
				});
		 });
		
		$("#dialog").dialog({
			autoOpen:false,
			height:400,
			width:900,
			modal:true,
			position:[50,50],
  		 }); 
		
		$("#handup").on("click",function(e){
			e.preventDefault();
			if($("input[name='barcodes']").size()<=0){
				alert("没有资料!");
				return;
			}
			var form = $("#f1");
			$.post("<%=request.getContextPath()%>/retial/handupAdeal.action",
				form.serialize(),
				function(data){
					if(data.indexOf("error")!=-1){
						alert(data+"出错请检查!");
						return;
					}else{
						alert("此单已挂起操作");
						window.location.href = "<%=request.getContextPath() %>/retial/topos.action";
						return;
					}
				});
		});
		
		$("#recover").on("click",function(e){
			e.preventDefault();
			var warehosueid = $("#warehouse").val();
			$.post("<%=request.getContextPath()%>/retial/listHandup.action",
					{warehosueid:warehosueid},
					function(data){
						if(data.indexOf("error")==0){
							if(data.indexOf("error01")==0){
								alert("没有找到该仓库！请联系管理人员！");
							}else if(data.indexOf("error02")==0){
								alert("请选择仓库！");
							}else if(data.indexOf("error03")==0){
								alert("没有挂起的销售单！");
							}else{
								alert("系统出错，未知错误！请联系管理员");
							}
							return;
						}else{
							var obj = eval('('+data+')');
							$("#dialogcontent").html("");
							$("#dialogcontent").append("<table>");
							$("#dialogcontent").append("<thead>");
							$("#dialogcontent").append("<tr>");
							$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+"号码"+"</td>");
							$("#dialogcontent").append("<td style='width:100px;text-align:center'>"+"客户"+"</td>");
							$("#dialogcontent").append("<td style='width:100px;text-align:center'>"+"数量"+"</td>");
							$("#dialogcontent").append("<td style='width:100px;text-align:center'>"+"总额"+"</td>");
							$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+"时间"+"</td>");
							$("#dialogcontent").append("<td style='width:100px;text-align:center'>"+"#"+"</td>");
							$("#dialogcontent").append("</tr>");
							$("#dialogcontent").append("</thead></tbody>");
							for(var i =0 ;i < obj.length ; i++){
								var sr = obj[i];
								$("#dialogcontent").append("<tr>");
								$("#dialogcontent").append("<td style='width:200px;text-align:center'>"
								+"<a style='color:blue' href='<%=request.getContextPath()%>/retial/recoverAdeal.action?id="+sr.invoiceno+"'>"
								+sr.invoiceno+"</a>"
								+"</td>");
								$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+sr.customer+"</td>");
								$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+sr.totalquantity+"</td>");
								$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+sr.totalamount+"</td>");
								$("#dialogcontent").append("<td style='width:200px;text-align:center'>"+sr.date+"</td>");
								$("#dialogcontent").append("<td style='width:100px;text-align:center'>"
								+"<a href='<%=request.getContextPath()%>/retial/cancelAdeal.action?hanpupid="+sr.invoiceno+"'>"
								+"删除</a>"
								+"</td>");
								$("#dialogcontent").append("</tr>");
							}
							$("#dialogcontent").append("</tbody></table>");
							$("#dialog").dialog("open");
					};
					return;
				});
		});
		
  	});
  </script>
</html>
