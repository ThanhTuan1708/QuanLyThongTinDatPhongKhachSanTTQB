/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Dich Vu
 */

package entity;

public class DichVu {
    private String maDV;
    private String tenDV;
    private double donGia;
    private String moTa;
    private boolean trangThai;

    public DichVu() {}

    public DichVu(String maDV, String tenDV, double donGia, String moTa, boolean trangThai) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public String getMaDV() { return maDV; }
    public void setMaDV(String maDV) { this.maDV = maDV; }

    public String getTenDV() { return tenDV; }
    public void setTenDV(String tenDV) { this.tenDV = tenDV; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return "DichVu{maDV=" + maDV + ", tenDV=" + tenDV + ", donGia=" + donGia + "}";
    }
}