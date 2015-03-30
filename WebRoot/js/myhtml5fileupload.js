jQuery.fn.extend({
	setimgsize:function(){
		/**
		*设置图片的宽和高,让图片显示的更整齐
		*/
		var width = $(this).width();
  		var height = width*3/4;
		$(this).find("img").each(function(i){
			$(this).css("width",width);
			$(this).css("height",height);
		});	
		return $(this);
	},
	responsiveimage:function(nwidth){
		/**
		 *设置图片的宽和高,让图片显示的更整齐
		 */
		$(this).find("img").each(function(i){
			var width = $(this).width();
			var height = $(this).height();
			var rate = Math.round(width/height);
			var nheight = nwidth/rate;
			$(this).css("width",nwidth);
			$(this).css("height",nheight);
		});
		return $(this);
	},
	resizebyheight:function(nheight){
		/**
		 *设置图片的宽和高,让图片显示的更整齐
		 */
		$(this).find("img").each(function(i){
			var width = $(this).width();
			var height = $(this).height();
			var rate = Math.round(width/height);
			var nwidth = nheight*rate;
			$(this).css("width",nwidth);
			$(this).css("height",nheight);
		});
		return $(this);
	},
	checkfiles:function(){
		$(this).on("change",function(){
			var fileinputid = $(this).attr("id");
			var fileInput = document.querySelector("#"+fileinputid);
			var files = fileInput.files;
		    // 获取到所选文件数量
		    var html = "";
		    var fl=files.length;
		    if(fl>5){
		    	alert("一次最多允许上传5个图片");
		        document.getElementById("upload").reset();
		        //$("#msg").html("");
		        return;
		    }
		    for ( var int = 0; int < fl; int++) {
		    	var url = webkitURL.createObjectURL(files[int]);
		    	alert(url);
		    	//msgcontiner.createElement("<canvas width='100' height='100'></canvas>");
		    }
		    for (var i = 0;i < fl;i++) {
		        var file = files[i];
		        var filesize = "";
		        var size = file.size;
		        if(size/1024>1){
		        	if(size/1024/1024>1){
		        		filesize = parseFloat(file.size/1024/1024).toFixed(2)+"mb";
		        	}else{
		        		filesize =  parseFloat(file.size/1024).toFixed(2)+"kb";
		        	}
		        }
		        if(size>2087134){
		        	alert(file.name+"文件过大");
		        	document.getElementById("upload").reset();
		        	//$("#msg").html("");
		        	return;
		        }
		        if(file.name.indexOf("jpg")!=-1||file.name.indexOf("gif")!=-1
		        	||file.name.indexOf("png")!=-1||file.name.indexOf("bmp")!=-1
		        	||file.name.indexOf("JPG")!=-1||file.name.indexOf("GIF")!=-1
		        	||file.name.indexOf("PNG")!=-1||file.name.indexOf("BMP")!=-1){
		        		
		        }else{
		        	alert("请选择图片格式.jpg,.gif,.png,.bmp,是可选文件");
		        	document.getElementById("upload").reset();
		        	//$("#msg").html("");
		        	return;
		        }
		      	html+="<canvas width='100' height='100'><img src=''></canvas><p>"+file.name+" ;   文件大小:"+filesize+"</p>";
		    };
		    $("#msg").html(html);
		});
		return $(this);
	}
});

jQuery.extend({
	uploadfile:function(url){
			var aciton = url;
			var fd = new FormData(document.getElementById("upload"));
			alert("开始上传");
			$.ajax({
				url: aciton,
				type: "POST",
				data:fd,
				processData:false,
				contentType:false,
				xhr:function() {
		        	var xhr = $.ajaxSettings.xhr();
			        //绑定上传进度的回调函数
			        xhr.upload.addEventListener('progress', function(evt){
			        	var pb = $("#progressBar");
						if (evt.lengthComputable) {
							var precent = Math.round(evt.loaded*100/evt.total);
							$("#pecent").html(precent+"%");
					        pb.attr("max",evt.total);
					        pb.attr("value",evt.loaded);
				    	}
			        }, false);
			        return xhr;//一定要返回，不然jQ没有XHR对象用了
		    	},
				beforeSend:function(xhr){
					if(!$.beforeupload()){
						document.getElementById("upload").reset();
					}
				},
				success:function(data){
					window.location.reload(true);
				}
			});
	},
	beforeupload:function(){
		$("input[type='file'").val();
		if($("input[type='file'").val()==""){
			return false;
		}else{
			return true;
		}
	}
});