package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("idhomestay")
    @Expose
    private String idhomestay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdhomestay() {
        return idhomestay;
    }

    public void setIdhomestay(String idhomestay) {
        this.idhomestay = idhomestay;
    }

}
