package com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon;

public class hoadonclass {
    private int idDonHang;
    private String fullname;
    private String userEmail;
    private String khoahoc;
    private  int thanhtoan;
    public hoadonclass() {

    }

    public hoadonclass(int idDonHang, String fullname, String userEmail, String khoahoc,  int thanhtoan) {
        this.idDonHang = idDonHang;
        this.fullname = fullname;
        this.userEmail = userEmail;
        this.khoahoc = khoahoc;
        this.thanhtoan = thanhtoan;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getKhoahoc() {
        return khoahoc;
    }

    public void setKhoahoc(String khoahoc) {
        this.khoahoc = khoahoc;
    }

    public int getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(int thanhtoan) {
        this.thanhtoan = thanhtoan;
    }

}
