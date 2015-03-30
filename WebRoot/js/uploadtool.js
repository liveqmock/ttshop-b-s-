function uploadfile(url){
	var action = url;
	var xhr = new XMLHttpRequest();
	var fd = new FormData(document.getElementById("upload"));
	xhr.upload.addEventListener("progress",function(evt){
		if(evt.lengthComputable){
			var pencent = Math.round(evt.loaded*100/evt.total);
			document.getElementById("progressBar").max =  100;
			document.getElementById("progressBar").value =  pencent;
			document.getElementById("percent").innerHTML = pencent+"%";
		}
	},false);
	xhr.addEventListener("error",function(evt){
		alert("上传失败!");
	},false);
	xhr.addEventListener("abort",function(evt){
		xrh.abort();
	},false);
	xhr.addEventListener("load",function(evt){
		alert("上传成功!");
		window.location.reload(true);
	},false);
	xhr.open("post",action,true);
	xhr.send(fd);
}

function checkfile(){
	var fileInput = document.querySelector("#images");
	var files = fileInput.files;
    // 获取到所选文件数量
    var html = "";
    var fl=files.length;
    var msgcontiner = document.getElementById("msg");
    if(fl>5){
    	alert("一次最多允许上传5个图片");
        document.getElementById("upload").reset();
        return;
    }
//    for ( var int = 0; int < fl; int++) {
//    	var url = webkitURL.createObjectURL(files[int]);
//    	var img = new Image();   // 创建一个img元素
//    	alert(url);
//    	//img.src = url;
//    	canvas = document.createElement('CANVAS');
//        canvas.setAttribute('width',150);
//        canvas.setAttribute('height',150);
//        var ctx = canvas.getContext('2d');
//        ctx.drawImage(img, 0, 0, 150, 150);
//    	//msgcontiner.createElement("<canvas width='100' height='100'></canvas>");
//    }
    var list = document.createElement("ul");
    msgcontiner.appendChild(list);
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
        	msgcontiner.innerHTML = "";
        	return;
        }
        if(file.name.indexOf("jpg")!=-1||file.name.indexOf("gif")!=-1
        	||file.name.indexOf("png")!=-1||file.name.indexOf("bmp")!=-1
        	||file.name.indexOf("JPG")!=-1||file.name.indexOf("GIF")!=-1
        	||file.name.indexOf("PNG")!=-1||file.name.indexOf("BMP")!=-1){
        		
        }else{
        	alert("请选择图片格式.jpg,.gif,.png,.bmp,是可选文件");
        	document.getElementById("upload").reset();
        	msgcontiner.innerHTML = "";
        	return;
        }
        
        var li = document.createElement("li");
        list.appendChild(li);
        
        var img = document.createElement("img");
        img.src = window.URL.createObjectURL(files[i]);;
        img.height = 60;
        img.onload = function() {
          window.URL.revokeObjectURL(this.src);
        };
        li.appendChild(img);
        
        var info = document.createElement("span");
        info.innerHTML = " 文件: "+ files[i].name + ", 大小: " + filesize;
        li.appendChild(info);
       // var url = window.URL.createObjectURL(fileObj);
      //	html+="<p>"+file.name+" ; 文件大小:"+filesize+"</p>";
    };
   // msgcontiner.innerHTML = html;
}
