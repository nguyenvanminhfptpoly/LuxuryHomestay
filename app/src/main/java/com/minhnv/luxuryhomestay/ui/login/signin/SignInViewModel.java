package com.minhnv.luxuryhomestay.ui.login.signin;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.User;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class SignInViewModel extends BaseViewModel<SignInNavigator> {

    private static final String TAG = "SignInViewModel";
    public BehaviorSubject<List<User>> listBehaviorSubject = BehaviorSubject.create();

    public SignInViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        Observable<List<User>> observable = listBehaviorSubject.share();
    }

    public boolean isRequestValid(String phoneNumber,String password){
        if(TextUtils.isEmpty(phoneNumber)){
            return false;
        }
        return !TextUtils.isEmpty(password);
    }

    public void signin(String phonenumber,String password){
        getCompositeDisposable().add(getDataManager()
            .doServerSignIn(new UserResponse.ServerSignInRequest(phonenumber,password))
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response -> {
                if(response.equals("Success")){
                    getDataManager().setCurrentPassword(phonenumber);
                    getDataManager().setCurrentPhoneNumber(password);
                    getNavigator().onSuccess();
                }else if(response.equals("Failed") || response.equals("Null")){
                    getNavigator().onFailed();
                }
            },throwable -> {
                getNavigator().handlerError(throwable);
                AppLogger.d("Sign In failed"+throwable);
            })
        );
    }


    public void startActivity(){
        getNavigator().openSignInActivity();
    }

    public void onServerSignIn(){
        getNavigator().login();
    }



    //test

}
