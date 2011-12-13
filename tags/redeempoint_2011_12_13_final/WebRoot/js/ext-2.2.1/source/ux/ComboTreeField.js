Ext.form.TreeField = function(config){
	config.readOnly = true;
	Ext.form.TreeField.superclass.constructor.call(this, config);
	
	this.addEvents({
		"select" : true,
		"expand" : true,
		"collapse" : true,
		"beforeselect" : true
	});
}

var treeField;

Ext.extend(Ext.form.TreeField, Ext.form.TriggerField, {
	hiddenName : undefined,
	displayField : "text",
	displayValue : undefined,
	valueField : "id",
	listAlign : "tl-bl?",
	shadow : "sides",
	listClass : "",
	rootText : "",
	rootId : "-1",
	setValue : function(node){
		var text = node[this.displayField];
		var value = node[this.valueField];
		if(this.hiddenField){
			this.hiddenField.value = value;
		}
		Ext.form.TreeField.superclass.setValue.call(this, text);
		this.value = value;
		//将node当成数组
		if(!node){
			if(this.hiddenField){
				this.hiddenField.value = "";
			}
			Ext.form.TreeField.superclass.setValue.call(this, "");
			this.value = "";
			return;
		}
		if(Object.prototype.toString.call(node) !== "[object Array]"){
			var arr = [];
			arr.push(node);
			node = arr;
		}
		var text = [];
		var value = [];
		for(var i=0;i<node.length;i++){
			text.push(node[i][this.displayField]);
			value.push(node[i][this.valueField]);
		}
		if(this.hiddenField){
			this.hiddenField.value = value;
		}
		Ext.form.TreeField.superclass.setValue.call(this, text.join(","));
		this.value = value.join(",");
	},
	getValue : function(){
		if(this.valueField){
			return typeof this.value != 'undefined' ? this.value : "";
		}else{
			return Ext.form.TreeField.superclass.getValue.call(this);
		}
	},
	onSelect : function(node, tree){
		if(this.fireEvent("beforeselect", node, this) != false){
			this.collapse();
			this.setValue(node);
			this.fireEvent("select", this, node);
		}
	},
	createTree : function(el){
		var tree = new Ext.tree.TreePanel({
			el:el,
			width:120,
			autoHeight:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll:true,
			loader:new Ext.tree.TreeLoader({dataUrl:this.url}),
			bbar:["->",{
				text:"确定",handler:function(){
					var c = tree.getChecked();
					//将非叶子节点去除
					var cc = [];
					for(var i=0; i<c.length; i++){
						if(c[i]["leaf"] == true){
							cc.push[c[i]];
						}
					}
					treeField.setValue(cc);
					treeField.collapse();
				}
			},{
				text:"取消",handler:function(){
					tree.getRootNode().reload();
					treeField.setValue();
					treeField.collapse();
				}
			}]
		});
		var root = new Ext.tree.AsyncTreeNode({
			text:this.rootText,
			draggable:false,
			id:this.rootId
		});
		tree.setRootNode(root);
		tree.render();
		tree.on("checkchange", function(node, checked){
			node.expand();
			node.attributes.checked = checked;
			node.eachChild(function(child){
				child.ui.toggleCheck(checked);
				child.attributes.checked = checked;
				child.fireEvent("checkchange", child, checked);
			});
		}, tree);
		
		return tree;
	},
	onRender : function(ct, position){
		var value = this.value;
		Ext.form.TreeField.superclass.onRender.call(this, ct, position);
		this.value = value;
		if(this.hiddenName){
			this.hiddenField = this.el.insertSibling({
				tag:"input",
				type:"hidden",
				name:this.hiddenName,
				id:(this.hiddenId || this.hiddenName) 
			},"before", true);
			// TODO
			this.hiddenField.value = 
			this.hiddenValue !== undefined ? this.hiddenValue : 
			this.value !== undefined ? this.value : "";
			this.el.dom.removeAttribute("name");
		}
		var cls = "x-combd-list";
		this.list = new Ext.Layer({
			shadow:this.shadow,
			cls:[cls, this.listClass].join(" "),
			constrain:false
		});
		this.list.swallowEvent("mousewheel");
		this.innerList = this.list.createChild({
			cls:cls+"-inner"
		});
		var tree = this.createTree(this.innerList);
		treeField = this;
		tree.on("click", function(node, e){
			//treeField.onSelect(node, tree)
		})
		this.tree = tree;
		var _id = this.value ? this.value : "";
		var _text = this.displayValue ? this.displayValue : "";
		var node = {
			id:_id,
			text:_text
		};
		this.setValue(node);
	},
	onDestroy:function(){
		if(this.list){
			this.list.destory();
		}
		Ext.form.TreeField.superclass.onDestroy.call(this);
	},
	isExpanded : function(){
		return this.list.isVisible();
	},
	collapse:function(){
		if(!this.isExpanded()){
			return;
		}
		this.list.hide();
		Ext.get(document).un("mousedown",  this.collapseIf, this);
		Ext.get(document).un("mousewheel",  this.collapseIf, this);
		this.fireEvent("collapse",this);
	},
	collapseIf:function(e){
		if(!e.within(this.wrap) && !e.within(this.list)){
			this.collapse();
		}
	},
	expand : function(){
		if(this.isExpanded()){
			return;
		}
		this.list.alignTo(this.el, this.listAlign);
		this.list.show();
		Ext.get(document).un("mousedown",  this.collapseIf, this);
		Ext.get(document).un("mousewheel",  this.collapseIf, this);
		this.fireEvent("expand",this);
	},
	onTriggerClick : function(){
		if(this.disabled){
			return;
		}
		if(this.isExpanded()){
			this.collapse();
		}else{
			this.expand();
		}
		this.el.focus();
	}
});
Ext.reg("combotree",Ext.form.TreeField);