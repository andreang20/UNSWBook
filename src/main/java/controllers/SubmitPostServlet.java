package controllers;

import dao.UserProfile;
import dao.WallPost;
import dao.WallPostDao;
import db.DbManager;
import db.IDbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/create_post")
public class SubmitPostServlet extends HttpServlet {
    public static int UNINIT_ID = -1;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Display the form
        resp.sendRedirect("/submit_post.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get logged in user from session
        UserProfile user = (UserProfile) req.getSession().getAttribute("userprofile");

        // Get inserted parameters from form
        String content = req.getParameter("content");
        byte[] imgBytes = null;
        String imgStr = req.getParameter("img");
        if (imgStr != null) {
            imgBytes = imgStr.getBytes();
        }

        // Get current timestamp
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        System.out.println(content);
        // Do a write
        WallPost newPost = new WallPost(user.getUsername(), SubmitPostServlet.UNINIT_ID, content, ts);

        // if success of the db write then redirect to wall
        try {
            WallPostDao wallPostDao = new WallPostDao(new DbManager());
            wallPostDao.addWallPost(newPost);
        } catch (Exception e) {
            // error
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }

        // if fail, then redirect to same page
        resp.sendRedirect("/home.jsp");

    }

}
