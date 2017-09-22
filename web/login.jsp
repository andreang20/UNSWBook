<%--
  Created by IntelliJ IDEA.
  User: mnema
  Date: 21/09/2017
  Time: 11:25 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <style>
        .reg-div-main {
            width: 30%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 100px;
            padding: 20px 5px;
            background-color: aliceblue;
            border: 1px solid grey;
            border-radius: 20px;
        }
    </style>
</head>
<body>
    <div class="reg-div-main">
        <div id="login_div" class="container">
            <form action="/login" method="post">

                <div class="form-group">
                    <label>UserName: </label>
                    <input type="text" class="form-control" id="username" name="username">
                    <label>Password: </label>
                    <input type="password" class="form-control" id="password" name="password">

                </div>
                <button type="submit" class="btn btn-primary">Sign in</button>
            </form>
        </div>
    </div>

</body>
</html>
