/**
 * Author: ThanhTuan
 * Date: 2025-10-23
 * Description: Entity Nhan Vien
 */

package entity;

import java.time.LocalDate;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String soDT;
    private String email;
    private String diaChi;
    private String CCCD;
    private LocalDate ngaySinh;
    private boolean gioiTinh;
    private String chucVu;
    private String matKhau;
    private boolean trangThai;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String soDT, String email, String diaChi, String cCCD, LocalDate ngaySinh,
            boolean gioiTinh, String chucVu, String matKhau, boolean trangThai) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.soDT = soDT;
        this.email = email;
        this.diaChi = diaChi;
        this.CCCD = cCCD;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", soDT=" + soDT + ", email=" + email + ", diaChi="
                + diaChi + ", CCCD=" + CCCD + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", chucVu=" + chucVu
                + ", trangThai=" + trangThai + "]";
    }
}