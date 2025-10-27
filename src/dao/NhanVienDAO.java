package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import entity.NhanVien;
import connectDB.ConnectDB;

public class NhanVienDAO {
    private Connection getConnection() throws SQLException {
        return ConnectDB.getInstance().getConnection();
    }

    private NhanVien createNhanVienFromResultSet(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();
        nv.setMaNV(rs.getString("maNV"));
        nv.setTenNV(rs.getString("hoTen")); // mapping hoTen in DB to tenNV in entity
        nv.setSoDT(rs.getString("sdt")); // mapping sdt in DB to soDT in entity
        nv.setEmail(rs.getString("email"));
        nv.setDiaChi(rs.getString("diaChi"));
        nv.setCCCD(rs.getString("cccd"));
        java.sql.Date sqlDate = rs.getDate("ngaySinh");
        if (sqlDate != null) {
            nv.setNgaySinh(sqlDate.toLocalDate());
        }
        nv.setGioiTinh(rs.getString("gioiTinh").equalsIgnoreCase("Nam"));
        nv.setChucVu(rs.getString("maLoaiNV").equals("1") ? "Quản lý" : "Lễ tân"); // mapping loaiNV
        nv.setMatKhau(rs.getString("matKhau"));
        return nv;
    }

    public NhanVien getNhanVienByMaNV(String maNV) {
        String sql = "SELECT nv.*, lnv.tenLoaiNV FROM NhanVien nv " +
                    "JOIN LoaiNhanVien lnv ON nv.maLoaiNV = lnv.maLoaiNV " +
                    "WHERE maNV = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createNhanVienFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public NhanVien checkDangNhap(String maNV, String matKhau) {
        String sql = "SELECT nv.*, lnv.tenLoaiNV FROM NhanVien nv " +
                    "JOIN LoaiNhanVien lnv ON nv.maLoaiNV = lnv.maLoaiNV " +
                    "WHERE maNV = ? AND matKhau = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.setString(2, matKhau);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createNhanVienFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}