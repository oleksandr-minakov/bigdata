<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Add book</title>
</head> 
<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/search"/>">Search </a>
</div>
<br>
<body>
<div id="title" style="text-align:center;">
	<h2>You can upload your book here</h2>
</div>
<br>

<form method="post" enctype="multipart/form-data" accept-charset="UTF-8" >

	<table>
		<tr>
			<td>Title</td>
  			<td><label>
                  <input type="text" name="title" value="${title}">
              </label></td>
		</tr>
		
		<tr>
			<td>Author</td>
  			<td><label>
                  <input type="text" name="author" value="${author}">
              </label></td>
		</tr>
		<tr>
			<td>Genre</td>
  			<td><label>
                  <input type="text" name="genre" value="${genre}">
              </label></td>
		</tr>
		<tr>
			<td>Text</td>
  			<td><input type="file" name="file" value="${text}"></td>
		</tr>
	</table>
	<div id = "upload" style="text-align:center;">
			<input type="submit" value="Upload" />
	</div>
</form>
</body>
</html>
