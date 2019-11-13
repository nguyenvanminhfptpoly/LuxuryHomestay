package com.minhnv.luxuryhomestay.ui.intro;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.UserInfo;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class IntroductionViewModel extends BaseViewModel<IntroductionNavigator> {
    public PublishSubject<List<UserInfo>> listPublishSubject = PublishSubject.create();

    public IntroductionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<UserInfo>> listObservable = listPublishSubject.share();
    }
    public void loadUser(int phoneNumber){
        getCompositeDisposable().add(
                getDataManager().doLoadInformationUser(new UserResponse.ServerGetUser(phoneNumber))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                           List<UserInfo> userInfos = new ArrayList<>(response);
                           listPublishSubject.onNext(response);
                           getNavigator().onSuccess();
                        }
                        ,throwable -> {
                            getNavigator().ANError(throwable);
                        }
                )
        );
    }

    public void ServerLoadUser(){
        getNavigator().loadUser();
    }
}
