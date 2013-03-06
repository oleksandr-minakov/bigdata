<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Text of the book</title>
</head>
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
    <a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;

    <a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;

    <a href="<c:url value="/search"/>">Search </a>
</div>
<br>
    ${text}
</body>
</html>