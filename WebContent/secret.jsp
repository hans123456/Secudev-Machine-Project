<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="register">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="lib/css/form.css" />
</head>
<body>
	<script src="lib/js/angular.min.js"></script>
	<script src="lib/js/jquery-2.1.4.min.js"></script>
	<script src="lib/js/register.js"></script>
	<shiro:lacksRole name="admin">
		<%@ include file="/error.jsp"%>
	</shiro:lacksRole>
	<shiro:hasRole name="admin">
		<a href="./">Back to Home</a>
		<form name="myform" ng-submit="myform.$valid && register();"
			ng-controller="myformController" novalidate>
			<fieldset style="display: inline-block;">
				<legend>Admin User Registration</legend>
				<table>
					<tr>
						<td></td>
						<td id="result"></td>
					</tr>
					<tr>
						<td>Access Level :</td>
						<td><select name="role" id="role">
								<option value="user">User</option>
								<option value="admin">Admin</option>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<%@ include file="/WEB-INF/create_account.jsp"%>
				</table>
			</fieldset>
		</form>
	</shiro:hasRole>
</body>
</html>