/**
 * @class Ext.tree.ColumnCheckNodeUI
 * @extends Ext.tree.TreeNodeUI
 * 
 * 对 Ext.tree.ColumnNodeUI 进行checkbox功能的扩展,后台返回的结点信息不用非要包含checked属性
 * 
 * 扩展的功能点有：
 * 一、支持只对树的叶子进行选择
 *    只有当返回的树结点属性leaf = true 时，结点才有checkbox可选
 * 	  使用时，只需在声明树时，加上属性 onlyLeafCheckable: true 既可，默认是false
 * 
 * 二、支持对树的单选
 *    只允许选择一个结点
 * 	  使用时，只需在声明树时，加上属性 checkModel: "single" 既可
 * 
 * 二、支持对树的级联多选 
 *    当选择结点时，自动选择该结点下的所有子结点，以及该结点的所有父结点（根结点除外），特别是支持异步，当子结点还没显示时，会从后台取得子结点，然后将其选中/取消选中
 *    使用时，只需在声明树时，加上属性 checkModel: "cascade" 既可
 * 
 * 三、添加"check"事件
 *    该事件会在树结点的checkbox发生改变时触发
 *    使用时，只需给树注册事件,如：
 *    tree.on("check",function(node,checked){alert(node.id+" = "+checked)});
 
 * 四、去掉checkbox
 *   使用时，只需在声明树时，加上属性 noCheck:true 即可
 * 
 * 默认情况下，checkModel为'multiple'，也就是多选，onlyLeafCheckable为false，所有结点都可选
 * 
 * 例如：
 * 
 *	var root = new Ext.tree.AsyncColumnTreeNode({
 *		text:'Tasks'
 *	});
 *	
 *	var store = new Ext.data.TreeJsonStore({
 *		root : 'data',   // 数据集合
 *		rootNode : root, // 配置树的根节点
 *		totalProperty : 'totalCount', // 总行数
 *		proxy : new Ext.data.HttpProxy( {
 *			url : 'grid-page.jsp'   //请求地址
 *		}),
 *		fields: ['id', 'name', 'age', 'birth', 'bz']  // 数据属性名
 *	});
 *
 *  var tree = new Ext.tree.ColumnCheckTree({
 *        width: 900,
 *        height: 600,
 *        rootVisible:false, // false不显示根节点；true 为显示
 *        autoScroll:true,
 *        checkModel : 'cascade',   // 级联多选
 *        title: 'columnCheckTree',
 *        renderTo: Ext.getBody(),
 *        columns:[{    
 *            header:'编号',
 *            width:330,
 *            dataIndex:'id'
 *        },{
 *        	header:'名称',
 *        	width:100,
 *        	dataIndex:'name'
 *        },{
 *        	header:'年龄',
 *        	width:100,
 *        	dataIndex:'age'
 *        },{
 *            header:'生日',
 *            width:100,
 *            dataIndex:'birth'
 *        },{
 *            header:'备注',
 *            width:100,
 *            dataIndex:'bz'
 *        }],
 *        store : store, // 数据源
 *		  bbar : new Ext.TreePagingToolbar( {  // 配置 翻页工具条
 *			  store : store,
 *			  displayInfo : true
 *		  }),
 *        root: root
 *    });
 *    
 * 拼接JSON：
 * sb.append("{success:true,data:");
 *    sb.append("[");
 *    {	
 *    	for(int i = s ; i < s+l; i ++){
 *    	sb.append("{leaf:false,cls:'master-task',iconCls:'task-folder',id:'").append(i);
 *   	sb.append("',name:'").append("bob" + i);
 *    	sb.append("',age:'").append(i);
 *   	sb.append("',birth:'").append("2010-12-20");
 *   	sb.append("',children:[{leaf:true,name:'ss',iconCls:'task',id:'").append(i+ "" + i).append("'}],");
 *   	sb.append("bz:'").append("备注").append(i==s+l-1?"'}":"'},");
 *   	}
 *   }
 * sb.append("],totalCount:100}");
 * 
 */
Ext.tree.ColumnCheckTree = Ext.extend(Ext.tree.TreePanel, {
	lines : false,
	borderWidth : Ext.isBorderBox ? 0 : 2, // the combined left/right border
	clearOnLoad	: true,									
	cls : 'x-column-tree',
	
	
	onRender : function() {
		Ext.tree.ColumnCheckTree.superclass.onRender.apply(this, arguments);
		this.headers = this.body.createChild( {cls : 'x-tree-headers'}, this.innerCt.dom);
	
		var cols = this.columns, c;
		var totalWidth = 0;
		
		for ( var i = 0, len = cols.length; i < len; i++) {
			c = cols[i];
			totalWidth += c.width;
			this.headers.createChild( {
				cls : 'x-tree-hd ' + (c.cls ? c.cls + '-hd' : ''),
				cn : {
					cls : 'x-tree-hd-text',
					html : c.header
				},
				style : 'width:' + (c.width - this.borderWidth) + 'px;'
			});
		}
		this.headers.createChild( {
			cls : 'x-clear'
		});
		// prevent floats from wrapping when clipped
		this.headers.setWidth(totalWidth);
		this.innerCt.setWidth(totalWidth);
	},
    
	getStore : function(){
	    return this.store;	
	},
	getSelectionModel : function(){
        if(!this.selModel){
            this.selModel = new Ext.tree.DefaultSelectionModel({
            	onNodeClick : function(node, e){
			        this.select(node);
			        if(node.getUI().checkbox){
			        	node.getUI().check(!node.attributes.checked);
			        }
			    }
            });
        }
        return this.selModel;
    }
});
Ext.tree.ColumnCheckNodeUI = Ext.extend(Ext.tree.TreeNodeUI,{
					focus : Ext.emptyFn, // prevent odd scrolling behavior
					renderElements : function(n, a, targetNode, bulkRender) {
						this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';
						var t = n.getOwnerTree();
						var cols = t.columns;
						var bw = t.borderWidth;
						var c = cols[0];
						var cb =  !t.noCheck&&(!t.onlyLeafCheckable||a.leaf);
						var buf = [
								'<li class="x-tree-node"><div ext:tree-node-id="',n.id,
								'" class="x-tree-node-el x-tree-node-leaf ',a.cls,'">',
								'<div class="x-tree-col" style="width:',c.width - bw,'px;">',
								'<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
								'<img src="',this.emptyIcon,'" mce_src="',this.emptyIcon,'" class="x-tree-ec-icon x-tree-elbow">',
								'<img src="',a.icon || this.emptyIcon,'" mce_src="',a.icon || this.emptyIcon,'" class="x-tree-node-icon',
								(a.icon ? " x-tree-node-inline-icon" : ""),
								(a.iconCls ? " " + a.iconCls : ""),
								'" unselectable="on">',
								cb?'<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />': '/>'):'',
								'<a hidefocus="on" class="x-tree-node-anchor" href="',
								a.href ? a.href : '" mce_href="',
								a.href ? a.href : "#",'" tabIndex="1" ',
								a.hrefTarget ? ' target="' + a.hrefTarget + '"': "",'>',
								'<span unselectable="on">',
								n.text|| (c.renderer ? c.renderer(a[c.dataIndex], n, a): a[c.dataIndex]),
								"</span></a>", "</div>" ];
						for ( var i = 1, len = cols.length; i < len; i++) {
							c = cols[i];
							buf.push('<div class="x-tree-col ', (c.cls ? c.cls: ''), '" style="width:', c.width - bw,
									'px;">', '<div class="x-tree-col-text">',(c.renderer ? c.renderer(a[c.dataIndex], n,a) : a[c.dataIndex]), "</div>",
									"</div>");
						}
						buf.push('<div class="x-clear"></div></div>','<ul class="x-tree-node-ct" style="display:none;" mce_style="display:none;"></ul>',"</li>");
						if (bulkRender !== true && n.nextSibling&& n.nextSibling.ui.getEl()) {
							this.wrap = Ext.DomHelper.insertHtml("beforeBegin",n.nextSibling.ui.getEl(), buf.join(""));
						} else {
							this.wrap = Ext.DomHelper.insertHtml("beforeEnd",targetNode, buf.join(""));
						}
						this.elNode = this.wrap.childNodes[0];
						this.ctNode = this.wrap.childNodes[1];
						var cs = this.elNode.firstChild.childNodes;
						this.indentNode = cs[0];
						this.ecNode = cs[1];
						this.iconNode = cs[2];
						var index = 3;
						if(cb){
							this.checkbox = cs[index];
							Ext.fly(this.checkbox).on('click',this.check.createDelegate(this,[null]));
							index++;
						}
						this.anchor = cs[index];
						this.textNode = cs[index].firstChild;
					},
					// private
					check : function(checked) {
						var n = this.node;
						var tree = n.getOwnerTree();
						this.checkModel = tree.checkModel || this.checkModel;
						if (checked === null) {
							checked = this.checkbox.checked;
						} else {
							this.checkbox.checked = checked;
						}
						n.attributes.checked = checked;
						if(checked){
							tree.getSelectionModel().select(n);
						}else{
							tree.getSelectionModel().unselect(n);
						}
						tree.fireEvent('check', n, checked);
						if(!this.onlyLeafCheckable && this.checkModel =='cascade'){
							var parentNode = n.parentNode;
							if (parentNode !== null) {
								this.parentCheck(parentNode, checked);
								parentNode.expand();
							}
							if (!n.expanded && !n.childrenRendered) {
								n.expand(false, false, this.childCheck);
							} else {
								this.childCheck(n);
							}
						}else if(this.checkModel == 'single'){
							 var checkedNodes = tree.getChecked();
							 for(var i=0;i<checkedNodes.length;i++){
								 var node = checkedNodes[i];
								 if(node.id != n.id){
									 node.getUI().checkbox.checked = false;
									 node.attributes.checked = false;
									 tree.fireEvent('check', node, false);
								 }
							 }
						}
					},
					// private
					childCheck : function(node) {
						var a = node.attributes;
						if (!a.leaf) {
							var cs = node.childNodes;
							var csui;
							for ( var i = 0; i < cs.length; i++) {
								csui = cs[i].getUI();
								if (csui.checkbox.checked ^ a.checked)
									csui.check(a.checked);
							}
						}
					},
					// private
					parentCheck : function(node, checked) {
						var checkbox = node.getUI().checkbox;
						if (typeof checkbox == 'undefined')
							return;
						if (!(checked ^ checkbox.checked))
							return;
						if (!checked && this.childHasChecked(node))
							return;
						checkbox.checked = checked;
						node.attributes.checked = checked;
//						node.getOwnerTree().fireEvent('check', node, checked);
						var parentNode = node.parentNode;
						if (parentNode !== null) {
							this.parentCheck(parentNode, checked);
						}
					},
					// private
					childHasChecked : function(node) {
						var childNodes = node.childNodes;
						if (childNodes || childNodes.length > 0) {
							for ( var i = 0; i < childNodes.length; i++) {
								if (childNodes[i].getUI().checkbox.checked)
									return true;
							}
						}
						return false;
					},
					// private
					onCheckChange : function() {
						var checked = this.checkbox.checked;
						// fix for IE6
						this.checkbox.defaultChecked = checked;
						this.node.attributes.checked = checked;
						this.check(checked);
						this.fireEvent('checkchange', this.node, checked);
					},
					toggleCheck : function(value) {
						var cb = this.checkbox;
						if (cb) {
							var checked = (value === undefined ? !cb.checked: value);
							this.check(checked);
						}
					 }
				});

Ext.tree.AsyncColumnTreeNode = Ext.extend(Ext.tree.AsyncTreeNode,{
	loaded : false,
	expand : function(deep, anim, callback){
	    if(this.loading){ // if an async load is already running, waiting til it's done
	        var timer;
	        var f = function(){
	            if(!this.loading){ // done loading
	                clearInterval(timer);
	                this.expand(deep, anim, callback);
	            }
	        }.createDelegate(this);
	        timer = setInterval(f, 200);
	        return;
	    }
	    if(!this.loaded){
	        if(this.fireEvent("beforeload", this) === false){
	            return;
	        }
	        this.loading = true;
	        this.ui.beforeLoad(this);
	        var s = this.store || this.attributes.store || this.getOwnerTree().getStore();
	        if(s){
	        	s.load({},this,this.loadComplete.createDelegate(this, [deep, anim, callback]));
	        }
	    }
	    Ext.tree.AsyncTreeNode.superclass.expand.call(this, deep, anim, callback);
	},
	loadComplete : function(deep, anim, callback){
		this.loading = false;
		this.loaded = true;
		this.ui.afterLoad(this);
		this.fireEvent("load", this);
		this.expand(deep, anim, callback);
	}
	
});
Ext.data.TreeJsonStore = Ext.extend(Ext.data.JsonStore,{
	
	clearOnLoad : true,
	
	loadNode : this.rootNode,
	
    loadData : function(o, node){
		if(!node){
			node = this.rootNode;
		}else{
			this.loadNode = node;
		}
		if(node|!node.render){ // attributes passed
			node = this.store.createNode(node,true);
		}
		while(node.firstChild){
            node.removeChild(node.firstChild);
        }
		node.beginUpdate();
        for(var i = 0, len = o.length; i < len; i++){
        	if(!o[i].children){
        		o[i].leaf = true;
        	}
            var n = this.createNode(o[i]);
            if(n){
                node.appendChild(n);
            }
        }
        node.endUpdate();
	    node.expand();
	},
	
    load : function(options,node,callback){
		if(!node){
			node = this.rootNode;
			this.loadNode = node;
		}else{
			this.loadNode = node;
		}
		if(node){
		    if(this.clearOnLoad){
		        while(node.firstChild){
		            node.removeChild(node.firstChild);
		        }
		    }
		    if(this.doPreload(node)){ // preloaded json children
		        if(typeof callback == "function"){
		            callback();
		        }
		    }else if(this.proxy.conn.dataUrl||this.proxy.conn.url){
		    	
		        this.requestData(options, node, callback);
		        node.loaded = true;
				node.loading = false;
				this.start = options&&options.params&&options.params.start?options.params.start:0;
		    }
		    return false;
		}
	},
	doPreload : function(node){
        if(node.attributes.children){
            if(node.childNodes.length < 1){ // preloaded?
                var cs = node.attributes.children;
                node.beginUpdate();
                for(var i = 0, len = cs.length; i < len; i++){
                    var cn = node.appendChild(this.createNode(cs[i]));
                    if(this.preloadChildren){
                        this.doPreload(cn);
                    }
                }
                node.endUpdate();
            }
            return true;
        }else {
            return false;
        }
    },
    requestData : function(options, node, callback){
        if(this.fireEvent("beforeload", this, node, callback) !== false){
            this.transId = Ext.Ajax.request({
                method:this.requestMethod,
                url: this.proxy.conn.dataUrl||this.proxy.conn.url,
                success: this.handleResponse,
                failure: this.handleFailure,
                scope: this,
                argument: {callback: callback, node: node},
                params: this.getParams(node,options)
            });
        }else{
            // if the load is cancelled, make sure we notify
            // the node that we are done
            if(typeof callback == "function"){
                callback();
            }
        }
    },
    getParams: function(node,options){
        var buf = [], bp = this.baseParams;
        for(var key in bp){
            if(typeof bp[key] != "function"){
                buf.push(encodeURIComponent(key), "=", encodeURIComponent(bp[key]), "&");
            }
        }
        if(options&&options.params){
	        for(var key in options.params){
	            if(typeof options.params[key] != "function"){
	                buf.push(encodeURIComponent(key), "=", encodeURIComponent(options.params[key]), "&");
	            }
	        }
        }
        buf.push("node=", encodeURIComponent(node.id));
        return buf.join("");
    }, 
    createNode : function(attr,asyn){
        if(this.baseAttrs){
            Ext.applyIf(attr, this.baseAttrs);
        }
        if(this.applyLoader !== false){
        	attr.store = this;
        }
        
        attr.uiProvider = eval('Ext.tree.ColumnCheckNodeUI');
        if(attr.nodeType){
            return new Ext.tree.TreePanel.nodeTypes[attr.nodeType](attr);
        }else{
            return attr.leaf | asyn ? new Ext.tree.TreeNode(attr) : new Ext.tree.AsyncColumnTreeNode(attr);
        }
    },
    processResponse : function(response, node, callback){
        var json = response.responseText;
        try {
            var o = eval("("+json+")");
            node.beginUpdate();
            for(var i = 0, len = o.data.length; i < len; i++){
                var n = this.createNode(o.data[i]);
                if(n){
                    node.appendChild(n);
                }
            }
            node.endUpdate();
            node.expand();
            if(typeof callback == "function"){
                callback(this, node);
            }
            this.data.clear();
            this.data.items = o.data;
            this.data.length = o.data.length;
            this.totalLength = o.totalCount;
        }catch(e){
            this.handleFailure(response);
        }
    },
    handleResponse : function(response){
        this.transId = false;
        var a = response.argument;
        this.processResponse(response, a.node, a.callback);
        this.fireEvent("load", this, a.node,{params:{start : this.start}} );
    },
    handleFailure : function(response){
        this.transId = false;
        var a = response.argument;
        this.fireEvent("loadexception", this, a.node, response);
        if(typeof a.callback == "function"){
            a.callback(this, a.node);
        }
    }
});
Ext.TreePagingToolbar = Ext.extend(Ext.PagingToolbar,{
		updateInfo : function(){
	        if(this.displayEl){
	        	if(this.store.loadNode.attributes.id != this.store.rootNode.attributes.id) return;
	            var count = this.store.getCount();
	            var msg = count == 0 ?
	                this.emptyMsg :
	                String.format(
	                    this.displayMsg,
	                    this.cursor+1, this.cursor+count, this.store.getTotalCount()
	                );
	            this.displayEl.update(msg);
	        }
	    }
});