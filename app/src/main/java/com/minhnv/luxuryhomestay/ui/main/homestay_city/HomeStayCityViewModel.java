package com.minhnv.luxuryhomestay.ui.main.homestay_city;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HomeStayCityViewModel extends BaseViewModel<HomeStayCityNavigator> {
    private static final String TAG = "HomeStayCityViewModel";

    public PublishSubject<List<Homestay>> listPublishSubject = PublishSubject.create();

    public HomeStayCityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Homestay>> listObservable = listPublishSubject.share();
    }

    public void loadlist(Integer InputIdHomeStays){
        getCompositeDisposable().add(
                getDataManager().doLoadHomeStay(new UserResponse.ServerListHomeStays(InputIdHomeStays))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().onSuccess();
                    listPublishSubject.onNext(response);
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                    AppLogger.d(TAG, "loadlist: "+throwable);
                })
        );
    }

    public void ServerLoadList(){
        getNavigator().loadList();
    }

}
