/**
 * 授权管理
 * 为用户授予角色，为角色授予菜单以及按钮, 以角色为中心点
 */
function authorize(){
	/**
	 * authorizeRoleReader - 角色信息解析器
	 */
	var authorizeRoleReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "roleList"
	},[
		{name:"roleId"},//唯一id
		{name:"roleName"},//菜单名称
		{name:"comment"}//菜单路径
	]);
	
	var proxyUrl = path+"/role/roleList.action?method=roleManageList";
	/**
	 * authorizeRoleStore:角色数据仓库
	 */
	var authorizeRoleStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		reader:authorizeRoleReader
	});
	
	/**
	 * authorizeRoleSM:数据展现样式
	 */
	var authorizeRoleSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * authorizeRoleCM:数据列展示样式
	 */
	var authorizeRoleCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),authorizeRoleSM,{
		dataIndex:"userId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"roleId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"menuId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"用户名",
		dataIndex:"userName",
		width:150
	},{
		header:"角色名称",
		dataIndex:"roleName",
		width:150
	},{
		header:"菜单名称",
		dataIndex:"menuName",
		width:150
	}]);
	
	/**
	 * authorizeRoleGrid: 角色展示列表
	 */
	/*
	var authorizeRoleGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("authorize_right_div").getWidth(),
		height:Ext.get("authorize_right_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:authorizeRoleStore,
		renderTo:authorize_right_div,
		cm:authorizeRoleCM,
		sm:authorizeRoleSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:authorizeRoleStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[{
			text:"用户授权",
			hidden:true,
			id:"authorize_user",
			iconCls:"table_edit",
			tooltip:"为用户赋予角色"
		},{
			text:"授权菜单",
			hidden:true,
			id:"authorize_rught_menu",
			iconCls:"table_edit",
			tooltip:"为角色赋予权限菜单"
		}]
	});
	*/
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	/*
	var buttonStore = buttonRight();
	buttonStore.load({
		params:{roleId:userRole,menuId:parent.menuId},
		callback:function(buttonRecords,buttonOptions,buttonSuccess){
			//这里处理按钮的显示和隐藏
			//alert(buttonRecords.length);
			for(var i=0;i<buttonRecords.length;i++){
				//alert(buttonRecords[i].get("buttonName"));
				var button = Ext.getCmp(buttonRecords[i].get("buttonName"));
				if(button){
					//button.hidden = false;
					button.show();
				}
			}
			authorizeRoleStore.load({
				params:{start:0,limit:50},
				callback:function(records,options,success){
					//alert(proxyUrl);
				}
			});
		}
	});
	*/
	
	
	
	
	
	var root = new Ext.tree.AsyncColumnTreeNode({
		text:'Tasks'
	});
	var store = new Ext.data.TreeJsonStore({
		root:"data",
		rootNode:root,
		totalProperty:"totalCount",
		proxy:new Ext.data.HttpProxy({
			url:path+"/right/authorizeList.action?method=authorizeList"
		}),
		listeners:{
			"load":function(){
				
			}
		},
		fields: ['userId', 'userName', 'roleId', 'roleName', 'menuId', 'menuName']
	});
	var tree = new Ext.tree.ColumnCheckTree({
		width:900,
		height:600,
		rootVisible:true,
		autoScroll:true,
		checkModel:"cascade",
		title:"Example Tasks",
		renderTo:"authorize_right_div",
		columns:[{
            header:'Task2111',
            width:330,
            dataIndex:'userId'
        },{
        	header:'Duration',
        	width:100,
        	dataIndex:'userName'
        },{
        	header:'age',
        	width:100,
        	dataIndex:'roleName'
        },{
            header:'birth',
            width:100,
            dataIndex:'menuName'
        }],
		store:store,
		bbar:new Ext.PagingToolbar({
			store : store,
			displayInfo : true 
		}),
		root: root
	});
	root.expand(); 
	
}

/**
 * 授权管理
 * 显示用户名，角色名即可
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	authorize();
});