
package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListVinHomes implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("createroom")
    @Expose
    private String createroom;
    @SerializedName("idvinhomes")
    @Expose
    private String idvinhomes;
    @SerializedName("detail")
    @Expose
    private String detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateroom() {
        return createroom;
    }

    public void setCreateroom(String createroom) {
        this.createroom = createroom;
    }

    public String getIdvinhomes() {
        return idvinhomes;
    }

    public void setIdvinhomes(String idvinhomes) {
        this.idvinhomes = idvinhomes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
