package com.minhnv.luxuryhomestay.ui.main.booking.list;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ListBookingViewModel extends BaseViewModel<ListBookingNavigator> {
    private static final String TAG = "ListBookingViewModel";
    public Observable<List<Booking>> listObservable;
    public PublishSubject<List<Booking>> listPublishSubject = PublishSubject.create();
    public ListBookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listObservable = listPublishSubject.share();
    }

    public void loadList(){
        getCompositeDisposable().add(
                getDataManager().doLoadListBooking()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response ->{
                            getNavigator().onSuccess();
                            listPublishSubject.onNext(response);
                        },throwable -> {
                            getNavigator().handleError(throwable);
                            Log.d(TAG, "loadList: "+throwable);
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
                    }
                },throwable -> {
                    getNavigator().handleError(throwable);
                    Log.d(TAG, "deleteBooking: "+throwable);
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
