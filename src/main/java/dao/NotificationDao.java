package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationDao {
    private IDbManager dbm;

    public NotificationDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public void addNotification(Notification notification) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into notification(username, user_action, time, has_seen) VALUES (?,?,?,?);");
            stmt.setString(1, notification.getUsername());
            stmt.setString(2, notification.getUserAction());
            stmt.setTimestamp(3, notification.getTime());
            stmt.setBoolean(4, notification.isHasSeen());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add notification.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Notification> getNotifications(String username) {
        ArrayList<Notification> res = new ArrayList<Notification>();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select * " +
                    "from notification " +
                    "where username = ? " +
                    "order by time desc;");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                res.add(new Notification(rs.getInt("notification_id"), rs.getString("username"),
                        rs.getString("user_action"), rs.getBoolean("has_seen"),
                        rs.getTimestamp("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return res;
    }

    public void setHasSeen(int notification_id, boolean status) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "update notification " +
                    "set has_seen = ? " +
                    "where notification_id = ?;");
            stmt.setBoolean(1, status);
            stmt.setInt(2, notification_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to update has_seen.");
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
}
