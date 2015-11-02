<%@page import="models.ManagementQueryFactory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.ManagementQuery"%>
<%@page import="models.CartTransaction"%>
<%@page import="java.util.List"%>
<%@page import="models.ManagementDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ManagementDAO dao = new ManagementDAO();
	List<ManagementQuery> queries = new ArrayList<ManagementQuery>();
	String[] i = request.getParameterValues("i");
	if (i != null && i.length > 1) {
		if (!i[0].equals("") && !i[1].equals("")) {
			ManagementQuery mq = null;
			try {
				mq = ManagementQueryFactory.createQuery(0, 2, 4);
				for (int j = 0; j < 2; j++)
					mq.addParams(i[j]);
				queries.add(mq);
			} catch (Exception e) {

			}
		}
	}
	List<CartTransaction> transactions = dao.getList(queries);
	request.setAttribute("transactions", transactions);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/lib/jquery-ui.css" />
</head>
<style>
.wrapper {
	text-align: center;
	width: 96%;
	border: 1px solid black;
	margin: 0 auto;
	margin-bottom: 10px;
}

.row {
	text-align: center;
	border-top: 1px solid black;
}

.date {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 18%;
	min-width: 18%;
	width: 18%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
	border-right: 1px solid black;
}

.username {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 18%;
	min-width: 18%;
	width: 18%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
	border-right: 1px solid black;
}

.firstname {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 18%;
	min-width: 18%;
	width: 18%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
	border-right: 1px solid black;
}

.lastname {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 18%;
	min-width: 18%;
	width: 18%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
	border-right: 1px solid black;
}

.amount {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 10%;
	min-width: 10%;
	width: 10%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
	border-right: 1px solid black;
}

.status {
	vertical-align: middle;
	white-space: nowrap;
	text-overflow: ellipsis;
	-ms-text-overflow: ellipsis;
	overflow: hidden;
	max-width: 14%;
	min-width: 14%;
	width: 14%;
	display: inline-block;
	text-align: center;
	padding-top: 3px;
	padding-bottom: 3px;
}
</style>
<body>
	<c:import url="/WEB-INF/menu.jsp"></c:import>
	<script src="/res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="/res/js/lib/jquery-ui.js"></script>
	<script src="/res/js/lib/angular.min.js"></script>
	<script src="/res/js/lib/jquery.colorbox-min.js"></script>
	<script src="/res/js/checkSession.js"></script>
	<br />
	<h1 style="text-align: center;">Transactions</h1>
	<div class="wrapper" style="border: 0; text-align: right;">
		<div id="result" style="display: inline-block; margin-top: 10px;"></div>
	</div>
	<div class="wrapper" style="border: 0; text-align: left;">
		<form action="" method="get">
			Between <input type="text" id="date1" name="i" readonly /> and <input
				type="text" id="date2" name="i" readonly /> <input type="submit"
				value="Search" />
		</form>
	</div>
	<div class="wrapper">
		<div style="background-color: #E4E4E4;">
			<div class="date">Date</div>
			<div class="username">Username</div>
			<div class="firstname">Firstname</div>
			<div class="lastname">Lastname</div>
			<div class="amount">Amount</div>
			<div class="status">Status</div>
		</div>
		<c:forEach var="item" items="${transactions}">
			<div class="row">
				<div class="date">
					<a href='/admin/view_cart.jsp?id=${item.getInfo("store_cart_id")}'>${item.getInfo("datetime_updated")}</a>
				</div>
				<div class="username">
					<a href='/profile.jsp?id=${item.getInfo("username")}'>${item.getInfo("username")}</a>
				</div>
				<div class="firstname">${item.getInfo("firstname")}</div>
				<div class="lastname">${item.getInfo("lastname")}</div>
				<div class="amount">$${item.getInfo("price")}</div>
				<div class="status">${item.getInfo("status")}</div>
			</div>
		</c:forEach>
	</div>
</body>
<script>
  $('#date1').datepicker({
    dateFormat : 'yy-mm-dd',
    yearRange : "-100:+0",
    changeMonth : true,
    changeYear : true,
    showOtherMonths : true,
  });
  $('#date2').datepicker({
    dateFormat : 'yy-mm-dd',
    yearRange : "-100:+0",
    changeMonth : true,
    changeYear : true,
    showOtherMonths : true,
  });
</script>
</html>