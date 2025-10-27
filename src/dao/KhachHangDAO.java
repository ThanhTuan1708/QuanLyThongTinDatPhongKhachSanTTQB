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
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "SELECT maKH, tenKH, CCCD, soDT, email, diaChi, gioiTinh, quocTich FROM KhachHang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                kh.setCCCD(rs.getString("CCCD"));
                kh.setSoDT(rs.getString("soDT")); 
                kh.setEmail(rs.getString("email"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setGioiTinh(rs.getBoolean("gioiTinh"));
                kh.setQuocTich(rs.getString("quocTich"));
                list.add(kh);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    public boolean deleteKhachHang(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection conn = ConnectDB.getInstance().getConnection();
            
            String sql = "DELETE FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            
            int n = stmt.executeUpdate();
            return n > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}