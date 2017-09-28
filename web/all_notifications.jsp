<%@ page import="dao.Notification" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--

  Created by IntelliJ IDEA.
  User: Clinton
  Date: 28/9/17
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    ArrayList<Notification> notifications = (ArrayList<Notification>) request.getAttribute("notifications");
    System.out.println(notifications.isEmpty());
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notifications</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navbar.html"%>

<div class="container">
    <h1>My notifications</h1>
    <table class="table">
        <thead>
        <tr>
            <th>Notification</th>
            <th>Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cur" items="${notifications}">
            <tr>
                <td><c:out value="${cur.getUserAction()}"/></td>
                <td><c:out value="${cur.getTime()}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
