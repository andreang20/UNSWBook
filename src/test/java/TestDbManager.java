import db.DbManager;
import db.IDbManager;

import java.sql.SQLException;

public class TestDbManager {
    public static void main(String[] args) {
        try {
            IDbManager dbManager = new DbManager();
            //String s = null;
            //System.out.println(s.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
