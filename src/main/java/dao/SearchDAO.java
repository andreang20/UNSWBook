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
        ArrayList<Search> temp = new ArrayList<Search>();
        Connection conn = null;
        Statement stmt = null;
        conn = dbm.establishConnection();
        stmt = conn.createStatement();
        String username = userProfile.getUsername();
        //String sql = "select concat(first_name,' ',last_name) as name, gender, date_of_birth " +
          //      "from unswbook.user_profile where username != '" + username + "';";
        String sql = "select lower(concat(first_name,' ',last_name)) as name, gender, date_of_birth " +
        "from user_profile where username != '" + username + "';";
        ResultSet rs = stmt.executeQuery(sql);
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) { }
        }
        while (rs.next()) {
            Search s = new Search(rs.getString("name"), rs.getString("gender"),
                    rs.getString("date_of_birth"));
            result.add(s);
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

        for(Search s: result)
        {
            System.out.println(s.getName());
        }
        return result;

    }
}
