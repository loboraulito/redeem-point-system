/**
 * 换肤功能
 */
function changeXtheme() {
	var cookieArr = document.cookie.split(";");
	var cssName = "";
	for (var i = 0; i < cookieArr.length; i++) {
		var arr = cookieArr[i].split("=");
		if (arr[0] == "css") {
			cssName = arr[1];
			break;
		}
	}
	if (cssName) {
		var themeCombo = Ext.getCmp("xthemebox");
		if (themeCombo) {
			themeCombo.setValue(cssName);
		}
		Ext.util.CSS.swapStyleSheet("theme", path
						+ "/js/ext-2.2.1/resources/css/" + cssName + ".css");
	}

}
/**
 * JS replaceAll
 * 
 * @param {}
 *            s1
 * @param {}
 *            s2
 * @return {}
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};
/**
 * JS trim
 * 
 * @return {}
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 获取当前页面的菜单ID
 * 
 * @return {}
 */
function getCurrentMenuId() {
	var parentExt = parent.Ext;
	if (parentExt) {
		var tabPanel = parentExt.getCmp("mainTabPanel");
		if (tabPanel) {
			var activePanel = tabPanel.getActiveTab();
			if (activePanel) {
				var tabId = activePanel.id;
				if (tabId) {
					return tabId.split("_")[0];
				}
			}
		}
	}
}
/**
 * 关闭当前页面
 */
function closeCurrentMenuTab(){
	//在父页面中调用时，直接使用Ext。在子页面中调用时，需要使用parent.Ext
	var parentExt = parent.Ext || Ext;
	if (parentExt) {
		var tabPanel = parentExt.getCmp("mainTabPanel");
		if (tabPanel) {
			var activePanel = tabPanel.getActiveTab();
			if (activePanel && activePanel.closable) {
				tabPanel.remove(activePanel);
			}
		}
	}
}
/**
 * 处理http的异常状态标识
 * @param httpStatusCode
 */
function httpStatusCodeHandler(httpStatusCode){
	if(httpStatusCode == "404"){
		Ext.Msg.alert('错误提示',"当前页面不可用，请检查您的URL地址。页面即将关闭！", function(btn){
			//关闭当前页面
			closeCurrentMenuTab();
		});
		return false;
	}
	if(httpStatusCode == "403"){
		Ext.Msg.alert('错误提示',"您无权访问此页面，当前页面即将关闭！", function(btn){
			//关闭当前页面
			closeCurrentMenuTab();
		});
		return false;
	}
	return true;
}
/**
 * 创建/增加/激活tab面板
 * @param {} title 面板标题
 * @param {} tabId 面板唯一ID
 * @param {} tabHref 面板的url
 * @param {} closable 是否可关闭的面板
 * @param {} needRefresh 是否需要刷新
 */
function createMainTabPanel(title, tabId, tabHref, closable, needRefresh) {
	var tabPanel = Ext.getCmp("mainTabPanel");
	if (!tabPanel) {
		createTabPanel(title, tabId + "_tab", tabHref, closable);
	} else {
		var tabPanelPage = Ext.getCmp(tabId + "_tab");
		if (!tabPanelPage) {
			addTabPanel(tabPanel, tabId + "_tab", title, tabHref, closable);
		} else {
			activeTabPanel(tabPanel, tabId + "_tab", needRefresh);
		}
	}
}
/**
 * 跳转到指定url的tab页面, tabHref与menuId二选一
 * @param {} tabHref 要跳转页面的相对url(不接path变量,菜单URL不是按钮URL)
 * @param {} menuId 要跳转页面的菜单ID(菜单ID不是按钮ID)
 * @param {} needRefresh(true|false) 是否要求刷新页面(这个针对已经打开的tab而言)
 */
function goToTabPanel(tabHref, menuId, needRefresh){
	Ext.Msg.alert("系统提示","页面跳转中，请稍候...");
	Ext.Ajax.request({
		params:{menuPath:tabHref, menuId:menuId},
		timeout:60000,
		url:path + "/menu/findMenuId.action?method=findMenuId",
		success:function(response,options){
			var msg = Ext.util.JSON.decode(response.responseText);
			if(msg && msg.success){
				Ext.Msg.hide();
				var title = msg.menuText;
				var tabId = msg.menuId;
				var tabUrl = msg.menuUrl;
				if(!tabUrl){
					createMainTabPanel(title, tabId, tabHref, true, needRefresh);
				}else{
					createMainTabPanel(title, tabId, tabUrl, true, needRefresh);
				}
			}else if(msg && !msg.success){
				Ext.Msg.hide();
				Ext.Msg.alert("提示信息","URL信息无效，请检查数据库是否存在该信息！");
			}
		},failure:function(response,options){
			Ext.Msg.hide();
			Ext.Msg.alert("提示信息","系统错误，请联系系统管理员！");
			return;
		}
	});
}
/**
 * 设置某控件为灰, 无法操作
 * @param {} cmpId
 */
function markComponent(cmpId){
	var c = Ext.getCmp(cmpId);
	if(c){
		c.getEl().parent().mask();
	}
}
/**
 * 解除某控件的灰, 可以操作
 * @param {} cmpId
 */
function unMarkComponent(cmpId){
	var c = Ext.getCmp(cmpId);
	if(c){
		c.getEl().parent().unmask();
	}
}

/**
 * 格式化日期(有异常处理)
 * value:一个日期
 * preformat:该日期的格式，如时间为：1999-02-05 19:05:30，其格式应该是：Y-m-d H:i:s
 * 注意该格式一定要匹配，否则异常
 * format:要格式化的日期格式，默认为Y-m-d
 */
function dateFormat(value,preformat,format){
	if(!value){
		return "";
	}
	if(!preformat){
		preformat = "Y-m-d H:i:s";
	}
	if(!format){
		format = "Y-m-d";
	}
	if(value && value!=""){
		try{
			var dt = Date.parseDate(value,preformat);
			if(dt){
				dt = dt.format(format);
				return dt;
			}else{
				return value;
			}
		}catch(e){
			return value;
		}
	}else{
		return value;
	}
}
/**
 * 将日期转换为指定格式的字符串
 * @param value
 * @param format
 * @returns
 */
function dateFormat2(value, format){
	if(!value){
		return "";
	}
	if(!format){
		format = "Y-m-d";
	}
	try{
		var date = new Date(value);
		return date.format(format);
	}catch(e){
		showSystemMsg("系统提示", "您的日期不合法！");
		return;
	}
}

/**
 * 查询数据标准的下拉框
 */
function getCodeListCombo(){
	var codeStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:path+"/common/codeListCombo.action?method=codeListCombo"
		}),
		reader:new Ext.data.JsonReader({
			totalProperty:"totalCount",
			root:"codeList"
		},[
			{name:"dataKey"},
			{name:"dataValue"}
		])
	});
	return codeStore;
}
/**
 * 查询地址
 * @param {} dataKey
 * @param {} fn 回调函数
 */
function getAddressFromCodeList(dataKey, fn){
	if(dataKey == null || dataKey == "" || dataKey.length != 6){
		return;
	}
	var url = path + "/common/codeListAddress.action?method=getAddressFromCodeData";
	Ext.Ajax.request({
		params:{codeId:"8ac388c529a607730129a608c52f0004", datakey:dataKey},
		timeout:60000,
		url:url,
		success:function(response, options){
			var msg = Ext.util.JSON.decode(response.responseText);
			if(msg.success){
				if(typeof fn == "function"){
					if(msg.codeList && msg.codeList.length > 0){
						fn(msg.codeList[0]);
					}
				}
			}else{
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
				}else{
					Ext.Msg.alert("系统提示","系统错误，请联系管理员！");
				}
			}
		},failure: function(response, options){
			try{
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
				}else{
					Ext.Msg.alert("系统提示","系统错误，请联系管理员！");
				}
			}catch(e){
				Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
			}
		}
	});
}

/**
 * 根据数据标准的具体值，找到其对应的数据标准值。
 * 使用时，若codeid与codename互换的话，则找其对应的值
 * value:查找的值
 * store：数据标准数据集
 * codeid：数据标准id
 * codename：数据标准id对应的值
 */
function getCodeNameFromStore(value,store,codeid,codename){
	if(!codeid){
		codeid = "codeId";
	}
	if(!codename){
		codename = "codeName";
	}
	try{
		var index = store.find(codeid,value);
		if(index>=0){
			var record = store.getAt(index);
			if(record){
				var fieldvalue = record.get(codename);
				if(fieldvalue){
					return fieldvalue;
				}
			}
		}
	}catch(e){
		return value;
	}
	return value;
}
/**
 * 显示系统消息
 * @param title
 * @param msg
 * @param fn
 */
function showSystemMsg(title, msg, fn){
	Ext.Msg.show({
		title: title,
		msg:msg,
		buttons:Ext.Msg.OK,
		icon:Ext.Msg.INFO,
		fn:fn,
		width:300
	});
}

/**
 * 显示警告信息
 * @param title
 * @param msg
 * @param icon
 * @param width
 */
function showAlertMessage(title, msg, icon, width){
	if(!width){
		width = 300;
	}
	if(!icon){
		icon = Ext.MessageBox.INFO;
	}
	Ext.Msg.show({
		buttons : Ext.MessageBox.OK,
		msg:msg,
		title:title,
		icon : icon,
		width:width
	});
}


/**
 * 查看我的邀请信息
 */
function viewInvitation(userName, currentMenuId){
	var url = path + "/invitation/invitationList.action?method=invitationList";
	Ext.Ajax.request({
		params:{userId:userName, menuId:currentMenuId, status:"1"},
		timeout:60000,
		url:url,
		success:function(response, options){
			var msg = Ext.util.JSON.decode(response.responseText);
			if(msg.success){
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
					family_manage();
				}else{
					if(msg.totalCount > 0){
						Ext.Msg.confirm("系统提示","您有未处理的系统邀请信息，现在处理？",function(btn){
							if(btn == "yes" || btn == "ok"){
								parent.fromMenuId = currentMenuId;
								parent.goToTabPanel("/invitation/invitation.action?method=begin", null, true);
							}else{
								//family_manage();
							}
						});
					}else{
						//family_manage();
					}
				}
			}else{
				//family_manage();
			}
		},failure: function(response, options){
			try{
				var msg = Ext.util.JSON.decode(response.responseText);
				if(msg.msg){
					Ext.Msg.alert("系统提示",msg.msg);
				}else{
					Ext.Msg.alert("系统提示","系统错误，请联系管理员！");
				}
			}catch(e){
				Ext.Msg.alert("系统提示","系统错误！错误代码：" + e);
			}
		}
	});
}
/**
 * 将15位的身份证转为18位
 * @param {} idcard
 */
function updateIDCard(idcard){
	if(!idcard || idcard.length != 15){
		return;
	}
	var zone = idcard.substring(0,6);
	var year = "19" + idcard.substring(6,8);
	var mdo = idcard.substring(8,15);
	var tempId = zone + year + mdo;
	/**
	 * 获取最后一位校验码
	 * @param {} id
	 * @return {}
	 */
	function getVerifyCode(id){
		var getNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
		getNum = getNum % 11;
		switch (getNum) {
		case 0 :
		  lastNumber="1";
		  break;
		case 1 :
		  lastNumber="0";
		  break;
		case 2 :
		  lastNumber="X";
		  break;
		case 3 :
		  lastNumber="9";
		  break;
		case 4 :
		  lastNumber="8";
		  break;
		case 5 :
		  lastNumber="7";
		  break;
		case 6 :
		  lastNumber="6";
		  break;
		case 7 :
		  lastNumber="5";
		  break;
		case 8 :
		  lastNumber="4";
		  break;
		case 9 :
		  lastNumber="3";
		  break;
		case 10 :
		  lastNumber="2";
		  break;
		}
		return lastNumber;
	}
	var card = tempId + getVerifyCode(tempId);
	return card;
}
/**
 * 通用窗口
 * @param id 唯一标识
 * @param title 窗口标题
 * @param width 宽度
 * @param height 高度
 * @param items 内容
 * @param html html标记<无用>
 * @param buttons 按钮
 */
function showAllWindow(id, title, width, height, items, html, buttons, modal){
	if(typeof(modal) == "undefined"){
		modal = true;
	}
	var componentWindow = new Ext.Window({
		id:id,
		title:title,
		width:width,
		height:height,
		items:items,
		buttons:buttons,
		//html:html,
		modal:modal,
		layout:"fit",
		resizable:false
	});
	componentWindow.show();
}
/**
 * 遮罩某个控件
 * @param cmp 控件ID，或者是控件
 */
function markCmp(cmp){
	var obj;
	if(typeof(cmp) == "string"){
		obj = Ext.getCmp(cmp);
	}else {
		obj = cmp;
	}
	var pel = obj.getEl().parent();
	pel.mask();
}
/**
 * 解除控件遮罩效果
 * @param cmp 控件id或者是控件
 */
function unMarkCmp(cmp){
	var obj;
	if(typeof(cmp) == "string"){
		obj = Ext.getCmp(cmp);
	}else {
		obj = cmp;
	}
	var pel = obj.getEl().parent();
	pel.unmask();
}

/**
 * 四舍五入并且精确到小数后几位
 * v：要四舍五入的数字
 * e：小数点位数
 */
function getRound(v,e){
	if(isNaN(v)){
		return v;
	}
	if(!e){
		e = 2;
	}
	var   t=1;   
    for(;e>0;t*=10,e--);   
    for(;e<0;t/=10,e++);   
    return   Math.round(v*t)/t;
}

/**
 * 激活DWR的反转功能
 * @param {} bool
 */
function enableDWRAjax(bool){
	//服务器停止时的错误处理
	dwr.engine.setErrorHandler(function(){});
	//重点关于解决页面每刷新一次会多创建一个新的ScriptSession的解决方法
	//但是似乎无用
	dwr.engine.setNotifyServerOnPageUnload(true);
	// 激活dwr反转 重要
	dwr.engine.setActiveReverseAjax(bool);
}
//enableDWRAjax(true);

/**
 * 禁用网页上下文(鼠标右键)，仅仅用于发布正式版本
 */
Ext.getBody().on('contextmenu', function(e) {
	//e.stopEvent();
});

/**
 * 设置只读
 * @param field 需要设置成只读的控件
 * @param value 是否只读。true：只读，false：可写
 */
function setReadOnly(field,value) {
	if(field && field.el) {
		if(value==false) {
			field.el.dom.readOnly = false;
		}
		else {
			field.el.dom.readOnly = true;
		}
	}
	else if(field) {
		if(value==false) {
			field.readOnly = false;
		}
		else {
			field.readOnly = true;
		}
	}
}