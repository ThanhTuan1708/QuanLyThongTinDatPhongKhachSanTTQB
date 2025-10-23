package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String maHD;
    private String maDatPhong; 
    private String maNV;       
    private LocalDateTime ngayLap;
    private double tongTien;
    private boolean thanhToan;
    private List<ChiTietHoaDon> chiTiet;

    public HoaDon() {
        this.chiTiet = new ArrayList<>();
        this.ngayLap = LocalDateTime.now();
    }

    public HoaDon(String maHD, String maDatPhong, String maNV, LocalDateTime ngayLap, boolean thanhToan) {
        this.maHD = maHD;
        this.maDatPhong = maDatPhong;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.thanhToan = thanhToan;
        this.chiTiet = new ArrayList<>();
        recalcTongTien();
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(String maDatPhong) { this.maDatPhong = maDatPhong; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }

    public double getTongTien() { return tongTien; }
    public boolean isThanhToan() { return thanhToan; }
    public void setThanhToan(boolean thanhToan) { this.thanhToan = thanhToan; }

    public List<ChiTietHoaDon> getChiTiet() { return chiTiet; }

    public void addChiTiet(ChiTietHoaDon ct) {
        if (ct == null) return;
        this.chiTiet.add(ct);
        recalcTongTien();
    }

    public void removeChiTiet(ChiTietHoaDon ct) {
        if (ct == null) return;
        this.chiTiet.remove(ct);
        recalcTongTien();
    }

    public void recalcTongTien() {
        this.tongTien = 0;
        for (ChiTietHoaDon ct : chiTiet) {
            this.tongTien += ct.getThanhTien();
        }
    }

    @Override
    public String toString() {
        return "HoaDon{maHD=" + maHD + ", ngayLap=" + ngayLap + ", tongTien=" + tongTien + ", thanhToan=" + thanhToan + "}";
    }
}
