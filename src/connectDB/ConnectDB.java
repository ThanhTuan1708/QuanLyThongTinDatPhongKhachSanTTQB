package connectDB;

import java.sql.*;

public class ConnectDB {
    private static ConnectDB instance = null;
    private Connection connection;
    
    private ConnectDB() {}
    
    public static ConnectDB getInstance() {
        if(instance == null) instance = new ConnectDB();
        return instance;
    }
    
    public void connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quan_ly_khach_san;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "sapassword"; // Change this to your SQL Server password
        connection = DriverManager.getConnection(url, user, password);
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void disconnect() {
        if(connection != null) {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}