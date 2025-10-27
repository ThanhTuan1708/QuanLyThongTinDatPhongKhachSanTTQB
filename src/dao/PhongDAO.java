package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Phong;

public class PhongDAO {
    private void executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } finally {
            ConnectDB.closeStatement(stmt);
            ConnectDB.closeConnection(conn);
        }
    }

    private List<Phong> executeQuery(String sql, Object... params) throws SQLException {
        List<Phong> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(extractPhongFromResultSet(rs));
            }
        } finally {
            ConnectDB.closeResultSet(rs);
            ConnectDB.closeStatement(stmt);
            ConnectDB.closeConnection(conn);
        }
        return list;
    }

    private Phong extractPhongFromResultSet(ResultSet rs) throws SQLException {
        Phong p = new Phong();
        p.setMaPhong(rs.getString("maPhong"));
        p.setTenPhong(rs.getString("maPhong")); // Using maPhong as tenPhong
        p.setLoaiPhong(rs.getString("tenLoaiPhong"));
        p.setGiaTienMotDem(rs.getDouble("giaTienMotDem"));
        p.setMoTa(rs.getString("moTa"));
        p.setTrangThai(rs.getString("tenTrangThai").equals("ĐÃ XÁC NHẬN"));
        return p;
    }

    public List<Phong> getAll() throws SQLException {
        String sql = "SELECT p.maPhong, p.giaTienMotDem, p.moTa, lp.tenLoaiPhong, tp.tenTrangThai " +
                    "FROM Phong p " +
                    "JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai";
        return executeQuery(sql);
    }

    public Phong getPhongById(String maPhong) throws SQLException {
        String sql = "SELECT p.maPhong, p.giaTienMotDem, p.moTa, lp.tenLoaiPhong, tp.tenTrangThai " +
                    "FROM Phong p " +
                    "JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai " +
                    "WHERE p.maPhong = ?";
        List<Phong> list = executeQuery(sql, maPhong);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Phong> getPhongTrong() throws SQLException {
        String sql = "SELECT p.maPhong, p.giaTienMotDem, p.moTa, lp.tenLoaiPhong, tp.tenTrangThai " +
                    "FROM Phong p " +
                    "JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai " +
                    "WHERE tp.tenTrangThai = N'CHỜ XỬ LÝ'";
        return executeQuery(sql);
    }

    public List<Phong> getPhongByLoai(String loaiPhong) throws SQLException {
        String sql = "SELECT p.maPhong, p.giaTienMotDem, p.moTa, lp.tenLoaiPhong, tp.tenTrangThai " +
                    "FROM Phong p " +
                    "JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai " +
                    "WHERE lp.tenLoaiPhong = ?";
        return executeQuery(sql, loaiPhong);
    }

    public List<Phong> getPhongByGiaRange(double giaMin, double giaMax) throws SQLException {
        String sql = "SELECT p.maPhong, p.giaTienMotDem, p.moTa, lp.tenLoaiPhong, tp.tenTrangThai " +
                    "FROM Phong p " +
                    "JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                    "JOIN TrangThaiPhong tp ON p.maTrangThai = tp.maTrangThai " +
                    "WHERE p.giaTienMotDem BETWEEN ? AND ?";
        return executeQuery(sql, giaMin, giaMax);
    }

    public boolean updateTrangThaiPhong(String maPhong, boolean trangThai) throws SQLException {
        String sql = "UPDATE Phong SET maTrangThai = " +
                    "(SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai = ?) " +
                    "WHERE maPhong = ?";
        try {
            executeUpdate(sql, trangThai ? "ĐÃ XÁC NHẬN" : "CHỜ XỬ LÝ", maPhong);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePhong(Phong phong) throws SQLException {
        String sql = "UPDATE Phong SET giaTienMotDem = ?, moTa = ?, " +
                    "maLoaiPhong = (SELECT maLoaiPhong FROM LoaiPhong WHERE tenLoaiPhong = ?), " +
                    "maTrangThai = (SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai = ?) " +
                    "WHERE maPhong = ?";
        try {
            executeUpdate(sql, 
                phong.getGiaTienMotDem(),
                phong.getMoTa(),
                phong.getLoaiPhong(),
                phong.isTrangThai() ? "ĐÃ XÁC NHẬN" : "CHỜ XỬ LÝ",
                phong.getMaPhong()
            );
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int resetAllRentedRooms() throws SQLException {
        String sql = "UPDATE Phong SET maTrangThai = " +
                    "(SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai = N'CHỜ XỬ LÝ') " +
                    "WHERE maTrangThai IN (SELECT maTrangThai FROM TrangThaiPhong WHERE tenTrangThai IN (N'ĐÃ XÁC NHẬN', N'ĐÃ ĐẶT'))";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            return stmt.executeUpdate();
        } finally {
            ConnectDB.closeStatement(stmt);
            ConnectDB.closeConnection(conn);
        }
    }
}