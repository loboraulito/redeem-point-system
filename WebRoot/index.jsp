<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path1 = request.getContextPath();
%>
<html>
<head>
<script type="text/javascript" src="<%=path1%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=path1%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=path1%>/dwr/interface/messageService.js"></script>

<%@include file="/jsp/common/config.jsp" %>
<%
Object loginRoleName = session.getAttribute("loginRoleName");
%>
<script type="text/javascript">
var loginRoleName = "<%=loginRoleName%>";
</script>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/util/calendar/simple/calendar/calendar.css">
<script type="text/javascript" src="<%=path %>/js/util/calendar/simple/calendar/calendar.js"></script>

<script type="text/javascript" src="<%=path %>/js/util/extTree.js"></script>
<script type="text/javascript" src="<%=path %>/js/index.js"></script>
<!--
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/RemoteValidator.js"></script>
<script type="text/javascript" src="<%=path %>/js/point/system/login.js"></script>
<script type="text/javascript" src="<%=path %>/js/point/system/register.js"></script>
-->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/TabCloseMenu.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/tabPanel.js"></script>

<script type="text/javascript" src="<%=path%>/js/util/messageRemind.js"></script>

<script type="text/javascript">
var rootMenu;
var menuId;
</script>

<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	enableDWRAjax(true);
});
</script>
<!--
<style type="text/css">
#header {
    background: #7F99BE url(<%=path %>/images/layout-browser-hd-bg.gif) repeat-x center;
}
#header h1 {
    font-size: 16px;
    color: #fff;
    font-weight: normal;
    padding: 5px 10px;
}
</style>
-->
<script type="text/javascript">
function recieveMsg(msg){
  	//提示框的长度和宽度的偏移量
	var windowwidth = 320;
	var windowheight = 180;
	//可见区域大小的宽度-提示框的宽度
	var xwidth = document.body.clientWidth-windowwidth;
	//可见区域大小的高度-提示框的高度
	var yheight = document.body.clientHeight-windowheight;
	//alert(xwidth+" "+yheight+" "+xwidths+" "+yheights);
	var show = Ext.getCmp("messageWindow_show");
	if(!show){
	    var messageWindow = new Ext.Window({
		    id:"messageWindow_show",
			title:"您有新的消息",
			width:windowwidth,
			height:windowheight,
			html:"您有新的消息: <br>" + msg.messageContent + "<br>发送时间：" + msg.messageSendTime.format("Y-m-d H:i:s"),
			modal:false,
			layout:"fit",
			x:xwidth,
			y:yheight,
			buttons:[{
			    text:"点击查看",
			    handler:function(){
			        goToTabPanel("","8ac388f134d57cdd0134d57f9d1b0001",true);
			        messageWindow.close();
			    }
			}],
			resizable:false
		});
		messageWindow.show();
	}
}
</script>
<title>redeempoint system</title>
</head>
<body>
<!--
<div id="header">
	<table width="100%">
		<tr>
			<td>
				<h1>会员积分兑换系统</h1>
			</td>
			<td align="right" width="60%">
				<a href="javascript:systemLogin()">登录系统</a>
			</td>
			<td align="right" width="10%">
				<a href="javascript:systemRegister()">尚未注册？</a>
			</td>
			<td align="right" width="10%">
				<a href="<%=path%>/j_spring_security_logout">退出系统</a>
			</td>
		</tr>
	</table>
</div>
-->
<%@include file="/jsp/common/header.jsp" %>
<div id="loadMarskDiv" style="position:absolute; z-index: 9999; display:none; width:100%; height:100%;"></div>
<script type="text/javascript">

</script>
<!-- 
<br>
<a href="<%=path%>/login.jsp">登录系统</a>
<p><a href="<%=path%>/j_spring_security_logout">退出系统</a></p>
 -->
</body>
</html>