package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/home")
public class AdminHome extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if have admin privileges
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("is_admin");
        if (isAdmin == null || !isAdmin) {
            resp.sendRedirect("/admin");
            return;
        }

        req.getRequestDispatcher("/admin_home.jsp").forward(req,resp);
    }

}
