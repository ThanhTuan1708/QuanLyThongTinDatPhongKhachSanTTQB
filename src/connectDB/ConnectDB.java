package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB instance = null;
    private Connection connection;

    private ConnectDB() {}

    public static ConnectDB getInstance() {
        if (instance == null) instance = new ConnectDB();
        return instance;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSan;encrypt=true;trustServerCertificate=true;";
        String user = "sa";
        String password = "sapassword";
        connection = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}