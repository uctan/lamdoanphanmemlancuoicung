package com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham;

public class ReviewItem {

    private String fullName; // Thêm trường này
    private float rating;
    private String review;

    public ReviewItem(float rating, String review, String fullName) {
        this.rating = rating;
        this.review = review;
        this.fullName = fullName;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

