<%--
  Created by IntelliJ IDEA.
  User: andreang
  Date: 9/26/17
  Time: 6:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.Search" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navbar.html"%>
<%
    ArrayList<Search> result = (ArrayList<Search>) request.getAttribute("result");
%>
<form method="post" action="/add">
<ul class="list-group">
    <c:forEach items="${result}" var="cur">
        <li class="list-group-item"><c:out value="${cur.getName()}"/> <button type = "submit" class="btn-btn-default pull-right" id="right-panel-link" href="#right-panel">+ Add Friend</button> </li>
    </c:forEach>
</ul>
</form>

</body>
</html>
