<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<link rel="stylesheet" href="/res/css/post.css" />
<link rel="stylesheet" href="/res/css/lib/jquery-ui.css" />
<script src="/res/js/lib/jquery-ui.js"></script>
<script src="/res/js/searchpost.js"></script>
<style>
.row {
	margin-top: 5px;
}
</style>
<div id="searchpost">
	<div style="margin: 10px;">
		<form ng-controller="searchformcontroller" name="searchform"
			id="searchform" method="get" action="/user/board.jsp">
			<div id="Post">
				<div style="display: inline-block; margin-bottom: 5px;">Search</div>
				<div>
					Post With : <input type="text" name="q" /> <input type="submit" />
				</div>
				<div style="margin-top: 5px;">
					Advance Search <input type="button" ng-click="addAdvance()"
						value="+">
					<div id="advance"></div>
				</div>
			</div>
		</form>
	</div>
</div>