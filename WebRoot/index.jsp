<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/jsp/common/config.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/util/calendar/simple/calendar/calendar.css">
<script type="text/javascript" src="<%=path %>/js/util/calendar/simple/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/RemoteValidator.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/extTree.js"></script>
<script type="text/javascript" src="<%=path %>/js/index.js"></script>
<script type="text/javascript" src="<%=path %>/js/point/system/login.js"></script>
<script type="text/javascript" src="<%=path %>/js/point/system/register.js"></script>
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/TabCloseMenu.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/tabPanel.js"></script>
<script type="text/javascript">
var rootMenu;
var menuId;
</script>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
});
</script>
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
<title>redeempoint system</title>
</head>
<body>
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
<script type="text/javascript">

</script>
<!-- 
<br>
<a href="<%=path%>/login.jsp">登录系统</a>
<p><a href="<%=path%>/j_spring_security_logout">退出系统</a></p>
 -->
</body>
</html>