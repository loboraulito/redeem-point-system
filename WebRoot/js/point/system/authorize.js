/**
 * 授权管理
 * 为用户授予角色，为角色授予菜单以及按钮, 以角色为中心点
 */
function authorize(){
	
	var proxyUrl = path+"/right/authorizeMenu.action?method=showAuthorizeMenu";
	var proxyRoleUrl = path+"/right/authorizeRole.action?method=showAuthorizeRole";
	var proxyUserUrl = path+"/right/authorizeUser.action?method=showAuthorizeUser";
	var authorizeUrl = path+"/right/authorizeMenu.action?method=showAuthorizeMenu";
	
	
	var loader = new Ext.tree.TreeLoader({
		url:proxyUrl,
		baseAttrs:{uiProvider:Ext.ux.TreeCheckNodeUI}
	});
	
	var root = new Ext.tree.AsyncTreeNode({
		id:"roleMenuTree",
		loader: loader,//new Ext.tree.TreeLoader({url:"extMenu/rootMenu.action?method=showRootMenu&nodeId=1"}),
		draggable:false,
		singleClickExpand:true,//用单击文本展开,默认为双击
		text:"系统菜单"
	});
	
	var tree = new Ext.tree.TreePanel({
		//title:title,
		//renderTo:authorize_right_div,//填充区域
		region:'center',
		animate:true,//动画效果
		autoScroll:true,//是否可自动滚动
		autoHeight:true,//自动高度
		collapsible:true,
		enableDD:false,//是否可拖曳
		containerScroll: true,
		rootVisible:false,//隐藏根节点
		singleExpand:false,//只显示一个树节点中的子节点,默认为显示全部
		checkModel: 'cascade',	//对树的级联多选 
		onlyLeafCheckable: false,//对树所有结点都可选 
		root:root,
		//width:250,
		lines : true,
		//hight:300,
		dropConfig: {appendOnly:true},
		border:false//没有边框
	});
	
	tree.on('beforeload',function(node){loader.url = proxyUrl});
	
	var treePanel = new Ext.Panel({
		autoScroll : true,
		items :[tree],
		//height:Ext.get("authorize_right_div").getHeight()-25,
		border:false
	});
	
	
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
	
	/**
	 * roleStore:角色数据仓库
	 */
	var roleStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyRoleUrl
		}),
		reader:roleReader,
		baseParams:{flag:"authorize_role"}
	});
	
	/**
	 * roleSM:数据展现样式
	 */
	var roleSM = new Ext.grid.CheckboxSelectionModel({
		singleSelect:true //单选框
	});
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
		//width:Ext.get("role_div").getWidth(),
		//height:Ext.get("role_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:roleStore,
		cm:roleCM,
		sm:roleSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		listeners :{
			"rowclick":function(grid, rowIndex, e){
				var gridSelectionModel = roleGrid.getSelectionModel();
				var gridSelection = gridSelectionModel.getSelections();
				if(gridSelection.length != 1){
		            Ext.MessageBox.alert('提示','请选择一条信息！');
		            return false;
		        }
		        var roleId = gridSelection[0].get("roleId");
		        //读取角色用户
		        loadAuthorizeUser(roleId);
		        //loadAuthorizeMenuOne(roleId);
		        loadAuthorizeMenuTwo(roleId);
			}
		},
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
	
	/**
	 * userReader - 用户信息解析器
	 */
	var userReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "userList"
	},[
		{name:"userId"},//唯一id
		{name:"userCode"},//菜单名称
		{name:"userName"},//菜单路径
		{name:"telphoneNo"},//菜单等级
		{name:"phoneNo"},//父级菜单
		{name:"privence"},//是否子节点
		{name:"city"},//是否子节点
		{name:"address"},//是否子节点
		{name:"zip"},//是否子节点
		{name:"email"}//是否子节点
	]);
	
	//var proxyUserUrl = path+"/user/userList.action?method=userManageList";
	/**
	 * userStore:用户数据仓库
	 */
	var userStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUserUrl
		}),
		reader:userReader,
		baseParams:{flag:"authorize_user"}
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
		//renderTo:user_div,
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
		})
	});
	
	var panel = new Ext.Panel({
		renderTo:"authorize_right_div",
		width:Ext.get("authorize_right_div").getWidth(),
		height:Ext.get("authorize_right_div").getHeight(),
		/*
		tbar:["查询",{
			text:"保存",
			iconCls:"table_save"
		}],
		*/
        border:false,
        layout : 'border',
        //bodyStyle: 'padding-bottom:0px;background:#eee;overflow-x:hidden;overflow-y:hidden;',
		autoScroll: true,
        items:[{
        	region : 'center',
        	title:"系统角色",
        	border:false,
        	layout:"border",
        	items:[{
        		region : 'north',
        		split : true,
        		layout:"fit",
        		border:false,
        		height:Ext.get("authorize_right_div").getHeight()/2-50,
        		items:[roleGrid]
        	},{
        		region : 'center',
        		split : true,
        		layout:"fit",
        		border:false,
        		items:[{
        			xtype:"tabpanel",
        			//title:"系统权限",
        			//layout:"fit",
        			activeTab:0,
        			border:false,
        			deferredRender:false,
        			layoutOnTabChange:true,//当activeTab改变的时候，执行doLayout
        			items:[{
        				layout:"fit",
        				title:"角色用户",
        				border:false,
        				items:[userGrid],
        				tbar:[{
	        				text:"添加用户",
	        				iconCls:"table_add"
	        			},"-",{
	        				text:"删除用户",
	        				iconCls:"table_delete"
	        			}]
        			},{
        				title:"角色菜单",
        				layout:"fit",
        				border:false,
        				items:[treePanel]
        			}]
        		}]
        	}]
        }]
	});
	
	roleStore.load({
		params:{start:0,limit:50,flag:"authorize_user"},
		callback:function(){
			
			loader.baseParams.rootId = "";
			loader.baseParams.roleId = "";
			loader.load(root, function(){
				tree.expandAll();
			});
			
		}
	});
	/**
	 * 读取角色用户
	 * @param {} roleId：角色ID
	 */
	function loadAuthorizeUser(roleId){
		userStore.load({
			params:{start:0,limit:50,roleId:roleId,flag:"authorize_user"}
		});
	}
	/**
	 * 第一种方案
	 * @param {} roleId
	 */
	function loadAuthorizeMenuOne(roleId){
		loader.baseParams.rootId = "";
		loader.baseParams.roleId = roleId;
		loader.load(root, function(){
			tree.expandAll();
		});
	}
	/**
	 * 第二种方案
	 * @param {} roleId
	 */
	function loadAuthorizeMenuTwo(roleId){
		//每次执行之前都应该把树的选中状态置空
		Ext.Ajax.request({
			params:{roleId:roleId,flag:"authorize_menu"},
			timeout:60000,
			url:authorizeUrl,
			success:function(response,options){
				var checkedNodes = tree.getChecked();
				for(var m=0;m<checkedNodes.length;m++){
					var node = checkedNodes[m];
					if(node){
						node.getUI().toggleCheck(false);
						node.attributes.checked=false;
					}
				}
				//Ext.MessageBox.hide();
				var authorizeMenus = Ext.util.JSON.decode(response.responseText);
				authorizeMenus = authorizeMenus.menus;
				alert(authorizeMenus);
				//Ext.Msg.alert("提示信息",authorizeMenus.length);
				for(var i=0;i<authorizeMenus.length;i++){
					var node = tree.getNodeById(authorizeMenus[i]);
					if(node){
						node.getUI().toggleCheck(true);
						node.attributes.checked=true;
					}
					//alert(node.attributes.text);
				}
			},failure:function(response,options){
				//Ext.Msg.hide();
				Ext.Msg.alert("提示信息","数据加载失败！");
				return;
			}
		});
	}
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