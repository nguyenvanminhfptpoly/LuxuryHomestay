package com.minhnv.luxuryhomestay.data.remote;

import com.google.gson.Gson;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Comment;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.data.model.UserInfo;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppApiHelper implements ApiHelper {

    private static final String TAG = "AppApiHelper";
    private Gson gson;
    @Inject
    public AppApiHelper(Gson gson) {
        this.gson = gson;

    }


    @Override
    public Observable<String> doServerSignUp(UserResponse.ServerSignUpRequest request) {
        return  Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SIGNUP)
                .addBodyParameter(request)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<String> doServerSignIn(UserResponse.ServerSignInRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SIGN_IN)
                .addBodyParameter(request)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<Homestay>> doLoadHomeStay(UserResponse.ServerListHomeStays request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_HOMESTAYS)
                .addBodyParameter(request)
                .build()
                .getObjectListObservable(Homestay.class);
    }

    @Override
    public Observable<List<City>> doLoadCity() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_CITY)
                .build()
                .getObjectListObservable(City.class);
    }

    @Override
    public Observable<List<Homestay>> doLoadListHomeStayRating(UserResponse.ServerListHomeStaysRating request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_HOMESTAY_RATING)
                .addBodyParameter(request)
                .build()
                .getObjectListObservable(Homestay.class);
    }

    @Override
    public Observable<List<HomestayPrice>> doLoadListHomeStayPriceAsc() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_HOMESTAY_PRICE_ASC)
                .build()
                .getObjectListObservable(HomestayPrice.class);
    }

    @Override
    public Observable<List<Homestay>> doSearchHomeStayFollowRating(UserResponse.ServerSearchHomeStaysFollowRating rating) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SEARCH_HOMESTAY)
                .addBodyParameter(rating)
                .build()
                .getObjectListObservable(Homestay.class);
    }

    @Override
    public Observable<String> doServerBooking(UserResponse.ServerBooking booking) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_BOOKING)
                .addBodyParameter(booking)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<Booking>> doLoadListBooking(UserResponse.ServerListBooking booking) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_BOOKING)
                .addBodyParameter(booking)
                .build()
                .getObjectListObservable(Booking.class);
    }

    @Override
    public Observable<String> doServerDeleteBooking(UserResponse.ServerDeleteBooking booking) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_BOOKING)
                .addBodyParameter(booking)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<Luxury>> doLoadListLuxury() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_LUXURY)
                .build()
                .getObjectListObservable(Luxury.class);
    }

    @Override
    public Observable<String> doServerPostFavorite(UserResponse.ServerAddFavorite favorite) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_POST_FAVORITE)
                .addBodyParameter(favorite)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<Favorite>> doLoadListFavorite(UserResponse.ServerListBooking favorite) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_FAVORITE)
                .addBodyParameter(favorite)
                .build()
                .getObjectListObservable(Favorite.class);
    }

    @Override
    public Observable<String> doDeleteFavorite(UserResponse.ServerDeleteBooking favorite) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_fAVORITE)
                .addBodyParameter(favorite)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<String> doAddLoveLuxury(UserResponse.ServerAddLove love) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ADD_LOVE_LUXURY)
                .addBodyParameter(love)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<VinHome>> doLoadCityVinHomes() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_VINHOMES_CITY)
                .build()
                .getObjectListObservable(VinHome.class);
    }

    @Override
    public Observable<List<ListVinHomes>> doLoadListHomeStayVinHomes(UserResponse.ServerLoadHomeStayVinHomes homes) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_HOMESTAYS_VINHOMES)
                .addBodyParameter(homes)
                .build()
                .getObjectListObservable(ListVinHomes.class);
    }

    @Override
    public Observable<List<Story>> doLoadListStory() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_STORY)
                .build()
                .getObjectListObservable(Story.class);
    }

    @Override
    public Observable<String> doDeleteStories() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_STORY)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<ImageDetail>> doLoadListImageDetail(UserResponse.ServerGetImageDetail detail) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_IMAGE_DATA)
                .addBodyParameter(detail)
                .build()
                .getObjectListObservable(ImageDetail.class);
    }

    @Override
    public Observable<List<UserInfo>> doLoadInformationUser(UserResponse.ServerGetUser user) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_USER)
                .addBodyParameter(user)
                .build()
                .getObjectListObservable(UserInfo.class);
    }

    @Override
    public Observable<String> doAddCommentHomestay(UserResponse.ServerComment comment) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMMENT)
                .addBodyParameter(comment)
                .build()
                .getStringObservable();
    }

    @Override
    public Observable<List<Comment>> doGetListCmt(UserResponse.ServerGetListCmt cmt) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LIST_CMT)
                .addBodyParameter(cmt)
                .build()
                .getObjectListObservable(Comment.class);
    }

}
