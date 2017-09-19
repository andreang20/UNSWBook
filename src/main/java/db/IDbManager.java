package db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDbManager {
    public Connection establishConnection() throws SQLException;
    public void close();
}
