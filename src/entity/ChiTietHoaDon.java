/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Chi tiet hoa don
 */

package entity;

public class ChiTietHoaDon {
    private String maCT;
    private DichVu dichVu;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String maCT, DichVu dichVu, int soLuong, double donGia) {
        this.maCT = maCT;
        this.dichVu = dichVu;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    public String getMaCT() { return maCT; }
    public void setMaCT(String maCT) { this.maCT = maCT; }

    public DichVu getDichVu() { return dichVu; }
    public void setDichVu(DichVu dichVu) { this.dichVu = dichVu; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        recalc();
    }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) {
        this.donGia = donGia;
        recalc();
    }

    public double getThanhTien() { return thanhTien; }

    private void recalc() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{maCT=" + maCT + ", dv=" + (dichVu!=null?dichVu.getTenDV():"null")
                + ", soLuong=" + soLuong + ", thanhTien=" + thanhTien + "}";
    }
}