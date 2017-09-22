<%@ page import="dao.UserProfile" %><%--
  Created by IntelliJ IDEA.
  User: mnema
  Date: 21/09/2017
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (request.getSession().getAttribute("username") == null) {
        //response.sendRedirect("login.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<%
    String username = (String) request.getSession().getAttribute("username");
    UserProfile user = (UserProfile) request.getSession().getAttribute("userprofile");
    // to make sure that user is login
%>
<h1>Welcome <%= username%></h1>
<hr />
<h3>Your profile:</h3>
Your Name is: <%= user.getFirstname()%> <%= user.getLastname()%>






</body>
</html>
