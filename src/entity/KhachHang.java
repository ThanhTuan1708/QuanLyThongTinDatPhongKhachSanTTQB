/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Khach Hang
 */

package entity;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String soDT;
    private String email;
    private String diaChi;
    private String CCCD;
    private boolean gioiTinh;
    private String quocTich;

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String soDT, String email, String diaChi, String cCCD, boolean gioiTinh,
            String quocTich) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.email = email;
        this.diaChi = diaChi;
        this.CCCD = cCCD;
        this.gioiTinh = gioiTinh;
        this.quocTich = quocTich;
    }

    // Getters and Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String cCCD) {
        CCCD = cCCD;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    @Override
    public String toString() {
        return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDT=" + soDT + ", email=" + email + ", diaChi="
                + diaChi + ", CCCD=" + CCCD + ", gioiTinh=" + gioiTinh + ", quocTich=" + quocTich + "]";
    }


}