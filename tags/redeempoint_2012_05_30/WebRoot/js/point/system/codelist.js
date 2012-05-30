/**
 * 数据字典
 */
function codeListDataManage(){
	/**
	 * 数据字典
	 */
	var codeListReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "codeList"
	},[
		{name:"codeId"},//数据字典ID
		{name:"codeName"}//数据字典名称
	]);
	
	var codeListDataReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "codeListData"
	},[
		{name:"dataId"},//数据字典数据ID
		{name:"dataKey"},//数据字典数据key
		{name:"dataValue"},//数据字典值
		{name:"codeId"},//数据字典ID
		{name:"codeName"},//数据字典
		{name:"parentDataKey"},//父级数据字典
		{name:"parentDataValue"},//父级数据字典
		{name:"remark"}//备注
	]);
	/**
	 * 数据字典列表
	 */
	var proxyCodeUrl = path+"/codelist/codeList.action?method=codeList";
	/**
	 * 数据列表
	 */
	var proxyDataUrl = path+"/codelist/codeDataList.action?method=codeDataList";
	/**
	 * 数据标准存储
	 */
	var codeListStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyCodeUrl
		}),
		reader:codeListReader
	});
	/**
	 * 数据标准存储(复制品)
	 */
	var codeListStoreClon = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyCodeUrl
		}),
		reader:codeListReader
	});
	
	/**
	 * 数据标准数据存储
	 */
	var codeListDataStore = new Ext.data.GroupingStore({
		url:proxyDataUrl,
		reader:codeListDataReader,
		groupField:"codeName",
		sortInfo:{field: 'dataId', direction: "ASC"}
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
		groupTextTpl: '{text} ({[values.rs.length]} {text})'//用于渲染分组信息的模板，默认为'{text}'
	});
	
	/**
	 * 数据展现形式 - 多选框
	 */
	var codeDataSM = new Ext.grid.CheckboxSelectionModel();
	var codeListSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * 数据字典展现形式
	 */
	var codeListCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),codeListSM,{
		dataIndex:"codeId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"数据标准名称",
		dataIndex:"codeName",
		width:150
	}]);
	/**
	 * 数据字典数据展现形式
	 */
	var codeDataCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),codeDataSM,{
		dataIndex:"dataId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"数据标准",
		dataIndex:"codeName",
		width:150
	},{
		header:"数据标准编码",
		dataIndex:"dataKey",
		width:150
	},{
		header:"数据标准值",
		dataIndex:"dataValue",
		width:150
	},{
		header:"上级数据标准值",
		dataIndex:"parentDataValue",
		width:150
	},{
		header:"数据标备注",
		dataIndex:"remark",
		width:150
	}]);
	/**
	 * 数据字典数据列表
	 */
	var codeListDataGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("codelist_div").getWidth(),
		height:Ext.get("codelist_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:codeListDataStore,
		renderTo:"codelist_div",
		cm:codeDataCM,
		sm:codeDataSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		view:groupView,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:codeListDataStore,
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
	loadButtonRight(buttonRightStore, codeListDataStore, codeListDataGrid, "codelist_div");
	/**
	 * 初始化数据标准下拉框
	 */
	codeListStoreClon.load({params:{start:0,limit:99999}});
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////////////数据标准值管理////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 增加数据标准值
	 * @param {Object} url
	 */
	this.addCodeData = function(url){
		var dataForm = getCodeDataForm(url, false, false);
		var buttons = [{
			text:"保存数据标准",
			handler:function(){
				if(dataForm.form.isValid()){
					saveCodeData("addCodeDataWindow", dataForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("addCodeDataWindow");
				if(w){
					w.close();
				}
			}
		}];
		//codeListStoreClon.load({params:{start:0,limit:99999}});
		showCodeListWindow("addCodeDataWindow","添加数据标准值",500,270,dataForm,"",buttons);
	};
	
	/**
	 * 修改数据标准值
	 * @param {Object} url
	 */
	this.editCodeData = function(url){
		var gridSelectionModel = codeListDataGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length != 1){
			Ext.MessageBox.alert('提示','请选择一条数据标准值信息！');
		    return false;
		}
		var dataForm = getCodeDataForm(url, false, false);
		var buttons = [{
			text:"保存数据标准",
			handler:function(){
				if(dataForm.form.isValid()){
					saveCodeData("editCodeDataWindow", dataForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("editCodeDataWindow");
				if(w){
					w.close();
				}
			}
		}];
		
		showCodeListWindow("editCodeDataWindow","修改数据标准值",500,270,dataForm,"",buttons);
		dataForm.getForm().loadRecord(gridSelection[0]);
		
		var node = {};
		node.text = gridSelection[0].get("parentDataValue");
		node.id = gridSelection[0].get("parentDataKey");
		
		dataForm.form.findField("parentDataKey").setValue(node);
		var formTree = dataForm.form.findField("parentDataKey").tree;
		var root = formTree.root;
		var loader = formTree.loader;
		loader.baseParams.codeId = gridSelection[0].get("codeId");
		root.reload(
			function(){
				//使用expandAll会导致首次加载时间过长！
				//formTree.expandAll();
			}
		);
		//dataForm.form.findField("parentDataKey").tree.expandAll();
	};
	/**
	 * 删除数据标准值
	 * @param {Object} url
	 */
	this.deleteCodeData = function(url){
		Ext.Msg.confirm("系统提示","确定需要删除所选数据标准值信息么？",function(btn){
			var gridSelectionModel = codeListDataGrid.getSelectionModel();
			var gridSelection = gridSelectionModel.getSelections();
			if(gridSelection.length < 1){
				Ext.MessageBox.alert('提示','请至少选择一条数据标准值信息！');
			    return false;
			}
			var dataIdArray = new Array();
			for(var i = 0; i < gridSelection.length; i++){
				dataIdArray.push(gridSelection[i].get("dataId"));
			}
			var dataIds = dataIdArray.join(",");
			deleteCodeDataList(url, dataIds);
		});
	};
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////////////数据标准值管理////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////////////数据标准管理//////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 数据标准管理窗口列表
	 * @param {Object} url
	 */
	this.codeListManage = function(url){
		
		var buttons = [{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("codeListManageWindow");
				if(w){
					w.close();
				}
			}
		}];
		//showCodeListWindow("codeListManageWindow","数据标准管理",400,300,null, "<div id='codeListGrid_div'></div>",buttons);
		var codeListGrid = new Ext.grid.GridPanel({
			id:"codeListGrid",
			collapsible:true,//是否可以展开
			animCollapse:true,//展开时是否有动画效果
			autoScroll:true,
			//width:Ext.get("codelist_div").getWidth(),
			//height:250,//Ext.get("codeListManageWindow").getHeight()-20,
			loadMask:true,//载入遮罩动画（默认）
			frame:true,
			autoShow:true,		
			store:codeListStore,
			//renderTo:"codeListGrid_div",
			cm:codeListCM,
			sm:codeListSM,
			viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
			split: true,
			bbar:new Ext.PagingToolbar({
				pageSize:50,//每页显示数
				store:codeListStore,
				displayInfo:true,
				displayMsg:"显示{0}-{1}条记录,共{2}条记录",
				nextText:"下一页",
				prevText:"上一页",
				emptyMsg:"无相关记录"
			}),
			tbar:[{
				text:"正在加载中..."
			}]
		});
		showCodeListWindow("codeListManageWindow","数据标准管理",410,300,codeListGrid,"",buttons);
		
		var loadMarsk = codeListGrid.loadMask;
		loadMarsk.show();
		
		var codeListMenuId = "";
		//查询数据标准列表管理页面的权限按钮
		Ext.Ajax.request({
			params:{menuPath:url},
			timeout:60000,
			url:path + "/menu/findMenuId.action?method=findMenuId",
			success:function(response, options){
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					codeListMenuId = msg.menuId;
					/**
					 * 执行权限按钮
					 */
					loadButtonRight(buttonRightStore, codeListStore, codeListGrid, null, null, codeListMenuId, function(codeListGrid,tbar){
						codeListGrid.setHeight(codeListGrid.getSize().height - codeListGrid.tbar.getHeight() + codeListGrid.bbar.getHeight());
						codeListGrid.render();
						codeListGrid.doLayout();
						loadMarsk.hide();
					});
				}
			},failure: function(response, options){
				codeListMenuId = "";
			}
		});
		
		//codeListStore.load({params:{start:0,limit:50}});
	};
	
	/**
	 * 添加数据标准
	 * @param {Object} url
	 */
	this.addCodeList = function(url){
		Ext.Msg.prompt("添加数据标准","请输入数据标准名称",function(btn, text){
			if(btn == "ok" || btn == "yes"){
				saveCodeList(url, null, text);
			}
		});
	};
	
	/**
	 * 修改数据标准
	 * @param {Object} url
	 */
	this.editCodeList = function(url){
		var codeListGrid = Ext.getCmp("codeListGrid");
		if(codeListGrid){
			var gridSelectionModel = codeListGrid.getSelectionModel();
			var gridSelection = gridSelectionModel.getSelections();
			if(gridSelection.length != 1){
				Ext.MessageBox.alert('提示','请选择一条数据标准信息！');
			    return false;
			}
			var codeList = gridSelection[0];
			var codeId = codeList.get("codeId");
			var codeName = codeList.get("codeName");
			//prompt( String title, String msg, [Function fn], [Object scope], [Boolean/Number multiline], [String value] ) 
			Ext.Msg.prompt("修改数据标准","请输入数据标准名称",function(btn, text){
				if(btn == "ok" || btn == "yes"){
					if(text == codeName){
						Ext.Msg.alert("系统提示","您未修改数据标准名称！");
						return;
					}
					saveCodeList(url, codeId, text);
				}
			},null,false,codeName);
		}
	};
	
	/**
	 * 删除数据标准
	 * @param {Object} url
	 */
	this.deleteCodeList = function(url){
		Ext.Msg.confirm("系统提示","删除数据标准的同时将会删除对应的标准值。确定需要删除么，？",function(btns){
			if(btns == "yes" || btns == "ok"){
				var codeListGrid = Ext.getCmp("codeListGrid");
				if (codeListGrid) {
					var gridSelectionModel = codeListGrid.getSelectionModel();
					var gridSelection = gridSelectionModel.getSelections();
					if (gridSelection.length < 1) {
						Ext.MessageBox.alert('提示', '请至少选择一条数据标准信息！');
						return false;
					}
					var codeLists = new Array();
					for(var i = 0; i < gridSelection.length; i++){
						codeLists.push(gridSelection[i].get("codeId"));
					}
					codeLists.join(",");
					saveCodeList(url, codeLists, null);
				}
			}
		});
	};
	
	/**
	 * 导出数据标准值
	 * @param {Object} url
	 */
	this.exportCodeDataList = function(url){
		//提高给用户选择要导出哪些字段
		var exportForm = getExportForm(url);
		var buttons = [{
			text:"导出",
			handler:function(){
				exportForm.getForm().submit({
				});
				//document.getElementById("exportProperty").value = (exportForm.getForm().getValues(true));
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("exportCodeDataWindow");
				if(w){
					w.close();
				}
			}
		}];
		showCodeListWindow("exportCodeDataWindow","导出数据标准",370, 230, exportForm, "", buttons);
		//document.getElementById("export2excel").src = url;
	};
	
	/**
	 * 导出数据标准值模板
	 * @param {Object} url
	 */
	this.exportCodeDataDemo = function(url){
		//提高给用户选择要导出哪些字段
		var exportForm = getExportForm(url);
		var buttons = [{
			text:"导出",
			handler:function(){
				exportForm.getForm().submit({
				});
				//document.getElementById("exportProperty").value = (exportForm.getForm().getValues(true));
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("exportCodeDataDemoWindow");
				if(w){
					w.close();
				}
			}
		}];
		showCodeListWindow("exportCodeDataDemoWindow","导出数据标准模板",370, 230, exportForm, "", buttons);
		//document.getElementById("export2excel").src = url;
	};
	
	/**
	 * 导入数据标准
	 * @param {Object} url
	 */
	this.importCodeDataList = function(url){
		var isTrue = false;
		var importForm = new Ext.form.FormPanel({
			url:url,
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
	            emptyText: '请选择数据标准信息文件',
	            fieldLabel: '文件',
	            name: 'codeDataList',
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
		var buttons = [{
			text:"上传",
			handler:function(){
				if(isTrue || true){
					saveImportCodeData("importCodeDataWindow", importForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("importCodeDataWindow");
				if(w){
					w.close();
				}
			}
		}];
		showCodeListWindow("importCodeDataWindow","导入数据标准",350, 110, importForm, "", buttons);
	};
	/**
	 * 查询数据标准
	 * @param {} url
	 */
	this.queryCodeData = function(url){
		var dataForm = getCodeDataForm(url, true, false);
		var buttons = [{
			text:"开始查询",
			handler:function(){
				if(dataForm.form.isValid()){
					queryCodeDataList("queryCodeDataWindow", dataForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var w = Ext.getCmp("queryCodeDataWindow");
				if(w){
					w.close();
				}
			}
		}];
		//codeListStoreClon.load({params:{start:0,limit:99999}});
		showCodeListWindow("queryCodeDataWindow","查询数据标准值",500,270,dataForm,"",buttons);
	};
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////////////数据标准管理//////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 数据标准值表单
	 * @param {Object} url 保存数据标准值路径
	 * @param {Object} isNull 是否可为空, true - 是
	 * @param {Object} readOnly 是否只读, true - 是
	 */
	function getCodeDataForm(url, isNull, readOnly){
		var codeDataForm = new Ext.form.FormPanel({
			url:url,
			frame: true,
			labelAlign: 'right',
			labelWidth:90,
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
						name:"dataKey",
						anchor:"90%",
						fieldLabel:"数据标准值代码",
						maxLength:200,
						readOnly:readOnly,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"dataValue",
						anchor:"90%",
						fieldLabel:"数据标准值",
						maxLength:200,
						readOnly:readOnly,
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'combo',
						name:"codeId",
						anchor:"90%",
						fieldLabel:"所属数据标准",
						editable:false,//false：不可编辑
						triggerAction:"all",//避免选定了一个值之后，再选的时候只显示刚刚选择的那个值
						valueField:"codeId",//将codeid设置为传递给后台的值
						displayField:"codeName",
						hiddenName:"codeId",//这个值就是传递给后台获取的值
						mode: "local",
						store:codeListStoreClon,
						allowBlank:isNull,
						listeners: {
							'change': function(combo, record, index){
								var tree = codeDataForm.form.findField("parentDataKey").tree;
								var root = tree.root;
								/*
								var treeRoot = new Ext.tree.AsyncTreeNode({
									id : combo.getValue(),
									text : combo.getEl().dom.value,
									singleClickExpand:true,
									draggable:false
								});
								*/
								root.text = combo.getEl().dom.value;
								tree.root = root;
								var loader = codeDataForm.form.findField("parentDataKey").tree.loader;
								loader.baseParams.codeId = combo.getValue();
								
								root.reload(
									function(){
										//使用expandAll会导致首次加载时间过长！
										//tree.expandAll();
									}
								);
							},
							"select":function(combo, record, index){
								codeDataForm.form.findField("parentDataKey").setValue("");
							}
						}
					}]
				},{
					layout:"form",
					columnWidth:0.5,
					height:50,
					items:[{
						xtype: 'treeField',
						name:"parentDataKey",
						displayField:"parentDataValue",
						valueField:"parentDataKey",
						hiddenName:"parentDataKey",
						dataUrl:path+"/codelist/codeDataManageTree.action?method=codeDataManageTree",
						listHeight:180,
						selectNodeModel:"exceptRoot",
						treeRootConfig:{
							id:" ",
							draggable:false,
							singleClickExpand:true,
							text:"请选择..."
						},
						anchor:"90%",
						fieldLabel:"上级数据标准值"
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:0.9,
					height:70,
					items:[{
						xtype: 'textarea',
						name:"remark",
						anchor:"90%",
						fieldLabel:"备注",
						maxLength:200
					},{
						xtype:"hidden",
						name:"dataId"
					}]
				}]
			}]
		});
		return codeDataForm;
	}
	/**
	 * 获取导出字段的form表单
	 * @param {Object} url
	 */
	function getExportForm(url){
		//提供给用户选择要导出哪些字段
		var exportForm = new Ext.form.FormPanel({
			//url:url,
			frame: true,
			//labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			viewConfig:{forceFit:true},
			//实现非Ajax提交表单
			onSubmit:Ext.emptyFn,
			submit:function(){
				this.getEl().dom.action = url;
				this.getEl().dom.target = "targetFram";
				this.getEl().dom.submit();
			},
			items:[{
				xtype:'fieldset',
				title: '请选择要导出的字段',
				autoHeight: true,
				defaultType: 'checkbox',
				items:[{
					xtype:"checkboxgroup",
					fieldLabel:"可选字段",
					//columns: [120, 100],
					//vertical: true,
					columns: 2,
					items:[
						{boxLabel: '数据标准值唯一编码', name: 'dataId',inputValue :"数据标准值唯一编码"},
	                    {boxLabel: '数据标准值编号', name: 'dataKey', inputValue :"数据标准值编号", checked: true},
	                    {boxLabel: '数据标准值', name: 'dataValue', inputValue:"数据标准值",checked: true},
	                    {boxLabel: '数据标准分类编码', name: 'codeId', inputValue:"数据标准分类编码"},
	                    {boxLabel: '数据标准分类', name: 'codeName', inputValue:"数据标准分类", checked: true},
						{boxLabel: '上级数据标准值编号', name: 'parentDataKey', inputValue:"上级数据标准值编号"},
						{boxLabel: '上级数据标准值', name: 'parentDataValue', inputValue:"上级数据标准值", checked: true},
						{boxLabel: '备注', name: 'remark', inputValue:"备注", checked: true}
					]
				}]
			}]
		});
		return exportForm;
	}
	
	/**
	 * 公用窗口
	 * @param {Object} id  唯一标识
	 * @param {Object} title 窗口显示的名称
	 * @param {Object} width 窗口宽度
	 * @param {Object} height 窗口高度
	 * @param {Object} items 窗口内容
	 * @param {Object} buttons 窗口按钮
	 */
	function showCodeListWindow(id, title, width, height, items, html, buttons){
		var codeListWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			//html:html,
			buttons:buttons,
			modal:true,
			//animateTarget:"giftmanage_div",//动画展示
			layout:"fit",
			resizable:false
		});
		codeListWindow.show();
	}
	/**
	 * 导入数据标准
	 * @param {Object} windowId
	 * @param {Object} form
	 */
	function saveImportCodeData(windowId, form){
		Ext.MessageBox.show({
		    msg: '正在导入数据标准, 请稍侯...',
		    progressText: '正在上传数据标准',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				var result = Ext.decode(action.response.responseText);
				if(result && result.success){
					var msg = "数据标准值信息保存成功！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							codeListDataStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "数据标准值信息保存失败！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var result = Ext.decode(action.response.responseText);
				if(result && result.success){
					var msg = "数据标准值信息保存成功！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							codeListDataStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "数据标准值信息保存失败！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			}
		});
	}
	
	/**
	 * 保存数据标准
	 * @param {Object} url 提交的url
	 * @param {Object} id 数据标准唯一ID
	 * @param {Object} text 数据标准名称
	 */
	function saveCodeList(url, id, text){
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		Ext.Ajax.request({
			params:{codeId:id, codeName:text},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","数据标准信息已成功保存！");
					}
					codeListStore.reload();
					codeListDataStore.reload();
					codeListStoreClon.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","数据标准信息保存失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				try{
					var msg = Ext.util.JSON.decode(response.responseText);
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","数据标准信息保存失败！");
					}
				}catch(e){
					Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
				}
			}
		});
	}
	
	/**
	 * 保存数据标准值
	 * @param {Object} windowId
	 * @param {Object} form
	 */
	function saveCodeData(windowId, form){
		Ext.MessageBox.show({
			msg:"正在保存数据标准值信息，请稍候...",
			progressText:"正在保存数据标准值，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				var result = Ext.decode(action.response.responseText);
				if(result && result.success){
					var msg = "数据标准值信息保存成功！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							codeListDataStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "数据标准值信息保存失败！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var result = Ext.decode(action.response.responseText);
				if(result && result.success){
					var msg = "数据标准值信息保存成功！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg, function(btn, text) {
						if (btn == 'ok') {
							codeListDataStore.reload();
							Ext.getCmp(windowId).close();
						}
					});
				}else if(!result.success){
					var msg = "数据标准值信息保存失败！";
					if(result.msg){
						msg = result.msg;
					}
					Ext.Msg.alert('系统提示信息', msg);
				}
			}
		});
	}
	
	/**
	 * 删除数据标准值信息
	 * @param {Object} url
	 * @param {Object} id 数据标准值ID
	 */
	function deleteCodeDataList(url, id){
		Ext.MessageBox.show({
		    msg: '正在提交您的请求, 请稍侯...',
		    progressText: '正在提交您的请求',
		    width:300,
		    wait:true,
		    waitConfig: {interval:200},
		    icon:Ext.Msg.INFO
		});
		Ext.Ajax.request({
			params:{dataIds:id},
			timeout:60000,
			url:url,
			success:function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.success){
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","数据标准信息已成功删除！");
					}
					codeListDataStore.reload();
				}else{
					if(msg.msg){
						Ext.Msg.alert("系统提示",msg.msg);
					}else{
						Ext.Msg.alert("系统提示","数据标准信息删除失败！");
					}
				}
			},failure: function(response, options){
				Ext.Msg.hide();
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
				}else{
					Ext.Msg.alert("系统提示","数据标准信息删除失败！");
				}
			}
		});
	}
	/**
	 * 查询数据标准
	 * @param {} windowId
	 * @param {} form
	 */
	function queryCodeDataList(windowId, form){
		if(form.form.findField("dataKey") && form.form.findField("dataKey").getValue()){
			var dataKey = form.form.findField("dataKey").getValue();
			codeListDataStore.baseParams.dataKey = dataKey;
		}else{
			codeListDataStore.baseParams.dataKey = "";
		}
		if(form.form.findField("dataValue") && form.form.findField("dataValue").getValue()){
			var dataValue = form.form.findField("dataValue").getValue();
			codeListDataStore.baseParams.dataValue = dataValue;
		}else{
			codeListDataStore.baseParams.dataValue = "";
		}
		if(form.form.findField("codeId") && form.form.findField("codeId").getValue()){
			var codeId = form.form.findField("codeId").getValue();
			codeListDataStore.baseParams.codeId = codeId;
		}else{
			codeListDataStore.baseParams.codeId = "";
		}
		if(form.form.findField("parentDataKey") && form.form.findField("parentDataKey").getValue()){
			var parentDataKey = form.form.findField("parentDataKey").getValue();
			codeListDataStore.baseParams.parentDataKey = parentDataKey;
		}else{
			codeListDataStore.baseParams.parentDataKey = "";
		}
		if(form.form.findField("remark") && form.form.findField("remark").getValue()){
			var remark = form.form.findField("remark").getValue();
			codeListDataStore.baseParams.remark = remark;
		}else{
			codeListDataStore.baseParams.remark = "";
		}
		codeListDataStore.reload();
		//codeListDataStore.reload(codeListDataStore.lastOptions);
		Ext.getCmp(windowId).close();
	}
}

/**
 * @author cdai
 * 数据字典管理
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	codeListDataManage();
});