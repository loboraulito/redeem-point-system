/**
 * 家庭管理入口
 */
function family(){
	/**
	 * 家庭成员数据解析
	 */
	var memberListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "memberList",
		successProperty:"success"
	},[
		{name:"familyMemberId"},//家庭成员ID
		{name:"familyId"},//家庭ID
		{name:"familyMemberName"},//成员姓名
		{name:"systemMemberId"},//对应系统用户ID
		{name:"familyMemberCard"},//身份证
		{name:"familyMemberBirthdate"},//生日
		{name:"familyMemberBirthplace"},//出生地
		{name:"familyMemberSex"},//性别
		{name:"familyMemberHeight"},//身高
		{name:"familyMemberEducational"},//学历
		{name:"familyMemberProfession"},//职业
		{name:"familyMemberDeaddate"}//死亡日期
	]);
	/**
	 * 家庭成员数据存储
	 * 是否需要分组显示？
	 */
	var memberListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/family_member/familyMemberList.action?method=familyMemberList"
		}),
		reader:memberListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg);
				}
			}
		}
	});
	
	var memberListSM = new Ext.grid.CheckboxSelectionModel();
	var memberListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),memberListSM,{
		dataIndex:"familyMemberId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"familyId",
		hidden:true,
		hideable:false
	},{
		header:"家庭",
		dataIndex:"familyName",
		width:150
	},{
		header:"成员姓名",
		dataIndex:"familyMemberName",
		width:150
	},{
		dataIndex:"systemMemberId",
		hidden:true,
		hideable:false
	},{
		header:"身份证",
		dataIndex:"familyMemberCard",
		width:150
	},{
		header:"生日",
		dataIndex:"familyMemberBirthdate",
		width:150
	},{
		header:"出生地",
		dataIndex:"familyMemberBirthplace",
		width:150
	},{
		header:"性别",
		dataIndex:"familyMemberSex",
		width:150
	},{
		header:"身高",
		dataIndex:"familyMemberHeight",
		width:150
	},{
		header:"学历",
		dataIndex:"familyMemberEducational",
		width:150
	},{
		header:"职业",
		dataIndex:"familyMemberProfession",
		width:150
	},{
		dataIndex:"familyMemberDeaddate",
		hidden:true,
		hideable:false
	}]);
	/**
	 * 家庭成员列表
	 */
	var memberListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("family_div").getWidth(),
		height:Ext.get("family_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:memberListStore,
		renderTo:"family_div",
		cm:memberListCM,
		sm:memberListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:memberListStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[]
	});
	var loadParam = {};
	loadParam.start = 0;
	loadParam.limit = 50;
	loadParam.userId = userName;
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonRightStore = buttonRight();
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 * loadButtonRight(buttonStore, mainDataStore, dataGrid, pageDiv, params)
	 */
	loadButtonRight(buttonRightStore, memberListStore, memberListDataGrid, "family_div", loadParam);
	
}
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	family();
});
