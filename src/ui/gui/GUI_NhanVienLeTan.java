// ---------------------------
// Chú thích metadata (comment)
// Người code: Nguyễn Thanh Tuấn
// Mô tả: Thêm nhãn chú thích hiển thị tên người chịu trách nhiệm / phần giao diện profile với dashboard
// Mục đích: Quản lý code, dễ dàng liên hệ khi cần chỉnh sửa
// Ngày tạo: 16/10/2025
// Giờ tạo: 10:30
// Lưu ý: cập nhật thời gian/ người sửa khi chỉnh sửa tiếp
// ---------------------------
package ui.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GUI_NhanVienLeTan extends JFrame {
    private final Color SIDEBAR_BG = new Color(240, 248, 255);      
    private final Color MAIN_BG = new Color(250, 252, 255);        
    private final Color CARD_BORDER = new Color(230, 230, 235);
    private final Color ACCENT_BLUE = new Color(51, 122, 255);     
    private final Color STAT_BG_1 = new Color(218, 240, 255);
    private final Color STAT_BG_2 = new Color(230, 235, 255);
    private final Color STAT_BG_3 = new Color(255, 235, 240);

    public GUI_NhanVienLeTan() {
        setTitle("Dashboard Nhân viên Lễ tân");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main sections
        add(createSidebar(), BorderLayout.WEST);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(MAIN_BG);
        main.setBorder(new EmptyBorder(18, 18, 18, 18));
        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createContentPanel(), BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
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

        String[] menuItems = {"Dashboard", "Đặt phòng", "Khách hàng", "Phòng", "Dịch vụ"};
        for (int i = 0; i < menuItems.length; i++) {
            JButton btn = new JButton(menuItems[i]);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
            btn.setContentAreaFilled(false);
            
            if (i == 0) {
                btn.setForeground(ACCENT_BLUE);
                btn.setBorder(new CompoundBorder(
                    new LineBorder(new Color(200,220,255), 2, true),
                    new EmptyBorder(6, 12, 6, 12)
                ));
            } else {
                btn.setBorder(new CompoundBorder(
                    new LineBorder(new Color(230,230,230)),
                    new EmptyBorder(6, 12, 6, 12)
                ));
            }
            
            menu.add(btn);
            menu.add(Box.createVerticalStrut(8));
        }
        sidebar.add(menu, BorderLayout.CENTER);

        // Profile section
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
        logout.setForeground(new Color(220,50,50));
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);

        profile.add(user);
        profile.add(role);
        profile.add(Box.createVerticalStrut(10));
        profile.add(logout);
        sidebar.add(profile, BorderLayout.SOUTH);

        return sidebar;
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

    // Zone 1: Profile info and stats
    JPanel topZone = createTopProfileCard();
    topZone.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
    content.add(topZone, BorderLayout.NORTH);
    
    // Zones 2 & 3 container
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setOpaque(false);
    
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(12, 0, 12, 0);
    gc.fill = GridBagConstraints.BOTH;
    gc.gridwidth = 1;
    gc.gridx = 0;
    gc.weightx = 1.0;

    // Zone 2: Schedule
    gc.gridy = 0;
    gc.weighty = 0.4;
    JPanel schedulePanel = createSchedulePanel();
    schedulePanel.setBorder(new CompoundBorder(
        new LineBorder(CARD_BORDER),
        new EmptyBorder(14, 14, 14, 14)
    ));
    schedulePanel.setBackground(Color.WHITE);
    centerPanel.add(schedulePanel, gc);

    // Zone 3: Tasks and Stats container
    gc.gridy = 1;
    gc.weighty = 0.6;
    JPanel bottomSection = new JPanel(new GridBagLayout());
    bottomSection.setOpaque(false);
    
    GridBagConstraints gc2 = new GridBagConstraints();
    gc2.fill = GridBagConstraints.BOTH;
    gc2.insets = new Insets(12, 0, 12, 12); // Adjusted insets
    
    // Tasks panel (left)
    gc2.gridx = 0;
    gc2.gridy = 0;
    gc2.weightx = 0.65;
    gc2.weighty = 1.0;
    bottomSection.add(createTasksPanel(), gc2);

    // Stats panel (right)
    gc2.gridx = 1;
    gc2.weightx = 0.35;
    gc2.insets = new Insets(12, 0, 12, 0); // Remove right padding for last component
    bottomSection.add(createStatsPanel(), gc2);
    
    centerPanel.add(bottomSection, gc);

    content.add(centerPanel, BorderLayout.CENTER);
    return content;
}
    private JPanel createTopProfileCard() {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBorder(new CompoundBorder(
            new LineBorder(CARD_BORDER),
            new EmptyBorder(14, 14, 14, 14)
        ));
        card.setBackground(Color.WHITE);

        // Left: Profile info
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        left.setOpaque(false);
        
        // Avatar
        JLabel avatar = new JLabel("LT");
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(120, 150, 255));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 20));
        left.add(avatar);

        // Info
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

        // Right: Stats
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

        // Schedule grid
        JPanel grid = new JPanel();
        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));
        grid.setOpaque(false);

        String[][] scheduleData = {
            {"Thứ Hai", "15/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 5:55", "Ra: 14:05", "Hoàn thành"},
            {"Thứ Ba", "16/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 6:00", "Ra: 14:00", "Hoàn thành"},
            {"Thứ Tư", "17/1", "Sáng", "6:00 - 14:00", "8 giờ", "Vào: 5:58", "", "Đang làm"},
            {"Thứ Năm", "18/1", "Sáng", "6:00 - 14:00", "8 giờ", "", "", ""},
            {"Thứ Sáu", "19/1", "Sáng", "6:00 - 14:00", "8 giờ", "", "", ""}
        };

        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (String[] row : scheduleData) {
            JPanel dayRow = new JPanel(new BorderLayout(15, 0));
            dayRow.setOpaque(true);
            dayRow.setBackground(Color.WHITE);
            dayRow.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(new Color(240,240,245))
            ));
            dayRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

            // Day column
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

            // Shift info
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

            // Status and time
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
                    new LineBorder(new Color(100,150,255), 2, true)
                ));
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
            new LineBorder(CARD_BORDER),
            new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel title = new JLabel("Nhiệm vụ & Thống kê hôm nay");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        tasks.add(title, BorderLayout.NORTH);

        String[] columns = {"Thời gian", "Nhiệm vụ", "Trạng thái", "Ghi chú"};
        Object[][] data = {
            {"07:30", "Check-in 3 khách mới", "Hoàn thành", "Phòng 101, 102, 103"},
            {"10:00", "Check-out 2 phòng", "Hoàn thành", "Phòng 201, 202"},
            {"11:15", "Xử lý yêu cầu phòng 301", "Hoàn thành", "Yêu cầu thêm khăn"},
            {"13:30", "Chuẩn bị báo cáo ca làm", "Chưa xong", ""},
            {"15:00", "Kiểm tra phòng trống", "Đang làm", ""}
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
        
        // Status column renderer
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
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
        box.setBorder(new LineBorder(CARD_BORDER));

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
        card.setBorder(new LineBorder(CARD_BORDER));

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
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GUI_NhanVienLeTan().setVisible(true);
        });
    }
}