/**
 * 消息提醒窗口
 * 提示：你有新的消息
 */
function remindMsg(){
	
}

/**
 * 入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	remindMsg();
});