package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificationDao {
    private IDbManager dbm;

    public VerificationDao(IDbManager dbm) {
        this.dbm = dbm;
    }

    public void addVerification(Verification verification) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "insert into verification(username, is_verified, code) VALUES (?,?,?);");
            stmt.setString(1, verification.getUsername());
            stmt.setBoolean(2, verification.isIs_verified());
            stmt.setString(3, verification.getCode());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add verification.");
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

    public void changeIsVerified(String username, boolean status) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "update verification " +
                    "set is_verified = ? " +
                    "where username = ?;");

            stmt.setString(2, username);
            stmt.setBoolean(1, status);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isExists(String username, String code) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Boolean res = false;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select * " +
                    "from verification " +
                    "where username = ? and code = ?;");
            stmt.setString(1, username);
            stmt.setString(2, code);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                res = true;
            } else {
                res = false;
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
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
}
