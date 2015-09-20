<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="models.UserDAO"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="editinfo">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="res/css/form.css" />
<link rel="stylesheet" href="res/css/lib/jquery-ui.css" />
</head>
<body>
	<shiro:guest>
		<c:import url="/error.jsp"></c:import>
	</shiro:guest>
	<shiro:user>
		<script src="res/js/lib/angular.min.js"></script>
		<script src="res/js/lib/jquery-2.1.4.min.js"></script>
		<script src="res/js/lib/jquery-ui.js"></script>
		<script src="res/js/form.js"></script>
		<script src="res/js/editinfo.js"></script>
		<script src="res/js/setter.js"></script>
		<%
			UserDAO dao = new UserDAO();
				User user = dao.getInfo(SecurityUtils.getSubject().getPrincipal().toString());
				request.setAttribute("user", user);
		%>
		<c:import url="WEB-INF/menu.jsp"></c:import>
		<br />
		<form name="myform" id="myform"
			ng-submit="myform.$valid && editinfo();"
			ng-controller="myformController" novalidate>
			<fieldset>
				<legend>Edit User Info</legend>
				<table>
					<tr>
						<td></td>
						<td id="result"></td>
					</tr>
					<shiro:hasRole name="admin">
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
					</shiro:hasRole>
					<tr>
						<td>First Name :</td>
						<td><input type="text" name="firstname" id="firstname"
							class="width-fixed" ng-model="firstname" ng-maxlength="50"
							ng-pattern="/^[a-zA-Z0-9 ]*$/"
							set-value="<c:out value="${user.getInfo(\"firstname\")}"/>"
							required /></td>
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
							set-value="<c:out value="${user.getInfo(\"lastname\")}"/>"
							required /></td>
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
						<td><select name="gender" id="gender" required><option
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
							ng-model="birthdate" legal-age="19"
							set-value="<c:out value="${user.getInfo(\"birthdate\")}"/>"
							required readonly /></td>
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
								<c:out value="${user.getInfo(\"username\")}" />
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
								set-value="<c:out value="${user.getInfo(\"about_me\")}"/>"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><small ng-cloak
							ng-show="myform.about_me.$error.maxlength">Too long.</small></td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align: right"><input type="submit"
							value="Submit"></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</shiro:user>
</body>
<shiro:user>
	<script>
    $(document).ready(
        function() {
          $("#gender").val('<c:out value="${user.getInfo(\"gender\")}" />')
              .change();
          $("#salutation").val(
              '<c:out value="${user.getInfo(\"salutation\")}" />');
        });
  </script>
</shiro:user>
<shiro:hasRole name="admin">
	<script>
    $(document).ready(function() {
      $("#role").val('<c:out value="${user.getInfo(\"role\")}" />');
    });
  </script>
</shiro:hasRole>
</html>
