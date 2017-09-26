<%@ page import="dao.Log" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 26/9/17
  Time: 8:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Log> userlogs = (ArrayList<Log>) request.getAttribute("userlogs");
%>
<html>
<head>
    <title>User log</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<body>
<h1></h1>
<div class="container">
    <h1>Requested user logs</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Username</th>
                <th>Action</th>
                <th>Time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cur" items="${userlogs}">
                <tr>
                    <td><c:out value="${cur.getUsername()}"/></td>
                    <td><c:out value="${cur.getAction()}"/></td>
                    <td><c:out value="${cur.getTime()}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
