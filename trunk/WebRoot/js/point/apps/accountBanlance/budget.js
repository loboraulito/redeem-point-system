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
	var budgetStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:uri
		}),
		reader:budgetReader,
		//groupOnSort:false,
		baseParams:{userName:userName},
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
		renderer: showdate,
		width:150
	},{
		dataIndex:"enddate",
		header:"预算结束时间",
		groupable: false,
		renderer: showdate,
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
			store:budgetStore,
			cm:budgetCM,
			sm:budgetSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			bbar:new Ext.PagingToolbar({
				pageSize:9999999,//每页显示数
				store:budgetStore,
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
							saveBudget(form, "addBudgetWindow");
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
					var gridSelection = budgetGrid.getSelectionModel().getSelections();
					if(gridSelection.length != 1){
			            showSystemMsg("系统提示", "请选择一条账目预算信息");
			            return false;
			        }
					var url = path + "/account_manage/editBudget.action?method=editBudget";
					var form = budgetForm(url);
					var buttons = [{
						text:"保存",
						handler:function(){
							saveBudget(form, "editBudgetWindow");
						}
					},{
						text:"取消",
						handler:function(){
							var w = Ext.getCmp("editBudgetWindow");
							if(w) w.close();
						}
					}];
					showAllWindow("editBudgetWindow", "修改账目预算", 500, 250, form, null, buttons);
					//日期类型的采用遮罩效果，避免使用disable效果。使用后则的话，后台无法获取数据
					markCmp(form.form.findField("budgetDate"));
					form.form.findField("budgetDate").setValue(dateFormat(gridSelection[0].get("begindate"), "Y-m-d H:i:s", "Y-m"));
					form.form.findField("budget.alertvalue").setValue(dateFormat(gridSelection[0].get("alertvalue")));
					form.form.findField("budget.remark").setValue(dateFormat(gridSelection[0].get("remark")));
					form.form.findField("budget.username").setValue(dateFormat(gridSelection[0].get("username")));
					form.form.findField("budget.alertid").setValue(dateFormat(gridSelection[0].get("alertid")));
				}
			},"-",{
				text:"删除账目预算",
				handler:function(){
					var url = path + "/account_manage/deleteBudget.action?method=deleteBudget";
					var gridSelection = budgetGrid.getSelectionModel().getSelections();
					if(gridSelection.length < 1){
			            showSystemMsg("系统提示", "请至少选择一条账目预算信息");
			            return false;
			        }
					var budgetArray = new Array();
					for(var i=0; i<gridSelection.length; i++){
						budgetArray.push(gridSelection[i].get("alertid"));
					}
					var budgetId = budgetArray.join(",");
					Ext.Msg.confirm("系统提示","请确认是否删除所选账目预算信息？",function(btn){
						if(btn == "yes" || btn == "ok"){
							Ext.MessageBox.show({
								msg: '正在删除数据, 请稍侯...',
								progressText: '正在删除数据',
								width:300,
								wait:true,
								waitConfig: {interval:200},
								icon:Ext.Msg.INFO
							});
							Ext.Ajax.request({
				    			params:{budgetListId:budgetId},
				    			url:url,
				    			timeout:60000,
				    			success:function(response,options){
					    			Ext.MessageBox.hide();
					    			try{
					    				var msg = Ext.util.JSON.decode(response.responseText);
					    				if(msg && msg.success){
					    					showSystemMsg("提示信息",msg.msg,function(){
					    						budgetStore.reload();
					    						accountGroupStore.reload();
						    				});
					    				}else{
					    					Ext.Msg.alert("提示信息",msg.msg);
					    				}
					    			}catch(e){
					    				Ext.Msg.alert("提示信息","错误代码："+e);
					    			}
						     	},failure:function(response,options){
						     		Ext.Msg.hide();
						     		try{
						     			var re = Ext.util.JSON.decode(response.responseText);
								    	Ext.Msg.alert("提示信息",re.msg);
						     		}catch(e){
						     			Ext.Msg.alert("提示信息","错误代码："+e);
						     		}
							    	return;
						     	}
				    		});
						}
					});
				}
			}]
		});
		budgetStore.load({params:{start:0,limit:50}});
		return budgetGrid;
	};
	
	/**
	 * 格式化日期
	 */
	function showdate(value,metadata,rocord,rowIndex,colIndex,store){
		if(value && value!=""){
			//引用unit.js中的方法
			return dateFormat(value,'Y-m-d H:i:s',"Y-m-d");
		}
	}
	
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
						xtype: 'datefield',
						name:"budgetDate",
						anchor:"90%",
						format:"Y-m",
						fieldLabel:"预算时间",
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
						fieldLabel:"备注"
					}]
				}]
			}]
		});
		return budform;
	}
	/**
	 * 保存预算
	 * @param form
	 * @param windowId
	 */
	function saveBudget(form, windowId){
		Ext.MessageBox.show({
			msg:"正在保存预算信息，请稍候...",
			progressText:"正在保存预算信息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			timeout:60000,
			success: function(form, action) {
				Ext.Msg.hide();
				try{
					var result = Ext.decode(action.response.responseText);
					if(result && result.success){
						var msg = "预算信息保存成功！";
						if(result.msg){
							msg = result.msg;
						}
						showSystemMsg("系统提示信息", msg, function(btn, text) {
							if (btn == 'ok') {
								budgetStore.reload();
								accountGroupStore.reload();
								Ext.getCmp(windowId).close();
							}
						});
					}else if(!result.success){
						var msg = "预算信息保存失败，请检查您所填信息是否完整无误！";
						if(result.msg){
							msg = result.msg;
						}
						Ext.Msg.alert('系统提示信息', msg);
					}
				}catch(e){
					Ext.Msg.alert('系统提示信息', "系统错误，错误代码："+e);
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var msg = "预算信息保存失败，请检查您的网络连接或者联系管理员！";
				try{
					var result = Ext.decode(action.response.responseText);
					if(result.msg){
						msg = result.msg;
					}
				}catch(e){
					msg = "系统错误，错误代码：" + e;
				}
				Ext.Msg.alert('系统提示信息', msg);
			}
		});
	}
}