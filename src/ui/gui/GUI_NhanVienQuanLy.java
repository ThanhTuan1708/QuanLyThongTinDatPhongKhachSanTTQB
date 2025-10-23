package ui.gui;
// ---------------------------
// Chú thích metadata (comment)
// Người code: Nguyễn Phong Tuấn
// Mô tả: Thêm nhãn chú thích hiển thị tên người chịu trách nhiệm / phần giao diện profile với dashboard, thanh menu, Panel đặt phòng
// Mục đích: Quản lý code, dễ dàng liên hệ khi cần chỉnh sửa
// Ngày tạo: 23/10/2025
// Giờ tạo: 19:32
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

import static ui.gui.GUI_NhanVienLeTan.ACCENT_BLUE;
import static ui.gui.GUI_NhanVienLeTan.CARD_BORDER;

/**
 * Giao diện Dashboard cho Nhân viên Quản lý
 * Format và cấu trúc tương tự GUI_NhanVienLeTan
 */
public class GUI_NhanVienQuanLy extends JFrame {

    private final Color SIDEBAR_BG = new Color(245, 250, 255);
    private final Color MAIN_BG = new Color(252, 253, 255);
    private final Color CARD_BORDER = new Color(230, 230, 235);
    private final Color ACCENT = new Color(124, 58, 237);
    private final Color STAT_BG_1 = new Color(245, 230, 255);
    private final Color STAT_BG_2 = new Color(230, 245, 255);
    private final Color STAT_BG_3 = new Color(240, 255, 235);

    // CardLayout để chuyển nội dung bên phải
    private CardLayout cardLayout;
    private JPanel contentPanelContainer;
    // Lưu lại các nút menu để đổi màu khi active
    private JButton btnNhanVien;
    private JButton btnDashboard;

    public GUI_NhanVienQuanLy() {
        setTitle("Dashboard Quản lý khách sạn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);


        cardLayout = new CardLayout();
        contentPanelContainer = new JPanel(cardLayout);
        contentPanelContainer.setBackground(MAIN_BG); // Nền cho vùng nội dung

        // 1. Tạo Sidebar cố định và đặt vào WEST
        add(createSidebar(), BorderLayout.WEST);

        // 2. Tạo Panel chứa CardLayout cho nội dung chính
        cardLayout = new CardLayout();
        contentPanelContainer = new JPanel(cardLayout);
        contentPanelContainer.setBackground(MAIN_BG); // Nền cho vùng nội dung

        // 3. Tạo các Panel nội dung riêng biệt (không chứa sidebar)
        PanelQuanLyContent panelQuanLyContent = new PanelQuanLyContent();
        PanelNhanVienContent panelNhanVienContent = new PanelNhanVienContent();


        // 4. Thêm các Panel nội dung vào CardLayout
        contentPanelContainer.add(panelQuanLyContent, "QUAN_LY_CONTENT");
        contentPanelContainer.add(panelNhanVienContent, "NHAN_VIEN_CONTENT");

        // 5. Thêm Panel CardLayout vào CENTER của JFrame
        add(contentPanelContainer, BorderLayout.CENTER);

        showContentPanel("QUAN_LY_CONTENT");
    }
   
private JPanel createSidebar() {
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
    btnNhanVien = createNavButton("Nhân viên");
    JButton btnThongKe = createNavButton("Thống kê");
    JButton btnKhuyenMai = createNavButton("Khuyến mãi");

    // Gắn ActionListener để chuyển đổi content panel
    btnDashboard.addActionListener(e -> showContentPanel("QUAN_LY_CONTENT"));
    btnNhanVien.addActionListener(e -> showContentPanel("NHAN_VIEN_CONTENT"));
    // (Thêm action listener cho các nút khác nếu bạn tạo panel tương ứng)

    // Thêm nút vào menu
    menu.add(btnDashboard);
    menu.add(Box.createVerticalStrut(8));
    menu.add(btnNhanVien);
    menu.add(Box.createVerticalStrut(8));;
    menu.add(btnThongKe);
    menu.add(Box.createVerticalStrut(8));
    menu.add(btnKhuyenMai);

    sidebar.add(menu, BorderLayout.CENTER);

    JPanel menuWrap = new JPanel(new BorderLayout());
    menuWrap.setOpaque(false);
    menuWrap.setBorder(new EmptyBorder(12, 12, 12, 12));
    menuWrap.add(menu, BorderLayout.NORTH);
    sidebar.add(menuWrap, BorderLayout.CENTER);



    // profile
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
        JButton[] allButtons = {btnDashboard, btnNhanVien /*, các nút khác */};
        for (JButton btn : allButtons) {
            if (btn == activeButton) {
                btn.setForeground(Color.WHITE);
                btn.setBackground(ACCENT_BLUE);
                btn.setOpaque(true); // Chỉ bật Opaque cho nút active
                btn.setBorder(new CompoundBorder(
                        new LineBorder(ACCENT_BLUE, 2, true),
                        new EmptyBorder(6, 12, 6, 12)
                ));
            } else if (btn != null) { // Kiểm tra null phòng trường hợp chưa khởi tạo hết
                btn.setForeground(Color.BLACK);
                btn.setOpaque(false); // Tắt Opaque cho nút không active
                btn.setBorder(new CompoundBorder(
                        new LineBorder(new Color(230, 230, 230)),
                        new EmptyBorder(6, 12, 6, 12)
                ));
            }
        }
    }


    /**
     * Chuyển đổi Panel nội dung hiển thị trong CardLayout
     */
    public void showContentPanel(String panelName) {
        cardLayout.show(contentPanelContainer, panelName);
        // Cập nhật trạng thái active của nút menu tương ứng
        if (panelName.equals("QUAN_LY_CONTENT")) {
            setActiveButton(btnDashboard);
        } else if (panelName.equals("NHAN_VIEN_CONTENT")) {
            setActiveButton(btnNhanVien);
        }
        // (Thêm else if cho các panel khác)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new GUI_NhanVienQuanLy().setVisible(true);
        });
    }
}

class PanelQuanLyContent extends JPanel {
    private final Color STAT_BG_1 = new Color(218, 240, 255);
    private final Color STAT_BG_2 = new Color(230, 235, 255);
    private final Color STAT_BG_3 = new Color(255, 235, 240);

    public PanelQuanLyContent() {
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
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);

        // Zone 1 - Profile + quick stats
        JPanel zone1 = createTopZone();
        zone1.setPreferredSize(new Dimension(Integer.MAX_VALUE, 110));
        content.add(zone1, BorderLayout.NORTH);

        // Middle container (Zone2 + Zone3 stacked and same width as zone1)
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

        // Zone 3: Tasks + stats
        gc.gridy = 1;
        gc.weighty = 0.62;
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setOpaque(false);
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.insets = new Insets(12, 12, 12, 12);
        gc2.fill = GridBagConstraints.BOTH;

        gc2.gridx = 0; gc2.gridy = 0; gc2.weightx = 0.66; gc2.weighty = 1.0;
        bottom.add(createCardWrapper(createTasksPanel()), gc2);

        gc2.gridx = 1; gc2.weightx = 0.34;
        bottom.add(createCardWrapper(createStatsPanel()), gc2);

        middle.add(bottom, gc);
        content.add(middle, BorderLayout.CENTER);

        return content;
    }

    // wrapper để có border/padding giống các card
    private JPanel createCardWrapper(JPanel inner) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(14,14,14,14)));
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    private JPanel createTopZone() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(CARD_BORDER), new EmptyBorder(12,12,12,12)));

        // left profile
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        left.setOpaque(false);
        JLabel avatar = new JLabel("QL");
        avatar.setPreferredSize(new Dimension(64,64));
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
        JPanel wrap = new JPanel(new BorderLayout(8,8));
        wrap.setOpaque(false);
        JLabel title = new JLabel("Lịch làm việc & Cuộc họp tuần này");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        wrap.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        String[][] data = {
                {"Thứ Hai", "15/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "7:55", "17:15", "Hoàn thành"},
                {"Thứ Ba", "16/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "8:00", "17:00", "Hoàn thành"},
                {"Thứ Tư", "17/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "7:58", "", "Đang làm"},
                {"Thứ Năm", "18/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "", "", ""},
                {"Thứ Sáu", "19/1/2024", "Hành chính", "8:00 - 17:00", "9 giờ", "", "", ""}
        };

        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (String[] row : data) {
            JPanel r = new JPanel(new BorderLayout(8,0));
            r.setOpaque(true);
            r.setBackground(Color.WHITE);
            r.setBorder(new CompoundBorder(new EmptyBorder(8,8,8,8), new LineBorder(new Color(241,241,246))));
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            JPanel left = new JPanel();
            left.setOpaque(false);
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            JLabel dn = new JLabel(row[0]);
            dn.setFont(new Font("SansSerif", Font.BOLD, 12));
            JLabel ddate = new JLabel(row[1]);
            ddate.setForeground(Color.GRAY);
            ddate.setFont(new Font("SansSerif", Font.PLAIN, 11));
            left.add(dn); left.add(ddate);
            r.add(left, BorderLayout.WEST);

            JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 12));
            mid.setOpaque(false);
            mid.add(new JLabel(row[2] + "  (" + row[3] + ")"));
            mid.add(new JLabel(row[4]));
            r.add(mid, BorderLayout.CENTER);

            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
            right.setOpaque(false);
            if (!row[5].isEmpty()) right.add(new JLabel("⏱ " + row[5]));
            if (!row[6].isEmpty()) right.add(new JLabel("⏲ " + row[6]));
            if (!row[7].isEmpty()) {
                JLabel st = new JLabel(row[7]);
                st.setOpaque(true);
                st.setBorder(new EmptyBorder(4,8,4,8));
                if ("Hoàn thành".equals(row[7])) {
                    st.setBackground(new Color(220,255,230));
                    st.setForeground(new Color(20,110,40));
                } else {
                    st.setBackground(new Color(230,245,255));
                    st.setForeground(new Color(10,90,180));
                }
                right.add(st);
            }
            r.add(right, BorderLayout.EAST);

            // highlight today
            DayOfWeek rowDay = getDayOfWeek(row[0]);
            if (rowDay == today) {
                r.setBorder(new CompoundBorder(new EmptyBorder(8,8,8,8), new LineBorder(new Color(200,160,255), 2, true)));
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
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel title = new JLabel("Công việc quan trọng hôm nay");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        p.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {
                        {"09:00", "Họp ban giám đốc", "Hoàn thành", "Phòng họp A"},
                        {"10:30", "Duyệt báo cáo doanh thu", "Hoàn thành", ""},
                        {"14:00", "Phỏng vấn ứng viên", "Chưa xong", ""},
                        {"15:30", "Kiểm tra chất lượng dịch vụ", "Chưa xong", ""}
                },
                new String[] {"Thời gian", "Công việc", "Trạng thái", "Ghi chú"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(240);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBorder(new EmptyBorder(6,8,6,8));
                String st = v==null? "": v.toString();
                if ("Hoàn thành".equals(st)) { lbl.setBackground(new Color(220,255,230)); lbl.setForeground(new Color(20,110,40)); }
                else if ("Chưa xong".equals(st)) { lbl.setBackground(new Color(255,245,230)); lbl.setForeground(new Color(170,110,20)); }
                else { lbl.setBackground(new Color(240,240,245)); lbl.setForeground(Color.DARK_GRAY); }
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
        JPanel p = new JPanel(new GridLayout(3,2,12,12));
        p.setOpaque(false);
        p.add(createStatCard("27h", "Tuần này", STAT_BG_1));
        p.add(createStatCard("12", "Cuộc họp", STAT_BG_2));
        p.add(createStatCard("24", "Nhân viên", new Color(230,255,245)));
        p.add(createStatCard("450.000.000đ", "Doanh thu tuần", new Color(230,255,230)));
        p.add(createStatCardWithArrow("2h", "Tăng ca", new Color(255,250,230), true));
        p.add(createStatCard("★", "Đánh giá", new Color(255,245,245)));
        return p;
    }

    private JPanel createStatBox(String big, String small, Color bg) {
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(bg);
        c.setBorder(new LineBorder(CARD_BORDER));
        c.add(new JLabel(big, SwingConstants.CENTER), BorderLayout.CENTER);
        JLabel lb = new JLabel(small, SwingConstants.CENTER);
        lb.setForeground(Color.DARK_GRAY);
        c.add(lb, BorderLayout.SOUTH);
        return c;
    }

    private JPanel createStatCard(String value, String label, Color bg) {
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
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(new LineBorder(CARD_BORDER));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER,6,6));
        top.setOpaque(false);
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel arr = new JLabel(up? "▲" : "▼");
        arr.setForeground(up? new Color(0,140,0) : new Color(200,50,50));
        top.add(v); top.add(arr);
        card.add(top, BorderLayout.CENTER);
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setForeground(Color.DARK_GRAY);
        card.add(l, BorderLayout.SOUTH);
        return card;
    }

    private DayOfWeek getDayOfWeek(String vnName) {
        switch (vnName) {
            case "Thứ Hai": return DayOfWeek.MONDAY;
            case "Thứ Ba": return DayOfWeek.TUESDAY;
            case "Thứ Tư": return DayOfWeek.WEDNESDAY;
            case "Thứ Năm": return DayOfWeek.THURSDAY;
            case "Thứ Sáu": return DayOfWeek.FRIDAY;
            case "Thứ Bảy": return DayOfWeek.SATURDAY;
            case "Chủ Nhật": return DayOfWeek.SUNDAY;
            default: return null;
        }
    }

}



// =================================================================================
// PANEL NỘI DUNG 1: NHÂN VIÊN
// =================================================================================

class PanelNhanVienContent extends JPanel {

    public PanelNhanVienContent() {
        setLayout(new BorderLayout());
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
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
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        content.add(createTopControls(), BorderLayout.NORTH);
        content.add(createCenterArea(), BorderLayout.CENTER);
        content.add(createFooterSummary(), BorderLayout.SOUTH);

        return content;
    }

    // ======== Đầu: tìm + lọc + thông tin=========
    private JPanel createTopControls() {
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
                new EmptyBorder(6, 8, 6, 8)
        ));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JComboBox<String> cbType = new JComboBox<>(new String[]{"Tất cả loại", "Lễ tân", "Quản lý"});
        cbType.setPreferredSize(new Dimension(160, 30));
        searchPanel.add(cbType, BorderLayout.EAST);

        return searchPanel;
    }

    private JPanel createStatCard(String value, String label, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(GUI_NhanVienLeTan.COLOR_WHITE);
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
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);

        center.add(createTableHeader(), BorderLayout.NORTH);
        center.add(createEmployeeScroll(), BorderLayout.CENTER);

        return center;
    }
    private static final double[] COL_WEIGHTS = {
            0.09, // Mã NV
            0.07, // Họ tên
            0.09, // Ngày sinh
            0.10, // Giới tính
            0.05, // Liên hệ
            0.06, // CCCD
            0.08, // Loại nhân viên
            0.08,  // Thao tác
    };
    private JPanel createTableHeader() {
        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setOpaque(false);
        header.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(8, 8, 8, 8))
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 8, 0, 8);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int col = 0;
        addHeaderLabel(header, "Mã NV", col++, 0.08, gbc);
        addHeaderLabel(header, "Họ tên", col++, 0.09, gbc);
        addHeaderLabel(header, "Ngày sinh", col++, 0.07, gbc);
        addHeaderLabel(header, "Giới tính", col++, 0.08, gbc);
        addHeaderLabel(header, "Liên hệ", col++, 0.12, gbc);
        addHeaderLabel(header, "CCCD", col++, 0.10, gbc);
        addHeaderLabel(header, "Loại nhân viên", col++, 0.10, gbc);
        addHeaderLabel(header, "Thao tác", col++, 0.03, gbc);

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
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // sample data - giống ảnh
        List<Employee> employees = sampleEmployees();

        for (Employee e : employees) {
            listPanel.add(createEmployeeRow(e));
            listPanel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scroll.setPreferredSize(new Dimension(0, 420));
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private JPanel createEmployeeRow(Employee e) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230,230,230)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        int col = 0;

        // ===== Mã NV =====
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.setOpaque(false);
        JLabel idLabel = new JLabel(" " + e.code);
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idPanel.add(idLabel, BorderLayout.CENTER);
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(idPanel, gbc);

        // ===== Họ tên =====
        JLabel nameLbl = new JLabel(e.name);
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(nameLbl, gbc);

        // ===== Ngày sinh =====
        JLabel dob = new JLabel(e.dob);
        dob.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dob.setForeground(new Color(100,100,100));
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(dob, gbc);

        // ===== Giới tính =====
        JLabel gender = new JLabel(e.gender);
        gender.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gender.setForeground(new Color(100,100,100));
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(gender, gbc);

        // ===== Liên hệ =====
        JPanel contact = new JPanel(new GridLayout(2, 1, 0, 2));
        contact.setOpaque(false);
        JLabel phone = new JLabel("\uD83D\uDCDE" + e.phone);
        phone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        phone.setForeground(new Color(100,100,100));
        JLabel email = new JLabel("✉" + e.email);
        email.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        email.setForeground(new Color(100,100,100));
        contact.add(phone);
        contact.add(email);
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(contact, gbc);

        // ===== CCCD =====
        JLabel cccd = new JLabel("\uD83D\uDCB3  " + e.cccd);
        cccd.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        cccd.setForeground(new Color(100,100,100));
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(cccd, gbc);

        // ===== Loại nhân viên =====
        JLabel role = new JLabel(e.role, SwingConstants.CENTER);
        role.setFont(new Font("Segoe UI", Font.BOLD, 12));
        role.setOpaque(true);
        role.setBackground(getRoleColor(e.role));
        role.setForeground(Color.WHITE);
        role.setBorder(new EmptyBorder(4, 10, 4, 10));
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(role, gbc);

        // ===== Thao tác =====
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton edit = new JButton("\u270E");
        JButton del = new JButton("\uD83D\uDDD1");
        for (JButton b : new JButton[]{edit, del}) {
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        }
        edit.setForeground(new Color(15,118,255));
        del.setForeground(Color.RED);
        actions.add(edit);
        actions.add(del);
        gbc.gridx = col; gbc.weightx = COL_WEIGHTS[col++];
        row.add(actions, gbc);

        return row;
    }

    private Color getRoleColor(String role) {
        return switch (role) {
            case "Lễ tân" -> new Color(99, 132, 244);
            case "Quản lý" -> new Color(186, 85, 211);
            default -> new Color(180, 180, 180);
        };
    }


    // ======== FOOTER: tổng kết ========
    private JPanel createFooterSummary() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(true);
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

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

    // ======== mau ========
    private List<Employee> sampleEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("NV001","Nguyễn Văn An","15/5/1995","Nam","0901234567","an.nguyen@tbqtt.com","001095012345","Lễ tân"));
        list.add(new Employee("NV002","Trần Thị Bình","20/8/1992","Nữ","0901234568","binh.tran@tbqtt.com","001092054321","Lễ tân"));
        list.add(new Employee("NV003","Lê Văn Cường","10/3/1990","Nam","0901234569","cuong.le@tbqtt.com","001090067890","Quản lý"));
        list.add(new Employee("NV004","Phạm Thị Dung","5/12/1993","Nữ","0901234570","dung.pham@tbqtt.com","001093098765","Quản lý"));
        list.add(new Employee("NV005","Võ Minh Tuấn","22/7/1994","Nam","0901234571","tuan.vo@tbqtt.com","001094123456","Lễ tân"));
        list.add(new Employee("NV006","Hoàng Thị Mai","18/11/1991","Nữ","0901234572","mai.hoang@tbqtt.com","001091789012","Lễ tân"));
        return list;
    }




    private static class Employee {
        String code, name, dob, gender, phone, email, cccd, role;
        Employee(String code, String name, String dob, String gender, String phone, String email, String cccd, String role) {
            this.code = code; this.name = name; this.dob = dob; this.gender = gender;
            this.phone = phone; this.email = email; this.cccd = cccd; this.role = role;
        }
    }

}
