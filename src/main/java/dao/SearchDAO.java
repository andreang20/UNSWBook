package dao;

import db.IDbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchDAO {
    private UserProfile userProfile;
    private IDbManager dbm;


    public SearchDAO(UserProfile user, IDbManager dbManager)
    {
        this.userProfile = user;
        this.dbm = dbManager;
    }
    public ArrayList<Search> searchUser(String name, String gender, String dob) throws SQLException {
        /*get all profile data from user_profile
         * store it in array, concat first_name and last_name and make all result to lowercase
         * eliminate user data
         * do search
         **/
        ArrayList<Search> result = new ArrayList<Search>();
        ArrayList<String> friend = new ArrayList<String>();
        ArrayList<Search> temp = new ArrayList<Search>();
        Connection conn = null;
        Statement stmt = null;
        conn = dbm.establishConnection();
        stmt = conn.createStatement();
        String username = userProfile.getUsername();
        //String sql = "select username, concat(first_name,' ',last_name) as name, gender, date_of_birth " +
        //       "from unswbook.user_profile where username != '" + username + "';";
        String sql = "select lower(concat(first_name,' ',last_name)) as name, gender, date_of_birth " +
        "from user_profile where username != '" + username + "';";
        ResultSet rs = stmt.executeQuery(sql);

        //convert 1st search result to array list
        while (rs.next()) {
            Search s = new Search(rs.getString("username"),rs.getString("name"), rs.getString("gender"),
                    rs.getString("date_of_birth"));
            result.add(s);
        }

        //check search result is friend or not
        //String sql2 = "select username_secondary from unswbook.friend_list where username_primary = '"+ username +"'; ";
        String sql2 = "select username_secondary from friend_list where username_primary = '"+username +"'; ";
        ResultSet rs2 = stmt.executeQuery(sql2);

        //convert 2nd friend result to array list
        while (rs2.next()) {
            String f = rs2.getString("username_secondary");
            friend.add(f);
        }

        //close connection
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) { }
        }

        //nested loop to fiend all friend
        for (Search s: result)
        {
            for (String f : friend)
            {
                if(f.equals(s.getUsername()))
                {
                    s.setFriend(true);
                }
            }
        }
            //search base on name
        if(name != null && !name.isEmpty())
        {
            for(Search s : result)
            {
                if (s.getName().toLowerCase().contains(name))
                {
                    temp.add(s);
                }
            }
            result = temp;
        }
        //search base on gender
        if(gender != null && !gender.isEmpty())
        {
            for(Search s : result)
            {
                if (s.getGender().toLowerCase().contains(gender))
                {
                    temp.add(s);
                }
            }
            result = temp;
        }
        //search base on dob
        if(dob != null && !dob.isEmpty())
        {
            for(Search s : result)
            {
                if (s.getDOB().toLowerCase().contains(dob))
                {
                    temp.add(s);
                }
            }
            result = temp;
        }

        //check the result
        for(Search s: result)
        {
            System.out.println(s.getName());
        }
        return result;

    }
}
