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
	
	var url = "/message/messageList.action?method=messageList";
	
	/**
	 * 消息解析
	 */
	var messageReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "messageList",
		successProperty:"success"
	},[
		{name:"messageId"},//消息ID
		{name:"messageFrom"},//发送人
		{name:"messageTo"},//接收人
		{name:"messageSendTime"},//发送时间
		{name:"messageReceiveTime"},//接收时间
		{name:"messageNew"},//是否新消息
		{name:"messageContent"},//消息内容
		{name:"messageTitle"}//消息标题
	]);
	
	var simpleMsgData = {"totalCount":0,"messageList":[],"success":true};
	
	/**
	 * 消息数据存储
	 */
	var msgListStore = new Ext.data.GroupingStore({
		url:path+ url,
		groupField:["messageNew","messageFrom"],
		sortInfo:{field: 'messageSendTime', direction: "ASC"},
		reader:messageReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					var o = Ext.util.JSON.decode(action.responseText);
					if(!o.success){
						Ext.Msg.alert('错误提示',o.msg, function(btn){
							msgListStore.loadData(simpleMsgData);
						});
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					msgListStore.loadData(simpleMsgData);
				}
			}
		}
	});
	
	var loadParam = {
		start : 0,
		limit : 50,
		viewAll : "no",
		userId : userName
	};
	
	var msgListSM = new Ext.grid.CheckboxSelectionModel();
	var msgListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),msgListSM,{
		dataIndex:"messageId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"消息标题",
		dataIndex:"messageTitle",
		width:70
	},{
		header:"发送人",
		dataIndex:"messageFrom",
		width:50
	},{
		header:"接收人",
		dataIndex:"messageTo",
		hidden:true,
		hideable:false
	},{
		header:"发送时间",
		dataIndex:"messageSendTime",
		width:50
	},{
		header:"接收时间",
		dataIndex:"messageReceiveTime",
		hidden:true,
		hideable:false
	},{
		header:"是否新消息",
		dataIndex:"messageNew",
		width:70
	}]);
	
	var msgListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("message_div").getWidth(),
		height:Ext.get("message_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:msgListStore,
		renderTo:"message_div",
		cm:msgListCM,
		sm:msgListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:msgListStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[]
	});
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonRightStore = buttonRight();
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 * loadButtonRight(buttonStore, mainDataStore, dataGrid, pageDiv, params)
	 */
	loadButtonRight(buttonRightStore, msgListStore, msgListDataGrid, "message_div", loadParam);
	
	
	
}
function getMessage(msgs){
	//alert(msgs);
}
function jsFunctionName(msg){
	//alert(msg);
	//alert(msg.messageId);
}
/**
 * 入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	//服务器停止时的错误处理
	dwr.engine.setErrorHandler(function(){});
	//重点关于解决页面每刷新一次会多创建一个新的ScriptSession的解决方法
	//但是似乎无用
	dwr.engine.setNotifyServerOnPageUnload(true);
	// 激活dwr反转 重要
	dwr.engine.setActiveReverseAjax(true);
	message();
});