<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<?xml version="1.0" encoding="UTF-8"?>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All books</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
<br>

<div id="findTitle" style="text-align:center;">
	<h2>You can find books here</h2>
</div>
<form name="find" method="GET" accept-charset="UTF-8">
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
        <th>Delete</th>
	</tr>
	<c:forEach items="${books}" var="book">
		<tr>
			<td><a href="<c:url value="/text?titleOfBook=${book.title}"/>">${book.title} </a></td>
			<td>${book.author}</td>
			<td>${book.genre}</td>
			<td>${book.text}</td>
            <td><a href="<c:url value="/delete?deleteBookId=${book.id}"/>">${book.id} </a></td>
		</tr>
	</c:forEach>
</table>
<br>
<div id="pagination" style="background-color:#E6E6E6; text-align:center;">
    <c:if test="${currentPage eq 1}">
        <a href="#" onclick="return false;"> Previous </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>

    <c:if test="${currentPage > 1}">
        <a href="search?pageNum=${currentPage - 1}&findString=${findString}&findBy=${findBy}"> Previous </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>

    ${currentPage} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    <c:if test="${numberOfRecords/10 > currentPage}">
        <a href="search?pageNum=${currentPage + 1}&findString=${findString}&findBy=${findBy}"> Next </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>

    <c:if test="${currentPage >= numberOfRecords/10}">
        <a href="#" onclick="return false;"> Next </a>
    </c:if>

</div>

   <!--
    <c:if test="${currentPage > 1}">
        <td><a href="search?pageNum=${currentPage - 1}&findString=${findString}&findBy=${findBy}">Previous</a></td>
    </c:if>

    <table border="1">
        <tr>
            <td><p>${currentPage}</p></td>

            <c:forEach begin="1" end="${numberOfRecords/10 + 1}" var="i">
                <c:choose>
                    <c:when test="${currentPage < i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="search?pageNum=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </tr>
    </table>

    <c:if test="${numberOfRecords/10 > currentPage}">
        <td><a href="search?pageNum=${currentPage + 1}&findString=${findString}&findBy=${findBy}">Next</a></td>
    </c:if>
    -->
<h2>Number of records: ${numberOfRecords} </h2>
<%--<h2>Number of pages: ${numberOfRecords/10} </h2>--%>
<h2>Current page: ${currentPage} </h2>
<br>
</body>
</html>