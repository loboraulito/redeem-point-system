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
Object obj = request.getAttribute("struts.valueStack");
ValueStack vs = (ValueStack) obj;
Map o = (Map)vs.findValue("fieldErrors");
ArrayList or = (ArrayList)o.get("giftImage");
error = String.valueOf(or.get(0));
}catch(Exception e){}
%>
{"success":false,"msg":"<%=error %>","error":"<%=error %>"}
</body>
</html>