package ui.form;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import connectDB.ConnectDB;
import ui.gui.GUI_NhanVienLeTan;
import ui.gui.GUI_NhanVienQuanLy;

public class FormDangNhap extends JFrame {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnEye;
    private String maNV = null;
    private String chucVu = null;

    private final String PLACEHOLDER_USER = "Nhập tên đăng nhập";
    private final String PLACEHOLDER_PASS = "Nhập mật khẩu";

    public FormDangNhap() {
        setTitle("TBQTT Hotel - Đăng nhập");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Background with gradient
        BackgroundPanel bg = new BackgroundPanel();
        bg.setLayout(new GridBagLayout());
        add(bg, BorderLayout.CENTER);

        // Login card
        CardPanel card = new CardPanel();
        card.setPreferredSize(new Dimension(520, 520));
        card.setLayout(new BorderLayout());
        bg.add(card);

        // Logo and titles
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel logoWrap = new JPanel(new GridBagLayout());
        logoWrap.setOpaque(false);
        
        // Load logo if exists
        try {
            ImageIcon logo = new ImageIcon("img/logo.png");
            Image img = logo.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            logoWrap.add(new JLabel(new ImageIcon(img)));
        } catch (Exception e) {
            // Logo missing - use text
            JLabel textLogo = new JLabel("TBQTT", SwingConstants.CENTER);
            textLogo.setFont(new Font("Arial", Font.BOLD, 48));
            logoWrap.add(textLogo);
        }
        top.add(logoWrap, BorderLayout.NORTH);

        JLabel title = new JLabel("TBQTT Hotel", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(40, 44, 52));
        top.add(title, BorderLayout.CENTER);

        JLabel subtitle = new JLabel("Hệ thống quản lý khách sạn", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(110, 118, 130));
        top.add(subtitle, BorderLayout.SOUTH);

        top.setBorder(new EmptyBorder(24, 24, 12, 24));
        card.add(top, BorderLayout.NORTH);

        // ...existing code...
        // Login form
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;

        // Label cho Tên đăng nhập
        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập");
        lblTenDangNhap.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTenDangNhap.setForeground(new Color(80, 90, 100));
        form.add(lblTenDangNhap, c);

        c.gridy++;
        txtTenDangNhap = new JTextField();
        styleField(txtTenDangNhap);
        setPlaceholder(txtTenDangNhap, PLACEHOLDER_USER);
        form.add(txtTenDangNhap, c);

        // Label cho Mật khẩu
        c.gridy++;
        JLabel lblMatKhau = new JLabel("Mật khẩu");
        lblMatKhau.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblMatKhau.setForeground(new Color(80, 90, 100));
        form.add(lblMatKhau, c);

        c.gridy++;
        JPanel passWrap = new JPanel(new BorderLayout(6,0));
        passWrap.setOpaque(false);
        txtMatKhau = new JPasswordField();
        styleField(txtMatKhau);
        setPlaceholder(txtMatKhau, PLACEHOLDER_PASS);

        btnEye = new JButton("\uD83D\uDC41");
        btnEye.setPreferredSize(new Dimension(44, 36));
        btnEye.setFocusPainted(false);
        btnEye.setBorder(BorderFactory.createLineBorder(new Color(230,230,235)));
        btnEye.setBackground(Color.WHITE);
        btnEye.addActionListener(e -> togglePasswordVisibility());

        passWrap.add(txtMatKhau, BorderLayout.CENTER);
        passWrap.add(btnEye, BorderLayout.EAST);
        form.add(passWrap, c);

        // ...existing code...
// Nút Đăng nhập (màu xanh)
c.gridy++;
btnDangNhap = new JButton("Đăng nhập");
final Color BTN_GREEN = new Color(39, 174, 96);
btnDangNhap.setPreferredSize(new Dimension(200, 44));
btnDangNhap.setBackground(BTN_GREEN);
btnDangNhap.setForeground(Color.WHITE);
btnDangNhap.setFocusPainted(false);
btnDangNhap.setBorder(BorderFactory.createEmptyBorder());
btnDangNhap.setOpaque(true);
btnDangNhap.setContentAreaFilled(true);
btnDangNhap.setBorderPainted(false);
btnDangNhap.setRequestFocusEnabled(false);

// Giữ nguyên màu trong mọi trạng thái (ấn, hover, focus)
btnDangNhap.setUI(new javax.swing.plaf.basic.BasicButtonUI());
btnDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override public void mousePressed(java.awt.event.MouseEvent e) { btnDangNhap.setBackground(BTN_GREEN); }
    @Override public void mouseReleased(java.awt.event.MouseEvent e) { btnDangNhap.setBackground(BTN_GREEN); }
    @Override public void mouseEntered(java.awt.event.MouseEvent e) { btnDangNhap.setBackground(BTN_GREEN); }
    @Override public void mouseExited(java.awt.event.MouseEvent e) { btnDangNhap.setBackground(BTN_GREEN); }
});

form.add(btnDangNhap, c);
// ...existing code...
        c.gridy++;
        JLabel footer = new JLabel("© 2025 TBQTT Hotel.", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(new Color(150, 160, 175));
        form.add(footer, c);

        card.add(form, BorderLayout.CENTER);

        // Event handlers
        btnDangNhap.addActionListener(e -> onLogin());
        txtMatKhau.addActionListener(e -> btnDangNhap.doClick());
        txtTenDangNhap.addActionListener(e -> txtMatKhau.requestFocusInWindow());
    }

    private void styleField(JTextField f) {
        f.setPreferredSize(new Dimension(320, 36));
        f.setBackground(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230,230,235)),
            BorderFactory.createEmptyBorder(6,10,6,10)
        ));
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private void setPlaceholder(JTextComponent comp, String text) {
        comp.setText(text);
        comp.setForeground(new Color(150,150,160));
        comp.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if(comp.getText().equals(text)) {
                    comp.setText("");
                    comp.setForeground(new Color(30,30,35));
                    if(comp instanceof JPasswordField) {
                        ((JPasswordField)comp).setEchoChar('\u2022');
                    }
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if(comp.getText().isEmpty()) {
                    comp.setText(text);
                    comp.setForeground(new Color(150,150,160));
                    if(comp instanceof JPasswordField) {
                        ((JPasswordField)comp).setEchoChar((char)0);
                    }
                }
            }
        });
        if(comp instanceof JPasswordField) {
            ((JPasswordField)comp).setEchoChar((char)0);
        }
    }

    private void togglePasswordVisibility() {
        if(String.valueOf(txtMatKhau.getPassword()).equals(PLACEHOLDER_PASS)) return;
        
        if(txtMatKhau.getEchoChar() == (char)0) {
            txtMatKhau.setEchoChar('\u2022');
        } else {
            txtMatKhau.setEchoChar((char)0);
        }
    }

    // ...existing code...
    private void onLogin() {
        String user = txtTenDangNhap.getText().trim();
        String pass = new String(txtMatKhau.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maLoaiNV FROM NhanVien WHERE maNV=? AND matKhau=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, user);
                pst.setString(2, pass);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        int maLoaiNV = rs.getInt("maLoaiNV");

                        // lưu thông tin để Application đọc sau khi form đóng
                        this.maNV = user;
                        if (maLoaiNV == 2) this.chucVu = "Quản lý";
                        else if (maLoaiNV == 1) this.chucVu = "Lễ tân";
                        else this.chucVu = "Khác";

                        // đóng form (Application sẽ mở GUI tương ứng)
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sai mã nhân viên hoặc mật khẩu", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                        txtMatKhau.setText("");
                        txtMatKhau.requestFocus();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
// ...existing code...

    // Thêm 2 getter để Application gọi được
    public String getMaNV() {
        return maNV;
    }

    public String getChucVu() {
        return chucVu;
    }
// ...existing code...

// Mở giao diện Quản lý
private void openManagerGUI() {
    GUI_NhanVienQuanLy formQL = new GUI_NhanVienQuanLy(this.maNV);
    formQL.setLocationRelativeTo(null);
    formQL.setVisible(true);
}

// Mở giao diện Lễ tân
private void openReceptionistGUI() {
    GUI_NhanVienLeTan formLT = new GUI_NhanVienLeTan(this.maNV); 
    formLT.setLocationRelativeTo(null);
    formLT.setVisible(true);
}

    // Custom panels for UI
    static class BackgroundPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g.create();
            int w = getWidth(), h = getHeight();
            
            // Gradient background
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(245,248,255),
                0, h, new Color(252,254,255)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            
            // Decorative circles
            g2.setColor(new Color(200,230,255,80));
            g2.fillOval(w-360, 40, 300, 300);
            g2.setColor(new Color(255,220,235,60));
            g2.fillOval(40, h-360, 300, 300);
            
            g2.dispose();
        }
    }

    static class CardPanel extends JPanel {
        public CardPanel() {
            setOpaque(false);
        }
        
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            
            int w = getWidth(), h = getHeight(), arc = 18;
            
            // Shadow
            g2.setColor(new Color(0,0,0,30));
            g2.fillRoundRect(6, 8, w-12, h-12, arc, arc);
            
            // Card
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, w-12, h-12, arc, arc);
            
            g2.dispose();
            super.paintComponent(g);
        }
        
        @Override public boolean isOpaque() {
            return false;
        }
    }
}