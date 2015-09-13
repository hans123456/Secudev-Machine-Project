<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="res/css/form.css" />
</head>
<body>
	<script src="res/js/lib/jquery-2.1.4.min.js"></script>
	<shiro:user>
		<c:import url="/error.jsp"></c:import>
	</shiro:user>
	<shiro:guest>
		<a href="./">Back to Home</a>
		<form name="myform" action="" method="post">
			<fieldset>
				<legend>Login</legend>
				<table>
					<tr>
						<td></td>
						<td id="result"><c:if test="${shiroLoginFailure != null}">
								Incorrect Username or Password.
							</c:if></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="username" id="username"
							class="width-fixed" /></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Password :</td>
						<td><input type="password" name="password" id="password"
							class="width-fixed" /></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align: right"><input type="reset"
							value="Reset" style="margin-right: 5px" /><input type="submit"
							value="Login" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</shiro:guest>
</body>
</html>