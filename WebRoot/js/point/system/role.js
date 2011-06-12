/**
 * 角色管理
 */
function roleManage(){
	/**
	 * roleReader - 角色信息解析器
	 */
	var roleReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "roleList"
	},[
		{name:"roleId"},//唯一id
		{name:"roleName"},//菜单名称
		{name:"comment"}//菜单路径
	]);
	
	var proxyUrl = path+"/role/roleList.action?method=roleManageList";
	/**
	 * roleStore:角色数据仓库
	 */
	var roleStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		reader:roleReader
	});
	
	/**
	 * roleSM:数据展现样式
	 */
	var roleSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * roleCM:数据列展示样式
	 */
	var roleCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),roleSM,{
		dataIndex:"roleId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"角色名称",
		dataIndex:"roleName",
		width:150
	},{
		header:"备注",
		dataIndex:"comment",
		width:150
	}]);
	
	/**
	 * roleGrid: 角色展示列表
	 */
	var roleGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("role_div").getWidth(),
		height:Ext.get("role_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:roleStore,
		renderTo:role_div,
		cm:roleCM,
		sm:roleSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:roleStore,
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
	
	roleStore.load({
		params:{start:0,limit:50}
	});
	
}
/**
 * 角色管理
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	roleManage();
});