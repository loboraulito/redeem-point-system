/**
 * 换肤功能
 */
function changeXtheme(){
	var cookieArr = document.cookie.split(";");
	var cssName = "";
	for(var i=0;i<cookieArr.length;i++){
		var arr = cookieArr[i].split("=");
		if(arr[0] == "css"){
			cssName = arr[1];
			break;
		}
	}
	if(cssName){
		var themeCombo = Ext.getCmp("xthemebox");
		if(themeCombo){
			themeCombo.setValue(cssName);
		}
		Ext.util.CSS.swapStyleSheet("theme", path+"/js/ext-2.2.1/resources/css/"+cssName+".css");
	}
}