<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@include file="/jsp/common/config.jsp" %>
<html>
    <head>
        <title>用户登录</title>
        <script type="text/javascript">
            var path = "<%=path%>";
        </script>
        <link href="<%=path%>/js/util/loginpage/bluelogin/css/Default.css" type="text/css" rel="stylesheet" />
        <link href="<%=path%>/js/util/loginpage/bluelogin/css/User_Login.css" type="text/css" rel="stylesheet" />
        <link href="<%=path%>/js/util/loginpage/bluelogin/css/xtree.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" language="JavaScript" src="<%=path%>/js/util/validate/validatorForForm_v4.js"></script>
    </head>
    
    <body id="userlogin_body">
        <!--
        <form action="<%=path%>/j_spring_security_check" method="post">
        <table width="100%" border="1" cellspacing="0" cellpadding="0">
        <tr>
        <td>
        用户名：
        </td>
        <td>
        <input type="text" name="j_username" id="j_username"/>
        </td>
        </tr>
        <tr>
        <td>
        密码：
        </td>
        <td>
        <input type="password" name="j_password" id="j_password" />
        </td>
        </tr>
        <tr>
        <td>
        <input type="radio" name="roleName" value="1">我是管理员</input>
        </td>
        <td>
        <input type="radio" name="roleName" value="2">我是客户</input>
        </td>
        </tr>
        <tr>
        <td>
        <input type="submit" value="登录"/>
        </td>
        <td>
        <input type="reset" />
        </td>
        </tr>
        </table>
        </form>
        -->
        <FORM id="user_login" action="<%=path%>/j_spring_security_check" method="post">
            <DL>
                <DD id=user_top>
                    <UL>
                        <LI class=user_top_l>
                        </LI>
                        <LI class=user_top_c>
                        </LI>
                        <LI class=user_top_r>
                        </LI>
                    </UL>
                    <DD id=user_main>
                        <UL>
                            <LI class=user_main_l>
                            </LI>
                            <LI class=user_main_c>
                                <DIV class=user_main_box>
                                    <UL>
                                        <LI class=user_main_text>
                                            用户名： 
                                        </LI>
                                        <LI class=user_main_input>
                                            <INPUT class=TxtUserNameCssClass id="j_username" maxLength=20 name="j_username" value="admin">
                                        </LI>
                                    </UL>
                                    <UL>
                                        <LI class=user_main_text>
                                            密 码： 
                                        </LI>
                                        <LI class=user_main_input>
                                            <INPUT class=TxtPasswordCssClass id="j_password" type=password name="j_password" value="admin">
                                        </LI>
                                    </UL>
                                    <UL>
                                        <li class="user_main_input">
                                        	<span>
                                        		<INPUT type="radio" checked name="roleName" id="radio1" value="1" style="vertical-align:middle;margin-top:-2px; margin-bottom:1px;">
													<label for="radio1">我是管理员</label>
												</INPUT>
                                        	</span>
                                        </li>
                                        <LI class=user_main_text>
                                            
                                        </LI>
                                        <LI class=user_main_input>
                                        	<span>
                                        		<INPUT type="radio" name="roleName" id="radio2" value="2" style="vertical-align:middle;margin-top:-2px; margin-bottom:1px;">
													<label for="radio2">我是用户</label>
	                                            </INPUT>
                                        	</span>
                                        </LI>
                                    </UL>
                                    <UL>
                                        <LI class=user_main_input>
                                            <input type="checkbox" id="rememberme" name="_spring_security_remember_me" checked>
												<label for="rememberme">记住我</label>
											</input>
                                        </LI>
                                    </UL>
                                </DIV>
                            </LI>
                            <LI class=user_main_r>
                                <INPUT class=IbtnEnterCssClass id=IbtnEnter style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px" type="image" src="<%=path%>/js/util/loginpage/bluelogin/Images/user_botton.gif" name=IbtnEnter>
                            </LI>
                        </UL>
                        <DD id=user_bottom>
                            <UL>
                                <LI class=user_bottom_l>
                                </LI>
                                <LI class=user_bottom_c>
                                </LI>
                                <LI class=user_bottom_r>
                                </LI>
                            </UL>
                        </DD>
                    </DD>
                </DD>
            </DL>
            <SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN>
            <SPAN id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN>
            <SPAN id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
            <DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red">
            </DIV>
            <DIV>
            </DIV>
        </FORM>
    </body>
	<script type="text/javascript">
		//function validateForm(){
			var frmvalidator  = new Validator("user_login");
			frmvalidator.addValidation("j_username","req","请输入用户名！");
			frmvalidator.addValidation("j_password","req","请输入密码！");
		//}
		//validateForm();
		
		function checkUser(){
			var j_user = document.getElementById("j_username");
			var j_psw = document.getElementById("j_password");
			if(!j_user.value || j_user.value == "" || j_user.value.trim() == "" || j_user.value.length <1){
				alert("请输入用户名！");
				j_user.focus();
				return false;
			}
			if(!j_psw.value || j_psw.value == "" || j_psw.value.length <1){
				alert("请输入密码！");
				j_psw.focus();
				return false;
			}
			return true;
		}
		function userLoginForm(){
			//this.form.submit(); //直接提交表单 
			//this.form.onsubmit(); //调用form的onsubmit方法 
			//this.form.fireEvent('onsubmit'); //同上, 
			//if(document.getElementById("user_login").onsubmit()){
				//document.getElementById("user_login").submit();
			//}
			//alert(1);
		}
		
	</script>
	<%
    Object msg = session.getAttribute("loginFailMsg");
    if(msg == null || "".equals(msg.toString().trim())){
    %>
	<%
    }else{
	%>
	<script type="text/javascript">
		Ext.Msg.alert("系统提示","<%=msg%>");
	</script>
	<%
	session.removeAttribute("loginFailMsg");
	%>
	<%} %>
	
</html>
