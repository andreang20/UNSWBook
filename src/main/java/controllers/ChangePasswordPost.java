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

@WebServlet("/my_profile/change_details/submitted_password")
public class ChangePasswordPost extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String new_password = req.getParameter("change_password");
        String username = (String) req.getSession().getAttribute("username");
        if (new_password == null || new_password.length() < 1) {
            System.err.println("User hasn't entered password.");
        }

        UserProfileDao userProfileDao = null;
        try {
            userProfileDao = new UserProfileDao(new DbManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userProfileDao.editPassword(new_password, username);

        UserProfile user = userProfileDao.getUserProfile(username);
        req.getSession().removeAttribute("userprofile");
        req.getSession().setAttribute("userprofile", user);

        resp.sendRedirect("/my_profile/change_details");
    }
}
