function createTabPanel(title,tabId,href){
	var tabPanel = new Ext.TabPanel({
		renderTo:"mainpage", //中间显示区域
		id:"mainTabPanel",
		activeTab:0,
		autoScroll:true,
		autoHeight:true,
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
			html:"<iframe id='"+tabId+"_frame' name='"+tabId+"_frame' src='"+path+href+"' frameborder='0' height='100%' width='100%' style='overflow:hidden;'></iframe>"
		}]
	});
}

function addTabPanel(panel,panelId,title,href){
	if(panel){
		var tab = {
			title:title,
			id:panelId,
			html:"<iframe id='"+panelId+"_frame' name='"+panelId+"_frame' src='"+path+href+"' frameborder='0' height='100%' width='100%' style='overflow:hidden;'></iframe>"
		};
		panel.add(tab);
		panel.setActiveTab(panelId);
	}
}

function activeTabPanel(panel,tabId){
	panel.setActiveTab(tabId);
}