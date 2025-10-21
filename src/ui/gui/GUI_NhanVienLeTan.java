package ui.gui;

// ---------------------------
// Chú thích metadata (comment)
// Người code: Phan Minh Thuận
// Mô tả: Thêm nhãn chú thích hiển thị tên người chịu trách nhiệm / phần giao diện profile với dashboard, thanh menu, Panel đặt phòng
// Mục đích: Quản lý code, dễ dàng liên hệ khi cần chỉnh sửa
// Ngày tạo: 22/10/2025 00h30
// Giờ tạo: 09:26
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

// =================================================================================
// LỚP CHÍNH (JFrame) - CHỨA SIDEBAR CỐ ĐỊNH VÀ CARDLAYOUT CHO NỘI DUNG
// =================================================================================
public class GUI_NhanVienLeTan extends JFrame {

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

    // CardLayout để chuyển nội dung bên phải
    private CardLayout cardLayout;
    private JPanel contentPanelContainer; // Panel chứa CardLayout

    // Lưu lại các nút menu để đổi màu khi active
    private JButton btnDashboard;
    private JButton btnDatPhong;
    private JButton btnKhachHang;
    private JButton btnDichVu;

    // (Thêm các nút khác nếu cần)

    public GUI_NhanVienLeTan() {
        setTitle("Quản lý Khách sạn TBQTT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // JFrame dùng BorderLayout

        // 1. Tạo Sidebar cố định và đặt vào WEST
        add(createStaticSidebar(), BorderLayout.WEST);

        // 2. Tạo Panel chứa CardLayout cho nội dung chính
        cardLayout = new CardLayout();
        contentPanelContainer = new JPanel(cardLayout);
        contentPanelContainer.setBackground(MAIN_BG); // Nền cho vùng nội dung

        // 3. Tạo các Panel nội dung riêng biệt (không chứa sidebar)
        PanelLeTanContent panelLeTanContent = new PanelLeTanContent();
        PanelDatPhongContent panelDatPhongContent = new PanelDatPhongContent();
        PanelKhachHangContent panelKhachHangContent = new PanelKhachHangContent();
        PanelDichVuContent panelDichVuContent = new PanelDichVuContent();

        // 4. Thêm các Panel nội dung vào CardLayout
        contentPanelContainer.add(panelLeTanContent, "LE_TAN_CONTENT");
        contentPanelContainer.add(panelDatPhongContent, "DAT_PHONG_CONTENT");
        contentPanelContainer.add(panelKhachHangContent, "KHACH_HANG_CONTENT");
        contentPanelContainer.add(panelDichVuContent, "DICH_VU_CONTENT");

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

        // Logo section
        JPanel logo = new JPanel();
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.setBorder(new EmptyBorder(18, 18, 18, 18));
        logo.setOpaque(false);
        // (Code tạo logo giữ nguyên...)
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

        // Menu buttons
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(12, 12, 12, 12));
        menu.setOpaque(false);

        // Tạo các nút menu (lưu lại tham chiếu)
        btnDashboard = createNavButton("Dashboard");
        btnDatPhong = createNavButton("Đặt phòng");
        btnKhachHang = createNavButton("Khách hàng");
        btnDichVu = createNavButton("Dịch vụ");

        JButton btnPhong = createNavButton("Phòng");
        // Gắn ActionListener để chuyển đổi content panel
        btnDashboard.addActionListener(e -> showContentPanel("LE_TAN_CONTENT"));
        btnDatPhong.addActionListener(e -> showContentPanel("DAT_PHONG_CONTENT"));
        btnKhachHang.addActionListener(e -> showContentPanel("KHACH_HANG_CONTENT"));
        btnDichVu.addActionListener(e -> showContentPanel("DICH_VU_CONTENT"));

        // (Thêm action listener cho các nút khác nếu bạn tạo panel tương ứng)

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

        // Profile section
        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setBorder(new EmptyBorder(12, 12, 12, 12));
        profile.setOpaque(false);
        // (Code tạo profile giữ nguyên...)
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
        // profile.add(exitButton); // Mở nếu cần
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
        btn.setOpaque(false); // Quan trọng để có thể đổi màu nền
        return btn;
    }

    /**
     * Helper: Đặt trạng thái active cho nút được chọn và reset các nút khác
     */
    private void setActiveButton(JButton activeButton) {
        JButton[] allButtons = { btnDashboard, btnDatPhong, btnKhachHang, btnDichVu/* , các nút khác */ };
        for (JButton btn : allButtons) {
            if (btn == activeButton) {
                btn.setForeground(Color.WHITE);
                btn.setBackground(ACCENT_BLUE);
                btn.setOpaque(true); // Chỉ bật Opaque cho nút active
                btn.setBorder(new CompoundBorder(
                        new LineBorder(ACCENT_BLUE, 2, true),
                        new EmptyBorder(6, 12, 6, 12)));
            } else if (btn != null) { // Kiểm tra null phòng trường hợp chưa khởi tạo hết
                btn.setForeground(Color.BLACK);
                btn.setOpaque(false); // Tắt Opaque cho nút không active
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
        cardLayout.show(contentPanelContainer, panelName);
        // Cập nhật trạng thái active của nút menu tương ứng
        if (panelName.equals("LE_TAN_CONTENT")) {
            setActiveButton(btnDashboard);
        } else if (panelName.equals("DAT_PHONG_CONTENT")) {
            setActiveButton(btnDatPhong);
        } else if (panelName.equals("KHACH_HANG_CONTENT")) {
            setActiveButton(btnKhachHang);
        } else if (panelName.equals("DICH_VU_CONTENT")) {
            setActiveButton(btnDichVu);
        }
        // (Thêm else if cho các panel khác)
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
// PANEL NỘI DUNG 1: MÀN HÌNH DASHBOARD (CHỈ CHỨA HEADER VÀ CONTENT)
// =================================================================================
class PanelLeTanContent extends JPanel {
    // (Copy các hằng số màu STAT_BG từ GUI_NhanVienLeTan cũ vào đây nếu cần)
    private final Color STAT_BG_1 = new Color(218, 240, 255);
    private final Color STAT_BG_2 = new Color(230, 235, 255);
    private final Color STAT_BG_3 = new Color(255, 235, 240);

    public PanelLeTanContent() {
        // --- Thiết lập cho JPanel này ---
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18)); // Lề cho nội dung

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

    // (Copy code gốc của bạn vào đây)
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
        JLabel name = new JLabel("Nguyễn Văn Lễ Tân");
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel details = new JLabel("<html>letan@tbqtthotel.vn • +84 (0) 123-456-789 • Mã NV: NV001</html>");
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
// PANEL NỘI DUNG 2: ĐẶT PHÒNG (CHỈ CHỨA HEADER VÀ CONTENT)
// =================================================================================
class PanelDatPhongContent extends JPanel {

    public PanelDatPhongContent() {
        // --- Thiết lập cho JPanel này ---
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15)); // Lề cho nội dung

        // --- Chỉ thêm Header và Content Panel ---
        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
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
        // Panel chính dùng BorderLayout
        JPanel content = new JPanel(new BorderLayout(0, 25)); // Khoảng cách dọc 25px
        content.setOpaque(false); // Nền trong suốt
        content.setBorder(new EmptyBorder(0, 5, 5, 5)); // Lề nhỏ

        // Phần 1: Danh sách đặt phòng (đặt ở phía trên - NORTH)
        // Phương thức createBookingsListPanel() trả về JPanel chứa JScrollPane bên
        // trong
        content.add(createBookingsListPanel(), BorderLayout.NORTH);

        // Phần 2: Chọn phòng để đặt (đặt ở giữa - CENTER)
        // Phương thức createRoomSelectionPanel() trả về JPanel chứa JScrollPane bên
        // trong
        content.add(createRoomSelectionPanel(), BorderLayout.CENTER);

        // KHÔNG còn JScrollPane bao ngoài cùng nữa
        return content;
    }

    private JPanel createSearchFilterPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

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
        mainPanel.add(createCardListPanel(), BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(mainPanel, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel createBookingCard(String name, String phone, String roomNum, String roomType, String date,
            String duration, String price, int status) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(12, 8, 12, 12)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(createVerticalInfoPanel(name, phone, 180));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(roomNum, roomType, 120));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(date, duration, 120));
        card.add(Box.createHorizontalStrut(10));
        card.add(createVerticalInfoPanel(price, "250.000 đ/đêm", 120));
        card.add(Box.createHorizontalGlue());

        JLabel statusLabel = new JLabel();
        JButton actionButton = new JButton();
        actionButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(false);
        actionButton.setOpaque(true);
        actionButton.setContentAreaFilled(true);
        actionButton.setForeground(GUI_NhanVienLeTan.COLOR_WHITE);

        Color buttonColor;

        if (status == 1) {
            statusLabel.setText("Đã xác nhận");
            statusLabel.setForeground(GUI_NhanVienLeTan.COLOR_GREEN);
            actionButton.setText("In");
            actionButton.setBackground(GUI_NhanVienLeTan.COLOR_GREEN);
            buttonColor = GUI_NhanVienLeTan.COLOR_GREEN;
        } else {
            statusLabel.setText("Đã nhận phòng");
            statusLabel.setForeground(GUI_NhanVienLeTan.COLOR_ORANGE);
            actionButton.setText("Out");
            actionButton.setBackground(GUI_NhanVienLeTan.COLOR_ORANGE);
            buttonColor = GUI_NhanVienLeTan.COLOR_ORANGE;
        }

        Border lineBorder = new LineBorder(buttonColor.darker(), 1);
        Border paddingBorder = new EmptyBorder(5, 15, 5, 15);
        actionButton.setBorder(new CompoundBorder(lineBorder, paddingBorder));

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(statusLabel);
        card.add(Box.createHorizontalStrut(15));
        card.add(actionButton);
        card.add(Box.createHorizontalStrut(5));

        JButton etcButton = new JButton("...");
        etcButton.setFocusPainted(false);
        card.add(etcButton);

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

        filterPanel.add(new JToggleButton("Tất cả", true));
        filterPanel.add(new JToggleButton("Tiêu chuẩn"));
        filterPanel.add(new JToggleButton("Deluxe"));
        filterPanel.add(new JToggleButton("View biển"));
        filterPanel.add(Box.createHorizontalStrut(15));

        JToggleButton btnAllPeople = new JToggleButton("Tất cả", true);
        btnAllPeople.setBackground(GUI_NhanVienLeTan.COLOR_GREEN);
        btnAllPeople.setForeground(GUI_NhanVienLeTan.COLOR_WHITE);
        btnAllPeople.setOpaque(true);
        btnAllPeople.setBorderPainted(false);

        filterPanel.add(btnAllPeople);
        filterPanel.add(new JToggleButton("2 người"));
        filterPanel.add(new JToggleButton("3 người"));
        filterPanel.add(new JToggleButton("4+ người"));

        return filterPanel;
    }

    private JScrollPane createRoomGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        gridPanel.add(createRoomCard("Phòng 101", "Phòng Tiêu chuẩn", "Tầng 1 • 30 m² • 2 người", "1.200.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 102", "Phòng Tiêu chuẩn", "Tầng 1 • 30 m² • 2 người", "1.200.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 201", "Phòng Deluxe", "Tầng 2 • 40 m² • 2 người", "2.000.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 202", "Phòng Deluxe", "Tầng 2 • 40 m² • 2 người", "2.000.000 đ", false));
        gridPanel.add(createRoomCard("Phòng 301", "Phòng View biển", "Tầng 3 • 35 m² • 2 người", "1.800.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 302", "Phòng View biển", "Tầng 3 • 35 m² • 2 người", "1.800.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 401", "Phòng Gia đình", "Tầng 4 • 50 m² • 4 người", "2.500.000 đ", true));
        gridPanel
                .add(createRoomCard("Phòng 501", "Phòng Tổng thống", "Tầng 5 • 100 m² • 4 người", "5.000.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 103", "Tiêu chuẩn", "Tầng 1 • 30 m² • 2 người", "1.200.000 đ", true));
        gridPanel.add(createRoomCard("Phòng 104", "Tiêu chuẩn", "Tầng 1 • 30 m² • 2 người", "1.200.000 đ", true));

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

    private JPanel createRoomCard(String roomNum, String roomType, String details, String price, boolean isAvailable) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                new EmptyBorder(12, 12, 12, 12)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel numLabel = new JLabel(roomNum);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel statusLabel = new JLabel(isAvailable ? "Sẵn sàng" : "Đã thuê");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(isAvailable ? GUI_NhanVienLeTan.COLOR_GREEN : GUI_NhanVienLeTan.COLOR_RED);
        topPanel.add(numLabel, BorderLayout.WEST);
        topPanel.add(statusLabel, BorderLayout.EAST);
        card.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel typeLabel = new JLabel(roomType);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        centerPanel.add(typeLabel);

        JLabel detailsLabel = new JLabel("<html>" + details.replace("• 2 người", "<br>• 2 người") + "</html>");
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        centerPanel.add(detailsLabel);
        card.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel priceLabel = new JLabel("<html><b>" + price + "</b> /đêm</html>");
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton selectButton = new JButton("+ Chọn");
        selectButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        selectButton.setOpaque(true);
        selectButton.setContentAreaFilled(true);
        selectButton.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        selectButton.setForeground(GUI_NhanVienLeTan.COLOR_WHITE);

        Border lineBorder = new LineBorder(GUI_NhanVienLeTan.ACCENT_BLUE.darker(), 1);
        Border paddingBorder = new EmptyBorder(5, 15, 5, 15);
        selectButton.setBorder(new CompoundBorder(lineBorder, paddingBorder));

        selectButton.setEnabled(isAvailable);

        bottomPanel.add(priceLabel, BorderLayout.CENTER);
        bottomPanel.add(selectButton, BorderLayout.EAST);
        card.add(bottomPanel, BorderLayout.SOUTH);

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

        // danh sách khách hang
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
        scrollPane.setPreferredSize(new Dimension(0, 350)); // chỉ hiển thị khoảng 3 khách
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
        JPanel avatarPanel = new JPanel(new GridBagLayout()); // Dùng GridBag để căn giữa tuyệt đối
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new Dimension(60, 60)); // tạo khung cố định cho avatar
        // vẽ avatar hình tròn
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

        avatarPanel.add(avatar); // căn giữa avatar trong panel
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

        if (top instanceof String s1)
            p.add(new JLabel(s1));
        else if (top instanceof JLabel l1)
            p.add(l1);

        if (bottom instanceof String s2)
            p.add(new JLabel(s2));
        else if (bottom instanceof JLabel l2)
            p.add(l2);

        return p;
    }

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
// LỚP PanelDatPhongContent
// =================================================================================
class PanelDatPhongContent extends JPanel {

    private final Color MAIN_BG = GUI_NhanVienLeTan.MAIN_BG;
    private final Color ACCENT_BLUE = GUI_NhanVienLeTan.ACCENT_BLUE;
    private final Color COLOR_WHITE = GUI_NhanVienLeTan.COLOR_WHITE;
    private final Color CARD_BORDER = GUI_NhanVienLeTan.CARD_BORDER;

    public PanelDatPhongContent() {
        setLayout(new BorderLayout());
        setBackground(MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Đặt phòng mới");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(15, 0));
        content.setOpaque(false);

        JPanel leftPanel = createFormPanel();
        leftPanel.setPreferredSize(new Dimension(400, 0));
        content.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = createRoomSelectionWrapper();
        content.add(centerPanel, BorderLayout.CENTER);

        return content;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(COLOR_WHITE);
        formPanel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Thông tin khách hàng");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(createInputField("Họ và Tên", new JTextField()));
        formPanel.add(createInputField("CCCD/Passport", new JTextField()));
        formPanel.add(createInputField("Số điện thoại", new JTextField()));
        formPanel.add(createInputField("Email", new JTextField()));
        formPanel.add(createInputField("Địa chỉ", new JTextField()));
        formPanel.add(createComboBoxField("Loại khách", new JComboBox<>(new String[] { "Nội địa", "Quốc tế" })));
        formPanel.add(createComboBoxField("Quốc tịch", new JComboBox<>(new String[] { "Việt Nam", "Mỹ", "Khác" })));

        formPanel.add(Box.createVerticalStrut(20));

        JLabel title2 = new JLabel("Thông tin đặt phòng");
        title2.setFont(new Font("SansSerif", Font.BOLD, 16));
        title2.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(title2);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(createInputField("Ngày nhận phòng", new JTextField()));
        formPanel.add(createInputField("Ngày trả phòng", new JTextField()));
        formPanel.add(createInputField("Số người", new JTextField()));
        formPanel.add(createInputField("Ghi chú", new JTextField()));

        formPanel.add(Box.createVerticalGlue());

        JButton btnDatPhong = new JButton("Xác nhận Đặt phòng");
        btnDatPhong.setBackground(ACCENT_BLUE);
        btnDatPhong.setForeground(COLOR_WHITE);
        btnDatPhong.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDatPhong.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        btnDatPhong.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(btnDatPhong);

        return formPanel;
    }

    private JPanel createInputField(String label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(5, 8, 5, 8)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private JPanel createComboBoxField(String label, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboBox.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(comboBox);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private JPanel createRoomSelectionWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBackground(COLOR_WHITE);
        wrapper.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Chọn phòng trống");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        wrapper.add(title, BorderLayout.NORTH);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filters.setOpaque(false);
        filters.add(new JComboBox<>(new String[] { "Tất cả loại phòng", "Tiêu chuẩn", "Suite" }));
        filters.add(new JComboBox<>(new String[] { "Tất cả sức chứa", "2 người", "4 người" }));
        filters.add(new JComboBox<>(new String[] { "Tất cả giá", "1M - 2M", "2M - 4M" }));
        wrapper.add(filters, BorderLayout.CENTER);

        JScrollPane roomScroll = createRoomSelectionPanel();
        wrapper.add(roomScroll, BorderLayout.SOUTH);

        return wrapper;
    }

    private JScrollPane createRoomSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        String[][] roomData = {
                { "101", "Tiêu chuẩn", "30m²", "2 người", "1.200.000 ₫" },
                { "102", "Tiêu chuẩn", "30m²", "2 người", "1.200.000 ₫" },
                { "203", "View biển", "39m²", "3 người", "3.500.000 ₫" },
                { "405", "Suite gia đình", "70m²", "6 người", "4.500.000 ₫" },
                { "504", "Suite cao cấp", "51m²", "4 người", "2.750.000 ₫" },
        };

        for (String[] data : roomData) {
            panel.add(createRoomCard(data[0], data[1], data[2], data[3], data[4]));
            panel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_WHITE);
        scroll.setPreferredSize(new Dimension(Integer.MAX_VALUE, 400));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel createRoomCard(String soPhong, String loaiPhong, String dienTich, String sucChua, String gia) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 255));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 210, 255)),
                new EmptyBorder(10, 10, 10, 10)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // LEFT: Room Info
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(createVerticalInfoPanel("Phòng " + soPhong, loaiPhong, 100));
        left.add(createVerticalInfoPanel("DT: " + dienTich, "SC: " + sucChua, 120));
        left.add(createVerticalInfoPanel(gia, "Giá/Đêm", 120));
        card.add(left, BorderLayout.WEST);

        // RIGHT: Action
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        JButton btnChon = new JButton("Chọn");
        btnChon.setBackground(GUI_NhanVienLeTan.COLOR_GREEN);
        btnChon.setForeground(COLOR_WHITE);
        btnChon.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnChon.setPreferredSize(new Dimension(80, 40));
        right.add(btnChon);
        card.add(right, BorderLayout.EAST);

        return card;
    }

    private JPanel createVerticalInfoPanel(String top, String bottom, int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(width, 60));
        JLabel topLabel = new JLabel(top);
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel bottomLabel = new JLabel(bottom);
        bottomLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bottomLabel.setForeground(Color.GRAY);
        panel.add(topLabel);
        panel.add(bottomLabel);
        return panel;
    }
}

// =================================================================================
// PANEL NỘI DUNG 4: DỊCH VỤ
// =================================================================================
class PanelDichVuContent extends JPanel {
    public PanelDichVuContent() {
        setLayout(new BorderLayout(15, 15));
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    // Tiêu đề + nút thêm dịch vụ
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel title = new JLabel("Quản lý Dịch vụ");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        return header;
    }

    // Chia làm 2 phần: danh sách dịch vụ và danh mục dịch vụ
    private JPanel createMainContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // Danh sách dịch vụ ở trên
        content.add(createServiceListPanel());
        content.add(Box.createVerticalStrut(20)); // khoảng cách

        // Danh mục dịch vụ ở dưới
        content.add(createServiceCategoryPanel());

        return content;
    }

    // Danh sách dịch vụ chi tiết
    private JScrollPane createServiceListPanel() {
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setAlignmentX(Component.LEFT_ALIGNMENT); // giữ full chiều ngang

        list.add(createServiceCard("Spa & Massage cao cấp", "300000 đ", "Còn", "Massage thư giãn, xông hơi", 4.65));
        list.add(Box.createVerticalStrut(10));
        list.add(createServiceCard("Xe đưa đón sân bay", "300000 đ", "Còn", "Đưa đón sân bay 2 chiều", 4.80));
        list.add(Box.createVerticalStrut(10));
        list.add(createServiceCard("Room service 24/7", "300000 đ", "Còn", "Phục vụ tận phòng mọi thời điểm", 4.90));

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scroll.setPreferredSize(new Dimension(0, 400));
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT); // giữ full chiều ngang
        return scroll;
    }

    // Một thẻ dịch vụ chi tiết
    private JPanel createServiceCard(String name, String price, String status, String desc, double rating) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(
                new CompoundBorder(new LineBorder(GUI_NhanVienLeTan.CARD_BORDER), new EmptyBorder(10, 10, 10, 10)));

        JLabel title = new JLabel(name);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setForeground(new Color(0, 128, 0));

        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(status.equals("Còn") ? GUI_NhanVienLeTan.COLOR_GREEN : GUI_NhanVienLeTan.COLOR_RED);

        JLabel descLabel = new JLabel("<html><i>" + desc + "</i></html>");
        descLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);

        JLabel ratingLabel = new JLabel("★ " + rating);
        ratingLabel.setForeground(new Color(255, 165, 0));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(title);
        info.add(priceLabel);
        info.add(statusLabel);
        info.add(descLabel);
        info.add(ratingLabel);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);
        actions.add(createIconButton("✎", Color.BLUE));
        actions.add(createIconButton("👁", new Color(0, 180, 0)));
        actions.add(createIconButton("🗑", Color.RED));

        card.add(info, BorderLayout.CENTER);
        card.add(actions, BorderLayout.EAST);
        return card;
    }

    // Danh mục dịch vụ theo nhóm
    private JPanel createServiceCategoryPanel() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15)); // 2 hàng, 3 cột
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        grid.add(createCategoryCard("Spa & Massage", "300000 đ", new Color(255, 230, 230)));
        grid.add(createCategoryCard("Nhà hàng cao cấp", "500000 đ", new Color(230, 255, 230)));
        grid.add(createCategoryCard("Xe đưa đón sân bay", "300000 đ", new Color(230, 240, 255)));
        grid.add(createCategoryCard("Phòng gym & fitness", "300000 đ", new Color(255, 245, 230)));
        grid.add(createCategoryCard("Room service 24/7", "300000 đ", new Color(240, 240, 255)));

        // Tiêu đề + nút thêm
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel title = new JLabel("Danh mục dịch vụ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnAdd = new JButton("+ Thêm dịch vụ");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(new EmptyBorder(8, 15, 8, 15));

        wrapper.add(btnAdd, BorderLayout.WEST);

        wrapper.add(title);
        wrapper.add(Box.createVerticalStrut(10));
        wrapper.add(grid);
        return wrapper;
    }

    // Một thẻ danh mục dịch vụ
    private JPanel createCategoryCard(String name, String price, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(
                new CompoundBorder(new LineBorder(GUI_NhanVienLeTan.CARD_BORDER), new EmptyBorder(10, 10, 10, 10)));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setForeground(Color.DARK_GRAY);

        card.add(nameLabel, BorderLayout.CENTER);
        card.add(priceLabel, BorderLayout.SOUTH);
        return card;
    }

    // Tạo nút icon nhỏ
    private JButton createIconButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        btn.setForeground(color);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
