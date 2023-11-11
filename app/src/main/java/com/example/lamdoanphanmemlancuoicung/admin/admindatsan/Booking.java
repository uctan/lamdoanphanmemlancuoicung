package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

import java.time.LocalTime;
import java.util.Date;

public class Booking {
    private String fieldName;
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;
    private String tensanthue;
    private String tennguoithue;
    public Booking() {
        // Có thể để trống hoặc thêm các khởi tạo mặc định nếu cần
    }
    public Booking(String fieldName, String date, LocalTime startTime, LocalTime endTime, int price, String tensanthue, String tennguoithue) {
        this.fieldName = fieldName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.tensanthue = tensanthue;
        this.tennguoithue = tennguoithue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTensanthue() {
        return tensanthue;
    }

    public void setTensanthue(String tensanthue) {
        this.tensanthue = tensanthue;
    }

    public String getTennguoithue() {
        return tennguoithue;
    }

    public void setTennguoithue(String tennguoithue) {
        this.tennguoithue = tennguoithue;
    }
}

