/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Hoa Don
 */

package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime ngayLap;
    private float VAT;
    private String hinhThucThanhToan;
    private double tongTien;
    private String maKH;
    private String maKhuyenMai;
    private List<ChiTietHoaDon> chiTiet;

    public HoaDon() {
        this.chiTiet = new ArrayList<>();
        this.ngayLap = LocalDateTime.now();
    }

    public HoaDon(String maHoaDon, LocalDateTime ngayLap, float VAT, 
                String hinhThucThanhToan, double tongTien, String maKH, String maKhuyenMai) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.VAT = VAT;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tongTien = tongTien;
        this.maKH = maKH;
        this.maKhuyenMai = maKhuyenMai;
        this.chiTiet = new ArrayList<>();
    }

    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }

    public float getVAT() { return VAT; }
    public void setVAT(float VAT) { this.VAT = VAT; }

    public String getHinhThucThanhToan() { return hinhThucThanhToan; }
    public void setHinhThucThanhToan(String hinhThucThanhToan) { this.hinhThucThanhToan = hinhThucThanhToan; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }

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
        return "HoaDon{maHoaDon=" + maHoaDon + ", ngayLap=" + ngayLap + ", VAT=" + VAT + ", hinhThucThanhToan=" + hinhThucThanhToan 
                + ", tongTien=" + tongTien + ", maKH=" + maKH + ", maKhuyenMai=" + maKhuyenMai + "}";
    }
}
