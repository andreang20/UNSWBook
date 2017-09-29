package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendDao {
    private IDbManager dbm;

    public FriendDao(IDbManager dbm) {
       this.dbm = dbm;
    }

    public void addFriend(Friend friend) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into friend_list(username_primary, username_secondary) VALUES (?,?);");
            stmt.setString(1, friend.getPrimaryUser());
            stmt.setString(2, friend.getSecondaryUser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot insert friend");
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
