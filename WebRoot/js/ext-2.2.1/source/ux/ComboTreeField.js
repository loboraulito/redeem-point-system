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
			http://hi.baidu.com/lvsiquan/blog/item/d46acc9797a83d7455fb96dc.html
		}
	}
})