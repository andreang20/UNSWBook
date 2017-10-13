package controllers;

import dao.GraphEdge;
import dao.GraphEdgeDao;
import dao.GraphEntity;
import dao.GraphEntityDao;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/graph/display_all")
public class DisplayAllOfGraph extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get all entities and edges
        try {
            GraphEntityDao graphEntityDao = new GraphEntityDao(new DbManager());
            ArrayList<GraphEntity> graphEntities = graphEntityDao.get_all();

            GraphEdgeDao graphEdgeDao = new GraphEdgeDao(new DbManager());
            ArrayList<GraphEdge> graphEdges = graphEdgeDao.get_all();

            System.out.println(graphEntities.size());
            System.out.println(graphEdges.size());

            req.setAttribute("graph_entities", graphEntities);
            req.setAttribute("graph_edges", graphEdges);

            req.getRequestDispatcher("/display_graph.jsp").forward(req,resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
    }
}
