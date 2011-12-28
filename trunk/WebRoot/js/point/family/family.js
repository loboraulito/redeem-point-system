/**
 * 家庭管理入口
 */
function family(){
	//加载性别下拉框
	var sexs = parent.sexStore;
	sexs.load({params:{codeId:"4af4953627d6f4ff0127d6fbc935000a"}});
	
	
	/**
	 * 家庭成员数据解析
	 */
	var memberListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "memberList",
		successProperty:"success"
	},[
		{name:"familyMemberId"},//家庭成员ID
		{name:"familyId"},//家庭ID
		{name:"familyName"},//家庭
		{name:"familyMemberName"},//成员姓名
		{name:"systemMemberId"},//对应系统用户ID
		{name:"familyMemberCard"},//身份证
		{name:"familyMemberBirthdate"},//生日
		{name:"familyMemberBirthplace"},//出生地
		{name:"familyMemberSex"},//性别
		{name:"familyMemberHeight"},//身高
		{name:"familyMemberEducational"},//学历
		{name:"familyMemberProfession"},//职业
		{name:"familyMemberDeaddate"}//死亡日期
	]);
	
	/**
	 * 空数据，当主数据位空时，使用该数据以避免报错
	 * @type 
	 */
	var simpleData = {"totalCount":0,"memberList":[],"success":true};
	
	var loadParam = {};
	loadParam.start = 0;
	loadParam.limit = 50;
	loadParam.userId = userName;
	
	/**
	 * 家庭成员数据存储
	 * 是否需要分组显示？
	 */
	var memberListStore = new Ext.data.GroupingStore({
		url:path+"/family_member/familyMemberList.action?method=familyMemberList",
		groupField:"familyName",
		sortInfo:{field: 'systemMemberId', direction: "ASC"},
		baseParams:loadParam,
		reader:memberListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					var o = Ext.util.JSON.decode(action.responseText);
					if(!o.success){
						Ext.Msg.alert('错误提示',o.msg, function(btn){
							memberListStore.loadData(simpleData);
							if(btn == "yes" || btn == "ok"){
								if(o.msg1){
									//Ext.MessageBox.buttonText.yes = '按钮一';
	    							//Ext.MessageBox.buttonText.no = '按钮二';
								    Ext.MessageBox.buttonText={
								        yes: "申请加入家庭",
										no: "创建家庭",
										cancel:"取消"
								    };
	
									Ext.Msg.show({
										title:"系统提示",
										msg:o.msg1,
										buttons: Ext.Msg.YESNOCANCEL,
										fn: processResult,
										icon: Ext.MessageBox.QUESTION
									});
								}
							}
						});
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
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
		groupTextTpl: '{text} 有 {[values.rs.length]} 位家庭成员'//用于渲染分组信息的模板，默认为'{text}'
	});
	
	function processResult(btn, text){
		if(btn == "yes"){
			
		}else if(btn == "no"){
			//window.location = path + "/family_manage/familyManage.action?method=begin";
			parent.goToTabPanel("/family_manage/familyManage.action?method=begin");
		}else{
			
		}
	}
	
	var memberListSM = new Ext.grid.CheckboxSelectionModel();
	var memberListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),memberListSM,{
		dataIndex:"familyMemberId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"familyId",
		hidden:true,
		hideable:false
	},{
		header:"家庭",
		dataIndex:"familyName",
		width:150
	},{
		header:"成员姓名",
		dataIndex:"familyMemberName",
		width:150
	},{
		header:"系统用户ID",
		dataIndex:"systemMemberId",
		width:150
	},{
		header:"身份证",
		dataIndex:"familyMemberCard",
		width:150
	},{
		header:"生日",
		dataIndex:"familyMemberBirthdate",
		width:150
	},{
		header:"出生地",
		dataIndex:"familyMemberBirthplace",
		width:150
	},{
		header:"性别",
		dataIndex:"familyMemberSex",
		width:150
	},{
		header:"身高",
		dataIndex:"familyMemberHeight",
		width:150
	},{
		header:"学历",
		dataIndex:"familyMemberEducational",
		width:150
	},{
		header:"职业",
		dataIndex:"familyMemberProfession",
		width:150
	},{
		dataIndex:"familyMemberDeaddate",
		hidden:true,
		hideable:false
	}]);
	/**
	 * 家庭成员列表
	 */
	var memberListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("family_div").getWidth(),
		height:Ext.get("family_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:memberListStore,
		renderTo:"family_div",
		cm:memberListCM,
		sm:memberListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		view:groupView,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:memberListStore,
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
	loadButtonRight(buttonRightStore, memberListStore, memberListDataGrid, "family_div", loadParam);
	
	/**
	 * 创建家庭
	 * @param {} url
	 */
	this.createFamily = function(url){
		parent.goToTabPanel(url, null, true);
	};
	/**
	 * 申请加入家庭
	 * @param {} url
	 */
	this.familyApply = function(url){
		
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
			viewAll : "yes",
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
			width:70
		},{
			header:"创建日期",
			dataIndex:"familyCreateDate",
			width:70
		},{
			header:"户主",
			dataIndex:"familyHouseHolder",
			width:70
		},{
			header:"家庭地址",
			dataIndex:"familyAddress",
			width:100
		},{
			header:"联系电话",
			dataIndex:"familyTel",
			width:80
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
		
		var family_ListDataGrid = new Ext.grid.GridPanel({
			collapsible:true,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			//width:Ext.get("family_manage").getWidth(),
			//height:Ext.get("family_manage").getHeight()-20,
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,		
			store:familyListStore,
			//renderTo:"family_manage",
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
		        	id:"row__Tip",
		        	title:"家庭简介",
		        	border:true,
		        	minWidth:300,
		        	maxWidth:300,
		        	draggable:true,
		        	//autoHide: false,
			        //closable: true,
		        	items:[{
		        		xtype:"textarea",
		        		id:"family__CommentArea",
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
						Ext.getCmp("family__CommentArea").setValue(this.store.getAt(row).get("familyComment"));
					});
				}
			},
			tbar:[{
				text:"申请加入所选家庭",
				iconCls:"table_gear",
				tooltip:"申请加入所选家庭",
				handler:function(){
					doApplyFamily(family_ListDataGrid, "applyFamilyWindow", url);
				}
			}]
		});
		showFamilyMemberWindow("applyFamilyWindow","申请加入家庭",560, 320, family_ListDataGrid);
		familyListStore.load();
	};
	
	function doApplyFamily(grid, windowId, url){
		var gridSelectionModel = grid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一个家庭！');
		    return false;
		}
		var dataIdArray = new Array();
		var holder = new Array();
		var fname = new Array();
		for(var i=0; i < gridSelection.length; i++){
			dataIdArray.push(gridSelection[i].get("familyId"));
			if(gridSelection[i].get("familyHouseHolder") != userName){
				holder.push(gridSelection[i].get("familyHouseHolder"));
			}else{
				Ext.MessageBox.alert('系统提示','您已是家庭【'+gridSelection[i].get("familyName")+'】的户主，无需申请！');
		    	return false;
			}
			fname.push(gridSelection[i].get("familyName"));
		}
		var familyIds = dataIdArray.join(",");
		var holderNames = holder.join(",");
		var familyName = fname.join(",");
		
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		
		Ext.Ajax.request({
			params:{sponsor:userName,recipient:holderNames, menuId: currentMenuId, familyId:familyIds, familyName: familyName},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","已向所选家庭户主发出请求，请等待请求结果！");
					}
					//familyListStore.reload();
					Ext.getCmp(windowId).close();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","向所选家庭户主发出请求失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","向所选家庭户主发出请求失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	}
	
	/**
	 * 完善个人信息
	 * @param {} url
	 */
	this.finishMemberInfo = function(url){
		var memberInfoFrom = getMemberInfoForm(url, false, true);
		var button = [{
			text:"保存"
		},{
			text:"取消"
		}];
		showFamilyMemberWindow("editFamilyMemberInfo","完善个人信息",500, 350, memberInfoFrom, null, button);
		//var educational = parent.educationalStore;
		//educational.load({params:{codeId:"8ac388f134837dd6013483a54f85004f"}});
	};
	
	function getMemberInfoForm(url, isNull, readOnly){
		var familyMemberForm = new Ext.form.FormPanel({
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
						name:"familyMemberName",
						anchor:"90%",
						fieldLabel:"姓名",
						maxLength:50,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyMemberCard",
						anchor:"90%",
						fieldLabel:"身份证",
						maxLength:50
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
						xtype: 'datefield',
						format:"Y-m-d",
						name:"familyMemberBirthdate",
						anchor:"90%",
						fieldLabel:"生日",
						maxLength:50
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"familyMemberSex",
						anchor:"90%",
						fieldLabel:"性别",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"dataKey",//将codeid设置为传递给后台的值
						displayField:"dataValue",
						hiddenName:"familyMemberSex",//这个值就是传递给后台获取的值
						mode: "local",
						store:sexs
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
						xtype: 'numberfield',
						name:"familyMemberHeight",
						anchor:"90%",
						fieldLabel:"身高(CM)",
						maxLength:50
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"familyMemberEducational",
						anchor:"90%",
						fieldLabel:"学历",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"dataKey",//将codeid设置为传递给后台的值
						displayField:"dataValue",
						hiddenName:"familyMemberEducational",//这个值就是传递给后台获取的值
						mode: "local",
						store:educational
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyMemberProfession",
						anchor:"90%",
						fieldLabel:"职业",
						maxLength:500
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"familyMemberBirthplace",
						anchor:"90%",
						fieldLabel:"出生地",
						maxLength:500
					}]
				}]
			}]
		});
		return familyMemberForm;
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
	function showFamilyMemberWindow(id, title, width, height, items, html, buttons){
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
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	family();
});
