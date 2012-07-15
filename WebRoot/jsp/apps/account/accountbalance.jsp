<%@ page language="java" pageEncoding="UTF-8"%>

<%
//系统路径uri
String path = request.getContextPath();
//系统绝对路径url
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <title>账目管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/ext2.0/resources/css/fileUploadField.css">
	<style type="text/css" rel="stylesheet">
	.comment{text-align:left;margin:0px 3px 5px 3px;border:1px solid #70990f0;background:#fff;border: 1px solid #70990f;}
  	.commentinfo{font-size:11px;color:#999;}
  	.commenttop{padding:4px;background:#DEF2B0;margin:1px;border-bottom: 1px dotted #70990f;}
  	.commentright{padding:4px;background:#DEF2B0;margin:1px;border-right: 1px dotted #70990f;}
  	.commentcontent{padding:10px;line-height:140%;overflow:hidden;width:96%}
	</style>
	<script type="text/javascript">
	var path = "<%=path%>";
	var basePath="<%=basePath%>";
	var userName = window.parent.userName;
	var userid = window.parent.userId;
	var userId = window.parent.userId;
	var roleName=window.parent.roleName;
	var roleId=window.parent.roleId;
	</script>	
	<script type="text/javascript" src="<%=path %>/js/ext2.0/ext-base.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/ext-core.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/ext-all.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/source/widgets/form/FileUploadField.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/source/plugins/GroupSummary.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ext2.0/source/widgets/form/adv-vtypes.js"></script>
	<script type="text/javascript" src="<%=path %>/js/apps/util/util.js"></script>
	<script type="text/javascript" src="<%=path %>/js/apps/dataStore.js"></script>
	<script type="text/javascript" src="<%=path %>/js/apps/accountbalance/acccountBalance.js"></script>
	
	<SCRIPT type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=path %>/js/ext2.0/resources/images/default/s.gif';
	</SCRIPT>
  </head>
  
  <body style="overflow: hidden">
  	<div id="accountdetail" style="width: 100%;height: 100%"></div>
	<iframe id="export2excel" width="0" height="0" style="display: none"></iframe>
  </body>
</html:html>
