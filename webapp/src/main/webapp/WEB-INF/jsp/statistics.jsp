<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MapReduce Words Frequency Statistics</title>
</head>
<body>
	<jsp:include page="menu.jsp"/>
<br/><br/><br/><br/><br/><br/>

<div id="header" style="background-color:#E6E6E6; text-align:center;">
	<a href="<c:url value="/statview"/>">View Calculated Statistics </a> &nbsp;&nbsp;&nbsp;
	<br/><br/><br/>
	<c:choose>
		<c:when test="${instanceof_flag == true && flag == true}">
			<a href="<c:url value="/recalculate"/>">Calculated New Statistics </a> &nbsp;&nbsp;&nbsp;
		</c:when>
		<c:when test="${instanceof_flag == true && flag == false}">
			<div align="center">
    			<h3>Status: ${avaibility}</h3>
			</div>
		</c:when>
 		<c:when test="${instanceof_flag == false && flag == true}">
			<div align="center">
    			<h3>Status: ${instance}</h3>
			</div>
		</c:when>
	</c:choose>
</div>

</body>
</html>