<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="models.UserDAO"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%
	UserDAO.getUserInfo(SecurityUtils.getSubject().getPrincipal().toString(), request);
%>
<link rel="stylesheet" href="lib/css/form.css" />
<body>
	<br />
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
						<c:out value="${firstname}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Last Name :</td>
				<td><div class="display-input">
						<c:out value="${lastname}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Gender :</td>
				<td><div class="display-input">
						<c:out value="${gender}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Salutation :</td>
				<td><div class="display-input">
						<c:out value="${salutation}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Birthdate :</td>
				<td><div class="display-input">
						<c:out value="${birthdate}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Username :</td>
				<td><div class="display-input">
						<c:out value="${username}" />
					</div></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>About Me :</td>
				<td><div class="display-input">
						<c:out value="${about_me}" />
					</div></td>
			</tr>
		</table>
	</fieldset>
</body>