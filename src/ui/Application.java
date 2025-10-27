package ui;

import javax.swing.*;
import ui.form.FormDangNhap;
import ui.gui.GUI_NhanVienLeTan;
import ui.gui.GUI_NhanVienQuanLy;

public class Application {
    public static String currentLoggedInUser = null;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("Starting application..."); // Debug
            FormDangNhap login = new FormDangNhap();
            login.setVisible(true);

            new Thread(() -> {
                while (login.isVisible()) {
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }

                currentLoggedInUser = login.getMaNV();
                String chucVu = login.getChucVu();

                if (currentLoggedInUser != null && chucVu != null) {
                    SwingUtilities.invokeLater(() -> {
                        if ("Quản lý".equals(chucVu)) {
                            new GUI_NhanVienQuanLy(currentLoggedInUser).setVisible(true);
                        } else if ("Lễ tân".equals(chucVu)) {
                            new GUI_NhanVienLeTan(currentLoggedInUser).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "Chức vụ không hợp lệ: " + chucVu,
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                            System.exit(1);
                        }
                    });
                } else {
                    System.exit(0);
                }
            }).start();
        });
    }
}