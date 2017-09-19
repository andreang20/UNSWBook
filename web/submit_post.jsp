<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 19/9/17
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit post</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<body>
    <div id="login_div" class="container">
        <form>
            <div class="form-group">
                <label for="content">Content: </label>
                <input type="text" class="form-control" id="content">
            </div>
            <div class="form-group">
                <label for="img">Image: </label>
                <input type="file" class="form-control" id="img">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</body>
</html>
