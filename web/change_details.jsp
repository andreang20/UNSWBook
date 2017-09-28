<%@ page import="dao.UserProfile" %><%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 24/9/17
  Time: 5:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    UserProfile userProfile = (UserProfile) request.getAttribute("userprofile");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change details</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navbar.html"%>
<div class="container">
    <form action="/my_profile/change_details/submitted_password" method="post">
        <div class="form-group ">
            <label for="change_password"><b>Change your password:</b></label>
            <input type="text" name="change_password" id="change_password" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Change</button>
    </form>
    <br>
    <form action="/my_profile/change_details/submitted_user_details" method="post">
        <b>Change your details:</b>
        <div class="form-group ">
            <label for="change_first_name">First name: </label>
            <input type="text" name="change_first_name" id="change_first_name" class="form-control"
                   value="<%=userProfile.getFirstname()%>">
        </div>
        <div class="form-group ">
            <label for="change_last_name">Last name: </label>
            <input type="text" name="change_last_name" id="change_last_name" class="form-control"
                   value="<%=userProfile.getLastname()%>">
        </div>
        <div class="form-group ">
            <label for="change_email">Email: </label>
            <input type="text" name="change_email" id="change_email" class="form-control"
                   value="<%=userProfile.getEmail()%>">
        </div>
        <div class="form-group ">
            <label for="change_gender">Gender: </label>
            <select class="form-control" id="change_gender" name="change_gender">
                <option>Male</option>
                <option
                        <%
                            if (userProfile.getGender().equals("Female")) {
                        %>
                        selected="selected"
                        <%
                            }
                        %>
                >Female
                </option>
            </select>
        </div>
        <div class="form-group ">
            <label for="change_dob">Date of birth: </label>
            <input type="text" name="change_dob" id="change_dob" class="form-control"
                   value="<%=userProfile.getFormattedDate()%>">
        </div>
        <button type="submit" class="btn btn-primary">Change</button>
    </form>

</div>

</body>
</html>
