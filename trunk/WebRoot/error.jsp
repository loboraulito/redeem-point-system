<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/jsp/common/config.jsp" %>
<title>Insert title here</title>
<script type="text/javascript">
var fileUploadErrorMsg = "<s:fielderror/>";
if(fileUploadErrorMsg){
	fileUploadErrorMsg = fileUploadErrorMsg.trim();
}
</script>
</head>

<body>
{"success":false,"msg":"<s:fielderror/>","error":"<s:actionerror/>"}
</body>
</html>