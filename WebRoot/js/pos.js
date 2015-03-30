$(function(){
	 var times = 0;  //记录录入行数
	 var maxtimes = 12; //设置一单最大行数
	 var itemlist = new Array(); //item 明细
	 inniItemlist(); //调用挂起单的时候,读取表格信息写入 itemlist
	 $("#quantity").on("keypress",function(event){  
	 	 //32 space键, 13 enter键
         if(event.keyCode==13){
        	 var obj = $(this);
        	 if(checknum(obj)){
        		$("#add").click();
        	 }
         }
     });
	 $("#barcode").on("keypress",function(e){
		if(event.keyCode==13){
			$("#price").focus();
		}
	 });
	 $("#price").on("keypress",function(e){
		if(event.keyCode==13){
			$("#quantity").focus();
		}
	 });
	 $("#customer").on("keypress",function(e){
	 	if(e.keyCode==13){
			$("#barcode").focus();
		}
	 });
	 
	 $("#add").on("click",function(e){
	 		e.preventDefault();
	 		var totalquantity = $("#totalquantity");
	 		var totalamount = $("#totalamount");
         	var barcode = $("#barcode").val();
         	var desc = $("#pdesc").val();
         	var quantity = $("#quantity").val();
			var price = $("#price").val();
			var imei = $("#imei").val();
			var discount = $("#discount").val();
			$("#warehouse option").each(function(){
				var wv = $("#warehouse").val();
				if($(this).val()!=wv){
					$(this).prop("disabled",true);
				}
			});
			if($("#barcode").val().trim()!=""){
				if(!checkMinPrice()){
					return;
				}
				if(!checkMaxquantity(itemlist)){
					alert("库存数量不足!");
					reset();
					return;
				}
				for( var i = 0; i < itemlist.length; i++) {
					// 判断是否在 已有的itemlist 中存在新输入的 barcode, 若有即添加其数量,修改其价格即可
					var tempbarcode = itemlist[i].barcode;
					if(tempbarcode.indexOf(barcode)!=-1){
						itemlist[i].quantity = parseInt(quantity)+parseInt(itemlist[i].quantity);
						itemlist[i].price = parseFloat(itemlist[i].price);
						if(imei.trim()!=""){
							itemlist[i].imei = itemlist[i].imei+","+imei;
						}
						listAll(itemlist);
						sumTotal(itemlist);
						reset();
						return;
					}
				}
				if(times<maxtimes){
					//若果 itemlist 里面不存在新输入的 barcode, 即判断当前行数是否足够,若足够添加一行
					var item = newItem(barcode,imei,desc,price,quantity);
					itemlist.push(item);
					//刷新列表,计算 total 数
					listAll(itemlist);
					sumTotal(itemlist);
					//清空输入栏, time增加一
					reset();
					times++;
				}else{
					alert("超过 "+maxtimes+"行！");
				}
			}
			reset();
      });
	 
	 $("#barcode").on("keypress",function(e){
  			if(e.keyCode==13){
  				$("#imei").focus();
  			}
  	 });

	 $("#content").delegate("button","click",function(){
		 var index = $(this).attr("data-role");
		 var content = $("#content");
		 itemlist.splice(index,1);
		 content.find("td").html("");
		 listAll(itemlist);
		 sumTotal(itemlist);
		 times--;
	 });
	 
	 $("#price").on("change keyup",function(e){
		 checkMinPrice();
	 });
	 
	 $("#discount").on("change keyup",function(e){
	 	 var dis = $(this).val();
	 	 if(parseFloat(dis)<0.0){
	 	 	$(this).val(0);
	 	 }
		 sumTotal(itemlist);
	 });
	 
	 $("#discountrate").on("change keyup",function(e){
	 	 var disrate = $(this).val();
	 	 if(parseFloat(disrate)<0.0){
	 	 	$(this).val(100);
	 	 }
		 sumTotal(itemlist);
	 });
	 
	 $("#comfirm").on("click",function(e){
		var f = $("#f1");
		//alert(f.serialize());
		if(presubmit()){
			if(countChange()){
				f.submit();
				reset();
				clear();
			}
		}else{
			alert("请输入客户和录入商品！");
		}
  	 });
	 
	 $("#cancel").on("click",function(e){
		 cancel(); 
	 });
	 
	 $("#pay").on("change",function(e){
		 countChange();
	 });
	 
	 function listAll(itemlist){
	 	//列表显示
		 var trs = $("#content").find("tr");
		 for(var i=0;i<itemlist.length;i++){
			 var tds = trs.eq(i).find("td");
			 var barcode=itemlist[i].barcode;
			 var imei=itemlist[i].imei;
			 var desc=itemlist[i].desc;
			 var quantity=itemlist[i].quantity;
			 var price=itemlist[i].price;
			 quantity = parseInt(quantity);
			 tds.eq(0).html("<input type='text'  readonly value='"+barcode+"' name='barcodes'>");
			 tds.eq(1).html("<input type='text'  readonly value='"+desc+"' name='pdescs'>");
			 tds.eq(2).html("<input type='text'  readonly value='"+imei+"' name='imeis'>");
			 tds.eq(3).html("<input type='text'  readonly value='"+quantity+"' name='quantitys'>");
			 tds.eq(4).html("<input type='text'  readonly value='"+parseFloat(price).toFixed(1)+"' name='prices'>");
			 tds.eq(5).html("<input type='text'  readonly value='"+parseFloat(quantity*price).toFixed(1)+"' name='amounts'>");
			 tds.eq(6).html("<button class='btn btn-default btn-xs clear-padding-height clear-padding-width' data-role='"+i+"' type='button'>删除</button>");
		 }
	 };
	 
	 function sumTotal(itemlist){
	 	//计算 total 数
		 var totalamount = parseFloat(0.0);
		 var totalquantity = parseInt(0);
		 var discount = parseFloat($("#discount").val());
		 var discountrate = (parseFloat($("#discountrate").val())/100).toFixed(2);
		 for ( var i = 0; i < itemlist.length; i++) {
			 totalquantity = totalquantity+parseInt(itemlist[i].quantity);
			 totalamount = totalamount+ parseFloat(parseInt(itemlist[i].quantity)*parseFloat(itemlist[i].price).toFixed(1));
		 }
		 totalamount = (totalamount-discount)*discountrate;
		 $("#totalquantity").val(totalquantity);
		 $("#totalamount").val(parseFloat(totalamount).toFixed(2));
	 };
	 
	 function countChange(){
	 	//计算找零
		 var ta = parseFloat($("#totalamount").val()).toFixed(1);
		 var pay = parseFloat($("#pay").val()).toFixed(1);
		 var c = pay-ta;
		 if(c<0){
			 alert("您少收钱了！");
			 $("#pay").focus();
			 return false;
		 }
		 $("#change").val(c.toFixed(1));
		 return true;
	 };
	 
	 function checkMinPrice(){
	 	//验证售价是否低于最低价
	 	var p = parseFloat($("#price").val());
	 	var mp = parseFloat($("#mprice").val());
	 	if(p<mp){
	 		alert("销售价不能低于最低价");
	 		p.val(mp);
	 		return false;
	 	}
	 	return true;
	 };
	 
	 function newItem(barcode,imei,desc,price,quantity){
	 	//新建 item 
		return eval('('+"{"+
		"\"barcode\":\""+barcode+"\","+
		"\"imei\":\""+imei+"\","+
		"\"desc\":\""+desc+"\","+
		"\"price\":"+price+","+
		"\"quantity\":"+quantity+"}"+')');
	 };
	 
	 function reset(){
	 	//回复输入栏
		$("#customer").val("Customer");
		$("#barcode").val("");
		$("#pdesc").val("");
		$("#imei").val("");
		$("#price").val(0.0);
		$("#quantity").val(1);
		$("#showdesc").html("");
		$("#stock").html("");
		$("#barcode").focus();
		$("#token").val(Math.random()*100000000000000000);
	 };

	 function clear(){
		 //清空表单
		 $("#content").find("td").html("");
		 $("#discount").val(0.0);
		 $("#totalquantity").val(0);
		 $("#totalamount").val(0);
		 $("#pay").val(0);
		 $("#change").val(0);
		 itemlist = new Array();
		 times=0;
	 };
	 
	 function cancel(){
	 	//取消当前单
		var t = confirm("是否确认要取消?");
		if(t){
			reset(); 
			clear();
			$("#warehouse option").prop("disabled",false);
		 }
	 };
	 
	 function presubmit(){
	 	//提交前验证表单信息
		 var c =  $("#customer").val();
		 if(c.trim()==""){
			 return false;
		 }
		 if(itemlist.length>0){
			 return true;
		 }
		 else{
			 return false;
		 }
	 };
	 
	 function inniItemlist(){
	 	//初始化 itemlist
		var trs = $("#content").find("tr");
		for ( var i = 0; i < trs.length; i++) {
			 var tds = trs.eq(i).find("td");
			 var b=tds.eq(0).html();
			 var de=tds.eq(1).html();
			 var im=tds.eq(2).html();
			 var qu=tds.eq(3).html();
			 var pr=tds.eq(4).html();
			 if(b.trim()!="" && qu.trim()!="" && pr.trim()!="" && de.trim()!=""){
				 var barcode=tds.eq(0).find("input").val();
				 var desc=tds.eq(1).find("input").val();
				 var imei=tds.eq(2).find("input").val();
				 var quantity=tds.eq(3).find("input").val();
				 var price=tds.eq(4).find("input").val();
				 var temp = newItem(barcode,imei,desc,price,quantity);
				 itemlist.push(temp);
			};
		};
		times = itemlist.length;
		sumTotal(itemlist);
	 };
	 
	 function checknum(obj){
	 	//验证数字, 采用了html5 number 后作用忽略
  		var sp = obj.val();
		if(!isFinite(sp)){
			alert("请输入 正确数字");
			obj.value = 1;
			return false;
		}
		if(sp<=0){
			alert("数量小于0");
			obj.value = 1;
			return false;
		}
		return true;
	 };
	 
	 function checkMaxquantity(itemlist){
	 	//验证销售item是否超过库存
		var v = parseInt($("#quantity").val());
 		var b = $("#barcode").val();
 		var m = parseInt($("#maxquantity").val());
 		if($("#maxquantity").val().trim()==""){
 			m = 0;
 		};
 		if(itemlist)
 		for(var i = 0 ;i<itemlist.length;i++){
 			var obj = eval('itemlist[i]');
 			if(obj.barcode==b){
 				var q = obj.quantity;
 				m = m-q;
 			};
 		};
 		m = parseInt(m);
		if(v>m){
			return false;
		}else{
			return true;
		}
	 };
});
	
