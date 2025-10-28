package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHangDAO {
    // Các hằng số cho hạng thành viên
    public static final double PLATINUM_THRESHOLD = 50000000;
    public static final double GOLD_THRESHOLD = 30000000;
    public static final double SILVER_THRESHOLD = 15000000;
    public static final double BRONZE_THRESHOLD = 5000000;

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            ConnectDB.getInstance().connect();
            conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT DISTINCT kh.* 
                FROM KhachHang kh 
                LEFT JOIN HoaDon hd ON kh.maKH = hd.maKH 
                ORDER BY hd.ngayLap DESC, kh.maKH
                """;
                
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("hoTen")); // Changed from tenKH to match DB column
                kh.setCCCD(rs.getString("CCCD"));
                kh.setSoDT(rs.getString("sdt")); // Changed from soDT to match DB column
                kh.setEmail(rs.getString("email"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setGioiTinh(rs.getBoolean("gioiTinh"));
                kh.setQuocTich(rs.getString("quocTich"));
                list.add(kh);
            }
            
            System.out.println("Loaded " + list.size() + " customers from database");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error loading customers: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public KhachHang getKhachHangById(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                kh.setCCCD(rs.getString("CCCD"));
                kh.setSoDT(rs.getString("soDT"));
                kh.setEmail(rs.getString("email"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setGioiTinh(rs.getBoolean("gioiTinh"));
                kh.setQuocTich(rs.getString("quocTich"));
                return kh;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "INSERT INTO KhachHang(maKH, tenKH, CCCD, soDT, email, diaChi, gioiTinh, quocTich) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getCCCD());
            stmt.setString(4, kh.getSoDT());
            stmt.setString(5, kh.getEmail());
            stmt.setString(6, kh.getDiaChi());
            stmt.setBoolean(7, kh.isGioiTinh());
            stmt.setString(8, kh.getQuocTich());
            
            int n = stmt.executeUpdate();
            return n > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "UPDATE KhachHang SET tenKH = ?, CCCD = ?, soDT = ?, email = ?, diaChi = ?, gioiTinh = ?, quocTich = ? WHERE maKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getCCCD());
            stmt.setString(3, kh.getSoDT());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getDiaChi());
            stmt.setBoolean(6, kh.isGioiTinh());
            stmt.setString(7, kh.getQuocTich());
            stmt.setString(8, kh.getMaKH());
            
            int n = stmt.executeUpdate();
            return n > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(String sdt) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "DELETE FROM KhachHang WHERE sdt = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sdt);
            
            int n = stmt.executeUpdate();
            return n > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy số lần lưu trú của khách hàng
    public int getSoLanLuuTru(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT COUNT(DISTINCT cthp.maHoaDon) as soLan
                FROM KhachHang kh
                JOIN HoaDon hd ON kh.maKH = hd.maKH
                JOIN ChiTietHoaDon_Phong cthp ON hd.maHoaDon = cthp.maHoaDon
                WHERE kh.maKH = ?
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("soLan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy ngày lưu trú cuối cùng
    public java.sql.Date getLanCuoiLuuTru(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT MAX(cthp.ngayTraPhong) as lanCuoi
                FROM KhachHang kh
                JOIN HoaDon hd ON kh.maKH = hd.maKH
                JOIN ChiTietHoaDon_Phong cthp ON hd.maHoaDon = cthp.maHoaDon
                WHERE kh.maKH = ?
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDate("lanCuoi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tổng chi tiêu của khách hàng
    public double getTongChiTieu(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT SUM(CASE 
                    WHEN km.chietKhau IS NOT NULL 
                    THEN hd.tongTien * (1 - km.chietKhau/100.0)
                    ELSE hd.tongTien 
                END) as tongChiTieu
                FROM KhachHang kh
                JOIN HoaDon hd ON kh.maKH = hd.maKH
                LEFT JOIN KhuyenMai km ON hd.maKhuyenMai = km.maKhuyenMai
                WHERE kh.maKH = ?
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("tongChiTieu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Tính đánh giá trung bình
    public double getDanhGiaTrungBinh(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT AVG(CAST(danhGia as FLOAT)) as trungBinh
                FROM KhachHang kh
                JOIN HoaDon hd ON kh.maKH = hd.maKH
                JOIN DanhGia dg ON hd.maHoaDon = dg.maHoaDon
                WHERE kh.maKH = ?
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("trungBinh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Xác định hạng thành viên dựa trên tổng chi tiêu
    public String getHangThanhVien(double tongChiTieu) {
        if (tongChiTieu >= PLATINUM_THRESHOLD) return "Platinum";
        if (tongChiTieu >= GOLD_THRESHOLD) return "Gold";
        if (tongChiTieu >= SILVER_THRESHOLD) return "Silver";
        if (tongChiTieu >= BRONZE_THRESHOLD) return "Bronze";
        return "Standard";
    }

    // Lấy thống kê tổng quát về khách hàng
    public int[] getThongKeTongQuat() {
        int[] thongKe = new int[5]; // [tong, platinum, gold, silver, bronze]
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                WITH TongChiTieu AS (
                    SELECT 
                        kh.maKH,
                        SUM(CASE 
                            WHEN km.chietKhau IS NOT NULL 
                            THEN hd.tongTien * (1 - km.chietKhau/100.0)
                            ELSE hd.tongTien 
                        END) as tongChiTieu
                    FROM KhachHang kh
                    LEFT JOIN HoaDon hd ON kh.maKH = hd.maKH
                    LEFT JOIN KhuyenMai km ON hd.maKhuyenMai = km.maKhuyenMai
                    GROUP BY kh.maKH
                )
                SELECT 
                    COUNT(*) as tongKH,
                    SUM(CASE WHEN tongChiTieu >= ? THEN 1 ELSE 0 END) as platinum,
                    SUM(CASE WHEN tongChiTieu >= ? AND tongChiTieu < ? THEN 1 ELSE 0 END) as gold,
                    SUM(CASE WHEN tongChiTieu >= ? AND tongChiTieu < ? THEN 1 ELSE 0 END) as silver,
                    SUM(CASE WHEN tongChiTieu >= ? AND tongChiTieu < ? THEN 1 ELSE 0 END) as bronze
                FROM TongChiTieu
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, PLATINUM_THRESHOLD);
            stmt.setDouble(2, GOLD_THRESHOLD);
            stmt.setDouble(3, PLATINUM_THRESHOLD);
            stmt.setDouble(4, SILVER_THRESHOLD);
            stmt.setDouble(5, GOLD_THRESHOLD);
            stmt.setDouble(6, BRONZE_THRESHOLD);
            stmt.setDouble(7, SILVER_THRESHOLD);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                thongKe[0] = rs.getInt("tongKH");
                thongKe[1] = rs.getInt("platinum");
                thongKe[2] = rs.getInt("gold");
                thongKe[3] = rs.getInt("silver");
                thongKe[4] = rs.getInt("bronze");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thongKe;
    }

    // Lấy tổng chi tiêu trung bình của tất cả khách hàng
    public double getTongChiTieuTrungBinh() {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = """
                SELECT AVG(chiTieu) as trungBinh
                FROM (
                    SELECT 
                        kh.maKH,
                        SUM(CASE 
                            WHEN km.chietKhau IS NOT NULL 
                            THEN hd.tongTien * (1 - km.chietKhau/100.0)
                            ELSE hd.tongTien 
                        END) as chiTieu
                    FROM KhachHang kh
                    JOIN HoaDon hd ON kh.maKH = hd.maKH
                    LEFT JOIN KhuyenMai km ON hd.maKhuyenMai = km.maKhuyenMai
                    GROUP BY kh.maKH
                ) ChiTieuKH
                """;
                
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("trungBinh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Lấy đánh giá trung bình của tất cả khách hàng
    public double getDanhGiaTrungBinhTatCa() {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "SELECT AVG(CAST(danhGia as FLOAT)) as trungBinh FROM DanhGia";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("trungBinh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}