package com.minhnv.luxuryhomestay.ui.main.homestay_hot;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HomeStayHotViewModel extends BaseViewModel<HomeStayHotNavigator> {
    private static final String TAG = "HomeStayHotViewModel";
    public PublishSubject<List<Homestay>> homeStayPublishObservable = PublishSubject.create();
    public HomeStayHotViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Homestay>> homeStayObservable = homeStayPublishObservable.share();
    }

    public void loadListHomeStayRating() {
        getCompositeDisposable().add(
                getDataManager().doLoadListHomeStayRating(new UserResponse.ServerListHomeStaysRating(5))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            Log.d(TAG, "loadListHomeStayRating: " + response);
                            homeStayPublishObservable.onNext(response);
                            getNavigator().onSuccess();
                        }, throwable -> {
                            getNavigator().HandlerError(throwable);
                            Log.d(TAG, "loadListHomeStayRating: " + throwable);
                        })
        );
    }

    public void ServerLoadHomeStaysRating() {
        getNavigator().doLoadHomeStaysRating();
    }

}
