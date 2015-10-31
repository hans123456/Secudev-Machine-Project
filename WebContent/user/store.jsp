<%@page import="models.StoreItem"%>
<%@page import="java.util.List"%>
<%@page import="models.StoreDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	StoreDAO dao = new StoreDAO();
	List<StoreItem> items = dao.getItemList();
	request.setAttribute("items", items);
%>
<html ng-app="store">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/store.css" />
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
</head>
<body ng-controller="myformController">
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/store.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<script src="/res/js/setter.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />
	<table class="admin" border="1">
		<tr>
			<shiro:hasRole name="admin">
				<td><a href="/admin/store/additem.jsp">Add New Item</a></td>
			</shiro:hasRole>
			<td><a href="/user/mycart.jsp">My Cart</a></td>
		</tr>
	</table>
	<c:forEach var="item" items="${items}">
		<div class="item">
			<div class="iitem">
				<shiro:hasRole name="admin">
					<a href="/admin/store/edititem.jsp?itemId=${item.getInfo("id")}">Edit
						Item</a>
				</shiro:hasRole>
				<form action="/user/cart/editcartitem" method="post">
					<div>Item #${item.getInfo("id")}</div>
					<div>
						<c:if test='${item.getInfo("image").equals("")}'>No Image</c:if>
						<c:if test='${!item.getInfo("image").equals("")}'>
							<a href='${item.getInfo("image")}' target="_blank">Image</a>
						</c:if>
					</div>
					<div>${item.getInfo("name")}</div>
					<div>${item.getInfo("description")}</div>
					<input class="ng-hide" set-value='${item.getInfo("price")}'
						ng-model='price${item.getInfo("id")}' />
					<div>${item.getInfo("price")}</div>
					<input class="ng-hide" value='${item.getInfo("id")}' name="itemId" />
					<div>
						<input class="quantity" name="quantity" set-value="1"
							ng-model='quantity${item.getInfo("id")}' />
					</div>
					<div>{{price${item.getInfo("id")}*quantity${item.getInfo("id")}}}</div>
					<input type="submit">
					<c:if test='${item.getInfo("donation").equals("true")}'>
						<div>Is Donation</div>
					</c:if>
				</form>
			</div>
		</div>
	</c:forEach>
</body>
</html>