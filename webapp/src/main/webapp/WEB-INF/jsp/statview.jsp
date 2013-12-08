<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>View</title>
</head> 
<jsp:include page="menu.jsp"/>
<br/><br/>
<body>
	<table border="1">
	<tr>
		<th>Word</th>
		<th>Frequency</th>
	</tr>
	<c:forEach items="${pairs}" var="pair">
		<tr>
			<td>${pair.word}</td>
			<td>${pair.count}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>