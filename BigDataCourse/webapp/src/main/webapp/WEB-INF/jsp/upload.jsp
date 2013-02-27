<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Status of upload</title>
</head> 
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/search"/>">Search </a>
</div>
<br>
<div style="text-align: center;">
<h2>Status: ${message}</h2>
</div>
<br>
<p>Book.title: ${book.title}</p>
<p>Book.author: ${book.author}</p>
<p>Book.genre: ${book.genre}</p>
<p>Book.text: ${book.text}</p>
<p>ID: ${id}</p>
 
</body>
</html>
