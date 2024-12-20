package vn.edu.stu.oss_appdatvexemphim.Models;

import java.util.Calendar;

public class User {
    private int ma;
    private String hoTen;
    private Calendar ngaySinh;
    private Boolean gioiTinh;
    private String thanhPho;
    private String sdt;

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Calendar getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Calendar ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public User(int ma, String hoTen, Calendar ngaySinh, Boolean gioiTinh, String thanhPho, String sdt) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.thanhPho = thanhPho;
        this.sdt = sdt;
    }

    public User(String hoTen, String sdt) {
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public User() {
    }
}
