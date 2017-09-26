<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 26/9/17
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        .admin-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .admin-signin .admin-signin-heading,
        .admin-signin .checkbox {
            margin-bottom: 10px;
        }
        .admin-signin .checkbox {
            font-weight: normal;
        }
        .admin-signin .form-control {
            position: relative;
            height: auto;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            padding: 10px;
            font-size: 16px;
        }
        .admin-signin .form-control:focus {
            z-index: 2;
        }
        .admin-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .admin-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body>
    <div class="container">

    <form class="admin-signin" action="/admin" method="post">
        <h2 class="admin-signin-heading">Sign in :)</h2>
        <label for="adminUsername" class="sr-only">Admin username</label>
        <input type="text" id="adminUsername" class="form-control" placeholder="Admin username" name="adminUsername" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="inputPassword" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</body>
</html>
