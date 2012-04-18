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
		url:basePath+"balance/accountinfo.action?method=accountDetailList",
		reader:accountReader,
		groupField:"basemonth",
		//groupOnSort:false,
		baseParams:{userName:userName, userId:userId},
		sortInfo:{field: 'basedate', direction: "ASC"},
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					var o = Ext.util.JSON.decode(action.responseText);
					if(o && !o.success){
						Ext.Msg.alert('错误提示',o.msg, function(btn){
							accountGroupStore.loadData(accountTempDate);
						});
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
			url:basePath+"balance/accoutalert.action?method=showAccoutAlert"
		}),
		reader:alertReader,
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
}

/**
 * 程序主入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	accountBalance();
});