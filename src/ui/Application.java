package ui;


import connectDB.ConnectDB;
import ui.form.FormDangNhap;
import ui.gui.GUI_NhanVienLeTan; 
import ui.gui.GUI_NhanVienQuanLy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Application {
    public static String currentLoggedInUser = "";

    // ...existing code...
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kết nối CSDL
        try {
            ConnectDB.getInstance().connect();
            System.out.println("Kết nối CSDL thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Lỗi kết nối CSDL: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hiện form đăng nhập và đợi kết quả
        FormDangNhap formDangNhap = new FormDangNhap();
        formDangNhap.setVisible(true);

        // Chờ form đăng nhập đóng
        while (formDangNhap.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Lấy mã NV đăng nhập
        currentLoggedInUser = formDangNhap.getMaNV();

        if (currentLoggedInUser != null) {
            // Phân quyền dựa vào vai trò
            try {
                String sql = "SELECT ChucVu FROM NhanVien WHERE MaNV=?";
                Connection conn = ConnectDB.getInstance().getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, currentLoggedInUser);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String chucVu = rs.getString("ChucVu");
                    if ("Quản lý".equals(chucVu)) {
                        new GUI_NhanVienQuanLy().setVisible(true);
                    } else if ("Lễ tân".equals(chucVu)) {
                        new GUI_NhanVienLeTan().setVisible(true);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Lỗi kiểm tra vai trò: " + e.getMessage());
            }
        }
    }
// ...existing code...
}