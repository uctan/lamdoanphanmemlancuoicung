package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

public class FootballField {
    private String name;
    private String imageResourceId;

    // Constructor
    public FootballField(String name, String imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}