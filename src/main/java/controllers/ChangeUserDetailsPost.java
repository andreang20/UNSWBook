package controllers;

import dao.UserProfile;
import dao.UserProfileDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/my_profile/change_details/submitted_user_details")
public class ChangeUserDetailsPost extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        String newFirstName = req.getParameter("change_first_name");
        String newLastName = req.getParameter("change_last_name");
        String newEmail = req.getParameter("change_email");
        String newGender = req.getParameter("change_gender");
        String newDobString = req.getParameter("change_dob");

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(newDobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date d = new java.sql.Date(date.getTime());

        UserProfileDao userProfileDao = null;
        try {
            userProfileDao = new UserProfileDao(new DbManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // put inside UserProfile and pass into edit method
        UserProfile userProfileContainer = new UserProfile(username, null, newFirstName, newLastName, newEmail, newGender, d, 0);

        userProfileDao.editUserProfile(userProfileContainer);
        resp.sendRedirect("/my_profile/change_details");
    }
}
