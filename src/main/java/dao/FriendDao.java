package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            System.out.println("TRYING TO ADD FRIEND");
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

            System.out.println("TRY TO ADD EDGE");
            // get entity ids between both people
            GraphEntityDao graphEntityDao = new GraphEntityDao(dbm);
            int primary_id = graphEntityDao.getUniqueId(new GraphEntity("user_profile", friend.getPrimaryUser())).getEntityId();
            int secondary_id = graphEntityDao.getUniqueId(new GraphEntity("user_profile", friend.getSecondaryUser())).getEntityId();

            // store in graph as edge
            GraphEdgeDao graphEdgeDao = new GraphEdgeDao(dbm);
            graphEdgeDao.insert_edge(conn, new GraphEdge(primary_id, secondary_id, "friend"));
            graphEdgeDao.insert_edge(conn, new GraphEdge(secondary_id, primary_id, "friend"));

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

    public ArrayList<String> get_friends(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<String> res = new ArrayList<>();

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM friend_list " +
                    "WHERE username_primary = ?;");

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("username_secondary"));
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
