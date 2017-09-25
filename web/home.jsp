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
</head>
<body>
<a href="/logout">logout</a>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (request.getSession().getAttribute("username") == null) {
        //response.sendRedirect("login.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<%
    String username = (String) request.getSession().getAttribute("username");
    //UserProfile user = (UserProfile) request.getSession().getAttribute("userprofile"); dont store user profile in session because you will get outdate info, if you want it then do another query for it
    // to make sure that user is login
    ArrayList<WallPost> posts = (ArrayList<WallPost>) request.getAttribute("posts");
%>
<h1>Welcome <%= username%></h1>
<hr />


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
