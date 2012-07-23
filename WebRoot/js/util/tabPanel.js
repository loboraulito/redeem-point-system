/**
 * 初始化tab面板，并且默认一个页面
 * @param {} title
 * @param {} tabId
 * @param {} href
 */
function createTabPanel(title,tabId,href,closable){
	var tabPanel = new Ext.TabPanel({
		//width:Ext.get("mainpage").getWidth(),
		height:Ext.get("mainpage").getHeight(),
		renderTo:"mainpage", //中间显示区域
		id:"mainTabPanel",
		activeTab:0,
		autoScroll:false,
		border:false,
		enableTabScroll:true,
		bodyStyle:"overflow:hidden;",
		plugins:new Ext.ux.TabCloseMenu(),
		//autoHeight:true,
		/*
		autoLoad:{
			url:path+href,
			params:{start:0,limit:50},
			discardUrl: false, 
        	nocache: true, 
        	timeout: 30, 
        	scripts: true ,
			callback:function(scope,success,responses){
				if(success){
					try{
						var response = Ext.decode(responses.responseText);
						alert(response);
					}catch(e){
						alert(e);
					}
				}else{
					return;
				}
			}
		},
		*/
		items:[{
			title:title,
			id:tabId,
			closable:closable,
			html:"<iframe id='"+tabId+"_frame' name='"+tabId+"_frame' src='"+path+href+"' frameborder='0' height='100%' width='100%' style='overflow:hidden;' scrolling=\"no\"></iframe>"
		}]
	});
}
/**
 * 增加一个tab页
 * @param {} panel
 * @param {} panelId
 * @param {} title
 * @param {} href
 */
function addTabPanel(panel,panelId,title,href,closable){
	if(panel){
		var tab = {
			title:title,
			id:panelId,
			closable:closable,
			html:"<iframe id='"+panelId+"_frame' name='"+panelId+"_frame' src='"+path+href+"' frameborder='0' height='100%' width='100%' style='overflow:hidden;' scrolling=\"no\"></iframe>"
		};
		panel.add(tab);
		panel.setActiveTab(panelId);
	}
}
/**
 * 激活当前面板
 * @param {} panel
 * @param {} tabId
 * @param {} needRefresh 是否需要刷新
 */
function activeTabPanel(panel, tabId, needRefresh){
	var tab = Ext.getCmp(tabId);
	//tab.load(tab.initialConfig);
	//tab.getUpdater().refresh();
	panel.setActiveTab(tabId);
	if(needRefresh){
		window.frames[tabId+"_frame"].location.reload();
	}
}