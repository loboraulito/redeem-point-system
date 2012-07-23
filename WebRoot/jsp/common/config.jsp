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
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-extend.css">
<!-- 引用Ext JS 的适配器文件 -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-base.js"></script>
<!-- 引用Ext JS 的框架文件 -->
<!-- <script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-all.js"></script> -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/ext-all-debug.js"></script>
<!-- 引用Ext JS 的语言文件 -->
<script type="text/javascript" src="<%=path %>/js/ext-2.2.1/source/locale/ext-lang-zh_CN.js"></script>


<!-- DWR3.0 RC 消息反转 -->
<!-- 
<script type="text/javascript" src="<%=path%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/interface/messageService.js"></script>
 -->

<!-- 
引用json转换工具，替代Ext中的json的转换工具（因为Ext中的json转换是调用eval，这将会可能导致安全隐患）
但是由于Ext自带有toJSONString方法，而json.js中也存在这样的方法，导致冲突异常,因此需要调用时再加入
-->
<!-- <script type="text/javascript" src="<%=path %>/js/util/json.js"></script> -->
<script type="text/javascript" src="<%=path %>/js/util/utils.js"></script>
<!-- 每个页面上的按钮权限管理 -->
<script type="text/javascript" src="<%=path %>/js/util/buttonRight.js"></script>
<!-- 设置默认图片路径 -->
<SCRIPT type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<%=path %>/js/ext-2.2.1/resources/images/default/s.gif";
</SCRIPT>

<%@page import="org.springframework.security.core.context.SecurityContext"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@page import="org.springframework.security.core.context.SecurityContextImpl"%>
<%@page import="org.springframework.security.web.authentication.WebAuthenticationDetails"%>
<%@page import="java.util.Collection"%>
<%@page import="com.integral.system.user.bean.UserInfo"%>
<%
String userName = "";
String userRole = "";
SecurityContext secCtx = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
String securitySessionId = "";
if(secCtx!=null){
Authentication authentication = secCtx.getAuthentication();
	if(authentication!=null){
	    WebAuthenticationDetails detail = (WebAuthenticationDetails)authentication.getDetails();
	    securitySessionId = detail.getSessionId();
		Object principal = authentication.getPrincipal();
		if(principal instanceof UserDetails){
		    userName = ((UserDetails)principal).getUsername();
		    Collection authorities = ((UserDetails)principal).getAuthorities();
		    userRole = (authorities == null || authorities.size()<1)?"":String.valueOf(authorities.toArray()[0]);
		}else{
		    userName = principal.toString();
		    userRole = String.valueOf(authentication.getAuthorities().toArray()[0]);
		}
	}
}
Object sessionUser = session.getAttribute("user");
UserInfo user = null;
if(sessionUser != null){
    user = (UserInfo)sessionUser;
}
%>

<%
if(user != null){
%>
<SCRIPT type="text/javascript">
	var userId = "<%=user.getUserId()%>";
</SCRIPT>
<%
}
%>

<SCRIPT type="text/javascript">
	var userName = "<%=userName%>";
	var userRole = "<%=userRole%>";
	var path = "<%=path%>";
	var currentMenuId = "";
</SCRIPT>
<SCRIPT type="text/javascript">
Ext.onReady(function(){
	changeXtheme();
	currentMenuId = getCurrentMenuId();
});
</SCRIPT>
<%
//TODO 用于判断用户Session是否超时，超时则要求用户重新登录。暂不实现此功能，应该劲量避免这种情况出现。
//if(session.isNew() && (securitySessionId == null || "null".equals(securitySessionId))){
%>
<!-- 
<script type="text/javascript">
Ext.onReady(function(){
	Ext.Msg.alert("系统提示","您长时间未操作，请重新登录！",function(btn){
		if(btn == "yes" || btn == "ok"){
			window.top.location = path+"/j_spring_security_logout";
		}
	});
});
</script>
 -->
<%
//}
%>
<SCRIPT type="text/javascript">
	//document.write('<iframe name="targetFram" id="targetFram" style="display: none" width="0" height="0"></iframe>');
</SCRIPT>