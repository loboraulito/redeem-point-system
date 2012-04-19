/**
 * 系统消息
 */
function message(){
	//alert(messageService);
	/*
	var msg = {
		messageFrom:"redeempoint_system",
		messageTo:"admin",
		messageTitle:"测试消息",
		messageContent:"测试消息,测试消息,测试消息,"
	};
	*/
	//msg.setMessageContent("测试！");
	//messageService.save(msg, getMessage);
	var userStore;
	//Get the userList
	userService.findUserByPageWithProtect(-1, -1, function(data){
		userStore = new Ext.data.Store({
			autoLoad:true,
			proxy : new Ext.data.MemoryProxy({
				'root' : data
			}),
			reader:new Ext.data.JsonReader({
				root : 'root',
				fields:["userId","userName"]
			})
		});
	});
	
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
	
	var loadParam = {
		start : 0,
		limit : 50,
		viewAll : "no",
		userId : userName
	};
	
	/**
	 * 消息数据存储
	 */
	var msgListStore = new Ext.data.GroupingStore({
		url:path+ url,
		groupField:["messageFrom"],
		sortInfo:{field: 'messageNew', direction: "DESC"},
		reader:messageReader,
		baseParams:loadParam,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(!o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								msgListStore.loadData(simpleMsgData);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					msgListStore.loadData(simpleMsgData);
				}
			}
		}
	});
	
	/**
	 * 数据标准分组显示
	 */
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,//是否在分组行上显示分组字段的名字
		enableNoGroups:false, //是否允许用户关闭分组功能REQUIRED!
		hideGroupedColumn: false,//是否隐藏分组列
		enableGroupingMenu:false,//是否在表头菜单中进行分组控制
		groupTextTpl: '来自 {text} 的 {[values.rs.length]} 条消息'//用于渲染分组信息的模板，默认为'{text}'
	});
	
	var msgListSM = new Ext.grid.CheckboxSelectionModel();
	var msgListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),msgListSM,{
		dataIndex:"messageId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"消息标题",
		dataIndex:"messageTitle",
		renderer:showContent,
		width:150
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
		renderer:isNew,
		width:20
	}]);
	
	var msgListDataGrid = new Ext.grid.GridPanel({
		id:"msgListDataGrid",
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
		view:groupView,
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
	
	function isNew(value,metadata,record,rowIndex,colIndex,store){
		return value == "1" ? "是" : "否";
	}
	
	function showContent(value,metadata,record,rowIndex,colIndex,store){
		
		return "<a href='javascript:void(0)' onclick='showMsgContent(\""+rowIndex+"\")'>"+value+"</a>";
	}
	
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

	/**
	 * 发送消息
	 * @param {} url
	 */
	this.sendMessage = function(url){
		var messageForm = getMessageForm(url, false, true);
		
		var buttons = [{
			text:"发送消息",
			handler:function(){
				if(messageForm.form.isValid()){
					saveMessageInfo("sendMsgWindow",messageForm);
				}
			}
		},{
			text:"取消",
			handler:function(){
				var w = Ext.getCmp("sendMsgWindow");
				if(w){
					w.close();
				}
			}
		}];
		
		showMessageWindow("sendMsgWindow","发送系统消息",500,300,messageForm,null,buttons);
	};
	/**
	 * 接收消息
	 * @param {} url
	 */
	this.receiveMessage = function(url){
		
	};
	
	/**
	 * 删除消息
	 * @param {} url
	 */
	this.deleteMessage = function(url){
		var gridSelectionModel = msgListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条消息删除！');
		    return false;
		}
		var dataIdArray = new Array();
		for(var i=0; i < gridSelection.length; i++){
			dataIdArray.push(gridSelection[i].get("messageId"));
		}
		var messageArray = dataIdArray.join(",");
		Ext.Msg.confirm("系统提示","确定要删除所选消息？",function(btn){
			if(btn == "yes" || btn == "ok"){
				deleteMessageInfo(url, messageArray);
			}
		});
	};
	/**
	 * 查询消息
	 * @param {} url
	 */
	this.queryMessage = function(url){
		var buttons = [{
			text:"查询"
		},{
			text:"取消",
			handler:function(){
				var w = Ext.getCmp("queryWindow");
				if(w){
					w.close();
				}
			}
		}];
		
		var messageForm = getMessageForm(url, true, true);
		showMessageWindow("queryWindow","查询系统消息",500,300,messageForm,null,buttons);
	};
	/**
	 * 显示详细消息
	 * @param {} msgId
	 */
	this.showMsgContent = function(rowIndex){
		var buttons = [{
			text:"关闭",
			handler:function(){
				var w = Ext.getCmp("showMsgContentWindow");
				if(w){
					w.close();
				}
			}
		}];
		var record = msgListStore.getAt(rowIndex);
		var msgId = record.get("messageId");
		var title = record.get("messageTitle");
		var from = record.get("messageFrom");
		var fromDate = record.get("messageSendTime");
		var content = record.get("messageContent");
		var item = [{
			xtype:"textarea",
			readOnly:true,
			value:content
		}];
		showMessageWindow("showMsgContentWindow","消息详细内容--"+title,500,300,item,null,buttons);
		var readUrl = path + "/message/messageRead.action?method=readMessage";
		if(record.get("messageNew") == "1"){
			readMessage(readUrl, msgId);
		}
	};
	/**
	 * 阅读消息
	 * @param {} url
	 * @param {} msgId
	 */
	this.readMessage = function(url, msgId){
		var id;
		if(!msgId || msgId == ""){
			var gridSelectionModel = msgListDataGrid.getSelectionModel();
			var gridSelection = gridSelectionModel.getSelections();
			if(gridSelection.length < 1){
				Ext.MessageBox.alert('提示','请至少选择一条消息！');
			    return false;
			}
			var dataIdArray = new Array();
			for(var i=0; i < gridSelection.length; i++){
				if(gridSelection[i].get("messageNew") != "1"){
					Ext.MessageBox.alert('提示','请选择新消息！');
			    	return false;
				}
				dataIdArray.push(gridSelection[i].get("messageId"));
			}
			id = dataIdArray.join(",");
			Ext.MessageBox.show({
				msg:"正在标记系统消息，请稍候...",
				progressText:"正在标记系统消息，请稍候...",
				width:300,
				wait:true,
				waitConfig: {interval:200},
				icon:Ext.Msg.INFO
			});
		}else{
			id = msgId;
		}
		Ext.Ajax.request({
			params:{msgId:id},
			timeout:60000,
			url:url,
			success:function(response, options){
				var msg = Ext.util.JSON.decode(response.responseText);
				if(!msgId || msgId == ""){
					Ext.Msg.hide();
				}
				if(msg.success){
					msgListStore.reload();
					if(!msgId || msgId == ""){
						Ext.Msg.alert("系统提示",msg.msg);
					}
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","系统消息读取失败！");
					}
				}
			},failure: function(response, options){
				if(!msgId || msgId == ""){
					Ext.Msg.hide();
				}
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","系统消息读取失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	};
	
	/**
	 * 消息表单
	 * @param {} url
	 * @param {} isNull
	 * @param {} readOnly
	 * @return {}
	 */
	function getMessageForm(url, isNull, readOnly){
		var messageForm = new Ext.form.FormPanel({
			url:url,
			frame: true,
			labelAlign: 'right',
			labelWidth:70,
			autoScroll:false,
			waitMsgTarget:true,
			viewConfig:{forceFit:true},
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:1,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"messageTitle",
						anchor:"90%",
						fieldLabel:"消息标题",
						maxLength:200,
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:1,
					height:50,
					items:[{
						xtype:"lovcombo",
						fieldLabel:"收件人",
						name:"messageTo",
						allowBlank:isNull,
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"userName",//将sortvalue设置为传递给后台的值
						displayField:"userName",
						hiddenName:"messageTo",//这个值就是传递给后台获取的值
						hideOnSelect : false, 
						readOnly : true,  
						/*
						store:new Ext.data.SimpleStore({
							fields:["text","value"],
							data:[[1,"a"],[2,"b"]]
						}),
						*/
						store:userStore,
						/*
						store:[  
             [1, 'Personnel []']  
            ,[11, 'Finance (33)']  
            ,[5, 'Door']  
            ,[6, 'Door Panel']  
            ,[2, 'Management !77']  
            ,[25, 'Production']  
            ,[3, 'Users']  
            ,[20, 'Window']  
            ,[21, 'Window Panel']  
            ,[22, 'Form Panel']  
            ,[23, 'Grid Panel']  
            ,[24, 'Data View Panel']  
        ]  ,
        */
						anchor:"90%",
						mode: "local"
					},{
						xtype:"hidden",
						name:"messageFrom",
						value:userName
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:1,
					height:90,
					items:[{
						xtype: 'textarea',
						name:"messageContent",
						anchor:"90%",
						fieldLabel:"消息内容",
						maxLength:500,
						allowBlank:isNull
					}]
				}]
			}]
		});
		return messageForm;
	}
	
	/**
	 * 公用窗口
	 * @param {} id
	 * @param {} title
	 * @param {} width
	 * @param {} height
	 * @param {} items
	 * @param {} html
	 * @param {} buttons
	 */
	function showMessageWindow(id, title, width, height, items, html, buttons){
		var messageWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			html:html,
			buttons:buttons,
			modal:true,
			//animateTarget:"giftmanage_div",//动画展示
			layout:"fit",
			resizable:false
		});
		messageWindow.show();
	}
	
	/**
	 * 发送系统消息
	 * @param {} windowId
	 * @param {} form
	 */
	function saveMessageInfo(windowId, form){
		Ext.MessageBox.show({
			msg:"正在发送系统消息，请稍候...",
			progressText:"正在发送系统消息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		
		form.getForm().submit({
			timeout:60000,
			success: function(form, action) {
				Ext.Msg.hide();
				var result = Ext.decode(action.response.responseText);
				if(result && result.success){
					var msg = "系统消息发送成功！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							//msgListStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "系统消息发送失败！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var msg = "系统消息发送失败，请检查您的网络连接或者联系管理员！";
				try{
					var result = Ext.decode(action.response.responseText);
					if(result.msg){
						msg = result.msg;
					}
				}catch(e){
					msg = "系统错误：" + e;
				}
				Ext.Msg.alert('系统提示信息', msg);
			}
		});
	}
	
	/**
	 * 删除消息
	 * @param {} url
	 * @param {} msg
	 */
	function deleteMessageInfo(url, msg){
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		
		Ext.Ajax.request({
			params:{msg:msg},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","所选消息已成功删除！");
					}
					msgListStore.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","所选消息删除失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","所选消息删除失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	}
	
}

/**
 * 入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	message();
});