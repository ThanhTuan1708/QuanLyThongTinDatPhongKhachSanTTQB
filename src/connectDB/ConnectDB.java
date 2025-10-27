package connectDB;

import java.sql.*;

public class ConnectDB {
    private static ConnectDB instance = null;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Quan_ly_khach_san;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";
    private boolean isConnected = false;
    
    private ConnectDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }
    }
    
    public static ConnectDB getInstance() {
        if(instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }
    
    public void connect() throws SQLException {
        if (!isConnected) {
            try (Connection testConn = getConnection()) {
                if (testConn != null && !testConn.isClosed()) {
                    isConnected = true;
                    System.out.println("Database connection established successfully");
                }
            }
        }
    }
    
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Failed to establish database connection");
            }
            return conn;
        } catch (SQLException e) {
            isConnected = false;
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
    
    /**
     * Safely close a connection
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Safely close a statement
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    /**
     * Safely close a result set
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
    }
}