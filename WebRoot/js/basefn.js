/*
 * 全局的功能函数,每个页面都会调用
 * */
/**
 * for listpage to delete checked all obj
 * @param obj
 */
function checkall(obj){
		var flag = obj.checked;
		var objids = document.getElementsByName("ids");
		var del = document.getElementById("delete");
		var cha = document.getElementById("change");
		var changebarcode = document.getElementById("changebarcode");
		var setallup = document.getElementById("setallup");
		var setalldown = document.getElementById("setalldown");
		if(flag){
			for(var i=0;i<objids.length;i++){
				objids[i].checked=true;
				del.style.visibility = "visible";
				if(cha!=null){
					cha.style.visibility = "visible";
				}
				if(changebarcode!=null){
					changebarcode.style.visibility = "visible";
				}
				if(setallup!=null){
					setallup.style.visibility = "visible";
				}
				if(setalldown!=null){
					setalldown.style.visibility = "visible";
				}
			}
		}else{
			for(var i=0;i<objids.length;i++){
				objids[i].checked=false;
				del.style.visibility = "hidden";
				if(cha!=null){
					cha.style.visibility = "hidden";
				}
				if(changebarcode!=null){
					changebarcode.style.visibility = "hidden";
				}
				if(setallup!=null){
					setallup.style.visibility = "hidden";
				}
				if(setalldown!=null){
					setalldown.style.visibility = "hidden";
				}
			}
		}
};

/**
 * for listpage to checked someone for delete
 * @param obj
 */
function checkone(obj){
	var flag = obj.checked;
	var objids = document.getElementsByName("ids");
	var del = document.getElementById("delete");
	var cha = document.getElementById("change");
	var changebarcode = document.getElementById("changebarcode");
	var setallup = document.getElementById("setallup");
	var setalldown = document.getElementById("setalldown");
	for(var i=0;i<objids.length;i++){
		var f = objids[i].checked;
		if(f){
			flag = true;
		}
	}
	if(flag){
		del.style.visibility = "visible";
		if(cha!=null){
			cha.style.visibility = "visible";
		}
		if(changebarcode!=null){
			changebarcode.style.visibility = "visible";
		}
		if(setallup!=null){
			setallup.style.visibility = "visible";
		}
		if(setalldown!=null){
			setalldown.style.visibility = "visible";
		}
	}else{
		del.style.visibility = "hidden";
		if(cha!=null){
			cha.style.visibility = "hidden";
		}
		if(changebarcode!=null){
			changebarcode.style.visibility = "hidden";
		}
		if(setallup!=null){
			setallup.style.visibility = "hidden";
		}
		if(setalldown!=null){
			setalldown.style.visibility = "hidden";
		}
	}
};

/**
 * is mobile to dispach to mobilepage
 * @returns {Boolean}
 */
function isMobile(){
	var flag = false;
	//以下为主流浏览器信息
	var sUserAgent= navigator.userAgent.toLowerCase(); 
	var bIsIpad= sUserAgent.match(/ipad/i) == "ipad"; 
	var bIsIphoneOs= sUserAgent.match(/iphone os/i) == "iphone os"; 
	var bIsMidp= sUserAgent.match(/midp/i) == "midp"; 
	var bIsUc7= sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4"; 
	var bIsUc= sUserAgent.match(/ucweb/i) == "ucweb"; 
	var bIsAndroid= sUserAgent.match(/android/i) == "android"; 
	var bIsCE= sUserAgent.match(/windows ce/i) == "windows ce"; 
	var bIsWM= sUserAgent.match(/windows mobile/i) == "windows mobile"; 
	if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) { 
		flag = true;
	} else { 
		flag = false;
	} 
	return flag;
}

/**
 * check is number style
 * @param obj
 */
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

/**
 * check is number style/mostly use for "quantity" line
 * @param obj
 */  	
function checkQuan(obj){
	var sp = obj.value;
	if(!isFinite(sp)){
		alert("请输入正确数字");
		obj.value = 1;
		return;
	}
	if(parseInt(sp)<=0){
		alert("数量小于0");
		obj.value = 1;
		return;
	}
};

function isNumber(obj){
	var sp = obj.value;
	var reg = /^\d*$/;
	if(sp.trim()==""){
		alert("请输入数字!");
		obj.value = 0;
		return;
	}
	if(!reg.test(sp)){
		alert("请输入数字!");
		obj.value = 0;
		return;
	}
}
/**
 * not use
 * @param obj
 */
function del(obj){
	  var tr = obj.parentNode.parentNode;
	  var table = obj.parentNode.parentNode.parentNode;
	  table.removeChild(tr);
};

/**
 * not use
 * @param obj
 */
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
* key "enter" To "Tab"
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

/**
 * check isChinese fro "ptype","warehouse" and "other type of Id"
 * @param obj
 * @returns {Boolean}
 */
function isChinese(obj){
  var str = obj.value;
 // var reg = /^[\u4E00-\u9FA5]+$/; 
  var reg1 = /.*[\u4e00-\u9fa5]+.*$/;
  if(!reg1.test(str)){ 
	  return false; 
  } 
  alert("不能有中文");
  obj.value="";
  return true; 
};

/**
 * 是否手机号码
 * @param obj
 * @returns {Boolean}
 */
function istelephone(obj){
	var thisobj = obj;
	var istelenumber = /^\d{11}|\d{3}\-\d{8}$/;
	var v = thisobj.value;
	if(!istelenumber.test(v)){
		alert("手机号码格式不正确！大陆地区为11为数字,港澳为xxx-xxxxxxxx");
		obj.value="";
		return false;
	}else{
		return true;
	}
};
/**
 * jquery 全局函数
 */
$(function(){
	$(document).ajaxStart(function(e){
    	$(".zhezhao").css("display","block");
     }).ajaxStop(function(e){
     	$(".zhezhao").css("display","none");
     });
	$("#jumpup").on("click",function(e){
		e.preventDefault();
		$(window).scrollTop(0);
	});
	$("#jumpdown").on("click",function(e){
		e.preventDefault();
		var nowtop = $(window).scrollTop();
		$(window).scrollTop(nowtop+548);
	});
	$("a[name='more']").on("click",function(e){
	  	e.preventDefault();
	  	var v = $(this).attr("data-index");
	  	$("#currentPage").val(v);
		$("#f1").submit();		  	
	});
	$(":input").on("focusin",function(e){
		var offset = $(this).offset();
		var srtop = parseInt(offset.top-100);
		$(document).scrollTop(srtop);
	});
});
	