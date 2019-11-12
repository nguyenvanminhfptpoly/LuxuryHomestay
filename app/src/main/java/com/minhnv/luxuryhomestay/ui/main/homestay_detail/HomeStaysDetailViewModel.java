package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class HomeStaysDetailViewModel extends BaseViewModel<HomeStayDetailNavigator> {
    private static final String TAG = "HomeStaysDetailViewMode";
    public PublishSubject<List<ImageDetail>> listPublishSubject = PublishSubject.create();
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
                        AppLogger.d(TAG, "addFavorite: "+response);
                    }else if(response.equals("Failed") || response.equals("Null")){
                        getNavigator().onFailed();
                    }
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                    AppLogger.d(TAG, "addFavorite: "+throwable);
                })
        );
    }

    public void loadList(int idHomeStay){
        getCompositeDisposable().add(
                getDataManager().doLoadListImageDetail(new UserResponse.ServerGetImageDetail(idHomeStay))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                            List<ImageDetail> imageDetails = new ArrayList<>(response);
                            listPublishSubject.onNext(response);
                        },throwable ->
                            getNavigator().HandlerError(throwable)
                )
        );
    }
    public void ServerPostFavorite(){
        getNavigator().addFavorite();
    }

    public void ServerLoadImageDetail(){
        getNavigator().loadImageDetail();
    }
}
