<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<link rel="stylesheet" href="/res/css/post.css" />
<script src="/res/js/lib/angular.min.js"></script>
<script src="/res/js/post.js"></script>
<div ng-app="post" id="createpost">
	<div style="margin: 10px;">
		<form name="myform" id="myform"
			ng-submit="myform.$valid && createpost()"
			ng-controller="myformController" novalidate>
			<div id="Post">
				<div style="display: inline-block; margin-bottom: 5px;">Create
					Post</div>
				<div id="PostNotification">
					<div id="result"></div>
					<a ng-cloak
						ng-show="(myform.post.$dirty || myform.$submitted) && myform.post.$error.required">
						Required.</a> <a ng-cloak ng-show="myform.post.$error.maxlength">Too
						long ( Max 5000 characters ).</a><a ng-cloak
						ng-show="myform.post.$error.minlength">Too Short ( Min 3
						characters ).</a>
				</div>
				<textarea name="post" ng-model="post" ng-minlength=3
					ng-maxlength=5000 required></textarea>
				<div id="PostSubmit">
					<input type="button" ng-click="myform.$valid && preview()"
						value="Preview"> <input type="submit" value="Submit">
				</div>
			</div>
		</form>
		<div id="PreviewPost" style="display: none">
			Preview
			<div id="PreviewText"></div>
		</div>
		<div>Available Tags: <a href="/postTags.jsp" target="_blank">Here</a></div>
	</div>
</div>