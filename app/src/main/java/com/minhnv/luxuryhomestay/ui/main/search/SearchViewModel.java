package com.minhnv.luxuryhomestay.ui.main.search;

import android.content.Context;
import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.ui.base.ViewModelType;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    private static final String TAG = "SearchViewModel";
    private List<Homestay> list;
    public PublishSubject<List<Homestay>> listPublishSubject = PublishSubject.create();

    public SearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<Homestay>> listObservable = listPublishSubject.share();
    }

    public boolean isRequestValid(String rating){
        return !TextUtils.isEmpty(rating);
    }

    public void search(String rating,String rating2, String price){
        getCompositeDisposable().add(
                getDataManager().doSearchHomeStayFollowRating(new UserResponse.ServerSearchHomeStaysFollowRating(rating,rating2, price))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().onSuccess();
                    list = new ArrayList<>(response);
                    listPublishSubject.onNext(response);
                },throwable ->
                    getNavigator().HandlerError(throwable)
                )
        );
    }

    public void ServerLoadList(){
        getNavigator().loadList();
    }


//
//    //test
//    static class Input{
//        public Observable<Integer> rating;
//        public Observable<Boolean> triggerSearch;
//
//        public Input(Observable<Integer> rating) {
//            this.rating = rating;
//        }
//    }
//
//    static class Output{
//        public Observable<List<Homestay>> listObservable;
//
//        public Output(PublishSubject<Boolean> subject) {
//        }
//    }
//
//    @Override
//    public Output transform(Context context, Input input) {
//        PublishSubject<Boolean> subject = PublishSubject.create();
//        BehaviorSubject<Integer> rating = BehaviorSubject.create();
//
//        getCompositeDisposable().add(
//                input.triggerSearch.subscribeOn(
//                        getSchedulerProvider().io()
//                ).observeOn(
//                        getSchedulerProvider().ui()
//                )
//                .subscribe(response -> {
//                    subject.onNext(response);
//                    getDataManager().doSearchHomeStayFollowRating(new UserResponse.ServerSearchHomeStaysFollowRating(rating));
//                })
//        );
//
//        return new Output(subject);
//    }
}
