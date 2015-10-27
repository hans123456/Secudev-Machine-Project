<style>
.menu {
	border-collapse: collapse;
}

.menu td {
	padding: 5px;
}

.padding {
	margin-bottom: 35px;
}

.floating {
	min-height: 35px;
	width: 100%;
	padding-top: 5px;
	padding-left: 5px;
	top: 0;
	left: 0;
	position: fixed;
	background: white;
	opacity: 1;
	border-bottom: 1px solid black;
	z-index: 999 !important;
}
</style>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="javax.security.auth.Subject"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<shiro:guest>
   	Hi there!  Please <a href="/login.jsp">Login</a> or <a
		href="/register.jsp">Register</a>
	today! <br />
</shiro:guest>
<shiro:user>
	<div class="padding">
		<div class="floating">
			<div style="display: inline-block;">
				<table class="menu" border="1">
					<tr>
						<td><form id="logout" method="post" action="/user/logout">
								<a href="#"
									onclick="document.getElementById('logout').submit();">Logout</a>
							</form></td>
						<td><a
							href="/profile.jsp?id=<%=SecurityUtils.getSubject().getPrincipal().toString()%>">Profile</a></td>
						<td><a href="/user/board.jsp">Board</a></td>
						<td><a href="/user/store.jsp">Store</a></td>
						<shiro:hasRole name="admin">
							<td><a href="/admin/secret.jsp">Admin Registration</a></td>
							<td><a href="/admin/management_console.jsp">Management Console</a></td>
							<td><a href="/admin/backup.jsp">Backups</a></td>
						</shiro:hasRole>
					</tr>
				</table>
			</div>
		</div>
	</div>
</shiro:user>