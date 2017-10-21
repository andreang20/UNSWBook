package db;

import java.sql.*;

/**
 * Object for database interfacing.
 * Remember to call the close method when done with database connection.
 */
public class DbManager implements IDbManager {
    public final static String JDBC_DRIVER = "org.postgresql.Driver";
    public final static String DB_URL = "jdbc:postgresql:UNSWbook";

    private Connection connection;

    public DbManager() throws ClassNotFoundException, SQLException {
        connection = null;
        // Load driver
        Class.forName(JDBC_DRIVER);
    }

    public Connection establishConnection() throws SQLException {
//        connection = DriverManager.getConnection(DB_URL);
        connection = DriverManager.getConnection(DB_URL, "postgres", "Mhn@3115");

        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) { }
        }
    }

}