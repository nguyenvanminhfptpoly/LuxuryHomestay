package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class HomeStaysDetailViewModel extends BaseViewModel<HomeStayDetailNavigator> {
    private static final String TAG = "HomeStaysDetailViewMode";
    public HomeStaysDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addFavorite(String image,String name,String address){
        getCompositeDisposable().add(
                getDataManager().doServerPostFavorite(new UserResponse.ServerAddFavorite(name,image,address))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response.equals("Success")) {
                        getNavigator().onSuccess();
                        Log.d(TAG, "addFavorite: "+response);
                    }
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                    Log.d(TAG, "addFavorite: "+throwable);
                })
        );
    }
    public void ServerPostFavorite(){
        getNavigator().addFavorite();
    }
}
