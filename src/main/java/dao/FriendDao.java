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
        PreparedStatement stmt1 = null;

        try {
            conn = dbm.establishConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("" +
                    "insert into friend_list(username_primary, username_secondary) VALUES (?,?);");
            stmt.setString(1, friend.getPrimaryUser());
            stmt.setString(2, friend.getSecondaryUser());
            stmt.executeUpdate();

            // insert the other way
            stmt1 = conn.prepareStatement("" +
                    "insert into friend_list(username_primary, username_secondary) VALUES (?,?);");
            stmt1.setString(2, friend.getPrimaryUser());
            stmt1.setString(1, friend.getSecondaryUser());
            stmt1.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
            if(stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
