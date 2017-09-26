package controllers;

import dao.Log;
import dao.LogDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/admin/userlog")
public class AdminUserLog extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("is_admin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("/admin");
            return;
        }

        String username = req.getParameter("log_username");
        if (username == null) {
            System.err.println("No username in parameter.");
            resp.sendRedirect("/GenericError.jsp");
            return;
        }

        LogDao logDao= null;
        try {
            logDao = new LogDao(new DbManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Log> userlogs = logDao.getLogs(username);

        req.setAttribute("userlogs", userlogs);
        req.getRequestDispatcher("/admin_userlog.jsp").forward(req,resp);
    }
}

