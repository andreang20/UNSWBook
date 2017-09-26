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
import java.util.ArrayList;

@WebServlet("/admin/search")
public class AdminSearchUser extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("is_admin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("/admin");
            return;
        }

        String input = req.getParameter("user_search");

        UserProfileDao userProfileDao = null;
        try {
            userProfileDao = new UserProfileDao(new DbManager());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<UserProfile> search_results = userProfileDao.searchByUsername(input);
        req.setAttribute("search_results", search_results);
        req.getRequestDispatcher("/admin_search.jsp").forward(req,resp);
    }
}
