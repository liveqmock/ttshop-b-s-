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
    <title>-- 销售发票 --</title>
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<style>
	*{
		font-size: 2mm;
	}
  	<s:if test="#session.printOption.invoicetype=='pos'">
  	.invoice{
		width:56mm;
		border-bottom: 1px dashed black;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4all'">
  	.invoice{
		width:210mm;
		height:290mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4halfx2'">
  	.invoice{
		width:210mm;
		height:140mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4halfx1'">
  	.invoice{
		width:210mm;
		height:145mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='custom'">
  	.invoice{
		width:${printOption.customWidth}mm;
		height:${printOption.customHeight}mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
  	table thead tr {
  		height: 20px;
  	}
  	table {
  		border: none;
  		table-layout: fixed;
  	}
  	.content{
  		overflow: hidden;
  	}
  	table thead td{
		border-bottom: thin dashed black;
		border-top: thin dashed black;
	}
	table tfoot{ border-top: thin dashed black; }
  	.underline { border:none; }
  	.invoice-header{
  		border: none;
  		width: 100%;
  	}
  	.onleft{ text-align: left; }
  	.oncenter { text-align: center; }
  	.onright { text-align: right; }
  	.quantity,.price,.amount,.discount{
  		width: 80%;
  		text-align:center;
  		border: none;
  	}
  	.company-logo{ border: none; }
  	.invoice-logo{
  		width: 100%;
  		display:block;
  		text-align: center;
  		font-weight: bold;
  	}
  	.chock-place{
  		height: 10%;
  		border-bottom: thin dashed #000;
  	}
  	.company-info{ display:block;}
  	.company-name{
		font-size: 12px;
		font-weight: bold;  		
  	}
  	.imeistyle{
  	 	word-wrap:break-word;
  	}
  	.invoicebody{
  		overflow: hidden;
  	}
  	.invoicebody td{
  		height: 5mm;
  	}
  	</style>
  	<style media="print">
	<s:if test="#session.printOption.invoicetype=='pos'">
  	.invoice{
		width:56mm;
		border-bottom: 1px dashed black;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4all'">
  	.invoice{
		width:210mm;
		height:290mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4halfx2'">
  	.invoice{
		width:210mm;
		height:140mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='a4halfx1'">
  	.invoice{
		width:210mm;
		height:145mm;
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	<s:if test="#session.printOption.invoicetype=='custom'">
  	.invoice{
		width:${printOption.customWidth}mm;
		<s:if test="#session.printOption.customHeight<=-1">	
		</s:if>
		<s:else>
		height:${printOption.customHeight}mm;
		</s:else>
		border-bottom: 1px dashed black;
		overflow: hidden;
	}
	</s:if>
	.noprint{
		display: none;
	}
	.hide-print{
		visibility: hidden;
	}
	table{
		border: none;
	}
	table thead td{
		border-bottom: thin dashed black;
		border-top: thin dashed black;
	}
	table tfoot{
		border-top: thin dashed black;
	}
	.invoicebody{
  		overflow: hidden;
  	}
  	.invoicebody td{
  		height: 5mm;
  	}
  	</style>
	<script>
		$(function(){
			$("#print").click(function(){
				window.print();
			});
		});
	</script>
  </head>
  
  <body>
  	<div class="container-fluid">
  	<div class="row noprint">
		<s:if test="saleRecord.status==0">
		<button class="btn btn-primary" id="print" >打印</button>
		</s:if>
		<s:if test="saleRecord.status==1">
		<button class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/sale/cancelByInvoice.action?keyword=${saleRecord.invoiceno}'" > 作废  </button>
		<button class="btn btn-primary" id="print" > 打印  </button>
		</s:if>
		<button type="button" onclick="window.history.back()" class="btn btn-primary noprint" style="float:right;"> 返回  </button>
	</div>
 	<div class="row invoice" >
  	  <!-- 设置发票信息-->
	  <!-- 设置发票信息-->
	  	<div >
	  		<div style="width: 100%">
	  			<div style="width: 100%">
		  			<span class="company-info company-name">${printOption.companyName}</span>
		  			<span class="company-info">${printOption.companyAddress}</span>
	  			</div>
	  		</div>
	  		<span class="invoice-logo"> INVOICE </span>
	  	</div>
  		<!-- 发票主要内容 -->
	    <div style="width: 100%">
		    <table class="invoice-header">
		    	<!--  <tr>
				    <td class="pure-u-1-2" style="text-align: left;">
				    	 ${saleRecord.operatorid }
				    </td>
		    	</tr>
		    	-->
			    <tr>
				    <td style="text-align: left;">
					    ${saleRecord.invoiceno }
					    <s:if test="saleRecord.status==0"> （作废） </s:if>
					</td>
			    </tr>
			    <tr>
				    <td class="onleft" style="width: 50%">
				     	 ${saleRecord.customerid }
				    </td>
			    </tr>
			    <tr>
				    <td style="text-align: left;width: 50%">
				      	 ${saleRecord.createtime} 
				    </td>
			    </tr>
		    </table>
		    </div>
		    <!-- 发票内容 -->
		<div class="invoicebody" style="width:100%">
		    <table width="100%">
			    <thead>
			    <tr>
			    <td class="oncenter" style="width:5%">项</td>
			    <td class="oncenter" style="width:45%">产品</td>
			    <td class="oncenter" style="width:13.5%">数量</td>
			    <td class="oncenter" style="width:16.5%">单价</td>
			    <td class="oncenter" style="width:20%">总额</td>
			    </tr>
			    </thead>
			    <tbody>
			    <s:iterator value="recordLists" id="s">
			    <tr>
			    <td class="oncenter">${s.invoiceline }  </td>
			    <td class="onleft">${s.pdesc } </td>
			    <td class="oncenter">${s.quantity }</td>
			    <td class="oncenter">${s.price }</td>
			    <td class="oncenter">${s.amount }</td>
			    </tr>
			    </s:iterator>
			    </tbody>
		    </table>
		    </div>
		    <div style="width:100%">
		    <table style="width:100%">
		    <tfoot>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">优惠:</td>
			    <td class="oncenter">${-saleRecord.discount}</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">打折:</td>
			    <td class="onleft">${saleRecord.discountrate}%</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">总额:</td>
			    <td class="oncenter">${saleRecord.totalamount }</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">收钱:</td>
			    <td class="oncenter">${saleRecord.pay }</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">找零:</td>
			    <td class="oncenter">${saleRecord.cashchange }</td>
			    </tr>
			    </tfoot>
			</table>
			</div>
			<s:if test="saleRecord.remark!=''">
				<div style="width:100%">
					<span>备注:${saleRecord.remark}</span>
				</div>
			</s:if>
			<div style="width:100%">
				<a>${printOption.otherremarks}</a>
			</div>
	 </div>
	 <s:if test="#session.printOption.invoicetype=='a4halfx2'">
	 <div class="pure-u-1 invoice" style="margin-top: 5mm;">
  		<!-- 设置发票信息-->
	  <!-- 设置发票信息-->
	  	<div style="width:100%">
	  		<div style="width:100%">
	  			<div class="pure-u-1-1">
		  			<span class="company-info company-name">${printOption.companyName}</span>
		  			<span class="company-info">${printOption.companyAddress}</span>
	  			</div>
	  		</div>
	  		<span class="invoice-logo"> INVOICE </span>
	  	</div>
  		<!-- 发票主要内容 -->
	    <div style="width:100%">
		    <table class="invoice-header">
		    	<!--  <tr>
				    <td class="pure-u-1-2" style="text-align: left;">
				    	 ${saleRecord.operatorid }
				    </td>
		    	</tr>
		    	-->
			    <tr>
				    <td style="text-align: left;">
					    ${saleRecord.invoiceno }
					    <s:if test="saleRecord.status==0"> （作废） </s:if>
					</td>
			    </tr>
			    <tr>
				    <td class="pure-u-1-2 onleft">
				     	 ${saleRecord.customerid }
				    </td>
			    </tr>
			    <tr>
				    <td class="pure-u-1-2" style="text-align: left;">
				      	 ${saleRecord.createtime} 
				    </td>
			    </tr>
		    </table>
		    </div>
		    <!-- 发票内容 -->
		<div class="pure-u-1 invoicebody">
		    <table style="width:100%">
			    <thead>
			    <tr>
			    <td class="pure-u-1-24 oncenter">项</td>
			    <td class="pure-u-11-24 oncenter">产品</td>
			    <td class="pure-u-3-24 oncenter">数量</td>
			    <td class="pure-u-4-24 oncenter">单价</td>
			    <td class="pure-u-5-24 oncenter">总额</td>
			    </tr>
			    </thead>
			    <tbody>
			    <s:iterator value="recordLists" id="s">
			    <tr>
			    <td class="oncenter">${s.invoiceline }  </td>
			    <td class="onleft">${s.pdesc } </td>
			    <td class="oncenter">${s.quantity }</td>
			    <td class="oncenter">${s.price }</td>
			    <td class="oncenter">${s.amount }</td>
			    </tr>
			    </s:iterator>
			    </tbody>
		    </table>
		    </div>
		    <div style="width:100%">
		    <table style="width:100%">
		    <tfoot>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">优惠:</td>
			    <td class="oncenter">${-saleRecord.discount}</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">打折:</td>
			    <td class="onleft">${saleRecord.discountrate}%</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">总额:</td>
			    <td class="oncenter">${saleRecord.totalamount }</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">收钱:</td>
			    <td class="oncenter">${saleRecord.pay }</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td></td>
			    <td></td>
			    <td class="onleft">找零:</td>
			    <td class="oncenter">${saleRecord.cashchange }</td>
			    </tr>
			    </tfoot>
			</table>
			</div>
			<s:if test="saleRecord.remark!=''">
				<div style="width:100%">
					<span>备注:${saleRecord.remark}</span>
				</div>
			</s:if>
			<div style="width:100%">
				<a>${printOption.otherremarks}</a>
			</div>
	 </div>
	 </s:if>
  </div>
  </body>
</html>
