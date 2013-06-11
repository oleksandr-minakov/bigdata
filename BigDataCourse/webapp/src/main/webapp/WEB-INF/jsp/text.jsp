<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Text of the book</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
<br>
    <pre>
        ${text}
    </pre>

</body>
</html>