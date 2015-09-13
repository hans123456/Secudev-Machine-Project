<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="models.UserDAO"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%
	try {
		UserDAO dao = new UserDAO();
		User user = dao.getInfo(request.getParameter("id"));
		request.setAttribute("user", user);
	} catch (Exception e) {
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="res/css/form.css" />
</head>
<body>
	<c:import url="WEB-INF/menu.jsp"></c:import>
	<br />
	<c:if test="${user==null}">
		<font color="red" style="margin-left: 10px;">User Does Not
			Exist.</font>
	</c:if>
	<c:if test="${user!=null}">
		<fieldset>
			<legend>User Information</legend>
			<table>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>First Name :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("firstname")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Last Name :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("lastname")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Gender :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("gender")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Salutation :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("salutation")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Birthdate :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("birthdate")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Username :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("username")}' />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>About Me :</td>
					<td><div class="display-input">
							<c:out value='${user.getInfo("about_me")}' />
						</div></td>
				</tr>
			</table>
		</fieldset>
	</c:if>
</body>
</html>
