package ui.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

// ---------------------------
// Chú thích metadata (comment)
// Người code: Đỗ Nguyễn Thanh Bình
// Mô tả: Thêm nhãn chú thích hiển thị tên người chịu trách nhiệm / hoàn thiện phần giao diện Quản lý phòng
// Mục đích: Quản lý code, dễ dàng liên hệ khi cần chỉnh sửa
// Ngày tạo: 23/10/2025
// Giờ tạo: 01:55
// Lưu ý: cập nhật thời gian/ người sửa khi chỉnh sửa tiếp
// ---------------------------
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import connectDB.ConnectDB;
import dao.PhongDAO;
import dao.DichVuDAO;
import dao.KhuyenMaiDAO;
import entity.Phong;
import entity.DichVu;
import entity.KhuyenMai;

// =================================================================================
// LỚP CHÍNH (JFrame) - CHỨA SIDEBAR CỐ ĐỊNH VÀ CARDLAYOUT CHO NỘI DUNG
// =================================================================================
public class GUI_NhanVienLeTan extends JFrame {
    private String maNV;
    private String tenNV;
    private String email;
    private String soDT;
    
    // Constructor nhận maNV
    public GUI_NhanVienLeTan(String maNV) {
        this(); // gọi constructor mặc định
        System.out.println("Constructor with maNV called: " + maNV); // Debug
        this.maNV = maNV;
        loadNhanVienInfo();
        initAfterLogin();
        System.out.println("After loading info - tenNV: " + this.tenNV + ", email: " + this.email + ", soDT: " + this.soDT); // Debug
    }

    private void loadNhanVienInfo() {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT hoTen, email, sdt FROM NhanVien WHERE maNV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNV);
            System.out.println("Executing query with maNV: " + maNV); // Debug
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                this.tenNV = rs.getString("hoTen");
                this.email = rs.getString("email");
                this.soDT = rs.getString("sdt");
                System.out.println("Found: tenNV=" + this.tenNV + ", email=" + this.email + ", sdt=" + this.soDT); // Debug
            } else {
                System.out.println("No results found for maNV: " + maNV); // Debug
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initAfterLogin() {
        if (maNV != null) {
            setTitle(getTitle() + " - " + (tenNV != null ? tenNV : maNV));
            
            // Tạo lại các panel sau khi có thông tin
            getContentPane().removeAll();
            add(createStaticSidebar(), BorderLayout.WEST);
            
            cardLayout = new CardLayout();
            contentPanelContainer = new JPanel(cardLayout);
            contentPanelContainer.setBackground(MAIN_BG);

            // Tạo lại các panel với thông tin mới
            PanelLeTanContent panelLeTanContent = new PanelLeTanContent(maNV, tenNV, email, soDT);
            PanelDatPhongContent panelDatPhongContent = new PanelDatPhongContent();
            PanelKhachHangContent panelKhachHangContent = new PanelKhachHangContent();
            PanelDichVuContent panelDichVuContent = new PanelDichVuContent();
            PanelPhongContent panelPhongContent = new PanelPhongContent();

            contentPanelContainer.add(panelLeTanContent, "LE_TAN_CONTENT");
            contentPanelContainer.add(panelDatPhongContent, "DAT_PHONG_CONTENT");
            contentPanelContainer.add(panelKhachHangContent, "KHACH_HANG_CONTENT");
            contentPanelContainer.add(panelDichVuContent, "DICH_VU_CONTENT");
            contentPanelContainer.add(panelPhongContent, "PHONG_CONTENT");

            add(contentPanelContainer, BorderLayout.CENTER);
            
            // Hiển thị nội dung Lễ tân đầu tiên
            showContentPanel("LE_TAN_CONTENT");
            
            // Yêu cầu cập nhật giao diện
            revalidate();
            repaint();
        }
    }
    // --- Các hằng số màu sắc ---
    public static final Color SIDEBAR_BG = new Color(240, 248, 255);
    public static final Color MAIN_BG = new Color(242, 245, 250);
    public static final Color CARD_BORDER = new Color(222, 226, 230);
    public static final Color ACCENT_BLUE = new Color(24, 90, 219);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_GREEN = new Color(50, 168, 82);
    public static final Color COLOR_RED = new Color(217, 30, 24);
    public static final Color COLOR_ORANGE = new Color(245, 124, 0);
    public static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
    public static final Color COLOR_AVATAR_BG = new Color(230, 230, 255);
    public static final Color STAT_BG_1 = new Color(218, 240, 255);
    public static final Color STAT_BG_2 = new Color(230, 235, 255);
    public static final Color STAT_BG_3 = new Color(255, 235, 240);

    public static final Color STATUS_GREEN_BG = new Color(225, 255, 230);
    public static final Color STATUS_GREEN_FG = new Color(30, 150, 50);
    public static final Color STATUS_RED_BG = new Color(255, 225, 225);
    public static final Color STATUS_RED_FG = new Color(180, 50, 50);
    public static final Color STATUS_ORANGE_BG = new Color(255, 240, 220);
    public static final Color STATUS_ORANGE_FG = new Color(245, 124, 0);
    public static final Color STATUS_YELLOW_BG = new Color(255, 250, 225);
    public static final Color STATUS_YELLOW_FG = new Color(180, 150, 0);

    private CardLayout cardLayout;
    private JPanel contentPanelContainer; 

    private JButton btnDashboard;
    private JButton btnDatPhong;
    private JButton btnKhachHang;
    private JButton btnDichVu;
    private JButton btnPhong;
    
    public GUI_NhanVienLeTan() {
        setTitle("Quản lý Khách sạn TBQTT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        // 1. Tạo Sidebar cố định và đặt vào WEST
        add(createStaticSidebar(), BorderLayout.WEST);

        // 2. Tạo Panel chứa CardLayout cho nội dung chính
        cardLayout = new CardLayout();
        contentPanelContainer = new JPanel(cardLayout);
        contentPanelContainer.setBackground(MAIN_BG); 

        // 3. Tạo các Panel nội dung riêng biệt
        PanelLeTanContent panelLeTanContent = new PanelLeTanContent(maNV, tenNV, email, soDT);
        PanelDatPhongContent panelDatPhongContent = new PanelDatPhongContent();
        PanelKhachHangContent panelKhachHangContent = new PanelKhachHangContent();
        PanelDichVuContent panelDichVuContent = new PanelDichVuContent();
        PanelPhongContent panelPhongContent = new PanelPhongContent(); 

        // 4. Thêm các Panel nội dung vào CardLayout
        contentPanelContainer.add(panelLeTanContent, "LE_TAN_CONTENT");
        contentPanelContainer.add(panelDatPhongContent, "DAT_PHONG_CONTENT");
        contentPanelContainer.add(panelKhachHangContent, "KHACH_HANG_CONTENT");
        contentPanelContainer.add(panelDichVuContent, "DICH_VU_CONTENT");
        contentPanelContainer.add(panelPhongContent, "PHONG_CONTENT");

        // 5. Thêm Panel CardLayout vào CENTER của JFrame
        add(contentPanelContainer, BorderLayout.CENTER);

        // Hiển thị nội dung Lễ tân đầu tiên
        showContentPanel("LE_TAN_CONTENT");
    }

    /**
     * Tạo Sidebar cố định (chỉ gọi 1 lần khi khởi tạo JFrame)
     */
    private JPanel createStaticSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(SIDEBAR_BG);

        // Khu vực Logo
        JPanel logo = new JPanel();
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.setBorder(new EmptyBorder(18, 18, 18, 18));
        logo.setOpaque(false);
        
        JLabel hotelName = new JLabel("TBQTT");
        hotelName.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel hotelType = new JLabel("HOTEL");
        hotelType.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JLabel subtitle = new JLabel("Hệ thống quản lý");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        logo.add(hotelName);
        logo.add(hotelType);
        logo.add(Box.createVerticalStrut(12));
        logo.add(subtitle);
        sidebar.add(logo, BorderLayout.NORTH);

        // Khu vực Menu
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(12, 12, 12, 12));
        menu.setOpaque(false);

        // Tạo các nút menu (lưu lại tham chiếu)
        btnDashboard = createNavButton("Dashboard");
        btnDatPhong = createNavButton("Đặt phòng");
        btnKhachHang = createNavButton("Khách hàng");
        btnDichVu = createNavButton("Dịch vụ");
        btnPhong = createNavButton("Phòng");

        // Gắn ActionListener để chuyển đổi content panel
        btnDashboard.addActionListener(e -> showContentPanel("LE_TAN_CONTENT"));
        btnDatPhong.addActionListener(e -> showContentPanel("DAT_PHONG_CONTENT"));
        btnPhong.addActionListener(e -> showContentPanel("PHONG_CONTENT"));
        btnKhachHang.addActionListener(e -> showContentPanel("KHACH_HANG_CONTENT"));
        btnDichVu.addActionListener(e -> showContentPanel("DICH_VU_CONTENT"));

        // Thêm nút vào menu
        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnDatPhong);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnKhachHang);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnPhong);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnDichVu);
        menu.add(Box.createVerticalStrut(8));

        sidebar.add(menu, BorderLayout.CENTER);

        // Khu vực Profile & Logout
        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setBorder(new EmptyBorder(12, 12, 12, 12));
        profile.setOpaque(false);
        
        JLabel user = new JLabel("admin");
        user.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel role = new JLabel("Nhân viên lễ tân");
        role.setFont(new Font("SansSerif", Font.PLAIN, 12));
        role.setForeground(Color.GRAY);
        JButton logout = new JButton("Đăng xuất");
        logout.setBorderPainted(false);
        logout.setContentAreaFilled(false);
        logout.setForeground(new Color(220, 50, 50));
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton exitButton = new JButton("Thoát Ứng Dụng");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setForeground(new Color(200, 0, 0));
        exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));

        profile.add(user);
        profile.add(role);
        profile.add(Box.createVerticalStrut(10));
        profile.add(logout);
        sidebar.add(profile, BorderLayout.SOUTH);

        // Đặt trạng thái active ban đầu cho nút Dashboard
        setActiveButton(btnDashboard);

        return sidebar;
    }

    /**
     * Helper: Tạo một nút điều hướng chuẩn
     */
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false); 
        return btn;
    }

    /**
     * Helper: Đặt trạng thái active cho nút được chọn và reset các nút khác
     */
    private void setActiveButton(JButton activeButton) {
        JButton[] allButtons = { btnDashboard, btnDatPhong, btnKhachHang, btnDichVu, btnPhong };
        for (JButton btn : allButtons) {
            if (btn == activeButton) {
                btn.setForeground(Color.WHITE);
                btn.setBackground(ACCENT_BLUE);
                btn.setOpaque(true); 
                btn.setBorder(new CompoundBorder(
                        new LineBorder(ACCENT_BLUE, 2, true),
                        new EmptyBorder(6, 12, 6, 12)));
            } else if (btn != null) { 
                btn.setForeground(Color.BLACK);
                btn.setOpaque(false); 
                btn.setBorder(new CompoundBorder(
                        new LineBorder(new Color(230, 230, 230)),
                        new EmptyBorder(6, 12, 6, 12)));
            }
        }
    }

    /**
     * Chuyển đổi Panel nội dung hiển thị trong CardLayout
     */
    public void showContentPanel(String panelName) {
        if (contentPanelContainer != null && cardLayout != null) {
            cardLayout.show(contentPanelContainer, panelName);
            contentPanelContainer.revalidate();
            contentPanelContainer.repaint();

            // Cập nhật trạng thái active của nút menu tương ứng
            if (panelName.equals("LE_TAN_CONTENT")) {
                setActiveButton(btnDashboard);
            } else if (panelName.equals("DAT_PHONG_CONTENT")) {
                setActiveButton(btnDatPhong);
            } else if (panelName.equals("PHONG_CONTENT")) {
                setActiveButton(btnPhong);
            } else if (panelName.equals("KHACH_HANG_CONTENT")) {
                setActiveButton(btnKhachHang);
            } else if (panelName.equals("DICH_VU_CONTENT")) {
                setActiveButton(btnDichVu);
            }
        }
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GUI_NhanVienLeTan().setVisible(true);
        });
    }
}

// =================================================================================
// PANEL NỘI DUNG 1: MÀN HÌNH DASHBOARD 
// =================================================================================
class PanelLeTanContent extends JPanel {
    private final Color STAT_BG_1 = new Color(218, 240, 255);
    private final Color STAT_BG_2 = new Color(230, 235, 255);
    private final Color STAT_BG_3 = new Color(255, 235, 240);

    private String maNV;
    private String tenNV;
    private String email;
    private String soDT;

    public PanelLeTanContent(String maNV, String tenNV, String email, String soDT) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.email = email;
        this.soDT = soDT;
        // --- Thiết lập cho JPanel này ---
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18)); 

        // --- Chỉ thêm Header và Content Panel ---
        add(createHeader(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Dashboard Nhân viên Lễ tân");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy");
        JLabel date = new JLabel(fmt.format(LocalDate.now()));
        date.setForeground(Color.GRAY);
        header.add(title, BorderLayout.WEST);
        header.add(date, BorderLayout.EAST);
        return header;
    }

    private JPanel createContentPanel() {
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);

        JPanel topZone = createTopProfileCard();
        topZone.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        content.add(topZone, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(12, 0, 12, 0);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.weightx = 1.0;

        gc.gridy = 0;
        gc.weighty = 0.4;
        JPanel schedulePanel = createSchedulePanel();
        schedulePanel.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(14, 14, 14, 14)));
        schedulePanel.setBackground(Color.WHITE);
        centerPanel.add(schedulePanel, gc);

        gc.gridy = 1;
        gc.weighty = 0.6;
        JPanel bottomSection = new JPanel(new GridBagLayout());
        bottomSection.setOpaque(false);

        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = GridBagConstraints.BOTH;
        gc2.insets = new Insets(12, 0, 12, 12);

        gc2.gridx = 0;
        gc2.gridy = 0;
        gc2.weightx = 0.65;
        gc2.weighty = 1.0;
        bottomSection.add(createTasksPanel(), gc2);

        gc2.gridx = 1;
        gc2.weightx = 0.35;
        gc2.insets = new Insets(12, 0, 12, 0);
        bottomSection.add(createStatsPanel(), gc2);

        centerPanel.add(bottomSection, gc);
        content.add(centerPanel, BorderLayout.CENTER);
        return content;
    }

    private JPanel createTopProfileCard() {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(14, 14, 14, 14)));
        card.setBackground(Color.WHITE);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        left.setOpaque(false);

        JLabel avatar = new JLabel("LT");
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(120, 150, 255));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 20));
        left.add(avatar);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel name = new JLabel(tenNV != null ? tenNV : "Chưa có tên");
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel details = new JLabel(String.format("<html>%s • %s • Mã NV: %s</html>",
            email != null ? email : "Chưa có email",
            soDT != null ? soDT : "Chưa có SĐT",
            maNV != null ? maNV : "Không có mã"));
        details.setForeground(Color.GRAY);
        details.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info.add(name);
        info.add(Box.createVerticalStrut(4));
        info.add(details);
        left.add(info);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);
        right.add(createStatBox("24", "Giờ tuần này", STAT_BG_1));
        right.add(createStatBox("12", "Check-in", STAT_BG_2));
        right.add(createStatBox("10", "Check-out", STAT_BG_3));

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    private JPanel createSchedulePanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        JLabel title = new JLabel("Lịch làm việc tuần này");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        wrap.add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel();
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
        grid.setOpaque(false);

        String[][] scheduleData = {
                { "Thứ Hai", "15/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 5:55", "Ra: 14:05", "Hoàn thành" },
                { "Thứ Ba", "16/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 6:00", "Ra: 14:00", "Hoàn thành" },
                { "Thứ Tư", "17/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 5:58", "", "Đang làm" },
                { "Thứ Năm", "18/1", "Sáng", "6:00 - 14:00", "8 giờ", "", "", "" },
                { "Thứ Sáu", "19/1", "Sáng", "6:00 - 14:00", "8 giờ", "", "", "" }
        };
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (String[] row : scheduleData) {
            JPanel dayRow = new JPanel(new BorderLayout(15, 0));
            dayRow.setOpaque(true);
            dayRow.setBackground(Color.WHITE);
            dayRow.setBorder(new CompoundBorder(
                    new EmptyBorder(8, 8, 8, 8),
                    new LineBorder(new Color(240, 240, 245))));
            dayRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

            JPanel dayCol = new JPanel();
            dayCol.setLayout(new BoxLayout(dayCol, BoxLayout.Y_AXIS));
            dayCol.setOpaque(false);
            dayCol.setPreferredSize(new Dimension(100, 40));

            JLabel dayName = new JLabel(row[0]);
            dayName.setFont(new Font("SansSerif", Font.BOLD, 12));
            JLabel date = new JLabel(row[1]);
            date.setFont(new Font("SansSerif", Font.PLAIN, 11));
            date.setForeground(Color.GRAY);

            dayCol.add(dayName);
            dayCol.add(Box.createVerticalStrut(2));
            dayCol.add(date);
            dayRow.add(dayCol, BorderLayout.WEST);

            JPanel shiftCol = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            shiftCol.setOpaque(false);
            JLabel shift = new JLabel(row[2] + " (" + row[3] + ")");
            shift.setFont(new Font("SansSerif", Font.PLAIN, 12));
            JLabel hours = new JLabel("  " + row[4]);
            hours.setFont(new Font("SansSerif", Font.PLAIN, 11));
            hours.setForeground(Color.GRAY);
            shiftCol.add(shift);
            shiftCol.add(hours);
            dayRow.add(shiftCol, BorderLayout.CENTER);

            JPanel statusCol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            statusCol.setOpaque(false);
            statusCol.setPreferredSize(new Dimension(280, 40));

            if (!row[5].isEmpty()) {
                JLabel timeIn = new JLabel(row[5]);
                timeIn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                timeIn.setForeground(Color.GRAY);
                statusCol.add(timeIn);
            }
            if (!row[6].isEmpty()) {
                JLabel timeOut = new JLabel(row[6]);
                timeOut.setFont(new Font("SansSerif", Font.PLAIN, 11));
                timeOut.setForeground(Color.GRAY);
                statusCol.add(timeOut);
            }
            if (!row[7].isEmpty()) {
                JLabel status = new JLabel(row[7]);
                status.setOpaque(true);
                status.setFont(new Font("SansSerif", Font.PLAIN, 11));
                status.setBorder(new EmptyBorder(4, 10, 4, 10));
                if (row[7].equals("Hoàn thành")) {
                    status.setBackground(new Color(220, 255, 230));
                    status.setForeground(new Color(25, 120, 50));
                } else if (row[7].equals("Đang làm")) {
                    status.setBackground(new Color(230, 245, 255));
                    status.setForeground(new Color(10, 90, 180));
                }
                statusCol.add(status);
            }
            dayRow.add(statusCol, BorderLayout.EAST);

            DayOfWeek rowDay = getDayOfWeek(row[0]);
            if (rowDay == today) {
                // Highlight hàng cho ngày hiện tại
                dayRow.setBorder(new CompoundBorder(
                        new EmptyBorder(8, 8, 8, 8),
                        new LineBorder(new Color(100, 150, 255), 2, true)));
            }
            grid.add(dayRow);
            grid.add(Box.createVerticalStrut(6));
        }
        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        wrap.add(scroll, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel createTasksPanel() {
        JPanel tasks = new JPanel(new BorderLayout());
        tasks.setBackground(Color.WHITE);
        tasks.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(14, 14, 14, 14)));

        JLabel title = new JLabel("Nhiệm vụ & Thống kê hôm nay");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        tasks.add(title, BorderLayout.NORTH);

        String[] columns = { "Thời gian", "Nhiệm vụ", "Trạng thái", "Ghi chú" };
        Object[][] data = {
                { "07:30", "Check-in 3 khách mới", "Hoàn thành", "Phòng 101, 102, 103" },
                { "10:00", "Check-out 2 phòng", "Hoàn thành", "Phòng 201, 202" },
                { "11:15", "Xử lý yêu cầu phòng 301", "Hoàn thành", "Yêu cầu thêm khăn" },
                { "13:30", "Chuẩn bị báo cáo ca làm", "Chưa xong", "" },
                { "15:00", "Kiểm tra phòng trống", "Đang làm", "" }
        };

        JTable table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(40);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getTableHeader().setReorderingAllowed(false);

        // Custom renderer cho cột Trạng thái
        table.getColumnModel().getColumn(2).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(new EmptyBorder(4, 8, 4, 8));
                String status = value.toString();
                if (status.equals("Hoàn thành")) {
                    label.setBackground(new Color(220, 255, 230));
                    label.setForeground(new Color(25, 120, 50));
                } else if (status.equals("Đang làm")) {
                    label.setBackground(new Color(230, 245, 255));
                    label.setForeground(new Color(10, 90, 180));
                } else if (status.equals("Chưa xong")) {
                    label.setBackground(new Color(255, 245, 230));
                    label.setForeground(new Color(180, 110, 20));
                }
                return label;
            }
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        tasks.add(scroll, BorderLayout.CENTER);
        return tasks;
    }

    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(3, 2, 12, 12));
        stats.setOpaque(false);
        stats.add(createStatCard("24h", "Tổng giờ làm", STAT_BG_1));
        stats.add(createStatCard("2/5", "Ca hoàn thành", new Color(220, 255, 230)));
        stats.add(createStatCard("12", "Check-in tuần", new Color(245, 235, 255)));
        stats.add(createStatCard("10", "Check-out tuần", new Color(255, 240, 230)));
        stats.add(createStatCard("3", "Yêu cầu", new Color(255, 235, 245)));
        stats.add(createStatCard("1h", "Tăng ca", new Color(250, 245, 230)));
        return stats;
    }

    private JPanel createStatBox(String value, String label, Color bg) {
        JPanel box = new JPanel(new BorderLayout());
        box.setPreferredSize(new Dimension(110, 60));
        box.setBackground(bg);
        box.setBorder(new LineBorder(GUI_NhanVienLeTan.CARD_BORDER));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textLabel.setForeground(Color.GRAY);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(valueLabel, BorderLayout.CENTER);
        box.add(textLabel, BorderLayout.SOUTH);
        return box;
    }

    private JPanel createStatCard(String value, String label, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(new LineBorder(GUI_NhanVienLeTan.CARD_BORDER));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textLabel.setForeground(Color.GRAY);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);
        return card;
    }

    private DayOfWeek getDayOfWeek(String dayName) {
        switch (dayName) {
            case "Thứ Hai":
                return DayOfWeek.MONDAY;
            case "Thứ Ba":
                return DayOfWeek.TUESDAY;
            case "Thứ Tư":
                return DayOfWeek.WEDNESDAY;
            case "Thứ Năm":
                return DayOfWeek.THURSDAY;
            case "Thứ Sáu":
                return DayOfWeek.FRIDAY;
            case "Thứ Bảy":
                return DayOfWeek.SATURDAY;
            case "Chủ Nhật":
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }
}

// =================================================================================
// PANEL NỘI DUNG 2: ĐẶT PHÒNG
// =================================================================================
class PanelDatPhongContent extends JPanel {
    private PhongDAO phongDAO;
    private DichVuDAO dichVuDAO;
    // KhuyenMaiDAO được sử dụng cho việc áp dụng mã khuyến mãi
    private List<Phong> danhSachPhong;
    private List<Phong> danhSachPhongDaLoc;
    private List<Phong> danhSachPhongDaChon;
    private List<DichVu> danhSachDichVu;
    private List<DichVu> danhSachDichVuDaChon;
    private KhuyenMai khuyenMaiDangDung;
    private String locLoaiPhong = "Tất cả";
    private String locSoNguoi = "Tất cả";
    private double tongTienPhong = 0;
    private double tongTienDichVu = 0;
    private double chietKhau = 0;
    private double tongTienSauGiam = 0;

    private JTextField promoField; // Field for promotion code
    // Connection sẽ được quản lý trong các phương thức cụ thể

    public PanelDatPhongContent() {
        // Khởi tạo các DAO
        phongDAO = new PhongDAO();
        dichVuDAO = new DichVuDAO();

        // Khởi tạo các danh sách
        danhSachPhongDaChon = new ArrayList<>();
        danhSachDichVuDaChon = new ArrayList<>();
        
        // Thiết lập layout và background
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Load dữ liệu và hiển thị UI
        loadDanhSachPhong();
        loadDanhSachDichVu();
        loadLatestBookings(); // Load và hiển thị danh sách booking ngay từ đầu
    }

    private void loadDanhSachDichVu() {
        try {
            danhSachDichVu = dichVuDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách dịch vụ: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tinhTongTien() {
        try {
            // Tính tổng tiền phòng
            tongTienPhong = danhSachPhongDaChon.stream()
                .mapToDouble(Phong::getGiaTienMotDem)
                .sum();

            // Tính tổng tiền dịch vụ
            tongTienDichVu = danhSachDichVuDaChon.stream()
                .mapToDouble(DichVu::getGia)
                .sum();

            // Tính chiết khấu nếu có
            double tongTienTruocGiam = tongTienPhong + tongTienDichVu;
            if (khuyenMaiDangDung != null) {
                System.out.println("Applying discount: " + khuyenMaiDangDung.getChietKhau() + "%"); // Debug log
                chietKhau = tongTienTruocGiam * (khuyenMaiDangDung.getChietKhau() / 100.0);
            } else {
                chietKhau = 0;
            }

            // Tính tổng tiền sau giảm
            tongTienSauGiam = tongTienTruocGiam - chietKhau;
            
            // Debug log
            System.out.println("Tổng tiền phòng: " + tongTienPhong);
            System.out.println("Tổng tiền dịch vụ: " + tongTienDichVu);
            System.out.println("Chiết khấu: " + chietKhau);
            System.out.println("Tổng sau giảm: " + tongTienSauGiam);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in tinhTongTien: " + e.getMessage());
        }
    }

    private void loadDanhSachPhong() {
        try {
            danhSachPhong = phongDAO.getAll();
            danhSachPhongDaLoc = new ArrayList<>(danhSachPhong);
            refreshUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách phòng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void refreshUI() {
        removeAll();
        
        // --- Thiết lập cho JPanel này ---
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15)); 

        // --- Tạo panel chính chứa tất cả nội dung ---
        JPanel mainContentPanel = new JPanel(new BorderLayout(0, 20));
        mainContentPanel.setOpaque(false);

        // Header
        mainContentPanel.add(createHeader(), BorderLayout.NORTH);

        // Panel chứa cả booking list và room selection
        JPanel contentWrapper = new JPanel(new BorderLayout(0, 20));
        contentWrapper.setOpaque(false);

        // Phần trên: Booking list
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setOpaque(false);
        bookingPanel.setPreferredSize(new Dimension(0, 300)); // Chiều cao cố định
        
        // Thêm thanh tìm kiếm và bộ lọc
        bookingPanel.add(createSearchFilterPanel(), BorderLayout.NORTH);
        
        // Load và thêm danh sách booking
        JScrollPane bookingList = loadLatestBookings();
        bookingPanel.add(bookingList, BorderLayout.CENTER);

        // Phần dưới: Room selection
        JPanel roomSelectionPanel = createRoomSelectionPanel();

        // Thêm vào content wrapper
        contentWrapper.add(bookingPanel, BorderLayout.NORTH);
        contentWrapper.add(roomSelectionPanel, BorderLayout.CENTER);

        // Thêm content wrapper vào main panel
        mainContentPanel.add(contentWrapper, BorderLayout.CENTER);

        // Thêm panel thanh toán nếu có phòng được chọn
        if (!danhSachPhongDaChon.isEmpty()) {
            mainContentPanel.add(createCheckoutPanel(), BorderLayout.SOUTH);
        }

        // Thêm tất cả vào panel chính
        add(mainContentPanel);

        // Cập nhật giao diện
        revalidate();
        repaint();
    }

    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, GUI_NhanVienLeTan.CARD_BORDER));

        tinhTongTien();
        JLabel infoLabel = new JLabel(String.format("Đã chọn %d phòng - Tổng tiền: %,.0f đ", 
            danhSachPhongDaChon.size(), tongTienSauGiam));
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnThanhToan = new JButton("Tiến hành đặt phòng");
        btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnThanhToan.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThanhToan.setBorder(new EmptyBorder(8, 15, 8, 15));

        btnThanhToan.addActionListener(e -> handleMultipleRoomBooking());

        panel.add(infoLabel);
        panel.add(btnThanhToan);
        return panel;
    }

    private void handleMultipleRoomBooking() {
        if (danhSachPhongDaChon.isEmpty()) {
            return;
        }
        
        // Khởi tạo danh sách dịch vụ nếu chưa có
        if (danhSachDichVuDaChon == null) {
            danhSachDichVuDaChon = new ArrayList<>();
        }

        // Tạo dialog thông tin đặt phòng
        JDialog bookingDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thông tin khách hàng", true);
        bookingDialog.setLayout(new BorderLayout(10, 10));
        bookingDialog.setSize(800, 720);
        bookingDialog.setLocationRelativeTo(null);

        // Panel chính sử dụng BoxLayout để xếp các thành phần theo chiều dọc
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // 1. Tiêu đề các phòng đã chọn
        JPanel roomListHeader = new JPanel(new BorderLayout());
        roomListHeader.setOpaque(false);
        JLabel roomListTitle = new JLabel("Các phòng đã chọn (" + danhSachPhongDaChon.size() + ")");
        roomListTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel roomListSubtitle = new JLabel("Thông tin booking sẽ được áp dụng cho tất cả các phòng");
        roomListSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roomListSubtitle.setForeground(Color.GRAY);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(roomListTitle);
        titlePanel.add(roomListSubtitle);
        
        roomListHeader.add(titlePanel, BorderLayout.WEST);
        mainPanel.add(roomListHeader);
        mainPanel.add(Box.createVerticalStrut(10));

        // Danh sách phòng
        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.setOpaque(false);
        
        for (Phong phong : danhSachPhongDaChon) {
            JPanel roomCard = new JPanel(new BorderLayout(10, 0));
            roomCard.setBackground(new Color(248, 249, 250));
            roomCard.setBorder(new EmptyBorder(12, 15, 12, 15));
            
            JPanel roomInfo = new JPanel();
            roomInfo.setLayout(new BoxLayout(roomInfo, BoxLayout.Y_AXIS));
            roomInfo.setOpaque(false);
            
            JLabel roomName = new JLabel("Phòng " + phong.getMaPhong());
            roomName.setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            JLabel roomType = new JLabel(phong.getLoaiPhong());
            roomType.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            roomType.setForeground(Color.GRAY);
            
            JLabel capacity = new JLabel("Sức chứa: 2 người");
            capacity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            roomInfo.add(roomName);
            roomInfo.add(roomType);
            roomInfo.add(capacity);
            
            JLabel priceLabel = new JLabel(String.format("%,.0f đ/đêm", phong.getGiaTienMotDem()));
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            roomCard.add(roomInfo, BorderLayout.WEST);
            roomCard.add(priceLabel, BorderLayout.EAST);
            
            roomsPanel.add(roomCard);
            roomsPanel.add(Box.createVerticalStrut(8));
        }
        mainPanel.add(roomsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // 2. Form thông tin khách hàng
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(6, 2, 20, 15));
        customerPanel.setOpaque(false);
        customerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Tên khách hàng
        JLabel nameLabel = new JLabel("Tên khách hàng *");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField nameField = createStyledTextField("Nhập tên đầy đủ");
        
        // Email
        JLabel emailLabel = new JLabel("Email *");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField emailField = createStyledTextField("email@example.com");
        
        // Số điện thoại
        JLabel phoneLabel = new JLabel("Số điện thoại *");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField phoneField = createStyledTextField("+84 xxx xxx xxx");
        
        // Số khách
        JLabel guestLabel = new JLabel("Số khách *");
        guestLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField guestField = createStyledTextField("1");
        
        // Ngày nhận phòng
        JLabel checkInLabel = new JLabel("Ngày nhận phòng *");
        checkInLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField checkInField = createStyledTextField("dd/mm/yyyy");
        
        // Ngày trả phòng
        JLabel checkOutLabel = new JLabel("Ngày trả phòng *");
        checkOutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTextField checkOutField = createStyledTextField("dd/mm/yyyy");

        customerPanel.add(nameLabel);
        customerPanel.add(nameField);
        customerPanel.add(emailLabel);
        customerPanel.add(emailField);
        customerPanel.add(phoneLabel);
        customerPanel.add(phoneField);
        customerPanel.add(guestLabel);
        customerPanel.add(guestField);
        customerPanel.add(checkInLabel);
        customerPanel.add(checkInField);
        customerPanel.add(checkOutLabel);
        customerPanel.add(checkOutField);

        mainPanel.add(customerPanel);

        // 3. Yêu cầu đặc biệt
        JLabel specialRequestLabel = new JLabel("Yêu cầu đặc biệt");
        specialRequestLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mainPanel.add(specialRequestLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        
        JTextArea specialRequestArea = new JTextArea(3, 20);
        specialRequestArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        specialRequestArea.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        specialRequestArea.setLineWrap(true);
        specialRequestArea.setWrapStyleWord(true);
        mainPanel.add(specialRequestArea);
        mainPanel.add(Box.createVerticalStrut(20));

        // 4. Dịch vụ (tùy chọn)
        JLabel servicesLabel = new JLabel("Chọn dịch vụ (tùy chọn)");
        servicesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mainPanel.add(servicesLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel servicesPanel = new JPanel();
        servicesPanel.setLayout(new BoxLayout(servicesPanel, BoxLayout.Y_AXIS));
        servicesPanel.setOpaque(false);

        // Lấy dịch vụ từ database
        try {
            danhSachDichVu = dichVuDAO.getAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage("Lỗi khi tải danh sách dịch vụ!");
            return;
        }

        for (DichVu dichVu : danhSachDichVu) {
            JPanel serviceCard = createServiceCard(
                dichVu.getTenDichVu(),
                String.format("%,.0f", dichVu.getGia()),
                "60", // Giá trị mặc định cho thời gian
                dichVu.getMoTa()
            );
            servicesPanel.add(serviceCard);
            servicesPanel.add(Box.createVerticalStrut(8));
        }

        mainPanel.add(servicesPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // 5. Mã khuyến mãi
        JLabel promoLabel = new JLabel("Mã khuyến mãi");
        promoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mainPanel.add(promoLabel);
        mainPanel.add(Box.createVerticalStrut(5));

        JPanel promoPanel = new JPanel(new BorderLayout(10, 0));
        promoPanel.setOpaque(false);
        
        promoField = createStyledTextField("NHẬP MÃ KHUYẾN MÃI");
        JButton applyButton = new JButton("Áp dụng");
        applyButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        applyButton.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        applyButton.setForeground(Color.WHITE);
        applyButton.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        applyButton.addActionListener(e -> {
            String code = promoField.getText();
            applyPromoCode(code);
        });
        
        // Add action listener for apply button
        applyButton.addActionListener(e -> {
            String code = promoField.getText().trim();
            if (code.isEmpty() || code.equals("NHẬP MÃ KHUYẾN MÃI")) {
                showErrorMessage("Vui lòng nhập mã khuyến mãi!");
                return;
            }
            
            try {
                Connection conn = ConnectDB.getInstance().getConnection();
                String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai = ? AND ? BETWEEN ngayBatDau AND ngayKetThuc";
                
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, code);
                    // Sử dụng ngày hiện tại từ Java
                    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    pst.setDate(2, currentDate);
                    
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            // Lấy các ngày từ database
                            java.sql.Date startDate = rs.getDate("ngayBatDau");
                            java.sql.Date endDate = rs.getDate("ngayKetThuc");

                            // In thông tin debug
                            System.out.println("Mã KM: " + rs.getString("maKhuyenMai"));
                            System.out.println("Ngày bắt đầu: " + startDate);
                            System.out.println("Ngày kết thúc: " + endDate);
                            System.out.println("Ngày hiện tại: " + currentDate);
                            
                            // Kiểm tra ngày một lần nữa để đảm bảo
                            if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                                // Khuyến mãi hợp lệ
                                khuyenMaiDangDung = new KhuyenMai(
                                    rs.getString("maKhuyenMai"),
                                    rs.getString("tenKhuyenMai"),
                                    rs.getDouble("chietKhau"),
                                    startDate,
                                    endDate,
                                    rs.getString("moTa")
                                );
                                
                                // Tính lại tổng tiền với khuyến mãi
                                tinhTongTien();
                                
                                JOptionPane.showMessageDialog(this,
                                    String.format("Đã áp dụng mã giảm giá: %.1f%%\nTổng tiền sau giảm: %,.0f đ",
                                    khuyenMaiDangDung.getChietKhau(), tongTienSauGiam),
                                    "Thành công",
                                    JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                showErrorMessage("Mã khuyến mãi đã hết hạn!");
                                khuyenMaiDangDung = null;
                                tinhTongTien();
                            }
                        } else {
                            showErrorMessage("Mã khuyến mãi không tồn tại!");
                            khuyenMaiDangDung = null;
                            tinhTongTien();
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage("Lỗi khi kiểm tra mã khuyến mãi: " + ex.getMessage());
                khuyenMaiDangDung = null;
                tinhTongTien();
            }
        });
        
        promoPanel.add(promoField, BorderLayout.CENTER);
        promoPanel.add(applyButton, BorderLayout.EAST);
        
        mainPanel.add(promoPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Mã khuyến mãi có sẵn
        JLabel availablePromosLabel = new JLabel("Mã khuyến mãi có sẵn:");
        availablePromosLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        availablePromosLabel.setForeground(Color.GRAY);
        mainPanel.add(availablePromosLabel);
        mainPanel.add(Box.createVerticalStrut(5));

        JPanel availablePromos = new JPanel();
        availablePromos.setLayout(new BoxLayout(availablePromos, BoxLayout.Y_AXIS));
        availablePromos.setOpaque(false);

        String[][] promos = {
            {"SUMMER2024", "Khuyến mãi hè 2024", "Giảm 20%"},
            {"WEEKEND50", "Giảm giá cuối tuần", "Giảm 500.000 đ"},
            {"NEWMEMBER", "Chào mừng thành viên mới", "Giảm 15%"}
        };

        for (String[] promo : promos) {
            JPanel promoCard = createPromoCard(promo[0], promo[1], promo[2]);
            availablePromos.add(promoCard);
            availablePromos.add(Box.createVerticalStrut(5));
        }

        mainPanel.add(availablePromos);
        mainPanel.add(Box.createVerticalStrut(20));

        // 6. Nút điều khiển
        JPanel controlPanel = new JPanel(new BorderLayout(10, 0));
        controlPanel.setOpaque(false);
        
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.GRAY);
        cancelButton.setBorder(new LineBorder(Color.GRAY));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        JButton confirmButton = new JButton("Xác nhận đặt phòng");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmButton.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBorder(new EmptyBorder(8, 20, 8, 20));
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);
        
        controlPanel.add(buttonsPanel, BorderLayout.EAST);
        mainPanel.add(controlPanel);

        // Thêm panel chính vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        bookingDialog.add(scrollPane);

        // Xử lý sự kiện nút
        cancelButton.addActionListener(e -> bookingDialog.dispose());
        confirmButton.addActionListener(e -> {
            // Disable the confirm button to prevent double booking
            confirmButton.setEnabled(false);
            
            // Lưu thông tin đặt phòng vào database (sử dụng text fields cho ngày để tránh phụ thuộc thư viện bên ngoài)
            try {
                int soKhach = Integer.parseInt(guestField.getText().trim());

                String checkInText = checkInField.getText().trim();
                String checkOutText = checkOutField.getText().trim();

                // Chuyển đổi định dạng ngày hỗ trợ cả yyyy-MM-dd và dd/MM/yyyy
                String checkInIso = convertToIsoDate(checkInText);
                String checkOutIso = convertToIsoDate(checkOutText);

                if (checkInIso == null || checkOutIso == null) {
                    showErrorMessage("Định dạng ngày không hợp lệ! Vui lòng nhập ngày theo YYYY-MM-DD hoặc dd/MM/yyyy");
                    return;
                }

                processBooking(nameField.getText(),
                             emailField.getText(),
                             phoneField.getText(),
                             soKhach,
                             checkInIso,
                             checkOutIso,
                             promoField.getText());
                bookingDialog.dispose();
            } catch (NumberFormatException ex) {
                showErrorMessage("Vui lòng nhập số khách hợp lệ!");
            }
        });

        // Hiển thị dialog
        bookingDialog.setVisible(true);
    }

    private void processBooking(String name, String email, String phone, 
                              int guestCount, String checkIn, String checkOut, 
                              String promoCode) {
        // Validate and apply promotion code first
        if (promoCode != null && !promoCode.trim().isEmpty() && !promoCode.equals("NHẬP MÃ KHUYẾN MÃI")) {
            try {
                Connection conn = ConnectDB.getInstance().getConnection();
                String sql = "SELECT maKhuyenMai, tenKhuyenMai, chietKhau, ngayBatDau, ngayKetThuc FROM KhuyenMai WHERE maKhuyenMai = ? AND GETDATE() BETWEEN ngayBatDau AND ngayKetThuc";
                
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, promoCode.trim());
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            khuyenMaiDangDung = new KhuyenMai(
                                rs.getString("maKhuyenMai"),
                                rs.getString("tenKhuyenMai"),
                                rs.getDouble("chietKhau"),
                                rs.getDate("ngayBatDau"),
                                rs.getDate("ngayKetThuc"),
                                "" // Empty string for moTa since we're not using it
                            );
                            tinhTongTien(); // Recalculate with promotion
                        } else {
                            showErrorMessage("Mã khuyến mãi không hợp lệ hoặc đã hết hạn!");
                            return;
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorMessage("Lỗi khi kiểm tra mã khuyến mãi: " + ex.getMessage());
                return;
            }
        }
        
        // Validate required fields
        if (!validateBookingInput(name, email, phone, checkIn, checkOut)) {
            return;
        }

        if (danhSachPhongDaChon.isEmpty()) {
            showErrorMessage("Vui lòng chọn ít nhất một phòng!");
            return;
        }

        // Validate date format and business rules
        if (!validateDateRange(checkIn, checkOut)) {
            return;
        }

        Connection conn = null;
        String maKH = null;

        try {
            // Get connection and start transaction
            conn = ConnectDB.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Generate unique IDs
            maKH = generateUniqueId("KH");
            String maHoaDon = generateUniqueId("HD");

            // 1. Create or update customer
            createOrUpdateCustomer(conn, maKH, name, phone, email);

            // 2. Create invoice
            String sqlHoaDon = """
                INSERT INTO HoaDon (maHoaDon, ngayLap, maKH, maKhuyenMai, tongTien)
                VALUES (?, GETDATE(), (SELECT maKH FROM KhachHang WHERE sdt = ?), ?, ?)
                """;
            try (PreparedStatement pstHoaDon = conn.prepareStatement(sqlHoaDon)) {
                pstHoaDon.setString(1, maHoaDon);
                pstHoaDon.setString(2, phone);
                pstHoaDon.setString(3, khuyenMaiDangDung != null ? khuyenMaiDangDung.getMaKhuyenMai() : null);
                double totalBeforeDiscount = tongTienPhong + tongTienDichVu;
                pstHoaDon.setDouble(4, totalBeforeDiscount); // Tổng tiền trước giảm
                pstHoaDon.executeUpdate();
            }

            // 3. Add booking details for each room
            String sqlChiTiet = """
                INSERT INTO ChiTietHoaDon_Phong (maHoaDon, maPhong, ngayNhanPhong, ngayTraPhong)
                VALUES (?, ?, ?, ?)
                """;
            try (PreparedStatement pstChiTiet = conn.prepareStatement(sqlChiTiet)) {
                for (Phong phong : danhSachPhongDaChon) {
                    pstChiTiet.setString(1, maHoaDon);
                    pstChiTiet.setString(2, phong.getMaPhong());
                    pstChiTiet.setString(3, checkIn);
                    pstChiTiet.setString(4, checkOut);
                    pstChiTiet.executeUpdate();

                    // Update room status to 'ĐÃ XÁC NHẬN'
                    String sqlUpdateRoom = """
                        UPDATE Phong 
                        SET maTrangThai = (SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai = 'ĐÃ XÁC NHẬN')
                        WHERE maPhong = ?
                        """;
                    try (PreparedStatement pstUpdateRoom = conn.prepareStatement(sqlUpdateRoom)) {
                        pstUpdateRoom.setString(1, phong.getMaPhong());
                        pstUpdateRoom.executeUpdate();
                    }
                }
            }

            // 4. Add services if any are selected
            if (!danhSachDichVuDaChon.isEmpty()) {
                String sqlDichVu = "INSERT INTO ChiTietHoaDon_DichVu (maHoaDon, maDichVu, soLuong) VALUES (?, ?, ?)";
                try (PreparedStatement pstDichVu = conn.prepareStatement(sqlDichVu)) {
                    for (DichVu dichVu : danhSachDichVuDaChon) {
                        pstDichVu.setString(1, maHoaDon);
                        pstDichVu.setString(2, dichVu.getMaDichVu());
                        pstDichVu.setInt(3, 1); // Default quantity
                        pstDichVu.executeUpdate();
                    }
                }
            }

            // If everything is successful, commit the transaction
            conn.commit();

            // Show success message
            showBookingSuccessMessage(maHoaDon, khuyenMaiDangDung != null ? khuyenMaiDangDung.getChietKhau() : 0);
            
            // Reset UI and booking state first
            danhSachPhongDaChon.clear();
            danhSachDichVuDaChon.clear();
            khuyenMaiDangDung = null;
            tongTienPhong = 0;
            tongTienDichVu = 0;
            chietKhau = 0;
            tongTienSauGiam = 0;
            
            // Reload the room list and booking list
            loadDanhSachPhong();
            refreshUI();
            
            // Force repaint the booking list
            revalidate();
            repaint();
            
            // Update the parent panel if needed
            Container parent = getParent();
            while (parent != null) {
                parent.revalidate();
                parent.repaint();
                parent = parent.getParent();
            }

        } catch (SQLException e) {
            handleBookingError(conn, e);
        } finally {
            cleanupTransaction(conn);
        }
    }

    private boolean validateBookingInput(String name, String email, String phone, String checkIn, String checkOut) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || 
            checkIn.isEmpty() || checkOut.isEmpty()) {
            showErrorMessage("Vui lòng điền đầy đủ thông tin bắt buộc!");
            return false;
        }

        // Validate email format
        String emailPattern = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        if (!email.matches(emailPattern)) {
            showErrorMessage("Email không hợp lệ! Vui lòng nhập đúng định dạng email (VD: example@domain.com)");
            return false;
        }

        // Validate phone number format (Vietnam)
        // Loại bỏ khoảng trắng và các ký tự đặc biệt từ số điện thoại
        String cleanPhone = phone.replaceAll("[\\s()-]", "");
        
        // Kiểm tra số điện thoại sau khi đã làm sạch
        if (!cleanPhone.matches("^(\\+84|0)\\d{9,10}$")) {
            showErrorMessage("Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại Việt Nam hợp lệ (VD: 0912345678 hoặc +84912345678)");
            return false;
        }

        return true;
    }

    private void applyPromoCode(String code) {
        if (code == null || code.trim().isEmpty() || code.equals("NHẬP MÃ KHUYẾN MÃI")) {
            showErrorMessage("Vui lòng nhập mã khuyến mãi!");
            return;
        }

        if (danhSachPhongDaChon.isEmpty()) {
            showErrorMessage("Vui lòng chọn phòng trước khi áp dụng mã giảm giá!");
            return;
        }

        try {
            Connection conn = ConnectDB.getInstance().getConnection();
            // Chỉ lấy các cột cần thiết
                String sql = "SELECT maKhuyenMai, tenKhuyenMai, chietKhau, ngayBatDau, ngayKetThuc FROM KhuyenMai " +
                          "WHERE maKhuyenMai = ? AND ? BETWEEN ngayBatDau AND ngayKetThuc";
                
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, code.trim());
                    java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    pst.setDate(2, currentDate);

                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            java.sql.Date ngayBatDau = rs.getDate("ngayBatDau");
                            java.sql.Date ngayKetThuc = rs.getDate("ngayKetThuc");
                            double chietKhau = rs.getDouble("chietKhau");

                            // In thông tin debug
                            System.out.println("Mã KM: " + rs.getString("maKhuyenMai"));
                            System.out.println("Ngày hiện tại: " + currentDate);
                            System.out.println("Ngày bắt đầu: " + ngayBatDau);
                            System.out.println("Ngày kết thúc: " + ngayKetThuc);
                            System.out.println("Chiết khấu: " + chietKhau);

                            khuyenMaiDangDung = new KhuyenMai(
                                rs.getString("maKhuyenMai"),
                                rs.getString("tenKhuyenMai"),
                                chietKhau,
                                ngayBatDau,
                                ngayKetThuc,
                                "Mã khuyến mãi"
                            );

                            tinhTongTien(); // Tính lại tổng tiền với khuyến mãi
                            double tienGiam = (tongTienPhong + tongTienDichVu) * (chietKhau / 100.0);                        JOptionPane.showMessageDialog(this,
                            String.format("Áp dụng mã giảm giá thành công!\n" +
                                        "Chiết khấu: %.1f%%\n" +
                                        "Số tiền giảm: %,.0f đ\n" + 
                                        "Tổng tiền sau giảm: %,.0f đ",
                                        chietKhau, tienGiam, tongTienSauGiam),
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        showErrorMessage("Mã khuyến mãi không tồn tại!");
                        khuyenMaiDangDung = null;
                        tinhTongTien();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage("Lỗi khi kiểm tra mã khuyến mãi: " + ex.getMessage());
            khuyenMaiDangDung = null;
            tinhTongTien();
        }
    }

    private boolean validateDateRange(String checkIn, String checkOut) {
        try {
            java.sql.Date dateIn = java.sql.Date.valueOf(checkIn);
            java.sql.Date dateOut = java.sql.Date.valueOf(checkOut);
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

            if (dateIn.before(today)) {
                showErrorMessage("Ngày nhận phòng không thể ở quá khứ!");
                return false;
            }

            if (dateOut.before(dateIn)) {
                showErrorMessage("Ngày trả phòng phải sau ngày nhận phòng!");
                return false;
            }

            return true;
        } catch (IllegalArgumentException e) {
            showErrorMessage("Định dạng ngày không hợp lệ! Vui lòng sử dụng định dạng YYYY-MM-DD");
            return false;
        }
    }

    private String generateUniqueId(String prefix) {
        return prefix + System.currentTimeMillis() % 1000000;
    }

    private double executeBookingProcess(Connection conn, String maKH, String maHoaDon,
                                       String name, String email, String phone,
                                       int guestCount, String checkIn, String checkOut,
                                       String promoCode) throws SQLException {
        // 1. Create or update customer record
        createOrUpdateCustomer(conn, maKH, name, phone, email);

        // 2. Create new invoice
        createInvoice(conn, maHoaDon, phone);

        // 3. Apply promotion if available
        double chietKhau = applyPromotion(conn, maHoaDon, promoCode);

        // 4. Add invoice details and update room status
        addInvoiceDetailsAndUpdateRooms(conn, maHoaDon, guestCount, checkIn, checkOut);

        return chietKhau;
    }

    private void createOrUpdateCustomer(Connection conn, String maKH, String name, 
                                      String phone, String email) throws SQLException {
        // Kiểm tra xem khách hàng đã tồn tại chưa
        String checkSql = "SELECT maKH FROM KhachHang WHERE sdt = ?";
        String existingMaKH = null;
        
        try (PreparedStatement checkPst = conn.prepareStatement(checkSql)) {
            checkPst.setString(1, phone);
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next()) {
                    existingMaKH = rs.getString("maKH");
                }
            }
        }
        
        if (existingMaKH != null) {
            // Update if customer exists
            String updateSql = """
                UPDATE KhachHang 
                SET hoTen = ?, email = ?
                WHERE sdt = ?
            """;
            try (PreparedStatement pst = conn.prepareStatement(updateSql)) {
                pst.setString(1, name);
                pst.setString(2, email);
                pst.setString(3, phone);
                pst.executeUpdate();
            }
        } else {
            // Insert if customer doesn't exist
            String insertSql = """
                INSERT INTO KhachHang (maKH, hoTen, sdt, email, gioiTinh, ngaySinh, cccd, diaChi)
                VALUES (?, ?, ?, ?, N'Không xác định', NULL, NULL, N'Chưa cập nhật')
            """;
            try (PreparedStatement pst = conn.prepareStatement(insertSql)) {
                pst.setString(1, maKH);
                pst.setString(2, name);
                pst.setString(3, phone);
                pst.setString(4, email);
                pst.executeUpdate();
            }
        }
    }

    private void createInvoice(Connection conn, String maHoaDon, String phone) throws SQLException {
        String sql = "INSERT INTO HoaDon (maHoaDon, ngayLap, maKH, VAT, hinhThucThanhToan) " +
                    "VALUES (?, GETDATE(), " +
                    "(SELECT maKH FROM KhachHang WHERE sdt = ?), 0.1, N'Tiền mặt')";
                    
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maHoaDon);
            pst.setString(2, phone);
            pst.executeUpdate();
        }
    }

    private double applyPromotion(Connection conn, String maHoaDon, String promoCode) throws SQLException {
        if (promoCode == null || promoCode.isEmpty()) {
            return 0.0;
        }

        double chietKhau = 0.0;
        String sql = "SELECT maKhuyenMai, tenKhuyenMai, chietKhau FROM KhuyenMai " +
                    "WHERE maKhuyenMai = ? AND GETDATE() BETWEEN ngayBatDau AND ngayKetThuc";
                    
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, promoCode);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    chietKhau = rs.getDouble("chietKhau");
                    updateInvoicePromotion(conn, maHoaDon, promoCode);
                }
            }
        }
        return chietKhau;
    }

    private void updateInvoicePromotion(Connection conn, String maHoaDon, String promoCode) throws SQLException {
        String sql = "UPDATE HoaDon SET maKhuyenMai = ? WHERE maHoaDon = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, promoCode);
            pst.setString(2, maHoaDon);
            pst.executeUpdate();
        }
    }

    private void addInvoiceDetailsAndUpdateRooms(Connection conn, String maHoaDon,
                                              int guestCount, String checkIn,
                                              String checkOut) throws SQLException {
        for (Phong phong : danhSachPhongDaChon) {
            addInvoiceDetail(conn, maHoaDon, phong.getMaPhong(), 
                           guestCount, checkIn, checkOut);
            updateRoomStatus(conn, phong.getMaPhong());
        }
    }

    private void addInvoiceDetail(Connection conn, String maHoaDon, String maPhong,
                               int guestCount, String checkIn, String checkOut) throws SQLException {
        String sql = "INSERT INTO ChiTietHoaDon_Phong (maHoaDon, maPhong, thanhTien) " +
                    "VALUES (?, ?, ?)";
                    
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maHoaDon);
            pst.setString(2, maPhong);
            pst.setDouble(3, tongTienPhong / danhSachPhongDaChon.size());
            pst.executeUpdate();
        }
    }

    private void updateRoomStatus(Connection conn, String maPhong) throws SQLException {
        String sql = "UPDATE Phong SET maTrangThai = " +
                    "(SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai = 'ĐÃ XÁC NHẬN') " +
                    "WHERE maPhong = ?";
                    
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maPhong);
            pst.executeUpdate();
        }
    }

    private void showBookingSuccessMessage(String maHD, double chietKhau) {
        tinhTongTien(); // Tính lại tổng tiền
        String message = String.format("""
            Đặt phòng thành công!
            Mã hóa đơn: %s
            Tổng tiền phòng: %,.0f đ
            Tổng tiền dịch vụ: %,.0f đ
            %s
            Tổng thanh toán: %,.0f đ""",
            maHD,
            tongTienPhong,
            tongTienDichVu,
            chietKhau > 0 ? String.format("Chiết khấu: %.1f%% (-%,.0f đ)", chietKhau, chietKhau) : "",
            tongTienSauGiam);
            
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetBookingState() {
        // Clear all booking-related data
        danhSachPhongDaChon.clear();
        danhSachDichVuDaChon.clear();
        khuyenMaiDangDung = null;
        tongTienPhong = 0;
        tongTienDichVu = 0;
        chietKhau = 0;
        tongTienSauGiam = 0;
        
        // Reload data and update UI
        SwingUtilities.invokeLater(() -> {
            try {
                // Reload room list
                loadDanhSachPhong();
                
                // Reload booking list and refresh UI
                refreshUI();
                
                // Force immediate repaint
                revalidate();
                repaint();
                
                // Update parent containers
                Container parent = getParent();
                while (parent != null) {
                    parent.revalidate();
                    parent.repaint();
                    parent = parent.getParent();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorMessage("Lỗi khi làm mới giao diện: " + ex.getMessage());
            }
        });
    }

    private JScrollPane loadLatestBookings() {
        // Panel chính chứa danh sách booking cards
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        try {
            // Truy vấn SQL để lấy dữ liệu đặt phòng
            String sql = """
                SELECT DISTINCT 
                    kh.maKH,
                    kh.hoTen,
                    kh.sdt,
                    kh.email,
                    kh.gioiTinh,
                    kh.ngaySinh,
                    kh.cccd,
                    kh.diaChi,
                    p.maPhong,
                    lp.tenLoaiPhong,
                    h.maHoaDon,
                    h.ngayLap,
                    cthp.ngayNhanPhong,
                    cthp.ngayTraPhong,
                    p.giaTienMotDem,
                    tp.tenTrangThai,
                    h.tongTien,
                    CASE 
                        WHEN km.chietKhau IS NOT NULL 
                        THEN h.tongTien * (1 - km.chietKhau/100.0)
                        ELSE h.tongTien
                    END as thanhTienSauGiam,
                    ISNULL(km.chietKhau, 0) as chietKhau,
                    h.maKhuyenMai
                FROM HoaDon h
                JOIN KhachHang kh ON h.maKH = kh.maKH
                JOIN ChiTietHoaDon_Phong cthp ON h.maHoaDon = cthp.maHoaDon  
                JOIN Phong p ON cthp.maPhong = p.maPhong
                JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong
                JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai
                LEFT JOIN KhuyenMai km ON h.maKhuyenMai = km.maKhuyenMai
                WHERE h.ngayLap >= DATEADD(day, -7, GETDATE())  
                ORDER BY h.ngayLap DESC, cthp.ngayNhanPhong ASC
            """;

            try (Connection conn = ConnectDB.getInstance().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                while (rs.next()) {
                    // Lấy thông tin từ ResultSet
                    String name = rs.getString("hoTen");
                    String phone = rs.getString("sdt"); 
                    String roomNum = rs.getString("maPhong");
                    String roomType = rs.getString("tenLoaiPhong");
                    String maHoaDon = rs.getString("maHoaDon");
                    
                    // Xử lý ngày và giờ đặt phòng
                    String bookingDate = "Chưa có";
                    if (rs.getTimestamp("ngayLap") != null) {
                        Date ngayLap = rs.getTimestamp("ngayLap");
                        bookingDate = dateFormat.format(ngayLap) + " " + timeFormat.format(ngayLap);
                    }
                    
                    // Xử lý thời gian lưu trú
                    String duration = "Chưa xác định";
                    Date checkIn = rs.getDate("ngayNhanPhong");
                    Date checkOut = rs.getDate("ngayTraPhong");
                    if (checkIn != null && checkOut != null) {
                        long diffTime = checkOut.getTime() - checkIn.getTime();
                        long nights = diffTime / (24 * 60 * 60 * 1000);
                        if (nights >= 0) {
                            duration = nights + " đêm";
                        }
                    }

                    // Lấy thông tin giá và chiết khấu
                    double roomPrice = rs.getDouble("giaTienMotDem");
                    double tongTien = rs.getDouble("tongTien");
                    double chietKhau = rs.getDouble("chietKhau");
                    String maKhuyenMai = rs.getString("maKhuyenMai");
                    
                    String price = String.format("%,.0f đ", roomPrice);
                    String totalPrice = String.format("%,.0f đ", tongTien);
                    String discountInfo = maKhuyenMai != null ? 
                        String.format("Giảm giá %.1f%% (Mã: %s)", chietKhau, maKhuyenMai) : 
                        "Không áp dụng giảm giá";
                    
                    String status = rs.getString("tenTrangThai");

                    // Tạo thẻ booking và thêm vào panel
                    JPanel card = createBookingCard(
                        name, phone, roomNum, roomType,
                        bookingDate, duration, price,
                        getTrangThaiInt(status)
                    );
                    
                    if (card != null) {
                        // Thêm thông tin chi tiết vào card
                        JPanel detailPanel = new JPanel(new GridLayout(4, 1, 5, 2));
                        detailPanel.setOpaque(false);
                        detailPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
                        
                        JLabel lblMaHD = new JLabel("Mã HD: " + maHoaDon);
                        lblMaHD.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        
                        JLabel lblTongTien = new JLabel("Tổng tiền: " + totalPrice);
                        lblTongTien.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        
                        JLabel lblGiamGia = new JLabel(discountInfo);
                        lblGiamGia.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                        lblGiamGia.setForeground(new Color(50, 150, 50));
                        
                        // Thêm thông tin khách hàng chi tiết
                        String gioiTinh = rs.getString("gioiTinh");
                        Date ngaySinh = rs.getDate("ngaySinh");
                        String cccd = rs.getString("cccd");
                        String diaChi = rs.getString("diaChi");
                        
                        JLabel lblInfo = new JLabel(String.format("<html>%s • %s • %s</html>", 
                            gioiTinh != null ? gioiTinh : "Không xác định",
                            ngaySinh != null ? dateFormat.format(ngaySinh) : "Chưa có ngày sinh",
                            cccd != null ? cccd : "Chưa có CCCD"));
                        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        lblInfo.setForeground(Color.GRAY);
                        
                        detailPanel.add(lblMaHD);
                        detailPanel.add(lblTongTien);
                        detailPanel.add(lblGiamGia);
                        detailPanel.add(lblInfo);
                        
                        // Thêm panel chi tiết vào card
                        JPanel cardContent = new JPanel(new BorderLayout());
                        cardContent.setOpaque(false);
                        cardContent.add(detailPanel, BorderLayout.SOUTH);
                        card.add(cardContent, BorderLayout.SOUTH);
                        
                        listPanel.add(card);
                        listPanel.add(Box.createVerticalStrut(10));
                    }
                }
            }
            
            // Tạo ScrollPane và cấu hình
            JScrollPane scrollPane = new JScrollPane(listPanel);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.getViewport().setOpaque(false);
            
            return scrollPane;

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Lỗi khi tải danh sách đặt phòng: " + e.getMessage());
            
            // Tạo ScrollPane trống trong trường hợp lỗi
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setOpaque(false);
            
            JScrollPane emptyScrollPane = new JScrollPane(emptyPanel);
            emptyScrollPane.setBorder(null);
            emptyScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            emptyScrollPane.getViewport().setOpaque(false);
            
            return emptyScrollPane;
        }
    }
    

    private int getTrangThaiInt(String trangThai) {
        switch (trangThai) {
            case "ĐÃ XÁC NHẬN":
                return 1;
            case "ĐANG SỬ DỤNG":
                return 2; 
            case "ĐÃ TRẢ PHÒNG":
                return 3;
            default:
                return 0;
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    // Convert date input (yyyy-MM-dd or dd/MM/yyyy) to ISO (yyyy-MM-dd) string.
    private String convertToIsoDate(String input) {
        if (input == null) return null;
        input = input.trim();
        if (input.isEmpty()) return null;
        try {
            // Try ISO first
            java.time.LocalDate d = java.time.LocalDate.parse(input);
            return d.toString();
        } catch (Exception ignored) {
        }
        try {
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate d = java.time.LocalDate.parse(input, fmt);
            return d.toString();
        } catch (Exception ignored) {
        }
        return null;
    }

    // Helper method to find JTextField by placeholder
    private JTextField findTextField(Container container, String placeholder) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField tf = (JTextField) comp;
                if (tf.getText().equals(placeholder)) {
                    return tf;
                }
            } else if (comp instanceof Container) {
                JTextField result = findTextField((Container) comp, placeholder);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    // (Removed helper that depended on external date-picker libraries.)

    private void handleBookingError(Connection conn, SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Lỗi khi rollback transaction: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
        }

        System.err.println("Lỗi trong quá trình đặt phòng: " + e.getMessage());
        e.printStackTrace();

        String errorMessage = getDetailedErrorMessage(e);
        showErrorMessage(errorMessage);
    }

    private String getDetailedErrorMessage(SQLException e) {
        String baseMessage = "Có lỗi xảy ra khi đặt phòng:";
        
        // Check for specific SQL error codes and states
        switch (e.getSQLState()) {
            case "23000": // Integrity constraint violation
                return baseMessage + "\nThông tin đặt phòng không hợp lệ hoặc trùng lặp.";
            case "08001": // Connection error
                return baseMessage + "\nKhông thể kết nối đến cơ sở dữ liệu.";
            case "42000": // Syntax error
                return baseMessage + "\nLỗi cú pháp SQL.";
            default:
                return baseMessage + "\n" + e.getMessage() + "\nVui lòng thử lại!";
        }
    }

    private void cleanupTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Lỗi khi reset auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Helper methods for UI components
    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        
        // Add placeholder
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        
        return textField;
    }

    private JPanel createServiceCard(String name, String price, String duration, String description) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        JCheckBox checkbox = new JCheckBox();
        checkbox.setBackground(card.getBackground());
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(card.getBackground());
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        
        infoPanel.add(nameLabel);
        infoPanel.add(descLabel);
        
        JLabel priceLabel = new JLabel(String.format("%s đ / %s phút", price, duration));
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JPanel leftPanel = new JPanel(new BorderLayout(10, 0));
        leftPanel.setBackground(card.getBackground());
        leftPanel.add(checkbox, BorderLayout.WEST);
        leftPanel.add(infoPanel, BorderLayout.CENTER);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(priceLabel, BorderLayout.EAST);
        
        return card;
    }

    private JPanel createPromoCard(String code, String title, String discount) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(new EmptyBorder(10, 12, 10, 12));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(card.getBackground());
        
        JLabel codeLabel = new JLabel(code);
        codeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        // Thêm sự kiện click cho card để tự động điền mã
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (promoField != null) {
                    promoField.setText(code);
                }
            }
        });
        
        leftPanel.add(codeLabel);
        leftPanel.add(titleLabel);
        
        JLabel discountLabel = new JLabel(discount);
        discountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        discountLabel.setForeground(new Color(46, 125, 50));
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(discountLabel, BorderLayout.EAST);
        
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // TODO: Handle promo code selection
            }
        });
        
        return card;
    }
    

    // Header của trang Đặt phòng
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel title = new JLabel("Đặt phòng");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);
        return header;
    }

    // Nội dung chính
    private JPanel createMainContent() {
        // Panel chứa cả booking list và room selection
        JPanel contentWrapper = new JPanel(new BorderLayout(0, 20));
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(new EmptyBorder(0, 5, 5, 5));

        // Phần trên: Booking list
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setOpaque(false);
        bookingPanel.setPreferredSize(new Dimension(0, 300)); // Chiều cao cố định
        bookingPanel.add(createSearchFilterPanel(), BorderLayout.NORTH);
        bookingPanel.add(loadLatestBookings(), BorderLayout.CENTER);
        contentWrapper.add(bookingPanel, BorderLayout.NORTH);

        // Phần dưới: Room selection
        contentWrapper.add(createRoomSelectionPanel(), BorderLayout.CENTER);

        return contentWrapper;
    }

    private JPanel createSearchFilterPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        // Logic cho placeholder text
        JTextField searchField = new JTextField("");
        String placeholder = " Tìm kiếm...";
        Color placeholderColor = Color.GRAY;
        Color defaultColor = UIManager.getColor("TextField.foreground");

        searchField.setText(placeholder);
        searchField.setForeground(placeholderColor);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(defaultColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(placeholderColor);
                }
            }
        });
        searchField.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(5, 8, 5, 8)));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(new JComboBox<>(new String[] { "Tất cả", "Đã xác nhận", "Đã nhận phòng" }), BorderLayout.EAST);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return searchPanel;
    }

    private JScrollPane createCardListPanel() {
        JPanel cardListPanel = new JPanel();
        cardListPanel.setLayout(new BoxLayout(cardListPanel, BoxLayout.Y_AXIS));
        cardListPanel.setOpaque(false);
        cardListPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Dữ liệu mô phỏng danh sách đặt phòng
        cardListPanel.add(createBookingCard("Nguyễn Văn An", "+84 (0) 123-456-789", "Phòng 201", "Phòng Deluxe",
                "15/1/2024", "3 đêm", "750.000 đ", 1));
        cardListPanel.add(Box.createVerticalStrut(10));
        cardListPanel.add(createBookingCard("Trần Thị Lan", "+84 (0) 987-654-321", "Phòng 105", "Tiêu chuẩn",
                "16/1/2024", "3 đêm", "420.000 đ", 2));
        cardListPanel.add(Box.createVerticalStrut(10));
        cardListPanel.add(createBookingCard("Phạm Thị Hoa", "+84 (0) 321-098-765", "Phòng 404", "Phòng Gia đình",
                "18/1/2024", "4 đêm", "1.200.000 đ", 1));
        cardListPanel.add(Box.createVerticalStrut(10));
        cardListPanel.add(createBookingCard("Khách 4", "+84 (0) 111-222-333", "Phòng 102", "Tiêu chuẩn", "19/1/2024",
                "1 đêm", "420.000 đ", 1));

        JScrollPane scrollPane = new JScrollPane(cardListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        scrollPane.setPreferredSize(new Dimension(0, 300));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        return scrollPane;
    }

    private JPanel createBookingsListPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setOpaque(false);
        mainPanel.add(createSearchFilterPanel(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        return mainPanel;
    }

    private void showBookingDetails(String name, String phone, String roomNum, 
                                  String roomType, String bookingDate,
                                  String duration, String price) {
        // Display booking details in a dialog or panel
        String message = String.format("""
            Chi tiết đặt phòng:
            Khách hàng: %s
            Số điện thoại: %s 
            Phòng: %s - %s
            Ngày đặt: %s
            Thời gian: %s
            Giá: %s/đêm""",
            name, phone, roomNum, roomType, 
            bookingDate, duration, price);
            
        JOptionPane.showMessageDialog(this,
            message,
            "Chi tiết đặt phòng",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JPanel createBookingCard(String name, String phone, String roomNum, String roomType, 
            String bookingDate, String duration, String price, int status) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(12, 8, 12, 12)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Thông tin khách hàng và phòng
        card.add(createVerticalInfoPanel(name, phone, 180));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(roomNum, roomType, 120));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(bookingDate, duration, 120));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(price, "/ đêm", 120));
        card.add(Box.createHorizontalGlue());

        // Trạng thái và nút hành động
        JPanel statusAndActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        statusAndActions.setOpaque(false);

        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        switch (status) {
            case 1:
                statusLabel.setText("ĐÃ XÁC NHẬN");
                statusLabel.setForeground(GUI_NhanVienLeTan.STATUS_GREEN_FG);
                statusLabel.setBackground(GUI_NhanVienLeTan.STATUS_GREEN_BG);
                break;
            case 2:
                statusLabel.setText("ĐANG SỬ DỤNG");
                statusLabel.setForeground(GUI_NhanVienLeTan.STATUS_YELLOW_FG);
                statusLabel.setBackground(GUI_NhanVienLeTan.STATUS_YELLOW_BG);
                break;
            case 3:
                statusLabel.setText("ĐÃ TRẢ PHÒNG");
                statusLabel.setForeground(GUI_NhanVienLeTan.STATUS_ORANGE_FG);
                statusLabel.setBackground(GUI_NhanVienLeTan.STATUS_ORANGE_BG);
                break;
            default:
                statusLabel.setText("KHÔNG XÁC ĐỊNH");
                statusLabel.setForeground(GUI_NhanVienLeTan.STATUS_RED_FG);
                statusLabel.setBackground(GUI_NhanVienLeTan.STATUS_RED_BG);
        }

        statusLabel.setOpaque(true);
        statusLabel.setBorder(new EmptyBorder(4, 8, 4, 8));
        statusAndActions.add(statusLabel);
        
        card.add(statusAndActions);

        // Thêm hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showBookingDetails(name, phone, roomNum, roomType, bookingDate, duration, price);
            }
        });

        return card;
    }

    private JPanel createVerticalInfoPanel(String line1, String line2, int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel l1 = new JLabel(line1);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l1.setForeground(Color.BLACK);
        panel.add(l1);

        JLabel l2 = new JLabel(line2);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l2.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        panel.add(l2);

        Dimension d = new Dimension(width, 50);
        panel.setPreferredSize(d);
        panel.setMinimumSize(d);
        panel.setMaximumSize(d);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        return panel;
    }

    private JPanel createRoomFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterPanel.setOpaque(false);

        // Panel cho lọc loại phòng
        JPanel roomTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        roomTypePanel.setOpaque(false);
        roomTypePanel.add(new JLabel("Loại phòng: "));
        String[] roomTypes = {"Tất cả", "Tiêu chuẩn", "Deluxe", "View biển"};
        JComboBox<String> roomTypeFilter = new JComboBox<>(roomTypes);
        roomTypeFilter.addActionListener(e -> {
            locLoaiPhong = (String) roomTypeFilter.getSelectedItem();
            applyFilters();
        });
        roomTypePanel.add(roomTypeFilter);
        filterPanel.add(roomTypePanel);

        filterPanel.add(Box.createHorizontalStrut(15));

        // Panel cho lọc số người
        JPanel peoplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        peoplePanel.setOpaque(false);
        peoplePanel.add(new JLabel("Số người: "));
        String[] peopleOptions = {"Tất cả", "2 người", "3 người", "4+ người"};
        JComboBox<String> peopleFilter = new JComboBox<>(peopleOptions);
        peopleFilter.addActionListener(e -> {
            locSoNguoi = (String) peopleFilter.getSelectedItem();
            applyFilters();
        });
        peoplePanel.add(peopleFilter);
        filterPanel.add(peoplePanel);

        filterPanel.add(Box.createHorizontalStrut(15));

        // Panel hiển thị tổng tiền và số phòng đã chọn
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        infoPanel.setOpaque(false);
        JLabel selectedLabel = new JLabel("Đã chọn: 0 phòng");
        JLabel totalLabel = new JLabel("Tổng tiền: 0đ");
        infoPanel.add(selectedLabel);
        infoPanel.add(Box.createHorizontalStrut(10));
        infoPanel.add(totalLabel);
        filterPanel.add(infoPanel);

        return filterPanel;
    }

    private JScrollPane createRoomGridPanel() {
        // Grid hiển thị các thẻ phòng
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Hiển thị danh sách phòng đã lọc từ database
        if (danhSachPhongDaLoc != null) {
            for (Phong phong : danhSachPhongDaLoc) {
                // Lấy thông tin chi tiết từ mô tả hoặc tạo mô tả mặc định
                String details = phong.getMoTa() != null ? phong.getMoTa() : 
                    String.format("Tầng %s • 30 m² • 2 người", phong.getMaPhong().substring(0, 1));
                    
                gridPanel.add(createRoomCard(
                    "Phòng " + phong.getMaPhong(), 
                    phong.getLoaiPhong(),
                    details,
                    String.format("%,.0f đ", phong.getGiaTienMotDem()),
                    !phong.isTrangThai(),
                    phong
                ));
            }
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        return scrollPane;
    }

    private JPanel createRoomSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setOpaque(false);

        JLabel title = new JLabel("Chọn phòng để đặt");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        centerPanel.add(createRoomFilterPanel(), BorderLayout.NORTH);
        centerPanel.add(createRoomGridPanel(), BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private void handleRoomSelection(Phong phong) {
        if (phong.isTrangThai()) {
            JOptionPane.showMessageDialog(this,
                "Phòng " + phong.getMaPhong() + " đang được thuê!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Nếu phòng đã được chọn, hủy chọn
        if (danhSachPhongDaChon.contains(phong)) {
            danhSachPhongDaChon.remove(phong);
            tinhTongTien();
        } else {
            // Nếu chưa chọn, thêm vào danh sách
            danhSachPhongDaChon.add(phong);
            tinhTongTien();
        }

        // Cập nhật hiển thị
        updateSelectionInfo();
        refreshUI();
    }

    private void updateSelectionInfo() {
        // Tìm các label để cập nhật
        for (Component comp : getRootPane().getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                updateLabelsInPanel((JPanel) comp);
            }
        }
    }

    private void updateLabelsInPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                updateLabelsInPanel((JPanel) comp);
            } else if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Đã chọn:")) {
                    label.setText(String.format("Đã chọn: %d phòng", danhSachPhongDaChon.size()));
                } else if (label.getText().startsWith("Tổng tiền:")) {
                    label.setText(String.format("Tổng tiền: %,.0f đ", tongTienSauGiam));
                }
            }
        }
    }

    private void applyFilters() {
        if (danhSachPhong != null) {
            danhSachPhongDaLoc = new ArrayList<>(danhSachPhong);

            // Lọc theo loại phòng
            if (!locLoaiPhong.equals("Tất cả")) {
                danhSachPhongDaLoc = danhSachPhongDaLoc.stream()
                    .filter(p -> p.getLoaiPhong().equals(locLoaiPhong))
                    .collect(Collectors.toList());
            }

            // Lọc theo số người
            if (!locSoNguoi.equals("Tất cả")) {
                int soNguoi = Integer.parseInt(locSoNguoi.split(" ")[0]);
                if (locSoNguoi.endsWith("+")) {
                    danhSachPhongDaLoc = danhSachPhongDaLoc.stream()
                        .filter(p -> extractSoNguoi(p.getMoTa()) >= soNguoi)
                        .collect(Collectors.toList());
                } else {
                    danhSachPhongDaLoc = danhSachPhongDaLoc.stream()
                        .filter(p -> extractSoNguoi(p.getMoTa()) == soNguoi)
                        .collect(Collectors.toList());
                }
            }

            refreshUI();
        }
    }

    private int extractSoNguoi(String moTa) {
        if (moTa == null) return 2; // Giá trị mặc định
        try {
            return Integer.parseInt(moTa.replaceAll(".*?(\\d+)\\s*người.*", "$1"));
        } catch (Exception e) {
            return 2; // Giá trị mặc định nếu không thể parse
        }
    }

    private JPanel createRoomCard(String roomNum, String roomType, String details, String price, boolean isAvailable, Phong phong) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        boolean isSelected = danhSachPhongDaChon.contains(phong);
        
        // Thay đổi màu nền nếu phòng được chọn
        card.setBackground(isSelected ? new Color(240, 248, 255) : GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(isSelected ? GUI_NhanVienLeTan.ACCENT_BLUE : GUI_NhanVienLeTan.CARD_BORDER, isSelected ? 2 : 1),
                new EmptyBorder(12, 12, 12, 12)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel numLabel = new JLabel(roomNum);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // Hiển thị trạng thái
        String statusText = isAvailable ? (isSelected ? "Đã chọn" : "Sẵn sàng") : "Đã thuê";
        Color statusColor = isAvailable ? 
            (isSelected ? GUI_NhanVienLeTan.ACCENT_BLUE : GUI_NhanVienLeTan.COLOR_GREEN) 
            : GUI_NhanVienLeTan.COLOR_RED;
        
        JLabel statusLabel = new JLabel(statusText);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(statusColor);
        topPanel.add(numLabel, BorderLayout.WEST);
        topPanel.add(statusLabel, BorderLayout.EAST);
        card.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel typeLabel = new JLabel(roomType);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        centerPanel.add(typeLabel);

        JLabel detailsLabel = new JLabel("<html>" + details.replace("• ", "<br>• ") + "</html>");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        centerPanel.add(detailsLabel);
        card.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel priceLabel = new JLabel("<html><b>" + price + "</b> /đêm</html>");
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton selectButton = new JButton(isSelected ? "- Bỏ chọn" : "+ Chọn");
        selectButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        selectButton.setOpaque(true);
        selectButton.setContentAreaFilled(true);
        
        // Thay đổi màu nút dựa vào trạng thái chọn
        if (isSelected) {
            selectButton.setBackground(new Color(220, 53, 69)); // Màu đỏ cho bỏ chọn
            selectButton.setForeground(Color.WHITE);
        } else {
            selectButton.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
            selectButton.setForeground(GUI_NhanVienLeTan.COLOR_WHITE);
        }

        Border lineBorder = new LineBorder(selectButton.getBackground().darker(), 1);
        Border paddingBorder = new EmptyBorder(5, 15, 5, 15);
        selectButton.setBorder(new CompoundBorder(lineBorder, paddingBorder));

        selectButton.setEnabled(isAvailable);
        
        // Thêm sự kiện click cho toàn bộ card
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (isAvailable) {
                    handleRoomSelection(phong);
                }
            }
        });
        
        // Thêm sự kiện click cho nút
        selectButton.addActionListener(e -> handleRoomSelection(phong));

        bottomPanel.add(priceLabel, BorderLayout.CENTER);
        bottomPanel.add(selectButton, BorderLayout.EAST);
        card.add(bottomPanel, BorderLayout.SOUTH);

        // Đổi con trỏ chuột khi di chuyển vào card
        if (isAvailable) {
            card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        return card;
    }
}

// =================================================================================
// PANEL NỘI DUNG 3: KHÁCH HÀNG
// =================================================================================
class PanelKhachHangContent extends JPanel {

    public PanelKhachHangContent() {
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Quản lý Khách hàng");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setOpaque(false);

        content.add(createCustomerListPanel(), BorderLayout.CENTER);
        content.add(createSummarySection(), BorderLayout.SOUTH);

        return content;
    }

    // ===== DANH SÁCH KHÁCH HÀNG =====
    private JPanel createCustomerListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setOpaque(false);

        panel.add(createSearchFilterPanel(), BorderLayout.NORTH);
        panel.add(createCustomerScrollPanel(), BorderLayout.CENTER);

        return panel;
    }

    // tìm kiếm
    private JPanel createSearchFilterPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        // Logic cho placeholder text
        JTextField searchField = new JTextField("");
        String placeholder = " Tìm kiếm khách hàng...";
        Color placeholderColor = Color.GRAY;
        Color defaultColor = UIManager.getColor("TextField.foreground");

        searchField.setText(placeholder);
        searchField.setForeground(placeholderColor);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(defaultColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(placeholderColor);
                }
            }
        });
        searchField.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(5, 8, 5, 8)));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(new JComboBox<>(new String[] { "Tất cả", "Platinum", "Gold", "Silver", "Bronze", "Standard" }),
                BorderLayout.EAST);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return searchPanel;
    }

    private JScrollPane createCustomerScrollPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // Danh sách khách hàng mô phỏng
        listPanel.add(createCustomerCard("Nguyễn Văn Minh", "+84 (0) 123-456-789", "nguyen.van.minh@email.com",
                "079123456789", "079123456789", 12, "15/1/2024", "45.000.000 đ", "Gold", 4.8));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCustomerCard("Trần Thị Hương", "+84 (0) 987-654-321", "tran.thi.huong@email.com",
                "079987654321", "079987654321", 8, "10/1/2024", "28.000.000 đ", "Silver", 4.9));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCustomerCard("Lê Quang Hải", "+84 (0) 456-789-123", "le.quang.hai@email.com",
                "079456789123", "079456789123", 5, "20/12/2023", "12.000.000 đ", "Bronze", 4.5));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCustomerCard("Phạm Thị Lan", "+84 (0) 321-987-654", "pham.thi.lan@email.com",
                "079321987654", "079321987654", 15, "8/1/2024", "72.000.000 đ", "Platinum", 5.0));
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(createCustomerCard("Võ Minh Tâm", "+84 (0) 789-123-456", "vo.minh.tam@email.com",
                "079789123456", "079789123456", 3, "15/10/2023", "9.000.000 đ", "Standard", 4.2));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scrollPane.setPreferredSize(new Dimension(0, 350)); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        return scrollPane;
    }

    private JPanel createCustomerCard(String name, String phone, String email, String sdt, String cccd,
            int stayCount, String lastStay, String totalSpend, String tier, double rating) {

        // --- Card tổng ---
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(10, 15, 10, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // ========== CỘT 1: AVATAR ==========
        JPanel avatarPanel = new JPanel(new GridBagLayout()); 
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new Dimension(60, 60)); 
        // Avatar bằng chữ cái đầu
        JLabel avatar = new JLabel(getInitials(name), SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diameter = Math.min(getWidth(), getHeight());
                g2.setColor(new Color(108, 99, 255));
                g2.fillOval(0, 0, diameter, diameter);

                // Vẽ chữ ở giữa
                FontMetrics fm = g2.getFontMetrics(getFont());
                int x = (diameter - fm.stringWidth(getText())) / 2;
                int y = (diameter + fm.getAscent()) / 2 - 2;
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        avatar.setPreferredSize(new Dimension(45, 45));
        avatar.setMinimumSize(new Dimension(45, 45));
        avatar.setMaximumSize(new Dimension(45, 45));
        avatar.setOpaque(false);

        avatarPanel.add(avatar); 
        card.add(avatarPanel);

        // ========== CỘT 2: TÊN + SỐ ĐIỆN THOẠI ==========
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel phoneLabel = new JLabel(phone);
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        phoneLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);

        infoPanel.add(nameLabel);
        infoPanel.add(phoneLabel);
        infoPanel.setPreferredSize(new Dimension(180, 40));
        card.add(infoPanel);

        // ========== CỘT 3: EMAIL + CCCD ==========
        card.add(createVerticalInfoPanel(email, cccd, 160));

        // ========== CỘT 4: LƯU TRÚ + LẦN CUỐI ==========
        card.add(createVerticalInfoPanel(stayCount + " lần lưu trú", "Lần cuối: " + lastStay, 130));

        // ========== CỘT 5: CHI TIÊU + ĐÁNH GIÁ ==========
        JLabel ratingLabel = new JLabel(
                "<html><span style='color:#FFD700;font-size:13px;'>★</span> " + String.format("%.1f/5", rating)
                        + "</html>");
        card.add(createVerticalInfoPanel(totalSpend + " ₫", ratingLabel, 120));

        // ========== CỘT 6: HẠNG VIP ==========
        JLabel vipLabel = new JLabel(tier, SwingConstants.CENTER);
        vipLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        vipLabel.setForeground(Color.WHITE);
        vipLabel.setOpaque(true);
        vipLabel.setBackground(getTierColor(tier));
        vipLabel.setBorder(new EmptyBorder(4, 10, 4, 10));
        vipLabel.setPreferredSize(new Dimension(80, 25));

        JPanel vipPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        vipPanel.setOpaque(false);
        vipPanel.add(vipLabel);
        card.add(vipPanel);

        // ========== CỘT 7: NÚT CHỨC NĂNG ==========
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        right.setOpaque(false);

        // Nút chức năng cho Khách hàng, dùng Emoji
        JButton edit = new JButton("✎");
        JButton view = new JButton("👁");
        JButton delete = new JButton("🗑");

        edit.setForeground(Color.blue);
        view.setForeground(new Color(0, 180, 0));
        delete.setForeground(Color.red);

        for (JButton b : new JButton[] { edit, view, delete }) {
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        right.add(edit);
        right.add(view);
        right.add(delete);
        card.add(right);

        return card;
    }

    private JPanel createVerticalInfoPanel(Object top, Object bottom, int width) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(width, 40));

        if (top instanceof String) {
            p.add(new JLabel((String) top));
        } else if (top instanceof JLabel) {
            p.add((JLabel) top);
        }
        if (bottom instanceof String) {
            p.add(new JLabel((String) bottom));
        } else if (bottom instanceof JLabel) {
            p.add((JLabel) bottom);
        }
        return p;
    }

    // Logic lấy màu cho hạng thành viên
    private Color getTierColor(String tier) {
        switch (tier) {
            case "Platinum":
                return new Color(162, 126, 251);
            case "Gold":
                return new Color(237, 207, 79, 239);
            case "Silver":
                return new Color(177, 170, 170);
            case "Bronze":
                return new Color(255, 191, 110);
            case "Standard":
                return new Color(99, 132, 244);
            default:
                return Color.GRAY;
        }
    }

    // Lấy chữ cái đầu làm avatar
    private String getInitials(String name) {
        String[] parts = name.split(" ");
        String initials = "";
        for (String p : parts) {
            if (!p.isEmpty())
                initials += p.charAt(0);
        }
        return initials.length() > 2 ? initials.substring(initials.length() - 2) : initials;
    }

    // ===== PHẦN THÊM KHÁCH HÀNG + TỔNG KẾT =====
    private JPanel createSummarySection() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);

        // Tiêu đề + nút thêm
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Thêm khách hàng mới");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title, BorderLayout.WEST);

        JButton btnAdd = new JButton("+ Thêm khách hàng");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(new EmptyBorder(8, 15, 8, 15));
        header.add(btnAdd, BorderLayout.EAST);

        container.add(header, BorderLayout.NORTH);

        // Thẻ thống kê có cuộn
        JScrollPane scrollSummary = new JScrollPane(createSummaryPanel());
        scrollSummary.setBorder(null);
        scrollSummary.setPreferredSize(new Dimension(0, 200));
        scrollSummary.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scrollSummary.getVerticalScrollBar().setUnitIncrement(15);
        container.add(scrollSummary, BorderLayout.CENTER);

        return container;
    }

    private JPanel createSummaryPanel() {
        JPanel summary = new JPanel(new GridLayout(2, 4, 15, 15));
        summary.setOpaque(false);
        summary.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Các thẻ thống kê nhanh
        summary.add(createSummaryCard("5", "Khách hàng", GUI_NhanVienLeTan.ACCENT_BLUE));
        summary.add(createSummaryCard("2", "VIP cao cấp", new Color(186, 85, 211)));
        summary.add(createSummaryCard("166.000.000 đ", "Tổng chi tiêu", new Color(60, 179, 113)));
        summary.add(createSummaryCard("4.7", "Đánh giá trung bình", new Color(255, 215, 0)));

        summary.add(createSummaryCard("1", "Platinum", new Color(186, 85, 211)));
        summary.add(createSummaryCard("1", "Gold", new Color(255, 191, 0)));
        summary.add(createSummaryCard("1", "Silver", new Color(192, 192, 192)));
        summary.add(createSummaryCard("1", "Bronze", new Color(205, 127, 50)));

        return summary;
    }

    private JPanel createSummaryCard(String value, String label, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(color), new EmptyBorder(10, 10, 10, 10)));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(color);

        JLabel labelLabel = new JLabel(label, SwingConstants.CENTER);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(Color.GRAY);

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelLabel, BorderLayout.SOUTH);

        return card;
    }
}

// =================================================================================
// PANEL NỘI DUNG 4: QUẢN LÝ PHÒNG (ĐÃ SỬA NÚT CHỨC NĂNG)
// =================================================================================
class PanelPhongContent extends JPanel {
    private PhongDAO phongDAO;
    private List<Phong> danhSachPhong;
    public PanelPhongContent() {
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        // Khởi tạo PhongDAO và lấy danh sách phòng
        phongDAO = new PhongDAO();
        loadDanhSachPhong();
        refreshUI(); // Cập nhật giao diện sau khi load dữ liệu
    }

    private void loadDanhSachPhong() {
        try {
            danhSachPhong = phongDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách phòng: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshUI() {
        removeAll();
        
        // Thiết lập cho JPanel này
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15)); 

        // Tạo panel chính chứa tất cả nội dung
        JPanel mainContentPanel = new JPanel(new BorderLayout(0, 20));
        mainContentPanel.setOpaque(false);

        // Header
        mainContentPanel.add(createHeader(), BorderLayout.NORTH);

        // Nội dung chính
        mainContentPanel.add(createMainContent(), BorderLayout.CENTER);

        // Thêm vào panel chính và cập nhật giao diện
        add(mainContentPanel);
        revalidate();
        repaint();
    }
    

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel title = new JLabel("Quản lý Phòng");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        // Nút reset phòng đã thuê
        JButton btnReset = new JButton("Reset phòng thuê");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnReset.setBackground(new Color(220, 53, 69));
        btnReset.setForeground(Color.WHITE);
        btnReset.setBorderPainted(false);
        btnReset.setFocusPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setBorder(new EmptyBorder(8, 15, 8, 15));

        btnReset.addActionListener(ev -> {
            int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn reset tất cả phòng đã thuê về trạng thái 'CHỜ XỬ LÝ'?",
                "Xác nhận reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    int updated = phongDAO.resetAllRentedRooms();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this,
                            String.format("Đã reset %d phòng về trạng thái 'CHỜ XỬ LÝ'.", updated),
                            "Hoàn tất",
                            JOptionPane.INFORMATION_MESSAGE);
                        loadDanhSachPhong();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Không có phòng đang ở trạng thái 'ĐÃ XÁC NHẬN' để reset.",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi reset phòng: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(btnReset);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setOpaque(false);

        // Phần trên: Thanh tìm kiếm và bộ lọc
        content.add(createSearchFilterBar(), BorderLayout.NORTH);

        // Phần giữa: Danh sách phòng
        content.add(createRoomListPanel(), BorderLayout.CENTER);

        // Phần dưới: Header cho sơ đồ phòng và Sơ đồ phòng
        content.add(createRoomSchemaPanelWrapper(), BorderLayout.SOUTH);

        return content;
    }

    // ===================================================
    // BAR TÌM KIẾM VÀ LỌC
    // ===================================================
    private JPanel createSearchFilterBar() {
        JPanel bar = new JPanel(new BorderLayout(10, 0));
        bar.setOpaque(false);

        // --- Search Field ---
        JTextField searchField = new JTextField("");
        String placeholder = " Tìm kiếm phòng...";
        Color placeholderColor = Color.GRAY;
        Color defaultColor = UIManager.getColor("TextField.foreground");

        searchField.setText(placeholder);
        searchField.setForeground(placeholderColor);

        // Logic cho placeholder text
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(defaultColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(placeholderColor);
                }
            }
        });
        searchField.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(5, 8, 5, 8)));
        bar.add(searchField, BorderLayout.CENTER);

        // --- Filters ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);

        JComboBox<String> statusFilter = new JComboBox<>(
                new String[] { "Tất cả trạng thái", "Sẵn sàng", "Đã thuê", "Bảo trì", "Đang dọn" });
        JComboBox<String> typeFilter = new JComboBox<>(new String[] { "Tất cả loại", "Tiêu chuẩn", "Suite cao cấp",
                "View biển", "Gia đình", "Suite Tổng thống" });

        filterPanel.add(statusFilter);
        filterPanel.add(typeFilter);

        bar.add(filterPanel, BorderLayout.EAST);
        bar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        return bar;
    }

    // ===================================================
    // DANH SÁCH PHÒNG (dạng thẻ chi tiết)
    // ===================================================
    private JScrollPane createRoomListPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        if (danhSachPhong != null) {
            for (Phong phong : danhSachPhong) {
                String tang = "Tầng " + phong.getMaPhong().substring(0, 1);
                String dienTich = phong.getMoTa() != null ? phong.getMoTa() : "30 m² • 2 người";
                String gia = String.format("%,.0f ₫", phong.getGiaTienMotDem());
                String trangThai = phong.isTrangThai() ? "Đang thuê" : "Sẵn sàng";

                JPanel roomCard = createDetailRoomCard(
                    phong.getMaPhong(),     // Số phòng
                    tang,                    // Tầng
                    phong.getLoaiPhong(),   // Loại phòng
                    dienTich,               // Diện tích và số người
                    gia,                    // Giá phòng
                    trangThai,              // Trạng thái
                    3,                      // Số tiện ích (mặc định)
                    "85% công suất",        // Công suất (mặc định)
                    "4 lần nghỉ"            // Số lần nghỉ (mặc định)
                );

                // Thêm sự kiện click cho thẻ phòng
                roomCard.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        handleRoomCardClick(phong);
                    }
                });
                roomCard.setCursor(new Cursor(Cursor.HAND_CURSOR));

                listPanel.add(roomCard);
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setPreferredSize(new Dimension(0, 450)); 
        return scrollPane;
    }

    private JPanel createDetailRoomCard(String num, String floor, String type, String specs, String price,
            String status, int amenities, String occupancy, String stays) {
        // Cấu trúc mới chỉ còn 5 cột: (Số/Tầng | Loại/Mô tả | Giá | Trạng thái | Thao tác)
        JPanel card = new JPanel(new GridLayout(1, 5, 10, 0));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(12, 12, 12, 12)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setMinimumSize(new Dimension(0, 70)); 
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Column 1: Room Number & Floor
        JPanel col1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        col1.setOpaque(false);

        JLabel icon = new JLabel();
        icon.setPreferredSize(new Dimension(32, 32));
        icon.setOpaque(true);
        icon.setBackground(new Color(175, 170, 255)); 
        icon.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel numPanel = new JPanel();
        numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.Y_AXIS));
        numPanel.setOpaque(false);
        JLabel numLabel = new JLabel(num);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel floorLabel = new JLabel(floor);
        floorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        floorLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        numPanel.add(numLabel);
        numPanel.add(floorLabel);

        col1.add(icon);
        col1.add(numPanel);
        card.add(col1);

        // Column 2: Type & Specs + Price (Gộp lại)
        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        col2.setOpaque(false);
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel specsLabel = new JLabel(specs);
        specsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        specsLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        col2.add(typeLabel);
        col2.add(specsLabel);
        card.add(col2);

        // Column 3: Price Info
        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        col3.setOpaque(false);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel perNight = new JLabel("/ đêm");
        perNight.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        perNight.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        col3.add(priceLabel);
        col3.add(perNight);
        card.add(col3);

        // Column 4: Status 
        JPanel col4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col4.setOpaque(false);

        JLabel statusLabel = new JLabel(status, SwingConstants.CENTER);
        StatusColors colors = getStatusColors(status);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(colors.bg);
        statusLabel.setForeground(colors.fg);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setBorder(new EmptyBorder(4, 8, 4, 8));
        statusLabel.setPreferredSize(new Dimension(80, 25)); 

        JPanel statusWrapper = new JPanel(new GridBagLayout()); 
        statusWrapper.setOpaque(false);
        statusWrapper.add(statusLabel); 

        col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));
        col4.add(statusWrapper); 
        card.add(col4);

        // Column 5: Actions (3 nút: Sửa, Xem, Xóa - giống Khách hàng)
        JPanel col5 = new JPanel(new GridBagLayout());
        col5.setOpaque(false);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        buttonsPanel.setOpaque(false);

        // Tạo 3 nút giống hệt PanelKhachHangContent
        JButton edit = new JButton("✎");
        JButton view = new JButton("👁");
        JButton delete = new JButton("🗑");

        edit.setForeground(Color.blue);
        view.setForeground(new Color(0, 180, 0));
        delete.setForeground(Color.red);

        for (JButton b : new JButton[] { edit, view, delete }) {
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            // Kích thước font và kiểu font giống hệt PanelKhachHangContent
            b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        buttonsPanel.add(edit);
        buttonsPanel.add(view);
        buttonsPanel.add(delete);

        col5.add(buttonsPanel); 

        card.add(col5);

        return card;
    }

    // Lớp Helper cho màu Trạng thái
    private static class StatusColors {
        Color bg;
        Color fg;

        StatusColors(Color bg, Color fg) {
            this.bg = bg;
            this.fg = fg;
        }
    }

    // Logic lấy màu trạng thái
    private StatusColors getStatusColors(String status) {
        switch (status) {
            case "Sẵn sàng":
                return new StatusColors(GUI_NhanVienLeTan.STATUS_GREEN_BG, GUI_NhanVienLeTan.STATUS_GREEN_FG);
            case "Đang thuê":
                return new StatusColors(GUI_NhanVienLeTan.STATUS_RED_BG, GUI_NhanVienLeTan.STATUS_RED_FG);
            case "Bảo trì":
                return new StatusColors(GUI_NhanVienLeTan.STATUS_ORANGE_BG, GUI_NhanVienLeTan.STATUS_ORANGE_FG);
            case "Đang dọn":
                return new StatusColors(GUI_NhanVienLeTan.STATUS_YELLOW_BG, GUI_NhanVienLeTan.STATUS_YELLOW_FG);
            default:
                return new StatusColors(new Color(240, 240, 240), GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        }
    }

    // Phương thức tạo nút Icon đã bị xóa khỏi đây để sử dụng cấu hình nút inline
    // và đồng bộ với PanelKhachHangContent.

    // ===================================================
    // WRAPPER CHO SƠ ĐỒ PHÒNG VÀ HEADER CÓ NÚT THÊM
    // ===================================================
    private JPanel createRoomSchemaPanelWrapper() {
        // Container chứa Header Sơ đồ phòng và Sơ đồ phòng
        JPanel wrapper = new JPanel(new BorderLayout(0, 15));
        wrapper.setOpaque(false);

        // Header chứa Tiêu đề Sơ đồ phòng và Nút Thêm phòng (song song)
        wrapper.add(createRoomSchemaPanelHeader(), BorderLayout.NORTH);

        // Sơ đồ phòng (Container cuộn)
        wrapper.add(createRoomSchemaPanel(), BorderLayout.CENTER);

        return wrapper;
    }

    // Header Sơ đồ phòng - Nút Thêm phòng ở đây để song song với tiêu đề
    private JPanel createRoomSchemaPanelHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(10, 0, 0, 0)); 

        JLabel title = new JLabel("Sơ đồ phòng");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(title, BorderLayout.WEST);

        // Nút Thêm phòng
        JButton btnAdd = new JButton("+ Thêm phòng");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(new EmptyBorder(8, 15, 8, 15));
        header.add(btnAdd, BorderLayout.EAST);
        
        return header;
    }

    // ===================================================
    // SƠ ĐỒ PHÒNG (Grid cuộn ngang)
    // ===================================================
    private JScrollPane createRoomSchemaPanel() {
        JPanel grid = new JPanel(new GridLayout(1, 0, 15, 15)); // 1 hàng, nhiều cột
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Thẻ sơ đồ phòng mô phỏng
        grid.add(createSchemaCard("101", "Tiêu chuẩn", "2", "1.2M", GUI_NhanVienLeTan.STATUS_GREEN_BG.darker()));
        grid.add(createSchemaCard("201", "Suite cao cấp", "4", "2.8M", GUI_NhanVienLeTan.STATUS_RED_BG.darker()));
        grid.add(createSchemaCard("301", "View biển", "3", "3.5M", GUI_NhanVienLeTan.STATUS_ORANGE_BG.darker()));
        grid.add(createSchemaCard("404", "Gia đình", "6", "4.5M", GUI_NhanVienLeTan.STATUS_YELLOW_BG.darker()));
        grid.add(createSchemaCard("501", "Suite Tổng thống", "4", "9.0M", GUI_NhanVienLeTan.STATUS_GREEN_BG.darker()));
        grid.add(createSchemaCard("102", "Tiêu chuẩn", "2", "1.2M", GUI_NhanVienLeTan.STATUS_GREEN_BG.darker()));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(Integer.MAX_VALUE, 120));

        return scroll;
    }

    private void handleRoomCardClick(Phong phong) {
        // Kiểm tra trạng thái phòng
        if (phong.isTrangThai()) {
            JOptionPane.showMessageDialog(this,
                "Phòng " + phong.getMaPhong() + " đang được thuê!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Hiển thị dialog xác nhận đặt phòng
        int option = JOptionPane.showConfirmDialog(this,
            "Bạn muốn đặt phòng " + phong.getMaPhong() + "?\n" +
            "Loại phòng: " + phong.getLoaiPhong() + "\n" +
            "Giá: " + String.format("%,.0f VNĐ", phong.getGiaTienMotDem()),
            "Xác nhận đặt phòng",
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                // Cập nhật trạng thái phòng trong database
                if (phongDAO.updateTrangThaiPhong(phong.getMaPhong(), true)) {
                    JOptionPane.showMessageDialog(this,
                        "Đặt phòng thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                    // Tải lại danh sách phòng
                    loadDanhSachPhong();
                    // Cập nhật giao diện
                    repaint();
                    revalidate();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Không thể đặt phòng. Vui lòng thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi đặt phòng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);
        
        return row;
    }

    private JPanel createSchemaCard(String num, String type, String capacity, String price, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(new CompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER, 1),
                new EmptyBorder(10, 10, 10, 10)));

        // Header: Room number & status dot
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel numLabel = new JLabel(num);
        numLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numLabel.setForeground(Color.BLACK);

        // Status Dot
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("SansSerif", Font.BOLD, 16));
        dot.setForeground(color.equals(GUI_NhanVienLeTan.STATUS_RED_BG.darker()) ? GUI_NhanVienLeTan.COLOR_RED
                : GUI_NhanVienLeTan.COLOR_GREEN);

        header.add(numLabel, BorderLayout.WEST);
        header.add(dot, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        // Center: Type
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        typeLabel.setForeground(Color.DARK_GRAY);
        card.add(typeLabel, BorderLayout.CENTER);

        // Footer: Capacity and Price
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel capLabel = new JLabel("<html><span style='font-size:10px;'>👤</span> " + capacity + "</html>");
        capLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        priceLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED.darker());

        footer.add(capLabel, BorderLayout.WEST);
        footer.add(priceLabel, BorderLayout.EAST);
        card.add(footer, BorderLayout.SOUTH);

        return card;
    }
}

// PanelDichVuContent removed from this file to avoid duplicate class; use the standalone
// `PanelDichVuContent.java` in the same package instead.
