/**
 * 账目预算
 */
function accountBudgetManage(uri){
	/**
	 * 账目预算数据解析
	 */
	var budgetReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "budgetList"
	},[
	   {name:"alertid"},//预算id
	   {name:"alerttype"},//预算类型
	   {name:"userid"},//用户
	   {name:"username"},//用户
	   {name:"alertvalue"},//预算值
	   {name:"begindate"},//预算开始时间
	   {name:"enddate"},//预算结束时间
	   {name:"remark"}//备注
	]);
	/**
	 * 账目预算数据
	 */
	this.budgetStore = new Ext.data.GroupingStore({
		url: uri,
		reader:budgetReader,
		groupField:"alerttype",
		//groupOnSort:false,
		baseParams:{userName:userName},
		sortInfo:{field: 'begindate', direction: "ASC"},
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg){
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(o && !o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								this.budgetStore.loadData(budgetTempDate);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					this.budgetStore.loadData(budgetTempDate);
				}
			}
		}
	});
	//数据展现样式
	var budgetSM = new Ext.grid.CheckboxSelectionModel();//展示样式
	var budgetCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),budgetSM,{
		dataIndex:"alertid",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"begindate",
		header:"预算开始时间",
		groupable: false,
		width:150
	},{
		dataIndex:"enddate",
		header:"预算结束时间",
		groupable: false,
		width:150
	},{
		dataIndex:"alerttype",
		header:"预算类型",
		width:150
	},{
		dataIndex:"alertvalue",
		header:"预算值",
		groupable: false,
		width:150
	},{
		dataIndex:"remark",
		header:"备注",
		groupable: false,
		width:150
	}]);
	
	//分组显示
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,
		enableNoGroups:false, // REQUIRED!
		hideGroupedColumn: false,
		groupTextTpl: '{[values.rs.length]} 个   {text}'
	});
	/**
	 * 账目预算列表
	 */
	this.getBudgetGrid = function(){
		var budgetGrid = new Ext.grid.GridPanel({
			id:"budgetGrid",
			collapsible:false,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,
			store:this.budgetStore,
			cm:budgetCM,
			sm:budgetSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			view:groupView,
			bbar:new Ext.PagingToolbar({
				pageSize:9999999,//每页显示数
				store:this.budgetStore,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				//nextText:"下一页",
				//prevText:"上一页",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"新增账目预算",
				handler:function(){
					var url = path + "/account_manage/addBudget.action?method=addBudget";
					var form = budgetForm(url);
					var buttons = [{
						text:"保存",
						handler:function(){
							
						}
					},{
						text:"取消",
						handler:function(){
							var w = Ext.getCmp("addBudgetWindow");
							if(w) w.close();
						}
					}];
					showAllWindow("addBudgetWindow", "新增账目预算", 500, 250, form, null, buttons);
				}
			},"-",{
				text:"修改账目预算",
				handler:function(){
					
				}
			},"-",{
				text:"删除账目预算",
				handler:function(){
					
				}
			}]
		});
		this.budgetStore.load({params:{start:0,limit:50}});
		return budgetGrid;
	};
	/**
	 * 账目预算表单
	 * @param url
	 * @returns {Ext.form.FormPanel}
	 */
	function budgetForm(url){
		var budform = new Ext.form.FormPanel({
			url:url,
			frame: true,
			labelAlign: 'right',
			labelWidth:80,
			autoScroll:false,
			waitMsgTarget:true,
			viewConfig:{forceFit:true},
			items:[{
				layout : 'column',
				columns : 2,
				items:[{
					columnWidth : .5,
					layout : 'form',
					border : false,
					height:50,
					items:[{
						xtype: 'combo',
						name:"budget.alerttype",
						store:budgetTypeStore,
						anchor:"90%",
						fieldLabel:"预算类型",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"dataKey",//将codeid设置为传递给后台的值
						displayField:"dataValue",
						hiddenName:"budget.alerttype",//这个值就是传递给后台获取的值
						mode: "local",
						allowBlank:false
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					border : false,
					height:50,
					items:[{
						xtype: 'numberfield',
						name:"budget.alertvalue",
						anchor:"90%",
						fieldLabel:"预算金额",
						value:0,
						allowBlank:false
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					border : false,
					height:50,
					items:[{
						xtype: 'datefield',
						name:"budget.begindate",
						anchor:"90%",
						format:"Y-m-d",
						fieldLabel:"预算开始时间",
						value:new Date(),
						allowBlank:false
					},{
						xtype:"hidden",
						name:"budget.username",
						value:userName
					},{
						xtype:"hidden",
						name:"budget.alertid"
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					border : false,
					height:50,
					items:[{
						xtype: 'datefield',
						name:"budget.enddate",
						anchor:"90%",
						format:"Y-m-d",
						fieldLabel:"预算结束时间",
						value:new Date(),
						allowBlank:false
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
						name:"budget.remark",
						anchor:"90%",
						fieldLabel:"备注",
						readOnly:true
					}]
				}]
			}]
		});
		return budform;
	}
}