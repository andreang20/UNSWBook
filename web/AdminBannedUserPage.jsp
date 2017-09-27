<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 27/9/17
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String toBanUsername = request.getParameter("ban_user");
%>
<html>
<head>
    <title>User is banned</title>
</head>
<body>
<h1><%=toBanUsername%> is banned.</h1>
</body>
</html>
