package controllers;


import db.DbManager;
import dao.UserProfile;
import dao.SendMail;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/add")
public class AddFriend extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String receiver = req.getParameter("user");
        System.out.println("receiver is " +receiver);
        UserProfile user = (UserProfile) req.getSession().getAttribute("userprofile");
        System.out.println(user.getUsername());
        String sender = user.getUsername();
        Boolean send = false;
        try {
            DbManager dbm = new DbManager();
            SendMail fm = new SendMail(dbm);
            send = fm.sendFriendMail(sender, receiver, req);
        } catch (Exception e) {
            // error
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }
        if (send == false)
        {
            resp.sendRedirect("/GenericError.jsp");
        }
        resp.sendRedirect("/Email.jsp");
    }
}
