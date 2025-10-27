/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Dich Vu
 */

package entity;

public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private double gia;
    private String moTa;

    public DichVu() {}

    public DichVu(String maDichVu, String tenDichVu, double gia, String moTa) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
        this.moTa = moTa;
    }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return "DichVu{maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", gia=" + gia + "}";
    }
}