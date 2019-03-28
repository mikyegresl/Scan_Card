package com.example.gamezale;

public class ContactDetail {

    private int imageURL;
    private String infoType;
    private String infoValue;

    public ContactDetail(int imageURL, String infoType, String infoValue) {
        this.imageURL = imageURL;
        this.infoType = infoType;
        this.infoValue = infoValue;
    }

    public int getImageURL() {
        return imageURL;
    }

    public String getInfoType() {
        return infoType;
    }

    public String getInfoValue() {
        return infoValue;
    }

    public void setImageURL(int imageURL) {
        this.imageURL = imageURL;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }
}
