/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Giao dien quan ly khach san cho nhan vien quan ly
 */

package ui.gui;

// ---------------------------
// Chú thích metadata (comment)
// Người code: Phan Minh Thuận
// Mô tả: Thêm nhãn chú thích hiển thị tên người chịu trách nhiệm / phần giao diện thống kê
// Mục đích: Quản lý code, dễ dàng liên hệ khi cần chỉnh sửa
// Ngày tạo: 26/10/2025
// Giờ tạo: 1:52AM
// Lưu ý: cập nhật thời gian/ người sửa khi chỉnh sửa tiếp
// ---------------------------
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import static ui.gui.PanelThongKeContent.ACCENT_BLUE;
import static ui.gui.PanelThongKeContent.CARD_BORDER;
import static ui.gui.PanelThongKeContent.MAIN_BG;
import static ui.gui.PanelThongKeContent.COLOR_GREEN;
import static ui.gui.PanelThongKeContent.COLOR_PURPLE;
import static ui.gui.PanelThongKeContent.COLOR_RED;

/**
 * Giao diện Dashboard cho Nhân viên Quản lý
 */
public class GUI_NhanVienQuanLy extends JFrame {

    // --- Các hằng số màu sắc nội bộ ---
    private final Color SIDEBAR_BG = new Color(245, 250, 255);
    private final Color ACCENT = new Color(124, 58, 237);

    // HẰNG SỐ CHO CARD LAYOUT
    private static final String DASHBOARD_PANEL = "QUAN_LY_CONTENT";
    private static final String EMPLOYEE_PANEL = "NHAN_VIEN_CONTENT";
    private static final String STATISTIC_PANEL = "THONG_KE_CONTENT";
    private static final String PROMOTION_PANEL = "KHUYEN_MAI_CONTENT";

    // CardLayout để chuyển nội dung bên phải
    private CardLayout cardLayout;
    private JPanel contentPanelContainer;

    // Lưu lại các nút menu để đổi màu khi active
    private JButton btnNhanVien;
    private JButton btnDashboard;
    private JButton btnThongKe;
    private JButton btnKhuyenMai;

    public GUI_NhanVienQuanLy() {
        setTitle("Dashboard Quản lý khách sạn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Khởi tạo CardLayout & Container (Khắc phục lỗi khởi tạo 2 lần)
        cardLayout = new CardLayout();
        contentPanelContainer = new JPanel(cardLayout);
        contentPanelContainer.setBackground(MAIN_BG);

        // 2. Thêm Sidebar
        add(createSidebar(), BorderLayout.WEST);

        // 3. Tạo các Panel nội dung riêng biệt
        PanelQuanLyContent panelQuanLyContent = new PanelQuanLyContent();
        PanelNhanVienContent panelNhanVienContent = new PanelNhanVienContent();
        PanelThongKeContent panelThongKeContent = new PanelThongKeContent(); // Thêm Panel Thống kê
        PanelKhuyenMaiContent panelKhuyenMaiContent = new PanelKhuyenMaiContent(); // Thêm Panel Khuyến mãi

        // 4. Thêm các Panel nội dung vào CardLayout
        contentPanelContainer.add(panelQuanLyContent, DASHBOARD_PANEL);
        contentPanelContainer.add(panelNhanVienContent, EMPLOYEE_PANEL);
        contentPanelContainer.add(panelThongKeContent, STATISTIC_PANEL); // Thêm Panel Thống kê
        contentPanelContainer.add(panelKhuyenMaiContent, PROMOTION_PANEL); // Thêm Panel Khuyến mãi

        // 5. Thêm Panel CardLayout vào CENTER của JFrame
        add(contentPanelContainer, BorderLayout.CENTER);

        // 6. Hiển thị Dashboard mặc định và Active button
        showContentPanel(DASHBOARD_PANEL);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(SIDEBAR_BG); // Màu nền sidebar

        // --- Phần Logo ---
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

        // --- Phần Menu ---
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(12, 12, 12, 12));
        menu.setOpaque(false);

        // Tạo các nút menu (lưu lại tham chiếu)
        btnDashboard = createNavButton("Dashboard");
        btnNhanVien = createNavButton("Nhân viên");
        btnThongKe = createNavButton("Thống kê"); // Lưu tham chiếu
        btnKhuyenMai = createNavButton("Khuyến mãi");

        // Gắn ActionListener để chuyển đổi content panel
        btnDashboard.addActionListener(e -> showContentPanel(DASHBOARD_PANEL));
        btnNhanVien.addActionListener(e -> showContentPanel(EMPLOYEE_PANEL));
        btnThongKe.addActionListener(e -> showContentPanel(STATISTIC_PANEL)); // Action cho Thống kê
        btnKhuyenMai.addActionListener(e -> showContentPanel(PROMOTION_PANEL)); // Action Khuyến mãi

        // Thêm nút vào menu
        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnNhanVien);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnThongKe);
        menu.add(Box.createVerticalStrut(8));
        menu.add(btnKhuyenMai);
        menu.add(Box.createVerticalStrut(8));

        JPanel menuWrap = new JPanel(new BorderLayout());
        menuWrap.setOpaque(false);
        menuWrap.setBorder(new EmptyBorder(12, 12, 12, 12));
        menuWrap.add(menu, BorderLayout.NORTH);
        sidebar.add(menuWrap, BorderLayout.CENTER);

        // --- Phần Profile (Bottom) ---
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(12, 12, 12, 12));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        JLabel user = new JLabel("admin");
        user.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel role = new JLabel("Quản lý khách sạn");
        role.setForeground(Color.GRAY);
        bottom.add(user);
        bottom.add(role);
        bottom.add(Box.createVerticalStrut(8));
        JButton logout = new JButton("Đăng xuất");
        logout.setContentAreaFilled(false);
        logout.setBorderPainted(false);
        logout.setForeground(new Color(200, 50, 50));
        bottom.add(logout);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

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
        JButton[] allButtons = { btnDashboard, btnNhanVien, btnThongKe, btnKhuyenMai /* , các nút khác */ };
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
        if (panelName.equals(DASHBOARD_PANEL)) {
            setActiveButton(btnDashboard);
        } else if (panelName.equals(EMPLOYEE_PANEL)) {
            setActiveButton(btnNhanVien);
        } else if (panelName.equals(STATISTIC_PANEL)) { // Kích hoạt nút Thống kê
            setActiveButton(btnThongKe);
        } else if (panelName.equals(PROMOTION_PANEL)) { // Kích hoạt nút Khuyến mãi
            setActiveButton(btnKhuyenMai);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new GUI_NhanVienQuanLy().setVisible(true);
        });
    }
}

// =================================================================================
// PANEL NỘI DUNG 2: DASHBOARD QUẢN LÝ
// =================================================================================

class PanelQuanLyContent extends JPanel {
    // Note: Màu sắc STAT_BG này chỉ dùng nội bộ trong Dashboard Quản lý
    private final Color STAT_BG_1 = new Color(218, 240, 255);
    private final Color STAT_BG_2 = new Color(230, 235, 255);
    private final Color STAT_BG_3 = new Color(255, 235, 240);

    public PanelQuanLyContent() {
        // --- Thiết lập cho JPanel này ---
        setLayout(new BorderLayout());
        setBackground(MAIN_BG); // Sử dụng MAIN_BG đã import
        setBorder(new EmptyBorder(18, 18, 18, 18)); // Lề cho nội dung

        // --- Chỉ thêm Header và Content Panel ---
        add(createHeader(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        // Note: Phần tiêu đề và ngày tháng
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Dashboard Quản lý Khách sạn");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy");
        JLabel date = new JLabel(fmt.format(LocalDate.now()));
        date.setForeground(Color.GRAY);

        header.add(title, BorderLayout.WEST);
        header.add(date, BorderLayout.EAST);
        return header;
    }

    private JPanel createContentPanel() {
        // Note: Cấu trúc chính của Dashboard (Top Zone, Schedule, Tasks, Stats)
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);

        // Zone 1 - Profile + quick stats (TOP)
        JPanel zone1 = createTopZone();
        zone1.setPreferredSize(new Dimension(Integer.MAX_VALUE, 110));
        content.add(zone1, BorderLayout.NORTH);

        // Middle container (Zone2 + Zone3 stacked)
        JPanel middle = new JPanel(new GridBagLayout());
        middle.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 0, 8, 0);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.weightx = 1.0;

        // Zone 2: Schedule (chiều rộng ngang với zone1)
        gc.gridy = 0;
        gc.weighty = 0.38;
        JPanel scheduleCard = createCardWrapper(createSchedulePanel());
        middle.add(scheduleCard, gc);

        // Zone 3: Tasks + stats (Chia 2 cột)
        gc.gridy = 1;
        gc.weighty = 0.62;
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setOpaque(false);
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.insets = new Insets(12, 12, 12, 12);
        gc2.fill = GridBagConstraints.BOTH;

        gc2.gridx = 0;
        gc2.gridy = 0;
        gc2.weightx = 0.66;
        gc2.weighty = 1.0;
        bottom.add(createCardWrapper(createTasksPanel()), gc2);

        gc2.gridx = 1;
        gc2.weightx = 0.34;
        bottom.add(createCardWrapper(createStatsPanel()), gc2);

        middle.add(bottom, gc);
        content.add(middle, BorderLayout.CENTER);

        return content;
    }

    // wrapper để có border/padding giống các card
    private JPanel createCardWrapper(JPanel inner) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(14, 14, 14, 14)));
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    private JPanel createTopZone() {
        // Note: Phần hiển thị Profile chi tiết và 3 ô quick stats
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(12, 12, 12, 12)));

        // left profile
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        left.setOpaque(false);
        JLabel avatar = new JLabel("QL");
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(160, 110, 255));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 20));
        left.add(avatar);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Trần Văn Quản Lý");
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel details = new JLabel("<html>quanly@tbqtthotel.vn • +84 (0) 987-654-321 • Ban Giám đốc</html>");
        details.setForeground(Color.GRAY);
        details.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info.add(name);
        info.add(Box.createVerticalStrut(6));
        info.add(details);
        left.add(info);

        // right quick stats
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        right.setOpaque(false);
        right.add(createStatBox("27h", "Giờ tuần này", STAT_BG_1));
        right.add(createStatBox("12", "Cuộc họp", STAT_BG_2));
        right.add(createStatBox("14", "Công việc", STAT_BG_3));

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);

        return card;
    }

    private JPanel createSchedulePanel() {
        // Note: Bảng Lịch làm việc/Cuộc họp
        JPanel wrap = new JPanel(new BorderLayout(8, 8));
        wrap.setOpaque(false);
        JLabel title = new JLabel("Lịch làm việc & Cuộc họp tuần này");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        wrap.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        String[][] data = {
                { "Thứ Hai", "15/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "7:55", "17:15", "Hoàn thành" },
                { "Thứ Ba", "16/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "8:00", "17:00", "Hoàn thành" },
                { "Thứ Tư", "17/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "7:58", "", "Đang làm" },
                { "Thứ Năm", "18/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "", "", "" },
                { "Thứ Sáu", "19/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "", "", "" }
        };

        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (String[] row : data) {
            JPanel r = new JPanel(new BorderLayout(8, 0));
            r.setOpaque(true);
            r.setBackground(Color.WHITE);
            r.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new LineBorder(new Color(241, 241, 246))));
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            JPanel left = new JPanel();
            left.setOpaque(false);
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            JLabel dn = new JLabel(row[0]);
            dn.setFont(new Font("SansSerif", Font.BOLD, 12));
            JLabel ddate = new JLabel(row[1]);
            ddate.setForeground(Color.GRAY);
            ddate.setFont(new Font("SansSerif", Font.PLAIN, 11));
            left.add(dn);
            left.add(ddate);
            r.add(left, BorderLayout.WEST);

            JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 12));
            mid.setOpaque(false);
            mid.add(new JLabel(row[2] + "  (" + row[3] + ")"));
            mid.add(new JLabel(row[4]));
            r.add(mid, BorderLayout.CENTER);

            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
            right.setOpaque(false);
            if (!row[5].isEmpty())
                right.add(new JLabel("⏱ " + row[5]));
            if (!row[6].isEmpty())
                right.add(new JLabel("⏲ " + row[6]));
            if (!row[7].isEmpty()) {
                JLabel st = new JLabel(row[7]);
                st.setOpaque(true);
                st.setBorder(new EmptyBorder(4, 8, 4, 8));
                if ("Hoàn thành".equals(row[7])) {
                    st.setBackground(new Color(220, 255, 230));
                    st.setForeground(new Color(20, 110, 40));
                } else {
                    st.setBackground(new Color(230, 245, 255));
                    st.setForeground(new Color(10, 90, 180));
                }
                right.add(st);
            }
            r.add(right, BorderLayout.EAST);

            // highlight today
            DayOfWeek rowDay = getDayOfWeek(row[0]);
            if (rowDay == today) {
                r.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8),
                        new LineBorder(new Color(200, 160, 255), 2, true)));
            }

            list.add(r);
            list.add(Box.createVerticalStrut(6));
        }

        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        wrap.add(sp, BorderLayout.CENTER);

        return wrap;
    }

    private JPanel createTasksPanel() {
        // Note: Bảng Công việc quan trọng hôm nay (Task Table)
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel title = new JLabel("Công việc quan trọng hôm nay");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        p.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {
                        { "09:00", "Họp ban giám đốc", "Hoàn thành", "Phòng họp A" },
                        { "10:30", "Duyệt báo cáo doanh thu", "Hoàn thành", "" },
                        { "14:00", "Phỏng vấn ứng viên", "Chưa xong", "" },
                        { "15:30", "Kiểm tra chất lượng dịch vụ", "Chưa xong", "" }
                },
                new String[] { "Thời gian", "Công việc", "Trạng thái", "Ghi chú" }) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(240);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBorder(new EmptyBorder(6, 8, 6, 8));
                String st = v == null ? "" : v.toString();
                if ("Hoàn thành".equals(st)) {
                    lbl.setBackground(new Color(220, 255, 230));
                    lbl.setForeground(new Color(20, 110, 40));
                } else if ("Chưa xong".equals(st)) {
                    lbl.setBackground(new Color(255, 245, 230));
                    lbl.setForeground(new Color(170, 110, 20));
                } else {
                    lbl.setBackground(new Color(240, 240, 245));
                    lbl.setForeground(Color.DARK_GRAY);
                }
                lbl.setOpaque(true);
                return lbl;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    private JPanel createStatsPanel() {
        // Note: 6 ô thống kê nhanh (3x2 grid)
        JPanel p = new JPanel(new GridLayout(3, 2, 12, 12));
        p.setOpaque(false);
        p.add(createStatCard("27h", "Tuần này", STAT_BG_1));
        p.add(createStatCard("12", "Cuộc họp", STAT_BG_2));
        p.add(createStatCard("24", "Nhân viên", new Color(230, 255, 245)));
        p.add(createStatCard("450.000.000đ", "Doanh thu tuần", new Color(230, 255, 230)));
        p.add(createStatCardWithArrow("2h", "Tăng ca", new Color(255, 250, 230), true));
        p.add(createStatCard("★", "Đánh giá", new Color(255, 245, 245)));
        return p;
    }

    private JPanel createStatBox(String big, String small, Color bg) {
        // Note: ô thống kê trên Top Zone
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(bg);
        c.setBorder(new LineBorder(CARD_BORDER));
        JLabel bigLabel = new JLabel(big, SwingConstants.CENTER); // Đổi tên biến để rõ ràng hơn
        bigLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        c.add(bigLabel, BorderLayout.CENTER);
        JLabel lb = new JLabel(small, SwingConstants.CENTER);
        lb.setForeground(Color.DARK_GRAY);
        c.add(lb, BorderLayout.SOUTH);
        return c;
    }

    private JPanel createStatCard(String value, String label, Color bg) {
        // Note: ô thống kê trong Stats Panel
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(new LineBorder(CARD_BORDER));
        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setForeground(Color.DARK_GRAY);
        card.add(v, BorderLayout.CENTER);
        card.add(l, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createStatCardWithArrow(String value, String label, Color bg, boolean up) {
        // Note: ô thống kê có mũi tên tăng/giảm
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(new LineBorder(CARD_BORDER));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        top.setOpaque(false);
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel arr = new JLabel(up ? "▲" : "▼");
        arr.setForeground(up ? new Color(0, 140, 0) : new Color(200, 50, 50));
        top.add(v);
        top.add(arr);
        card.add(top, BorderLayout.CENTER);
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setForeground(Color.DARK_GRAY);
        card.add(l, BorderLayout.SOUTH);
        return card;
    }

    private DayOfWeek getDayOfWeek(String vnName) {
        // Note: Chuyển tên tiếng Việt sang DayOfWeek
        switch (vnName) {
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
// PANEL NỘI DUNG 3: QUẢN LÝ NHÂN VIÊN
// =================================================================================

class PanelNhanVienContent extends JPanel {
    // Note: Dùng lại các hằng số màu sắc từ PanelThongKeContent (đã đổi tên)

    public PanelNhanVienContent() {
        setLayout(new BorderLayout());
        setBackground(MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        // Note: Tiêu đề Quản lý Nhân viên và nút Thêm
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Quản lý Nhân viên");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        JButton btnAdd = new JButton("Thêm nhân viên");
        btnAdd.setBackground(ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorder(new EmptyBorder(6, 12, 6, 12));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(btnAdd, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        // Note: Cấu trúc chính: Controls (Search/Filter/Stats), Table, Footer
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        content.add(createTopControls(), BorderLayout.NORTH);
        content.add(createCenterArea(), BorderLayout.CENTER);
        content.add(createFooterSummary(), BorderLayout.SOUTH);

        return content;
    }

    // ======== Đầu: tìm + lọc + thông tin=========
    private JPanel createTopControls() {
        // Note: Chứa thanh tìm kiếm và các ô thống kê nhanh
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(10, 15, 10, 15));

        // ===== Hàng tìm kiếm + lọc =====
        JPanel searchFilter = createSearchFilterPanel();
        searchFilter.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(searchFilter);

        // ===== Hàng thống kê =====
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 15, 0)); // 3 cột, cách nhau 15px
        statsRow.setOpaque(false);
        statsRow.setBorder(new EmptyBorder(10, 0, 5, 0));
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsRow.add(createStatCard("6", "Tổng nhân viên", Color.gray));
        statsRow.add(createStatCard("4", "Nhân viên lễ tân", new Color(99, 132, 244)));
        statsRow.add(createStatCard("2", "Nhân viên quản lý", new Color(186, 85, 211)));

        top.add(statsRow);

        return top;
    }

    private JPanel createSearchFilterPanel() {
        // Note: Thanh tìm kiếm và ComboBox Lọc
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        JTextField searchField = new JTextField("");
        String placeholder = " Tìm kiếm theo mã NV, họ tên, số điện thoại, email, CCCD...";
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
                new LineBorder(CARD_BORDER),
                new EmptyBorder(6, 8, 6, 8)));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JComboBox<String> cbType = new JComboBox<>(new String[] { "Tất cả loại", "Lễ tân", "Quản lý" });
        cbType.setPreferredSize(new Dimension(160, 30));
        searchPanel.add(cbType, BorderLayout.EAST);

        return searchPanel;
    }

    private JPanel createStatCard(String value, String label, Color color) {
        // Note: ô thống kê nhanh cho nhân viên
        JPanel card = new JPanel(new BorderLayout());
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(new Color(230, 230, 230)), new EmptyBorder(12, 18, 12, 18)));
        card.setPreferredSize(new Dimension(200, 60));

        JLabel val = new JLabel(value, SwingConstants.LEFT);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));
        val.setForeground(color);

        JLabel lab = new JLabel(label, SwingConstants.LEFT);
        lab.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lab.setForeground(Color.GRAY);

        card.add(lab, BorderLayout.NORTH);
        card.add(Box.createRigidArea(new Dimension(0, 2)));
        card.add(val, BorderLayout.SOUTH);

        return card;
    }

    // ======== Giữa: Danh sách nhân viên ========
    private JPanel createCenterArea() {
        // Note: Phần bảng dữ liệu nhân viên
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);

        center.add(createTableHeader(), BorderLayout.NORTH);
        center.add(createEmployeeScroll(), BorderLayout.CENTER);

        return center;
    }

    // Note: Trọng số của các cột (cần được đồng bộ giữa header và row)
    private static final double[] COL_WEIGHTS = {
            0.08, // Mã NV
            0.15, // Họ tên (Tăng để hiển thị đủ)
            0.08, // Ngày sinh
            0.08, // Giới tính
            0.15, // Liên hệ (Tăng để hiển thị đủ)
            0.12, // CCCD
            0.10, // Loại nhân viên
            0.14, // Thao tác
    };

    private JPanel createTableHeader() {
        // Note: Dòng tiêu đề của bảng
        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setOpaque(false);
        header.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(8, 8, 8, 8)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 8, 0, 8);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int col = 0;
        addHeaderLabel(header, "Mã NV", col++, COL_WEIGHTS[0], gbc);
        addHeaderLabel(header, "Họ tên", col++, COL_WEIGHTS[1], gbc);
        addHeaderLabel(header, "Ngày sinh", col++, COL_WEIGHTS[2], gbc);
        addHeaderLabel(header, "Giới tính", col++, COL_WEIGHTS[3], gbc);
        addHeaderLabel(header, "Liên hệ", col++, COL_WEIGHTS[4], gbc);
        addHeaderLabel(header, "CCCD", col++, COL_WEIGHTS[5], gbc);
        addHeaderLabel(header, "Loại nhân viên", col++, COL_WEIGHTS[6], gbc);
        addHeaderLabel(header, "Thao tác", col++, COL_WEIGHTS[7], gbc);

        return header;
    }

    private void addHeaderLabel(JPanel panel, String text, int gridx, double weightx, GridBagConstraints gbcBase) {
        GridBagConstraints gbc = (GridBagConstraints) gbcBase.clone();
        gbc.gridx = gridx;
        gbc.weightx = weightx;

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(90, 90, 90));
        panel.add(lbl, gbc);
    }

    private JScrollPane createEmployeeScroll() {
        // Note: Khung cuộn chứa các hàng nhân viên
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<Employee> employees = sampleEmployees();

        for (Employee e : employees) {
            listPanel.add(createEmployeeRow(e));
            listPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(MAIN_BG);
        scroll.setPreferredSize(new Dimension(0, 420));
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private JPanel createEmployeeRow(Employee e) {
        // Note: Một hàng dữ liệu nhân viên
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        int col = 0;
        double[] weights = COL_WEIGHTS; // Sử dụng trọng số đã định nghĩa

        // ===== Mã NV =====
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.setOpaque(false);
        JLabel idLabel = new JLabel(" " + e.code);
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idPanel.add(idLabel, BorderLayout.CENTER);
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(idPanel, gbc);

        // ===== Họ tên =====
        JLabel nameLbl = new JLabel(e.name);
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(nameLbl, gbc);

        // ===== Ngày sinh =====
        JLabel dob = new JLabel(e.dob);
        dob.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dob.setForeground(new Color(100, 100, 100));
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(dob, gbc);

        // ===== Giới tính =====
        JLabel gender = new JLabel(e.gender);
        gender.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gender.setForeground(new Color(100, 100, 100));
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(gender, gbc);

        // ===== Liên hệ (Thay icon emoji bằng ký tự đơn giản hơn để tránh lỗi font)
        // =====
        JPanel contact = new JPanel(new GridLayout(2, 1, 0, 2));
        contact.setOpaque(false);
        JLabel phone = new JLabel("☎ " + e.phone); // Dùng ký tự điện thoại ổn định hơn
        phone.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        phone.setForeground(new Color(100, 100, 100));
        JLabel email = new JLabel("✉ " + e.email); // Dùng ký tự email ổn định hơn
        email.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        email.setForeground(new Color(100, 100, 100));
        contact.add(phone);
        contact.add(email);
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(contact, gbc);

        // ===== CCCD (Thay icon emoji bằng ký tự đơn giản hơn) =====
        JLabel cccd = new JLabel("ID " + e.cccd); // Dùng ID thay cho biểu tượng thẻ
        cccd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cccd.setForeground(new Color(100, 100, 100));
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(cccd, gbc);

        // ===== Loại nhân viên =====
        JLabel role = new JLabel(e.role, SwingConstants.CENTER);
        role.setFont(new Font("Segoe UI", Font.BOLD, 12));
        role.setOpaque(true);
        role.setBackground(getRoleColor(e.role));
        role.setForeground(Color.WHITE);
        role.setBorder(new EmptyBorder(4, 10, 4, 10));
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(role, gbc);

        // ===== Thao tác (Thay icon emoji bằng ký tự đơn giản hơn) =====
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton edit = new JButton("Sửa"); // Thay thế
        JButton del = new JButton("Xóa"); // Thay thế
        for (JButton b : new JButton[] { edit, del }) {
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }
        edit.setForeground(new Color(15, 118, 255));
        del.setForeground(Color.RED);
        actions.add(edit);
        actions.add(del);
        gbc.gridx = col;
        gbc.weightx = weights[col++];
        row.add(actions, gbc);

        return row;
    }

    private Color getRoleColor(String role) {
        // Note: Khắc phục lỗi Switch Expression (Sử dụng Switch Statement truyền thống)
        switch (role) {
            case "Lễ tân":
                return new Color(99, 132, 244);
            case "Quản lý":
                return new Color(186, 85, 211);
            default:
                return new Color(180, 180, 180);
        }
    }

    // ======== FOOTER: tổng kết ========
    private JPanel createFooterSummary() {
        // Note: Dòng tóm tắt tổng số nhân viên ở cuối trang
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(true);
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 15, 10, 15)));

        JLabel total = new JLabel("Tổng số nhân viên: 6");
        total.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        total.setForeground(Color.DARK_GRAY);

        JLabel breakdown = new JLabel("Lễ tân: 4 | Quản lý: 2");
        breakdown.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        breakdown.setForeground(Color.DARK_GRAY);

        footer.add(total, BorderLayout.WEST);
        footer.add(breakdown, BorderLayout.EAST);

        return footer;
    }

    // ======== Data mẫu ========
    private List<Employee> sampleEmployees() {
        // Note: Dữ liệu mẫu cho bảng Nhân viên
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("NV001", "Nguyễn Văn An", "15/5/1995", "Nam", "0901234567", "an.nguyen@tbqtt.com",
                "001095012345", "Lễ tân"));
        list.add(new Employee("NV002", "Trần Thị Bình", "20/8/1992", "Nữ", "0901234568", "binh.tran@tbqtt.com",
                "001092054321", "Lễ tân"));
        list.add(new Employee("NV003", "Lê Văn Cường", "10/3/1990", "Nam", "0901234569", "cuong.le@tbqtt.com",
                "001090067890", "Quản lý"));
        list.add(new Employee("NV004", "Phạm Thị Dung", "5/12/1993", "Nữ", "0901234570", "dung.pham@tbqtt.com",
                "001093098765", "Quản lý"));
        list.add(new Employee("NV005", "Võ Minh Tuấn", "22/7/1994", "Nam", "0901234571", "tuan.vo@tbqtt.com",
                "001094123456", "Lễ tân"));
        list.add(new Employee("NV006", "Hoàng Thị Mai", "18/11/1991", "Nữ", "0901234572", "mai.hoang@tbqtt.com",
                "001091789012", "Lễ tân"));
        return list;
    }

    private static class Employee {
        // Note: Lớp cấu trúc dữ liệu cho nhân viên
        String code, name, dob, gender, phone, email, cccd, role;

        Employee(String code, String name, String dob, String gender, String phone, String email, String cccd,
                String role) {
            this.code = code;
            this.name = name;
            this.dob = dob;
            this.gender = gender;
            this.phone = phone;
            this.email = email;
            this.cccd = cccd;
            this.role = role;
        }
    }

}

// =================================================================================
// PANEL NỘI DUNG 4: QUẢN LÝ KHUYẾN MÃI
// =================================================================================

// Lớp giao diện quản lý khuyến mãi, kế thừa từ JPanel
class PanelKhuyenMaiContent extends JPanel {

    // Constructor khởi tạo giao diện
    public PanelKhuyenMaiContent() {
        setLayout(new BorderLayout(15, 15)); // bố cục chính: header ở trên, nội dung ở giữa
        setBackground(MAIN_BG); // màu nền chính
        setBorder(new EmptyBorder(15, 15, 15, 15)); // khoảng cách viền

        add(createHeader(), BorderLayout.NORTH); // thêm tiêu đề ở trên
        add(createMainContent(), BorderLayout.CENTER); // thêm nội dung chính ở giữa
    }

    // Tạo phần tiêu đề gồm tên trang và nút "Thêm khuyến mãi"
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false); // không tô nền

        JLabel title = new JLabel("Quản lý Khuyến mãi"); // tiêu đề
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST); // đặt tiêu đề bên trái

        JButton btnAdd = new JButton("Thêm khuyến mãi"); // nút thêm
        btnAdd.setBackground(ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorder(new EmptyBorder(6, 12, 6, 12));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(btnAdd, BorderLayout.EAST); // đặt nút bên phải

        return header;
    }

    // Tạo phần nội dung chính gồm: tìm kiếm → bảng → thống kê → thẻ
    private JPanel createMainContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // bố cục dọc
        content.setOpaque(false);

        content.add(createSearchPanel()); // thanh tìm kiếm
        content.add(Box.createVerticalStrut(10)); // khoảng cách
        content.add(createPromotionTable()); // bảng khuyến mãi
        content.add(Box.createVerticalStrut(10));
        content.add(createSummaryStats()); // thống kê
        content.add(Box.createVerticalStrut(10));
        content.add(createPromotionCards()); // thẻ khuyến mãi

        return content;
    }

    // Tạo thanh tìm kiếm gồm ô nhập và nút "Tìm"
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44)); // chiều cao cố định

        JTextField searchField = new JTextField(); // ô nhập tìm kiếm
        searchField.setPreferredSize(new Dimension(600, 36)); // chiều rộng
        String placeholder = "Tìm kiếm theo mã, tên khuyến mãi, trạng thái...";
        Color placeholderColor = Color.GRAY;
        Color defaultColor = UIManager.getColor("TextField.foreground");

        // Thiết lập placeholder
        searchField.setText(placeholder);
        searchField.setForeground(placeholderColor);

        // Xử lý focus để hiển thị/xóa placeholder
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

        // Viền ô nhập
        searchField.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER),
                new EmptyBorder(6, 8, 6, 8)));

        searchPanel.add(searchField, BorderLayout.CENTER); // đặt ô nhập ở giữa

        JButton btnSearch = new JButton("Tìm"); // nút tìm kiếm
        btnSearch.setPreferredSize(new Dimension(100, 36));
        btnSearch.setBackground(ACCENT_BLUE);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchPanel.add(btnSearch, BorderLayout.EAST); // đặt nút bên phải

        return searchPanel;
    }

    // Tạo bảng danh sách khuyến mãi
    private JScrollPane createPromotionTable() {
        String[] columns = { "Tên khuyến mãi", "Giảm giá", "Thời gian", "Trạng thái" };
        Object[][] data = {
                { "KM hè 2024", "20%", "15/5 - 30/6", "Đang hoạt động" },
                { "KM hè 2024", "20%", "15/5 - 30/6", "Đang hoạt động" },
                { "KM hè 2024 dành cho VENDORS", "15%", "15/5 - 30/6", "Đang hoạt động" },
                { "KM VIP", "50%", "15/5 - 30/6", "Đã hết hạn" }
        };

        // Model bảng không cho sửa dữ liệu
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setFillsViewportHeight(true);

        // TODO: Thêm renderer trạng thái ở đây nếu cần tô màu

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi"));
        return scroll;
    }

    // Tạo phần thống kê tổng quan
    private JPanel createSummaryStats() {
        JPanel stats = new JPanel(new GridLayout(1, 4, 15, 0)); // 4 cột
        stats.setOpaque(false);

        stats.add(createStatCard("4", "Khuyến mãi", COLOR_PURPLE));
        stats.add(createStatCard("3", "Đang áp dụng", COLOR_GREEN));
        stats.add(createStatCard("902", "Lượt sử dụng", new Color(255, 200, 0)));
        stats.add(createStatCard("1", "Đã hết hạn", COLOR_RED));

        return stats;
    }

    // Tạo từng ô thống kê
    private JPanel createStatCard(String value, String label, Color color) {
        // Tạo panel với bố cục BorderLayout để chia thành phần giữa và dưới
        JPanel card = new JPanel(new BorderLayout()); // Đặt màu nền trắng cho ô thống kê
        card.setBackground(Color.WHITE); // Đặt viền: đường viền màu CARD_BORDER + khoảng cách bên trong (padding)
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(12, 18, 12, 18))); // viền ngoài
                                                                                                          // + padding:
                                                                                                          // trên, trái,
                                                                                                          // dưới, phải

        // Tạo label hiển thị giá trị thống kê (ví dụ: "4")
        JLabel val = new JLabel(value, SwingConstants.CENTER); // căn giữa ngang
        val.setFont(new Font("SansSerif", Font.BOLD, 18)); // chữ đậm, cỡ lớn
        val.setForeground(color); // màu chữ theo loại thống kê

        // Tạo label hiển thị tên thống kê (ví dụ: "Khuyến mãi")
        JLabel lab = new JLabel(label, SwingConstants.CENTER);
        lab.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lab.setForeground(Color.GRAY);

        card.add(val, BorderLayout.CENTER); // Thêm label giá trị vào giữa ô
        card.add(lab, BorderLayout.SOUTH); // Thêm label mô tả vào phía dưới ô

        return card;
    }

    // Tạo các thẻ khuyến mãi
    private JPanel createPromotionCards() {
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0)); // 4 thẻ
        cards.setOpaque(false);

        cards.add(createPromoCard("SUMMER24", "20%", "15/5 - 30/6"));
        cards.add(createPromoCard("WED2024", "500.000đ", "15/5 - 30/6"));
        cards.add(createPromoCard("VENDOR24", "15%", "15/5 - 30/6"));
        cards.add(createPromoCard("VIP", "50%", "15/5 - 30/6"));

        return cards;
    }

    // Tạo một thẻ khuyến mãi hiển thị mã, giảm giá và thời gian áp dụng
    private JPanel createPromoCard(String code, String discount, String time) {
        JPanel card = new JPanel(); // Tạo panel chứa nội dung thẻ
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // Đặt layout theo chiều dọc (BoxLayout Y_AXIS)
        card.setBackground(Color.WHITE); // Đặt màu nền trắng cho thẻ
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(12, 12, 12, 12))); // Đặt viền
                                                                                                          // ngoài:
                                                                                                          // đường viền
                                                                                                          // màu
                                                                                                          // CARD_BORDER
                                                                                                          // + khoảng
                                                                                                          // cách bên
                                                                                                          // trong 12px

        // Tạo label hiển thị mã khuyến mãi (ví dụ: "SUMMER24")
        JLabel lblCode = new JLabel(code);
        lblCode.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblCode.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tạo label hiển thị mức giảm giá (ví dụ: "Giảm giá: 20%")
        JLabel lblDiscount = new JLabel("Giảm giá: " + discount);
        lblDiscount.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblDiscount.setForeground(Color.GRAY);
        lblDiscount.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tạo label hiển thị thời gian áp dụng (ví dụ: "Thời gian: 15/5 - 30/6")
        JLabel lblTime = new JLabel("Thời gian: " + time);
        lblTime.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblTime.setForeground(Color.GRAY);
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm các label vào panel theo thứ tự: mã → giảm giá → thời gian
        card.add(lblCode);
        card.add(Box.createVerticalStrut(6));
        card.add(lblDiscount);
        card.add(lblTime);

        return card;
    }
}

// =================================================================================
// PANEL NỘI DUNG 5: THỐNG KÊ & BÁO CÁO (GUI_ThongKeBaoCao cũ)
// =================================================================================

class PanelThongKeContent extends JPanel {

    // --- Các hằng số màu sắc (Được sử dụng làm nguồn cho các lớp khác) ---
    public static final Color MAIN_BG = new Color(242, 245, 250);
    public static final Color CARD_BORDER = new Color(222, 226, 230);
    public static final Color ACCENT_BLUE = new Color(24, 90, 219);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_GREEN = new Color(50, 168, 82);
    public static final Color COLOR_RED = new Color(217, 30, 24);
    public static final Color COLOR_PURPLE = new Color(153, 51, 204);
    public static final Color COLOR_ORANGE = new Color(255, 140, 0);
    public static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);

    // Màu sắc trong biểu đồ
    private static final Color CHART_COLOR_REVENUE = new Color(70, 130, 180); // Steel Blue
    private static final Color CHART_COLOR_BOOKING = new Color(46, 204, 113); // Emerald
    private static final Color CHART_COLOR_RATE = new Color(255, 179, 0); // Orange

    public PanelThongKeContent() {
        // Note: Panel chính chứa toàn bộ nội dung thống kê
        setLayout(new BorderLayout());

        // Tạo Panel chứa toàn bộ nội dung chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(MAIN_BG);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Thêm Header và Content
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createSummaryFooter(), BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        // Note: Phần tiêu đề, phụ đề và nút Xuất/Lọc
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Thống kê & Báo cáo");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Phân tích hiệu suất hoạt động khách sạn");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(COLOR_TEXT_MUTED);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(title);
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        // Nút Xuất báo cáo và Lọc năm
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JComboBox<String> yearFilter = new JComboBox<>(new String[] { "Năm 2024", "Năm 2023", "Năm 2022" });
        rightPanel.add(yearFilter);

        JButton btnExport = new JButton("Xuất báo cáo");
        btnExport.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExport.setBackground(ACCENT_BLUE);
        btnExport.setForeground(COLOR_WHITE);
        btnExport.setFocusPainted(false);
        btnExport.setBorderPainted(false);
        btnExport.setBorder(new EmptyBorder(8, 15, 8, 15));
        rightPanel.add(btnExport);

        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createContentPanel() {
        // Note: Cấu trúc chính 2 hàng (Stat Cards và Biểu đồ)
        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setOpaque(false);

        // --- Hàng 1: Stat Cards ---
        content.add(createStatCardsPanel(), BorderLayout.NORTH);

        // --- Hàng 2: Biểu đồ (GridBagLayout 2x2)
        JPanel centerGrid = new JPanel(new GridBagLayout());
        centerGrid.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 15); // Khoảng cách giữa các panel

        // 1. Cột Trái trên (Doanh thu)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.70;
        gbc.weighty = 0.5;
        centerGrid.add(createRevenueChartPanel(), gbc);

        // 2. Cột Phải trên (Phân loại phòng)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.30;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 0, 15, 0);
        centerGrid.add(createRoomTypeChartPanel(), gbc);

        // 3. Cột Trái Dưới (Booking)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.70;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 0, 0, 15);
        centerGrid.add(createBookingChartPanel(), gbc);

        // 4. Cột Phải Dưới (Rate)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.30;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerGrid.add(createRateChartPanel(), gbc);

        content.add(centerGrid, BorderLayout.CENTER);

        return content;
    }

    // =================================================================================
    // CÁC HÀM TẠO COMPONENT DASHBOARD
    // =================================================================================

    private JPanel createStatCardsPanel() {
        // Note: 4 thẻ thống kê ở hàng trên cùng
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0)); // 1 hàng, 4 cột
        panel.setOpaque(false);

        // Stat 1: Tổng doanh thu ($ - Icon đại diện)
        panel.add(createStatCard("Tổng doanh thu", "13.700.000.000 ₫", "+12.5%", true, COLOR_GREEN.darker().darker(),
                "$"));
        // Stat 2: Tổng booking (B - Icon đại diện)
        panel.add(createStatCard("Tổng booking", "2,435", "+5.3%", true, CHART_COLOR_BOOKING, "B"));
        // Stat 3: Tỷ lệ lấp đầy (P - Icon đại diện)
        panel.add(createStatCard("Tỷ lệ lấp đầy", "90.0%", "-2.1%", false, COLOR_PURPLE, "P"));
        // Stat 4: DT trung bình/tháng (A - Icon đại diện)
        panel.add(createStatCard("DT trung bình/tháng", "1.141.666.667 ₫", "+5.8%", true, COLOR_ORANGE, "A"));

        return panel;
    }

    /**
     * Helper tạo một thẻ thống kê (Stat Card)
     */
    private JPanel createStatCard(String title, String value, String change, boolean isPositive, Color accentColor,
            String iconChar) {
        // Note: Thiết kế thẻ thống kê với màu nền nhạt cho icon
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(COLOR_WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));

        // Top: Title and Icon
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titleLabel.setForeground(COLOR_TEXT_MUTED);
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Simple Icon Placeholder (đã sửa để hiển thị ổn định)
        JLabel icon = new JLabel(iconChar, SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(30, 30));
        icon.setFont(new Font("SansSerif", Font.BOLD, 18));
        icon.setForeground(accentColor);
        icon.setOpaque(true);
        // Thiết lập màu nền icon nhạt hơn (dùng alpha 30 để tạo nền nhạt)
        icon.setBackground(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 30));

        topPanel.add(icon, BorderLayout.EAST);

        // Center: Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        card.add(valueLabel, BorderLayout.CENTER);

        // Bottom: Change (Tăng/giảm)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel changeLabel = new JLabel(change);
        changeLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        changeLabel.setForeground(isPositive ? COLOR_GREEN : COLOR_RED);
        bottomPanel.add(changeLabel, BorderLayout.WEST);

        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    /**
     * Tạo Panel chứa biểu đồ Doanh thu theo tháng
     */
    private JPanel createRevenueChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Doanh thu theo tháng");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, BorderLayout.NORTH);

        // Placeholder cho biểu đồ đường
        JPanel chart = new ChartPlaceholder("LINE", CHART_COLOR_REVENUE);
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo Panel chứa biểu đồ Phân bổ loại phòng
     */
    private JPanel createRoomTypeChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Phân bổ loại phòng");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, BorderLayout.NORTH);

        // Placeholder cho biểu đồ tròn (Pie Chart)
        JPanel chart = new PieChartPlaceholder();
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo Panel chứa biểu đồ Số lượng booking
     */
    private JPanel createBookingChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Số lượng booking");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, BorderLayout.NORTH);

        // Placeholder cho biểu đồ cột
        JPanel chart = new ChartPlaceholder("BAR_GREEN", CHART_COLOR_BOOKING);
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo Panel chứa biểu đồ Tỷ lệ lấp đầy theo ngày
     */
    private JPanel createRateChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel title = new JLabel("Tỷ lệ lấp đầy theo ngày");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(title, BorderLayout.NORTH);

        // Placeholder cho biểu đồ cột (màu cam)
        JPanel chart = new ChartPlaceholder("BAR_ORANGE", CHART_COLOR_RATE);
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    // =================================================================================
    // FOOTER TÓM TẮT HIỆU SUẤT
    // =================================================================================

    private JPanel createSummaryFooter() {
        // Note: Thanh tóm tắt hiệu suất ở cuối trang
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        footer.setBackground(COLOR_WHITE);
        footer.setBorder(new CompoundBorder(
                new LineBorder(CARD_BORDER, 1),
                new EmptyBorder(10, 0, 10, 0)));

        footer.add(createSummaryItem("+12.5%", "Tăng trưởng doanh thu", COLOR_GREEN));
        footer.add(createSummaryItem("89.2", "Tỷ lệ lấp đầy trung bình", ACCENT_BLUE));
        footer.add(createSummaryItem("4.8/5", "Đánh giá khách hàng", COLOR_PURPLE));

        return footer;
    }

    private JPanel createSummaryItem(String value, String label, Color color) {
        // Note: Một mục trong thanh tóm tắt
        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelLabel.setForeground(COLOR_TEXT_MUTED);
        labelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        item.add(valueLabel);
        item.add(labelLabel);
        return item;
    }

    // =================================================================================
    // LỚP CUSTOM CHỈ ĐỂ VẼ PLACEHOLDER CHO BIỂU ĐỒ (ĐÃ SỬA LỖI TRỤC X)
    // =================================================================================

    /**
     * Lớp Placeholder cho biểu đồ cột và biểu đồ đường
     */
    private static class ChartPlaceholder extends JPanel {
        private final String type;
        private final Color color;

        public ChartPlaceholder(String type, Color color) {
            this.type = type;
            this.color = color;
            setPreferredSize(new Dimension(500, 250));
            setBackground(COLOR_WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 30;
            int labelPadding = 15;
            int yAxisLabelWidth = 50;

            int chartHeight = height - 2 * padding;
            // Đã sửa lỗi T12
            int chartWidth = width - (padding * 2) - yAxisLabelWidth;

            // Draw Y axis
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(padding + yAxisLabelWidth, height - padding, padding + yAxisLabelWidth, padding);

            // Draw X axis
            g2d.drawLine(padding + yAxisLabelWidth, height - padding, padding + yAxisLabelWidth + chartWidth,
                    height - padding);

            // Draw X-axis labels
            g2d.setColor(Color.GRAY);
            String[] xLabels = { "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12" };
            int numPoints = xLabels.length;

            if (type.equals("BAR_ORANGE")) {
                xLabels = new String[] { "T2", "T3", "T4", "T5", "T6", "T7", "CN" };
                numPoints = 7;
            }

            int spacing;
            if (numPoints > 1) {
                spacing = numPoints - 1;
            } else {
                spacing = 1;
            }

            for (int i = 0; i < xLabels.length; i++) {
                if (type.equals("BAR_ORANGE") && i >= numPoints)
                    break;

                int x;
                x = padding + yAxisLabelWidth + i * chartWidth / spacing;

                String label = xLabels[i];
                g2d.drawString(label, x - g2d.getFontMetrics().stringWidth(label) / 2, height - padding + labelPadding);
            }

            // Draw Y-axis labels
            g2d.setColor(Color.GRAY);
            if (type.startsWith("LINE")) {
                String[] yLabels = { "0.0", "0.4", "0.8", "1.2", "1.6B" };
                int numYLabels = yLabels.length;

                for (int i = 0; i < numYLabels; i++) {
                    int y = height - padding - i * (chartHeight / (numYLabels - 1));
                    String label = yLabels[i];

                    if (i > 0) {
                        g2d.setColor(new Color(240, 240, 240));
                        g2d.drawLine(padding + yAxisLabelWidth, y, padding + yAxisLabelWidth + chartWidth, y);
                        g2d.setColor(Color.GRAY);
                    }
                    g2d.drawString(label, padding, y + g2d.getFontMetrics().getHeight() / 4);
                }
            } else if (type.equals("BAR_GREEN")) {
                String[] yLabels = { "0", "65", "130", "195", "260" };
                int numYLabels = yLabels.length;
                for (int i = 0; i < numYLabels; i++) {
                    int y = height - padding - i * (chartHeight / (numYLabels - 1));
                    String label = yLabels[i];
                    g2d.drawString(label, padding, y + g2d.getFontMetrics().getHeight() / 4);
                }
            } else if (type.equals("BAR_ORANGE")) {
                String[] yLabels = { "0", "25", "50", "75", "100" };
                int numYLabels = yLabels.length;
                for (int i = 0; i < numYLabels; i++) {
                    int y = height - padding - i * (chartHeight / (numYLabels - 1));
                    String label = yLabels[i];
                    g2d.drawString(label, padding, y + g2d.getFontMetrics().getHeight() / 4);
                }
            }

            // Draw placeholder data
            g2d.setColor(color);

            if (type.startsWith("LINE")) {
                double[] revenueData = { 0.88, 0.92, 0.83, 1.15, 1.25, 1.45, 1.55, 1.5, 1.25, 1.15, 0.95, 1.35 };
                double maxVal = 1.6;
                double minVal = 0.0;
                double range = maxVal - minVal;

                g2d.setStroke(new BasicStroke(2));
                for (int i = 0; i < xLabels.length; i++) {
                    int pX1 = padding + yAxisLabelWidth + i * chartWidth / spacing;
                    int pY1 = height - padding - (int) ((revenueData[i] - minVal) * chartHeight / range);

                    if (i < xLabels.length - 1) {
                        int pX2 = padding + yAxisLabelWidth + (i + 1) * chartWidth / spacing;
                        int pY2 = height - padding - (int) ((revenueData[i + 1] - minVal) * chartHeight / range);

                        g2d.drawLine(pX1, pY1, pX2, pY2);
                    }
                    g2d.fillOval(pX1 - 3, pY1 - 3, 6, 6);
                }
            } else {
                int barWidth = chartWidth / spacing / 3;

                for (int i = 0; i < numPoints; i++) {
                    double val = 0;
                    double chartMax = 1;

                    if (type.equals("BAR_GREEN")) {
                        double[] bookingData = { 150, 135, 190, 180, 220, 240, 255, 250, 200, 195, 170, 230 };
                        val = bookingData[i];
                        chartMax = 260;
                    } else if (type.equals("BAR_ORANGE")) {
                        double[] rateData = { 85, 78, 92, 90, 95, 98, 99 };
                        val = rateData[i];
                        chartMax = 100;
                    }

                    if (val > 0) {
                        int barHeight = (int) (val * chartHeight / chartMax);
                        int xCenter = padding + yAxisLabelWidth + i * chartWidth / spacing;
                        int x = xCenter - (barWidth / 2);
                        int y = height - padding - barHeight;
                        g2d.fillRect(x, y, barWidth, barHeight);
                    }
                }
            }
        }
    }

    /**
     * Lớp Placeholder cho biểu đồ tròn (Pie Chart)
     */
    private static class PieChartPlaceholder extends JPanel {
        private final Color[] colors = {
                new Color(70, 130, 180), // Standard 35%
                new Color(46, 204, 113), // Deluxe 30%
                new Color(255, 179, 0), // Suite 20%
                new Color(217, 30, 24) // Presidential 15%
        };
        private final int[] percentages = { 35, 30, 20, 15 };
        private final String[] labels = { "Standard 35%", "Deluxe 30%", "Suite 20%", "Presidential 15%" };

        public PieChartPlaceholder() {
            setPreferredSize(new Dimension(500, 250));
            setBackground(COLOR_WHITE);
            setLayout(new GridBagLayout());

            // Replicate the legend arrangement (Right side of the pie)
            JPanel legendPanel = new JPanel();
            legendPanel.setOpaque(false);
            legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
            legendPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

            for (int i = 0; i < labels.length; i++) {
                JLabel label = new JLabel(labels[i]);
                label.setForeground(colors[i]);
                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                legendPanel.add(label);
            }

            GridBagConstraints gbc = new GridBagConstraints();

            // Add Pie Chart (Custom Paint)
            gbc.gridx = 0;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            add(new PiePanel(), gbc);

            // Add Legend
            gbc.gridx = 1;
            gbc.weightx = 0.5;
            gbc.fill = GridBagConstraints.NONE;
            add(legendPanel, gbc);
        }

        private class PiePanel extends JPanel {
            public PiePanel() {
                setOpaque(false);
                setPreferredSize(new Dimension(200, 200));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int diameter = Math.min(getWidth(), getHeight()) - 20;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                int startAngle = 90;
                for (int i = 0; i < percentages.length; i++) {
                    int angle = (int) (percentages[i] * 3.6);
                    g2d.setColor(colors[i]);
                    g2d.fillArc(x, y, diameter, diameter, startAngle, -angle);
                    startAngle -= angle;
                }
            }
        }
    }
}
