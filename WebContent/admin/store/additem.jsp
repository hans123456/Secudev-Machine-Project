<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/form.css" />
</head>
<body>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />

	<form action="/admin/store/additem" method="post">
		<fieldset>
			<legend>Add New Item</legend>
			<table>
				<tr>
					<td></td>
					<td id="result"></td>
				</tr>
				<tr>
					<td>Name :</td>
					<td><input type="text" name="name" id="name"
						class="width-fixed" maxlength="100" required /></td>
				</tr>
				<tr>
					<td>Image :</td>
					<td><input type="text" name="image" class="width-fixed"
						maxlength="100" /></td>
				</tr>
				<tr>
					<td>Price :</td>
					<td><input type="text" name="price" class="width-fixed"
						maxlength="100" required /></td>
				</tr>
				<tr>
					<td>Description :</td>
					<td><textarea name="description" rows="10"
							style="resize: vertical" class="width-fixed" ng-model="about_me"
							ng-maxlength="1000"></textarea></td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: right"><input type="reset"
						value="Reset" style="margin-right: 5px"><input
						type="submit" value="Submit"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>