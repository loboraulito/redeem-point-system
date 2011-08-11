/**
 * 数据字典
 */
function codeListDataManage(){
	/**
	 * 数据字典
	 */
	var codeListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "codeList"
	},[
		{name:"codeId"},//数据字典ID
		{name:"codeName"}//数据字典名称
	]);
	
	var codeListDataReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "codeListData"
	},[
		{name:"dataId"},//数据字典数据ID
		{name:"dataKey"},//数据字典数据key
		{name:"dataValue"},//数据字典值
		{name:"codeId"},//数据字典ID
		{name:"codeName"},//数据字典
		{name:"parentDataKey"},//父级数据字典
		{name:"parentDataValue"},//父级数据字典
		{name:"remark"}//备注
	]);
	/**
	 * 数据字典列表
	 */
	var proxyCodeUrl = path+"/codelist/codeList.action?method=codeList";
	/**
	 * 数据列表
	 */
	var proxyDataUrl = path+"/codelist/codeDataList.action?method=codeDataList";
	/**
	 * 数据标准存储
	 */
	var codeListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyCodeUrl
		}),
		reader:codeListReader
	});
	/**
	 * 数据标准数据存储
	 */
	var codeListDataStore = new Ext.data.GroupingStore({
		url:proxyDataUrl,
		reader:codeListDataReader,
		groupField:"codeName",
		sortInfo:{field: 'dataId', direction: "ASC"}
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
		groupTextTpl: '{text} ({[values.rs.length]} {text})'//用于渲染分组信息的模板，默认为'{text}'
	});
	
	/**
	 * 数据展现形式 - 多选框
	 */
	var codeSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * 数据字典展现形式
	 */
	var codeListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),codeSM,{
		dataIndex:"codeId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"数据标准名称",
		dataIndex:"codeName",
		width:150
	}]);
	/**
	 * 数据字典数据展现形式
	 */
	var codeDataCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),codeSM,{
		dataIndex:"dataId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"数据标准",
		dataIndex:"codeName",
		width:150
	},{
		header:"数据标准编码",
		dataIndex:"dataKey",
		width:150
	},{
		header:"数据标准值",
		dataIndex:"dataValue",
		width:150
	},{
		header:"上级数据标准值",
		dataIndex:"parentDataValue",
		width:150
	},{
		header:"数据标备注",
		dataIndex:"remark",
		width:150
	}]);
	
	/**
	 * 数据字典数据列表
	 */
	var codeListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("codelist_div").getWidth(),
		height:Ext.get("codelist_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:codeListDataStore,
		renderTo:"codelist_div",
		cm:codeDataCM,
		sm:codeSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		view:groupView,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:codeListDataStore,
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
	loadButtonRight(buttonRightStore, codeListDataStore, codeListDataGrid, "codelist_div");
	
	this.codeListManage = function(url){
		var codeListGrid = new Ext.grid.GridPanel({
			collapsible:true,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			//width:Ext.get("codelist_div").getWidth(),
			//height:Ext.get("codelist_div").getHeight()-20,
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,		
			store:codeListStore,
			//renderTo:"codelist_div",
			cm:codeListCM,
			sm:codeSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:codeListStore,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				nextText:"下一页",
				prevText:"上一页",
				emptyMsg:"无相关记录"
			}),
			tbar:["-"]
		});
		var buttons = [{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("codeListManageWindow");
				if(w){
					w.close();
				}
			}
		}];
		showCodeListWindow("codeListManageWindow","数据标准管理",400,300,codeListGrid,buttons);
		var codeListMenuId = "";
		//查询数据标准列表管理页面的权限按钮
		Ext.Ajax.request({
			params:{menuPath:url},
			timeout:60000,
			url:path + "/menu/findMenuId.action?method=findMenuId",
			success:function(response, options){
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					codeListMenuId = msg.menuId;
					/**
					 * 执行权限按钮
					 */
					loadButtonRight(buttonRightStore, codeListStore, codeListGrid, "codeListManageWindow", null, codeListMenuId);
				}
			},failure: function(response, options){
				codeListMenuId = "";
			}
		});
		//codeListStore.load({params:{start:0,limit:50}});
	}
	
	/**
	 * 公用窗口
	 * @param {Object} id  唯一标识
	 * @param {Object} title 窗口显示的名称
	 * @param {Object} width 窗口宽度
	 * @param {Object} height 窗口高度
	 * @param {Object} items 窗口内容
	 * @param {Object} buttons 窗口按钮
	 */
	function showCodeListWindow(id, title, width, height, items, buttons){
		var codeListWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
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
 * @author cdai
 * 数据字典管理
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	codeListDataManage();
});