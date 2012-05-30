<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/jsp/common/config.jsp" %>

<!-- 下拉多选框插件皮肤 -->
<link rel="stylesheet" type="text/css" href="<%=path%>/js/util/mutiCombo/lovcombo-1.0/css/empty.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/util/mutiCombo/lovcombo-1.0/css/Ext.ux.form.LovCombo.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/util/mutiCombo/lovcombo-1.0/css/lovcombo.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=path%>/js/util/mutiCombo/lovcombo-1.0/css/webpage.css"> -->

<!-- 下拉多选框基于Ext2的插件 -->
<script type="text/javascript" src="<%=path %>/js/util/mutiCombo/lovcombo-1.0/js/WebPage.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/mutiCombo/lovcombo-1.0/js/Ext.ux.form.LovCombo.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/mutiCombo/lovcombo-1.0/js/Ext.ux.ThemeCombo.js"></script>

<!-- DWR3.0 RC Ajax反转 -->
<script type="text/javascript" src="<%=path%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/util.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/dwr/interface/messageService.js"></script> -->

<script type="text/javascript" src="<%=path%>/dwr/interface/userService.js"></script>
<script type="text/javascript" src="<%=path%>/js/point/system/message.js"></script>

<style type="text/css">
.wap {
	overflow-x: hidden;
	overflow-y: auto;
	word-break: break-all;
}
</style>

<title>Insert title here</title>
</head>
<body>
	<div id="message_div" style="width: 100%;height: 100%"></div>
</body>
</html>