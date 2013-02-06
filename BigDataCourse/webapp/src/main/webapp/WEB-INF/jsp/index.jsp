<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Books database</title>
</head> 

<body>
<div id="title" style="text-align:center;">
	<b>YOU CAN UPLOAD YOUR BOOKS HERE</b>
</div>
<br>



 
<form>
Title:   <input type="text" name="title">
<br>
Author:  <input type="text" name="author">
<br>
Genre:   <input type="text" name="genre">
<br>
</form>
<!-- 
<form action="">
Text:    <input type="file" />
<br>  
</form>
 -->

<form id="upload" action="/webapp/upload.htm" method="post" enctype="multipart/form-data" class="cleanform">
<label for="file">File</label>
<input id="file" type="file" name="file" />
<p><button type="submit">Upload</button></p>
<!-- 
<input type="submit" value="Upload">
 -->
</form> 
 
<br>
<hr>

<div id="findTitle" style="text-align:center;">
	<b>YOU CAN FIND BOOKS HERE</b>
</div>
<form>
Find:   <input type="text" name="find">
	by
	<select name="Find">
	<option value="by title">Title</option>
	<option value="by author">Author</option>
	<option value="by genre">Genre</option>
	<option value="by text">Text</option>
	</select>
</form>
<div id="find" style="text-align:center;">
<form name="find" action="........." method="get">
<input type="submit" value="Search">
</form> 
</div>

</body>
</html>
