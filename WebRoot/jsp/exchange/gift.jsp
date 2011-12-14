<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String basePath = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--
<script type="text/javascript" src="<%=basePath %>/js/jQuery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/ext-2.2.1/source/adapter/jquery/jquery-plugins.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/ext-2.2.1/source/adapter/jquery/ext-jquery-adapter.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jQuery/ux/zoomer.js"></script>
<script src="<%=basePath%>/js/util/PopBox/scripts/PopBox.js" type="text/javascript"></script>
-->
<%@include file="/jsp/common/config.jsp" %>
<title>Insert title here</title>
<!-- Ext上传组件必须css以及js插件 begin -->
<link href="<%=path %>/css/file-upload.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/FileUploadField.js"></script>
<!-- Ext上传组件必须css以及js插件 end -->
<!--
<script type="text/javascript">
	popBoxShowRevertBar = false;
	popBoxShowRevertText = false;
	popBoxShowRevertImage = false;
</script>
-->
<script type="text/javascript" src="<%=path %>/js/util/imgReady.js"></script>
<script type="text/javascript" src="<%=path %>/js/point/exchange/gift.js"></script>
</head>
<body>
<div id="giftmanage_div" style="width:100%;height:100%"></div>
</body>
</html>