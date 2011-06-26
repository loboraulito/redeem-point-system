/**
 * 按钮管理系统
 */
function buttonManage(){
	/**
	 * buttonReader:按钮数据解析器
	 */
	var buttonReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "buttonList"
	},[
		{name:"buttonId"},//唯一id
		{name:"buttonName"},//按钮名称
		{name:"buttonText"},//按钮文字
		{name:"menuId"},//所属菜单ID
		{name:"menuName"},//所属菜单名称
		{name:"buttonUrl"},//按钮路径
		{name:"isShow"},//是否显示
		{name:"buttonIconCls"},//按钮样式
		{name:"handler"}//按钮触发的js方法
	]);
	var proxyUrl = path+"/button/buttonManageList.action?method=buttonList";
	/**
	 * buttonStore:按钮数据仓库
	 */
	var buttonStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		reader:buttonReader
	});
	/**
	 * buttonSM:数据展现样式
	 */
	var buttonSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * buttonCM:数据列展示样式
	 */
	var buttonCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),buttonSM,{
		dataIndex:"buttonId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"按钮名称",
		dataIndex:"buttonName",
		hidden:true,
		hideable:false
	},{
		header:"按钮显示文字",
		dataIndex:"buttonText",
		width:80
	},{
		header:"所属菜单",
		dataIndex:"menuName",
		renderer:showMenuUrl,
		width:60
	},{
		header:"所属菜单ID",
		dataIndex:"menuId",
		hidden:true,
		hideable:false
	},{
		header:"按钮路径",
		dataIndex:"buttonUrl",
		width:250
	},{
		header:"按钮样式",
		dataIndex:"buttonIconCls",
		width:50
	},{
		header:"按钮触发事件",
		dataIndex:"handler",
		width:60
	},{
		header:"是否显示",
		dataIndex:"isShow",
		renderer:isYesOrNo,
		width:40
	}]);
	/**
	 * buttonGrid: 菜单展示列表
	 */
	var buttonGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("button_div").getWidth(),
		height:Ext.get("button_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:buttonStore,
		renderTo:button_div,
		cm:buttonCM,
		sm:buttonSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:buttonStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[]
	});
	
	/**
	 * 是否显示
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {String}
	 */
	function isYesOrNo(value,metadata,record,rowIndex,colIndex,store){
		if(value=="yes"){
			return "显示";
		}else{
			return "不显示";
		}
	}
	/**
	 * 跳转到菜单管理页面,显示超级链接
	 * @param {} value
	 * @param {} metadata
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} store
	 * @return {}
	 */
	function showMenuUrl(value,metadata,record,rowIndex,colIndex,store){
		return "<a href = '###' onclick='showMenuManage()' title='点击查看菜单管理页面'>"+value+"</a>";
	}
	/**
	 * 跳转到菜单管理页面, 如何处理？
	 */
	this.showMenuManage = function(){
		var menuManageUrl = path + "/menu/beginMenuManage.action?method=begin";
		var tabPanel = parent.Ext.getCmp("mainTabPanel");
		var items = tabPanel.items;
		if(items){
			for(var i=0;i<items.length;i++){
				
			}
		}
		//tabPanel.activeTabPanel(0);
		//alert(1);
	}
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonRightStore = buttonRight();
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 */
	loadButtonRight(buttonRightStore, buttonStore, buttonGrid, "button_div");
	/**
	 * 添加按钮
	 * @param {} url
	 */
	this.addButton = function(url){
		
	}
	/**
	 * 修改按钮
	 * @param {} url
	 */
	this.editButton = function(url){
		
	}
	/**
	 * 删除菜单
	 * @param {} url
	 */
	this.deleteButton = function(url){
		var gridSelectionModel = buttonGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条按钮信息！');
		    return false;
		}
		Ext.Msg.confirm("系统提示信息","确定要删除所选按钮信息？",function(btn){
			if(btn == "ok" || btn == "yes"){
				var buttonids = [];
				for(var i=0;i<gridSelection.length; i++){
					var buttonId = gridSelection[i].get("buttonId");
					buttonids.push(buttonId);
				}
				var deleteButtons = buttonids.join(",");
				Ext.MessageBox.show({
					msg:"正在删除所选按钮信息，请稍候...",
					progressText:"正在删除所选按钮信息，请稍候...",
					width:300,
					wait:true,
					waitConfig: {interval:200},
					icon:Ext.Msg.INFO
				});
				Ext.Ajax.request({
					params:{buttonIds:deleteButtons},
					timeout:60000,
					url:url,
					success:function(response,options){
						Ext.MessageBox.hide();
						var msg = Ext.util.JSON.decode(response.responseText);
						if(msg && msg.msg){
							Ext.Msg.alert("提示信息",msg.msg);
						}else{
							Ext.Msg.alert("提示信息","所选按钮信息删除成功！");
							menuStore.reload();
						}
					},failure:function(response,options){
						Ext.Msg.hide();
						Ext.Msg.alert("提示信息","所选按钮信息删除失败！");
						return;
					}
				});
			}
		});
	}
	
	/**
	 * 菜单窗口, 用于新增，修改
	 * @param {} id 窗口ID
	 * @param {} title 窗口名字
	 * @param {} width 窗口宽度
	 * @param {} height 窗口高度
	 * @param {} items 窗口的内部
	 * @param {} buttons 窗口的按钮
	 */
	function showMenuWindow(id, title, width, height, items, buttons){
		var menuWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			buttons:buttons,
			modal:true,
			layout:"fit"
		});
		menuWindow.show();
	}
	/**
	 * 按钮表单
	 * @param {} url
	 * @param {} isNull
	 * @return {}
	 */
	function showMenuForm(url,isNull){
		var menuForm = new Ext.form.FormPanel({
			frame: true,
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			url:url,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"menuName",
						anchor:"90%",
						fieldLabel:"菜单名称",
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'treeField',
						name:"parentMenuId",
						displayField:"menuName",
						valueField:"menuId",
						hiddenName:"parentMenuId",
						dataUrl:path+"/menu/menuComboTree.action?method=menuComboTree",
						listHeight:180,
						//selectNodeModel:"all",
						treeRootConfig:{
							id:" ",
							draggable:false,
							text:"会员积分兑换系统"
						},
						anchor:"90%",
						fieldLabel:"上级菜单",
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"isLeave",
						anchor:"90%",
						fieldLabel:"叶子节点",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeid",//将codeid设置为传递给后台的值
						displayField:"codename",
						hiddenName:"isLeave",//这个值就是传递给后台获取的值
						mode: "local",
						store:new Ext.data.SimpleStore({
							fields:["codeid","codename"],
							data:[["1","是"],["0","否"]]
						}),
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"pagePath",
						anchor:"90%",
						fieldLabel:"菜单路径",
						allowBlank:isNull
					},{
						xtype: 'hidden',
						name:"menuId"
					},{
						xtype: 'hidden',
						name:"menuLevel"
					},{
						xtype: 'hidden',
						name:"parentMenuName"
					}]
				}]
			}]
		});
		return menuForm;
	}
}


/**
 * 按钮管理入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';//side
	buttonManage();
});