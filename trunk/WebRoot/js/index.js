function indexPage(){
    /**
     * 显示在菜单面板上面的部分,可以更改
     */
	var detailsPanel = {
		id: 'details-panel',
        title: '',
        region: 'north',
        height:182,
        bodyStyle: 'padding-bottom:0px;background:#eee;',
		autoScroll: true,
		html: '<p class="details-info" id="showCalendar">When you select a layout from the tree, additional details will display here.</p>'
    };
    /**
     * 菜单面板
     */
    var menuPanel = new Ext.Panel({
    	id: 'menu-panel',
        title: '菜单信息',
        region: 'center',
        border:false,
        bodyStyle: 'padding-bottom:0px;background:#eee;',
		autoScroll: true,
		layout:"accordion",
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
					//alert(rootMenu);
					menuPanel.body.dom.innerHTML = "";
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
			id:"userInfo",
			layout:"border",
			region:"west",
			collapsible:true,
			//split:true,
			margins:"0 0 0 5",
			width:216,
			//minSize:100,
			//maxSize:500,
			items:[detailsPanel,menuPanel]
		},{
			region:"center",
			html:"Hello world",
			margins:'0 5 0 5'
		},{
			region:"south",
			height:20,
			collapsible:true,
			html:"world",
			margins:'5 0 0 0'
		},{
			title:"HelloWorld",
			region:"east",
			width:230,
			collapsible:true,
			html:"world",
			margins:'0 5 0 0'
		}]
	});
	//loadMenuPanel();
}

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
		
		for(var i=0;i<menuSize;i++){
			var menu = rootMenu[i];
			//获取某菜单下的所有菜单,树形结构
			var tree = providetreePanel(menu.menuId,menu.menuName);
			menuPanel.add({
				title:menu.menuName,
				items:[tree]
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
});