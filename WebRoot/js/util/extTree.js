/**
 * 显示树形菜单
 * @param {} mainMenuId 树形菜单的根节点ID
 * @param {} rootName 树形菜单的根节点显示的名称
 * @return {} 一棵树形菜单(无显示菜单树的根节点)
 */
function providetreePanel(mainMenuId, rootName){
	var url = path+"/menu/rightChildModule.action?method=showChildRightMenu&mainMenuId=";
	var rloader = new Ext.tree.TreeLoader({
		url:url+mainMenuId,
		baseAttrs:{uiProvider:Ext.tree.TreeNodeUI}
	});
	var root = new Ext.tree.AsyncTreeNode({
		id:mainMenuId,
		loader: rloader,//new Ext.tree.TreeLoader({url:"extMenu/rootMenu.action?method=showRootMenu&nodeId=1"}),
		draggable:false,
		singleClickExpand:true,//用单击文本展开,默认为双击
		text:rootName
	});
	var tree = new Ext.tree.TreePanel({
		//title:title,
		//renderTo:renderTo,//填充区域
		region:'center',
		animate:true,//动画效果
		autoScroll:true,//是否可自动滚动
		autoHeight:true,//自动高度
		collapsible:true,
		enableDD:false,//是否可拖曳
		containerScroll: true,
		rootVisible:false,//隐藏根节点
		singleExpand:false,//只显示一个树节点中的子节点,默认为显示全部
		root:root,
		width:250,
		//hight:600,
		dropConfig: {appendOnly:true},
		border:false//没有边框
	});
	tree.on('beforeload',function(node){rloader.url = url+node.id;});//mainMenuId
	// TODO 可以在这里做，当用户点击菜单的时候，在中间出现tabPanel
	tree.on("click",function(node){
		menuId = node.id;
		if(node.isLeaf()){
			var tabPanel = Ext.getCmp("mainTabPanel");
			if(!tabPanel){
				createTabPanel(node.text,node.id+"_tab",node.attributes.hrefComment);
			}else{
				var tabPanelPage = Ext.getCmp(node.id+"_tab");
				if(!tabPanelPage){
					addTabPanel(tabPanel,node.id+"_tab",node.text,node.attributes.hrefComment);
				}else{
					activeTabPanel(tabPanel,node.id+"_tab");
				}
			}
			return;
		}
	});
	return tree;
}