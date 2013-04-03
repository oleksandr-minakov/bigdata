<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
<br>
<div style="text-align:center">
	<h2>Welcome to library!</h2>
	Today is <fmt:formatDate value="${today}" pattern="yyyy-MM-dd" />.
	<br>
</div>
</body>
</html>
