<%@page import="models.StoreItem"%>
<%@page import="models.StoreDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	try {
		String id = request.getParameter("itemId");
		StoreDAO dao = new StoreDAO();
		StoreItem item = dao.getItem(Integer.parseInt(id));
		request.setAttribute("item", item);
	} catch (Exception e) {
	}
%>
<html ng-app="store">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/form.css" />
<link rel="stylesheet" href="/res/css/store.css" />
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
</head>
<body>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<script src="/res/js/store.js"></script>
	<script src="/res/js/setter.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />

	<table class="admin" border="1">
		<tr>
			<td><form id="deleteitem" method="post"
					action="/admin/store/deleteitem">
					<input class="ng-hide" name="id" value='${item.getInfo("id")}' />
					<a href="#"
						onclick="document.getElementById('deleteitem').submit();">Delete
						Item</a>
				</form></td>
		</tr>
	</table>

	<form action="/admin/store/edititem" method="post">
		<input class="ng-hide" value='${item.getInfo("id")}' name="id" />
		<fieldset>
			<legend>Edit Item</legend>
			<table>
				<tr>
					<td></td>
					<td id="result"></td>
				</tr>
				<tr>
					<td>Name :</td>
					<td><input type="text" name="name" id="name"
						class="width-fixed" maxlength="100"
						set-value='${item.getInfo("name")}' ng-model="name" required /></td>
				</tr>
				<tr>
					<td>Image :</td>
					<td><input type="text" name="image" class="width-fixed"
						maxlength="100" set-value='${item.getInfo("image")}'
						ng-model="image" /></td>
				</tr>
				<tr>
					<td>Price :</td>
					<td><input type="text" name="price" class="width-fixed"
						maxlength="10" set-value='${item.getInfo("price")}'
						ng-model="price" required /></td>
				</tr>
				<tr>
					<td>Description :</td>
					<td><textarea name="description" rows="10"
							style="resize: vertical" class="width-fixed"
							set-value='${item.getInfo("description")}' ng-model="description"
							maxlength="1000"></textarea></td>
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