<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="register">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/lib/jquery-ui.css" />
<link rel="stylesheet" href="/res/css/form.css" />
</head>
<body>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/jquery-ui.js"></script>
	<script src="/res/js/form.js"></script>
	<script src="/res/js/registration.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />
	<form id="myform" name="myform"
		ng-submit="myform.$valid && register();"
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
				<c:import url="/WEB-INF/create_account.jsp"></c:import>
			</table>
		</fieldset>
	</form>
</body>
</html>