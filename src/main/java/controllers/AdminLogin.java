package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("admin_login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String admin_username = req.getParameter("adminUsername");
        String admin_password = req.getParameter("inputPassword");

        if (admin_password.equals("admin") && admin_username.equals("admin")) {
            // then login
            req.getSession().setAttribute("is_admin", true);
            resp.sendRedirect("/admin/home");
            return;
        }
    }
}
