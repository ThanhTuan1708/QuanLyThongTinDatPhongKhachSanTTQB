package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static final ConnectDB instance = new ConnectDB();
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSan";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";

    public static ConnectDB getInstance() {
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            instance.connect(); // Tái tạo kết nối nếu cần
        }
        return con;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver JDBC: " + e.getMessage());
        }
    }

    public void disconnect() throws SQLException {
        if (con != null && !con.isClosed()) {
            try {
                con.close();
                con = null; // Đặt lại con về null để buộc tạo kết nối mới
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}