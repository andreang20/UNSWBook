package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GraphEdgeDao {
    private IDbManager dbm;

    public GraphEdgeDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public int insert_edge(Connection conn, GraphEdge edge) throws SQLException{
        PreparedStatement stmt = null;
        String[] generatedCol = {"edge_id"};
        try {
            stmt = conn.prepareStatement("" +
                    "INSERT INTO graph_edge (from_entity, to_entity, relationship) VALUES (?,?,?);", generatedCol);
            stmt.setInt(1, edge.getFrom());
            stmt.setInt(2, edge.getTo());
            stmt.setString(3, edge.getRelationship());

            stmt.executeUpdate();

            ResultSet keyset = stmt.getGeneratedKeys();
            keyset.next();
            int id = keyset.getInt(1);
            return id;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public ArrayList<GraphEdge> get_all() throws SQLException {
        ArrayList<GraphEdge> res = new ArrayList<GraphEdge>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_edge;");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new GraphEdge(rs.getInt("edge_id"), rs.getInt("from_entity"),
                        rs.getInt("to_entity"), rs.getString("relationship")));
            }

        } finally {
            if (conn != null) {
                conn.close();
            }
            if(stmt != null) {
                stmt.close();
            }
        }
        return res;
    }

    public void remove_edge(Connection conn, GraphEdge edge) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("" +
                    "DELETE FROM graph_edge " +
                    "WHERE from_entity = ? and to_entity = ? and relationship = ?;");
            stmt.setInt(1, edge.getFrom());
            stmt.setInt(2, edge.getTo());
            stmt.setString(3, edge.getRelationship());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public GraphEdge getUniqueEdge(GraphEdge edge) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_edge " +
                    "WHERE from_entity = ? and to_entity = ? and relationship = ?;");

            stmt.setInt(1, edge.getFrom());
            stmt.setInt(2, edge.getTo());
            stmt.setString(3, edge.getRelationship());

            ResultSet rs = stmt.executeQuery();
            GraphEdge res = null;
            if (rs.next()) {
                res = new GraphEdge(rs.getInt("edge_id"), edge.getFrom(), edge.getTo(), edge.getRelationship());
            }
            return res;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public ArrayList<GraphEdge> getNodeEdges(int entity_id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<GraphEdge> res = new ArrayList<>();
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_edge " +
                    "WHERE from_entity = ? or to_entity = ?;");
            stmt.setInt(1, entity_id);
            stmt.setInt(2, entity_id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                res.add(new GraphEdge(rs.getInt("edge_id"), rs.getInt("from_entity"),
                        rs.getInt("to_entity"), rs.getString("relationship")));
            }

            return res;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
