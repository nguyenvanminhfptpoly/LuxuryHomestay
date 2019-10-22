package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("datestart")
    @Expose
    private String datestart;
    @SerializedName("dateend")
    @Expose
    private String dateend;
    @SerializedName("countmember")
    @Expose
    private String countmember;
    @SerializedName("namehomestay")
    private String nameHomeStay;
    @SerializedName("address")
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public String getCountmember() {
        return countmember;
    }

    public void setCountmember(String countmember) {
        this.countmember = countmember;
    }

    public String getNameHomeStay() {
        return nameHomeStay;
    }

    public void setNameHomeStay(String nameHomeStay) {
        this.nameHomeStay = nameHomeStay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}