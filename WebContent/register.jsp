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
	<shiro:user>
		<c:import url="/error.jsp"></c:import>
	</shiro:user>
	<shiro:guest>
		<script src="/res/js/lib/angular.min.js"></script>
		<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
		<script src="/res/js/lib/jquery-ui.js"></script>
		<script src="/res/js/form.js"></script>
		<script src="/res/js/registration.js"></script>
		<a href="/">Back to Home</a>
		<form name="myform" id="myform"
			ng-submit="myform.$valid && register();"
			ng-controller="myformController" novalidate>
			<fieldset>
				<legend>User Registration</legend>
				<table>
					<tr>
						<td></td>
						<td id="result"></td>
					</tr>
					<%@ include file="/WEB-INF/create_account.jsp"%>
				</table>
			</fieldset>
		</form>
	</shiro:guest>
</body>
</html>