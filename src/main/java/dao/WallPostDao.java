package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Statement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO wall_post (username, content, post_date)" +
                    " VALUES ('"+wallPost.getUsername()+"', '"
                    +wallPost.getContent()+"', '"+wallPost.getPostDate().toString()+"');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            //conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add wall post.");
            /*try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }*/
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
        Statement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.createStatement();

            String sql = "SELECT *\n" +
                    "FROM wall_post\n" +
                    "WHERE wall_post.username = '"+username+"';";
            System.out.println(sql);

            ResultSet rs = stmt.executeQuery(sql);
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
