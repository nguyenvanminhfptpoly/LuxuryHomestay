package com.minhnv.luxuryhomestay.utils;

import com.minhnv.luxuryhomestay.data.remote.DataClient;
import com.minhnv.luxuryhomestay.data.remote.RetrofitClient;

public class ApiUtils {
    public static final String baseUrl = "http://192.168.1.157/Demo/";

    public static DataClient getData(){
        return RetrofitClient.getRetrofit(baseUrl).create(DataClient.class);
    }
}
