<%@page import="org.joda.time.format.DateTimeFormat"%>
<%@page import="org.joda.time.LocalDate"%>
<%@page import="models.BoardQueryFactory"%>
<%@page import="models.BoardQuery"%>
<%@page import="utilities.BBCodeProccessor"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="models.PostDAO"%>
<%@page import="models.Post"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="post">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/lib/colorbox.css" />
<link rel="stylesheet" href="/res/css/board.css" />
<style>
.menu {
	border-collapse: collapse;
}

.menu td {
	padding: 5px;
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

li {
	border: 1px solid #000;
	border-bottom-width: 0;
	margin: 0px 0px 0px 3px;
	padding: 5px 5px 2px 5px;
	background-color: #CCC;
	color: #696969;
	display: inline-block;
}

#mainView {
	
}

.active {
	background-color: #FFF;
	color: #000;
}
</style>
</head>
<body>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/board.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<center>
		<H1>The Board</H1>
	</center>
	<%
		int currentPage = 1;
		int recordsPerPage = 10;
		boolean search = false, success = true;
		if (request.getParameter("page") != null) {
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
				//if (currentPage < 1) throw new Exception(); 
			} catch (Exception e) {
				success = false;
			}
		}
		String q = "";
		String query = "";
		if (request.getParameter("q") != null) {
			q = request.getParameter("q");
			query += "&q=" + q;
			if (q != "") search = true;
		}
		List<BoardQuery> queries = new ArrayList<BoardQuery>();
		BoardQuery bq;
		try {
			String[] a = request.getParameterValues("a");
			String[] b = request.getParameterValues("b");
			String[] c = request.getParameterValues("c");
			String[] i = request.getParameterValues("i");
			if (a != null) {
				for (int j = 0, k = a.length, l = 0; j < k; j++) {
					bq = BoardQueryFactory.createQuery(Integer.parseInt(a[j]), Integer.parseInt(b[j]),
							Integer.parseInt(c[j]));
					for (int m = l + bq.getExpectedNoOfParams(); l < m; l++) {
						if (b[j].equals("2")) LocalDate.parse(i[l], DateTimeFormat.forPattern("yyyy-MM-dd"));
						bq.addParams(i[l]);
					}
					queries.add(bq);
					query += bq.getURLParams();
				}
				search = true;
			}
		} catch (Exception e) {
			success = false;
		}
		PostDAO dao = new PostDAO();
		List<Post> posts = dao.getList((currentPage - 1) * recordsPerPage, recordsPerPage, q, queries);
		for (Post p : posts)
			p.setInfo("post", BBCodeProccessor.process(p.getInfo("post")));
		int noOfRecords = dao.getNoOfRecords();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		if (noOfPages < currentPage && currentPage != 1) {
			success = false;
		}
		request.setAttribute("query", query);
		request.setAttribute("search", search);
		request.setAttribute("success", success);
		request.setAttribute("posts", posts);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("noOfPages", noOfPages);
	%>
	<c:if test="${success==false}">
		<div style="color: red">Invalid Search Query.</div>
	</c:if>
	<div id="tabs">
		<div class="post" style="border: 0; margin-bottom: -20px;">
			<ul ng-init="search=true">
				<li ng-class="{active:search==true}" ng-click="search=true">Search</li>
				<c:if test="${search==false && success==true}">
					<li ng-class="{active:search==false}" ng-click="search=false">Create
						Post</li>
				</c:if>
			</ul>
		</div>
		<div id="mainView">
			<div ng-cloak ng-show="search==true">
				<c:import url="/WEB-INF/searchpost.jsp"></c:import>
			</div>
			<c:if test="${search==false && success==true}">
				<div ng-cloak ng-show="search==false">
					<c:import url="/WEB-INF/createpost.jsp"></c:import>
				</div>
			</c:if>
		</div>
	</div>
	<c:if test="${success==true}">
		<c:forEach var="post" items="${posts}">
			<div class="post">
				<div class="postheader">
					<div class="postdate">
						Posted:
						<c:out value='${post.getInfo("datetime_created")}' />
					</div>
					<div class="postid">
						<a href="#">#<c:out value='${post.getInfo("id")}' /></a>
					</div>
				</div>
				<div class="postbody">
					<div class="postuserinfo">
						<a class="userlink"
							href='/profile.jsp?id=${post.getInfo("username")}'> <c:out
								value='${post.getInfo("username")}' /></a>
						<div class="postuserjoineddate">
							Joined Date:
							<c:out value='${post.getInfo("datetime_joined")}' />
						</div>
					</div>
					<c:if test="${post.getInfo(\"deleted\").equals(\"false\") }">
						<c:set var="username"
							value="<%=SecurityUtils.getSubject().getPrincipal()%>" />
						<c:set var="isAdmin"
							value="<%=SecurityUtils.getSubject().hasRole(\"admin\")%>" />
						<c:set var="id" value="${post.getInfo(\"id\")}" />
						<c:if
							test="${post.getInfo(\"username\").equals(username) || isAdmin}">
							<div class="postuseroptions">
								<a class="editlink" href="editpost.jsp?id=${id}">Edit</a> <a
									class="deletelink" href="#"
									onclick="deletePost(${id}); return false;">Delete</a>
							</div>
						</c:if>
						<div class="postcontent">
							<div class="posttext">${post.getInfo("post")}</div>
						</div>
					</c:if>
					<c:if test="${post.getInfo(\"deleted\").equals(\"true\") }">
						<div class="deleted">Deleted</div>
					</c:if>
				</div>
				<c:if
					test="${post.getInfo(\"deleted\").equals(\"false\") && post.getInfo(\"datetime_lastedited\")!=null}">
					<div class="postfooter">
						<div class="postdate">
							Last Edited:
							<c:out value='${post.getInfo("datetime_lastedited")}' />
						</div>
					</div>
				</c:if>
			</div>
		</c:forEach>
		<div class="pagination">
			<div>
				<div class="pagination_n">
					<a
						<c:if test="${currentPage > 1}">href="/user/board.jsp?page=${currentPage - 1}${query}"</c:if>>Previous</a>
				</div>
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<div class="pagination_n">${i}</div>
						</c:when>
						<c:otherwise>
							<div class="pagination_n">
								<a href="/user/board.jsp?page=${i}${query}">${i}</a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<div class="pagination_n">
					<a
						<c:if test="${currentPage lt noOfPages}">href="/user/board.jsp?page=${currentPage + 1}${query}"</c:if>>Next</a>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>