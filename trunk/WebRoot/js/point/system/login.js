function systemLogin(){
	var loginForm = new Ext.FormPanel({
		url: path+"/j_spring_security_check",
		//title: 'Login',
		//renderTo: Ext.getBody(),
		frame: true,
		cls: 'my-form-class',
		width: 350,
		items: [{
			xtype: 'textfield',
			fieldLabel: 'Login Name',
			allowBlank:false,
			name: 'j_username',
			enableKeyEvents:true,
			listeners:{
				"keydown":function(field,event){
					if(event.keyCode==13){
						//回车提交
						Ext.getCmp("j_password").focus();
					}
				}
			}
	    },{
			xtype: 'textfield',
			inputType: 'password',
			fieldLabel: 'Password',
			allowBlank:false,
			id:"j_password",
			name: 'j_password',
			enableKeyEvents:true,
			listeners:{
				"keydown":function(field,event){
					if(event.keyCode==13){
						//回车提交
						fnLoginForm(loginForm);
					}
				}
			}
		}, {
			xtype: 'checkbox',
			fieldLabel: 'Remember Me?',
			name: '_spring_security_remember_me',
			checked: false
		}],
		buttons: [{
			id: 'lf.btn.login',
			text: 'Login',
			handler: function() {
				fnLoginForm(loginForm);
			}
		},{
			id: 'lf.btn.reset',
			text: 'Reset',
			handler: function() {
				fnResetForm(loginForm);
			}
		}]
	});
	var loginWindow = new Ext.Window({
		id:"loginWindow",
		title:"Login",
		//layout:"fit",
		modal:true,
		plain:true,
		items:[loginForm]
	});
	loginWindow.show();
}

//Submit login and handler response
function fnLoginForm(theForm)
{
	//清除菜单信息
	resetMenu();
	if(theForm.form.isValid()){
		theForm.getForm().submit({
			success: function(form, action) {
				Ext.Msg.alert('Success', 'Login Successful!', function(btn, text) {
					if (btn == 'ok') {
						//window.location = path+"/index.jsp";
						var msg = Ext.decode(action.response.responseText);
						rootMenu = msg.rootMenu;
						userName = msg.userName;
						//显示根节点的菜单
						//showRootMenu(rootMenu,msg.menuSize);
						loadMenuPanel(msg.userName);
						Ext.getCmp("userInfo").setTitle("欢迎您   "+msg.userName);
						Ext.getCmp("loginWindow").close();
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				Ext.Msg.alert('Warning', "error");
			}
		});
	}
} //end fnLoginForm

function fnResetForm(theForm)
{
theForm.getForm().reset();
} //end fnResetForm
