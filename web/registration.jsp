<%--
  Created by IntelliJ IDEA.
  User: mnema
  Date: 21/09/2017
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="db.DbManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.UserProfileDao" %>
<%@ include file="navbar.html"%>

<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <style>
        .reg-div-main {
            width: 30%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 100px;
            padding: 20px 5px;
            background-color: aliceblue;
        }
    </style>
</head>
<body>
    <div class="reg-div-main">
        <div id="login_div" class="container">
            <form action="/registration.jsp" method="post">
                <div class="form-group">
                    <label>FirstName: </label>
                    <input type="text" class="form-control" id="firstname" name="firstname">
                    <label>LastName: </label>
                    <input type="text" class="form-control" id="lastname" name="lastname">
                    <label>Email: </label>
                    <input type="email" class="form-control" id="email" name="email">
                    <label>Gender: </label>
                    <select class="form-control" id="gender" name="gender">
                        <option>male</option>
                        <option>female</option>
                        <!--<option>Other</option>-->
                    </select>
                    <label>Date of Birth: </label>
                    <input type="date" class="form-control" id="dob" name="dob">
                    <hr/>
                    <label>UserName: </label>
                    <input type="text" class="form-control" id="username" name="username">
                    <label>Password: </label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Sign Up</button>
            </form>
        </div>
    </div>
    <%
        if (request.getParameter("username") != null) {
            DbManager db = new DbManager();
            Connection conn = db.establishConnection();
            Statement stmt = conn.createStatement();
            String user = request.getParameter("username");

            String sql = "select username from user_profile as u where username = '" + user + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                response.sendRedirect("/registration.jsp");
            }
            else {
                request.getRequestDispatcher("/register").forward(request, response);

            }
        }
//        String user = request.getParameter("username");
//        UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
//        if (userProfileDao.isUserNameExist(user)) {
//            request.getRequestDispatcher("registration.jsp").forward(request, response);
//        }
//        else {
//            request.getRequestDispatcher("/register").forward(request, response);
//        }
    %>

</body>
</html>
