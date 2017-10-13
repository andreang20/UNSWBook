<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 11/10/17
  Time: 3:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Graph form</title>
</head>
<body>
    <h1>Display all:</h1>
    <a href="/graph/display_all"> Display all</a>
    <h1>Graph search</h1>
    <form action="/graph/search">
        <input type="text" name="parameter"/>
        <select name="type">
            <option>person</option>
            <option>message</option>
            <option>friends_of_friends</option>
        </select>
        <button type="submit">Search</button>
    </form>
</body>
</html>
