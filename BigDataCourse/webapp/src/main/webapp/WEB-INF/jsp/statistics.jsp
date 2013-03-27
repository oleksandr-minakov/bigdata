<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MapReduce Statistics</title>
</head>
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