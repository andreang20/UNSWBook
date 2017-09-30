package controllers;

import dao.Request;
import dao.RequestDao;
import db.DbManager;
import utils.Utils;

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
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
        }

        String sender = (String) req.getSession().getAttribute("username");
        String receiver = req.getParameter("receiver");

        try {
            RequestDao requestDao = new RequestDao(new DbManager());
            requestDao.addRequest(new Request(sender, receiver, false));

            // log
            Utils utils = new Utils(new DbManager());
            utils.logActionNow(sender, "Has sent a friend request to "+receiver+".");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
        resp.sendRedirect("/home");
    }
}
