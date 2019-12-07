package com.minhnv.luxuryhomestay.data.remote;


import com.minhnv.luxuryhomestay.BuildConfig;

class ApiEndPoint {
    static final String ENDPOINT_SIGNUP = BuildConfig.BASE_URL +  "signup.php";
    static final String ENDPOINT_SIGN_IN =  BuildConfig.BASE_URL + "login.php";
    static final String ENDPOINT_LIST_HOMESTAYS =  BuildConfig.BASE_URL + "getlist.php";
    static final String ENDPOINT_LIST_CITY =  BuildConfig.BASE_URL + "getcity.php";
    static final String ENDPOINT_HOMESTAY_RATING  =  BuildConfig.BASE_URL + "getlistrating.php";
    static final String ENDPOINT_HOMESTAY_PRICE_ASC =  BuildConfig.BASE_URL + "getlisthomestayprice.php";
    static final String ENDPOINT_SEARCH_HOMESTAY =  BuildConfig.BASE_URL + "search.php";
    static final String ENDPOINT_BOOKING =  BuildConfig.BASE_URL + "booking.php";
    static final String ENDPOINT_LIST_BOOKING =  BuildConfig.BASE_URL + "getlistbooking.php";
    static final String ENDPOINT_DELETE_BOOKING =  BuildConfig.BASE_URL + "deletebooking.php";
    static final String ENDPOINT_LIST_LUXURY =  BuildConfig.BASE_URL + "luxurylist.php";
    static final String ENDPOINT_LIST_FAVORITE =  BuildConfig.BASE_URL + "getfavorite.php";
    static final String ENDPOINT_POST_FAVORITE =  BuildConfig.BASE_URL + "addfovorite.php";
    static final String ENDPOINT_DELETE_fAVORITE =  BuildConfig.BASE_URL + "deletefavorite.php";
    static final String ENDPOINT_ADD_LOVE_LUXURY =  BuildConfig.BASE_URL + "addlovefavorite.php";
    static final String ENDPOINT_LIST_VINHOMES_CITY =  BuildConfig.BASE_URL + "getvinhomes.php";
    static final String ENDPOINT_LIST_HOMESTAYS_VINHOMES =  BuildConfig.BASE_URL + "getlisthomestayvinhomes.php";
    static final String ENDPOINT_LIST_STORY =  BuildConfig.BASE_URL + "getliststory.php";
    static final String ENDPOINT_DELETE_STORY =  BuildConfig.BASE_URL + "deletestories.php";
    static final String ENDPOINT_GET_IMAGE_DATA =  BuildConfig.BASE_URL + "getimagedetail.php";
    static final String ENDPOINT_GET_USER = BuildConfig.BASE_URL + "getuser.php";

}
