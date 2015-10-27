<%@page import="models.CartItem"%>
<%@page import="java.util.List"%>
<%@page import="models.CartDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String cartId = request.getParameter("id");
	CartDAO dao = new CartDAO();
	List<CartItem> items = dao.getCart(cartId);
	request.setAttribute("items", items);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<br />
	<table>
		<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.getInfo("name")}</td>
				<td>${item.getInfo("quantity")}</td>
				<td>${item.getInfo("price")}</td>
				<td>${item.getInfo("quantity")*item.getInfo("price")}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>