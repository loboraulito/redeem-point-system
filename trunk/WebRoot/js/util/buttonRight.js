/**
 * 按钮的权限管理
 */
function buttonRight(roleId, menuId, callbackFunction){
	/**
	 * 按钮信息解析器
	 */
	var buttonReader = new Ext.data.JsonReader({
		totalProperty:"totalCount",
		root:"buttons"
	},[
		{name:"buttonId"},//唯一id
		{name:"buttonName"},//按钮名称
		{name:"buttonText"},//按钮显示的文字
		{name:"menuId"},//菜单ID
		{name:"isShow"},//是否显示
		{name:"buttonIconCls"},//按钮样式
		{name:"handler"},//触发的事件
		{name:"buttonUrl"}//按钮路径
	]);
	/**
	 * 获取有权限的按钮路径
	 * @type 
	 */
	var proxyUrl = path+"/right/showButtons.action?method=buttonRight";
	/**
	 * 按钮信息存储器
	 */
	var buttonStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		reader:buttonReader
	});
	/**
	 * 读取数据，并且在完成之后，执行后调方法：callbackFunction
	 */
	/*
	buttonStore.load({
		params:{roleId:roleId,menuId:menuId},
		callback:function(records,options,success){
			if(typeof(callbackFunction) == "function"){
				callbackFunction(records,options,success);
			}
		}
	});
	*/
	return buttonStore;
}