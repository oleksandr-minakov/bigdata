<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="/webapp/welcome">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
	<a href="/webapp/addbook">Add book </a> &nbsp;&nbsp;&nbsp;
	
	<a href="/webapp/search">Search </a> 
</div>
<br>
<div style="text-align:center">
	<h2>Welcome to library</h2>
	Today is <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" />.
	<br>
</div>
</body>
</html>