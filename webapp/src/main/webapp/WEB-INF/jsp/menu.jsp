<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu</title>
</head>
	<div id="header" style="background-color:#E6E6E6; text-align:center;">
		<a href="<c:url value="/welcome"/>">Welcome page </a> &nbsp;&nbsp;&nbsp;
	
		<a href="<c:url value="/addbook"/>">Add book </a> &nbsp;&nbsp;&nbsp;
	
		<a href="<c:url value="/search"/>">Search </a> &nbsp;&nbsp;&nbsp;
	
		<a href="<c:url value="/statistics"/>">Statistics </a> &nbsp;&nbsp;&nbsp;
	</div>
<body/>
</html>