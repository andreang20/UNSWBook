package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestDao {
    private IDbManager dbm;

    public RequestDao(IDbManager dbm) {
        this.dbm  = dbm;
    }

    public void addRequest(Request request) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into request(sender, receiver, accepted) VALUES (?,?,?);");
            stmt.setString(1, request.getSender());
            stmt.setString(2, request.getReceiver());
            stmt.setBoolean(3, request.isAccepted());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add request.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
