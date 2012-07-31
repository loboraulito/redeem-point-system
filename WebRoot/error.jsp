<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/jsp/common/config.jsp" %>
<title>Insert title here</title>

</head>

<body>
<%
String error = "";
try{
	Object strutsStack = request.getAttribute("struts.valueStack");
	ValueStack vs = (ValueStack) strutsStack;
	/*
	ArrayList or1 = (ArrayList)vs.findValue("actionErrors");
	if(or1!=null && or1.size()>0)
	System.out.println("========================="+or1.get(0)+"==========================");
	*/
	Map o = (Map)vs.findValue("fieldErrors");
	if(o != null && o.size() > 0){
		ArrayList or = (ArrayList)o.get("files");
		if(or != null && or.size() > 0){
			error = String.valueOf(or.get(0));
		}
	}
}catch(Exception e){
    error = e.getMessage();
	e.printStackTrace();
}
%>
{"success":false,"msg":"<%=error %>","error":"<%=error %>"}
</body>
</html>