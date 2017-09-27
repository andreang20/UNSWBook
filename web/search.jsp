<%--
  Created by IntelliJ IDEA.
  User: andreang
  Date: 9/26/17
  Time: 4:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navbar.html"%>
<div class="container">
    <form class="form-group" action="/result" method="post">
        <input placeholder="Enter name" type="text" name="keyname" class="form-control"/><br>
        <input placeholder="Enter gender" type="text" name="keygender" class="form-control"/><br>
        <input placeholder="Enter date of birth" type="text" name="keyDOB" class="form-control"/><br>
        <button type="submit" class="btn btn-default">Search</button>
    </form>
</div>

</body>
</html>
