<%@page import="models.CartDAO"%>
<%@page import="models.CartItem"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	CartDAO dao = new CartDAO();
	List<CartItem> items = dao
			.getCart(dao.getLatestCartId(SecurityUtils.getSubject().getPrincipal().toString()));
	request.setAttribute("items", items);
%>
<html ng-app="store">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/store.css" />
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
</head>
<body>
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
			<td><form id="checkout" method="post"
					action="/user/cart/checkout">
					<a href="#" onclick="document.getElementById('checkout').submit();">Checkout</a>
				</form></td>
			<td><a href="/user/store.jsp">Store</a></td>
		</tr>
	</table>
	<c:if test="${status!=null}">
		<h3>Payment was ${status}.</h3>
	</c:if>
	<c:forEach var="item" items="${items}">
		<div class="item">
			<div class="iitem">
				<form action="/user/cart/editcartitem" method="post">
					<div>${item.getInfo("name")}</div>
					<input class="ng-hide" set-value='${item.getInfo("price")}'
						ng-model='price${item.getInfo("itemId")}' />
					<div>${item.getInfo("price")}</div>
					<input class="ng-hide" value='${item.getInfo("itemId")}'
						name="itemId" />
					<div>
						<input class="quantity" name="quantity"
							set-value='${item.getInfo("quantity")}'
							ng-model='quantity${item.getInfo("itemId")}' />
					</div>
					<div>{{price${item.getInfo("itemId")}*quantity${item.getInfo("itemId")}}}</div>
					<input type="submit">
				</form>
			</div>
		</div>
	</c:forEach>
</body>
</html>