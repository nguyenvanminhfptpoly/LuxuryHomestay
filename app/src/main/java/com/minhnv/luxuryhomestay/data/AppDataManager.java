package com.minhnv.luxuryhomestay.data;

import android.content.Context;

import com.google.gson.Gson;
import com.minhnv.luxuryhomestay.data.local.db.DbHelper;
import com.minhnv.luxuryhomestay.data.local.preference.PreferenceHelper;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.data.model.User;
import com.minhnv.luxuryhomestay.data.model.UserInfo;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = "AppDataManager";

    private final ApiHelper apiHelper;

    private final Context context;

    private final PreferenceHelper preferenceHelper;
    private Gson gson;

    private DbHelper dbHelper;


    @Inject
    AppDataManager(ApiHelper apiHelper, Context context, PreferenceHelper preferenceHelper, Gson gson, DbHelper dbHelper) {
        this.apiHelper = apiHelper;
        this.context = context;
        this.preferenceHelper = preferenceHelper;
        this.gson = gson;
        this.dbHelper = dbHelper;
    }


    public String accessToken() {
        return preferenceHelper.accessToken();
    }

    public void setAccessToken(String accessToken) {
        preferenceHelper.setAccessToken(accessToken);
    }


    @Override
    public String getCurrentPhoneNumber() {
        return preferenceHelper.getCurrentPhoneNumber();
    }

    @Override
    public void setCurrentPhoneNumber(String currentPhoneNumber) {
        preferenceHelper.setCurrentPhoneNumber(currentPhoneNumber);
    }

    @Override
    public String getCurrentAddress() {
        return preferenceHelper.getCurrentAddress();
    }

    @Override
    public void setCurrentAddress(String currentAddress) {
        preferenceHelper.setCurrentAddress(currentAddress);
    }

    @Override
    public String getCurrentPassword() {
        return preferenceHelper.getCurrentPassword();
    }

    @Override
    public void setCurrentPassword(String currentPassword) {
        preferenceHelper.setCurrentPassword(currentPassword);
    }

    @Override
    public Boolean getCheckedToSwitchDarkMode() {
        return preferenceHelper.getCheckedToSwitchDarkMode();
    }

    @Override
    public void setCheckedToSwitchDarkMode(Boolean isCheck) {
        preferenceHelper.setCheckedToSwitchDarkMode(isCheck);
    }

    @Override
    public String getCurrentId() {
        return preferenceHelper.getCurrentId();
    }

    @Override
    public void setCurrentId(String currentId) {

    }

    @Override
    public void updateUserInfo(String password, String phoneNumber, String address) {
        setCurrentAddress(address);
        setCurrentPhoneNumber(phoneNumber);
        setCurrentAddress(address);

    }

    @Override
    public Observable<String> doServerSignUp(UserResponse.ServerSignUpRequest request) {
        return apiHelper.doServerSignUp(request);
    }

    @Override
    public Observable<String> doServerSignIn(UserResponse.ServerSignInRequest request) {
        return apiHelper.doServerSignIn(request);
    }

    @Override
    public Observable<List<Homestay>> doLoadHomeStay(UserResponse.ServerListHomeStays request) {
        return apiHelper.doLoadHomeStay(request);
    }

    @Override
    public Observable<List<City>> doLoadCity() {
        return apiHelper.doLoadCity();
    }

    @Override
    public Observable<List<Homestay>> doLoadListHomeStayRating(UserResponse.ServerListHomeStaysRating request) {
        return apiHelper.doLoadListHomeStayRating(request);
    }

    @Override
    public Observable<List<HomestayPrice>> doLoadListHomeStayPriceAsc() {
        return apiHelper.doLoadListHomeStayPriceAsc();
    }

    @Override
    public Observable<List<Homestay>> doSearchHomeStayFollowRating(UserResponse.ServerSearchHomeStaysFollowRating rating) {
        return apiHelper.doSearchHomeStayFollowRating(rating);
    }

    @Override
    public Observable<String> doServerBooking(UserResponse.ServerBooking booking) {
        return apiHelper.doServerBooking(booking);
    }

    @Override
    public Observable<List<Booking>> doLoadListBooking(UserResponse.ServerListBooking booking) {
        return apiHelper.doLoadListBooking(booking);
    }

    @Override
    public Observable<String> doServerDeleteBooking(UserResponse.ServerDeleteBooking booking) {
        return apiHelper.doServerDeleteBooking(booking);
    }

    @Override
    public Observable<List<Luxury>> doLoadListLuxury() {
        return apiHelper.doLoadListLuxury();
    }

    @Override
    public Observable<String> doServerPostFavorite(UserResponse.ServerAddFavorite favorite) {
        return apiHelper.doServerPostFavorite(favorite);
    }

    @Override
    public Observable<List<Favorite>> doLoadListFavorite(UserResponse.ServerListBooking favorite) {
        return apiHelper.doLoadListFavorite(favorite);
    }

    @Override
    public Observable<String> doDeleteFavorite(UserResponse.ServerDeleteBooking favorite) {
        return apiHelper.doDeleteFavorite(favorite);
    }

    @Override
    public Observable<String> doAddLoveLuxury(UserResponse.ServerAddLove love) {
        return apiHelper.doAddLoveLuxury(love);
    }

    @Override
    public Observable<List<VinHome>> doLoadCityVinHomes() {
        return apiHelper.doLoadCityVinHomes();
    }

    @Override
    public Observable<List<ListVinHomes>> doLoadListHomeStayVinHomes(UserResponse.ServerLoadHomeStayVinHomes homes) {
        return apiHelper.doLoadListHomeStayVinHomes(homes);
    }

    @Override
    public Observable<List<Story>> doLoadListStory() {
        return apiHelper.doLoadListStory();
    }

    @Override
    public Observable<String> doDeleteStories() {
        return apiHelper.doDeleteStories();
    }

    @Override
    public Observable<List<ImageDetail>> doLoadListImageDetail(UserResponse.ServerGetImageDetail detail) {
        return apiHelper.doLoadListImageDetail(detail);
    }

    @Override
    public Observable<List<UserInfo>> doLoadInformationUser(UserResponse.ServerGetUser user) {
        return apiHelper.doLoadInformationUser(user);
    }


    @Override
    public Observable<List<User>> getAll() {
        return dbHelper.getAll();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return dbHelper.insertUser(user);
    }
}
