package com.minhnv.luxuryhomestay.utils;

import com.minhnv.luxuryhomestay.data.remote.DataClient;
import com.minhnv.luxuryhomestay.data.remote.RetrofitClient;

public class ApiUtils {
    public static final String baseUrl = "https://luxuryhomestay.000webhostapp.com/";

    public static DataClient getData(){
        return RetrofitClient.getRetrofit(baseUrl).create(DataClient.class);
    }
}
