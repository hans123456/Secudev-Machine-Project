<%@page import="models.Post"%>
<%@page import="models.exceptions.SecurityBreachException"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="models.PostDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="post">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
<link rel="stylesheet" href="/res/css/board.css" />
<link rel="stylesheet" href="/res/css/post.css" />
</head>
<body>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/post.js"></script>
	<script src="/res/js/setter.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<%
		try {
			int id = -1;
			id = Integer.parseInt(request.getParameter("id"));
			Subject currentUser = SecurityUtils.getSubject();
			PostDAO dao = new PostDAO();
			if (!(dao.checkIfCreator(id, currentUser.getPrincipal().toString()) || currentUser.hasRole("admin")))
				throw new SecurityBreachException();
			Post post = dao.getInfo(id);
			if (post.getInfo("deleted").equals("true")) throw new SecurityBreachException();
			request.setAttribute("id", id);
			request.setAttribute("post", post);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	%>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />
	<fieldset>
		<legend>Edit Post</legend>
		<form name="myform" id="myform"
			ng-submit="myform.$valid && editpost()"
			ng-controller="myformController" novalidate>
			<div id="Post">
				<div>
					<div id="PostNotification">
						<div id="result"></div>
						<a ng-cloak
							ng-show="(myform.post.$dirty || myform.$submitted) && myform.post.$error.required">
							Required.</a> <a ng-cloak ng-show="myform.post.$error.maxlength">Too
							long ( Max 5000 characters ).</a><a ng-cloak
							ng-show="myform.post.$error.minlength">Too Short ( Min 3
							characters ).</a>
					</div>
					<input name="id" ng-cloak ng-show="false" value="${id}"></input>
					<textarea name="post" ng-model="post" ng-minlength=3
						ng-maxlength=5000
						set-value="<c:out value="${post.getInfo(\"post\")}"/>" required></textarea>
				</div>
				<div id="PostSubmit">
					<input type="button" ng-click="myform.$valid && preview()"
						value="Preview"> <input type="submit" value="Submit">
				</div>
			</div>
		</form>
		<div id="PreviewPost">
			Preview
			<div id="PreviewText"></div>
		</div>
	</fieldset>
</body>
</html>