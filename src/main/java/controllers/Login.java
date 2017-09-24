package controllers;

import dao.UserProfile;
import dao.UserProfileDao;
import db.DbManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
            UserProfile user = userProfileDao.Login(username, password);
            String uName = user.getUsername();
            if (uName == null) {
                resp.sendRedirect("/index.html");
            }
            else {
                req.getSession().setAttribute("username", uName);
                req.getSession().setAttribute("userprofile", user);
                //req.getRequestDispatcher("/home.jsp").forward(req, resp);
                resp.sendRedirect("/home");
            }


        } catch (Exception e) {
            e.printStackTrace();
            //resp.sendRedirect("/GenericError.jsp");

        }
    }
}
