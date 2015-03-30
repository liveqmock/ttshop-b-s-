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
    
    <title>--STORE-INBOUND--</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pure.css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/checktool.js"></script> 
  </head>
  <script type="text/javascript">
        $(function(){  
        	${resJson}
           
           var index = 0;
           $("input:text").keypress(function(event){  
                if(event.keyCode== 13){
                	$(this).next().focus();
                	if($(this).next().index()>2 || $(this).next().index()==-1 ){
                		index++;
                		var bs =  $("input[name='barcodes']");
                		$(bs).eq(index).focus();
                	}
                }
            });
           $("input:text").focus(function(event){
           		var c = $(this).attr("tabindex");
           		index = c;
           });
           
           $("input[name='barcodes']").each(function(i){
            	$(this).change(function(){
            		var bar = $(this).val();
            		$.post("<%=request.getContextPath()%>/productinfo/getbarcode.action",{barcode:bar},
					   	function(data){
				   			if(data.trim()!=""){
				   				$("a[name='descs']").eq(i).html(data);
				   				return;
				   			}
				   			var r = confirm(bar+"没有资料，是否现在添加？");
				   			if(r==true){
				   				window.location = "<%=request.getContextPath()%>/productinfo/toaddproductinfo.action";
				   			}
						}
					);
            	});
            });
           
           $("input[name='barcodes']").each(function(i){
           		$(this).autocomplete({
  				minLength:2,
				source:infos,
				focus:function(event,ui){
					$(this).val(ui.item.value);
					$("a[name='descs']").eq(i).html(ui.item.label);
					return false;
				}
  			});
           });
           
           
        });    
    </script>
  <body> 
	 <s:form action="storeInbound" namespace="/inbound" method="post" cssClass="pure-form pure-form-aligned">
		<fieldset>
	 	<legend> 门市入货  <a style="color: red;">${me} </a> </legend>
	 		<div class="pure-u-1-4">
	 		<div class="pure-u-1">
			<label class="pure-u-1">供货商 *</label>
			<SELECT class="pure-u-1-2" name="supplierid">
			<s:iterator value="suppliers" id="s" >
				<option value="${s.supplierId}">${s.supplierName }</option>		
			</s:iterator>
			</SELECT>
			<a style="display: block;">&nbsp;</a>
			</div>
			<div class="pure-u-1">
			<label class="pure-u-1">仓库 * </label>
			<SELECT class="pure-u-1-2" name="warehouse">
			<s:iterator value="warehouses" id="w" >
				<option value="${w.wname}">${w.wname }</option>
			</s:iterator>
			</SELECT>
			<a style="display: block;">&nbsp;</a>
			</div>
			<div class="pure-u-1">
	 		<button type="button" onclick="this.form.submit()" class="pure-button pure-button-primary pure-u-1-2"> 确    认   </button>
	 		</div>
			</div>
			<div class="pure-u-2-3">
		<div class="pure-control-group pure-u-1">
	 	<s:iterator step="1" begin="0" end="19" var="index">
	 	<div class="pure-u-1">
		<input type="text" name="barcodes" tabindex="${index}" class="pure-u-1-4"  placeholder="Barcode">
		<input type="text" name="imeis" tabindex="${index}" class="pure-u-1-4"  placeholder="Imei">
		<a name="descs" style="color: green;display:block;vertical-align: middle;padding-left: 2em;" class="pure-u-1-4">&nbsp;</a>
		</div>
		</s:iterator>
		</div>
	 	</div>
		</fieldset>
		<s:token></s:token>
	 </s:form>
  </body>
</html>
