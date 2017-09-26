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

@WebServlet("/my_profile/change_details")
public class ChangeDetails extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println((String) req.getSession().getAttribute("username"));
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
            //req.getRequestDispatcher("/index.html").forward(req,resp);
        } else {
            // Get the user profile
            UserProfileDao userProfileDao = null;
            try {
                userProfileDao = new UserProfileDao(new DbManager());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            UserProfile user = (UserProfile) req.getSession().getAttribute("userprofile");
            req.setAttribute("userprofile", user);
            req.getRequestDispatcher("/change_details.jsp").forward(req, resp);
        }
    }

}
