<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.UserProfile" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 26/9/17
  Time: 5:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    // get results
    ArrayList<UserProfile> search_results =(ArrayList<UserProfile>) request.getAttribute("search_results");
%>

<head>
    <title>Search results</title>
    <style>
        .panel {
            margin-top: 20px;
            margin-bottom: 20px;
            background-color: gainsboro;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<body>
    <h1>Your search results are:</h1><br>
    <div class="container">
        <c:forEach var="cur" items="${search_results}">
            <div class="panel panel-default">
                Username: <c:out value="${cur.getUsername()}"/><br>
                First name: <c:out value="${cur.getFirstname()}"/><br>
                Last name: <c:out value="${cur.getLastname()}"/><br>
                Email: <c:out value="${cur.getEmail()}"/><br>
                Gender: <c:out value="${cur.getGender()}"/><br>
                Date of birth: <c:out value="${cur.getFormattedDate()}"/><br>
                is_banned: <c:out value="${cur.getIs_banned()}"/><br>
                <form action="/admin/userlog" method="get">
                    <input type="hidden" name="log_username" value="${cur.getUsername()}"/>
                    <button type="submit">See user log</button>
                </form>
                <form action="/admin/ban" method="get">
                    <input type="hidden" name="ban_user" value="${cur.getUsername()}">
                    <button type="submit">Ban user</button>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>
