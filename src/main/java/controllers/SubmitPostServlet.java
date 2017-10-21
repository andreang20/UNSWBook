package controllers;

import Rest.RestPost;
import dao.UserProfile;
import dao.WallPost;
import dao.WallPostDao;
import db.DbManager;
import email.MyEmail;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import utils.Utils;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;


@WebServlet(urlPatterns = "/create_post")
@MultipartConfig
public class SubmitPostServlet extends HttpServlet {
    public static int UNINIT_ID = -1;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Display the form
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
        }
        req.getRequestDispatcher("/submit_post.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get logged in user from session
        String username = (String) req.getSession().getAttribute("username");

        // Get inserted parameters from form
        String content = req.getParameter("content");

        // image stuff
        String base64img = null;
        Part imgPart= req.getPart("file");

        InputStream imgstream = imgPart.getInputStream();
        byte[] imgBytes = IOUtils.toByteArray(imgstream);
        base64img = new String(Base64.getEncoder().encode(imgBytes));
        if (base64img.length() == 0) {
            base64img = null;
        }

        // Get current timestamp
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        System.out.println(content);

        // extract key words
        RestPost rp = new RestPost();
        List<String> postBullyWords = rp.ExtractFeaturesRest(content);

        System.out.println("bully words in post: " + postBullyWords.toString());


        // Send email to admin if there is bully words in post
        // send email
        try {
            MyEmail myEmail = new MyEmail();
            String url = "http://localhost:8080/admin";
            String string = username + " sent a post with the following bullying words: " + postBullyWords.toString();
            String admin_email = "unswbookjusticeleague@gmail.com";
            myEmail.send("UNSWbook: Bullying Post", string, url, admin_email);

        } catch (EmailException e) {
            e.printStackTrace();
        }

        // Do a write
        WallPost newPost = new WallPost(username, SubmitPostServlet.UNINIT_ID, content, ts);
        newPost.setImage(base64img);

        // if success of the db write then redirect to wall
        try {
            WallPostDao wallPostDao = new WallPostDao(new DbManager());
            wallPostDao.addWallPost(newPost);
        } catch (Exception e) {
            // error
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }

        // log
        try {
            if (postBullyWords.isEmpty()) {
                Utils utils = new Utils(new DbManager());
                utils.logActionNow(username, "User has posted '" + content + "' on wall.");
            }
            else {
                Utils utils = new Utils(new DbManager());
                utils.logActionNow(username, "User has posted '" + content + "' on wall." +
                 "!!!!!!!!! This post contains these bullying words: " + postBullyWords.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // then redirect to same page
        resp.sendRedirect("/home");
    }

}
