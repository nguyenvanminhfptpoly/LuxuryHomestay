package com.minhnv.luxuryhomestay.ui.main.vin_homes;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class VinHomeDetailViewModel extends BaseViewModel<VinHomeDetailNavigator> {
    private static final String TAG = "VinHomeDetailViewModel";
    public PublishSubject<List<ListVinHomes>> listPublishSubject = PublishSubject.create();

     public VinHomeDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
         Observable<List<ListVinHomes>> observable = listPublishSubject.share();
    }

    public void loadList(int id){
         getCompositeDisposable().add(
                 getDataManager().doLoadListHomeStayVinHomes(new UserResponse.ServerLoadHomeStayVinHomes(id))
                 .subscribeOn(getSchedulerProvider().io())
                 .observeOn(getSchedulerProvider().ui())
                 .subscribe(response -> {
                     List<ListVinHomes> listVinHomes = new ArrayList<>(response);
                     listPublishSubject.onNext(response);
                     getNavigator().onSuccess();
                 },throwable -> {
                     getNavigator().handlerError(throwable);
                 })
         );
    }

    public void ServerLoadList(){
         getNavigator().ServerLoadList();
    }
}
