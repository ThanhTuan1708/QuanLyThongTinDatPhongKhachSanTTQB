package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhuyenMai;

public class KhuyenMaiDAO {
    public List<KhuyenMai> getValidKhuyenMai() throws SQLException {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM KhuyenMai WHERE GETDATE() BETWEEN ngayBatDau AND ngayKetThuc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getString("maKhuyenMai"),
                    rs.getString("tenKhuyenMai"),
                    rs.getDouble("chietKhau"),
                    rs.getDate("ngayBatDau"),
                    rs.getDate("ngayKetThuc"),
                    rs.getString("moTa")
                );
                dsKhuyenMai.add(km);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return dsKhuyenMai;
    }

    public KhuyenMai getByMaKhuyenMai(String maKhuyenMai) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai = ? AND GETDATE() BETWEEN ngayBatDau AND ngayKetThuc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKhuyenMai);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new KhuyenMai(
                    rs.getString("maKhuyenMai"),
                    rs.getString("tenKhuyenMai"),
                    rs.getDouble("chietKhau"),
                    rs.getDate("ngayBatDau"),
                    rs.getDate("ngayKetThuc"),
                    rs.getString("moTa")
                );
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }
}