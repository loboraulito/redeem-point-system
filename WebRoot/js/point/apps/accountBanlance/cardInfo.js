/**
 * 我的账户管理
 */
function accountInfoGrid(url){
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
	
	var cardInfoStore = new Ext.data.GroupingStore({
		url:url,
		reader:cardInfoReader,
		groupField:"cardType",
		//groupOnSort:false,
		baseParams:{userName:userName},
		sortInfo:{field: 'cardBalance', direction: "ASC"}
	});
	
	//账户临时数据
	var cardInfoTempDate = {"totalCount":0, "accountCard":[], "success":true};
	
	//数据展现样式
	var cardInfoSM = new Ext.grid.CheckboxSelectionModel();
	//展示样式
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
		width:150
	},{
		dataIndex:"cardType",
		header:"账户类型",
		width:150
	},{
		dataIndex:"cardStatus",
		header:"账户状态",
		width:150
	},{
		dataIndex:"cardBank",
		header:"开户行",
		width:150
	},{
		dataIndex:"cardCurrency",
		header:"币种",
		hidden:true,
		hideable:false
	},{
		dataIndex:"cardBalance",
		header:"余额",
		width:150
	},{
		dataIndex:"cardUser",
		header:"持卡人",
		hidden:true,
		hideable:false
	}]);
	
	//分组显示
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,
		enableNoGroups:false, // REQUIRED!
		hideGroupedColumn: false,
		groupTextTpl: '{text} ({[values.rs.length]}  "条账目明细")          本月设置消费报警值：<font color="red">{[isNaN(parseFloat(values.rs[0].data.accountalertmon))?"未设置":values.rs[0].data.accountalertmon]}</font>（单位：人民币/元）'
	});
	
	//展示列表
	this.cardInfoGrid = new Ext.grid.GridPanel({
		id:"cardInfoGrid",
		title:"账户信息",
		collapsible:true,//是否可以展开
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
			pageSize:50,//每页显示数
			store:cardInfoStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[]
	});
	
	cardInfoStore.load();
}