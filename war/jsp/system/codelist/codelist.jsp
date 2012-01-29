<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/jsp/common/config.jsp" %>
<title>Insert title here</title>
<!-- Ext上传组件必须css以及js插件 begin -->
<link href="<%=path %>/css/file-upload.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/FileUploadField.js"></script>
<!-- Ext上传组件必须css以及js插件 end -->
<!-- base64编码 -->
<script type="text/javascript" src="<%=path %>/js/util/webtoolkit.base64.js"></script>
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/ux/TreeField.js"></script>

</head>
<body >
<div id="codelist_div" style="width:100%; height:100%"></div>
<iframe id="export2excel" style="width:0px; height: 0px; display:none;"></iframe>
<script type="text/javascript" src="<%=path%>/js/point/system/codelist.js"></script>
</body>
</html>