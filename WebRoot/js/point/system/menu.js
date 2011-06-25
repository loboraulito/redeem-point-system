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
		{name:"isLeave"}//是否子节点
	]);
	var proxyUrl = path+"/menu/menuManage.action?method=menuList";
	/**
	 * menuStore:菜单数据仓库
	 */
	var menuStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
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
		width:150
	},{
		header:"菜单路径",
		dataIndex:"pagePath",
		width:150
	},{
		header:"菜单级别",
		groupable: false,
		dataIndex:"menuLevel",
		hidden:true,
		hideable:false,
		width:180
	},{
		header:"上级菜单",
		groupable: false,
		dataIndex:"parentMenuName",
		sortable:true,
		width:80
	},{
		header:"是否叶子菜单",
		groupable: false,
		dataIndex:"isLeave",
		renderer:isYesOrNo,
		width:130
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
		renderTo:menu_div,
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
	
	function isYesOrNo(value,metadata,record,rowIndex,colIndex,store){
		if(value=="1"){
			return "是";
		}else{
			return "否";
		}
	}
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonStore = buttonRight();
	buttonStore.load({
		params:{roleId:userRole,menuId:parent.menuId},
		callback:function(buttonRecords,buttonOptions,buttonSuccess){
			//这里处理按钮的显示和隐藏
			//alert(buttonRecords.length);
			var tbar = menuGrid.getTopToolbar();
			if(!tbar){
				tbar = new Ext.Toolbar();
			}
			var hasButtonShow = false;
			for(var i=0;i<buttonRecords.length;i++){
				//是否显示
				var isShow = buttonRecords[i].get("isShow");
				//var button = "";
				if(isShow && isShow == "yes"){
					hasButtonShow = true;
					var buttonId = buttonRecords[i].get("buttonName");
					var buttonText = buttonRecords[i].get("buttonText");
					var buttonUrl = buttonRecords[i].get("buttonUrl");
					var buttonCss = buttonRecords[i].get("buttonIconCls");
					var buttonHandler = buttonRecords[i].get("handler");
					var button = new Ext.Button({
						text:buttonText,
						id:buttonId,
						iconCls:buttonCss,
						tooltip:buttonText,
						handlerFunction:buttonHandler,
						handlerUrl:buttonUrl,
						listeners:{
							"click":function(bt, e){
								var handlerFun = bt.handlerFunction;
								if(handlerFun && handlerFun!= "" && typeof (eval(""+handlerFun+"")) == "function"){
									eval(""+handlerFun+"('"+bt.handlerUrl+"')");
								}
							}
						}
					});
					tbar.add(button);
				}else{
					continue;
				}
				
				tbar.addSeparator();
			}
			if(!hasButtonShow){
				menuGrid.setHeight(Ext.get("menu_div").getHeight());
				menuGrid.render();
			}
			menuStore.load({
				params:{start:0,limit:50},
				callback:function(records,options,success){
					//alert(proxyUrl);
				}
			});
		}
	});
	/**
	 * 增加菜单
	 * @param {} buttonUrl : 处理该请求的url
	 */
	function addMenu(url){
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
			layout:"fit"
		});
		menuWindow.show();
	}
	
	function showMenuForm(url,isNull){
		var menuForm = new Ext.form.FormPanel({
			frame: true,
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			url:path+url,
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
						treeRootConfig:{
							id:" ",
							draggable:false,
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
				Ext.Msg.alert('Success', 'Save Successful!', function(btn, text) {
					if (btn == 'ok') {
						var msg = Ext.decode(action.response.responseText);
						menuStore.reload();
						Ext.getCmp(menuWindow).close();
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				Ext.Msg.alert('Warning', "error");
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