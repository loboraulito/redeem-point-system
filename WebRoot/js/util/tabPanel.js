function createTabPanel(title,tabId,href){
	var tabPanel = new Ext.TabPanel({
		renderTo:"mainpage",
		id:"mainTabPanel",
		activeTab:0,
		autoScroll:true,
		autoHeight:true,
		/*
		autoLoad:{
			url:path+href,
			params:{userName:userName},
			discardUrl: false, 
        	nocache: true, 
        	timeout: 30, 
        	scripts: true ,
			callback:function(scope,success,responses){
				if(success){
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
				}else{
					return;
				}
			}
		},
		*/
		items:[{
			title:title,
			id:tabId,
			html:href
		}]
	});
}

function addTabPanel(panel,panelId,title){
	if(panel){
		var tab = {
			title:title,
			id:panelId,
			html:"abcd"
		};
		panel.add(tab);
		panel.setActiveTab(panelId);
	}
}

function activeTabPanel(panel,tabId){
	panel.setActiveTab(tabId);
}