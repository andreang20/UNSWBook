package controllers;

import dao.UserProfileDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/ban")
public class AdminBan extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("is_admin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("/admin");
            return;
        }

        String toBanUsername = req.getParameter("ban_user");

        try {
            UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
            userProfileDao.updateIsBannedStatus(toBanUsername, true);
            req.getRequestDispatcher("/AdminBannedUserPage.jsp").forward(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
    }
}
