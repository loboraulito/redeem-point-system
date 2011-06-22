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
		dataIndex:"parentMenuId",
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
			for(var i=0;i<buttonRecords.length;i++){
				//alert(buttonRecords[i].get("buttonName"));
				var button = Ext.getCmp(buttonRecords[i].get("buttonName"));
				var buttonText = buttonRecords[i].get("buttonText");
				if(button){
					//button.hidden = false;
					button.setText(buttonText);
					button.handlerUrl = buttonRecords[i].get("buttonUrl");
					button.show();
				}
				/*
				var tbar = menuGrid.getTopToolbar();
				if(!tbar){
					tbar = new Ext.Toolbar();
				}
				*/
				/*
				tbar.addButton({
					text:buttonRecords[i].get("buttonText"),
					id:buttonRecords[i].get("buttonName")
				});
				*/
				/*
				tbar.add({
					text:buttonRecords[i].get("buttonText"),
					id:buttonRecords[i].get("buttonName")
				});
				*/
			}
			menuStore.load({
				params:{start:0,limit:50},
				callback:function(records,options,success){
					//alert(proxyUrl);
				}
			});
		}
	});
}
/**
 * 菜单管理入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	menuManage();
});