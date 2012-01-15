/**
 * 系统消息
 */
function message(){
	//alert(messageService);
	var msg = {
		messageFrom:"redeempoint_system",
		messageTo:"admin",
		messageTitle:"测试消息",
		messageContent:"测试消息,测试消息,测试消息,"
	};
	//msg.setMessageContent("测试！");
	
	messageService.save(msg, getMessage);
}
function getMessage(msgs){
	alert(msgs);
}

/**
 * 入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	//重点关于解决页面每刷新一次会多创建一个新的ScriptSession的解决方法
	//但是似乎无用
	dwr.engine.setNotifyServerOnPageUnload(true);
	// 激活dwr反转 重要
	dwr.engine.setActiveReverseAjax(true);
	message();
});