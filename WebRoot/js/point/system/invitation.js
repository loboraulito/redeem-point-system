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
		{name:"invitationMenuName"},//相关菜单
		{name:"processResultCode"},//处理结果
		{name:"invitationEvent"},//处理事件内容
		{name:"invitationReason"},//理由
		{name:"nextaction"},//下一步操作
		{name:"relationData"}//相关数据
	]);
	var loadParam = {
		start : 0,
		limit : 50,
		viewAll : "no",
		status: "1",
		userId : userName,
		fromUserId:"",
		menuId : parent.fromMenuId
	};
	/**
	 * 数据标准分组显示
	 */
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,//是否在分组行上显示分组字段的名字
		enableNoGroups:false, //是否允许用户关闭分组功能REQUIRED!
		hideGroupedColumn: false,//是否隐藏分组列
		enableGroupingMenu:false,//是否在表头菜单中进行分组控制
		groupTextTpl: '{text} 有 {[values.rs.length]} 条请求信息'//用于渲染分组信息的模板，默认为'{text}'
	});
	/**
	 * 空数据
	 * @type 
	 */
	var simpleData = {"totalCount":0,"invitationList":[],"success":true};
	/**
	 * 处理结果数据字典
	 * @type 
	 */
	var processResultStore = parent.processResult;
	processResultStore.load({params:{codeId:"8ac388f13469610701346962d7ba0002"}});
	processResultStore.on("loadexception",function(dataProxy, type, action, options, response, arg){
		try{
			if(action.status == "200"){
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg, function(btn){
					});
				}
			}else{
				httpStatusCodeHandler(action.status);
			}
		}catch(e){
			Ext.Msg.alert('错误提示',"系统错误！错误代码："+e, function(btn){
			});
		}
	});
	/**
	 * 处理状态数据字典
	 * @type 
	 */
	var processStatusStore = parent.processStatus;
	processStatusStore.load({params:{codeId:"8ac388f13469610701346962a3e90001"}});
	processStatusStore.on("loadexception",function(dataProxy, type, action, options, response, arg){
		try{
			if(action.status == "200"){
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg, function(btn){
					});
				}
			}else{
				httpStatusCodeHandler(action.status);
			}
		}catch(e){
			Ext.Msg.alert('错误提示',"系统错误！错误代码："+e, function(btn){
			});
		}
	});
	/**
	 * 数据存储
	 */
	var invitationListStore = new Ext.data.GroupingStore({
		url:path+"/invitation/invitationList.action?method=invitationList",
		groupField:"invitationMenuName",
		sortInfo:{field: 'sponsorTime', direction: "ASC"},
		baseParams: loadParam,
		reader:invitationListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(!o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								invitationListStore.loadData(simpleData);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e, function(btn){
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
		dataIndex:"relationData",
		hidden:true,
		hideable:false
	},{
		header:"发起人",
		dataIndex:"sponsor",
		width:70
	},{
		header:"接收人",
		dataIndex:"recipient",
		width:150
	},{
		header:"相关菜单链接",
		dataIndex:"invitationMenuName",
		renderer:showMenuLink,
		width:130
	},{
		header:"发起时间",
		dataIndex:"sponsorTime",
		width:130
	},{
		header:"处理时间",
		dataIndex:"processTime",
		width:130
	},{
		header:"处理状态",
		dataIndex:"processStatus",
		renderer:showProcessStatus,
		width:70
	},{
		header:"处理结果",
		dataIndex:"processResultCode",
		renderer:showProcessResultCode,
		width:70
	},{
		dataIndex:"invitationMenu",
		hidden:true,
		hideable:false
	},{
		dataIndex:"invitationEvent",
		hidden:true,
		hideable:false
	},{
		header:"理由",
		dataIndex:"invitationReason",
		width:150
	},{
		dataIndex:"nextaction",
		hidden:true,
		hideable:false
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
		view:groupView,
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
	        	id:"ro_wTip",
	        	title:"处理事件内容",
	        	border:true,
	        	minWidth:300,
	        	maxWidth:300,
	        	draggable:true,
	        	//autoHide: false,
		        //closable: true,
	        	items:[{
	        		xtype:"textarea",
	        		id:"invitation_EventArea",
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
					if(this.store.getAt(row) && this.store.getAt(row).get("invitationEvent")){
						Ext.getCmp("invitation_EventArea").setValue(this.store.getAt(row).get("invitationEvent"));
					}
				});
			}
		},
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
	loadButtonRight(buttonRightStore, invitationListStore, invitationListDataGrid, "invitation_div", loadParam);
	/**
	 * 显示处理状态
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {}
	 */
	function showProcessStatus(value,metadata,record,rowIndex,colIndex,store){
		return parent.getCodeNameFromStore(value,processStatusStore,"dataKey","dataValue");
	}
	/**
	 * 显示处理结果
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {}
	 */
	function showProcessResultCode(value,metadata,record,rowIndex,colIndex,store){
		return parent.getCodeNameFromStore(value,processResultStore,"dataKey","dataValue");
	}
	/**
	 * 显示相关菜单链接
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 */
	function showMenuLink(value,metadata,record,rowIndex,colIndex,store){
		if(value && value != ""){
			var menuId = record.get("invitationMenu");
			return "<a href='javascript:parent.goToTabPanel(null,\""+menuId+"\", true)'>"+ value +"</a>";
		}
		return value;
	}
	/**
	 * 已处理过的请求
	 * @param {} url
	 */
	this.invitationProcessed = function(url){
		invitationListStore.baseParams.status = "2";
		invitationListStore.baseParams.fromUserId = "";
		invitationListStore.reload();
	};
	/**
	 * 未处理的请求
	 * @param {} url
	 */
	this.invitationProcessing = function(url){
		invitationListStore.baseParams.status = "1";
		invitationListStore.baseParams.fromUserId = "";
		invitationListStore.reload();
	};
	/**
	 * 全部请求
	 * @param {} url
	 */
	this.invitationAll = function(url){
		invitationListStore.baseParams.status = "";
		invitationListStore.baseParams.fromUserId = "";
		invitationListStore.reload();
	};
	/**
	 * 我发出的请求
	 * @param {} url
	 */
	this.viewMyInvitation = function(url){
		invitationListStore.baseParams.status = "";
		invitationListStore.baseParams.fromUserId = userName;
		invitationListStore.reload();
	};
	/**
	 * 通过请求
	 * @param {} url
	 */
	this.invitationPass = function(url, txt){
		var gridSelectionModel = invitationListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条请求信息！');
		    return false;
		}
		
		var invitationId = new Array();
		var relationDataArray = new Array();
		var invitationUrl = new Array();
		for(var i = 0; i < gridSelection.length; i++){
			invitationId.push(gridSelection[i].get("id"));
			var relateData = gridSelection[i].get("relationData");
			
			var status = gridSelection[i].get("processStatus");
			if(status == "2"){
				Ext.MessageBox.alert('提示','不能选择已处理过的请求进行处理！');
		    	return false;
			}
			
			var recipient = gridSelection[i].get("recipient");
			if(recipient.indexOf(userName) < 0){
				Ext.MessageBox.alert('提示','您无权处理发给别人的请求！');
				return false;
			}
			
			/*
			if(relateData){
				relateData = Ext.decode(relateData);
			}
			*/
			relationDataArray.push(relateData);
			invitationUrl.push(path + gridSelection[i].get("nextaction"));
			if(i > 0){
				if(invitationUrl[0] != invitationUrl[i]){
					Ext.MessageBox.alert('提示','请选择“相关菜单”一致的系统请求，否则系统无法处理！');
					return false;
				}
			}
		}
		var invitationIds = invitationId.join(",");
		var datas = relationDataArray.join("@");
		var urls = invitationUrl[0];
		processInvitation(url, invitationIds, datas, urls, txt);
	};
	/**
	 * 拒绝请求
	 * @param {} url
	 */
	this.invitationReject = function(url){
		var gridSelectionModel = invitationListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条请求信息！');
		    return false;
		}
		for(var i = 0; i < gridSelection.length; i++){
			var status = gridSelection[i].get("processStatus");
			if(status == "2"){
				Ext.MessageBox.alert('提示','不能选择已处理过的请求进行处理！');
		    	return false;
			}
			var recipient = gridSelection[i].get("recipient");
			if(recipient != userName){
				Ext.MessageBox.alert('提示','您无权处理发给别人的请求！');
				return false;
			}
		}
		Ext.Msg.prompt("系统提示","请输入拒绝理由：",function(btn,txt){
			if(btn == "yes" || btn == "ok"){
				invitationPass(url, txt);
			}
		},this, true);
		
	};
	this.testFunction = function(url){
		window.location = url;
	};
	/*
	this.testFunction = function(url){
		var u = path + "/invitation/flowAction.action";
		Ext.Ajax.request({
			params:{eventId:"processPass"},
			timeout:60000,
			url:u,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求已经成功处理！");
					}
					invitationListStore.baseParams.status = "1";
					invitationListStore.baseParams.fromUserId = "";
					invitationListStore.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求已经成功处理！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求处理失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	};
	*/
	/**
	 * 处理请求
	 * @param {} url
	 * @param {} invitationId
	 * @param {} data
	 * @param {} urls
	 */
	function processInvitation(url, invitationId, data, urls, txt){
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		
		Ext.Ajax.request({
			params:{invitationId:invitationId, data: data, urls: urls, reason:txt},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求已经成功处理！");
					}
					invitationListStore.baseParams.status = "1";
					invitationListStore.baseParams.fromUserId = "";
					invitationListStore.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求已经成功处理！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","请求处理失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	}
}
/**
 * 入口函数
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	invitation();
});