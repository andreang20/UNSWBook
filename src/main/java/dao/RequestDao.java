package dao;

import db.IDbManager;
import email.MyEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestDao {
    private IDbManager dbm;

    public RequestDao(IDbManager dbm) {
        this.dbm  = dbm;
    }

    public void addRequest(Request request) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("" +
                    "insert into request(sender, receiver, accepted) VALUES (?,?,?);");
            stmt.setString(1, request.getSender());
            stmt.setString(2, request.getReceiver());
            stmt.setBoolean(3, request.isAccepted());

            stmt.executeUpdate();

            // need to get receiver's email
            UserProfileDao userProfileDao = new UserProfileDao(dbm);
            UserProfile receiverProfile = userProfileDao.getUserProfile(request.getReceiver());


            // send email
            MyEmail myEmail = new MyEmail();
            String url = "http://localhost:8080/accept_request?sender="+request.getSender()+"&receiver="+request.getReceiver();
            String content = "Hello "+request.getReceiver()+", you have received a friend request from "+request.getSender()+".";
            myEmail.send("UNSWbook: Friend request", content, url, receiverProfile.getEmail());

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("Unable to add request.");
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

    public void removeRequest(Request request) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "delete from request " +
                    "where sender = ? and receiver = ?;");
            stmt.setString(1, request.getSender());
            stmt.setString(2, request.getReceiver());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot delete from request.");
        }
    }

    public boolean requestExists(String sender, String receiver) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean res = false;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select * " +
                    "from request " +
                    "where sender = ? and receiver = ?;");
            stmt.setString(1, sender);
            stmt.setString(2, receiver);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                res = true;
            } else {
                res = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
