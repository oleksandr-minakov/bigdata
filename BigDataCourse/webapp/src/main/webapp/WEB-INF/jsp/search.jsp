<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<?xml version="1.0" encoding="UTF-8"?>
<html>
<head>
<title>All books</title>
</head>
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/search"/>">Search </a>
</div>
<br>

<div id="findTitle" style="text-align:center;">
	<h2>You can find books here</h2>
</div>
<form name="find" method="GET">
Find: <label>
    <input type="text" name="findString" value="${findString}">
</label>

    <p>by</p>
    <label>
        <select name="findBy">
            <option>title</option>
            <option>author</option>
            <option>genre</option>
            <option>text</option>
        </select>
    </label>

    <br>
<div id="find" style="text-align:center;">
<input type="submit" value="Search">
</div>
</form> 




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

<h2>Number of records: ${numberOfRecords} </h2>

<br>
</body>
</html>