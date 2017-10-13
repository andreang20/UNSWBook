<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 26/9/17
  Time: 4:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script></head>
<body>
<%@include file="adminnavbar.html"%>
    <form action="/admin/search" method="post">
        <div class="form-group">
            <label for="user_search">Search for user:</label>
            <input type="text" class="form-control" id="user_search" name="user_search" placeholder="username">
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">search</button>
    </form>

    <h1>Graph stuff</h1>
<a href="/graph_form.jsp"> Search or view all graph </a>
</body>
</html>
