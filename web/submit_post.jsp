<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 19/9/17
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (request.getSession().getAttribute("username") == null) {
        //response.sendRedirect("login.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit post</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <style>

    </style>
</head>
<body>
    <div id="login_div" class="container">
        <form action="/create_post" method="post" enctype="multipart/form-data">
            <div class="form-group ">
                <label for="content">Content: </label>
                <textarea name="content" id="content" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <label for="file">Image: </label>
                <input type="file" class="form-control" id="file" name="file">
            </div>
            <div class="form-group ">
                <label for="change_first_name">First name: </label>
                <input type="text" name="change_first_name" id="change_first_name" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</body>
</html>
