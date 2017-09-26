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


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");
        String dob = req.getParameter("dob");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date d = new java.sql.Date(date.getTime());

        //int session_id = Integer.parseInt(req.getSession().getId());
        int session_id = 0;
        UserProfile newProfile = new UserProfile(userName, password, firstName, lastName, email, gender, d, session_id);

        try {
            UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
            userProfileDao.addUserProfile(newProfile);
            resp.sendRedirect("/index.html");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }


    }
}
