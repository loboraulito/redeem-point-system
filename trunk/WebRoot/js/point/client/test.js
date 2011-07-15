$(document).ready(function(){
    //alert("jQuery 1.6.2");
    //loadimg
    //mainpagediv
	//pagionbar
    $("#loadimg").ajaxStart(function(){
        $(this).show();
    });
    $("#loadimg").ajaxStop(function(){
        $(this).hide();
    });
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
		$("#mainpagediv").html(loadGiftList(data.giftList));
	}
	
	function loadGiftList(data){
		
		var html = "<ul class='mainProductList'>";
		if(data){
			for(var i=0; i < data.length; i++){
				var d = data[i];
				var li = "<li class='p5'>";
				var gift = "<div>礼品颜色："+d.giftColor+"</div>";
				li += gift;
				li += "</li>";
				html += li;
			}
		}
		html += "</ul><br>";
		return html;
	}
	
	$("#pagionbar").myPagination({
		currPage:1,
		//pageCount:50,
		pageSize:3,
		cssStyle:"yahoo2",
		ajax:{
			on:true,
			callback:callbackHandler,
			url:path+"/gift/giftManageList.action?method=giftManageList",
			dataType:"json",
			//自定义参数时，on必须存在，且为true
			param:{on:true, start:0, limit:5}
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
