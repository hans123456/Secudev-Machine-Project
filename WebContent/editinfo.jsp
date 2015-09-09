<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="models.UserDAO"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="register">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="lib/css/form.css" />
<link rel="stylesheet" href="lib/css/jquery-ui.css" />
</head>
<body>
	<script src="lib/js/angular.min.js"></script>
	<script src="lib/js/jquery-2.1.4.min.js"></script>
	<script src="lib/js/jquery-ui.js"></script>
	<script src="lib/js/register.js"></script>
	<shiro:guest>
		<%@ include file="/error.jsp"%>
	</shiro:guest>
	<shiro:user>
		<%
			UserDAO.getUserInfo(SecurityUtils.getSubject().getPrincipal().toString(), request);
		%>
		<a href="./">Back to Home</a>
		<form name="myform" id="myform"
			ng-submit="myform.$valid && editinfo();"
			ng-controller="myformController" novalidate>
			<fieldset>
				<legend>User Registration</legend>
				<table>
					<tr>
						<td></td>
						<td id="result"></td>
					</tr>
					<tr>
						<td>First Name :</td>
						<td><input type="text" name="firstname" id="firstname"
							class="width-fixed" ng-model="firstname" ng-maxlength="50"
							ng-pattern="/^[a-zA-Z0-9 ]*$/"
							ng-init="firstname='<c:out value="${firstname}" />'" required /></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="(myform.firstname.$dirty || myform.$submitted) && myform.firstname.$error.required">Required.</small>
							<small ng-cloak ng-show="myform.firstname.$error.maxlength">Too
								long.</small> <small ng-cloak ng-show="myform.firstname.$error.pattern">No
								Special Characters.</small></td>
					</tr>
					<tr>
						<td>Last Name :</td>
						<td><input type="text" name="lastname" id="lastname"
							class="width-fixed" ng-model="lastname" ng-maxlength="50"
							ng-pattern="/^[a-zA-Z0-9 ]*$/"
							ng-init="lastname='<c:out value="${lastname}" />'" required /></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="(myform.lastname.$dirty || myform.$submitted) && myform.lastname.$error.required">Required.</small>
							<small ng-cloak ng-show="myform.lastname.$error.maxlength">Too
								long.</small> <small ng-cloak ng-show="myform.lastname.$error.pattern">No
								Special Characters.</small></td>
					</tr>
					<tr>
						<td>Gender :</td>
						<td><select name="gender" id="gender"
							ng-value="<c:out value="${gender}" />" required><option
									value="Male">Male</option>
								<option value="Female">Female</option></select></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Salutation :</td>
						<td><select name="salutation" id="salutation" required></select></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td style="vertical-align: middle">Birthdate :</td>
						<td><input type="text" name="birthdate" id="birthdate"
							ng-model="birthdate" legal-age="19" required readonly /></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="(myform.birthdate.$dirty || myform.$submitted) && myform.birthdate.$error.required">Required.</small>
							<small ng-cloak ng-show="myform.birthdate.$error.date">LOL.</small>
							<small ng-cloak ng-show="myform.birthdate.$error.legalAge">Must
								be above 18 years old.</small></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><div class="display-input">
								<c:out value="${username}" />
							</div></td>
					</tr>
					<tr>
					</tr>
					<tr>
						<td>Password :</td>
						<td><input type="password" name="password" id="password"
							ng-model="password" ng-maxlength="50" ng-pattern="/^\S*$/"
							class="width-fixed" required /></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="(myform.password.$dirty || myform.$submitted) && myform.password.$error.required">Required.</small>
							<small ng-cloak ng-show="myform.password.$error.maxlength">Too
								long.</small><small ng-cloak ng-show="myform.password.$error.pattern">No
								Spaces Allowed.</small></td>
					</tr>
					<tr>
						<td>About Me :</td>
						<td><textarea name="about_me" id="about_me" rows="10"
								style="resize: vertical" class="width-fixed" ng-model="about_me"
								ng-maxlength="1000"
								ng-init="about_me='<c:out value="${about_me}" />'"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="myform.about_me.$error.maxlength">Too long.</small></td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align: right"><input type="reset"
							value="Reset" style="margin-right: 5px" ng-click="reset()"><input
							type="submit" value="Submit"></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<script>
			// $('#birthdate').val(Date.parse('${birthdate}'));
		</script>
	</shiro:user>
</body>
</html>