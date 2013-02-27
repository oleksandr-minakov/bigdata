<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
    <a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="<c:url value="/search"/>">Search </a>
</div>
<br>
<div style="text-align:center">
	<h2>Welcome to library!</h2>
	Today is <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" />.
	<br>
</div>
</body>
</html>
