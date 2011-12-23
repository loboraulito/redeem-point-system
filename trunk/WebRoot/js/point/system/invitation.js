/**
 * 系统请求处理
 */
function invitation(){
	/**
	 * 数据解析
	 */
	var invitationListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "invitationList",
		successProperty:"success"
	},[
		{name:"id"},//ID
		{name:"sponsor"},//发起人ID
		{name:"recipient"},//接收人ID
		{name:"sponsorName"},//发起人ID
		{name:"recipientName"},//接收人ID
		{name:"sponsorTime"},//发起时间
		{name:"processTime"},//处理时间
		{name:"processStatus"},//处理状态
		{name:"invitationMenu"},//相关菜单ID
		{name:"processResultCode"},//处理结果
		{name:"invitationEvent"},//处理事件内容
		{name:"invitationReason"},//理由
		{name:"nextaction"}//下一步操作
	]);
	
	/**
	 * 空数据
	 * @type 
	 */
	var simpleData = {"totalCount":0,"invitationList":[],"success":true};
	/**
	 * 数据存储
	 */
	var invitationListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/invitation/invitationList.action?method=invitationList"
		}),
		reader:invitationListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg, function(btn){
						invitationListStore.loadData(simpleData);
					});
				}
			}
		}
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,{
		dataIndex:"id",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"sponsor",
		hidden:true,
		hideable:false
	},{
		dataIndex:"recipient",
		hidden:true,
		hideable:false
	},{
		header:"发起人",
		dataIndex:"sponsorName",
		width:150
	},{
		header:"接收人",
		dataIndex:"recipientName",
		width:150
	},{
		header:"发起时间",
		dataIndex:"sponsorTime",
		width:150
	},{
		header:"处理时间",
		dataIndex:"processTime",
		width:150
	},{
		header:"处理状态",
		dataIndex:"processStatus",
		width:150
	},{
		header:"处理结果",
		dataIndex:"processResultCode",
		width:150
	},{
		dataIndex:"invitationMenu",
		hidden:true,
		hideable:false
	},{
		dataIndex:"invitationEvent",
		hidden:true,
		hideable:false
	},{
		dataIndex:"invitationReason",
		hidden:true,
		hideable:false
	},{
		dataIndex:"nextaction",
		hidden:true,
		hideable:false
	}]);
	
	var invitationListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("invitation_div").getWidth(),
		height:Ext.get("invitation_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:invitationListStore,
		renderTo:"invitation_div",
		cm:cm,
		sm:sm,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:invitationListStore,
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
	        	id:"rowTip",
	        	title:"处理事件内容",
	        	border:true,
	        	minWidth:300,
	        	maxWidth:300,
	        	draggable:true,
	        	//autoHide: false,
		        //closable: true,
	        	items:[{
	        		xtype:"textarea",
	        		id:"invitationEventArea",
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
					Ext.getCmp("invitationEventArea").setValue(this.store.getAt(row).get("invitationEvent"));
				});
			}
		},
		tbar:[]
	});
	
	var loadParam = {
		start : 0,
		limit : 50,
		viewAll : "no",
		userId : userName
	};
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonRightStore = buttonRight();
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 * loadButtonRight(buttonStore, mainDataStore, dataGrid, pageDiv, params)
	 */
	loadButtonRight(buttonRightStore, invitationListStore, invitationListDataGrid, "invitation_div", loadParam);
}
/**
 * 入口函数
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	invitation();
});