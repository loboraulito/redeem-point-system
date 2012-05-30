/**
 * 我的账户管理
 */
function accountInfoGrid(url){
	//var account = new accountBalance();
	/**
	 * 卡信息
	 */
	var cardInfoReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "accountCard"
	},[
	   {name:"accountId"},//账户id
	   {name:"cardId"},//卡号
	   {name:"cardName"},//账户名称
	   {name:"cardType"},//账户类型
	   {name:"cardStatus"},//账户状态
	   {name:"cardBank"},//开户行
	   {name:"cardCurrency"},//币种
	   {name:"comment"},//备注
	   {name:"cardBalance"},//余额
	   {name:"cardUser"},//持卡人
	]);
	/**
	 * 卡信息
	 */
	var cardInfoStore = new Ext.data.GroupingStore({
		url: url,
		reader:cardInfoReader,
		groupField:"cardType",
		//groupOnSort:false,
		baseParams:{userName:userName},
		sortInfo:{field: 'cardBalance', direction: "ASC"},
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg){
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(o && !o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								cardInfoStore.loadData(cardInfoTempDate);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					cardInfoStore.loadData(cardInfoTempDate);
				}
			}
		}
	});
	//数据展现样式
	var cardInfoSM = new Ext.grid.CheckboxSelectionModel();//展示样式
	var cardInfoCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),cardInfoSM,{
		dataIndex:"accountId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"cardId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"cardName",
		header:"账户名称",
		groupable: false,
		width:150
	},{
		dataIndex:"cardType",
		header:"账户类型",
		renderer:cardTypeFunction,
		width:150
	},{
		dataIndex:"cardStatus",
		header:"账户状态",
		groupable: false,
		renderer:cardStatusFunction,
		width:150
	},{
		dataIndex:"cardBank",
		header:"开户行",
		groupable: false,
		width:150
	},{
		dataIndex:"cardCurrency",
		header:"币种",
		hidden:true,
		groupable: false,
		hideable:false
	},{
		dataIndex:"cardBalance",
		header:"余额",
		groupable: false,
		width:150
	},{
		dataIndex:"cardUser",
		header:"持卡人",
		groupable: false,
		hidden:true,
		hideable:false
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
	 * 账户信息列表
	 */
	this.getCardInfoGrid = function(){
		//展示列表
		var cardInfoGrid = new Ext.grid.GridPanel({
			id:"cardInfoGrid",
			//title:"账户信息",
			collapsible:false,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			//loadMask:new Ext.LoadMask(Ext.getBody(),{msg:"数据正在加载中，请稍候..."}),
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,
			store:cardInfoStore,
			cm:cardInfoCM,
			sm:cardInfoSM,
			//renderTo:"account_div",
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			//columnLines:true,
			//stripeRows: true,
			view:groupView,
			bbar:new Ext.PagingToolbar({
				pageSize:9999999,//每页显示数
				store:cardInfoStore,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				//nextText:"下一页",
				//prevText:"上一页",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"新增账户信息",
				iconCls:"table_add",
				tooltip:"新增账户信息",
				handler:function(){
					var uri = path + "/account_manage/addAccountCard.action?method=addAccountCard";
					var cardTypeCombo = new Ext.form.ComboBox({
						name:"card.cardType",
						anchor:"90%",
						fieldLabel:"账户类型",
						store:cardTypeStore,
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"dataKey",//将codeid设置为传递给后台的值
						displayField:"dataValue",
						hiddenName:"card.cardType",//这个值就是传递给后台获取的值
						mode: "local",
						allowBlank:false
					});
					var cardStatusCombo = new Ext.form.ComboBox({
						name:"card.cardStatus",
						store:new Ext.data.SimpleStore({
							fields:["codeid","codename"],
							data:[["1","可用"],["0","不可用"]]
						}),
						anchor:"90%",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"card.cardStatus",//这个值就是传递给后台获取的值
						mode: "local",
						value:"1",
						fieldLabel:"账户状态"
					});
					var cardForm = cardInfoForm(uri, cardTypeCombo, cardStatusCombo, false, false);
					var buttons = [{
						text:"新增保存",
						handler:function(){
							if(cardForm.form.isValid()){
								saveCardInfo(cardForm, "addCardWindow");
							}
						}
					},{
						text:"关闭窗口",
						handler:function(){
							var w = Ext.getCmp("addCardWindow");
							if(w) w.close();
						}
					}];
					showAccountWindow("addCardWindow","新增账户信息", 500, 330, cardForm, null, buttons);
				}
			},"-",{
				text:"修改账户信息",
				iconCls:"table_edit",
				tooltip:"修改账户信息",
				handler:function(){
					var uri = path + "/account_manage/editAccountCard.action?method=editAccountCard";
					
					var gridSelectionModel = cardInfoGrid.getSelectionModel();
					var gridSelection = gridSelectionModel.getSelections();
					if(gridSelection.length != 1){
						Ext.MessageBox.alert('提示','请选择一个账户修改！');
					    return false;
					}
					
					var cardTypeCombo = new Ext.form.ComboBox({
						name:"card.cardType",
						anchor:"90%",
						fieldLabel:"账户类型",
						store:cardTypeStore,
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"dataKey",//将codeid设置为传递给后台的值
						displayField:"dataValue",
						hiddenName:"card.cardType",//这个值就是传递给后台获取的值
						mode: "local",
						allowBlank:false
					});
					var cardStatusCombo = new Ext.form.ComboBox({
						name:"card.cardStatus",
						store:new Ext.data.SimpleStore({
							fields:["codeid","codename"],
							data:[["1","可用"],["0","不可用"]]
						}),
						anchor:"90%",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"card.cardStatus",//这个值就是传递给后台获取的值
						mode: "local",
						value:"1",
						fieldLabel:"账户状态"
					});
					
					var cardForm = cardInfoForm(uri, cardTypeCombo, cardStatusCombo, true, false);
					var buttons = [{
						text:"修改保存",
						handler:function(){
							if(cardForm.form.isValid()){
								saveCardInfo(cardForm, "editCardWindow");
							}
						}
					},{
						text:"关闭窗口",
						handler:function(){
							var w = Ext.getCmp("editCardWindow");
							if(w) w.close();
						}
					}];
					//cardForm.getForm().loadRecord(gridSelection[0]);
					Ext.getCmp("card.accountId").setValue(gridSelection[0].get("accountId"));
					Ext.getCmp("card.cardId").setValue(gridSelection[0].get("cardId"));
					Ext.getCmp("card.cardName").setValue(gridSelection[0].get("cardName"));
					cardTypeCombo.setValue(gridSelection[0].get("cardType"));
					cardStatusCombo.setValue(gridSelection[0].get("cardStatus"));
					Ext.getCmp("card.cardBank").setValue(gridSelection[0].get("cardBank"));
					Ext.getCmp("card.cardCurrency").setValue(gridSelection[0].get("cardCurrency"));
					Ext.getCmp("card.comment").setValue(gridSelection[0].get("comment"));
					Ext.getCmp("card.cardBalance").setValue(gridSelection[0].get("cardBalance"));
					Ext.getCmp("card.cardUser").setValue(gridSelection[0].get("cardUser"));
					showAccountWindow("editCardWindow","修改账户信息", 500, 330, cardForm, null, buttons);
				}
			},"-",{
				text:"删除账户信息",
				iconCls:"table_delete",
				tooltip:"删除账户信息",
				handler:function(){
					var uri = path + "/account_manage/deleteAccountCard.action?method=deleteAccountCard";
					var gridSelectionModel = cardInfoGrid.getSelectionModel();
					var gridSelection = gridSelectionModel.getSelections();
					if(gridSelection.length < 1){
						Ext.MessageBox.alert('提示','请至少选择一个账户删除！');
					    return false;
					}
					var dataIdArray = new Array();
					for(var i=0; i < gridSelection.length; i++){
						dataIdArray.push(gridSelection[i].get("accountId"));
					}
					var accountListId = dataIdArray.join(",");
					Ext.Msg.confirm("系统提示","是否确认删除所选账户信息？", function(btn){
						if(btn == "yes" || btn == "ok"){
							Ext.MessageBox.show({
							    msg: '正在提交您的请求, 请稍侯...',
							    progressText: '正在提交您的请求',
							    width:300,
							    wait:true,
							    waitConfig: {interval:200},
							    icon:Ext.Msg.INFO
							});
							
							Ext.Ajax.request({
								params:{accountListId:accountListId},
								timeout:60000,
								url:uri,
								success:function(response, options){
									Ext.Msg.hide();
									try{
										var msg = Ext.util.JSON.decode(response.responseText);
										if(msg.success){
											var msg = "账户信息已成功删除！";
											if(msg.msg){
												msg = msg.msg;
											}
											showSystemMsg("系统提示信息", msg, function(btn, text) {
												if (btn == 'ok') {
													cardInfoStore.reload();
													//account.cardInfoStore.reload();
													cardInfosStore.reload();
													//account.accountGroupStore.reload();
													//account.accountGrid.getStore().reload();
													//accountGroupStore.reload();
													Ext.getCmp("accountGrid").getStore().reload();
												}
											});
										}else{
											if(msg.msg){
												Ext.Msg.alert("系统提示",msg.msg);
											}else{
												Ext.Msg.alert("系统提示","账户信息删除失败！");
											}
										}
									}catch(e){
										Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
									}
								},failure: function(response, options){
									Ext.Msg.hide();
									try{
										var msg = Ext.util.JSON.decode(response.responseText);
										if(msg.msg){
											Ext.Msg.alert("系统提示",msg.msg);
										}else{
											Ext.Msg.alert("系统提示","账户信息删除失败！");
										}
									}catch(e){
										Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
									}
								}
							});
						}
					});
				}
			},"-",{
				text:"账户转账",
				iconCls:"table_goto",
				tooltip:"账户转账",
				handler:function(){
					var uri = path + "/account_manage/transferAccount.action?method=transferAccount";
					var transferForm = getTransferForm(uri);
					var buttons = [{
						text:"开始转账",
						handler:function(){
							if(transferForm.form.isValid()){
								saveCardInfo(transferForm, "transferWindow");
							}
						}
					},{
						text:"关闭窗口",
						handler:function(){
							var w = Ext.getCmp("transferWindow");
							if(w) w.close();
						}
					}];
					showAccountWindow("transferWindow","账户转账", 500, 230, transferForm, null, buttons);
				}
			}]
		});
		cardInfoStore.load({params:{start:0, limit:9999999}});
		return cardInfoGrid;
	};
	/**
	 * 卡类型
	 * @param value
	 * @param metadata
	 * @param record
	 * @param rowIndex
	 * @param colIndex
	 * @param store
	 * @returns
	 */
	function cardTypeFunction(value,metadata,record,rowIndex,colIndex,store){
		return getCodeNameFromStore(value, cardTypeStore, "dataKey", "dataValue");
	}
	
	/**
	 * 卡状态
	 * @param value
	 * @param metadata
	 * @param record
	 * @param rowIndex
	 * @param colIndex
	 * @param store
	 * @returns {String}
	 */
	function cardStatusFunction(value,metadata,record,rowIndex,colIndex,store){
		if(value == "1"){
			return "可用";
		}else{
			return "不可用";
		}
	}
	
	/**
	 * 卡表单
	 * @param readOnly
	 * @param isNull
	 * @returns {Ext.form.FormPanel}
	 */
	function cardInfoForm(uri, cardTypeCombo, cardStatusCombo, readOnly, isNull){
		var cardInfoForm = new Ext.form.FormPanel({
			url: uri,
			frame: true,
			labelAlign: 'right',
			labelWidth:70,
			autoScroll:false,
			waitMsgTarget:true,
			viewConfig:{forceFit:true},
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"card.cardId",
						id:"card.cardId",
						anchor:"90%",
						fieldLabel:"卡号",
						readOnly:readOnly,
						allowBlank:isNull
					},{
						xtype: 'hidden',
						name:"card.accountId",
						id:"card.accountId"
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"card.cardName",
						id:"card.cardName",
						anchor:"90%",
						fieldLabel:"账户名称",
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[cardTypeCombo]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"card.cardBank",
						id:"card.cardBank",
						anchor:"90%",
						fieldLabel:"开户行"
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'numberfield',
						name:"card.cardBalance",
						id:"card.cardBalance",
						anchor:"90%",
						value:0,
						fieldLabel:"余额"
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[cardStatusCombo]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"card.cardCurrency",
						id:"card.cardCurrency",
						anchor:"90%",
						fieldLabel:"币种"
					},{
						xtype: 'hidden',
						name:"card.cardUser",
						id:"card.cardUser",
						value:userName
					}]
				},{
					layout:"form",
					columnWidth:1,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"card.comment",
						id:"card.comment",
						anchor:"90%",
						fieldLabel:"备注"
					}]
				}]
			}]
		});
		return cardInfoForm;
	}
	/**
	 * 账户转账
	 * @param uri
	 */
	function getTransferForm(uri){
		var transferForm = new Ext.form.FormPanel({
			url: uri,
			frame: true,
			labelAlign: 'right',
			labelWidth:70,
			autoScroll:false,
			waitMsgTarget:true,
			viewConfig:{forceFit:true},
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"tranOutAccount",
						anchor:"90%",
						store:cardInfoStore,
						fieldLabel:"转出账户",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"accountId",//将codeid设置为传递给后台的值
						displayField:"cardName",
						hiddenName:"tranOutAccount",//这个值就是传递给后台获取的值
						mode: "local",
						allowBlank:false
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"tranInAccount",
						anchor:"90%",
						store:cardInfoStore,
						fieldLabel:"转入账户",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"accountId",//将codeid设置为传递给后台的值
						displayField:"cardName",
						hiddenName:"tranInAccount",//这个值就是传递给后台获取的值
						mode: "local",
						allowBlank:false
					}]
				},{
					layout:"form",
					columnWidth:1,
					height:50,
					items:[{
						xtype: 'numberfield',
						name:"tranAmount",
						anchor:"90%",
						fieldLabel:"转账金额",
						value:0,
						allowBlank:false
					}]
				},{
					layout:"form",
					columnWidth:1,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"comment",
						anchor:"90%",
						fieldLabel:"备注"
					},{
						xtype:"hidden",
						name:"userName",
						value:userName
					}]
				}]
			}]
		});
		return transferForm;
	}
	
	/**
	 * 保存账户信息
	 * @param form
	 * @param windowId
	 */
	function saveCardInfo(form, windowId){
		Ext.MessageBox.show({
			msg:"正在保存账户信息，请稍候...",
			progressText:"正在保存账户信息，请稍候...",
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
						var msg = "账户信息保存成功！";
						if(result.msg){
							msg = result.msg;
						}
						showSystemMsg("系统提示信息", msg, function(btn, text) {
							if (btn == 'ok') {
								cardInfoStore.reload();
								//account.cardInfoStore.reload();
								cardInfosStore.reload();
								//account.accountGroupStore.reload();
								//account.accountGrid.getStore().reload();
								accountGroupStore.reload();
								Ext.getCmp(windowId).close();
							}
						});
					}else if(!result.success){
						var msg = "账户信息保存失败，请检查您所填信息是否完整无误！";
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
				var msg = "账户信息保存失败，请检查您的网络连接或者联系管理员！";
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