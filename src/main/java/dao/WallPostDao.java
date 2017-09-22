package dao;

import db.IDbManager;

import java.sql.*;
import java.util.ArrayList;

public class WallPostDao implements IWallPostDao {
    private ArrayList<WallPost> wallPosts;
    IDbManager dbm;

    public WallPostDao(IDbManager dbm) {
        wallPosts = new ArrayList<WallPost>();
        this.dbm = dbm;
    }

    public ArrayList<WallPost> getWallPosts() {
        return wallPosts;
    }

    public void addWallPost(WallPost wallPost) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("INSERT INTO wall_post (username, content, post_date)" +
                    " VALUES (?,?,?);");
            stmt.setString(1, wallPost.getUsername());
            stmt.setString(2, wallPost.getContent());
            stmt.setTimestamp(3, wallPost.getPostDate());
            stmt.executeUpdate();

            //conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add wall post.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { }
            }
        }
    }

    public void getPostsByUser(String username) {
        wallPosts.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("SELECT * FROM wall_post WHERE wall_post.username = ?;");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                wallPosts.add(new WallPost(rs.getString("username"), rs.getInt("id"),
                        rs.getString("content"), rs.getTimestamp("post_date")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { }
            }
        }
    }

}
