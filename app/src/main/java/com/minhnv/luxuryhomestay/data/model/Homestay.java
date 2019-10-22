
package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Homestay implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("hastag")
    @Expose
    private String hastag;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("evalute")
    @Expose
    private String evalute;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("idhomestay")
    @Expose
    private String idhomestay;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHastag() {
        return hastag;
    }

    public void setHastag(String hastag) {
        this.hastag = hastag;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getEvalute() {
        return evalute;
    }

    public void setEvalute(String evalute) {
        this.evalute = evalute;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIdhomestay() {
        return idhomestay;
    }

    public void setIdhomestay(String idhomestay) {
        this.idhomestay = idhomestay;
    }

}
