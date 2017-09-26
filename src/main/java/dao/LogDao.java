package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogDao {
    private IDbManager dbm;

    public LogDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public void addLog(Log log) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into log(username,user_action,time) values (?,?,?);");
            stmt.setString(1, log.getUsername());
            stmt.setString(2, log.getAction());
            stmt.setTimestamp(3, log.getTime());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add log.");
        } finally {
            if(conn != null) {
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

    public ArrayList<Log> getLogs(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Log> res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM log " +
                    "WHERE username = ? " +
                    "ORDER BY time DESC;");
            stmt.setString(1, username);

            ResultSet rs =  stmt.executeQuery();
            res = rsToArrayList(rs);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn == null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt == null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    private ArrayList<Log> rsToArrayList(ResultSet rs) throws SQLException {
        ArrayList<Log> res = new ArrayList<Log>();
        while (rs.next()) {
            Log log = new Log(rs.getString("username"), rs.getString("user_action"), rs.getTimestamp("time"));
            res.add(log);
        }
        return res;
    }
}
