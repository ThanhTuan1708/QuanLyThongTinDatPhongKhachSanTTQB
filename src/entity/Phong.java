package entity;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String loaiPhong;
    private double giaPhong;
    private String moTa;
    private boolean trangThai;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String loaiPhong, double giaPhong, String moTa, boolean trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
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

    public double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
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
        return "Phong [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", loaiPhong=" + loaiPhong + ", giaPhong="
                + giaPhong + ", moTa=" + moTa + ", trangThai=" + trangThai + "]";
    }
}