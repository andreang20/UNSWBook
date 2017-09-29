package controllers;

import dao.Request;
import dao.RequestDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/send_request")
public class SendRequest extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender = (String) req.getSession().getAttribute("username");
        String receiver = req.getParameter("receiver");

        try {
            RequestDao requestDao = new RequestDao(new DbManager());
            requestDao.addRequest(new Request(Request.INVALID, sender, receiver, false));

            // need to send email.

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
        resp.sendRedirect("/home");
    }
}
