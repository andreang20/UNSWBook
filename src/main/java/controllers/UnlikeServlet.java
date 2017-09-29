package controllers;

import dao.Like;
import dao.LikeDao;
import db.DbManager;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/unlike")
public class UnlikeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String wall_id = req.getParameter("wall_id");
        String username = (String) req.getSession().getAttribute("username");

        try {
            LikeDao likeDao = new LikeDao(new DbManager());
            likeDao.removeLike(new Like(Integer.parseInt(wall_id), username));

            Utils utils = new Utils(new DbManager());
            utils.logActionNow(username, username+" has unliked post with id of "+wall_id+".");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
        resp.sendRedirect("/home");
    }
}
