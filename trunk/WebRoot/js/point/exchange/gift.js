/**
 * 礼品管理
 */
function giftManage(){
	/**
	 * giftReader - 礼品信息解析器
	 */
	var giftReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "giftList"
	},[
		{name:"giftId"},//唯一id
		{name:"giftCode"},//编码
		{name:"giftName"},//名称
		{name:"giftModel"},//型号
		{name:"giftComment"},//注释
		{name:"giftColor"},//颜色
		{name:"giftSize"},//尺寸
		{name:"giftUnit"},//单位
		{name:"giftType"},//类型
		{name:"giftImage"},//图片
		{name:"supplierId"},//供应商id
		{name:"stockNo"}//库存
	]);
	
	var proxyUrl = path+"/gift/giftList.action?method=giftList";
	/**
	 * giftStore:礼品数据仓库
	 */
	var giftStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:proxyUrl
		}),
		baseParams:{supplierId:"1"},
		reader:giftReader
	});
	
	/**
	 * giftSM:数据展现样式
	 */
	var giftSM = new Ext.grid.CheckboxSelectionModel();
	/**
	 * giftCM:数据列展示样式
	 */
	var giftCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),giftSM,{
		dataIndex:"giftId",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		dataIndex:"giftCode",
		hidden:true,
		hideable:false//不允许将隐藏的字段显示出来
	},{
		header:"礼品名称",
		dataIndex:"giftName",
		width:150
	},{
		header:"礼品型号",
		dataIndex:"giftModel",
		width:150
	},{
		header:"礼品颜色",
		dataIndex:"giftColor",
		sortable:true,
		width:80
	},{
		header:"礼品尺寸",
		dataIndex:"giftSize",
		width:80
	},{
		header:"计量单位",
		dataIndex:"giftUnit",
		width:80
	},{
		header:"礼品类型",
		dataIndex:"giftType",
		width:80
	},{
		header:"供应商",
		dataIndex:"supplierId",
		width:80
	},{
		header:"实物图片",
		dataIndex:"giftImage"
		//hidden:true,
		//hideable:false
	},{
		header:"库存数量",
		dataIndex:"stockNo",
		width:80
	},{
		header:"备注",
		dataIndex:"giftComment",
		width:180
	}]);
	
	/**
	 * giftGrid: 礼品展示列表
	 */
	var giftGrid = new Ext.grid.GridPanel({
		collapsible:true,//是否可以展开
		animCollapse:true,//展开时是否有动画效果
		autoScroll:true,
		width:Ext.get("giftmanage_div").getWidth(),
		height:Ext.get("giftmanage_div").getHeight()-20,
		loadMask:true,//载入遮罩动画（默认）
		frame:true,
		autoShow:true,		
		store:giftStore,
		renderTo:"giftmanage_div",
		cm:giftCM,
		sm:giftSM,
		viewConfig:{forceFit:true},//若父容器的layout为fit，那么强制本grid充满该父容器
		split: true,
		bbar:new Ext.PagingToolbar({
			pageSize:50,//每页显示数
			store:giftStore,
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
	 *  礼品信息加载的参数列表
	 */
	var storeParams = {};
	storeParams.start = 0;
	storeParams.limit = 50;
	//storeParams.supplierId = "1";
	/**
	 * 执行权限按钮加载, 并且加载列表数据, 显示权限按钮
	 * see buttonRight.js
	 */
	loadButtonRight(buttonRightStore, giftStore, giftGrid, "giftmanage_div", storeParams);
	/**
	 * 添加礼品信息
	 * @param {Object} url
	 */
	this.addGift = function(url){
		//alert($("#giftmanage_div").html());
		var giftForm = getGiftForm(url, false, false);
		var button = [{
			text:"保存",
			handler:function(){
				if(giftForm.form.isValid()){
					saveGift("addGiftWindow", giftForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var giftWin = Ext.getCmp("addGiftWindow");
				if(giftWin){
					giftWin.close();
				}
			}
		}];
		showGiftWindow("addGiftWindow", "添加礼品信息", 500, 400, giftForm, button);
		var imgsize = Ext.getCmp('currentGiftImage').getSize();
		var s = {};
		if(imgsize.width > imgsize.height){
			s.property = "width";
			s.width = imgsize.width;
		}else{
			s.property = "height";
			s.width = imgsize.height;
		}
		Ext.getCmp("currentGiftImage").body.update("当前礼品图片:<br><img src='"+path+"/images/nopic.jpg' title='点击查看原图' alt='点击查看原图' onclick='showLargeImage(this)' class='cursor-magplus' style='"+s.property+":"+s.width+"'></img>");
	}
	/**
	 * 编辑礼品信息
	 * @param {Object} url
	 */
	this.editGift = function(url){
		var gridSelectionModel = giftGrid.getSelectionModel();
		var gridSelection = gridSelectionModel.getSelections();
		if(gridSelection.length != 1){
			Ext.MessageBox.alert('提示','请选择一条礼品信息！');
		    return false;
		}
		var giftForm = getGiftForm(url, false, true);
		var button = [{
			text:"保存",
			handler:function(){
				if(giftForm.form.isValid()){
					saveGift("editGiftWindow", giftForm);
				}
			}
		},{
			text:"关闭窗口",
			handler:function(){
				var giftWin = Ext.getCmp("editGiftWindow");
				if(giftWin){
					giftWin.close();
				}
			}
		}];
		showGiftWindow("editGiftWindow", "修改礼品信息", 500, 400, giftForm, button);
		giftForm.getForm().loadRecord(gridSelection[0]);
		//giftForm.form.findField("giftImage").setValue("");
		Ext.getCmp("giftImage").setValue("");
		//var img = document.createElement("img");
		var img = new Image();
		img.src = path+gridSelection[0].get("giftImage");
		//进行图片预加载
		imgReady(img.src,function(){
			//尺寸就绪
			if(this.width == 0 && this.height == 0){
				this.src = path + "/images/nopic.jpg";
			}
		},function(){
			//图片加载就绪
			Ext.getCmp("currentGiftImage").body.update("当前礼品图片:<br><img src='"+this.src+"' title='点击查看原图' alt='点击查看原图' onclick='showLargeImage(this)' class='cursor-magplus' style='width:"+Ext.getCmp('currentGiftImage').getSize().width+"'></img>");
		},function(){
			//图片加载错误
			this.src = path + "/images/nopic.jpg";
			Ext.getCmp("currentGiftImage").body.update("当前礼品图片:<br><img src='"+this.src+"' title='点击查看原图' alt='点击查看原图' onclick='showLargeImage(this)' class='cursor-magplus' style='width:"+Ext.getCmp('currentGiftImage').getSize().width+"'></img>");
		});
		
		//Ext.getCmp("currentGiftImage").body.update("当前礼品图片:<br><img src='"+img.src+"' title='点击查看原图' alt='点击查看原图' onclick='showLargeImage(this)' class='cursor-magplus' style='width:"+Ext.getCmp('currentGiftImage').getSize().width+"'></img>");
		//$("ul.thumb li").Zoomer({speedView:200,speedRemove:400,altAnim:true,speedTitle:400,debug:false});
		img = null;
	}
	/**
	 *  删除礼品信息
	 * @param {Object} url
	 */
	this.deleteGift = function(url){
		
	}
	
	/**
	 * 展示大图
	 * @param {Object} obj 图片对象
	 */
	this.showLargeImage = function(obj){
		var img = document.createElement("img");
		img.src = obj.src;
		/*
		var scale = obj.width/obj.height;
		var w = obj.width > img.width ? obj.width : img.width;
		w = w > 400 ? w : 400;
		w = w > 1000 ? 1000 : w;
		var h = obj.height > img.height ? obj.height : img.height;
		h = h > 300 ? h : 300;
		h = h > 600 ? 600 : h;
		h = w / scale;
		img = null;
		if(h > w){
			var temp = w;
			w = h;
			h = w;
			temp = null;
		}
		*/
		if(img.width == 0 && img.height == 0){
			img.src = path + "/images/nopic.jpg";
		}
		var w = img.width, h=img.height;
		if(img.width <200){
			w = 200;
		}
		if(img.height <200){
			h = 200;
		}
		if(img.width >1000){
			w = 1000;
		}
		if(img.height >800){
			h = 800;
		}
		
		var html = "<img src='"+img.src+"' class='cursor-magminus' onclick='closeWindow(\"showLargImageWindow\")' title='点击关闭窗口' alt='点击关闭窗口'></img>";
		var items = [{
			layout:"fit",
			frame: true,
			html:html
		}];
		showGiftWindow("showLargImageWindow","查看大图",w+30, h+40 ,items);
		img = null;
	}
	/**
	 * 关闭指定窗口
	 * @param {Object} windowId 窗口ID
	 */
	this.closeWindow = function(windowId){
		var win = Ext.getCmp(windowId);
		if(win){
			win.close();
		}
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
	function showGiftWindow(id, title, width, height, items, buttons){
		var giftWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			buttons:buttons,
			modal:true,
			//animateTarget:"giftmanage_div",//动画展示
			layout:"fit",
			resizable:false
		});
		giftWindow.show();
	}
	
	/**
	 * 礼品表单
	 * @param {Object} url 表单提交的url
	 * @param {Object} isNull 是否允许为空, true - 允许空
	 * @param {Object} readOnly 是否只读, true - 只读
	 */
	function getGiftForm(url,isNull,readOnly){
		var giftForm = new Ext.form.FormPanel({
			fileUpload:true,
			frame: true,
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			url:url,
			method:"post",
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftName",
						anchor:"90%",
						fieldLabel:"礼品名称",
						maxLength:250,
						readOnly:readOnly,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftModel",
						anchor:"90%",
						fieldLabel:"礼品型号",
						maxLength:50,
						readOnly:readOnly
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftColor",
						anchor:"90%",
						fieldLabel:"礼品颜色",
						maxLength:50,
						readOnly:readOnly
					}]
				},{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftSize",
						anchor:"90%",
						fieldLabel:"礼品尺寸",
						maxLength:50,
						readOnly:readOnly
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftUnit",
						anchor:"90%",
						fieldLabel:"计量单位",
						maxLength:50,
						readOnly:readOnly
					}]
				},{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"giftType",
						anchor:"90%",
						fieldLabel:"礼品类型",
						maxLength:50,
						readOnly:readOnly
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"supplierId",
						anchor:"90%",
						fieldLabel:"供应商",
						maxLength:50,
						readOnly:readOnly,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:".5",
					height:50,
					items:[{
						xtype: 'textfield',
						name:"stockNo",
						anchor:"90%",
						fieldLabel:"库存数量",
						maxLength:50,
						readOnly:readOnly
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:".7",
					//height:70,
					items:[{
						layout:"form",
						height:70,
						items:[{
							xtype: 'textarea',
							name:"giftComment",
							anchor:"90%",
							fieldLabel:"备注",
							maxLength:500,
							readOnly:readOnly
						}]
					},{
						xtype:"hidden",
						name:"giftId"
					},{
						xtype:"hidden",
						name:"giftCode"
					},{
						layout:"form",
						height:50,
						items:[{
							xtype: 'fileuploadfield',
							name:"giftImage",
							id:"giftImage",
							buttonText:"请选择...",
							anchor:"90%",
							fieldLabel:"实物图片",
							readOnly:readOnly
						}]
					}]
				},{
					layout:"form",
					columnWidth:".3",
					//height:50,
					//rowspan:2,
					labelAlign:"top",
					items:[{
						id:"currentGiftImage",
						name:"currentGiftImage",
						html:"<img></img>",
						anchor:"90%",
						fieldLabel:"当前礼品图片"
					}]
					
				}]
			}]
		});
		return giftForm;
	}
	/**
	 * 提交礼品信息
	 * @param {Object} windowId
	 * @param {Object} form
	 */
	function saveGift(windowId, form){
		Ext.MessageBox.show({
			msg:"正在保存礼品信息，请稍候...",
			progressText:"正在保存礼品信息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				Ext.Msg.alert('系统提示信息', '礼品信息保存成功!', function(btn) {
					if (btn == 'ok') {
						//var msg = Ext.decode(action.response.responseText);
						giftStore.reload();
						Ext.getCmp(windowId).close();
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var msg = Ext.decode(action.response.responseText);
				if(msg && msg.msg){
					Ext.Msg.alert('系统提示信息', msg.msg);
				}else{
					Ext.Msg.alert('系统提示信息', "礼品信息保存过程中出现异常!");
				}
			}
		});
	}
}

/**
 * 礼品管理
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	giftManage();
});