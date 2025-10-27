package ui.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import dao.DichVuDAO;
import entity.DichVu;

class PanelDichVuContent extends JPanel {
    private DichVuDAO dichVuDAO;
    private List<DichVu> danhSachDichVu;

    public PanelDichVuContent() {
        // Initialize DAO
        dichVuDAO = new DichVuDAO();

        // Load data
        loadDanhSachDichVu();

        // Setup UI
        setLayout(new BorderLayout(15, 15));
        setBackground(GUI_NhanVienLeTan.MAIN_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private void loadDanhSachDichVu() {
        try {
            danhSachDichVu = dichVuDAO.getAll();
            refreshUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách dịch vụ: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshUI() {
        // Clear and rebuild the UI
        removeAll();
        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel title = new JLabel("Quản lý Dịch vụ");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        // Add Service button
        JButton btnAdd = new JButton("+ Thêm dịch vụ");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(GUI_NhanVienLeTan.ACCENT_BLUE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(new EmptyBorder(8, 15, 8, 15));

        btnAdd.addActionListener(e -> showAddServiceDialog());

        header.add(btnAdd, BorderLayout.EAST);

        return header;
    }

    private void showAddServiceDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Dịch vụ", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Tên dịch vụ:"));
        JTextField tenDichVu = new JTextField();
        form.add(tenDichVu);

        form.add(new JLabel("Đơn giá:"));
        JTextField donGia = new JTextField();
        form.add(donGia);

        form.add(new JLabel("Mô tả:"));
        JTextField moTa = new JTextField();
        form.add(moTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Lưu");
        JButton cancelBtn = new JButton("Hủy");

        saveBtn.addActionListener(e -> {
            try {
                DichVu dichVu = new DichVu();
                dichVu.setTenDichVu(tenDichVu.getText());
                dichVu.setGia(Double.parseDouble(donGia.getText()));
                dichVu.setMoTa(moTa.getText());

                if (dichVuDAO.addDichVu(dichVu)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm dịch vụ thành công!");
                    loadDanhSachDichVu();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không thể thêm dịch vụ!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Đơn giá không hợp lệ!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // Service list
        content.add(createServiceListPanel());
        content.add(Box.createVerticalStrut(20));

        // Service categories 
        content.add(createServiceCategoryPanel());

        return content;
    }

    private JScrollPane createServiceListPanel() {
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (danhSachDichVu != null) {
            for (DichVu dv : danhSachDichVu) {
                list.add(createServiceCard(dv));
                list.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(GUI_NhanVienLeTan.MAIN_BG);
        scroll.setPreferredSize(new Dimension(0, 400));
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        return scroll;
    }

    private JPanel createServiceCard(DichVu dichVu) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(GUI_NhanVienLeTan.CARD_BORDER), 
                                        new EmptyBorder(10, 10, 10, 10)));

        // Service info panel
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        // Name
        JLabel title = new JLabel(dichVu.getTenDichVu());
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        info.add(title);

        // Price
        JLabel priceLabel = new JLabel(String.format("%,.0f đ", dichVu.getGia()));
        priceLabel.setForeground(new Color(0, 128, 0));
        info.add(priceLabel);

        // Description
        if (dichVu.getMoTa() != null && !dichVu.getMoTa().isEmpty()) {
            JLabel descLabel = new JLabel("<html><i>" + dichVu.getMoTa() + "</i></html>");
            descLabel.setForeground(GUI_NhanVienLeTan.COLOR_TEXT_MUTED);
            info.add(descLabel);
        }

        // Action buttons panel
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);

        // Edit button
        JButton editBtn = createIconButton("✎", Color.BLUE);
        editBtn.addActionListener(e -> showEditServiceDialog(dichVu));
        actions.add(editBtn);

        // Delete button
        JButton deleteBtn = createIconButton("🗑", Color.RED);
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa dịch vụ này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (dichVuDAO.deleteDichVu(dichVu.getMaDichVu())) {
                        JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                        loadDanhSachDichVu();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa dịch vụ!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Lỗi khi xóa dịch vụ: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        actions.add(deleteBtn);

        card.add(info, BorderLayout.CENTER);
        card.add(actions, BorderLayout.EAST);
        return card;
    }

    private void showEditServiceDialog(DichVu dichVu) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Dịch vụ", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Tên dịch vụ:"));
        JTextField tenDichVu = new JTextField(dichVu.getTenDichVu());
        form.add(tenDichVu);

        form.add(new JLabel("Đơn giá:"));
        JTextField donGia = new JTextField(String.format("%.0f", dichVu.getGia()));
        form.add(donGia);

        form.add(new JLabel("Mô tả:"));
        JTextField moTa = new JTextField(dichVu.getMoTa());
        form.add(moTa);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Lưu");
        JButton cancelBtn = new JButton("Hủy");

        saveBtn.addActionListener(e -> {
            try {
                DichVu updatedDichVu = new DichVu();
                updatedDichVu.setMaDichVu(dichVu.getMaDichVu());
                updatedDichVu.setTenDichVu(tenDichVu.getText());
                updatedDichVu.setGia(Double.parseDouble(donGia.getText())); 
                updatedDichVu.setMoTa(moTa.getText());

                if (dichVuDAO.updateDichVu(updatedDichVu)) {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật dịch vụ thành công!");
                    loadDanhSachDichVu();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không thể cập nhật dịch vụ!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Đơn giá không hợp lệ!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

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

    private JPanel createServiceCategoryPanel() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Title
        JLabel title = new JLabel("Danh mục dịch vụ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Service categories in grid
        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (danhSachDichVu != null) {
            // Group services by category and show summaries
            addServiceCategoryCard(grid, "Dịch vụ phòng", new Color(255, 230, 230));
            addServiceCategoryCard(grid, "Dịch vụ ăn uống", new Color(230, 255, 230));
            addServiceCategoryCard(grid, "Dịch vụ giải trí", new Color(230, 240, 255));
            addServiceCategoryCard(grid, "Dịch vụ đưa đón", new Color(255, 245, 230));
            addServiceCategoryCard(grid, "Dịch vụ khác", new Color(240, 240, 255));
        }

        wrapper.add(title);
        wrapper.add(Box.createVerticalStrut(10));
        wrapper.add(grid);

        return wrapper;
    }

    private void addServiceCategoryCard(JPanel grid, String category, Color bg) {
        // Count services and total price for this category
        double totalPrice = 0;
        int count = 0;

        if (danhSachDichVu != null) {
            for (DichVu dv : danhSachDichVu) {
                // Simple categorization based on service name
                if (dv.getTenDichVu().toLowerCase().contains(category.toLowerCase())) {
                    totalPrice += dv.getGia();
                    count++;
                }
            }
        }

        if (count > 0) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(bg);
            card.setBorder(new CompoundBorder(
                    new LineBorder(GUI_NhanVienLeTan.CARD_BORDER),
                    new EmptyBorder(10, 10, 10, 10)));

            JLabel nameLabel = new JLabel(category + " (" + count + ")");
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            JLabel priceLabel = new JLabel(String.format("TB: %,.0f đ", totalPrice/count));
            priceLabel.setForeground(Color.DARK_GRAY);

            card.add(nameLabel, BorderLayout.CENTER);
            card.add(priceLabel, BorderLayout.SOUTH);
            grid.add(card);
        }
    }
}