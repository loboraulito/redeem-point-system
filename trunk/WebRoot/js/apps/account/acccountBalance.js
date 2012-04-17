/**
 * 账目管理系统
 */
function accountBalance(){
	//账目明细数据解析器
	var accountReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "account"
	},[
		{name:"baseinfoid"},//唯一id
		{name:"baseyear"},//年份
		{name:"basemonth"},//月份
		{name:"basedate"},//具体日期
		{name:"accountenter"},//当天收入
		{name:"accountout"},//当天消费
		{name:"accountmargin"},//当天结算
		{name:"remark"},//备注
		{name:"userid"},//备注
		{name:"username"},//备注
		{name:"deletetag"},//删除标志，1：已删除，0未删除
		{name:"margintag"},//结算标志：1：已结算，0未结算
		{name:"accountalert"},//日警告点
		{name:"accountalertmon"}//月报警点
	]);
	//警报点数据解析器
	var alertReader = new Ext.data.JsonReader({
		totalProperty:"totalAlertCount",
		root:"alerts"
	},[
		{name:"alertid"},
		{name:"alerttype"},//警报类型
		{name:"userid"},//用户id
		{name:"username"},//用户名
		{name:"alertvalue"},//警报值
		{name:"begindate"},//开始日期
		{name:"enddate"},//结束日期
		{name:"remark"}//备注
	]);
	//结算结果数据解析器
	var balanceReader = new Ext.data.JsonReader({
		totalProperty:"totalCount",
		root:"balances"
	},[
		{name:"balanceid"},
		{name:"begindate"},//结算范围起始日期
		{name:"enddate"},//结算范围终止日期
		{name:"balanceyear"},//结算年度
		{name:"balancemonth"},//结算月度
		{name:"balancetype"},//结算类型
		{name:"accountenter"},//结算期间总收入
		{name:"accountout"},//结算期间总支出
		{name:"accountmargin"},//差额
		{name:"alertvalue"},//年度结算警告点
		{name:"balance"},//盈利
		{name:"remark"},//备注
		{name:"userid"},//用户id
		{name:"username"}//用户名
	]);
	
	//结算结果数据解析器
	var rightReader = new Ext.data.JsonReader({
		totalProperty:"totalCount",
		root:"rights"
	},[
		{name:"balancerightid"},
		{name:"userid"},//用户id
		{name:"username"},//用户名
		{name:"allowuserid"},//用户名
		{name:"allowusername"}//用户名
	]);
	
	// define a custom summary function
	// 定义自定义结算函数
    Ext.grid.GroupSummary.Calculations['totalCost'] = function(v, record, field){
        return getRound(0 + parseFloat(parseFloat(v) + (parseFloat(record.data.accountenter) - parseFloat(record.data.accountout))),2);
    }
    Ext.grid.GroupSummary.Calculations['currentMonth'] = function(v, record, field){
        return dateFormat(record.data.basemonth,'Y-m',"Y 年 m 月")+" 小结";
    }
    Ext.grid.GroupSummary.Calculations['alertMonth'] = function(v, record, field){
        return record.data.accountalertmon;
    }
    Ext.grid.GroupSummary.Calculations['marginTag'] = function(v, record, field){
        return (record.data.margintag);
    }
    Ext.grid.GroupSummary.Calculations['balanceYear'] = function(v, record, field){
        return (record.data.balanceyear) +" 年度总结";
    }
    
    var summary = new Ext.grid.GroupSummary(); 
    
	//主数据分组显示
	var accountGroupStore = new Ext.data.GroupingStore({
		url:basePath+"balance/accountinfo.action?method=accountDetailList",
		reader:accountReader,
		groupField:"basemonth",
		//groupOnSort:false,
		baseParams:{username:userName,userid:userid},
		sortInfo:{field: 'basedate', direction: "ASC"}
	});
	/**
	 * 警报点数据存储
	 */
	var alertStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basePath+"balance/accoutalert.action?method=showAccoutAlert"
		}),
		reader:alertReader,
		baseParams:{username:userName,userid:userid}
	});
	/**
	 * 结算账目数据存储
	 */	
	var balanceStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basePath+"balance/balancelist.action?method=showBalanceInfo"
		}),
		reader:balanceReader,
		baseParams:{username:userName,userid:userid}
	});
	/**
	 * 结算账目数据存储,分组显示
	 */
	var balanceGroupStore = new Ext.data.GroupingStore({
		url:basePath+"balance/balancelist.action?method=showBalanceInfo",
		reader:balanceReader,
		groupField:"balanceyear",
		//groupOnSort:false,
		baseParams:{username:userName,userid:userid},
		sortInfo:{field: 'balancemonth', direction: "ASC"}
	});
	
	
	/**
	 * 展示可允许查看账目信息的好友信息数据存储
	 */
	var rightStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basePath+"balance/balanceright.action?method=showRightInfo"
		}),
		reader:rightReader,
		baseParams:{username:userName,userid:userid}
	});
	/**
	 * 查询好友信息
	 */
	var friendStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basePath+"memberfriend/friendList.action?method=getFriendList"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"totalCount",
			root:"friendList"
		},["id","userId","userName","friendId","friendName","friendSort","friendSortName","friendOnLine"]),
		baseParams:{userName:userName,userid:userid}
	});
	
	//分组显示
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,
		enableNoGroups:false, // REQUIRED!
		hideGroupedColumn: false,
		groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "条账目明细" : "条账目明细"]})          本月设置消费报警值：<font color="red">{[isNaN(parseFloat(values.rs[0].data.accountalertmon))?"未设置":values.rs[0].data.accountalertmon]}</font>（单位：人民币/元）'
	});
	
	//结算之后，以年度方式来分组显示
	var balanceGroupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,
		enableNoGroups:false, // REQUIRED!
		hideGroupedColumn: false,
		groupTextTpl: '{text} 年度总结          本年度设置总支出警报值：<font color="red">{[isNaN(parseFloat(values.rs[0].data.alertvalue))?"未设置":values.rs[0].data.alertvalue]}</font>（单位：人民币/元）'
	});
	
	
	//数据展现样式
	var accountSM = new Ext.grid.CheckboxSelectionModel();
	//展示样式
	var accountCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),accountSM,{
		dataIndex:"baseinfoid",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"年度",
		dataIndex:"baseyear",
		width:150,
		hidden:true,
		hideable:false
	},{
		header:"月份",
		dataIndex:"basemonth",
		width:150,
		//renderer:showmonth,
		hidden:true,
		hideable:false
	},{
		header:"记账理由",
		groupable: false,
		dataIndex:"remark",
		renderer:edited,
		width:180
	},{
		header:"记账日期",
		groupable: false,
		dataIndex:"basedate",
		summaryType:"currentMonth",
		sortable:true,
		renderer:showdate,
		width:80
	},{
		header:"当天收入（单位：人民币/元）",
		groupable: false,
		dataIndex:"accountenter",
		renderer:showmoney,
		summaryType:"sum",
		width:130
	},{
		header:"当天消费（单位：人民币/元）",
		groupable: false,
		dataIndex:"accountout",
		summaryType:"sum",
		renderer:showoutmoney,
		//summaryRenderer:showmoney,
		width:130
	},{
		header:"当天结算（单位：人民币/元）",
		groupable: false,
		dataIndex:"accountmargin",
		summaryType:"totalCost",
		renderer:showmoney,
		width:130
	},{
		header:"结算标志",
		groupable: false,
		dataIndex:"margintag",
		summaryType:"marginTag",
		renderer:showmargintag,
		width:50
	},{
		dataIndex:"accountalert",
		summaryType:"average",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"accountalertmon",
		summaryType:"alertMonth",
		hidden:true,
		hideable:false,//不允许将隐藏的字段显示出来
		header:"aaaa"
	}]);
	
	//展示列表
	var accountGrid = new Ext.grid.GridPanel({
		id:"accountGrid",
		title:"账目详细信息",
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("accountdetail").getWidth(),
		height:Ext.get("accountdetail").getHeight(),
		//width:800,
		//height:500,
		//loadMask:new Ext.LoadMask(Ext.getBody(),{msg:"数据正在加载中，请稍候..."}),
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:accountGroupStore,
		cm:accountCM,
		sm:accountSM,
		renderTo:accountdetail,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		region: 'north',
		//columnLines:true,
		//stripeRows: true,
		view:groupView,
		plugins: summary,
		listeners:{
			"rowdblclick":function(grid,rowIndex,e){
				var gridSelectionModel = accountGrid.getSelectionModel();
				gridSelectionModel.selectRow(rowIndex);
				var gridSelection = gridSelectionModel.getSelections();
				if(gridSelection.length != 1){
		            Ext.MessageBox.alert('提示','请选择一条账目信息！');
		            return false;
		        }
		        
		        if(gridSelection[0].get("margintag")=="1"){
		        	Ext.MessageBox.alert('提示','已经结算的账目不允许进行修改！');
		            return false;
		        }
		        
		        if(gridSelection[0].get("userid") != userid || gridSelection[0].get("username") != userName){
		        	Ext.MessageBox.alert('提示','您不能修改别人的账目信息！');
		            return false;
		        }
		        var form = showAddEditForm(false,true);
		        var button = [{
					text:"保存",
					handler:function(){
						var win = Ext.getCmp("editaccountwin");
						if(win && form.form.isValid()){
							marginOfDay(form);
							doSaveEditAccount(win,form,"2");
						}else{
							Ext.Msg.alert("提示信息","账目信息未填写完整，请填写完整之后再保存！");
						}
					}
				},{
					text:"取消",
					handler:function (){
						var win = Ext.getCmp("editaccountwin");
						if(win){
							win.close();
						}
					}
				}];
		        showWindow("editaccountwin","修改账目",400,300,form,button);
		        var basedate = gridSelection[0].get("basedate");
		        basedate = Date.parseDate(basedate,'Y-m-d H:i:s');
		        basedate = basedate.format("Y-m-d");
		        basedate = Date.parseDate(basedate,'Y-m-d');
		        form.getForm().loadRecord(gridSelection[0]);
		        form.form.findField("basedate").setValue(basedate);
				
			}
		},
		bbar:new Ext.PagingToolbar({
			pageSize:9999999,//每页显示数
			store:accountGroupStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			//nextText:"下一年",
			//prevText:"上一年",
			emptyMsg:"无相关记录",
			items:["-",{
				text:"导出",
				menu:[{
					text:"导出当前年度账目信息",
					id:"exportTemplate",
					tooltip:"导出当前年度账目信息",
					handler:function(){
						exportExcel("2");
					}
				},{
					text:"导出所有账目信息",
					id:"exportInfo",
					tooltip:"导出所有账目信息",
					handler:function(){
						exportExcel("2","all");
					}
				}]
			},"-",{
				text:"导入账目信息",
				id:"importInfo",
				tooltip:"导入账目信息",
				handler:function(){
					importExcel();
				}
			}]
		}),
		tbar:[{
			text:"新增账目",
			tooltip:"新增账目",
			id:"newaccount",
			handler:function(){
				var form = showAddEditForm(false,true);
				var button = [{
					text:"保存",
					handler:function(){
						var win = Ext.getCmp("newaccountwin");
						if(win && form.form.isValid()){
							marginOfDay(form);
							doSaveEditAccount(win,form,"1");
						}else{
							Ext.Msg.alert("提示信息","账目信息未填写完整，请填写完整之后再保存！");
						}
					}
				},{
					text:"取消",
					handler:function (){
						var win = Ext.getCmp("newaccountwin");
						if(win){
							win.close();
						}
					}
				}];
				showWindow("newaccountwin","新增账目",400,300,form,button);
			}
		},"-",{
			text:"修改账目",
			tooltip:"修改账目",
			id:"editaccount",
			handler:function(){
				var gridSelection = accountGrid.getSelectionModel().getSelections();
				if(gridSelection.length != 1){
		            Ext.MessageBox.alert('提示','请选择一条账目信息！');
		            return false;
		        }
		        
		        if(gridSelection[0].get("margintag")=="1"){
		        	Ext.MessageBox.alert('提示','已经结算的账目不允许进行修改！');
		            return false;
		        }
		        var form = showAddEditForm(false,true);
		        var button = [{
					text:"保存",
					handler:function(){
						var win = Ext.getCmp("editaccountwin");
						if(win && form.form.isValid()){
							marginOfDay(form);
							doSaveEditAccount(win,form,"2");
						}else{
							Ext.Msg.alert("提示信息","账目信息未填写完整，请填写完整之后再保存！");
						}
					}
				},{
					text:"取消",
					handler:function (){
						var win = Ext.getCmp("editaccountwin");
						if(win){
							win.close();
						}
					}
				}];
		        showWindow("editaccountwin","修改账目",400,300,form,button);
		        var basedate = gridSelection[0].get("basedate");
		        basedate = Date.parseDate(basedate,'Y-m-d H:i:s');
		        basedate = basedate.format("Y-m-d");
		        basedate = Date.parseDate(basedate,'Y-m-d');
		        form.getForm().loadRecord(gridSelection[0]);
		        form.form.findField("basedate").setValue(basedate);
			}
		},"-",{
			text:"删除账目",
			tooltip:"删除账目",
			id:"delaccount",
			handler:function(){
				var gridSelection = accountGrid.getSelectionModel().getSelections();
				if(gridSelection.length < 1){
		            Ext.MessageBox.alert('提示','请至少选择一条账目信息！');
		            return false;
		        }
		        var accountIds = "";
		        for(var i=0;i<gridSelection.length;i++){
		        	if(gridSelection[i].get("margintag")=="1"){
			        	Ext.MessageBox.alert('提示','已经结算的账目不允许进行修改！');
			            return false;
			        }
		        	accountIds += gridSelection[i].get("baseinfoid") + ",";
		        }
		        Ext.Msg.confirm("提示信息","您确定要删除所选择的账目信息？删除之后无法恢复！",function(btn){
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
							//type==1:硬删除，==2：软删除，设置删除标志
			    			params:{accountIds:accountIds,type:'2'},
			    			url:basePath+"balance/delaccountinfo.action?method=deleteAccountDetail",
			    			success:function(response,options){
				    			Ext.MessageBox.hide();
						     	var re = Ext.util.JSON.decode(response.responseText);
						     	Ext.Msg.alert("提示信息",re.msg);
						     	if(re.success){
						     		accountGroupStore.reload();
						     	}
					     	},failure:function(response,options){
					     		Ext.Msg.hide();
					     		var re = Ext.util.JSON.decode(response.responseText);
						    	Ext.Msg.alert("提示信息",re.msg);
						    	return;
					     	}
			    		});
		        	}
		        });
			}
		},"-",{
			text:"账目查询",
			tooltip:"账目查询",
			id:"queryaccount",
			handler:function(){
				var form = showQueryForm();
				var button = [{
					text:"查询",
					handler:function(){
						var win = Ext.getCmp("queryaccountwin");
						if(win && form.form.isValid()){
							doQueryAccount(win,form);
						}else{
							Ext.Msg.alert("提示信息","账目信息未填写完整，请填写完整之后再查询！");
						}
					}
				},{
					text:"取消",
					handler:function (){
						var win = Ext.getCmp("queryaccountwin");
						if(win){
							win.close();
						}
					}
				}];
				showWindow("queryaccountwin","账目查询",400,200,form,button);
			}
		}
		/*
		,"-",{
			text:"上一年度账目",
			handler:function(){
				accountGroupStore.reload({
					params:{type:"privous",username:userName,userid:userid,start:0,limit:9999999}
				});
			}
		},"-",{
			text:"当前年度账目",
			handler:function(){
				accountGroupStore.reload({
					params:{type:"",username:userName,userid:userid,start:0,limit:9999999}
				});
			}
		},"-",{
			text:"下一年度账目",
			handler:function(){
				accountGroupStore.reload({
					params:{type:"next",username:userName,userid:userid,start:0,limit:9999999}
				});
			}
		}
		*/
		,"-",{
			text:"设置报警点",
			tooltip:"设置报警点",
			id:"setalert",
			handler:function(){
				var alertGrid = showAlertGrid(500,350,alertStore);
				var button = [{
					text:"关闭",
					handler:function(){
						var gridWindow = Ext.getCmp("alertGridwindow");
						if(gridWindow){
							gridWindow.close();
						}
					}
				}];
				alertStore.load({
					params:{start:0,limit:50},
					callback:function(){
						showWindow("alertGridwindow","警报点设置",500,350,alertGrid,button);
					}
				});
			}
		},"-",{
			text:"设置好友查看权限",
			tooltip:"设置好友查看权限",
			id:"setfriend",
			handler:function(){
				var rightGrid = showRightGrid(500,350,rightStore);
				var button = [{
					text:"关闭",
					handler:function(){
						var gridWindow = Ext.getCmp("rightGridwindow");
						if(gridWindow){
							gridWindow.close();
						}
					}
				}];
				rightStore.load({
					params:{start:0,limit:50},
					callback:function(){
						showWindow("rightGridwindow","好友查看权限设置",500,350,rightGrid,button);
					}
				});
			}
		},"-"]
	});
	//在列表上面显示下拉式的按钮组合
	var menu = new Ext.menu.Menu({
		items:[{
			text:"账目结算",
			id:"balanceaccount",
			tooltip:"账目结算",
			handler:function(){
				var form = showMarginForm();
				var button = [{
					text:"结算",
					handler:function(){
						var win = Ext.getCmp("marginccount");
						if(win && form.form.isValid()){
							doMarginAccount(win,form);
						}else{
							Ext.Msg.alert("提示信息","结算账目信息未填写完整，请填写完整之后再结算！");
						}
					}
				},{
					text:"取消",
					handler:function (){
						var win = Ext.getCmp("marginccount");
						if(win){
							win.close();
						}
					}
				}];
		        showWindow("marginccount","结算账目",300,140,form,button);
			}
		},{
			text:"账目结算结果查询",
			tooltip:"账目结算结果查询",
			id:"balanceresult",
			handler:function(){
				var balanceGrid = showBalanceGrid(600,document.body.clientHeight-50,balanceGroupStore);//balanceStore
				var button = [{
					text:"关闭",
					handler:function(){
						var gridWindow = Ext.getCmp("balanceGridwindow");
						if(gridWindow){
							gridWindow.close();
						}
					}
				}];
				balanceGroupStore.load({
					params:{start:0,limit:50},
					callback:function(){
						showWindow("balanceGridwindow","账目结算结果（单位：人民币/元）",600,document.body.clientHeight-50,balanceGrid,button);
					}
				});
			}
		}]
	});
	//查看好友账目
	var fmenu = new Ext.menu.Menu();
	fmenu.add({
		text:"请选择"
	}).on("click",function(){
		Ext.Msg.alert("提示信息","请点击下面的好友名字，以查看该好友的账目信息");
	});
	fmenu.addSeparator();
	
	//在列表上方显示下拉框显示年度的下拉框
	var combo = new Ext.form.ComboBox({
		id:"accountyear",
        store: yearStore,
        displayField:'yearname',
        valueField:"year",
        typeAhead: true,
        mode: 'local',
        editable:false,
        triggerAction: 'all',
        emptyText:'选择年度',
        selectOnFocus:true,
        width:100,
        listeners:{
        	"select":function(combo,record,index){
        		accountGroupStore.baseParams.year = combo.getValue();
        		accountGroupStore.reload({
					params:{start:0,limit:9999999}
				});
        	}
        }
    });
	accountGrid.getTopToolbar().addField(combo);
	accountGrid.getTopToolbar().add("-",{
		text:"账目结算",
		menu:menu
	});
	accountGrid.getTopToolbar().add("->","-",{
		text:"查看好友账目信息",
		menu:fmenu
	});
	
	function showRightGrid(width,height,store){
		//数据展现样式
		var rightSM = new Ext.grid.CheckboxSelectionModel();
		var rightCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),rightSM,{
			dataIndex:"balancerightid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"userid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"username",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"allowuserid",
			header:"允许查看我的账目好友ID",
			width:120
		},{
			dataIndex:"allowusername",
			header:"允许查看我的账目好友姓名",
			width:120
		}]);
		
		var rightGrid = new Ext.grid.GridPanel({
			id:"rightGrid",
			//title:"警报点设置",
			collapsible:true,//是否可展开
			animCollapse:true,//展开是否动态效果
			autoScroll:true,//是否有滚动条
			width:width,
			height:height,
			loadMask:true,//是否载入动画
			frame:true,//是否渲染
			store:store,//数据存储
			cm:rightCM,
			sm:rightSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			region: 'north',
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:store,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"新增可查看我的账目信息好友",
				tooltip:"新增可查看我的账目信息好友",
				handler:function(){
					var form = showRightForm();
					var button = [{
						text:"保存",
						handler:function(){
							var win = Ext.getCmp("accountright");
							if(win && form.form.isValid()){
								doAddNewfriendRight(win,form,store);
							}
						}
					},{
						text:"取消",
						handler:function (){
							var win = Ext.getCmp("accountright");
							if(win){
								win.close();
							}
						}
					}];
					showWindow("accountright","增加好友权限",300,120,form,button);
				}
			},"-",{
				text:"删除可查看我的账目信息好友",
				tooltip:"删除可查看我的账目信息好友",
				handler:function(){
					var gridSelection = rightGrid.getSelectionModel().getSelections();
					if(gridSelection.length < 1){
			            Ext.MessageBox.alert('提示','请至少选择一条信息！');
			            return false;
			        }
			        var rightdsIds = "";
			        for(var i=0;i<gridSelection.length;i++){
			        	rightdsIds += gridSelection[i].get("balancerightid") + ",";
			        }
			        Ext.Msg.confirm("提示信息","您确定要删除所选择好友权限信息？删除之后无法恢复！",function(btn){
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
								//type==1:硬删除，==2：软删除，设置删除标志
				    			params:{rightdsIds:rightdsIds,type:'2'},
				    			url:basePath+"balance/delaccountright.action?method=deleteAccountRight",
				    			success:function(response,options){
					    			Ext.MessageBox.hide();
							     	var re = Ext.util.JSON.decode(response.responseText);
							     	Ext.Msg.alert("提示信息",re.msg);
							     	if(re.success){
							     		store.reload();
							     	}
						     	},failure:function(response,options){
						     		Ext.Msg.hide();
						     		var re = Ext.util.JSON.decode(response.responseText);
							    	Ext.Msg.alert("提示信息",re.msg);
							    	return;
						     	}
				    		});
			        	}
			        });
				}
			}]
		});
		return rightGrid;
	}
	
	/**
	 * 展示结算账目后的账目信息
	 * @param {} width
	 * @param {} height
	 * @param {} store
	 */
	function showBalanceGrid(width,height,store){
		//数据展现样式
		var balanceSM = new Ext.grid.CheckboxSelectionModel();
		var balanceCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),balanceSM,{
			dataIndex:"balanceid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"userid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"username",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"balanceyear",
			summaryType:"average",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"balancemonth",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"alertvalue",
			summaryType:"average",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"begindate",
			header:"结算范围起始日期",
			renderer:showdate,
			width:120
		},{
			dataIndex:"enddate",
			header:"结算范围终止日期",
			renderer:showdate,//showmoney
			summaryType:"balanceYear",
			width:120
		},{
			dataIndex:"balancetype",
			header:"结算类型",
			renderer:showdate,
			width:80
		},{
			dataIndex:"accountenter",
			header:"结算总收入",
			renderer:showmoney,
			summaryType:"sum",
			width:80
		},{
			dataIndex:"accountout",
			header:"结算总支出",
			renderer:showalertmoney,
			summaryType:"sum",
			width:80
		},{
			dataIndex:"balance",
			header:"结算结果",
			renderer:showresultmoney,
			summaryType:"sum",
			width:80
		}]);
		var balanceGrid = new Ext.grid.GridPanel({
			id:"balanceGrid",
			//title:"警报点设置",
			collapsible:true,//是否可展开
			animCollapse:true,//展开是否动态效果
			autoScroll:true,//是否有滚动条
			width:width,
			height:height,
			loadMask:true,//是否载入动画
			frame:true,//是否渲染
			store:store,//数据存储
			cm:balanceCM,
			sm:balanceSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			region: 'north',
			view:balanceGroupView,
			plugins: new Ext.grid.GroupSummary(),
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:store,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"图形展示",
				handler:function(){
					//var html = "<iframe frameborder='0' width='100%' height='100%' src = '"+basePath+"balance/histogram.action?method=histogramChart'></iframe>";
					var html = "<iframe frameborder='0' width='100%' height='100%' src = '"+basePath+"jsp/apps/accountbalance/histogram.jsp'></iframe>";
					var button = [{
						text:"关闭",
						handler:function(){
							var pwin = Ext.getCmp("histogram");
							if(pwin){
								pwin.close();
							}
						}
					}];
					showWindow("histogram","图形展示",document.body.clientWidth-100,document.body.clientHeight,null,button,html);
				}
			},"-"]
		});
		return balanceGrid;
	}
	
	/**
	 * 设置警报点列表展示
	 * @param {} width：列表展示的宽度
	 * @param {} height：列表展示的高度
	 * @param {} store：列表展示中的数据
	 * @return {}：展示列表
	 */
	function showAlertGrid(width,height,store){
		//数据展现样式
		var alertSM = new Ext.grid.CheckboxSelectionModel();
		var alertCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),alertSM,{
			dataIndex:"alertid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"userid",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"username",
			hidden:true,
			hideable:false//不允许将隐藏的字段显示出来
		},{
			dataIndex:"alerttype",
			header:"警报类型",
			renderer:showalerttype,
			width:80
		},{
			dataIndex:"alertvalue",
			header:"警报值（单位：人民币/元）",
			renderer:showmoney,
			width:150
		},{
			dataIndex:"begindate",
			header:"警报开始日期",
			renderer:showdate,
			width:80
		},{
			dataIndex:"enddate",
			header:"警报结束日期",
			renderer:showdate,
			width:80
		}]);
		
		var alertGrid = new Ext.grid.GridPanel({
			id:"alertGrid",
			//title:"警报点设置",
			collapsible:true,//是否可展开
			animCollapse:true,//展开是否动态效果
			autoScroll:true,//是否有滚动条
			width:width,
			height:height,
			loadMask:true,//是否载入动画
			frame:true,//是否渲染
			store:store,//数据存储
			cm:alertCM,
			sm:alertSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			region: 'north',
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:store,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录，共{2}条记录",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"新增警报点",
				handler:function(){
					var form = showAlertForm(false,true);
					var button = [{
						text:"保存",
						handler:function(){
							var win = Ext.getCmp("accountalert");
							if(win && form.form.isValid()){
								doSaveAlertAccount(win,form,store);
							}else{
								Ext.Msg.alert("提示信息","账目报警设置信息未填写完整，请填写完整之后再保存！");
							}
						}
					},{
						text:"取消",
						handler:function (){
							var win = Ext.getCmp("accountalert");
							if(win){
								win.close();
							}
						}
					}];
					showWindow("accountalert","账目报警点",500,300,form,button);
				}
			},"-",{
				text:"修改警报点",
				handler:function(){
					var gridSelection = alertGrid.getSelectionModel().getSelections();
					if(gridSelection.length != 1){
			            Ext.MessageBox.alert('提示','请选择一条警报点设置信息！');
			            return false;
			        }
			        var form = showAlertForm(false,false);
			        var button = [{
						text:"保存",
						handler:function(){
							var win = Ext.getCmp("accountalertedit");
							if(win && form.form.isValid()){
								doSaveAlertAccount(win,form,store);
							}else{
								Ext.Msg.alert("提示信息","账目报警设置信息未填写完整，请填写完整之后再保存！");
							}
						}
					},{
						text:"取消",
						handler:function (){
							var win = Ext.getCmp("accountalertedit");
							if(win){
								win.close();
							}
						}
					}];
					showWindow("accountalertedit","账目报警点",500,300,form,button);
					form.getForm().loadRecord(gridSelection[0]);
					var begindate = gridSelection[0].get("begindate");
					begindate = dateFormat(begindate,'Y-m-d H:i:s','Y-m-d');
					begindate = Date.parseDate(begindate,'Y-m-d');
					var enddate = gridSelection[0].get("enddate");
					enddate = dateFormat(enddate,'Y-m-d H:i:s','Y-m-d');
					enddate = Date.parseDate(enddate,'Y-m-d');
		        	form.form.findField("begindate").setValue(begindate);
		       		form.form.findField("enddate").setValue(enddate);
				}
			},"-",{
				text:"删除报警点",
				handler:function(){
					var gridSelection = alertGrid.getSelectionModel().getSelections();
					if(gridSelection.length < 1){
			            Ext.MessageBox.alert('提示','请至少选择一条警报点设置信息！');
			            return false;
			        }
			        var accountIds = "";
			        for(var i=0;i<gridSelection.length;i++){
			        	accountIds += gridSelection[i].get("alertid") + ",";
			        }
			        Ext.Msg.confirm("提示信息","您确定要删除所选择的警报点设置信息？删除之后无法恢复！",function(btn){
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
				    			params:{alertIds:accountIds},
				    			url:basePath+"balance/delaccountalert.action?method=deleteAccountAlert",
				    			success:function(response,options){
					    			Ext.MessageBox.hide();
							     	var re = Ext.util.JSON.decode(response.responseText);
							     	Ext.Msg.alert("提示信息",re.msg);
							     	if(re.success){
							     		store.reload();
							     		accountGroupStore.reload();
							     	}
						     	},failure:function(response,options){
						     		Ext.Msg.hide();
						     		var re = Ext.util.JSON.decode(response.responseText);
							    	Ext.Msg.alert("提示信息",re.msg);
							    	return;
						     	}
				    		});
			        	}
			        });
				}
			}]
		});
		return alertGrid;
	}
	
	/**
	 * 新增好友权限表单
	 */
	function showRightForm(){
		var showform = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth:70,
			frame:true,
			autoScroll:false,
			bodyStyle:"width:100%;overflow-x:hidden;height:100%;padding:0px 0px 0",
			waitMsgTarget:true,
			items:[{
				layout:"column",
				border:false,
				//height:90,
				labelSeparator:"：",
				items:[{
					layout:"form",
					columnWidth:.8,
					items:[{
						xtype: 'combo',
						name:"allowuserid",
						anchor:"99%",
						editable:false,//false：不可编辑
						fieldLabel:"好友姓名",
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"friendId",//将codeid设置为传递给后台的值
						displayField:"friendName",
						hiddenName:"allowuserid",//这个值就是传递给后台获取的值
						mode: "local",
						store:friendStore,
						allowBlank:false,
						listeners:{
							"select":function(combo,record,index){
								var allowusername = showform.form.findField("allowusername");
								allowusername.setValue(record.get("friendName"));
							}
						}
					},{
						xtype:"hidden",
						name:"allowusername"
					}]
				}]
			}]
		});
		return showform;
	}
	
	/**
	 * 查询账目信息form
	 */
	function showQueryForm(){
		var showform = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth:70,
			frame:true,
			autoScroll:true,
			bodyStyle:"width:100%;overflow-x:hidden;height:100%;padding:0px 0px 0",
			waitMsgTarget:true,
			items:[{
				layout:"column",
				border:false,
				//height:90,
				items:[{
					layout:"form",
					columnWidth:.5,
					labelSeparator:'>=',
					items:[{
						xtype: 'datefield',
						name:"begindate",
						id:"beginalertdate",
						anchor:"99%",
						vtype: 'daterange',
						endDateField: 'endalertdate', // id of the end date field
						fieldLabel:"记账日期",
						format:'Y-m-d',
						invalidText:'{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd'
					},{
						xtype: 'numberfield',
						name:"accountenterone",
						anchor:"99%",
						fieldLabel:"当天收入"
					},{
						xtype: 'numberfield',
						name:"accountoutone",
						anchor:"99%",
						fieldLabel:"当天支出"
					}]
				},{
					layout:"form",
					columnWidth:.5,
					labelSeparator:'<=',
					items:[{
						xtype: 'datefield',
						name:"enddate",
						id:"endalertdate",
						anchor:"99%",
						vtype: 'daterange',
						startDateField: 'beginalertdate', // id of the end date field
						fieldLabel:"记账日期",
						format:'Y-m-d',
						invalidText:'{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd'
					},{
						xtype: 'numberfield',
						name:"accountentertwo",
						anchor:"99%",
						fieldLabel:"当天收入"
					},{
						xtype: 'numberfield',
						name:"accountouttwo",
						anchor:"99%",
						fieldLabel:"当天支出"
					}]
				}]
			}]
		});
		return showform;
	}
	
	/**
	 * 设置报警点form表单
	 */
	function showAlertForm(isNull,disabled){
		var showform = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth:70,
			//autoScroll:true,
			frame:true,
			bodyStyle:"width:100%;overflow-x:hidden;height:100%;padding:0px 0px 0",
			//width:500,
			waitMsgTarget:true,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:90,
				items:[{
					layout:"form",
					columnWidth:.5,
					items:[{
						xtype: 'combo',
						name:"alerttype",
						anchor:"99%",
						editable:false,//false：不可编辑
						fieldLabel:"报警类型",
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"alerttype",//这个值就是传递给后台获取的值
						mode: "local",
						store:alertTypeStore,
						allowBlank:isNull,
						listeners :{
							"blur":function(field){
								var beginField = showform.form.findField("begindate");
								var endField = showform.form.findField("enddate");
								beginField.reset();
								endField.reset();
								beginField.purgeListeners();
								endField.purgeListeners();
								createDateInput(showform,field.getValue());
							},
							"select":function(combo,record,index){
								var beginField = showform.form.findField("begindate");
								var endField = showform.form.findField("enddate");
								beginField.setDisabled(false);
								endField.setDisabled(false);
							}
						}
					},{
						xtype: 'datefield',
						name:"begindate",
						id:"beginalertdate",
						anchor:"99%",
						vtype: 'daterange',
						endDateField: 'endalertdate', // id of the end date field
						fieldLabel:"起始日期",
						format:'Y-m-d',
						disabled:disabled,
						invalidText:'{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd',
						allowBlank:isNull
					},{
						xtype:"hidden",
						name:"alertid"
					}]
				},{
					layout:"form",
					columnWidth:.5,
					items:[{
						layout:"column",
						border:false,
						labelSeparator:'：',
						items:[{
							layout:"form",
							columnWidth:.65,
							items:[{
								xtype: 'numberfield',
								name:"alertvalue",
								anchor:"99%",
								fieldLabel:"报警临界值",
								allowBlank:isNull
							}]
						},{
							layout:"form",
							columnWidth:.35,
							items:[{
								html:'人民币（元）'
							}]
						}]
					},{
						xtype: 'datefield',
						name:"enddate",
						id:"endalertdate",
						anchor:"99%",
						vtype: 'daterange',
						startDateField: 'beginalertdate', // id of the end date field
						fieldLabel:"终止日期",
						format:'Y-m-d',
						disabled:disabled,
						invalidText:'{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd',
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:60,
				items:[{
					layout:"form",
					columnWidth:1,
					items:[{
						xtype:"textarea",
						name:"remark",
						anchor:"99%",
						fieldLabel:"备注"
					}]
				}]
			}]
		});
		return showform;
	}
	
	/**
	 * 创建报警点处的时间输入框
	 */
	function createDateInput(form,value){
		var beginField = form.form.findField("begindate");
		var endField = form.form.findField("enddate");
		if(value == "1"){//日报警点
			beginField.on("blur",function(field){
				beginField.setValue(field.getValue());
			});
			endField.on("blur",function(field){
				endField.setValue(field.getValue());
			});
		}else if(value == "2"){//月报警点
			beginField.on("blur",function(field){
				var begind = field.getValue();
				if(begind){
					begind = begind.getFirstDateOfMonth();
					beginField.setValue(begind);
				}
			});
			endField.on("blur",function(field){
				var newValue = field.getValue();
				if(newValue){
					var endd = newValue.getLastDateOfMonth();
					endField.setValue(endd);
				}
				
			});
		}else if(value == "3"){//季度报警点
			beginField.on("blur",function(field){
				var newValue = field.getValue();
				if(newValue){
					var currentMonth = newValue.format("m");
					var currentYear = newValue.format("Y");
					if(currentMonth>=10){
						currentMonth = 9;
					}else if(currentMonth >= 7){
						currentMonth = 6;
					}else if(currentMonth >= 4){
						currentMonth = 3;
					}else{
						currentMonth = 0;
					}
					var begind = new Date(currentYear,currentMonth,1);
					beginField.setValue(begind);
				}
			});
			endField.on("blur",function(field){
				var newValue = field.getValue();
				if(newValue){
					var currentMonth = newValue.format("m");
					var currentYear = newValue.format("Y");
					if(currentMonth>=10){
						currentMonth = 11;
					}else if(currentMonth >= 7){
						currentMonth = 8;
					}else if(currentMonth >= 4){
						currentMonth = 5;
					}else{
						currentMonth = 2;
					}
					var endd = new Date(currentYear,currentMonth,1);
					endd = endd.getLastDateOfMonth();
					endField.setValue(endd);
				}
			});
		}else if(value == "4"){//年度报警点
			beginField.on("blur",function(field){
				var newValue = field.getValue();
				if(newValue){
					var currentYear = newValue.format("Y");
					var begind = new Date(currentYear,0,1);
					beginField.setValue(begind);
				}
			});
			endField.on("blur",function(field){
				var newValue = field.getValue();
				if(newValue){
					var currentYear = newValue.format("Y");
					var endd = new Date(currentYear,11,31);
					endField.setValue(endd);
				}
			});
		}else{//自定义报警点
			beginField.on("blur",function(field){
				beginField.setValue(field.getValue());
			});
			endField.on("blur",function(field){
				endField.setValue(field.getValue());
			});
		}
		//var formLength = form.items.length;
		//form.add(endField);
		//form.doLayout();
	}
	
	/**
	 * 新增/修改的form表单
	 */
	function showAddEditForm(isNull,disabled){
		var showform = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:true,
			frame:true,
			bodyStyle:"width:100%;overflow-x:hidden;height:100%;padding:0px 0px 0",
			//width:500,
			waitMsgTarget:true,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:50,
				items:[{
					layout:"form",
					columnWidth:.5,
					items:[{
						xtype: 'datefield',
						name:"basedate",
						anchor:"99%",
						fieldLabel:"记账日期",
						allowBlank:isNull,
						format:"Y-m-d",
						listeners :{
							"change":function(field,newValue,oldValue){
								var date = newValue.format("Y-m");
								var yeardate = newValue.format("Y");
								showform.form.findField("baseyear").setValue(yeardate);
								showform.form.findField("basemonth").setValue(date);
							}
						}
					},{
						xtype:"hidden",
						name:"baseyear"
					},{
						xtype:"hidden",
						name:"basemonth"
					},{
						xtype:"hidden",
						name:"baseinfoid"
					},{
						xtype:"hidden",
						name:"deletetag",
						value:'0'
					},{
						xtype:"hidden",
						name:"margintag",
						value:'0'
					}]
				},{
					layout:"form",
					columnWidth:.5,
					items:[{
						xtype: 'numberfield',
						name:"accountenter",
						anchor:"99%",
						fieldLabel:"当天收入",
						allowBlank:isNull,
						validator:function(field){
							return !isNaN(field)
						},
						listeners :{
							"change":function(field,newValue,oldValue){
								if(!newValue || newValue==""){
									newValue = 0;
								}
								var out = showform.form.findField("accountout").getValue();
								if(!out || out==""){
									out = 0;
								}
								var maegin = (0 + parseFloat(newValue)) - (0 + parseFloat(out));
								maegin = 0 + maegin;
								showform.form.findField("accountmargin").setValue(maegin);
							}
						}
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:50,
				items:[{
					layout:"form",
					columnWidth:.5,
					items:[{
						xtype: 'numberfield',
						name:"accountout",
						anchor:"99%",
						fieldLabel:"当天支出",
						allowBlank:isNull,
						validator:function(field){
							return !isNaN(field)
						},
						listeners :{
							"change":function(field,newValue,oldValue){
								if(!newValue || newValue==""){
									newValue = 0;
								}
								var enter = showform.form.findField("accountenter").getValue();
								if(!enter || enter==""){
									enter = 0;
								}
								
								var maegin = (0 + parseFloat(enter)) - (0 + parseFloat(newValue));
								maegin = 0 + maegin;
								showform.form.findField("accountmargin").setValue(maegin);
							}
						}
					}]
				},{
					layout:"form",
					columnWidth:.5,
					items:[{
						xtype: 'numberfield',
						name:"accountmargin",
						anchor:"99%",
						fieldLabel:"当天结算",
						allowBlank:isNull,
						readOnly:true,
						validator:function(field){
							return !isNaN(field)
						}
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:50,
				items:[{
					layout:"form",
					columnWidth:1,
					items:[{
						xtype: 'textfield',
						name:"remark",
						anchor:"99%",
						fieldLabel:"记账事由",
						allowBlank:isNull
					}]
				}]
			}]
		});
		return showform;
	}
	
	/**
	 * 结算form表单
	 */
	function showMarginForm(){
		var showform = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			frame:true,
			bodyStyle:"width:100%;overflow-x:hidden;height:100%;padding:0px 0px 0",
			//width:500,
			waitMsgTarget:true,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				height:50,
				items:[{
					layout:"form",
					columnWidth:1,
					items:[{
						xtype: 'datefield',
						name:"marginmonth",
						anchor:"99%",
						fieldLabel:"结算月份",
						allowBlank:false,
						format:"Y-m"
					}]
				}]
			}]
		});
		return showform;
	}
	/**
	 * 共有窗口
	 */
	function showWindow(id,title,width,height,item,button,html){
		var win = Ext.getCmp(id);
		if(!width){
			width = 600;
		}
		if(!height){
			height = 400;
		}
		if(!win){
			win = new Ext.Window({
				id:id,
				title:title,
				width:width,
				height:height,
				items:item,
				layout:"fit",
				closeAction:"close",
				bodyStyle:'overflow-x:hidden;',
				autoScroll:true,
				modal:true,
				animateTarget:"accountdetail",//动画展示
				buttons:button,
				html:html,
				resizable:false
			});
		}
		win.show();
	}
	/**
	 * 执行保存操作
	 */
	function doSaveEditAccount(win,form,type){
		Ext.MessageBox.show({
			msg: '正在保存数据, 请稍侯...',
			progressText: '正在保存数据',
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			url:basePath+"balance/newaccountinfo.action?method=saveAccountDetail",
			params:{type:type,username:userName,userid:userid},
			method:"post",
			timeout:60000,
			success:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				if(re.success){
					accountGroupStore.reload();
					win.close();
				}
			},failure:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				return;
			}
		});
	}
	
	/**
	 * 执行当月结算
	 */
	function doMarginAccount(win,form){
		Ext.MessageBox.show({
			msg: '正在结算账目数据, 请稍侯...',
			progressText: '正在结算账目数据',
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		var currentMonth = form.form.findField("marginmonth").getValue();
		//var date = Date.parseDate(currentMonth+"-01","Y-m-d");
		var beginDate = currentMonth.getFirstDateOfMonth();
		var endDate = currentMonth.getLastDateOfMonth();
		form.getForm().submit({
			url:basePath+"balance/marginaccountinfo.action?method=marginAccountDetail",
			method:"post",
			timeout:60000,
			params:{beginDate:beginDate,endDate:endDate,username:userName,userid:userid},
			success:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				if(re.success){
					accountGroupStore.reload();
					win.close();
				}
			},failure:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				return;
			}
		});
	}
	/**
	 * 保存报警点信息
	 * @param {} win :弹出窗口，用于保存之后关闭
	 * @param {} form ：form表单用于提交
	 * @param {} store ：报警点列表用于reload。
	 */
	function doSaveAlertAccount(win,form,store){
		Ext.MessageBox.show({
			msg: '正在保存报警点信息, 请稍侯...',
			progressText: '正在保存报警点信息',
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			url:basePath+"balance/newaccoutalert.action?method=doSaveAccountAlert",
			method:"post",
			timeout:60000,
			params:{username:userName,userid:userid},
			success:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				if(re.success){
					store.reload();
					accountGroupStore.reload();
					win.close();
				}
			},failure:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				return;
			}
		});
	}
	
	/**
	 * 查询账目信息
	 * @param {} win
	 * @param {} form
	 */
	function doQueryAccount(win,form){
		Ext.MessageBox.show({
			msg: '正在查询账目信息, 请稍侯...',
			progressText: '正在查询账目信息',
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			url:basePath+"balance/queryaccount.action?method=queryAccountInfo",
			method:"post",
			timeout:60000,
			params:{username:userName,userid:userid},
			success:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				if(re.success){
					accountGroupStore.loadData(re);
					win.close();
				}
			},failure:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				return;
			}
		});
	}
	
	function doAddNewfriendRight(win,form,store){
		Ext.MessageBox.show({
			msg: '正在保存信息, 请稍侯...',
			progressText: '正在保存信息',
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			url:basePath+"balance/addrightaccount.action?method=addAccountRight",
			method:"post",
			timeout:60000,
			params:{username:userName,userid:userid},
			success:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				if(re.success){
					store.reload();
					win.close();
				}
			},failure:function(form,action){
				Ext.Msg.hide();
				var re = Ext.util.JSON.decode(action.response.responseText);
				Ext.Msg.alert("提示信息",re.msg);
				return;
			}
		});
	}
	
	/**
	 * 执行当天结算
	 */
	function marginOfDay(form){
		var out = form.form.findField("accountout").getValue();
		if(!out || out==""){
			out = 0;
		}
		var enter = form.form.findField("accountenter").getValue();
		if(!enter || enter==""){
			enter = 0;
		}
		var margin = (0 + parseFloat(enter)) - (0 + parseFloat(out));
		margin = 0 + margin;
		form.form.findField("accountmargin").setValue(margin);
	}
	//读取年度下拉框
	yearStore.load();
	//账目信息
	accountGroupStore.load({params:{username:userName,userid:userid,start:0,limit:9999999},callback:function(records,options,success){
		if(success){
			setDefaultComboYear();
		}
	}});
	//读取数据标准
	alertTypeStore.load({params:{codelistname:"账目报警"}});
	friendStore.load({params:{start:0,limit:999999},callback:function(records,option,success){
		for(var i=0;i<records.length;i++){
			var friendId = records[i].get("friendId");
			var friendName = records[i].get("friendName");
			var menuItem = new Ext.menu.Item({text:friendName,id:friendId});
			fmenu.add(menuItem).on("click",function(item){
				//要判断该用户是否设置本人可用查看该用户的账目信息
				Ext.Ajax.request({
					timeout:60000,
					params:{userid:item.id,username:item.text,allowuserid:userid,allowusername:userName},
					url:basePath+"balance/queryaccountright.action?method=queryAccountRight",
					method:"post",
					success:function(response,option){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.alert("提示信息","您没有权限查看该好友的账目信息！");
							return;
						}else{
							//账目信息
							accountGroupStore.baseParams.username = item.text;
							accountGroupStore.baseParams.userid = item.id;
							accountGroupStore.load({params:{username:item.text,userid:item.id,start:0,limit:9999999},callback:function(records,option,success){
								//var user
								//var toolbar = accountGrid.getTopToolbar();//.disable();
								setCmpDisabled(true);
								if(success){
									setDefaultComboYear();
								}
							}});
						}
					},failure:function(form,action){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.alert("提示信息","您没有权限查看该好友的账目信息！");
							return;
						}else{
							Ext.Msg.alert("提示信息","查询过程中出现异常，请联系管理员！");
							return;
						}
					}
				});
			});
		}
		if(records.length>0){
			fmenu.addSeparator();
		}
		var selfItem = new Ext.menu.Item({text:"返回我的账目",id:"selfItem"});
		fmenu.add(selfItem).on("click",function(item){
			accountGroupStore.baseParams.username = userName;
			accountGroupStore.baseParams.userid = userid;
			accountGroupStore.baseParams.year = "";
			accountGroupStore.load({params:{username:userName,userid:userid,start:0,limit:9999999},callback:function(){
				var accountyear = Ext.getCmp("accountyear");
				accountyear.reset();
				setCmpDisabled(false);
				if(success){
					setDefaultComboYear();
				}
			}});
		});
	}});
	/**
	 * 导出
	 * @param type
	 * @return
	 */
	function exportExcel(type,year){
		var comboYear = Ext.getCmp("accountyear");
		if(!year){
			if(comboYear){
				year = comboYear.getValue();
			}else{
				year = "";
			}
		}
		
		//document.getElementById("export2excel").src = basePath+"balance/export2Excel.action?method=export2Excel&type="+type+"&year="+year;		
		document.getElementById("export2excel").src = basePath+"balance/export2ExcelPoi.action?method=export2ExcelWithPOI&type="+type+"&year="+year;
	}
	/**
	 * 导入
	 */
	function importExcel(){
		//一个校验标志
		var isTrue = true;
		//一个form上传表单
		var importForm = new Ext.form.FormPanel({
			autoScroll:true,
			labelAlign: 'right',
			labelWidth:50,
			frame:true,
			waitMsgTarget:true,
			fileUpload: true,
			defaults: {
	            msgTarget: 'side'
	        },
	        items:[{
				xtype: 'fileuploadfield',
	            id: 'form-file',
	            width:250,
	            emptyText: '请选择您的账目信息文件',
	            fieldLabel: '文件',
	            name: 'accountFile',
	            allowBlank:false,
	            buttonCfg: {
	                text: '选择文件'
	            },
	            listeners:{
	            	"fileselected":function(fb,v){
	            		var extName = v.substr(v.lastIndexOf(".")+1);
	            		if(extName!="xls" && extName != "xlsx"){
	            			Ext.Msg.alert("提示信息","请您选择Excel文件！");
	            			isTrue = false;
	            			return;
	            		}else{
	            			isTrue = true;
	            		}
	            	}
	            }
			}]
		});
		//一个窗口
		var importWin;
		if(!importWin){
			importWin= new Ext.Window({
				id:"importWindow",
				title:"导入账目信息",
				layout:"fit",
				bodyStyle:'overflow-x:hidden;',
				autoScroll:true,
				width:350,
				height:110,
				items:importForm,
				modal:true,
				buttons:[{
					text:"导出模板",
					handler:function(){
						exportExcel("1");
					}
				},{
					text:"导入",
					handler:function(){
						if(importForm.form.isValid() && isTrue){
							//var url = basePath+"balance/importExcel.action?method=importExcel";
							var url = basePath+"balance/importExcelPoi.action?method=importExcelWithPOI";
							Ext.MessageBox.show({
								msg:"正在上传文件，请稍候...",
								progressText:"正在上传文件，请稍候...",
								width:300,
								wait:true,
								waitConfig: {interval:200},
								icon:Ext.Msg.INFO
							});
							importForm.getForm().submit({
								timeout:60000,
								params:{userid:userId,username:userName},
								url:basePath+"fileUploadServlet/fileUploadServlet?url="+url+"&save=true",
								method:"post",
								success:function(form,action){
									Ext.Msg.hide();
									var result = Ext.util.JSON.decode(action.response.responseText);
									var msg = result.msg;
									if(!msg || msg ==""){
										msg = "上传成功！";
									}
									Ext.Msg.alert("提示信息",msg,function(btn){
										if(btn == "ok" || btn == "yes"){
											importWin.close();
											accountGroupStore.reload();
										}
									});
								},failure:function(form,action){
									Ext.Msg.hide();
									var result = Ext.util.JSON.decode(action.response.responseText);
									var msg = result.msg;
									if(!msg || msg ==""){
										msg = "上传失败";
									}
									Ext.Msg.show({
										title:"提示信息",
										msg:msg,
										buttons:Ext.Msg.OK,
										icon:Ext.Msg.ERROR
									});
								}
							});
						}else{
							Ext.Msg.alert("提示信息","您选择的文件不合法，请重新选择！");
						}
					}
				},{
					text:"取消",
					handler:function(){
						importWin.close();
					}
				}],
				resizable:false
			});
		}
		
		importWin.show();
	}
	
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
	 * 格式化月份
	 */
	function showmonth(value,metadata,rocord,rowIndex,colIndex,store){
		//引用unit.js中的方法
		return dateFormat(value,'Y-m',"Y-M");
	}
	/**
	 * 提示双击修改
	 * @param value
	 * @param metadata
	 * @param record
	 * @param rowIndex
	 * @param colIndex
	 * @param store
	 * @return
	 */
	function edited(value,metadata,record,rowIndex,colIndex,store){
		metadata.attr = "ext:qtip='双击编辑'";
		return value;
	}
	/**
	 * 格式化金钱
	 */
	function showmoney(value,metadata,record,rowIndex,colIndex,store){
		if(parseFloat(value)<0){
			metadata.attr = "ext:qtip='今日已经入不敷出啦，注意节约哦！'";
			return "￥ "+"<font color='red'>"+value+"</font>";
		}
		return "￥ "+ value;
	}
	/**
	 * 格式化金额，并且比较年度总消费与年度警告，超出则已红色警告
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {}
	 */
	function showalertmoney(value,metadata,record,rowIndex,colIndex,store){
		var alertvalue = "";
		var accountenter = "";
		if(parseFloat(value)<0){
			metadata.attr = "ext:qtip='今日已经入不敷出啦，注意节约哦！'";
			return "￥ "+"<font color='red'>"+value+"</font>";
		}
		try{
			accountenter = record.get("accountenter");
			alertvalue = record.get("alertvalue");
			if(accountenter){
				if(parseFloat(value)>=parseFloat(accountenter)){
					metadata.attr = "ext:qtip='"+record.data.balanceyear+"年"+record.data.balancemonth+" 月消费过度啦，注意节约哦！'";
					return "￥ "+"<font color='red'>"+value+"</font>";
				}
			}
			/*
			if(parseFloat(value)>=parseFloat(alertvalue)){
				metadata.attr = "ext:qtip='"+record.get("balanceyear")+" 年消费过度啦，注意节约哦！'";
				return "￥ "+"<font color='red'>"+value+"</font>";
			}
			*/
		}catch(e){
			alertvalue = record.data.alertvalue;
			accountenter = record.data.accountenter;
			if(alertvalue){
				if(parseFloat(value)>=parseFloat(alertvalue)){
					metadata.attr = "ext:qtip='"+record.data.balanceyear+" 年消费过度啦，注意节约哦！'";
					return "￥ "+"<font color='red'>"+value+"</font>";
				}
			}
			if(accountenter){
				if(parseFloat(value)>=parseFloat(accountenter)){
					metadata.attr = "ext:qtip='"+record.data.balanceyear+" 年消费过度啦，注意节约哦！'";
					return "￥ "+"<font color='red'>"+value+"</font>";
				}
			}
		}
		return "￥ "+ value;
	}
	
	/**
	 * 格式化金额，小于0的将以红色展示
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 */
	function showresultmoney(value,metadata,record,rowIndex,colIndex,store){
		if(parseFloat(value)<0){
			metadata.attr = "ext:qtip='出现红色财政赤字啦，注意节约哦！'";
			return "￥ "+"<font color='red'>"+value+"</font>";
		}
		return "￥ "+ value;
	}
	
	/**
	 * 格式化金钱，并且判断是否超出警告点
	 */
	function showoutmoney(value,metadata,record,rowIndex,colIndex,store){
		var alertvalue = "";
		try{
			alertvalue = record.get("accountalert");
			metadata.attr = "ext:qtip='今日设置消费报警值：<br><font color=red>"+(isNaN(parseFloat(alertvalue))?"未设置":parseFloat(alertvalue))+"</font>  （单位：人民币/元）'";
			if(parseFloat(value)>=parseFloat(alertvalue)){
				return "￥"+"<font color='red'>"+value+"</font>";
			}
			return "￥"+value;
		}catch(e){
			var currentMonBalance = record.data.basedate;
			currentMonBalance = currentMonBalance.split(" ");
			var  currentMon = currentMonBalance[0] + "-" +currentMonBalance[2];
			if(parseFloat(value)>=parseFloat(record.data.accountalertmon)){
				return "￥"+"<font color='red'>"+value+"</font>";
			}
			return "￥"+value;
		}
	}
	
	/**
	 * 格式化结算标志
	 */
	function showmargintag(value,metadata,record,rowIndex,colIndex,store){
		if(value=="1"){
			return "已结算";
		}else{
			return "<font color='red'>未结算</font>";
		}
	}
	/**
	 * 格式化警报类型
	 * @param {} value ：要被格式化的值
	 * @param {} metadata
	 * @param {} rocord ：该行的数据
	 * @param {} rowIndex：第几行
	 * @param {} colIndex：第几列
	 * @param {} store：数据存储
	 */
	function showalerttype(value,metadata,rocord,rowIndex,colIndex,store){
		return getCodeNameFromStore(value,alertTypeStore,"codeid","codename");
	}
	/**
	 * 设置控件不可用
	 * @param {} disabled
	 */
	function setCmpDisabled(disabled){
		var newaccount = Ext.getCmp("newaccount");
		var editaccount = Ext.getCmp("editaccount");
		var delaccount = Ext.getCmp("delaccount");
		var queryaccount = Ext.getCmp("queryaccount");
		var setalert = Ext.getCmp("setalert");
		var setfriend = Ext.getCmp("setfriend");
		var balanceaccount = Ext.getCmp("balanceaccount");
		var balanceresult = Ext.getCmp("balanceresult");
		var accountyear = Ext.getCmp("accountyear");
		if(newaccount) newaccount.setDisabled(disabled);
		if(editaccount) editaccount.setDisabled(disabled);
		if(delaccount) delaccount.setDisabled(disabled);
		if(queryaccount) queryaccount.setDisabled(disabled);
		if(setalert) setalert.setDisabled(disabled);
		if(setfriend) setfriend.setDisabled(disabled);
		if(balanceaccount) balanceaccount.setDisabled(disabled);
		//if(accountyear) accountyear.setDisabled(disabled);
		if(balanceresult) balanceresult.setDisabled(disabled);
	}
	/**
	 * 设置下拉框中的默认年份
	 * @return
	 */
	function setDefaultComboYear(){
		var year = "";
		if(accountReader.jsonData){
			year = accountReader.jsonData.comboYear;
		}
		var comboYear = Ext.getCmp("accountyear");
		if(comboYear){
			comboYear.setValue(year);
		}
	}
}


/**
 * 程序主入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	accountBalance();
});