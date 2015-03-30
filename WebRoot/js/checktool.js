 function check(obj){
  		var sp = obj.value;
		if(!isFinite(sp)){
			alert("请输入正确数字");
			obj.value = 0;
			return;
		}
		if(sp<0){
			alert("不能输入负数！");
			return;
		}
 	};
	  	
  function checkQuan(obj){
  		var sp = obj.value;
		if(!isFinite(sp)){
			alert("请输入 正确数字");
			obj.value = 1;
			return;
		}
		if(sp<=0){
			alert("数量小于0");
			obj.value = 1;
			return;
		}
  };
	  
  function del(obj){
  	  var tr = obj.parentNode.parentNode;
  	  var table = obj.parentNode.parentNode.parentNode;
  	  table.removeChild(tr);
  };
	  
  function checkform(id,tabname){
  	var bs = document.getElementsByName(tabname);
	var f = document.getElementById(id);
	var flag = false;
	for(var i=0;i<bs.length;i++){
	if(bs[i].value!=""){
		flag = true;
	}else{
		alert("错误!!");
  		flag = false;
  		}
  	}
  	if(flag){
  		f.submit();
  	}
  };
  
  /**
   * enter璺虫牸 
   * @param event
   * @param imei
   */
  function enterTotab(event,imei){
	  var e = event?event:window.event;
	  var ims = document.getElementsByName(imei);
	  if(e.keyCode==13){
		  imei.nextSibling.focus();
	  }
  };
  
  function isChinese(obj){
	  var str = obj.value;
	 // var reg = /^[\u4E00-\u9FA5]+$/; 
	  var reg1 = /.*[\u4e00-\u9fa5]+.*$/;
	  if(!reg1.test(str)){ 
		  return false; 
	  } 
	  alert("产品编码不能有中文");
	  obj.value="";
	  return true; 
  };
  
 /**	
  	function check(id){
  		//var sp = document.getElementById(id).value;
  		var obj = document.getElementById(id);
  		var sp = obj.value;
		var re1=/^\d*$/;
		var re2=/^\d*\.\d*/;
		/**
		var b1 = re1.test(sp);
		var b2 = re2.test(sp);
		if(!b1&&!b2){
			alert("请输入正确价格!");
			obj.value = 0;
		}
  	}
  */