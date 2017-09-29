<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 29/9/17
  Time: 11:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    String sender = (String) request.getAttribute("sender");
    String receiver = (String) request.getAttribute("receiver");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friend request accepted</title>
</head>
<body>
<h1>Hello <%=receiver%></h1><br>
You have accepted a friend request from <%=sender%>.
</body>
</html>
