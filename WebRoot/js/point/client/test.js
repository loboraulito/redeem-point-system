$(document).ready(function(){
    //alert("jQuery 1.6.2");
    //loadimg
    //mainpagediv
    $("#loadimg").ajaxStart(function(){
        $(this).show();
    });
    $("#loadimg").ajaxStop(function(){
        $(this).hide();
    });
	var data = {};
	data["start"] = 0;
	data["limit"] = 50;
    $.ajax({
        url: path+"/gift/giftManageList.action?method=giftManageList",
		type:"post",
		data:{start:0,limit:50},
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			alert(data);
		},
		complete:function(){
			alert(1);
		},
        global: false //不触发全局Ajax事件   
    });
});
