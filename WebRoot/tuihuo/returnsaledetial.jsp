<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>-- 退货单 --</title>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<style type="text/css">
  		.print{margin-top: 10px;}
  	</style>
  </head>
  	<script type="text/javascript">
		$(function(){
		
			$(document).ajaxStart(function(e){
	        	var h = window.outerHeight;
	        	$("#zhezhao").css("height",h);
	        	$("#zhezhao").css("display","block");
	         }).ajaxStop(function(e){
	         	$("#zhezhao").css("display","none");
	         });
			
			$("#print").click(function(){
				window.print();
			});
			$("#dialog").dialog({
  				autoOpen:false,
  				height:500,
  				width:700,
  				modal:true,
  				position:[50,50],
  				buttons:{
  					"确认修改":function(){
  						$.post("<%=request.getContextPath()%>/tuihuo/updateReturnsaleAjax.action",
  						$("#f1").serialize(),
					   		function(data){
					   			if(data.indexOf("error")!=-1){
					   				alert(data);
					   				return;
					   			}else{
					   				alert(data);
					   				window.location.reload();
					   				return;
					   			}
					  	 });
  						$(this).dialog("close");
  					}
  				},
  			}); 
  			
  			$("#opener").on("click",function(){
  				$("#dialog").dialog("open");
  			});
		});
	</script>
  <body>
  <s:if test="returnSale.status==1">
  <div class="panel panel-default">
  	<div class="panel-heading hidden-print">
      <button class="btn btn-primary noprint" onclick="location.href='<%=request.getContextPath()%>/tuihuo/cancel.action?returnsaleno=${returnSale.returnsaleno}'">作废</button>
      <button class="btn btn-primary noprint" id="print">打印</button>
      <button type="button" onclick="window.location.href='${returnurl}'" class="btn btn-primary noprint" style="float:right;">返回</button>
  	</div>
  <div class="panel-body print-border">
  <div class="print">
  <div style="margin-bottom: 10px">
	    <div class="col-md-6 col-sm-6 col-xs-6">
    		<p> 退货单号：${returnSale.returnsaleno} </p>
    		<p> 仓库: ${returnSale.warehouse }  </p>
    		<p> 操作者:${returnSale.operatorid }</p>
    		<p> 客户: ${returnSale.customerid} </p>
    	</div>
    	<div class="col-md-6 col-sm-6 col-xs-6">
    		<p>&nbsp;</p>
    		<p> 数 量: ${returnSale.totalQuantity }  </p>
    		<p> 总 额: <fmt:formatNumber value="${returnSale.totalAmount }" type="currency" pattern="＄.0#"/>  </p>
    		<p> 退货时间: ${returnSale.updatetime} </p>
    	</div>
    </div>
    <div class="">
  	<table class="table">
    <thead>
    	<tr>
    	<td width="5%"># </td>
    	<td width="20%">产品编号 </td>
    	<td width="30%">产品名称</td>
    	<td width="10%">数量</td>
    	<td width="10%">单价</td>
    	<td width="20%">合计</td>
    	<td width="5%" class="noprint"><button id="opener" class="btn btn-xs btn-primary noprint">修改</button></td>
        </tr>
        </thead>
        <tbody>
    	<s:iterator value="objects" id="o" status="st">
    		<tr>
				<td> ${st.index+1 } </td>
				<td> ${o[2] } <input type="hidden" name="ids" value="${o[0]}">  </td>
				<td> ${o[3] } </td>
				<td> ${o[4] } </td>
				<td> <fmt:formatNumber value="${o[5] }" type="currency" pattern="＄.0#"/>  </td>
				<td> <fmt:formatNumber value="${o[6] }" type="currency" pattern="＄.0#"/>  </td>
				<td class="noprint"> </td>
    		</tr>
    	</s:iterator>
    </tbody>
    </table>
    </div>
    </div>
    <div id="dialog" title="修改退货货单">
    	<div class="row">
    	<s:form action="updateReturnsaleAjax" namespace="/tuihuo" id="f1" cssStyle="simple" cssClass="form-horizontal">
  		<input type="hidden" name="returnsaleno" value="${returnSale.returnsaleno}">
  		<table class="table" width="100%">
	  		<thead>
	  		<tr>
		  		<td width="25%">产品编码</td>
		  		<td width="25%">产品名称</td>
		  		<td width="25%">退货数量</td>
		  		<td width="25%">退货价格</td>
	  		</tr>
	  		</thead>
	  		<tbody>
	  		<s:iterator value="objects" id="in" >
	  		<tr>
		  		<td> <input type="hidden" name="ids" value="${o[0]}">
		  		<a>${in[2]}</a> </td>
		  		<td> ${in[3] } </td>
		  		<td> <input type="text" name="quantitys" value="${in[4] }" class="textcenter"> </td>
		  		<td> <input type="text" name="prices" value="${in[5] }" class="textcenter"> </td>
	  		</tr>
	  		</s:iterator>
	  		</tbody>
    	</table>
    	</s:form>
    	</div>
    </div>
   </div>
  </div>  
    </s:if>
    <s:else>
    	<button type="button" onclick="window.history.back()" class="btn btn-primary noprint" style="float:right;"> 返回  </button>
    	<div class="pure-u-1" style="text-align: center;">
    		<strong><a style="color: red;">该单已作废！</a></strong>
    	</div>
    </s:else>
    <div id="zhezhao" class="zhezhao">
   		<h3>网速不给力,正努力加载数据中...</h3>
   		<a><img style="width: 200px;height: 200px;" src="./images/loading.gif"></a>
   	</div>
  </body>
</html>
