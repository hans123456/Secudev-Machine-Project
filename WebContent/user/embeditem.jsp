<%@page import="models.StoreItem"%>
<%@page import="models.StoreDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/res/css/store.css" />
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
<%
	StoreDAO dao = new StoreDAO();
	Integer id = null;
	StoreItem item = null;
	try {
		id = Integer.parseInt(request.getParameter("id"));
		item = dao.getItem(id);
		request.setAttribute("id", id);
		request.setAttribute("item", item);
	} catch (Exception e) {

	}
%>
<c:if test="${item!=null}">
	<div class="item" style="display: inline-block; margin-top: 0;">
		<div class="iitem">
			<form action="/user/cart/editcartitem" method="post">
				<div>${item.getInfo("name")}</div>
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
</c:if>
<c:if test="${item==null && id!=null}">[item]${id}[/item]</c:if>