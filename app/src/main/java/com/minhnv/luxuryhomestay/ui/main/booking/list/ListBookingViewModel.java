package com.minhnv.luxuryhomestay.ui.main.booking.list;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class ListBookingViewModel extends BaseViewModel<ListBookingNavigator> {
    private static final String TAG = "ListBookingViewModel";
    private List<Booking> list;
    public PublishSubject<List<Booking>> listPublishSubject = PublishSubject.create();
    public ListBookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Booking>> listObservable = listPublishSubject.share();
    }

    public void loadList(){
        getCompositeDisposable().add(
                getDataManager().doLoadListBooking()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response ->{
                            list = new ArrayList<>(response);
                            getNavigator().onSuccess();
                            listPublishSubject.onNext(response);
                        },throwable -> {
                            getNavigator().handleError(throwable);
                            Timber.d(throwable);
                        })
        );
    }
    public void deleteBooking(Integer id){
        getCompositeDisposable().add(
                getDataManager().doServerDeleteBooking(new UserResponse.ServerDeleteBooking(id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response.equals("success")) {
                        getNavigator().onDeleteSuccess();
                    }else if(response.equals("eror")){
                        getNavigator().onFailed();
                    }
                },throwable -> {
                    getNavigator().handleError(throwable);
                    AppLogger.d(TAG,throwable);
                })
        );
    }
    public void ServerLoadList(){
        getNavigator().ServerLoadList();
    }
    public void ServerDeleteBooking(int id){
        getNavigator().ServerDeleteBooking(id);
    }
}
