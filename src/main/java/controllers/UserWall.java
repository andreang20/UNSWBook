package controllers;

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
import java.util.ArrayList;

@WebServlet("/home")
public class UserWall extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IDbManager dbm = null;
        try {
            dbm = new DbManager();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
        }

        WallPostDao wallPostDao = new WallPostDao(dbm);
        wallPostDao.getUserWall("john123");

        ArrayList<WallPost> posts = wallPostDao.getWallPosts();
        req.setAttribute("posts", posts);

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
