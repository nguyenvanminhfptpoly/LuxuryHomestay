package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.Comment;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HomeStaysDetailViewModel extends BaseViewModel<HomeStayDetailNavigator> {
    private static final String TAG = "HomeStaysDetailViewMode";
    public PublishSubject<List<ImageDetail>> listPublishSubject = PublishSubject.create();
    public PublishSubject<List<Comment>> listPublishSubjectCmt = PublishSubject.create();
    public HomeStaysDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<ImageDetail>> listObservable = listPublishSubject.share();
        Observable<List<Comment>> observableComment = listPublishSubjectCmt.share();

    }

    public void addFavorite(String image,String name,String address,int idUser){
        getCompositeDisposable().add(
                getDataManager().doServerPostFavorite(new UserResponse.ServerAddFavorite(name,image,address,idUser))
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


    public boolean isRequestValid(String username,String comment, int rating){
        if(TextUtils.isEmpty(username)){
            getNavigator().CommentIsEmpty();
            return false;
        } else if(TextUtils.isEmpty(comment)){
            getNavigator().CommentIsEmpty();
            return false;
        } else if(rating <= 0){
            getNavigator().ratingFailed();
            return false;
        }
        return !TextUtils.isEmpty(username);
    }

    public void addComment(String username, String comment, int rating, int idHomestay){
        getCompositeDisposable().add(
                getDataManager().doAddCommentHomestay(new UserResponse.ServerComment(username, comment, rating, idHomestay))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(
                                response -> {
                                    if(response.equals("Success")){
                                        getNavigator().commentSuccess();
                                    } else if(response.equals("Failed") || response.equals("Null")){
                                        getNavigator().commentFailed();
                                    }
                                },throwable -> {
                                    getNavigator().HandlerError(throwable);
                                }
                        )
        );
    }

    public void getListComment(int idHomestay){
        getCompositeDisposable().add(
                getDataManager().doGetListCmt(new UserResponse.ServerGetListCmt(idHomestay))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response ->{
                            List<Comment> comments = new ArrayList<>(response);
                            listPublishSubjectCmt.onNext(response);
                        }, throwable -> {
                            getNavigator().HandlerError(throwable);
                        }
                )
        );
    }

    public void ServerloadListComment(){
        getNavigator().LoadListComment();
    }
    public void ServerPostFavorite(){
        getNavigator().addFavorite();
    }
    public void ServerLoadImageDetail(){
        getNavigator().loadImageDetail();
    }
}
