package dao;

import db.IDbManager;

import java.sql.*;
import java.util.ArrayList;

public class WallPostDao {
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
            stmt = conn.prepareStatement("INSERT INTO wall_post (username, content, post_date, img)" +
                    " VALUES (?,?,?,?);");
            stmt.setString(1, wallPost.getUsername());
            stmt.setString(2, wallPost.getContent());
            stmt.setTimestamp(3, wallPost.getPostDate());
            stmt.setString(4, wallPost.getImage());
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

    /*public void getPostsByUser(String username) {
        wallPosts.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("SELECT * FROM wall_post WHERE wall_post.username = ?;");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                WallPost newPost = new WallPost(rs.getString("username"), rs.getInt("id"),
                        rs.getString("content"), rs.getTimestamp("post_date"));
                newPost.setImage(rs.getString("img"));
                wallPosts.add(newPost);
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
    }*/

    /**
     * Gets wall posts of user and friends and sorts in order (desc) by timestamp
     */
    public void getUserWall(String username) {
        wallPosts.clear();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("SELECT *\n" +
                    "FROM wall_post\n" +
                    "WHERE wall_post.username = ? OR wall_post.username in\n" +
                    "\t(select username_secondary\n" +
                    "\t FROM friend_list\n" +
                    "\t WHERE friend_list.username_primary = ? \n" +
                    "\t)\n" +
                    "ORDER BY wall_post.post_date desc;");
            stmt.setString(1, username);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                WallPost newPost = new WallPost(rs.getString("username"), rs.getInt("id"),
                        rs.getString("content"), rs.getTimestamp("post_date"));
                newPost.setImage(rs.getString("img"));
                // need to store the likes.
                LikeDao likeDao = new LikeDao(dbm);
                newPost.setLikes(likeDao.getLikes(newPost.getId()));
                wallPosts.add(newPost);
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

    public WallPost getPostsById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        WallPost res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("SELECT * FROM wall_post WHERE wall_post.id = ?;");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
           res = new WallPost(rs.getString("username"), rs.getInt("id"),
                    rs.getString("content"), rs.getTimestamp("post_date"));
           res.setImage(rs.getString("img"));

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
        return res;
    }


}
