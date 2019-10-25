package com.minhnv.luxuryhomestay.data.remote;

import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.data.model.VinHome;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface ApiHelper {
    Observable<String> doServerSignUp(UserResponse.ServerSignUpRequest request);

    Observable<String> doServerSignIn(UserResponse.ServerSignInRequest request);

    Observable<List<Homestay>> doLoadHomeStay(UserResponse.ServerListHomeStays request);

    Observable<List<City>> doLoadCity();

    Observable<List<Homestay>> doLoadListHomeStayRating(UserResponse.ServerListHomeStaysRating request);

    Observable<List<HomestayPrice>> doLoadListHomeStayPriceAsc();

    Observable<List<Homestay>> doSearchHomeStayFollowRating(UserResponse.ServerSearchHomeStaysFollowRating rating);

    Observable<String> doServerBooking(UserResponse.ServerBooking booking);

    Observable<List<Booking>> doLoadListBooking();

    Observable<String> doServerDeleteBooking(UserResponse.ServerDeleteBooking booking);

    Observable<List<Luxury>> doLoadListLuxury();

    Observable<String> doServerPostFavorite(UserResponse.ServerAddFavorite favorite);

    Observable<List<Favorite>> doLoadListFavorite();

    Observable<String> doDeleteFavorite(UserResponse.ServerDeleteBooking favorite);

    Observable<String> doAddLoveLuxury(UserResponse.ServerAddLove love);

    Observable<List<VinHome>> doLoadCityVinHomes();

    Observable<List<ListVinHomes>> doLoadListHomeStayVinHomes(UserResponse.ServerLoadHomeStayVinHomes homes);

}
