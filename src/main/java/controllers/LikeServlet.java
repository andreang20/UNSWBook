package controllers;

import dao.Like;
import dao.LikeDao;
import dao.WallPost;
import dao.WallPostDao;
import db.DbManager;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String wall_id = req.getParameter("wall_id");
        String username = (String) req.getSession().getAttribute("username");
        try {
            LikeDao likeDao = new LikeDao(new DbManager());
            likeDao.addLike(new Like(Integer.parseInt(wall_id), username));

            Utils utils = new Utils(new DbManager());
            utils.logActionNow(username, username+" has liked post with id of "+wall_id+".");
            // need to get wall_post to get contents and time
            WallPostDao wallPostDao = new WallPostDao(new DbManager());
            WallPost post = wallPostDao.getPostsById(Integer.parseInt(wall_id));

            utils.makeNotification(username, username+" has liked your post of content '"+post.getContent()+"' at time '"+post.getPostDate()+"'.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
        resp.sendRedirect("/home");
    }
}
