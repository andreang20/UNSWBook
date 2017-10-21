package controllers;

import dao.UserProfile;
import dao.UserProfileDao;
import dao.Verification;
import dao.VerificationDao;
import db.DbManager;
import org.apache.commons.lang.RandomStringUtils;
import utils.Utils;

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

import static com.sun.activation.registries.LogSupport.log;


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
        try {
            UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
            // check if username exists already
            if (userProfileDao.getUserProfile(userName) != null) {
                resp.sendRedirect("/username_already_exists.jsp");
                return;
            }
            System.out.println(dob);
            Date date = null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.sql.Date d = new java.sql.Date(date.getTime());

            //int session_id = Integer.parseInt(req.getSession().getId());
            int session_id = 0;
            UserProfile newProfile = new UserProfile(userName, password, firstName, lastName, email, gender, d, session_id, false);

            userProfileDao.addUserProfile(newProfile);


            // success
            Utils utils = new Utils(new DbManager());
            utils.logActionNow(userName, "User has registered.");

            resp.sendRedirect("/index.html");
            return;
        } catch (Exception e) {
            e.printStackTrace();

            resp.sendRedirect("/GenericError.jsp");
            return;
        }
    }

    private void tryAddVerification(VerificationDao dao, String username) {

    }

}
