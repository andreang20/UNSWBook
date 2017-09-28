<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 27/9/17
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String toBanUsername = request.getParameter("ban_user");
%>
<html>
<head>
    <title>User is banned</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="adminnavbar.html"%>
<h1><%=toBanUsername%> is banned.</h1>
</body>
</html>
