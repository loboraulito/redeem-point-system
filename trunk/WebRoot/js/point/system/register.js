/**
 * 系统用户注册
 */
function systemRegister(){
	var url = path + "/user/userRegister.action?method=registerUser";
	/**
	 * 窗口, 用于新增，修改
	 * @param {} id 窗口ID
	 * @param {} title 窗口名字
	 * @param {} width 窗口宽度
	 * @param {} height 窗口高度
	 * @param {} items 窗口的内部
	 * @param {} buttons 窗口的按钮
	 */
	function showUserWindow(id, title, width, height, items, buttons){
		var userWindow = new Ext.Window({
			id:id,
			title:title,
			width:width,
			height:height,
			items:items,
			buttons:buttons,
			modal:true,
			layout:"fit",
			resizable:false
		});
		userWindow.show();
	}
	/**
	 * 表单信息
	 * @param {} url 提交表单的url
	 * @param {} isNull 是否允许为null，false-不允许
	 * @param {} readOnly 是否只读， true-只读
	 * @param {} validator 是否校验， true-校验数据
	 * @return {}
	 */
	function showUserForm(url,isNull,readOnly,validator){
		var userForm = new Ext.form.FormPanel({
			frame: true,
			labelAlign: 'right',
			labelWidth:60,
			autoScroll:false,
			waitMsgTarget:true,
			url:url,
			items:[{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"userName",
						anchor:"90%",
						fieldLabel:"用户名",
						maxLength:50,
						readOnly:readOnly,
						allowBlank:isNull,
						validationEvent:"blur",
						//see @js/ext-2.2.1/source/ux/RemoteValidator.js. Be sure that the response text must be like this:{success:true,valid:true/false,reson:'some reson'}
						plugins:validator ? [Ext.ux.plugins.RemoteValidator] : null,
						rvOptions:{
							url:path + "/user/userValidation.action?method=validateUserName"
						}
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						inputType: 'password',
						name:"password",
						anchor:"90%",
						fieldLabel:"密码",
						maxLength:50,
						allowBlank:isNull
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"telphoneNo",
						anchor:"90%",
						fieldLabel:"电话号码",
						maxLength:50,
						allowBlank:isNull
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"phoneNo",
						anchor:"90%",
						fieldLabel:"手机号码",
						maxLength:50
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"privence",
						anchor:"90%",
						fieldLabel:"省/直辖市",
						maxLength:50
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"city",
						anchor:"90%",
						fieldLabel:"市/县",
						maxLength:50
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.9,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"address",
						anchor:"90%",
						fieldLabel:"地址",
						maxLength:500
					},{
						xtype:"hidden",
						name:"userId"
					},{
						xtype:"hidden",
						name:"userCode"
					}]
				}]
			},{
				layout:"column",
				border:false,
				labelSeparator:'：',
				items:[{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"zip",
						anchor:"90%",
						fieldLabel:"邮编",
						maxLength:50
					}]
				},{
					layout:"form",
					columnWidth:.5,
					height:50,
					items:[{
						xtype: 'textfield',
						name:"email",
						anchor:"90%",
						fieldLabel:"电子邮件",
						maxLength:50
					}]
				}]
			}]
		});
		return userForm;
	}
	var userForm = showUserForm(url,false,false,true);
	var button = [{
		text:"提交",
		type:"submit",
		handler:function(){
			if(userForm.form.isValid()){
				saveUser("registerWindow", userForm);
			}
		}
	},{
		text:"关闭窗口",
		handler:function(){
			var userWindow = Ext.getCmp("registerWindow");
			if(userWindow){
				userWindow.close();
			}
		}
	}];
	showUserWindow("registerWindow","系统用户注册",500,320,userForm,button); 
	
	
	/**
	 * 保存用户信息
	 * @param {} windowId
	 * @param {} form
	 */
	function saveUser(windowId, form){
		Ext.MessageBox.show({
			msg:"正在注册用户信息，请稍候...",
			progressText:"正在注册用户信息，请稍候...",
			width:300,
			wait:true,
			waitConfig: {interval:200},
			icon:Ext.Msg.INFO
		});
		form.getForm().submit({
			success: function(form, action) {
				Ext.Msg.hide();
				var msg = Ext.decode(action.response.responseText);
				if(msg && msg.msg){
					Ext.Msg.alert('系统提示信息', msg.msg+"系统将自动为您登录！", function(btn) {
						if (btn == 'ok') {
							Ext.getCmp(windowId).close();
							var username = form.findField("userName").getValue();
							var password = form.findField("password").getValue();
							autoLogin(username,password);
						}
					});
				}else{
					Ext.Msg.alert('系统提示信息', '注册成功,系统将自动为您登录！', function(btn) {
						if (btn == 'ok') {
							Ext.getCmp(windowId).close();
							var username = form.findField("userName").getValue();
							var password = form.findField("password").getValue();
							autoLogin(username,password);
						}
					});
				}
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.hide();
				var msg = Ext.decode(action.response.responseText);
				if(msg && msg.msg){
					Ext.Msg.alert('系统提示信息', msg.msg);
				}else{
					Ext.Msg.alert('系统提示信息', "注册过程中出现异常!");
				}
			}
		});
	}
	/**
	 * 自动登录
	 * @param {} params
	 */
	function autoLogin(username,password){
		//清除菜单信息
		resetMenu();
		Ext.Ajax.request({
			params:{j_username:username,j_password:password,loginType:"ext"},
			timeout:60000,
			method:"post",
			url:path+"/j_spring_security_check",
			success:function(response,options){
				var msg = Ext.util.JSON.decode(response.responseText);
				rootMenu = msg.rootMenu;
				userName = msg.userName;
				loadMenuPanel(msg.userName);
				Ext.getCmp("userInfo_panel").setTitle("欢迎您   "+msg.userName);
			},failure:function(response,options){
				Ext.Msg.alert("提示信息","用户登录过程中出现异常！");
				return;
			}
		});
	}
}