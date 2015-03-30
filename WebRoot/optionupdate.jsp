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
    <link rel="apple-touch-icon" href="icon.png">
  	<link rel="shortcut icon" href="favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  </head>
  	<script type="text/javascript">
  		$(function(){
  			$("#confirm").on("click",function(){
  				$.post("<%=request.getContextPath()%>/printoption/updateAjax.action",
	  			$("#f1").serialize(),
	  			function(data){
	  				if(data.indexOf("er")==0){
	  					alert("保存失败");
	  					return;
	  				}else{
	  					alert("操作成功");
	  					window.location.href = "<%=request.getContextPath()%>/printoption/toupdate.action";
	  					return;
	  				}
  			});
  			});
  			
  			$("#type").change(function(){
				var type = $(this).val();
				if(type.indexOf("custom")==0){
					$("#customwh").css("display","block");
				}else{
					$("#customwh").css("display","none");
				}
			});
			
			$("#view").on("click",function(){
				window.location.href = "<%=request.getContextPath()%>/optionview/viewprintinvoice.jsp";
			});
  		});
  	
  	
  	</script>
  <body>
  	 <div class="panel panel-default">
  		<div class="panel-heading">
  			<h4 class="panel-title">发票样式</h4>
  		</div>
  		  <div class="panel-body">
  			<div class="col-md-8 col-sm-8">
  			<form id="f1" action="<%=request.getContextPath()%>/printoption/updateAjax.action" class="form-horizontal col-md-12" role="form">
  				<input type="hidden" name="printOption.id" value="${printOption.id }">
  				<div class="form-group">
		  			<label for="companyName" class="control-label col-md-4 col-sm-4">发票大小</label>
		  			<div class="col-md-8 col-sm-8">
		  			<select id="type" name="printOption.invoicetype" class="form-control">
		  			<option value="a41ll" <s:if test="printOption.invoicetype=='a41ll'">selected="selected"</s:if>>A4纸全页</option>
		  			<option value="a4halfx2" <s:if test="printOption.invoicetype=='a4halfx2'">selected="selected"</s:if>>A4纸半页/2份</option>
		  			<option value="a4halfx1" <s:if test="printOption.invoicetype=='a4halfx1'">selected="selected"</s:if>>A4纸半页/1份</option>
		  			<option value="pos" <s:if test="printOption.invoicetype=='pos'">selected="selected"</s:if>>POS</option>
		  			<option value="custom" <s:if test="printOption.invoicetype=='custom'">selected="selected"</s:if>>自定义</option>
		  			</select>
		  			</div>
	  			</div>
  				<div id="customwh" class="form-group"  <s:if test="printOption.invoicetype=='custom'">style="display:block;"</s:if><s:else>style="display:none;"</s:else> >
		  			<div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4">
		  			<input type="text" name="printOption.customWidth" <s:if test="printOption.customWidth!=null">value="${printOption.customWidth}"</s:if>  placeholder="宽（mm）" class="form-control col-md-4 col-sm-4 text-center"/>
		  			</div>
		  			<div class="col-md-4 col-sm-4">
		  			<input type="text" name="printOption.customHeight" <s:if test="printOption.customHeight!=null"> value="${printOption.customHeight}"</s:if>  placeholder="高（mm）" class="form-control col-md-4 col-sm-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="companyName" class="control-label col-md-4 col-sm-4">公司名称</label>
		  			<div class="col-md-8 col-sm-8">
		  			<input type="text" name="printOption.companyName" value="${printOption.companyName }" class="form-control">
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="companyAddress" class="control-label col-md-4 col-sm-4">公司地址</label>
		  			<div class="col-md-8 col-sm-8">
		  				<input type="text" name="printOption.companyAddress" value="${printOption.companyAddress }" class="col-md-8 col-sm-8 form-control">
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="发票备注" class="control-label col-md-4 col-sm-4">发票备注</label>
		  			<div class="col-md-8 col-sm-8">
		  			<textarea name="printOption.otherremarks" class="col-md-8 col-sm-8 form-control">${printOption.otherremarks }</textarea>
		  			</div>
	  			</div>
	  			<!--  
	  			<h4 class="col-md-offset-3">坐标设置</h4>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">公司名称</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.companyNameXpoint" value="${printOption.companyNameXpoint }" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.companyNameYpoint" value="${printOption.companyNameYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">公司地址</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.companyAddressXpoint" value="${printOption.companyAddressXpoint }" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.companyAddressYpoint" value="${printOption.companyAddressYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">发票号码</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.invoiceNoXpoint" value="${printOption.invoiceNoXpoint }" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.invoiceNoYpoint" value="${printOption.invoiceNoYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">日期时间</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.invoicedateXpoint" value="${printOption.invoicedateXpoint }" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.invoicedateYpoint" value="${printOption.invoicedateYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">主内容</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.mainXpoint" value="${printOption.mainXpoint }" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.mainYpoint" value="${printOption.mainYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
  				<div class="form-group">
		  			<label for="" class="control-label col-md-4">发票备注</label>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.otherremarksXpoint" value="${printOption.otherremarksXpoint}" placeholder="x坐标" class="form-control col-md-4 text-center"/>
		  			</div>
		  			<div class="col-md-4">
		  			<input type="text" name="printOption.otherremarksYpoint" value="${printOption.otherremarksYpoint }" placeholder="y坐标" class="form-control col-md-4 text-center"/>
		  			</div>
	  			</div>
	  			-->
  				<div class="form-group">
  					<button type="button" id="confirm" class="btn btn-success col-md-offset-4 col-md-3 col-sm-offset-4 col-sm-3">确认</button>
  					<button type="button" id="view" class="btn btn-primary col-md-offset-2 col-md-3 col-sm-offset-2 col-am-3">预览</button>
	  			</div>
  			</form>
  			</div>
  			<div class="col-xs-12 col-md-4 col-sm-4 well">
  				<h4>提示：</h4>
  				<ul>
  					<li>关于发票，系统为大家定义了基本类型4种，如用户需自定义，可以选择“自定义”，然后输入发票“宽”mm，“高”mm，即可，若无需定义高度，只需将高度设置“-1”即可。</li>
  					<li>公司信息、公司地址、发票备注可选择填写。</li>
  				</ul>
  			</div>
  		</div>	
  			
  	</div>
  </body>
</html>
