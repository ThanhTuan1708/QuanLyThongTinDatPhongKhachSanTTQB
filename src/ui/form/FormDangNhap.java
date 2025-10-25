package ui.form;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import connectDB.ConnectDB;

public class FormDangNhap extends JFrame {
    private JTextField txtMaNV;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private String maNV = null;
    
    public FormDangNhap() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel lblHeader = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblHeader, BorderLayout.NORTH);
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        txtMaNV = new JTextField();
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        txtMatKhau = new JPasswordField();
        
        inputPanel.add(lblMaNV);
        inputPanel.add(txtMaNV);
        inputPanel.add(lblMatKhau);
        inputPanel.add(txtMatKhau);
        
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(btnDangNhap);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Event handling
        btnDangNhap.addActionListener(e -> {
            String maNV = txtMaNV.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword());
            
            // Validate input
            if(maNV.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if(checkLogin(maNV, matKhau)) {
                this.maNV = maNV;
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Sai mã nhân viên hoặc mật khẩu!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
                txtMatKhau.setText("");
                txtMatKhau.requestFocus();
            }
        });

        // Press Enter to login
        txtMatKhau.addActionListener(e -> btnDangNhap.doClick());
    }
    
    private boolean checkLogin(String maNV, String matKhau) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=? AND MatKhau=?";
        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, maNV);
            pst.setString(2, matKhau);
            
            ResultSet rs = pst.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi kết nối CSDL: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public String getMaNV() {
        return maNV;
    }

    // Test form
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new FormDangNhap().setVisible(true);
        });
    }
}