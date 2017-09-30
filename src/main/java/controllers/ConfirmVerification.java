package controllers;

import dao.VerificationDao;
import db.DbManager;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/confirm_verification")
public class ConfirmVerification extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        try {
            if (!((String) req.getSession().getAttribute("username")).equals(username)) {
                resp.sendRedirect("/index.html");
                return;
            }

            VerificationDao verificationDao = new VerificationDao(new DbManager());
            if (verificationDao.isExists(username, code)) {
                verificationDao.changeIsVerified(username, true);
                // need to log
                Utils utils = new Utils(new DbManager());
                utils.logActionNow(username, username+" has verified email.");
                resp.sendRedirect("/email_confirmed.jsp");
                return;
            } else {
                resp.sendRedirect("/GenericError.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
    }
}
