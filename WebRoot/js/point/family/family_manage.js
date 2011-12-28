/**
 * 家庭基本信息管理
 */
function family_manage(){
	/**
	 * 家庭基本信息数据解析
	 */
	var familyListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "familyList",
		successProperty:"success"
	},[
		{name:"familyId"},//家庭ID
		{name:"familyName"},//家庭名称
		{name:"familyCreateDate"},//家庭创建日期
		{name:"familyHouseHolder"},//户主
		{name:"familyAddress"},//家庭地址
		{name:"familyComment"},//家庭简介
		{name:"familyTel"}//联系电话
	]);
	
	var loadParam = {
		start : 0,
		limit : 50,
		viewAll : "no",
		userId : userName
	};
	
	/**
	 * 空数据，当主数据位空时，使用该数据以避免报错
	 * @type 
	 */
	var simpleData = {"totalCount":0,"familyList":[],"success":true};
	/**
	 * 数据存储
	 */
	var familyListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/family_manage/familyList.action?method=familyList"
		}),
		baseParams:loadParam,
		reader:familyListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					var o = Ext.util.JSON.decode(action.responseText);
					if(!o.success){
						Ext.Msg.alert('错误提示',o.msg);
						familyListStore.loadData(simpleData);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					familyListStore.loadData(simpleData);
				}
			}
		}
	});
	
	var familyListSM = new Ext.grid.CheckboxSelectionModel();
	var familyListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),familyListSM,{
		dataIndex:"familyId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"家庭名称",
		dataIndex:"familyName",
		width:50
	},{
		header:"创建日期",
		dataIndex:"familyCreateDate",
		width:50
	},{
		header:"户主",
		dataIndex:"familyHouseHolder",
		width:50
	},{
		header:"家庭地址",
		dataIndex:"familyAddress",
		width:150
	},{
		header:"联系电话",
		dataIndex:"familyTel",
		width:70
	}]);
	
	
	Ext.ToolTip.prototype.onTargetOver = Ext.ToolTip.prototype.onTargetOver
			.createInterceptor(function(e) {
		this.baseTarget = e.getTarget();
	});
	
	Ext.ToolTip.prototype.onMouseMove = Ext.ToolTip.prototype.onMouseMove
			.createInterceptor(function(e) {
		if(this.baseTarget != null){
			if (!e.within(this.baseTarget)) {
				this.onTargetOver(e);
				return false;
			}
		}else{
			return false;
		}
	});
	
	var familyListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("family_manage").getWidth(),
		height:Ext.get("family_manage").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:familyListStore,
		renderTo:"family_manage",
		cm:familyListCM,
		sm:familyListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:familyListStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		
		onRender: function() {
        	Ext.grid.GridPanel.prototype.onRender.apply(this, arguments);
        	this.addEvents("beforetooltipshow");
	        this.tooltip = new Ext.ToolTip({
	        	id:"rowTip_a",
	        	title:"家庭简介",
	        	border:true,
	        	minWidth:300,
	        	maxWidth:300,
	        	draggable:true,
	        	//autoHide: false,
		        //closable: true,
	        	items:[{
	        		xtype:"textarea",
	        		id:"familyCommentArea_a",
	        		width:287,
	        		readOnly:true
	        	}],
	        	
	        	renderTo: Ext.getBody(),
	        	target: this.view.mainBody,
	        	listeners: {
	        		beforeshow: function(qt) {
	        			var v = this.getView();
	        			var rows = (this.store != null ? this.store.getCount() : 0);
	        			if(rows > 0){
				            var row = v.findRowIndex(qt.baseTarget);
				            var cell = v.findCellIndex(qt.baseTarget);
				            /*
				            var rowData = this.getStore().getAt((row-v.lastRowIndex) + v.lastRowIndex - v.bufferRange[0]);
				            if (rowData && this.lastSelectedRow != row){
				            	this.fireEvent("beforetooltipshow", this, row, cell, rowData);
				            }
				            */
				            //this.lastSelectedRow = row;
				            this.fireEvent("beforetooltipshow", this, row, cell);
	        			}else{
	        				return false;
	        			}
	        		},
	        		scope: this
	        	}
	        });
        },
		listeners: {
			render: function(g) {
				g.on("beforetooltipshow", function(grid, row, col) {
					//grid.tooltip.body.update("Tooltip for (" + row + ", " + col + ")");
					//grid.tooltip.body.update(this.store.getAt(row).get("familyComment"));
					//Ext.getCmp("familyCommentArea").setWidth(Ext.getCmp("rowTip").getInnerWidth());
					Ext.getCmp("familyCommentArea_a").setValue(this.store.getAt(row).get("familyComment"));
				});
			}
		},
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
	loadButtonRight(buttonRightStore, familyListStore, familyListDataGrid, "family_manage", loadParam);
	/**
	 * 创建新家庭
	 * @param {} url
	 */
	this.createFamily = function(url){
		var familyForm = getFamilyManageForm(url, false, false);
		var buttons = [{
			text:"保存",
			handler: function(){
				if(familyForm.form.isValid()){
					saveFamilyInfo("addFamilyInfo", familyForm);
				}
			}
		},{
			text:"取消",
			handler: function(){
				var fw = Ext.getCmp("addFamilyInfo");
				if(fw){
					fw.close();
				}
			}
		}];
		showFamilyManageWindow("addFamilyInfo","创建家庭",450, 320, familyForm, null, buttons);
		markComponent("familyCreateDate_field");
	};
	/**
	 * 修改家庭信息
	 * @param {} url
	 */
	this.editFamily = function(url){
		var gridSelectionModel = familyListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length != 1){
			Ext.MessageBox.alert('提示','请选择一个家庭进行修改！');
		    return false;
		}
		
		var holder = gridSelection[0].get("familyHouseHolder");
		if(holder != userName){
			Ext.Msg.alert("系统提示","您不是家庭："+gridSelection[0].get("familyName") + " 的户主，无权修改！");
			return;
		}
		
		var familyForm = getFamilyManageForm(url, false, false);
		var buttons = [{
			text:"保存",
			handler: function(){
				if(familyForm.form.isValid()){
					saveFamilyInfo("editFamilyInfo", familyForm);
				}
			}
		},{
			text:"取消",
			handler: function(){
				var fw = Ext.getCmp("editFamilyInfo");
				if(fw){
					fw.close();
				}
			}
		}];
		showFamilyManageWindow("editFamilyInfo","修改家庭信息",450, 320, familyForm, null, buttons);
		familyForm.getForm().loadRecord(gridSelection[0]);
		markComponent("familyCreateDate_field");
	};
	/**
	 * 删除家庭信息
	 * @param {} url
	 */
	this.deleteFamily = function(url){
		var gridSelectionModel = familyListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一个家庭删除！');
		    return false;
		}
		var dataIdArray = new Array();
		for(var i=0; i < gridSelection.length; i++){
			var holder = gridSelection[i].get("familyHouseHolder");
			if(holder != userName){
				Ext.Msg.alert("系统提示","您不是家庭："+gridSelection[i].get("familyName") + " 的户主，无权删除！");
				return;
			}
			dataIdArray.push(gridSelection[i].get("familyId"));
		}
		var familyIds = dataIdArray.join(",");
		Ext.Msg.confirm("系统提示","确定要删除所选家庭信息？将会同时删除该家庭的所有家庭成员并且不可恢复！",function(btn){
			if(btn == "yes" || btn == "ok"){
				deleteFamilyInfo(url, familyIds);
			}
		});
	};
	/**
	 * 查看所有家庭信息
	 * @param {} url
	 */
	this.viewAllFamily = function(url){
		familyListStore.baseParams.viewAll = "yes";
		familyListStore.baseParams.userId = "";
		familyListStore.baseParams.start = 0;
		familyListStore.baseParams.limit = 50;
		familyListStore.reload();
	};
	/**
	 * 查看我的家庭
	 */
	this.viewMyFamily = function(){
		familyListStore.baseParams.viewAll = "no";
		familyListStore.baseParams.userId = userName;
		familyListStore.baseParams.start = 0;
		familyListStore.baseParams.limit = 50;
		familyListStore.reload();
	};
	/**
	 * 邀请用户加入我的家庭
	 * @param {} url
	 */
	this.inviteFamilyMember = function(url){
		var gridSelectionModel = familyListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一个家庭！');
		    return false;
		}
		var dataIdArray = new Array();
		var dataNameArray = new Array();
		for(var i=0; i < gridSelection.length; i++){
			var holder = gridSelection[i].get("familyHouseHolder");
			if(holder != userName){
				Ext.Msg.alert("系统提示","您不是家庭："+gridSelection[i].get("familyName") + " 的户主，无权邀请！");
				return;
			}
			dataIdArray.push(gridSelection[i].get("familyId"));
			dataNameArray.push(gridSelection[i].get("familyName"));
		}
		
		var familyIds = dataIdArray.join(",");
		var familyNames = dataNameArray.join(",");
		
		var userReader = new Ext.data.JsonReader({
			totalProperty : "totalCount",
			root : "userList"
		},[
			{name:"userId"},//唯一id
			{name:"userCode"},//用户编号
			{name:"userName"},//用户名
			{name:"telphoneNo"},//电话
			{name:"phoneNo"},//手机
			{name:"privence"},//省
			{name:"city"},//城市
			{name:"address"},//地址
			{name:"zip"},//邮编
			{name:"email"}//电子邮件
		]);
		
		var proxyUrl = path+"/family_manage/familyUserList.action?method=familyUserList";
		/**
		 * userStore:用户数据仓库
		 */
		var userStore = new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({
				url:proxyUrl
			}),
			baseParams:{
				start:0,
				limit:50,
				userId : userName
			},
			reader:userReader
		});
		
		/**
		 * userSM:数据展现样式
		 */
		var userSM = new Ext.grid.CheckboxSelectionModel();
		/**
		 * userCM:数据列展示样式
		 */
		var userCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),userSM,{
			dataIndex:"userId",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			header:"用户名称",
			dataIndex:"userName",
			width:150
		},{
			header:"电话号码",
			dataIndex:"telphoneNo",
			width:150
		},{
			header:"手机号码",
			dataIndex:"phoneNo",
			width:180
		},{
			header:"省",
			dataIndex:"privence",
			sortable:true,
			width:80
		},{
			header:"市",
			dataIndex:"city",
			width:130
		},{
			header:"具体地址",
			dataIndex:"address",
			width:130
		},{
			header:"邮编号码",
			dataIndex:"zip",
			width:130
		},{
			header:"电子邮件",
			dataIndex:"email",
			width:130
		}]);
		
		/**
		 * userGrid: 菜单展示列表
		 */
		var userGrid = new Ext.grid.GridPanel({
			collapsible:true,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			//width:Ext.get("user_div").getWidth(),
			//height:Ext.get("user_div").getHeight()-20,
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,		
			store:userStore,
			//renderTo:"user_div",
			cm:userCM,
			sm:userSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:userStore,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				nextText:"下一页",
				prevText:"上一页",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"邀请用户加入我的家庭",
				iconCls:"table_gear",
				tooltip:"邀请选中用户加入我的家庭",
				handler:function(){
					inviteMember(userGrid, "inviteFamilyMemberWindow", familyIds, familyNames, url);
				}
			}]
		});
		
		showFamilyManageWindow("inviteFamilyMemberWindow","邀请用户加入我的家庭",450, 320, userGrid);
		userStore.load();
	};
	/**
	 * 邀请用户加入家庭
	 * @param {} grid
	 * @param {} windowId
	 */
	function inviteMember(grid, windowId, familyIds, familyNames, url){
		var gridSelectionModel = grid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一个家庭！');
		    return false;
		}
		var dataIdArray = new Array();
		for(var i=0; i < gridSelection.length; i++){
			dataIdArray.push(gridSelection[i].get("userName"));
		}
		var userIds = dataIdArray.join(",");
		
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		
		Ext.Ajax.request({
			params:{familyId:familyIds, familyName:familyNames, userId:userIds, sponsor:userName, menuId: currentMenuId},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","已向所选用户发出邀请，请等待邀请结果！");
					}
					//familyListStore.reload();
					Ext.getCmp(windowId).close();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","向所选用户发出邀请失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","向所选用户发出邀请失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
		
	}
	/**
	 * 查看我的邀请
	 * @param {} url
	 */
	this.viewMyInvite = function(url){
		parent.fromMenuId = currentMenuId;
		parent.goToTabPanel(url, null, true);
	};
	
	
	/**
	 * 保存数据
	 * @param {} windowId
	 * @param {} form
	 */	
	function saveFamilyInfo(windowId, form){
		Ext.MessageBox.show({
			msg:"正在保存家庭信息，请稍候...",
			progressText:"正在保存家庭信息，请稍候...",
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
					var msg = "家庭信息保存成功，现在你可以邀请别人加入您的家庭！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							familyListStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "家庭信息保存失败，请检查您所填信息是否完整无误！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var msg = "家庭信息保存失败，请检查您的网络连接或者联系管理员！";
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
	 * 删除家庭信息
	 * @param {} url
	 * @param {} familyIds
	 */
	function deleteFamilyInfo(url, familyIds){
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		
		Ext.Ajax.request({
			params:{familyId:familyIds},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","家庭信息已成功删除！");
					}
					familyListStore.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","家庭信息删除失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","家庭信息删除失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	}
	
	/**
	 * 获取家庭信息表单
	 * @param {} url
	 * @param {} isNull
	 * @param {} readOnly
	 * @return {}
	 */
	function getFamilyManageForm(url, isNull, readOnly){
		var familyManageForm = new Ext.form.FormPanel({
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
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyName",
						anchor:"90%",
						fieldLabel:"家庭名称",
						maxLength:200,
						readOnly:readOnly,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyHouseHolder",
						anchor:"90%",
						fieldLabel:"家庭户主",
						value:userName,
						maxLength:200,
						readOnly:true,
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyTel",
						anchor:"90%",
						fieldLabel:"联系电话",
						maxLength:200
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'datefield',
						format:"Y-m-d",
						name:"familyCreateDate",
						id:"familyCreateDate_field",
						anchor:"90%",
						readOnly:true,
						value:new Date(),
						fieldLabel:"创建日期"
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
						xtype: 'textfield',
						name:"familyAddress",
						anchor:"95%",
						fieldLabel:"家庭地址",
						maxLength:200
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:1,
					height:70,
					items:[{
						xtype: 'textarea',
						name:"familyComment",
						anchor:"95%",
						fieldLabel:"家庭简介",
						maxLength:200
					},{
						xtype:"hidden",
						name:"familyId"
					}]
				}]
			}]
		});
		return familyManageForm;
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
	function showFamilyManageWindow(id, title, width, height, items, html, buttons){
		var codeListWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			//html:html,
			buttons:buttons,
			modal:true,
			//animateTarget:"giftmanage_div",//动画展示
			layout:"fit",
			resizable:false
		});
		codeListWindow.show();
	}
}
/**
 * 查看我的邀请信息
 */
function viewInvitation(){
	var url = path + "/invitation/invitationList.action?method=invitationList";
	Ext.Ajax.request({
		params:{userId:userName, menuId:currentMenuId, status:"1"},
		timeout:60000,
		url:url,
		success:function(response, options){
			var msg = Ext.util.JSON.decode(response.responseText);
			if(msg.success){
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
					family_manage();
				}else{
					if(msg.totalCount > 0){
						Ext.Msg.confirm("系统提示","您有未处理的系统邀请信息，现在处理？",function(btn){
							if(btn == "yes" || btn == "ok"){
								parent.fromMenuId = currentMenuId;
								parent.goToTabPanel("/invitation/invitation.action?method=begin", null, true);
							}else{
								//family_manage();
							}
						});
					}else{
						//family_manage();
					}
				}
			}else{
				//family_manage();
			}
		},failure: function(response, options){
			try{
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
				}else{
					Ext.Msg.alert("系统提示","系统错误，请联系管理员！");
				}
			}catch(e){
				Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
			}
		}
	});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	viewInvitation();
	family_manage();
});