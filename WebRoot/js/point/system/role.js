/**
 * 角色管理
 */
function roleManage(){
	/**
	 * roleReader - 角色信息解析器
	 */
	var roleReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "roleList"
	},[
		{name:"roleId"},//唯一id
		{name:"roleName"},//菜单名称
		{name:"comment"}//菜单路径
	]);
	
	var proxyUrl = path+"/role/roleList.action?method=roleManageList";
	/**
	 * roleStore:角色数据仓库
	 */
	var roleStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		reader:roleReader
	});
	
	/**
	 * roleSM:数据展现样式
	 */
	var roleSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * roleCM:数据列展示样式
	 */
	var roleCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),roleSM,{
		dataIndex:"roleId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"角色名称",
		dataIndex:"roleName",
		width:150
	},{
		header:"备注",
		dataIndex:"comment",
		width:150
	}]);
	
	/**
	 * roleGrid: 角色展示列表
	 */
	var roleGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("role_div").getWidth(),
		height:Ext.get("role_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:roleStore,
		renderTo:"role_div",
		cm:roleCM,
		sm:roleSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:roleStore,
			displayInfo:true,
			displayMsg:"显示{0}-{1}条记录，共{2}条记录",
			nextText:"下一页",
			prevText:"上一页",
			emptyMsg:"无相关记录"
		}),
		tbar:[{
			text:"测试按钮1",
			hidden:true,
			id:"menu_addMenu",
			iconCls:"table_add",
			tooltip:"测试按钮1"
		},{
			text:"测试按钮2",
			hidden:true,
			id:"menu_editMenu",
			iconCls:"table_edit",
			tooltip:"测试按钮2"
		}]
	});
	
	/**
	 * 按钮存储器，尚未执行查询
	 */
	var buttonRightStore = buttonRight();
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 */
	loadButtonRight(buttonRightStore, roleStore, roleGrid, "role_div");
	/**
	 * 增加角色
	 * @param {} url
	 */
	this.addRole = function(url){
		var roleForm = showRoleForm(url, false);
		var button = [{
			text:"保存",
			handler:function(){
				if(roleForm.form.isValid()){
					saveRole("addRoleWindow", roleForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var currentWindow = Ext.getCmp("addRoleWindow");
				if(currentWindow){
					currentWindow.close();
				}
			}
		}];
		showRoleWindow("addRoleWindow","添加角色信息",400, 200, roleForm, button);
	}
	/**
	 * 编辑角色
	 * @param {} url
	 */
	this.editRole = function(url){
		var gridSelectionModel = roleGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length != 1){
			Ext.MessageBox.alert('提示','请选择一条角色信息！');
		    return false;
		}
		var roleForm = showRoleForm(url, false);
		var button = [{
			text:"保存",
			handler:function(){
				if(roleForm.form.isValid()){
					saveRole("editRoleWindow", roleForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var currentWindow = Ext.getCmp("editRoleWindow");
				if(currentWindow){
					currentWindow.close();
				}
			}
		}];
		showRoleWindow("editRoleWindow","修改角色信息",400, 200, roleForm, button);
		
		roleForm.getForm().loadRecord(gridSelection[0]);
	}
	/**
	 * 删除角色
	 * @param {} url
	 */
	this.deleteRole = function(url){
		var gridSelectionModel = roleGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length < 1){
			Ext.MessageBox.alert('提示','请至少选择一条角色信息！');
		    return false;
		}
		
		Ext.Msg.confirm("系统提示信息","删除角色信息的同时将删除与其相关的权限信息！确定吗？",function(btn){
			if(btn == "yes" || btn == "ok"){
				var roleArray = [];
				for(var i=0;i<gridSelection.length; i++){
					var roleId = gridSelection[i].get("roleId");
					roleArray.push(roleId);
				}
				var roleList = roleArray.join(",");
				Ext.MessageBox.show({
					msg:"正在删除所选角色信息，请稍候...",
					progressText:"正在删除所选角色信息，请稍候...",
					width:300,
					wait:true,
					waitConfig: {interval:200},
					icon:Ext.Msg.INFO
				});
				Ext.Ajax.request({
					params:{roleList:roleList},
					timeout:60000,
					url:url,
					success:function(response,options){
						Ext.MessageBox.hide();
						var msg = Ext.util.JSON.decode(response.responseText);
						if(msg && msg.success){
							Ext.Msg.alert("提示信息","所选角色信息删除成功！");
							roleStore.reload();
						}else if(msg && !msg.success){
							Ext.Msg.alert("提示信息","所选角色信息删除失败！");
						}
					},failure:function(response,options){
						Ext.Msg.hide();
						Ext.Msg.alert("提示信息","所选角色信息删除失败！");
						return;
					}
				});
			}
		});
	}
	
	/**
	 * 窗口, 用于新增，修改
	 * @param {} id 窗口ID
	 * @param {} title 窗口名字
	 * @param {} width 窗口宽度
	 * @param {} height 窗口高度
	 * @param {} items 窗口的内部
	 * @param {} buttons 窗口的按钮
	 */
	function showRoleWindow(id, title, width, height, items, buttons){
		var menuWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			buttons:buttons,
			modal:true,
			layout:"fit",
			resizable:false
		});
		menuWindow.show();
	}
	
	/**
	 * 表单
	 * @param {} url
	 * @param {} isNull
	 * @return {}
	 */
	function showRoleForm(url,isNull){
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
					columnWidth:.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"roleName",
						anchor:"90%",
						fieldLabel:"角色名称",
						maxLength:200,
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
					height:80,
					items:[{
						xtype: 'textarea',
						name:"comment",
						anchor:"90%",
						fieldLabel:"备注",
						maxLength:500
					},{
						xtype:"hidden",
						name:"roleId"
					}]
				}]
			}]
		});
		return menuForm;
	}
	/**
	 * 保存角色信息
	 * @param {} windowId
	 * @param {} form
	 */
	function saveRole(windowId, form){
		Ext.MessageBox.show({
			msg:"正在保存角色信息，请稍候...",
			progressText:"正在保存角色信息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				Ext.Msg.alert('系统提示信息', '角色信息保存成功!', function(btn, text) {
					if (btn == 'ok') {
						var msg = Ext.decode(action.response.responseText);
						roleStore.reload();
						Ext.getCmp(windowId).close();
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				Ext.Msg.alert('系统提示信息', "角色信息保存过程中出现异常!");
			}
		});
	}
}
/**
 * 角色管理
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	roleManage();
});