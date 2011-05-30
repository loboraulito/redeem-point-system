<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!-- 引用Ext JS 样式文件 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/js/ext-2.2.1/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/main.css">
<!-- 引用Ext JS 的适配器文件 -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-base.js"></script>
<!-- 引用Ext JS 的框架文件 -->
<!-- <script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-all.js"></script> -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-all-debug.js"></script>
<!-- 引用Ext JS 的语言文件 -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/locale/ext-lang-zh_CN.js"></script>
<!-- 设置默认图片路径 -->
<SCRIPT type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<%=path %>/js/ext-2.2.1/resources/images/default/s.gif";
</SCRIPT>

<%@page import="org.springframework.security.core.context.SecurityContext"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@page import="org.springframework.security.core.context.SecurityContextImpl"%>
<%
String userName = "";
SecurityContext secCtx = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
if(secCtx!=null){
Authentication authentication = secCtx.getAuthentication();
	if(authentication!=null){
		Object principal = authentication.getPrincipal();
		if(principal instanceof UserDetails){
		    userName = ((UserDetails)principal).getUsername();
		}else{
		    userName = principal.toString();
		}
	}
}

%>

<SCRIPT type="text/javascript">
	var userName = "<%=userName%>";
	var path = "<%=path%>";
</SCRIPT>
