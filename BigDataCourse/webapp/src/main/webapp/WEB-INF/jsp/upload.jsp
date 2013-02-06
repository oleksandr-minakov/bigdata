<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>

 <%@ page import="java.util.*" %>

<html>
<head>
<title>Status of upload</title>
</head> 
<body>
<h2>Title</h2>
<p>This is a text.</p>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>

<h1>Current Date: <%= df.format(new java.util.Date()) %> </h1>
</body>
