package controllers;

import dao.Notification;
import dao.NotificationDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/all_notifications")
public class UserAllNotifications extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println((String) req.getSession().getAttribute("username"));
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
        }
        try {
            String username = (String) req.getSession().getAttribute("username");
            NotificationDao notificationDao = new NotificationDao(new DbManager());
            ArrayList<Notification> notifications = notificationDao.getNotifications(username);
            req.setAttribute("notifications", notifications);
            req.getRequestDispatcher("/all_notifications.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }

    }
}
