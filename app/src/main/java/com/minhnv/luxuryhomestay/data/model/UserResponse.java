package com.minhnv.luxuryhomestay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.subjects.BehaviorSubject;

public class UserResponse {

    public static class ServerSignUpRequest {
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("phonenumber")
        private String phonenumber;
        @Expose
        @SerializedName("address")
        private String address;

        public ServerSignUpRequest(String password, String phonenumber, String address) {
            this.password = password;
            this.phonenumber = phonenumber;
            this.address = address;
        }
    }

    public static class ServerSignInRequest {
        @Expose
        @SerializedName("phonenumber")
        private String phonenumber;
        @Expose
        @SerializedName("password")
        private String password;

        public ServerSignInRequest(String phonenumber, String password) {
            this.phonenumber = phonenumber;
            this.password = password;
        }
    }

    public static class ServerListHomeStays {
        @Expose
        @SerializedName("idhomestay")
        private Integer idhomestay;

        public ServerListHomeStays(Integer idhomestay) {
            this.idhomestay = idhomestay;
        }
    }

    public static class ServerListHomeStaysRating {
        @Expose
        @SerializedName("rating")
        private Integer rating;

        public ServerListHomeStaysRating(Integer rating) {
            this.rating = rating;
        }
    }

    public static class ServerSearchHomeStaysFollowRating {
        @Expose
        @SerializedName("address")
        private String rating;
        @Expose
        @SerializedName("rating")
        private String rating2;
        @Expose
        @SerializedName("price")
        private String priceAgo;

        public ServerSearchHomeStaysFollowRating(String rating, String rating2, String priceAgo) {
            this.rating = rating;
            this.rating2 = rating2;
            this.priceAgo = priceAgo;
        }


    }

    public static class ServerBooking {
        @SerializedName("datestart")
        private String dateStart;
        @SerializedName("dateend")
        private String dateEnd;
        @SerializedName("countmember")
        private String countMember;
        @SerializedName("namehomestay")
        private String nameHomeStay;
        @SerializedName("address")
        private String address;
        @SerializedName("iduser")
        private int idUser;

        public ServerBooking(String dateStart, String dateEnd, String countMember, String nameHomeStay, String address,int idUser) {
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.countMember = countMember;
            this.nameHomeStay = nameHomeStay;
            this.address = address;
            this.idUser = idUser;
        }
    }

    public static class ServerDeleteBooking{
        @SerializedName("id")
        private Integer id;

        public ServerDeleteBooking(Integer id) {
            this.id = id;
        }
    }

    public static class ServerPostLuxury{
        @SerializedName("image")
        private String image;
        @SerializedName("detail")
        private String detail;
        @SerializedName("username")
        private String username;
        @SerializedName("address")
        private String address;

        public ServerPostLuxury(String image, String detail, String username, String address) {
            this.image = image;
            this.detail = detail;
            this.username = username;
            this.address = address;
        }
    }

    public static class ServerAddFavorite{
        @SerializedName("namehomestay")
        private String nameHomeStay;
        @SerializedName("image")
        private String image;
        @SerializedName("address")
        private String address;
        @SerializedName("iduser")
        private int idUser;

        public ServerAddFavorite(String nameHomeStay, String image, String address,int idUser) {
            this.nameHomeStay = nameHomeStay;
            this.image = image;
            this.address = address;
            this.idUser = idUser;
        }
    }

    public static class ServerAddLove{
        @SerializedName("id")
        private Integer id;
        @SerializedName("countlove")
        private Integer countLove;

        public ServerAddLove(Integer id, Integer countLove) {
            this.id = id;
            this.countLove = countLove;
        }
    }

    public static class ServerLoadHomeStayVinHomes{
        @SerializedName("idvinhomes")
        private Integer id;

        public ServerLoadHomeStayVinHomes(Integer id) {
            this.id = id;
        }
    }

    public static class ServerGetImageDetail{
        @SerializedName("idhomestay")
        private int id;

        public ServerGetImageDetail(int id) {
            this.id = id;
        }
    }

    public static class ServerGetUser{
        @SerializedName("phonenumber")
        private int phonenumber;

        public ServerGetUser(int phonenumber) {
            this.phonenumber = phonenumber;
        }
    }

    public static class ServerListBooking{
        @SerializedName("iduser")
        private int idUser;

        public ServerListBooking(int idUser) {
            this.idUser = idUser;
        }
    }

}
