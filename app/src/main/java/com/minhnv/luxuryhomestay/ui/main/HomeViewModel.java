package com.minhnv.luxuryhomestay.ui.main;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    private static final String TAG = "HomeViewModel";
    public PublishSubject<List<Homestay>> homeStayPublishObservable = PublishSubject.create();

    public PublishSubject<List<HomestayPrice>> listPublishSubject = PublishSubject.create();

    public PublishSubject<List<City>> listCityPublishSubject = PublishSubject.create();

    public PublishSubject<List<VinHome>> listBehaviorSubject = PublishSubject.create();

    public PublishSubject<List<Luxury>> listLuxuryBehaviorSubject = PublishSubject.create();

    public List<Homestay> list;
    public List<HomestayPrice> priceList;
    public List<City> cityList;
    public List<VinHome> vinHomeList;


    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Homestay>> homeStayObservable = homeStayPublishObservable.share();
        Observable<List<City>> listCityObservable = listCityPublishSubject.share();
        Observable<List<HomestayPrice>> listObservable = listPublishSubject.share();
        Observable<List<VinHome>> listVinHomeObservable = listBehaviorSubject.share();
        Observable<List<Luxury>> listLuxuryObservable = listLuxuryBehaviorSubject.share();
    }


    public void loadCity() {
        getCompositeDisposable().add(
                getDataManager().doLoadCity()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            cityList = new ArrayList<>(response);
                            listCityPublishSubject.onNext(response);
                            getNavigator().onSuccess();
                        }, throwable -> {
                            getNavigator().HandlerError(throwable);
                            AppLogger.d(TAG, "doLoadCity: " + throwable);
                        })
        );
    }

    public void loadListHomeStayRating() {
        getCompositeDisposable().add(
                getDataManager().doLoadListHomeStayRating(new UserResponse.ServerListHomeStaysRating(5))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            AppLogger.d(TAG, "loadListHomeStayRating: " + response);
                            list = new ArrayList<>(response);
                            homeStayPublishObservable.onNext(response);
                            getNavigator().onSuccess();
                        }, throwable -> {
                            getNavigator().HandlerError(throwable);
                            AppLogger.d(TAG, "loadListHomeStayRating: " + throwable);
                        })
        );
    }

    public void loadListHomeStaysPriceAsc() {
        getCompositeDisposable().add(
                getDataManager().doLoadListHomeStayPriceAsc()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            AppLogger.d(TAG, "loadListHomeStayRating: " + response);
                            listPublishSubject.onNext(response);
                            priceList = new ArrayList<>(response);
                            getNavigator().onSuccess();
                        }, throwable -> {
                            getNavigator().HandlerError(throwable);
                            AppLogger.d(TAG, "loadListHomeStayRating: " + throwable);
                        })
        );
    }

    public void loadCityVinHome(){
        getCompositeDisposable().add(
                getDataManager().doLoadCityVinHomes()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    vinHomeList = new ArrayList<>(response);
                    listBehaviorSubject.onNext(response);
                },throwable -> {
                    AppLogger.d(TAG,throwable);
                    getNavigator().HandlerError(throwable);
                })
        );
    }


    public void ServerLoadCityVinHomes(){
        getNavigator().doLoadCityVinHome();
    }

    public void ServerLoadHomeStaysPriceAsc() {
        getNavigator().doLoadHomeStaysPriceAsc();
    }

    public void ServerLoadHomeStaysRating() {
        getNavigator().doLoadHomeStaysRating();
    }

    public void ServerLoadCity() {
        getNavigator().doLoadCity();
    }

    public void openBookingFragment() {
        getNavigator().openBookingActivity();
    }

    public void openFavoriteFragment() {
        getNavigator().openFavoriteActivity();
    }

    public void openSocialFragment() {
        getNavigator().openSocialActivity();
    }

    public void openIntroductionFragment() {
        getNavigator().openIntroActivity();
    }

    public void logout() {
        getNavigator().logout();
    }

    public void closeDrawer() {
        getNavigator().close();
    }


}
