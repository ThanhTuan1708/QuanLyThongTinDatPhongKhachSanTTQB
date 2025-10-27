package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;
import entity.DichVu;

public class DichVuDAO {
    private Connection getConnection() throws SQLException {
        return ConnectDB.getInstance().getConnection();
    }
    
    private DichVu createDichVuFromResultSet(ResultSet rs) throws SQLException {
        DichVu dv = new DichVu();
        dv.setMaDichVu(rs.getString("maDichVu"));
        dv.setTenDichVu(rs.getString("tenDichVu"));
        dv.setGia(rs.getDouble("gia"));
        dv.setMoTa(rs.getString("moTa"));
        return dv;
    }

    public List<DichVu> getAll() {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM DichVu";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(createDichVuFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public DichVu getDichVuById(String maDV) {
        String sql = "SELECT * FROM DichVu WHERE maDichVu = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createDichVuFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addDichVu(DichVu dv) {
        String sql = "INSERT INTO DichVu(maDichVu, tenDichVu, gia, moTa) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dv.getMaDichVu());
            stmt.setString(2, dv.getTenDichVu());
            stmt.setDouble(3, dv.getGia());
            stmt.setString(4, dv.getMoTa());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateDichVu(DichVu dv) {
        String sql = "UPDATE DichVu SET tenDichVu = ?, gia = ?, moTa = ? WHERE maDichVu = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dv.getTenDichVu());
            stmt.setDouble(2, dv.getGia());
            stmt.setString(3, dv.getMoTa());
            stmt.setString(4, dv.getMaDichVu());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DichVu> getDichVuByGiaRange(double giaMin, double giaMax) {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM DichVu WHERE gia BETWEEN ? AND ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, giaMin);
            stmt.setDouble(2, giaMax);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(createDichVuFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteDichVu(String maDV) {
        String sql = "DELETE FROM DichVu WHERE maDichVu = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}