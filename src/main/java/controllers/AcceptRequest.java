package controllers;

import dao.Friend;
import dao.FriendDao;
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

@WebServlet("/accept_request")
public class AcceptRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // need to check that we are authenticated
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
        }

        String sender = req.getParameter("sender");
        String receiver = req.getParameter("receiver");

        // remove it from requests, add as friend
        try {
            // need to check that the request exists.
            RequestDao requestDao = new RequestDao(new DbManager());
            if(!requestDao.requestExists(sender, receiver)) {
                // not real url or out of date (already friends).
                resp.sendRedirect("/GenericError.jsp");
                return;
            }

            requestDao.removeRequest(new Request(sender, receiver, false));
            FriendDao friendDao = new FriendDao(new DbManager());
            friendDao.addFriend(new Friend(sender, receiver));
            friendDao.addFriend(new Friend(receiver, sender));

            // make notification
            Utils utils = new Utils(new DbManager());
            utils.makeNotification(sender, receiver+" has accepted your friend request.");

            // need to log
            utils.logActionNow(receiver, "Has accepted "+sender+"'s friend request and now are friends.");
            utils.logActionNow(sender, "Is now friends with "+receiver+".");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }


    }
}
