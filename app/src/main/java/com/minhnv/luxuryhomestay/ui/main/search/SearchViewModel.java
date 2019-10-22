package com.minhnv.luxuryhomestay.ui.main.search;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    private static final String TAG = "SearchViewModel";

    public PublishSubject<List<Homestay>> listPublishSubject = PublishSubject.create();

    public SearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Homestay>> listObservable = listPublishSubject.share();
    }

    public boolean isRequestValid(String rating){
        return !TextUtils.isEmpty(rating);
    }

    public void search(int rating){
        getCompositeDisposable().add(
                getDataManager().doSearchHomeStayFollowRating(new UserResponse.ServerSearchHomeStaysFollowRating(rating))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().onSuccess();
                    listPublishSubject.onNext(response);
                },throwable -> {
                    getNavigator().HandlerError(throwable);
                })
        );
    }

    public void ServerLoadList(){
        getNavigator().loadList();
    }
}
