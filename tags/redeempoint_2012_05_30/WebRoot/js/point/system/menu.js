function menuManage(){
	/**
	 * menuReader:菜单数据解析器
	 */
	var menuReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "menuList"
	},[
		{name:"menuId"},//唯一id
		{name:"menuName"},//菜单名称
		{name:"pagePath"},//菜单路径
		{name:"menuLevel"},//菜单等级
		{name:"parentMenuId"},//父级菜单
		{name:"parentMenuName"},//父级菜单
		{name:"isLeave"},//是否子节点
		{name:"isShow"},//是否显示
		{name:"comment"}//备注
	]);
	var proxyUrl = path+"/menu/menuManage.action?method=menuList";
	/**
	 * menuStore:菜单数据仓库
	 */
	var menuStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		listeners:{
			"loadexception":function(loader, node, response){
				httpStatusCodeHandler(response.status);
			}
		},
		reader:menuReader
	});
	/**
	 * menuSM:数据展现样式
	 */
	var menuSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * menuCM:数据列展示样式
	 */
	var menuCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),menuSM,{
		dataIndex:"menuId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"菜单名称",
		dataIndex:"menuName",
		width:80
	},{
		header:"菜单路径",
		dataIndex:"pagePath",
		width:180
	},{
		header:"菜单级别",
		groupable: false,
		dataIndex:"menuLevel",
		hidden:true,
		hideable:false,
		width:200
	},{
		header:"上级菜单",
		groupable: false,
		dataIndex:"parentMenuName",
		sortable:true,
		renderer:showParentMenuName,
		width:80
	},{
		header:"是否叶子菜单",
		groupable: false,
		dataIndex:"isLeave",
		renderer:isYesOrNo,
		width:50
	},{
		header:"是否显示",
		groupable: false,
		dataIndex:"isShow",
		renderer:isYesOrNo,
		width:50
	}]);
	/**
	 * menuGrid: 菜单展示列表
	 */
	var menuGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("menu_div").getWidth(),
		height:Ext.get("menu_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:menuStore,
		renderTo:"menu_div",
		cm:menuCM,
		sm:menuSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:menuStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[]
		/*,
		tbar:[{
			text:"测试按钮1",
			hidden:true,
			id:"menu_addMenu",
			iconCls:"table_add",
			tooltip:"测试按钮1"
		},{
			text:"测试按钮2",
			hidden:true,
			id:"menu_editMenu",
			iconCls:"table_edit",
			tooltip:"测试按钮2"
		}]
		*/
	});
	/**
	 * 显示是否叶子节点
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {String}
	 */
	function isYesOrNo(value,metadata,record,rowIndex,colIndex,store){
		if(value=="1"){
			return "是";
		}else{
			return "否";
		}
	}
	/**
	 * 显示上级菜单的名称
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {String}
	 */
	function showParentMenuName(value,metadata,record,rowIndex,colIndex,store){
		if(!value || value.trim() == ""){
			return "会员积分兑换系统";
		}else{
			return value;
		}
	}
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonStore = buttonRight();
	/**
	 * 加载权限按钮,加载页面数据
	 * see buttonRight.js
	 */
	loadButtonRight(buttonStore, menuStore, menuGrid, "menu_div");
	
	/**
	 * 增加菜单
	 * @param {} buttonUrl : 处理该请求的url
	 */
	this.addMenu = function(url){
		var form = showMenuForm(url, false);
		var button = [{
			text:"保存",
			handler:function(){
				if(form.form.isValid()){
					saveMenu("addMenuWindow", form)
				}
			}
		},{
			text:"关闭本窗口",
			handler:function(){
				var menuWindow = Ext.getCmp("addMenuWindow");
				if(menuWindow){
					menuWindow.close();
				}
			}
		}];
		showMenuWindow("addMenuWindow", "添加菜单", 550, 280, form, button);
	}
	/**
	 * 删除菜单
	 * @param {} url 进行删除菜单的系统url
	 */
	this.deleteMenu = function(url){
		var gridSelectionModel = menuGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条菜单信息！');
		    return false;
		}
		Ext.Msg.confirm("系统提示信息","确定要删除所选菜单信息？删除菜单信息的同时将会删除它们的按钮信息！",function(btn){
			if(btn == "ok" || btn == "yes"){
				var menuids = [];
				for(var i=0;i<gridSelection.length; i++){
					var menuId = gridSelection[i].get("menuId");
					menuids.push(menuId);
				}
				var deleteMenu = menuids.join(",");
				Ext.MessageBox.show({
					msg:"正在删除所选菜单信息，请稍候...",
					progressText:"正在删除所选菜单信息，请稍候...",
					width:300,
					wait:true,
					waitConfig: {interval:200},
					icon:Ext.Msg.INFO
				});
				Ext.Ajax.request({
					params:{menuIds:deleteMenu},
					timeout:60000,
					url:url,
					success:function(response,options){
						Ext.MessageBox.hide();
						var msg = Ext.util.JSON.decode(response.responseText);
						if(msg && msg.msg){
							Ext.Msg.alert("提示信息",msg.msg);
						}else{
							Ext.Msg.alert("提示信息","所选菜单删除成功！");
							menuStore.reload();
						}
					},failure:function(response,options){
						Ext.Msg.hide();
						Ext.Msg.alert("提示信息","所选菜单删除失败！");
						return;
					}
				});
			}
		});
	}
	/**
	 * 编辑菜单信息
	 * @param {} url
	 */
	this.editMenu = function(url){
		var gridSelectionModel = menuGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length != 1){
			Ext.MessageBox.alert('提示','请选择一条菜单信息！');
		    return false;
		}
		var form = showMenuForm(url, false);
		var button = [{
			text:"保存",
			handler:function(){
				if(form.form.isValid()){
					var menuId = form.form.findField("menuId").getValue();
					var parentMenuId = form.form.findField("parentMenuId").getValue();
					if(menuId == parentMenuId){
						Ext.Msg.alert("系统提示信息","不能选择当前菜单为上级菜单！");
						return false;
					}
					saveMenu("editMenuWindow", form);
				}
			}
		},{
			text:"关闭本窗口",
			handler:function(){
				var menuWindow = Ext.getCmp("editMenuWindow");
				if(menuWindow){
					menuWindow.close();
				}
			}
		}];
		showMenuWindow("editMenuWindow", "添加菜单", 550, 280, form, button);
		
		form.getForm().loadRecord(gridSelection[0]);
		var node = {};
		node.text = gridSelection[0].get("parentMenuName") || "会员积分兑换系统";
		node.id = gridSelection[0].get("parentMenuId");
		
		form.form.findField("parentMenuId").setValue(node);
		form.form.findField("parentMenuId").tree.expandAll();
	};
	
	/**
	 * 菜单窗口, 用于新增，修改
	 * @param {} id 窗口ID
	 * @param {} title 窗口名字
	 * @param {} width 窗口宽度
	 * @param {} height 窗口高度
	 * @param {} items 窗口的内部
	 * @param {} buttons 窗口的按钮
	 */
	function showMenuWindow(id, title, width, height, items, buttons){
		var menuWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			buttons:buttons,
			modal:true,
			layout:"fit",
			resizable:false
		});
		menuWindow.show();
	}
	/**
	 * 菜单表单
	 * @param {} url
	 * @param {} isNull
	 * @return {}
	 */
	function showMenuForm(url,isNull){
		var menuForm = new Ext.form.FormPanel({
			frame: true,
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			url:url,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"menuName",
						anchor:"90%",
						maxLength:50,
						fieldLabel:"菜单名称",
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'treeField',
						name:"parentMenuId",
						displayField:"menuName",
						valueField:"menuId",
						hiddenName:"parentMenuId",
						dataUrl:path+"/menu/menuComboTree.action?method=menuComboTree",
						listHeight:180,
						//selectNodeModel:"all",
						treeRootConfig:{
							id:" ",
							draggable:false,
							singleClickExpand:true,
							text:"会员积分兑换系统"
						},
						anchor:"90%",
						fieldLabel:"上级菜单",
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"isLeave",
						anchor:"90%",
						fieldLabel:"叶子节点",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"isLeave",//这个值就是传递给后台获取的值
						mode: "local",
						store:new Ext.data.SimpleStore({
							fields:["codeid","codename"],
							data:[["1","是"],["0","否"]]
						}),
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"isShow",
						anchor:"90%",
						fieldLabel:"是否显示",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"isShow",//这个值就是传递给后台获取的值
						mode: "local",
						store:new Ext.data.SimpleStore({
							fields:["codeid","codename"],
							data:[["1","是"],["0","否"]]
						}),
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"pagePath",
						anchor:"90%",
						maxLength:500,
						fieldLabel:"菜单路径",
						allowBlank:isNull
					},{
						xtype: 'hidden',
						name:"menuId"
					},{
						xtype: 'hidden',
						name:"menuLevel"
					},{
						xtype: 'hidden',
						name:"parentMenuName"
					}]
				}]
			}]
		});
		return menuForm;
	}
	/**
	 * 保存菜单信息
	 * @param {} menuWindow 弹出窗口ID
	 * @param {} form 表单内容
	 */
	function saveMenu(menuWindow, form){
		Ext.MessageBox.show({
			msg:"正在保存菜单信息，请稍候...",
			progressText:"正在保存菜单信息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				Ext.Msg.alert('系统提示信息', '菜单信息保存成功!', function(btn, text) {
					if (btn == 'ok') {
						var msg = Ext.decode(action.response.responseText);
						menuStore.reload();
						Ext.getCmp(menuWindow).close();
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				Ext.Msg.alert('系统提示信息', "菜单信息保存过程中出现异常!");
			}
		});
	}
}
/**
 * 菜单管理入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';//side
	menuManage();
});