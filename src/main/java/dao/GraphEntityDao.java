package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GraphEntityDao {
    private IDbManager dbm;

    public GraphEntityDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public int insert_entity(Connection conn, GraphEntity entity) throws SQLException {
        PreparedStatement stmt = null;
        String[] generatedCol = {"entity_id"};
        try {

            stmt = conn.prepareStatement("" +
                    "INSERT INTO graph_entity (attribute, value) VALUES (?,?);", generatedCol);

            stmt.setString(1, entity.getAttribute());
            stmt.setString(2, entity.getValue());

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

    public ArrayList<GraphEntity> get_all() throws SQLException {
        ArrayList<GraphEntity> res = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_entity;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) { //entityId, String attribute, String value
                res.add(new GraphEntity(rs.getInt("entity_id"), rs.getString("attribute"), rs.getString("value")));
            }

        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return res;
    }

    public GraphEntity getUniqueId(GraphEntity entity) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        GraphEntity res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_entity " +
                    "WHERE attribute = ? and value = ?;");

            stmt.setString(1, entity.getAttribute());
            stmt.setString(2, entity.getValue());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                res = new GraphEntity(rs.getInt("entity_id"), rs.getString("attribute"), rs.getString("value"));
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return res;
    }

    /*public GraphEntity[] getEntitiesFromEdge(GraphEdge edge) throws SQLException {
        GraphEntity[] res = new GraphEntity[2];
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT ");

        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

    }*/


    public GraphEntity get_entity(int entity_id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        GraphEntity res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM graph_entity " +
                    "WHERE entity_id = ?;");
            stmt.setInt(1, entity_id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                res = new GraphEntity(rs.getInt("entity_id"),
                        rs.getString("attribute"), rs.getString("value"));
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
