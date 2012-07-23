function indexPage(){
    /**
     * 显示在菜单面板上面的部分,可以更改
     */
	var detailsPanel = {
		id: 'details-panel',
        title: '',
        region: 'north',
        //height:182,
        height:212,
        bodyStyle: 'padding-bottom:0px;background:#eee;',
		autoScroll: true,
		html: '<div class="details-info" id="showCalendar"></div><div><table><tr><td>更改页面风格：</td><td><div id="changeSkin"></div></td></tr></table></div>'
    };
    /**
     * 菜单面板
     */
    var menuPanel = new Ext.Panel({
    	id: 'menu-panel',
        title: '菜单信息',
        region: 'center',
        //height:212,
        border:false,
        //autoScroll:true,
        bodyStyle: 'padding-bottom:0px;background:#eee;overflow-x:hidden;overflow-y:hidden;',
		//autoScroll: true,
		layout:"accordion",
		tbar:[{
			text:"刷新系统菜单",
			iconCls:"table_refresh",
			tooltip:"刷新系统菜单",
			handler:function(){
				menuPanel.removeAll();
				loadMenuPanel(userName);
				/*
				menuPanel.getUpdater().refresh(function(oElement, bSuccess){
					try{
						var response = Ext.decode(oElement.dom.lastChild.data);
						var rootMenu = response.rootMenu;
						var menuSize = response.menuSize;
						menuPanel.body.dom.innerHTML = "";
						//menuPanel.body.update("");
						showRootMenu(rootMenu,menuSize);
					}catch(e){
						//alert(e);
					}
				});
				*/
			}
		}],
		layoutConfig:{
            animate:true
        },
        autoLoad:{
        	url:path+"/menu/rootModule.action?method=showRootMenu",
			params:{userName:userName},
			discardUrl: false, 
        	nocache: true, 
        	timeout: 30, 
        	scripts: true ,
			callback:function(scope,success,responses){
				try{
					var response = Ext.decode(responses.responseText);
					var rootMenu = response.rootMenu;
					var menuSize = response.menuSize;
					//menuPanel.body.dom.innerHTML = "";
					menuPanel.body.update("");
					showRootMenu(rootMenu,menuSize);
				}catch(e){
					//alert(e);
				}
			}
        }
    });
    
    /**
     * 主页面
     */
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			//title:"hi",
			region:"north",
			xtype:"box",
			height:35,
			applyTo:"header",
			margins:'0 0 10 0'
			//html:"aaaa"
		},{
			title:"欢迎您    "+userName,
			id:"userInfo_panel",
			layout:"border",
			region:"west",
			collapsible:true,
			//titleCollapse:true,//仅点击"<<"箭头才能伸缩
			collapsed:loginRoleName == "2" ? true : false,
			animCollapse :true,//动画
			//split:true,
			margins:"0 0 0 5",
			width:216,
			//minSize:100,
			//maxSize:500,
			items:[detailsPanel,menuPanel]
		},{
			region:"center",
			html:"<div id='mainpage' style='height: 100%;width: 100%;overflow:hidden;'></div>", //让中间的tabpanel显示区域
			deferredRender:false,
			id:"mainpagepanel",
			margins:'0 5 0 5'
				/*
			autoLoad:{
				callback:function(scope,success,responses){
					Ext.getCmp("mainpagepanel").body.update("<div id='mainpage' style='height: 100%;width: 100%;overflow:hidden;'></div>");
					//Ext.getCmp("mainpagepanel").body.dom.innerHTML = "<div id='mainpage' style='height: 100%;width: 100%;overflow:hidden;'></div>";
					//createMainTabPanel("首&nbsp;&nbsp;&nbsp;&nbsp;页", "mainPagePanel", "/jsp/client/index.jsp", false);
					createMainTabPanel("首&nbsp;&nbsp;&nbsp;&nbsp;页", "mainPagePanel", "/jsp/main.jsp", false);
				}
			}
			*/
		},{
			region:"south",
			height:20,
			collapsible:true,
			html:"world",
			margins:'5 0 0 0'
		}
		/*
		,{
			title:"HelloWorld",
			region:"east",
			width:230,
			collapsible:true,
			html:"world",
			margins:'0 5 0 0'
		}*/
		]
	});
	
	//loadMenuPanel();
	var themeCombo = new Ext.form.ComboBox({
		id:"xthemebox",
		renderTo:"changeSkin",
        store: new Ext.data.SimpleStore({
			fields:["skinname","skin"],
			data:[["系统默认","ext-all"],["黑色","xtheme-black"],["深绿色","xtheme-calista"],["深灰色","xtheme-darkgray"],["银白色","xtheme-gray"],["浅绿色","xtheme-green"],["靛青色","xtheme-indigo"],["紫深色","xtheme-midnight"],["橄榄色","xtheme-olive"],["苹果红","xtheme-pink"],["紫色","xtheme-purple"],["深蓝色","xtheme-slate"],["灰黑色","xtheme-slickness"]]
		}),
        displayField:'skinname',
        valueField:"skin",
        typeAhead: true,
        mode: 'local',
        editable:false,
        triggerAction: 'all',
        emptyText:'更换样式',
        selectOnFocus:true,
        value:"ext-all",
        width:100,
        listeners:{
        	"select":function(combo,record,index){
        		var skin = combo.getValue();
        		if(skin && skin!=""){
        			Ext.util.CSS.swapStyleSheet("theme", path+"/js/ext-2.2.1/resources/css/"+skin+".css");
        		}else{
        			Ext.util.CSS.swapStyleSheet("theme", "");
        		}
        		var date = new Date();
        		date.setDate(date.getTime() + 30*24*3600*1000);
        		document.cookie = "css="+skin+";expires="+date.toGMTString();
        		//调用子窗口中的方法，以便让子窗口随时变化
        		for(var i=0;i<window.frames.length;i++){
        			window.frames[i].window.changeXtheme();
        		}
        	}
        }
	});
	/**
	 * 设置菜单栏高度
	 */
	function setMenuPanelHight(){
		//alert(document.body.clientHeight);
		//alert(document.body.scrollHeight);
		//alert(Ext.getCmp("userInfo_panel").getInnerHeight());
		menuPanel.setHeight(Ext.getCmp("userInfo_panel").getInnerHeight() - detailsPanel.height - 2);
	}
	
	createMainTabPanel("首&nbsp;&nbsp;&nbsp;&nbsp;页", "mainPagePanel", "/jsp/main.jsp", false);
	setMenuPanelHight();
}
/**
 * 读取某用户有权限访问的菜单系统
 * @param user_name
 */
function loadMenuPanel(user_name){
	var menuPanel = Ext.getCmp("menu-panel");
	menuPanel.load({
		url:path+"/menu/rootModule.action?method=showRootMenu",
		params:{userName:userName},
		callback:function(scope,success,responses){
			try{
				var response = Ext.decode(responses.responseText);
				var rootMenu = response.rootMenu;
				var menuSize = response.menuSize;
				//alert(rootMenu);
				menuPanel.body.dom.innerHTML = "";
				showRootMenu(rootMenu,menuSize);
			}catch(e){
				//alert(e);
			}
		},
		discardUrl: false,
    	nocache: true,
    	discardUrl: false,
    	text: "Loading...",
		timeout:60000,
		scripts:true
	});
}

/**
 * 当用户登录成功之后,并且对某菜单拥有访问权限,加入并且显示在菜单树,以accordion方式显示
 * @param {} rootMenu 要显示的菜单
 * @param {} menuSize 菜单的数量
 */
function showRootMenu(rootMenu, menuSize){
	if(rootMenu){
		var menuPanel = Ext.getCmp("menu-panel");
		var menuHeight = menuPanel.getInnerHeight();
		for(var i=0;i<menuSize;i++){
			var menu = rootMenu[i];
			//获取某菜单下的所有菜单,树形结构
			var tree = providetreePanel(menu.menuId,menu.menuName);
			
			//174~198~222~246~270
			var height = menuHeight - 28;
			
			if(i != 0){
				height -= 28;
			}
			menuPanel.add({
				title:menu.menuName,
				items:[{
					layout:"fit",
					//autoScroll:true,
					bodyStyle: 'overflow-x:hidden;overflow-y:hidden;',
					height: height - (16.35 * menuSize) - 4 + (30 - 7.5 * menuSize),
					items:tree,
					width:212,
					tbar:[{
						id:menu.menuId + "_menutbar",
						text:"刷新菜单",
						tooltip:"刷新["+menu.menuName+"]菜单",
						iconCls:"table_refresh",
						handler:function(e){
							var rootId = this.getId().split("_")[0];
							var t = Ext.getCmp(rootId+"_tree");
							var n = t.getRootNode();
							n.reload();
						}
					}]
				}]
			});
		}
		menuPanel.doLayout();
	}
}

/**
 * 当用户点击登录系统按钮,并且点击确定时,调用此方法清空菜单信息
 */
function resetMenu(){
	var menuPanel = Ext.getCmp("menu-panel");
	var items = menuPanel.items;
	if(items.length<1){
		return;
	}
	menuPanel.removeAll();
	menuPanel.doLayout();
}
/**
 * 关闭所有标签
 */
function closeAllTab(){
	var tab = Ext.getCmp("mainTabPanel");
	if(tab){
		tab.items.each(function(item){
            if(item.closable){
                tab.remove(item);
            }
        });
	}
}

/**
 * 显示日历
 * @param {} calendarId : 日历显示的目标div
 */
function showCalendar(calendarId){
	var cr = new Calendar(calendarId,"0",2005,2099);
	cr.show({});
}
/**
 * 程序入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	indexPage();
	showCalendar("showCalendar");
	/*
	if(loginRoleName == "2"){
		var w = Ext.getCmp("userInfo_panel");
		w.collapsed ? w.expand() : w.collapse();
	}
	*/
});