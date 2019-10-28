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

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    private static final String TAG = "HomeViewModel";
    public BehaviorSubject<List<Homestay>> homeStayPublishObservable = BehaviorSubject.create();

    public BehaviorSubject<List<HomestayPrice>> listPublishSubject = BehaviorSubject.create();

    public BehaviorSubject<List<City>> listCityPublishSubject = BehaviorSubject.create();

    public BehaviorSubject<List<VinHome>> listBehaviorSubject = BehaviorSubject.create();

    public BehaviorSubject<List<Luxury>> listLuxuryBehaviorSubject = BehaviorSubject.create();


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
