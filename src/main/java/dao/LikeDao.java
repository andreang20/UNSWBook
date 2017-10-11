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
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("" +
                    "insert into user_like(username, wall_id) values (?, ?);");
            stmt.setString(1, like.getUsername());
            stmt.setInt(2, like.getWall_id());
            stmt.executeUpdate();

            GraphEntityDao graphEntityDao = new GraphEntityDao(dbm);
            int wall_post_id = graphEntityDao.getUniqueId(new GraphEntity("wall_post",  Integer.toString(like.getWall_id()))).getEntityId();
            int person_id = graphEntityDao.getUniqueId(new GraphEntity("user_profile", like.getUsername())).getEntityId();

            GraphEdgeDao graphEdgeDao = new GraphEdgeDao(dbm);
            graphEdgeDao.insert_edge(conn, new GraphEdge(person_id, wall_post_id, "like"));
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

    public void removeLike(Like like) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("" +
                    "delete from user_like " +
                    "where username = ? and wall_id = ?;");
            stmt.setString(1, like.getUsername());
            stmt.setInt(2, like.getWall_id());

            stmt.executeUpdate();

            // remove like from graph
            GraphEntityDao graphEntityDao = new GraphEntityDao(dbm);
            int wall_post_id = graphEntityDao.getUniqueId(new GraphEntity("wall_post",  Integer.toString(like.getWall_id()))).getEntityId();
            int person_id = graphEntityDao.getUniqueId(new GraphEntity("user_profile", like.getUsername())).getEntityId();

            GraphEdgeDao graphEdgeDao = new GraphEdgeDao(dbm);
            graphEdgeDao.remove_edge(conn, new GraphEdge(person_id, wall_post_id, "like"));

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("Unable to remove like.");
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
