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
}
/**
 * JS trim
 * 
 * @return {}
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
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
 * 创建/增加/激活tab面板
 * @param {} title 面板标题
 * @param {} tabId 面板唯一ID
 * @param {} tabHref 面板的url
 * @param {} closable 是否可关闭的面板
 */
function createMainTabPanel(title, tabId, tabHref, closable) {
	var tabPanel = Ext.getCmp("mainTabPanel");
	if (!tabPanel) {
		createTabPanel(title, tabId + "_tab", tabHref, closable);
	} else {
		var tabPanelPage = Ext.getCmp(tabId + "_tab");
		if (!tabPanelPage) {
			addTabPanel(tabPanel, tabId + "_tab", title, tabHref, closable);
		} else {
			activeTabPanel(tabPanel, tabId + "_tab");
		}
	}
}
