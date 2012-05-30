function systemLogin(){
	var loginForm = userLoginForm();
	var loginWindow = userLoginWindow("loginWindow",loginForm);
	loginWindow.show();
}

function userLoginWindow(id, items){
	var loginWindow = new Ext.Window({
		id:id,
		title:"登录系统",
		//layout:"fit",
		width:300,
		//height:300,
		modal:true,
		plain:true,
		items:items
	});
	return loginWindow;
}

function userLoginForm(username,password){
	var loginForm = new Ext.form.FormPanel({
		url: path+"/j_spring_security_check",
		//title: 'Login',
		//renderTo: Ext.getBody(),
		frame: true,
		cls: 'my-form-class',
		labelWidth:80,
		
		width: 300,
		items: [{
			xtype: 'textfield',
			fieldLabel: '用户名',
			allowBlank:false,
			name: 'j_username',
			enableKeyEvents:true,
			value:username,
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
			fieldLabel: '密码',
			allowBlank:false,
			id:"j_password",
			name: 'j_password',
			value:password,
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
			fieldLabel: '记住我',
			name: '_spring_security_remember_me',
			checked: false
		},{
			xtype:"hidden",
			name:"loginType",
			value:"ext"
		}],
		buttons: [{
			id: 'lf.btn.login',
			text: '登录',
			handler: function() {
				fnLoginForm(loginForm);
			}
		},{
			id: 'lf.btn.reset',
			text: '重置',
			handler: function() {
				fnResetForm(loginForm);
			}
		}]
	});
	
	return loginForm;
}

//Submit login and handler response
function fnLoginForm(theForm)
{	
	document.getElementById("loadMarskDiv").style.display = "block";
	var loadMask = new Ext.LoadMask("loadMarskDiv",{
		msg:"正在登陆系统，请稍候...",
		removeMask:true
	});
	loadMask.show();
	//清除菜单信息
	resetMenu();
	closeAllTab();
	if(theForm.form.isValid()){
		theForm.getForm().submit({
			timeout:60000,
			success: function(form, action) {
				document.getElementById("loadMarskDiv").style.display = "none";
				loadMask.hide();
				Ext.Msg.alert('系统提示', '您已成功登录系统!', function(btn, text) {
					if (btn == 'ok') {
						//window.location = path+"/index.jsp";
						var msg = Ext.decode(action.response.responseText);
						rootMenu = msg.rootMenu;
						userName = msg.userName;
						//显示根节点的菜单
						//showRootMenu(rootMenu,msg.menuSize);
						loadMenuPanel(msg.userName);
						Ext.getCmp("userInfo_panel").setTitle("欢迎您   "+msg.userName);
						var loginWindow = Ext.getCmp("loginWindow");
						if(loginWindow){
							loginWindow.close();
						}
					}
				});
			},
			failure: function(form, action) {//action.result.errorMessage
				document.getElementById("loadMarskDiv").style.display = "none";
				loadMask.hide();
				var msg = Ext.decode(action.response.responseText);
				if(msg && msg.error){
					Ext.Msg.alert('系统提示', "登录失败，可能是以下原因导致您登录失败："+msg.error, function(btn){
						if(btn == "yes" || btn == "ok"){
							window.location = path+"/j_spring_security_logout";
						}
					});
				}else{
					Ext.Msg.alert('系统提示', "登录失败！");
				}
				
			}
		});
	}
} //end fnLoginForm

function fnResetForm(theForm)
{
	theForm.getForm().reset();
} //end fnResetForm
