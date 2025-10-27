/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Phong
 */

package entity;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String loaiPhong;
    private double giaTienMotDem;
    private String moTa;
    private boolean trangThai;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String loaiPhong, double giaTienMotDem, String moTa, boolean trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.giaTienMotDem = giaTienMotDem;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public double getGiaTienMotDem() {
        return giaTienMotDem;
    }

    public void setGiaTienMotDem(double giaTienMotDem) {
        this.giaTienMotDem = giaTienMotDem;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Phong [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", loaiPhong=" + loaiPhong + ", giaTienMotDem="
                + giaTienMotDem + ", moTa=" + moTa + ", trangThai=" + trangThai + "]";
    }
}