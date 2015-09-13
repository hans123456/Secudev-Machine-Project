<style>
.menu {
	border-collapse: collapse;
}

.menu td {
	padding: 5px;
}
</style>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="javax.security.auth.Subject"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<shiro:guest>
   	Hi there!  Please <a href="login.jsp">Login</a> or <a
		href="register.jsp">Register</a>
	today! 
</shiro:guest>
<shiro:user>
	<table class="menu" border="1">
		<tr>
			<td><a href="logout">Logout</a></td>
			<td><a
				href="profile.jsp?id=<%=SecurityUtils.getSubject().getPrincipal().toString()%>">View
					Profile</a></td>
			<td><a href="editinfo.jsp">Edit Profile</a></td>
			<td><a href="board.jsp">Board</a></td>
			<shiro:hasRole name="admin">
				<td><a href="secret.jsp">Secret Admin Page Here</a></td>
			</shiro:hasRole>
		</tr>
	</table>
</shiro:user>