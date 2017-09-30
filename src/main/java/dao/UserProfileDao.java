package dao;

import db.DbManager;
import db.IDbManager;
import email.MyEmail;
import org.apache.commons.lang.RandomStringUtils;
import utils.Utils;

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
        PreparedStatement stmt1 = null;
        try {
            conn = dbm.establishConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO user_profile (username, password, first_name, last_name, email, gender, date_of_birth, session_id, is_banned) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");

            stmt.setString(1, userProfile.getUsername());
            stmt.setString(2, userProfile.getPassword());
            stmt.setString(3, userProfile.getFirstname());
            stmt.setString(4, userProfile.getLastname());
            stmt.setString(5, userProfile.getEmail());
            stmt.setString(6, userProfile.getGender());
            stmt.setDate(7, userProfile.getDate());
            stmt.setInt(8, userProfile.getSession_id());
            stmt.setBoolean(9, userProfile.getIs_banned());

            stmt.executeUpdate();

            // need to add to verification
            String code = RandomStringUtils.randomAlphanumeric(500);
            stmt1 = conn.prepareStatement("" +
                    "insert into verification(username, is_verified, code) VALUES (?,?,?);");
            stmt1.setString(1, userProfile.getUsername());
            stmt1.setBoolean(2, false);
            stmt1.setString(3, code);
            stmt1.executeUpdate();

            MyEmail myEmail = new MyEmail();
            String url = "http://localhost:8080/confirm_verification?username="+userProfile.getUsername()+"&code="+code;
            String content = "Hello "+userProfile.getUsername()+", confirm your email here!";
            myEmail.send("UNSWbook: Email verification", content, url, userProfile.getEmail());


            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
                        rs.getDate("date_of_birth"), rs.getInt("session_id"), rs.getBoolean("is_banned")));
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

    public boolean isUserNameExist(String username) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        conn = dbm.establishConnection();
        stmt = conn.createStatement();
        String sql = "select username from user_profile where username = '" + username + "';";
        ResultSet rs = stmt.executeQuery(sql);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { }
        }
//        if (stmt != null) {
//            try {
//                stmt.close();
//            } catch (SQLException e) { }
//        }
        if (rs.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public UserProfile Login(String username, String password) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        conn = dbm.establishConnection();
        stmt = conn.createStatement();
        String sql = "select * from user_profile where username = '" + username + "' and password = '" +password + "';";
        //String sql = "select * from user_profile where username = '" + username + "';";
        ResultSet rs = stmt.executeQuery(sql);

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { }
        }

        if (rs.next()) {
            UserProfile userProfile = new UserProfile(rs.getString("username"), rs.getString("password"),
                    rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("gender"),
                    rs.getDate("date_of_birth"), rs.getInt("session_id"), rs.getBoolean("is_banned"));
            return userProfile;
        }
        else {
            UserProfile userProfile = new UserProfile();
            return userProfile;
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
                    "where friend_list.username_primary = ? t1\n" +
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
                        rs.getDate("date_of_birth"), rs.getInt("session_id"), rs.getBoolean("is_banned")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editUserProfile(UserProfile userProfile) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("UPDATE user_profile " +
                    "SET first_name = ?, last_name = ?, email = ?, gender = ?, " +
                    "date_of_birth = ? " +
                    "WHERE username = ?");
            stmt.setString(1, userProfile.getFirstname());
            stmt.setString(2, userProfile.getLastname());
            stmt.setString(3, userProfile.getEmail());
            stmt.setString(4, userProfile.getGender());
            stmt.setDate(5, userProfile.getDate());
            stmt.setString(6, userProfile.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to edit user profile.");
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

    public void editPassword(String password, String username) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("UPDATE user_profile " +
                    "SET password = ? " +
                    "WHERE username = ?");
            stmt.setString(1, password);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to edit user profile.");
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

    public UserProfile getUserProfile(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        UserProfile res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("SELECT * " +
                    "FROM user_profile " +
                    "WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                res = null;
            } else {
                res = new UserProfile(rs.getString("username"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("gender"),
                        rs.getDate("date_of_birth"), rs.getInt("session_id"), rs.getBoolean("is_banned"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
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

    public ArrayList<UserProfile> searchByUsername(String input) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<UserProfile> res = null;
        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select * " +
                    "from user_profile " +
                    "where lower(user_profile.username) like '%%' || lower(?) || '%%';");
            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();
            res = convertRsToArrayList(rs);
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

    private ArrayList<UserProfile> convertRsToArrayList(ResultSet rs) throws SQLException {
        ArrayList<UserProfile> res = new ArrayList<UserProfile>();
        while(rs.next()) {
            UserProfile cur = new UserProfile(rs.getString("username"), rs.getString("password"),
                    rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("gender"),
                    rs.getDate("date_of_birth"), rs.getInt("session_id"), rs.getBoolean("is_banned"));
            res.add(cur);
        }
        return res;
    }

    public void updateIsBannedStatus(String username, boolean is_banned) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "UPDATE user_profile " +
                    "SET is_banned = ? " +
                    "WHERE username = ?;");
            stmt.setBoolean(1, is_banned);
            stmt.setString(2, username);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to update banned status.");
        } finally {
            if(conn != null) {
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

    public ArrayList<UserProfile> searchUsers(String full_name, String gender, String dob, String currentUser) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<UserProfile> res = null;

        try {
            conn = dbm.establishConnection();
            stmt = conn.prepareStatement("" +
                    "select lower(concat(first_name,' ',last_name)) as name, username, password, first_name, last_name, email, gender, date_of_birth, session_id, is_banned " +
                    "from user_profile " +
                    "where (? is null or lower(concat(first_name,' ',last_name)) like '%%' || lower(?) || '%%') and " +
                    "(? is null or gender like ?) and " +
                    "(? is null or date_of_birth = ?::date) and username != ?;");
            stmt.setString(1, full_name);
            stmt.setString(2, full_name);
            stmt.setString(3, gender);
            stmt.setString(4, gender);
            stmt.setString(5, dob);
            stmt.setString(6, dob);
            stmt.setString(7, currentUser);

            ResultSet rs = stmt.executeQuery();
            res = convertRsToArrayList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}

