package ui.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public GUI_NhanVienQuanLy() {
        setTitle("Dashboard Quản lý khách sạn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(MAIN_BG);
        main.setBorder(new EmptyBorder(16, 16, 16, 16));
        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createContentPanel(), BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

   
private JPanel createSidebar() {
    JPanel p = new JPanel(new BorderLayout());
    p.setPreferredSize(new Dimension(240, 0));
    p.setBackground(SIDEBAR_BG);

    // logo
    JPanel top = new JPanel();
    top.setOpaque(false);
    top.setBorder(new EmptyBorder(18, 18, 18, 18));
    top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
    JLabel logo = new JLabel("<html><b>TBQTT</b> <small>HOTEL</small></html>");
    logo.setFont(new Font("SansSerif", Font.BOLD, 18));
    top.add(logo);
    top.add(Box.createVerticalStrut(8));
    top.add(new JLabel("Quản lý khách sạn"));
    p.add(top, BorderLayout.NORTH);

    // menu (Dashboard, Nhân viên, Thống kê, Khuyến Mãi)
    JPanel menu = new JPanel();
    menu.setOpaque(false);
    menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
    String[] items = {"Dashboard", "Nhân viên", "Thống kê", "Khuyến Mãi"};
    for (int i = 0; i < items.length; i++) {
        JButton b = new JButton(items[i]);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorder(new CompoundBorder(new LineBorder(new Color(230,230,230)), new EmptyBorder(6,12,6,12)));
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        if (i == 0) {
            b.setForeground(ACCENT);
            b.setBorder(new CompoundBorder(new LineBorder(new Color(230,210,255), 2, true), new EmptyBorder(6,12,6,12)));
        }
        // TODO: add action listener to switch cards, e.g.
        // b.addActionListener(e -> cardLayout.show(contentPanelContainer, items[i].toUpperCase().replace(" ", "_")));
        menu.add(b);
        menu.add(Box.createVerticalStrut(8));
    }
    JPanel menuWrap = new JPanel(new BorderLayout());
    menuWrap.setOpaque(false);
    menuWrap.setBorder(new EmptyBorder(12, 12, 12, 12));
    menuWrap.add(menu, BorderLayout.NORTH);
    p.add(menuWrap, BorderLayout.CENTER);

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
    p.add(bottom, BorderLayout.SOUTH);

    return p;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new GUI_NhanVienQuanLy().setVisible(true);
        });
    }
}
