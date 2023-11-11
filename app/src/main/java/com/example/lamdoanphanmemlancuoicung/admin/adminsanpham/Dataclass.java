package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.FootballField;
public class Dataclass {
    private int productID;
    private String dataTitle;
    private String dataDesc;
    private int dataLang;
    private String dataImage;
    private boolean isFavorite;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Dataclass() {

    }

    public Dataclass(int productID, String dataTitle, String dataDesc, int dataLang, String dataImage, boolean isFavorite) {
        this.productID = productID;
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.isFavorite = isFavorite;
    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    // Getters and Setters
    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public int getDataLang() {
        return dataLang;
    }

    public void setDataLang(int dataLang) {
        this.dataLang = dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}