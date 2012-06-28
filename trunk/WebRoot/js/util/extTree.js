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
		id:mainMenuId+"_tree",
		//title:title,
		//renderTo:"testDiv",//填充区域
		//region:'center',
		animate:true,//动画效果
		autoScroll:true,//是否可自动滚动
		//autoHeight:true,//自动高度
		collapsible:true,
		enableDD:false,//是否可拖曳
		containerScroll: true,// 随自身或父容器的改变而显示或隐藏scroll
		rootVisible:false,//隐藏根节点
		singleExpand:false,//只显示一个树节点中的子节点,默认为显示全部
		root:root,
		//width:193,
		//height:100,
		dropConfig: {appendOnly:true},
		border:false//没有边框
//		tbar:[{
//			text:"刷新菜单",
//			tooltip:"刷新["+rootName+"]菜单",
//			iconCls:"table_refresh",
//			handler:function(){
//				root.reload();
//			}
//		}]
	});
	tree.on('beforeload',function(node){rloader.url = url+node.id;});//mainMenuId
	//当用户点击菜单的时候，在中间出现tabPanel
	tree.on("click",function(node){
		menuId = node.id;
		if(node.isLeaf()){
			fromMenuId = "";
			createMainTabPanel(node.text, node.id, node.attributes.hrefComment, true);
			return;
		}
	});
	return tree;
}