<%@page import="models.PostDAO"%>
<%@page import="models.CartDAO"%>
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
		CartDAO cartDAO = new CartDAO();
		PostDAO postDAO = new PostDAO();
		String username = request.getParameter("id");
		User user = dao.getInfo(username);
		String totalDonation = cartDAO.getTotalDonation(username);
		String totalPurchase = cartDAO.getTotalPurchase(username);
		String totalPost = postDAO.getTotalPost(username);
		request.setAttribute("user", user);
		request.setAttribute("isUser",
				user.getInfo("username").equals(SecurityUtils.getSubject().getPrincipal()));
		request.setAttribute("totalDonation", totalDonation);
		request.setAttribute("totalPurchase", totalPurchase);
		request.setAttribute("totalPost", totalPost);
	} catch (Exception e) {
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/form.css" />
</head>
<body>
	<script src="/res/js/checkSession.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
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
				<tr>
					<td>Badges :</td>
					<td><div class="display-input">
							<c:if test="${totalPost>=3}">Participant Badge<br />
							</c:if>
							<c:if test="${totalPost>=5}">Chatter Badge<br />
							</c:if>
							<c:if test="${totalPost>=10}">Socialite Badge<br />
							</c:if>
							<c:if test="${totalDonation>=5}">Supporter Badge<br />
							</c:if>
							<c:if test="${totalDonation>=20}">Contributor Badge<br />
							</c:if>
							<c:if test="${totalDonation>=100}">Pillar Badge<br />
							</c:if>
							<c:if test="${totalPurchase>=5}">Shopper Badge<br />
							</c:if>
							<c:if test="${totalPurchase>=20}">Promoter Badge<br />
							</c:if>
							<c:if test="${totalPurchase>=100}">Evangelist Badge<br />
							</c:if>
							<c:if
								test="${totalPost>=3 && totalDonation>=5 && totalPurchase>=5}">Explorer Badge<br />
							</c:if>
							<c:if
								test="${totalPost>=5 && totalDonation>=20 && totalPurchase>=20}">Explorer Badge<br />
							</c:if>
							<c:if
								test="${totalPost>=10 && totalDonation>=100 && totalPurchase>=100}">Explorer Badge<br />
							</c:if>
						</div></td>
				</tr>
			</table>
		</fieldset>
	</c:if>
	<c:if test="${isUser}">
		<div>
			<br /> <a href="/user/editinfo.jsp">Edit Profile</a>
		</div>
	</c:if>
</body>
</html>
