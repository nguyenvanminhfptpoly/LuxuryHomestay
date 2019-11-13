package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("id")
    private int id;

    @SerializedName("phonenumber")
    private int phonenumber;

    @SerializedName("password")
    private String password;

    @SerializedName("address")
    private String address;

    public UserInfo(int id, int phonenumber, String password, String address) {
        this.id = id;
        this.phonenumber = phonenumber;
        this.password = password;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
