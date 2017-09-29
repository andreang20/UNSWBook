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
        .panel {
            margin-top: 20px;
            margin-bottom: 20px;
            background-color: lightblue;
        }
    </style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%
    String username = (String) request.getSession().getAttribute("username");
    UserProfile user = (UserProfile) request.getSession().getAttribute("userprofile");
    ArrayList<WallPost> posts = (ArrayList<WallPost>) request.getAttribute("posts");
%>
<%@include file="navbar.html"%>
<h1>Welcome <%= username%></h1>
<hr />

<div class="all_posts container">
    <c:forEach items="${posts}" var="cur">
        <div class="post panel panel-default">
            Content: <c:out value="${cur.getContent()}"/><br>
            Username: <c:out value="${cur.getUsername()}"/><br>
            Posted date: <c:out value="${cur.getPostDate()}"/><br>
            <c:if test="${cur.getImage() != null}">
                <img src="data:image/png;base64,${cur.getImage()}" class="post_img"/>
            </c:if><br>
            <c:if test="${!cur.hasLiked(username)}">
                <form action="/like" method="post">
                    <input type="hidden" name="wall_id" value="${cur.getId()}">
                    <button type="submit">Like</button>
                </form>
            </c:if>
            <c:if test="${cur.hasLiked(username)}">
                <form action="/unlike" method="post">
                    <input type="hidden" name="wall_id" value="${cur.getId()}">
                    <button type="submit">Unlike</button>
                </form>
            </c:if>

            Liked by:
            <ul>
                <c:forEach items="${cur.getLikes()}" var="curLike">
                    <li><c:out value="${curLike.getUsername()}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>
</div>
</body>
</html>
