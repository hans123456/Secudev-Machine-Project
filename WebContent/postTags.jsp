<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
fieldset {
	display: inline-block;
}

table {
	border-collapse: collapse;
}

table, td {
	border: 1px solid black;
	padding: 5px;
}

form table tr td:nth-child(1) {
	width: 100px;
	text-align: right;
	vertical-align: top;
	font-weight: bold;
}
</style>
<link rel="stylesheet" href="res/css/lib/colorbox.css" />
<link rel="stylesheet" href="res/css/board.css" />
</head>
<body>
	<script src="res/js/lib/jquery-2.1.4.min.js"></script>
	<script src="res/js/lib/jquery.colorbox-min.js"></script>
	<c:import url="WEB-INF/menu.jsp"></c:import>
	<br />
	<fieldset>
		<legend>Legend</legend>
		<table>
			<tr>
				<td>Bold</td>
				<td>[b]value[/b]</td>
				<td><b>value</b></td>
			<tr>
				<td>Italic</td>
				<td>[i]value[/i]</td>
				<td><i>value</i></td>
			</tr>
			<tr>
				<td>Underline</td>
				<td>[u]value[/u]</td>
				<td><u>value</u></td>
			</tr>
			<tr>
				<td>Strikethrough</td>
				<td>[s]value[/s]</td>
				<td><s>value</s></td>
			</tr>
			<tr>
				<td>Text Color</td>
				<td>[color=blue]value[/color]</td>
				<td><span style="color: blue;">value</span></td>
			</tr>
			<tr>
				<td>Text Size (in px)</td>
				<td>[size=20]value[/size]</td>
				<td><span style="font-size: 20px;">value</span></td>
			</tr>
			<tr>
				<td>Insert Link</td>
				<td>[url=#]value[/url]</td>
				<td><a href="#">value</a></td>
			</tr>
			<tr>
				<td>Preformat</td>
				<td>[code]t<br />t&ensp;t[/code]
				</td>
				<td><pre>t
t	t</pre></td>
			</tr>
			<tr>
				<td>Don't Parse</td>
				<td>[noparse][b]value[/b][/noparse]</td>
				<td>[b]value[/b]</td>
			</tr>
			<tr>
				<td>Insert Image</td>
				<td>[img]link[/img]</td>
				<td><a class="colorbox"
					href="/res/images/11224862_10154185918728135_6213573383190973758_n.jpg"><img
						class="board-image"
						src="/res/images/11224862_10154185918728135_6213573383190973758_n.jpg" /></a></td>
			</tr>
			<tr>
				<td>Insert Link</td>
				<td>[quote=test"]value[/quote]</td>
				<td><blockquote class="uncited">
						<div>
							<div class="qauth">
								<img src="/res/images/board/quote_icon.png"> Originally
								Posted by <b>test</b>
							</div>
							<div class="qpost">value</div>
						</div>
					</blockquote></td>
			</tr>
		</table>
	</fieldset>
</body>
<script>
  $(document).ready(function() {
    $('a.colorbox').colorbox({
      photo : true,
      maxWidth : '95%',
      maxHeight : '95%',
    });
  });
</script>
</html>