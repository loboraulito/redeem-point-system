/**
 * 家庭成员关系管理
 */
function family_relation(){
	//加载关系下拉框
	var familyRelationStore = parent.familyRelationStore;
	familyRelationStore.load({params:{codeId:"8ac388f134220b830134221115020039"}});
	/**
	 * 家庭关系数据解析
	 */
	var relationListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "relationList",
		successProperty:"success"
	},[
		{name:"familyRelationId"},//主键
		{name:"familyRelation"},//家庭关系
		{name:"familyRelationFrom"},//
		{name:"familyRelationTo"}//
	]);
	
	/**
	 * 空数据，当主数据位空时，使用该数据以避免报错
	 * @type 
	 */
	var simpleData = {"totalCount":0,"relationList":[],"success":true};
	/**
	 * 数据加载参数
	 * @type 
	 */
	var loadParam = {
		start:0,
		limit:50,
		userId:userName
	};
	
	/**
	 * 家庭关系数据存储
	 */
	var relationListStore = new Ext.data.GroupingStore({
		url:path+"/family_relation/familyRelationList.action?method=familyRelationList",
		groupField:"familyName",
		sortInfo:{field: 'systemMemberId', direction: "ASC"},
		baseParams:loadParam,
		reader:relationListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				try{
					if(action.status == "200"){
						var o = Ext.util.JSON.decode(action.responseText);
						if(!o.success){
							Ext.Msg.alert('错误提示',o.msg, function(btn){
								relationListStore.loadData(simpleData);
							});
						}
					}else{
						httpStatusCodeHandler(action.status);
					}
				}catch(e){
					Ext.Msg.alert('错误提示',"系统错误！错误代码："+e);
				}
			}
		}
	});
	
	/**
	 * 数据标准分组显示
	 */
	var groupView = new Ext.grid.GroupingView({
		forceFit:true,
		showGroupName: false,//是否在分组行上显示分组字段的名字
		enableNoGroups:false, //是否允许用户关闭分组功能REQUIRED!
		hideGroupedColumn: false,//是否隐藏分组列
		enableGroupingMenu:false,//是否在表头菜单中进行分组控制
		groupTextTpl: '{text} 有 {[values.rs.length]} 位家庭成员'//用于渲染分组信息的模板，默认为'{text}'
	});
	
	var relationListSM = new Ext.grid.CheckboxSelectionModel();
	var relationListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),relationListSM,{
		dataIndex:"familyRelationId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"成员姓名",
		dataIndex:"familyMemberName",
		width:70
	},{
		header:"关系人",
		dataIndex:"familyRelationTo",
		width:70
	},{
		header:"关系",
		dataIndex:"familyRelation",
		renderer:relationCode,
		width:110
	}]);
	
	function relationCode(value,metadata,record,rowIndex,colIndex,store){
		return parent.getCodeNameFromStore(value,familyRelationStore,"dataKey","dataValue");
	}
	
	/**
	 * 家庭关系列表
	 */
	var memberListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("family_relation_div").getWidth(),
		height:Ext.get("family_relation_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:relationListStore,
		renderTo:"family_relation_div",
		cm:relationListCM,
		sm:relationListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		view:groupView,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:relationListStore,
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
	loadButtonRight(buttonRightStore, relationListStore, memberListDataGrid, "family_relation_div", loadParam);
	
	/**
	 * 家庭关系变更
	 * @param {} url
	 */
	this.familyRelationChange = function(url){
		
	};
}
/**
 * 入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	family_relation();
});