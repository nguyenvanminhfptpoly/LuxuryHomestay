package com.minhnv.luxuryhomestay.data.remote;



class ApiEndPoint {
    private static final String BASE_URL = "https://luxuryhomestay.000webhostapp.com/";
    static final String ENDPOINT_SIGNUP = BASE_URL +  "signup.php";
    static final String ENDPOINT_SIGN_IN = BASE_URL + "login.php";
    static final String ENDPOINT_LIST_HOMESTAYS = BASE_URL + "getlist.php";
    static final String ENDPOINT_LIST_CITY = BASE_URL + "getcity.php";
    static final String ENDPOINT_HOMESTAY_RATING  = BASE_URL + "getlistrating.php";
    static final String ENDPOINT_HOMESTAY_PRICE_ASC = BASE_URL + "getlisthomestayprice.php";
    static final String ENDPOINT_SEARCH_HOMESTAY = BASE_URL + "searchhomestay.php";
    static final String ENDPOINT_BOOKING = BASE_URL + "booking.php";
    static final String ENDPOINT_LIST_BOOKING = BASE_URL + "getlistbooking.php";
    static final String ENDPOINT_DELETE_BOOKING = BASE_URL + "deletebooking.php";
    static final String ENDPOINT_LIST_LUXURY = BASE_URL + "luxurylist.php";
    static final String ENDPOINT_LIST_FAVORITE = BASE_URL + "getfavorite.php";
    static final String ENDPOINT_POST_FAVORITE = BASE_URL + "addfovorite.php";
    static final String ENDPOINT_DELETE_fAVORITE = BASE_URL + "deletefavorite.php";
    static final String ENDPOINT_ADD_LOVE_LUXURY = BASE_URL + "addlovefavorite.php";
    static final String ENDPOINT_UPLOAD_IMAGE = BASE_URL + "uploadimage.php";
    static final String ENDPOINT_POST_LUXURY = BASE_URL + "luxurypost.php";

}
