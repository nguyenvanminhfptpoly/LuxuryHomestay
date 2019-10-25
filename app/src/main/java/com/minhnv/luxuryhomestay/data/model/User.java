package com.minhnv.luxuryhomestay.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {

    @Expose
    @SerializedName("userId")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @Expose
    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    @Expose
    @SerializedName("phoneNumber")
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @Expose
    @SerializedName("userName")
    @ColumnInfo(name = "userName")
    private String userName;

    public User(String password, String phoneNumber, String userName) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
