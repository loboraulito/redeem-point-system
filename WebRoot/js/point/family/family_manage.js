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
	/**
	 * 数据存储
	 */
	var familyListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/family_manage/familyList.action?method=familyList"
		}),
		reader:familyListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg);
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
		width:150
	},{
		header:"创建日期",
		dataIndex:"familyCreateDate",
		width:150
	},{
		header:"户主",
		dataIndex:"familyHouseHolder",
		width:150
	},{
		header:"家庭地址",
		dataIndex:"familyAddress",
		width:150
	},{
		header:"联系电话",
		dataIndex:"familyTel",
		width:150
	}]);
	
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
		tbar:[]
	});
	
	var loadParam = {};
	loadParam.start = 0;
	loadParam.limit = 50;
	loadParam.userId = userName;
	
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

	this.createFamily = function(url){
		var familyForm = getFamilyManageForm(url, false, false);
		showFamilyManageWindow("addFamilyInfo","创建家庭",600, 400, familyForm);
		markComponent("familyCreateDate_field");
	};
	function getFamilyManageForm(url, isNull, readOnly){
		var familyManageForm = new Ext.form.FormPanel({
			url:url,
			frame: true,
			labelAlign: 'right',
			labelWidth:90,
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

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	family_manage();
});