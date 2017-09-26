<%@ page import="dao.UserProfile" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.WallPost" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
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
    <style>
        .post_img {
            width: 600px;
            height: auto;
        }
    </style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navbar.html"%>

<%
    String username = (String) request.getSession().getAttribute("username");
    // to make sure that user is login
    ArrayList<WallPost> posts = (ArrayList<WallPost>) request.getAttribute("posts");
%>
<h1>Welcome <%= username%></h1>

<div class="all_posts">
    <c:forEach items="${posts}" var="cur">
        <div class="post">
            Content: <c:out value="${cur.getContent()}"/><br>
            Username: <c:out value="${cur.getUsername()}"/><br>
            Posted date: <c:out value="${cur.getPostDate()}"/><br><br>
            <c:if test="${cur.getImage() != null}">
                <img src="data:image/png;base64,${cur.getImage()}" class="post_img"/>
            </c:if>
        </div>
    </c:forEach>
</div>





</body>
</html>
