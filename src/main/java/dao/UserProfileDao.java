package dao;

import db.IDbManager;

import java.sql.*;
import java.util.ArrayList;

public class UserProfileDao {
    private ArrayList<UserProfile> userProfiles;
    private IDbManager dbm;

    public UserProfileDao(IDbManager dbManager) {
        userProfiles = new ArrayList<UserProfile>();
        this.dbm = dbManager;
    }

    public void addUserProfile(UserProfile userProfile) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("INSERT INTO user_profile (username, password, first_name, last_name, email, gender, date_of_birth, session_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            stmt.setString(1, userProfile.getUsername());
            stmt.setString(2, userProfile.getPassword());
            stmt.setString(3, userProfile.getFirstname());
            stmt.setString(4, userProfile.getLastname());
            stmt.setString(5, userProfile.getEmail());
            stmt.setString(6, userProfile.getGender());
            System.out.println(userProfile.getDate());
            stmt.setDate(7, userProfile.getDate());
            stmt.setInt(8, userProfile.getSession_id());


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add user profile");
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { }
            }
        }
    }

    public ArrayList<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void search(String firstname, String lastname) {
        userProfiles.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("select * " +
                    "from user_profile" +
                    " where lower(user_profile.first_name) like '%%' || lower(?) || '%%' and lower(user_profile.last_name) like '%%' || lower(?) || '%%';");
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                userProfiles.add(new UserProfile(rs.getString("username"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("gender"),
                        rs.getDate("date_of_birth"), rs.getInt("session_id")));
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

    public void getFriends(String username) {
        userProfiles.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbm.establishConnection();

            String sql = "select *\n" +
                    "from\n" +
                    "(select friend_list.username_secondary\n" +
                    "from friend_list\n" +
                    "where friend_list.username_primary = ? and friend_list.accepted = TRUE) t1\n" +
                    "inner join\n" +
                    "(select *\n" +
                    "from user_profile) t2\n" +
                    "on t1.username_secondary = t2.username;";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                userProfiles.add(new UserProfile(rs.getString("username"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("gender"),
                        rs.getDate("date_of_birth"), rs.getInt("session_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}

