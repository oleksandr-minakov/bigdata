<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<?xml version="1.0" encoding="UTF-8"?>
<html>
<head>
<title>All books</title>
</head>
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="/webapp/welcome">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="/webapp/addbook">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="/webapp/search">Search </a> 
</div>
<br>

<div id="findTitle" style="text-align:center;">
	<h2>You can find books here</h2>
</div>
<form>
Find:   <input type="text" name="find">
	by
	<select name="by">
	<option value="title">Title</option>
	<option value="author">Author</option>
	<option value="genre">Genre</option>
	<option value="text">Text</option>
	</select>
</form>
<br>
<div id="find" style="text-align:center;">
<form name="find" method="POST">
<input type="submit" value="Search">
</form> 
</div>



<table border="1">
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Genre</th>
		<th>Text</th>
	</tr>
	<c:forEach items="${books}" var="book">
		<tr>
			<td>${book.title}</td>
			<td>${book.author}</td>
			<td>${book.genre}</td>
			<td>${book.text}</td>
		</tr>
	</c:forEach>
</table>
<br>
<br>

</body>
</html>