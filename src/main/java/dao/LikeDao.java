package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LikeDao {
    private IDbManager dbm;

    public LikeDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public ArrayList<Like> getLikes(int wall_id) {
        ArrayList<Like> res = new ArrayList<Like>();

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select * " +
                    "from user_like " +
                    "where wall_id = ?;");
            stmt.setInt(1, wall_id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                res.add(new Like(rs.getInt("wall_id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return res;
    }

    public void addLike(Like like) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into user_like(username, wall_id) values (?, ?);");
            stmt.setString(1, like.getUsername());
            stmt.setInt(2, like.getWall_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
}
