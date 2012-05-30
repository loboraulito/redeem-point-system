$(document).ready(function(){
    //alert("jQuery 1.6.2");
    //loadimg
    //mainpagediv
	//pagionbar
    $("#loadimg").ajaxStart(function(){
		$("#maskDiv").show();
		$(this).show();
    });
    $("#loadimg").ajaxStop(function(){
		$("#maskDiv").hide();
        $(this).hide();
    });
	var hightManus = false;
	var data = {};
	data["start"] = 0;
	data["limit"] = 50;
    /*
	$.ajax({
        url: path+"/gift/giftManageList.action?method=giftManageList",
		type:"post",
		data:{start:0,limit:50},
		dataType:"json",
		success:function(data,textStatus){
			//alert(data.giftList[0].giftColor);
			$("#mainpagediv").html(loadGiftList(data.giftList));
		},
		//global: false, //不触发全局Ajax事件   
		complete:function(httpRequest, textStatus){
			//alert(1);
		}
    });
    */
	/*
	var callbackHandler = function(data){
		alert(data);
	}
	
	this.callbackHandler = function(data){
		alert(data);
		$("#mainpagediv").html(loadGiftList(data.giftList));
	}
	*/
	function callbackHandler(data){
		//var liWidth = $("#p5");
		if(!hightManus){
			$("#mainpagediv").height($("#mainpagediv").height() - $("#pagionbar").height() - $("#giftCart").height());
			hightManus = true;
		}
		$("#mainpagediv").html("");
		$("#mainpagediv").html(loadGiftList(data.giftList));
		$("img").each(function(i,data){
			imgReady(data.src,function(){
				//尺寸就绪
			},function(){
				//加载完毕
				//alert(this);
				//PopEx(data,0,0,120,0,99999999,null);
			},function(){
				//加载错误
			});
		});
	}
	
	function loadGiftList(data){
		var html = "<ul class='mainProductList'>";
		if(data){
			for(var i=0; i < data.length; i++){
				var d = data[i];
				var li = "<li class='p5'>";
				var gift = "<div>";
				//picture/logo_cn.png
				//d.giftImage
				var img = "<img src='"+path+""+d.giftImage+"' width='120px'></img><br/>";
				//alert(img);
				//onload='PopEx(this,0,0,120,0,20,null)'
				gift = gift+img;
				
				gift = gift + "礼品颜色："+d.giftColor+"";
				
				gift = gift+"</div>";
				li += gift;
				li += "</li>";
				html += li;
			}
		}
		html += "</ul><br>";
		return html;
	}
	$("#mainpagediv").css({position: "absolute", border: "1px solid #d5e1f2", left:"0",top:"20"}).height("97%");
	$("#pagionbar").css({position: "absolute", border: "0px solid green", left:"0",bottom:"0"}).width("100%");
	
	$("#pagionbar").myPagination({
		currPage:1,
		//pageCount:50,
		//pageSize:3,
		cssStyle:"yahoo2",
		ajax:{
			on:true,
			callback:callbackHandler,
			url:path+"/gift/giftManageList.action?method=giftManageList",
			dataType:"json",
			//自定义参数时，on必须存在，且为true
			param:{on:true, start:0, limit:30}
		}
	});
	
	/*
	$("#mainpagediv").pagination({
		totalRecord:data.giftList.length,
		proxyUrl:path+"/gift/giftManageList.action?method=giftManageList",
		groupSize:4
	});
	*/
});
