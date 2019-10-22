package com.minhnv.luxuryhomestay.data.remote;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("uploadimage.php")
    Call<String> UploadPhot(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("luxurypost.php")
    Call<String> insertData(@Field("image") String image,
                            @Field("detail") String detail,
                            @Field("username") String username,
                            @Field("address") String address);
}
