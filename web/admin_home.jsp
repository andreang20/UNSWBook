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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<body>
    <form action="/admin/search" method="post">
        <div class="form-group">
            <label for="user_search">Search for user:</label>
            <input type="text" class="form-control" id="user_search" name="user_search" placeholder="username">
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">search</button>
    </form>


</body>
</html>
