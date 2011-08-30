<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/config.jsp" %>

<html>
<head>
<title>用户登录</title>
<script type="text/javascript">
var path = "<%=path%>";
</script>
</head>
<body>
	<form action="<%=path%>/j_spring_security_check" method="post">
		<table width="100%" border="1" cellspacing="0" cellpadding="0">
		    <tr>
		        <td>
		        	用户名：
		        </td>
		        <td>
		        	<input type="text" name="j_username" id="j_username"/>
		        </td>
		    </tr>
		    <tr>
		        <td>
		        	密码：
		        </td>
		        <td>
		        	<input type="password" name="j_password" id="j_password" />
		        </td>
		    </tr>
			<tr>
				<td>
					<input type="radio" name="roleName" value="1">我是管理员</input>
				</td>
				<td>
					<input type="radio" name="roleName" value="2">我是客户</input>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="登录"/>
				</td>
				<td>
					<input type="reset" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>