/**
 * 家庭基本信息管理
 */
function family_manage(){
	/**
	 * 家庭基本信息数据解析
	 */
	var familyListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "familyList",
		successProperty:"success"
	},[
		{name:"familyId"},//家庭ID
		{name:"familyName"},//家庭名称
		{name:"familyCreateDate"},//家庭创建日期
		{name:"familyHouseHolder"},//户主
		{name:"familyAddress"},//家庭地址
		{name:"familyTel"}//联系电话
	]);
	/**
	 * 数据存储
	 */
	var familyListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/family_manage/familyList.action?method=familyList"
		}),
		reader:familyListReader,
		listeners:{
			loadexception:function(dataProxy, type, action, options, response, arg) { 
				var o = Ext.util.JSON.decode(action.responseText);
				if(!o.success){
					Ext.Msg.alert('错误提示',o.msg, function(btn){
						if(btn == "yes" || btn == "ok"){
							if(o.msg1){
								//Ext.MessageBox.buttonText.yes = '按钮一';
    							//Ext.MessageBox.buttonText.no = '按钮二';
							    Ext.MessageBox.buttonText={
							        yes: "申请加入家庭",
									no: "创建家庭",
									cancel:"取消"
							    };

								Ext.Msg.show({
									title:"系统提示",
									msg:o.msg1,
									buttons: Ext.Msg.YESNOCANCEL,
									fn: processResult,
									icon: Ext.MessageBox.QUESTION
								});
							}
						}
					});
				}
			}
		}
	});
	
	function processResult(btn, text){
		if(btn == "yes"){
			
		}else if(btn == "no"){
			//window.location = path + "/family_manage/familyManage.action?method=begin";
			//parent.goToTabPanel("/family_manage/familyManage.action?method=begin");
		}else{
			
		}
	}
	
	var familyListSM = new Ext.grid.CheckboxSelectionModel();
	var familyListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),familyListSM,{
		dataIndex:"familyId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"家庭名称",
		dataIndex:"familyName",
		width:150
	},{
		header:"创建日期",
		dataIndex:"familyCreateDate",
		width:150
	},{
		header:"户主",
		dataIndex:"familyHouseHolder",
		width:150
	},{
		header:"家庭地址",
		dataIndex:"familyAddress",
		width:150
	},{
		header:"联系电话",
		dataIndex:"familyTel",
		width:150
	}]);
	
	var familyListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("family_manage").getWidth(),
		height:Ext.get("family_manage").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:familyListStore,
		renderTo:"family_manage",
		cm:familyListCM,
		sm:familyListSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:familyListStore,
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
	loadButtonRight(buttonRightStore, familyListStore, familyListDataGrid, "family_manage", loadParam);

}

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	family_manage();
});