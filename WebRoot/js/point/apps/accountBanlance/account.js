/**
 * 账目管理, 由ext系统移植而来
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
	//账目明细临时数据
	var accountTempDate = {"totalCount":0, "account":[], "success":true};
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
	//警报点临时数据
	var alertTempDate = {"totalAlertCount":0, "alerts":[], "success":true};
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
	//结算结果临时数据
	var balanceTempDate = {"totalCount":0, "balances":[], "success":true};
	//查看权限数据解析器
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
	//查看权限临时数据
	var rightTempDate = {"totalCount":0, "rights":[], "success":true};
	// define a custom summary function
	// 定义自定义结算函数
    Ext.grid.GroupSummary.Calculations['totalCost'] = function(v, record, field){
        return getRound(0 + parseFloat(parseFloat(v) + (parseFloat(record.data.accountenter) - parseFloat(record.data.accountout))),2);
    };
    Ext.grid.GroupSummary.Calculations['currentMonth'] = function(v, record, field){
        return dateFormat(record.data.basemonth,'Y-m',"Y 年 m 月")+" 小结";
    };
    Ext.grid.GroupSummary.Calculations['alertMonth'] = function(v, record, field){
        return record.data.accountalertmon;
    };
    Ext.grid.GroupSummary.Calculations['marginTag'] = function(v, record, field){
        return (record.data.margintag);
    };
    Ext.grid.GroupSummary.Calculations['balanceYear'] = function(v, record, field){
        return (record.data.balanceyear) +" 年度总结";
    };
    //合计栏
    var summary = new Ext.grid.GroupSummary(); 
    //主数据分组显示
	var accountGroupStore = new Ext.data.GroupingStore({
		url:path+"balance/accountinfo.action?method=accountDetailList",
		reader:accountReader,
		groupField:"basemonth",
		//groupOnSort:false,
		baseParams:{userName:userName},
		sortInfo:{field: 'basedate', direction: "ASC"},
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(o && !o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								accountGroupStore.loadData(accountTempDate);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
					accountGroupStore.loadData(accountTempDate);
				}
			}
		}
	});
	
	/**
	 * 警报点数据存储
	 */
	var alertStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"balance/accoutalert.action?method=showAccoutAlert"
		}),
		reader:alertReader,
		baseParams:{username:userName}
	});
	/**
	 * 结算账目数据存储,分组显示
	 */
	var balanceGroupStore = new Ext.data.GroupingStore({
		url:path+"balance/balancelist.action?method=showBalanceInfo",
		reader:balanceReader,
		groupField:"balanceyear",
		//groupOnSort:false,
		baseParams:{username:userName},
		sortInfo:{field: 'balancemonth', direction: "ASC"}
	});
	
	/**
	 * 展示可允许查看账目信息的好友信息数据存储
	 */
	var rightStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"balance/balanceright.action?method=showRightInfo"
		}),
		reader:rightReader,
		baseParams:{username:userName}
	});
	//分组显示
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,
		enableNoGroups:false, // REQUIRED!
		hideGroupedColumn: false,
		groupTextTpl: '{text} ({[values.rs.length]}  "条账目明细")          本月设置消费报警值：<font color="red">{[isNaN(parseFloat(values.rs[0].data.accountalertmon))?"未设置":values.rs[0].data.accountalertmon]}</font>（单位：人民币/元）'
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
		width:Ext.get("account_div").getWidth(),
		height:Ext.get("account_div").getHeight()-20,
		//width:800,
		//height:500,
		//loadMask:new Ext.LoadMask(Ext.getBody(),{msg:"数据正在加载中，请稍候..."}),
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:accountGroupStore,
		cm:accountCM,
		sm:accountSM,
		renderTo:"account_div",
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		//columnLines:true,
		//stripeRows: true,
		view:groupView,
		plugins: summary,
		listeners:{
			"rowdblclick":function(grid,rowIndex,e){
				
			}
		},
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:accountGroupStore,
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
	loadButtonRight(buttonRightStore, accountGroupStore, accountGrid, "account_div");
	
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
			var currentMon = currentMonBalance[0] + "-" +currentMonBalance[2];
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
}

/**
 * 程序主入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	accountBalance();
});