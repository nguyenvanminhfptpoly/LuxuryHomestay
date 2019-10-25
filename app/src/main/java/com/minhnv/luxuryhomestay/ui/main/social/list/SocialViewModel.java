package com.minhnv.luxuryhomestay.ui.main.social.list;

import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SocialViewModel extends BaseViewModel<SocialNavigator> {
    private static final String TAG = "SocialViewModel";
    public BehaviorSubject<List<Luxury>> listPublishSubject = BehaviorSubject.create();
    public SocialViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Luxury>> listObservable = listPublishSubject.share();
    }
    public void luxuryList(){
        getCompositeDisposable().add(
                getDataManager().doLoadListLuxury()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    listPublishSubject.onNext(response);
                    getNavigator().onSuccess();
                    AppLogger.d(TAG, "luxuryList: "+response);
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                })
        );
    }

    public void ServerLoadListLuxury(){
        getNavigator().loadList();
    }

   public void addLoveForPost(Integer id, Integer countLove){
        getCompositeDisposable().add(
                getDataManager().doAddLoveLuxury(new UserResponse.ServerAddLove(id,countLove))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response.equals("Success")) {
                        getNavigator().onSuccess();
                        AppLogger.d(TAG, "addLoveForPost: "+response);
                    }
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                    AppLogger.d(TAG, "addLoveForPost: "+throwable);
                })
        );
   }


}
