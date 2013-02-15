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
<form name="find" method="GET">
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

    <c:if test="${currentPage != 1}">
        <td><a href="search?pageNum=${currentPage - 1}">Previous</a></td>
    </c:if>
    
    
    
    <table border="1">
        <tr>
        
        
        <!-- 
            <c:forEach begin="1" end="${numberOfPage}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="search?pageNum=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
         -->
        </tr>
    </table>
 
    <c:if test="${numberOfPages eq 10}">
        <td><a href="search?pageNum=${currentPage + 1}">Next</a></td>
    </c:if>

<br>

<h2>Status: ${numberOfPages} pages.</h2>


</body>
</html>