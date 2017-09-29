package controllers;
import dao.SearchDAO;
import db.DbManager;
import dao.Search;
import dao.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/result")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((String) req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("/index.html");
            return;
        }

        //System.out.println("Search servlet start");
        String name = req.getParameter("keyname");
        String gender = req.getParameter("keygender");
        String DOB = req.getParameter("keyDOB");
        DOB = DOB.toLowerCase();
        name = name.toLowerCase();
        gender = gender.toLowerCase();
       // Search search = new Search(name,gender,DOB);
        // Get logged in user from session
        UserProfile user = (UserProfile) req.getSession().getAttribute("userprofile");
        //System.out.println("Search servlet");
        try {
            DbManager db = new DbManager();
            SearchDAO sdao = new SearchDAO(user, db);
            ArrayList<Search> result = new ArrayList<Search>();
            //System.out.println("start Search ");
            result = sdao.searchUser(name, gender, DOB);
            //System.out.println("end Search ");
            /*if (result.size() == 0)
            {
                resp.sendRedirect("/GenericError.jsp");
                return;
            }*/
            req.setAttribute("result", result);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
        // error
        e.printStackTrace();
        resp.sendRedirect("/GenericError.jsp");
        return;
        }

    }
}
