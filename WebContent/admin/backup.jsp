<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
.wrapper {
	width: 96%;
	border: 1px solid black;
	margin: 0 auto;
	margin-bottom: 10px;
}

.row {
	border-top: 1px solid black;
}

.title {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 70%;
	min-width: 70%;
	width: 70%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
}

.link {
	vertical-align: middle;
	text-align: center;
	max-width: 28%;
	min-width: 28%;
	width: 29%;
	display: inline-block;
	padding-top: 3px;
	padding-bottom: 3px;
	border-left: 1px solid black;
}
</style>
<%
	File folder = new File(System.getProperty("catalina.home") + "/secudev/backup/post");
	if (!folder.exists())
		folder.mkdirs();
	File[] files = folder.listFiles();
	String[][] filesLink = new String[files.length][2];
	for (int i = files.length - 1, j = 0; i >= 0; i--) {
		if (files[i].isFile()) {
			filesLink[j][0] = files[i].getName();
			filesLink[j][1] = files[i].getName();
			j++;
		}
	}
	request.setAttribute("files", filesLink);
%>
<body ng-app="backup">
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<br />
	<h1 style="text-align: center;">The Backups</h1>
	<div class="wrapper" style="border: 0; text-align: right;">
		<form ng-controller="backupcontroller" ng-submit="backup()">
			<input type="submit" value="Do Backup" />
		</form>
		<div id="result" style="display: inline-block; margin-top: 10px;"></div>
	</div>
	<div class="wrapper">
		<div style="background-color: #E4E4E4;">
			<div class="title">File Name</div>
			<div class="link">Links</div>
		</div>
		<c:forEach var="item" items="${files}">
			<div class="row">
				<div class="title">${item[0]}</div>
				<div class="link">
					<a href="/admin/backup/post/${item[1]}">Link</a>
				</div>
			</div>
		</c:forEach>
	</div>
	<div style="margin-bottom: 40px;"></div>
</body>
<script>
	(function() {
		var app = angular.module('backup', []);
		app.controller('backupcontroller', [ '$scope', function($scope) {
			$scope.backup = function() {
				$('#result').html("");
				$.post('/admin/backupposts').done(function(res) {
					$('#result').html(res);
				});
			}
		} ]);
	})();
</script>
</html>