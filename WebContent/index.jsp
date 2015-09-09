<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<shiro:guest>
    	Hi there!  Please <a href="login.jsp">Login</a> or <a
			href="register.jsp">Register</a> today!	
	</shiro:guest>
	<shiro:user>
		<a href="logout">Logout</a>
		<a href="editinfo.jsp">Edit Info</a>
		<shiro:hasRole name="admin">
			<a href="secret.jsp">Secret Admin Page Here</a>
		</shiro:hasRole>
		<%@ include file="/WEB-INF/account.jsp"%>
	</shiro:user>
</body>
</html>